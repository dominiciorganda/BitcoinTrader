package com.example.demo.Controllers;

import com.example.demo.DTOs.FundsDTO;
import com.example.demo.DTOs.TransactionDTO;
import com.example.demo.Services.FundsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("/CoinTrader/funds")
public class FundsController {

    @Autowired
    private FundsService fundsService;

    @PostMapping("/addFunds")
    public ResponseEntity addFunds(@RequestBody FundsDTO fundsDTO) {
        fundsService.addFunds(fundsDTO);
        return new ResponseEntity<>("Transaction added", HttpStatus.OK);
    }

    @GetMapping("getMoney")
    public double getMoney() {
        return fundsService.getMoney();
    }

}
