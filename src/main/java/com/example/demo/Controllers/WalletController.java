package com.example.demo.Controllers;

import com.example.demo.DTOs.TransactionDTO;
import com.example.demo.DTOs.TransactionDateDTO;
import com.example.demo.DTOs.WalletCoinDTO;
import com.example.demo.Entities.Coin;
import com.example.demo.Mappers.TransactionMapper;
import com.example.demo.Mappers.WalletCoinMapper;
import com.example.demo.Services.WalletService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Api
@RestController
@RequestMapping("/CoinTrader/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/makeTransaction")
    public ResponseEntity makeTransaction(@RequestBody TransactionDTO transactionDTO) {
        walletService.addTransaction(transactionDTO);
        return new ResponseEntity<>("Transaction added", HttpStatus.OK);
    }

    @GetMapping("/allTransactions")
    public List<TransactionDateDTO> getAllTransactions() {
        return TransactionMapper.mapTransactionListtoTransactionDateDTOList(walletService.getUserTransactions());
    }

    @GetMapping("/getWallet")
    public List<WalletCoinDTO> getWallet() throws IOException {
        return WalletCoinMapper.mapWalletCoinListtoWalletCoinDTOList(walletService.getWalletCoins());
    }

    @GetMapping("/getPortfolio")
    public List<Coin> getPortfolio() throws IOException, ParseException {
        return walletService.getWalletCoinsPortfolio();
    }


}
