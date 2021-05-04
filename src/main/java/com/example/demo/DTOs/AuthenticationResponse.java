package com.example.demo.DTOs;

public class AuthenticationResponse {
    private String authenticationToken;
    private String username;
    private double money;

    public AuthenticationResponse(String authenticationToken, String username, double money) {
        this.authenticationToken = authenticationToken;
        this.username = username;
        this.money = money;
    }

    public AuthenticationResponse(String authenticationToken, String username) {
        this.authenticationToken = authenticationToken;
        this.username = username;
    }

    public AuthenticationResponse() {
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
