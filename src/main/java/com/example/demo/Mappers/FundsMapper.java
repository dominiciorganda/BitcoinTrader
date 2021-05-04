package com.example.demo.Mappers;


import com.example.demo.DTOs.FundsDTO;
import com.example.demo.Entities.FundsEntity;

public class FundsMapper {
    public static FundsEntity mapFundsDTOtoFunds(FundsDTO fundsDTO) {
        FundsEntity fundsEntity = new FundsEntity();
        if (fundsDTO == null)
            return null;
        fundsEntity.setAmount(fundsDTO.getAmount());
        return fundsEntity;
    }
}
