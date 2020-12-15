package com.example.demo.Services;

import com.example.demo.Controllers.BitcoinController;
import com.example.demo.Entities.Bitcoin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class BitcoinService {
    private BitcoinController bitcoinController = new BitcoinController();

    public BitcoinService() throws IOException {
    }

    @GetMapping("/getLastMonth")
    public List<Bitcoin> getLastMonth() {
        return bitcoinController.getLastMonth();
    }

    @GetMapping("/getMax")
    public Bitcoin getMax() {
        return bitcoinController.getAllTimeMax();
    }

    @GetMapping("/getLast")
    public Bitcoin getLast() {
        return bitcoinController.getLast();
    }

    @GetMapping("/getActual")
    public Bitcoin getActual() throws IOException {
        return bitcoinController.getActual();
    }

    @GetMapping("getAnualMax")
    public Bitcoin getAnualMax() {
        return bitcoinController.getAnualMax();
    }

    @GetMapping("getAnualMin")
    public Bitcoin getAnualMin() {
        return bitcoinController.getAnualMin();
    }


}

