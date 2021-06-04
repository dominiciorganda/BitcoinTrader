package com.example.demo.DTOs;

public class AuthenticationResponse {
    private String authenticationToken;
    private String username;
    private double money;
    private String email;

    public AuthenticationResponse(String authenticationToken, String username, double money, String email) {
        this.authenticationToken = authenticationToken;
        this.username = username;
        this.money = money;
        this.email = email;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
