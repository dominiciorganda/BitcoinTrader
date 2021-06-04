package com.example.demo.Entities;

public class CoinAmount {
    private CoinTypes name;
    private double amount;

    public CoinAmount(CoinAmount coinAmount) {
        this.name = coinAmount.getName();
        this.amount = coinAmount.getAmount();
    }

    @Override
    public String toString() {
        return "CoinAmount{" +
                "name=" + name +
                ", amount=" + amount +
                '}';
    }

    public CoinTypes getName() {
        return name;
    }

    public void setName(CoinTypes name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public CoinAmount() {
    }

    public CoinAmount(CoinTypes name, double amount) {
        this.name = name;
        this.amount = amount;
    }
}
