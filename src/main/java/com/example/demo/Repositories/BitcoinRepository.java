package com.example.demo.Repositories;

import com.example.demo.Entities.Bitcoin;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class BitcoinRepository {
    List<Bitcoin> bitcoinList = new ArrayList<>();


    public BitcoinRepository() throws IOException {
        populate();
    }

    public void populate() throws IOException {
        String webPage = "https://www.satochi.co/allBTCPrice";
        String json = new Scanner(new URL(webPage).openStream(), "UTF-8").useDelimiter("\\A").next();
        json = json.replaceAll("Date", "date");
        json = json.replaceAll("Price", "price");
        Gson gson = new Gson();
        Bitcoin[] bitcoins = gson.fromJson(json, Bitcoin[].class);
        bitcoinList.addAll(Arrays.asList(bitcoins));
    }

    public List<Bitcoin> getAll() {
        List<Bitcoin>bitcoins = new ArrayList<>();
        bitcoins.addAll(bitcoinList);
        return bitcoins;
    }
}
