package com.example.demo.Repositories;

import com.example.demo.Entities.Coin;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class BitcoinRepository implements ICoinRepository{
    List<Coin> coinList = new ArrayList<>();


    public BitcoinRepository() throws IOException {
        populate();
    }
    
    public void populate() throws IOException {
        String webPage = "https://www.satochi.co/allBTCPrice";
        String json = new Scanner(new URL(webPage).openStream(), "UTF-8").useDelimiter("\\A").next();
        json = json.replaceAll("Date", "date");
        json = json.replaceAll("Price", "price");
        Gson gson = new Gson();
        Coin[] coins = gson.fromJson(json, Coin[].class);
        coinList.addAll(Arrays.asList(coins));
    }

    public List<Coin> getAll() {
        List<Coin> coins = new ArrayList<>();
        coins.addAll(coinList);
        return coins;
    }
}
