package com.example.demo.Controllers;

import com.example.demo.Entities.Bitcoin;
import com.example.demo.Services.BitcoinService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Api
@RestController
@RequestMapping("/BitcoinTrader")
public class BitcoinController {

    private BitcoinService bitcoinService = new BitcoinService();

    public BitcoinController() throws IOException {
    }

    @GetMapping("/getLastMonth")
    public List<Bitcoin> getLastMonth() {
        return bitcoinService.getLastMonth();
    }

    @GetMapping("/getMax")
    public Bitcoin getMax() {
        return bitcoinService.getAllTimeMax();
    }

    @GetMapping("/getLast")
    public Bitcoin getLast() {
        return bitcoinService.getLast();
    }

    @GetMapping("/getActual")
    public Bitcoin getActual() throws IOException {
        return bitcoinService.getActual();
    }

    @GetMapping("getAnualMax")
    public Bitcoin getAnualMax() {
        return bitcoinService.getAnualMax();
    }

    @GetMapping("getAnualMin")
    public Bitcoin getAnualMin() {
        return bitcoinService.getAnualMin();
    }

}
