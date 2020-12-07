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

    @GetMapping("/getAll")
    public List<Bitcoin> getAll() {
        return bitcoinController.getAll();
    }
    @GetMapping("/getMax")
    public Bitcoin getMax() {
        return bitcoinController.getAllTimeMax();
    }
}

