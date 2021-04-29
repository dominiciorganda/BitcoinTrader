package com.example.demo.Mappers;

import com.example.demo.DTOs.TransactionDTO;
import com.example.demo.Entities.TransactionEntity;

import java.util.ArrayList;
import java.util.List;

public class TransactionMapper {
    public static TransactionDTO mapTransactiontoTransactionDTO(TransactionEntity transactionEntity) {
        TransactionDTO transactionDTO = new TransactionDTO();
        if (transactionEntity == null)
            return null;
        transactionDTO.setActualPrice(transactionEntity.getActualPrice());
        transactionDTO.setAmount(transactionEntity.getAmount());
        transactionDTO.setCoin(transactionEntity.getCoin());
        transactionDTO.setPaidPrice(transactionEntity.getPaidPrice());
        transactionDTO.setType(transactionEntity.getType());
        return transactionDTO;
    }

    public static TransactionEntity mapTransactionDTOtoTransaction(TransactionDTO transactionDTO) {
        TransactionEntity transactionEntity = new TransactionEntity();
        if (transactionDTO == null)
            return null;
        transactionEntity.setActualPrice(transactionDTO.getActualPrice());
        transactionEntity.setAmount(transactionDTO.getAmount());
        transactionEntity.setCoin(transactionDTO.getCoin());
        transactionEntity.setPaidPrice(transactionDTO.getPaidPrice());
        transactionEntity.setType(transactionDTO.getType());
        return transactionEntity;
    }

    public static List<TransactionDTO> mapTransactionListtoTransactionDTOList(List<TransactionEntity> transactionEntityList) {
        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        if (transactionEntityList == null)
            return null;
        for (TransactionEntity transactionEntity : transactionEntityList)
            transactionDTOS.add(mapTransactiontoTransactionDTO(transactionEntity));
        return transactionDTOS;
    }
}
