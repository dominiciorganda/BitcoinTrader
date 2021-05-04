package com.example.demo.DTOs;

public class FundsDTO {
    private double amount;

    public FundsDTO(double amount) {
        this.amount = amount;
    }

    public FundsDTO() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
