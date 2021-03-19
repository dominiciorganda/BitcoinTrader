package com.example.demo.Controllers;

import com.example.demo.DTOs.CoinDTO;
import com.example.demo.Mappers.CoinMapper;
import com.example.demo.Services.BitcoinService;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/CoinTrader/bitcoin")
public class BitcoinController {

    private BitcoinService bitcoinService = new BitcoinService();

    public BitcoinController() throws IOException {
    }

    @GetMapping("/getLastMonth")
    public List<CoinDTO> getLastMonth() {
        return CoinMapper.mapCoinListtoCoinDTOList(bitcoinService.getLastMonth());
    }

    @GetMapping("/getMax")
    public CoinDTO getMax() {
        return CoinMapper.mapCointoCoinDTO(bitcoinService.getAllTimeMax());
    }

    @GetMapping("/getLast")
    public CoinDTO getLast() {
        return CoinMapper.mapCointoCoinDTO(bitcoinService.getLast());
    }

    @GetMapping("/getActual")
    public CoinDTO getActual() throws IOException {
        return CoinMapper.mapCointoCoinDTO(bitcoinService.getActual());
    }

    @GetMapping("/getLastX/{number}")
    public List<CoinDTO> getLastX(@PathVariable("number") int num) {
        return CoinMapper.mapCoinListtoCoinDTOList(bitcoinService.getLastX(num));
    }

    @GetMapping("prediction")
    public ResponseEntity<?> makePrediction() {
        writeToCSV(CoinMapper.mapCoinListtoCoinDTOList(bitcoinService.getLastX(50)));
        LocalDate localDate = LocalDate.now().plusDays(1);
        List<CoinDTO> coinDTOS = new ArrayList<>();
        try {
            String argument = localDate.toString().replaceAll("\\-", "");

            ProcessBuilder pb = new ProcessBuilder("python", "E:\\licenta\\prediction\\venv\\bitcoinPrediction.py", "" + argument + "-" + argument);
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

    @GetMapping("getAnualMax")
    public CoinDTO getAnualMax() {
        return CoinMapper.mapCointoCoinDTO(bitcoinService.getAnualMax());
    }

    @GetMapping("getAnualMin")
    public CoinDTO getAnualMin() {
        return CoinMapper.mapCointoCoinDTO(bitcoinService.getAnualMin());
    }


    private static final String CSV_SEPARATOR = ",";

    private static void writeToCSV(List<CoinDTO> coinDTOS) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("CSV\\bitcoins.csv"), "UTF-8"));
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

}
