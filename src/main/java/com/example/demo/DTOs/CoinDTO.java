package com.example.demo.DTOs;

public class CoinDTO {
    private String date;
    private double price;

    public CoinDTO(String date, double price) {
        this.date = date;
        this.price = price;
    }

    public CoinDTO() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CoinDTO{" +
                "date=" + date +
                ", price=" + price +
                '}';
    }
}
