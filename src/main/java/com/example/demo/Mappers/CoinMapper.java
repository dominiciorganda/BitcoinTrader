package com.example.demo.Mappers;

import com.example.demo.DTOs.CoinDTO;
import com.example.demo.Entities.Coin;

import java.util.ArrayList;
import java.util.List;

public class CoinMapper {
    public static CoinDTO mapCointoCoinDTO(Coin coin) {
        CoinDTO coinDTO = new CoinDTO();
        if(coin == null)
            return null;
        coinDTO.setDate(coin.getDate());
        coinDTO.setPrice(coin.getPrice());
        return coinDTO;
    }

    public static Coin mapCoinDTOtoCoin(CoinDTO coinDTO) {
        Coin coin = new Coin();
        if(coinDTO == null)
            return null;
        coin.setDate(coinDTO.getDate());
        coin.setPrice(coinDTO.getPrice());
        return coin;
    }

    public static List<CoinDTO>mapCoinListtoCoinDTOList(List<Coin> coinList){
        List<CoinDTO> coinDTOS = new ArrayList<>();
        if(coinList == null)
            return null;
        for(Coin coin:coinList)
            coinDTOS.add(mapCointoCoinDTO(coin));
        return coinDTOS;
    }
}
