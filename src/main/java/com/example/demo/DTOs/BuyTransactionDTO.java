package com.example.demo.DTOs;

import com.example.demo.Entities.CoinTypes;

import java.time.Instant;

public class BuyTransactionDTO {
    //    private Instant transactionDate;
    //    private long userId;
    private CoinTypes coin;
    private double amount;
    private double actualPrice;
    private double paidPrice;
//    private String username;

    public BuyTransactionDTO(CoinTypes coin, double amount, double actualPrice, double paidPrice) {
//        this.id = id;
        this.coin = coin;
        this.amount = amount;
        this.actualPrice = actualPrice;
        this.paidPrice = paidPrice;
//        this.username = username;
    }

    public BuyTransactionDTO() {
    }

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    public CoinTypes getCoin() {
        return coin;
    }

    public void setCoin(CoinTypes coin) {
        this.coin = coin;
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

    public double getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(double paidPrice) {
        this.paidPrice = paidPrice;
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
}
