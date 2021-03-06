package com.example.demo.Repositories;

import com.example.demo.Entities.Coin;

import java.io.IOException;
import java.util.List;

public interface ICoinRepository {
    public void populate() throws IOException;
    public List<Coin> getAll();
}
