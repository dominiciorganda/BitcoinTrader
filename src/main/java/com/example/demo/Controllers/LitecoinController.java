package com.example.demo.Controllers;


import com.example.demo.DTOs.CoinDTO;
import com.example.demo.Mappers.CoinMapper;
import com.example.demo.Services.LitecoinService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/CoinTrader/litecoin")
public class LitecoinController {
    public LitecoinController() throws IOException {
    }

    @Autowired
    private LitecoinService litecoinService;

    @GetMapping("/getLastMonth")
    public List<CoinDTO> getLastMonth() {
        return CoinMapper.mapCoinListtoCoinDTOList(litecoinService.getLastMonth());
    }

    @GetMapping("/getMax")
    public CoinDTO getMax() {
        return CoinMapper.mapCointoCoinDTO(litecoinService.getAllTimeMax());
    }

    @GetMapping("/getLast")
    public CoinDTO getLast() {
        return CoinMapper.mapCointoCoinDTO(litecoinService.getLast());
    }

    @GetMapping("/getLastX/{number}")
    public List<CoinDTO> getLastX(@PathVariable("number") int num) {
        return CoinMapper.mapCoinListtoCoinDTOList(litecoinService.getLastX(num));
    }

    @GetMapping(value = "/getActual")
    public CoinDTO getActual() throws IOException {
        return CoinMapper.mapCointoCoinDTO(litecoinService.getActual());
    }

    @GetMapping("/getAnualMax")
    public CoinDTO getAnualMax() {
        return CoinMapper.mapCointoCoinDTO(litecoinService.getAnualMax());
    }

    @GetMapping("/getAnualMin")
    public CoinDTO getAnualMin() {
        return CoinMapper.mapCointoCoinDTO(litecoinService.getAnualMin());
    }

    private static final String CSV_SEPARATOR = ",";

    private static void writeToCSV(List<CoinDTO> coinDTOS) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("CSV\\litecoins.csv"), "UTF-8"));
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
        writeToCSV(CoinMapper.mapCoinListtoCoinDTOList(litecoinService.getLastX(50)));
        LocalDate localDate = LocalDate.now().plusDays(1);
        List<CoinDTO> coinDTOS = new ArrayList<>();
        try {
            String argument = localDate.toString().replaceAll("\\-", "");

            ProcessBuilder pb = new ProcessBuilder("python", "E:\\licenta\\prediction\\venv\\litecoinPrediction.py", "" + argument + "-" + argument);
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
