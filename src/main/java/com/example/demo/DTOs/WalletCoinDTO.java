package com.example.demo.DTOs;

import com.example.demo.Entities.CoinTypes;

public class WalletCoinDTO {
    private CoinTypes coinName;
    private double amount;
    private double actualPrice;
    private double value;
    private double paid;

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public WalletCoinDTO(CoinTypes coinName, double amount, double actualPrice, double value) {
        this.coinName = coinName;
        this.amount = amount;
        this.actualPrice = actualPrice;
        this.value = value;
    }

    public WalletCoinDTO() {
    }

    public CoinTypes getCoinName() {
        return coinName;
    }

    public void setCoinName(CoinTypes coinName) {
        this.coinName = coinName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
