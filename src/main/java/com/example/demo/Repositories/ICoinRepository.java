package com.example.demo.Repositories;

import com.example.demo.Entities.Coin;

import java.io.IOException;
import java.util.List;

public interface ICoinRepository {
    void populate() throws IOException;

    List<Coin> getAll();
}
