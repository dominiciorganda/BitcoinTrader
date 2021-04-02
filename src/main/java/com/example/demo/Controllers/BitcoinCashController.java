package com.example.demo.Controllers;


import com.example.demo.DTOs.CoinDTO;
import com.example.demo.Mappers.CoinMapper;
import com.example.demo.Services.BitcoinCashService;
import com.example.demo.Services.LitecoinService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Api
@RestController
@RequestMapping("/CoinTrader/bitcoincash")
public class BitcoinCashController {
    public BitcoinCashController() throws IOException {
    }

    @Autowired
    private BitcoinCashService bitcoinCashService;

    @GetMapping("/getLastMonth")
    public List<CoinDTO> getLastMonth() {
        return CoinMapper.mapCoinListtoCoinDTOList(bitcoinCashService.getLastMonth());
    }

    @GetMapping("/getMax")
    public CoinDTO getMax() {
        return CoinMapper.mapCointoCoinDTO(bitcoinCashService.getAllTimeMax());
    }

    @GetMapping("/getLast")
    public CoinDTO getLast() {
        return CoinMapper.mapCointoCoinDTO(bitcoinCashService.getLast());
    }

    @GetMapping("/getLastX/{number}")
    public List<CoinDTO> getLastX(@PathVariable("number") int num) {
        return CoinMapper.mapCoinListtoCoinDTOList(bitcoinCashService.getLastX(num));
    }

    @GetMapping(value = "/getActual")
    public CoinDTO getActual() throws IOException {
        return CoinMapper.mapCointoCoinDTO(bitcoinCashService.getActual());
    }

    @GetMapping("/getAnualMax")
    public CoinDTO getAnualMax() {
        return CoinMapper.mapCointoCoinDTO(bitcoinCashService.getAnualMax());
    }

    @GetMapping("/getAnualMin")
    public CoinDTO getAnualMin() {
        return CoinMapper.mapCointoCoinDTO(bitcoinCashService.getAnualMin());
    }

    private static final String CSV_SEPARATOR = ",";

    @Async
    void writeToCSV(List<CoinDTO> coinDTOS) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("CSV\\bitcoincashs.csv"), "UTF-8"));
            bw.write("Date" + CSV_SEPARATOR + "Price");
            bw.newLine();
            for (CoinDTO coinDTO : coinDTOS) {
                String oneLine = coinDTO.getDate().replaceAll("\\-", "") +
                        CSV_SEPARATOR +
                        coinDTO.getPrice();
                bw.write(oneLine);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException ignored) {
        }
    }

    @GetMapping("/prediction")
    public ResponseEntity<?> makePrediction() {
        writeToCSV(CoinMapper.mapCoinListtoCoinDTOList(bitcoinCashService.getLastX(50)));
        LocalDate localDate = LocalDate.now().plusDays(1);
        List<CoinDTO> coinDTOS = new ArrayList<>(getLastX(6));
        try {
            String argument = localDate.toString().replaceAll("\\-", "");

            ProcessBuilder pb = new ProcessBuilder("python", "E:\\licenta\\prediction\\venv\\bitcoincashPrediction.py", "" + argument + "-" + argument);
            Process p = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            for (int i = 0; i < 5; i++) {
                String price = in.readLine();
                String date = localDate.plusDays(i).toString();
                coinDTOS.add(new CoinDTO(date, Double.parseDouble(price)));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return ResponseEntity.ok(coinDTOS);
    }
}
