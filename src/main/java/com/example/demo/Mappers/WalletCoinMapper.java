package com.example.demo.Mappers;

import com.example.demo.DTOs.CoinDTO;
import com.example.demo.DTOs.WalletCoinDTO;
import com.example.demo.Entities.Coin;
import com.example.demo.Entities.WalletCoin;

import java.util.ArrayList;
import java.util.List;

public class WalletCoinMapper {
    public static WalletCoinDTO mapWalletCointoWalletCoinDTO(WalletCoin coin) {
        WalletCoinDTO coinDTO = new WalletCoinDTO();
        if (coin == null)
            return null;
        coinDTO.setCoinName(coin.getCoinName());
        coinDTO.setActualPrice(coin.getActualPrice());
        coinDTO.setAmount(coin.getAmount());
        coinDTO.setValue(coin.getValue());
        coinDTO.setPaid(coin.getPaid());
        return coinDTO;
    }

    public static WalletCoin mapWalletCoinDTOtoWalletCoin(WalletCoinDTO coinDTO) {
        WalletCoin coin = new WalletCoin();
        if (coinDTO == null)
            return null;
        coin.setCoinName(coinDTO.getCoinName());
        coin.setActualPrice(coinDTO.getActualPrice());
        coin.setAmount(coinDTO.getAmount());
        coin.setValue(coinDTO.getValue());
        coin.setPaid(coinDTO.getPaid());
        return coin;
    }

    public static List<WalletCoinDTO> mapWalletCoinListtoWalletCoinDTOList(List<WalletCoin> coinList) {
        List<WalletCoinDTO> coinDTOS = new ArrayList<>();
        if (coinList == null)
            return null;
        for (WalletCoin coin : coinList)
            coinDTOS.add(mapWalletCointoWalletCoinDTO(coin));
        return coinDTOS;
    }
}
