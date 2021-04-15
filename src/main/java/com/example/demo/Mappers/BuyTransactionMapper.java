package com.example.demo.Mappers;

import com.example.demo.DTOs.BuyTransactionDTO;
import com.example.demo.Entities.BuytransactionEntity;

import java.util.ArrayList;
import java.util.List;

public class BuyTransactionMapper {
    public static BuyTransactionDTO mapTransactiontoTransactionDTO(BuytransactionEntity buytransactionEntity) {
        BuyTransactionDTO buyTransactionDTO = new BuyTransactionDTO();
        if (buytransactionEntity == null)
            return null;
        buyTransactionDTO.setActualPrice(buytransactionEntity.getActualPrice());
        buyTransactionDTO.setAmount(buytransactionEntity.getAmount());
        buyTransactionDTO.setCoin(buytransactionEntity.getCoin());
        buyTransactionDTO.setPaidPrice(buytransactionEntity.getPaidPrice());
        return buyTransactionDTO;
    }

    public static BuytransactionEntity mapTransactionDTOtoTransaction(BuyTransactionDTO buyTransactionDTO) {
        BuytransactionEntity buytransactionEntity = new BuytransactionEntity();
        if (buyTransactionDTO == null)
            return null;
        buytransactionEntity.setActualPrice(buyTransactionDTO.getActualPrice());
        buytransactionEntity.setAmount(buyTransactionDTO.getAmount());
        buytransactionEntity.setCoin(buyTransactionDTO.getCoin());
        buytransactionEntity.setPaidPrice(buyTransactionDTO.getPaidPrice());
        return buytransactionEntity;
    }

    public static List<BuyTransactionDTO> mapTransactionListtoTransactionDTOList(List<BuytransactionEntity> buytransactionEntityList) {
        List<BuyTransactionDTO> buyTransactionDTOS = new ArrayList<>();
        if (buytransactionEntityList == null)
            return null;
        for (BuytransactionEntity buytransactionEntity : buytransactionEntityList)
            buyTransactionDTOS.add(mapTransactiontoTransactionDTO(buytransactionEntity));
        return buyTransactionDTOS;
    }
}
