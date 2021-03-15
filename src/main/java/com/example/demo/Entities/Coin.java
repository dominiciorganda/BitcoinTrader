package com.example.demo.Entities;


public class Coin {
    private String date;
    private double price;

    public Coin(String date, double price) {
        this.date = date;
        this.price = price;
    }

    public Coin() {
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
        return "Coin{" +
                "date=" + date +
                ", price=" + price +
                '}';
    }
}
