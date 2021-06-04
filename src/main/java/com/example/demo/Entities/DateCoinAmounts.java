package com.example.demo.Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateCoinAmounts {
    private LocalDate date;
    private List<CoinAmount> coinAmountList;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<CoinAmount> getCoinAmountList() {
        return coinAmountList;
    }

    public void setCoinAmountList(List<CoinAmount> coinAmountList) {
        this.coinAmountList = coinAmountList;
    }

    public DateCoinAmounts() {
    }

    public DateCoinAmounts(LocalDate date, List<CoinAmount> coinAmountList) {
        this.date = date;
        this.coinAmountList = new ArrayList<>();
        for(CoinAmount coinAmount:coinAmountList)
            this.coinAmountList.add(new CoinAmount(coinAmount));
//        System.out.println();
//        System.out.println(this.coinAmountList);
    }
}
