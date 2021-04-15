package com.example.demo.Entities;

public class WalletCoin {
    private CoinTypes coinName;
    private double amount;
    private double actualPrice;
    private double value;
    private double paid;

    public WalletCoin(CoinTypes coinName, double amount, double paid) {
        this.coinName = coinName;
        this.amount = amount;
        this.paid = paid;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getActualPrice() {
        return actualPrice;
    }


    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }



    public WalletCoin(CoinTypes coinName, double amount) {
        this.coinName = coinName;
        this.amount = amount;
    }

    public WalletCoin() {
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
}
