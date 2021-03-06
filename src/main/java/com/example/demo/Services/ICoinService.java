package com.example.demo.Services;

import com.example.demo.Entities.Coin;

import java.io.IOException;
import java.util.List;

public interface ICoinService {
    public List<Coin> getAll();
    public Coin getAllTimeMax();
    public Coin getActual() throws IOException;
    public List<Coin> getLastMonth();
    public List<Coin> getLastX(int number);
}
