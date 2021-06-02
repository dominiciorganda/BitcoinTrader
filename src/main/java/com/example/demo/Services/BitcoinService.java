package com.example.demo.Services;

import com.example.demo.Entities.Coin;
import com.example.demo.Repositories.BitcoinRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BitcoinService implements ICoinService {

    @Autowired
    private BitcoinRepository bitcoinRepository;

    public BitcoinService() throws IOException {
    }

    public List<Coin> getAll() {
        return bitcoinRepository.getAll();
    }


    public Coin getAllTimeMax() {
        List<Coin> coins = bitcoinRepository.getAll();
        coins.sort(new Sorter());
        return coins.get(0);
    }

    public Coin getLast() {
        return bitcoinRepository.getAll().get(bitcoinRepository.getAll().size() - 1);
    }

    public Coin getActual() throws IOException {
        String webPage = "https://api.coindesk.com/v1/bpi/currentprice.json";
        String json = new Scanner(new URL(webPage).openStream(), "UTF-8").useDelimiter("\\A").next();
        Pattern pattern = Pattern.compile("(\"rate.*Dollar\")");
        Matcher matcher = pattern.matcher(json);
        json = json.replaceAll("(\"rate.*Dollar\")", "");

        if (matcher.find())
            json = matcher.group(1);

        json = json.replaceAll(",\".*", "");
        json = json.replaceAll("\"|\\,", "");
        json = json.replaceAll("rate", "\"price\"");
        json = "{\"date\":\"" + getLast().getDate() + "\"," + json + "}";
        Gson gson = new Gson();
        Coin coin = gson.fromJson(json, Coin.class);
        return coin;
    }

    public Coin getAnualMax() {
        String year = getLast().getDate().replaceAll("-.*", "");
        List<Coin> coins = getAll();
        coins.removeIf(coin -> !coin.getDate().replaceAll("-.*", "").equals(year));
        coins.sort(new Sorter());
        return coins.get(0);
    }

    public Coin getAnualMin() {
        String year = getLast().getDate().replaceAll("-.*", "");
        List<Coin> coins = getAll();
        coins.removeIf(coin -> !coin.getDate().replaceAll("-.*", "").equals(year));
        coins.sort(new Sorter());
        return coins.get(coins.size() - 1);
    }

    public List<Coin> getLastMonth() {
        List<Coin> coins = bitcoinRepository.getAll();
        return coins.subList(coins.size() - 32, coins.size());
    }

    public List<Coin> getLastX(int number) {
        List<Coin> coins = bitcoinRepository.getAll();
        if (coins.size() > number)
            return coins.subList(coins.size() - number, coins.size());
        return null;
    }


    class Sorter implements Comparator<Coin> {
        @Override
        public int compare(Coin o1, Coin o2) {
            return Double.compare(o2.getPrice(), o1.getPrice());
        }
    }

}

