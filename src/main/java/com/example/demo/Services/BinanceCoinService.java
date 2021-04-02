package com.example.demo.Services;

import com.example.demo.Entities.Coin;
import com.example.demo.Repositories.BinanceCoinRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BinanceCoinService implements ICoinService {

    @Autowired
    private BinanceCoinRepository binanceCoinRepository;

    public BinanceCoinService() throws IOException {
    }

    @Override
    public List<Coin> getAll() {
        return binanceCoinRepository.getAll();
    }

    @Override
    public Coin getAllTimeMax() {
        List<Coin> coins = binanceCoinRepository.getAll();
        coins.sort(new Sorter());
        return coins.get(0);
    }

    @Override
    public Coin getActual() throws IOException {
        String webPage = "https://min-api.cryptocompare.com/data/v2/histominute?fsym=BNB&tsym=USD&limit=1";
        String apiKey = "32c1622d7adaead2982febecec83ac7de1ae67d1bc622ffa0f55b88d294748f1";
        String sufix = "&api_key={" + apiKey + "}";
        String json = new Scanner(new URL(webPage + sufix).openStream(), "UTF-8").useDelimiter("\\A").next();
        Pattern pattern = Pattern.compile("\\[.*\\]");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find())
            json = matcher.group();
        json = binanceCoinRepository.styleJson(json);
        json = json.replaceAll("time", "date");
        json = json.replaceAll("open", "price");
        Gson gson = new Gson();
        Coin[] coins = gson.fromJson(json, Coin[].class);
        Coin coin = coins[1];
        long timestamp = Long.parseLong(coin.getDate());
        Date d = new Date(timestamp * 1000);
        String year = d.toString().substring(24).replaceAll("\\s", "");
        String day = d.toString().substring(8, 10);
        String month = d.toString().substring(4, 7);
        month = binanceCoinRepository.getMonth(month);
        coin.setDate("" + year + "-" + month + "-" + day);
        return coin;
    }

    @Override
    public Coin getAnualMax() {
        String year = getLast().getDate().replaceAll("-.*", "");
        List<Coin> coins = getAll();
        coins.removeIf(coin -> !coin.getDate().replaceAll("-.*", "").equals(year));
        coins.sort(new Sorter());
        return coins.get(0);
    }

    @Override
    public Coin getAnualMin() {
        String year = getLast().getDate().replaceAll("-.*", "");
        List<Coin> coins = getAll();
        coins.removeIf(coin -> !coin.getDate().replaceAll("-.*", "").equals(year));
        coins.sort(new Sorter());
        return coins.get(coins.size() - 1);
    }

    @Override
    public List<Coin> getLastMonth() {
        List<Coin> coins = binanceCoinRepository.getAll();
        return coins.subList(coins.size() - 32, coins.size());
    }

    @Override
    public List<Coin> getLastX(int number) {
        List<Coin> coins = binanceCoinRepository.getAll();
        if (coins.size() > number)
            return coins.subList(coins.size() - number, coins.size());
        return null;
    }

    public Coin getLast() {
        return binanceCoinRepository.getAll().get(binanceCoinRepository.getAll().size() - 1);
    }

    class Sorter implements Comparator<Coin> {
        @Override
        public int compare(Coin o1, Coin o2) {
            return (int) (-o1.getPrice() + o2.getPrice());
        }
    }

}
