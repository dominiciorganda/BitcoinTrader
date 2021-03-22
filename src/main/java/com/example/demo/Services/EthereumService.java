package com.example.demo.Services;

import com.example.demo.Entities.Coin;
import com.example.demo.Repositories.EthereumRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EthereumService implements ICoinService {

    @Autowired
    private EthereumRepository etherumRepository;

    public EthereumService() throws IOException {
    }

    public List<Coin> getAll() {
        return etherumRepository.getAll();
    }

    public Coin getAllTimeMax() {
        List<Coin> coins = etherumRepository.getAll();
        coins.sort(new Sorter());
        return coins.get(0);
    }

    public Coin getActual() throws IOException {
        String webPage = "https://min-api.cryptocompare.com/data/v2/histominute?fsym=ETH&tsym=USD&limit=1";
        String apiKey = "32c1622d7adaead2982febecec83ac7de1ae67d1bc622ffa0f55b88d294748f1";
        String sufix = "&api_key={" + apiKey + "}";
        String json = new Scanner(new URL(webPage + sufix).openStream(), "UTF-8").useDelimiter("\\A").next();
        Pattern pattern = Pattern.compile("\\[.*\\]");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find())
            json = matcher.group();
        json = etherumRepository.styleJson(json);
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
        month = etherumRepository.getMonth(month);
        coin.setDate("" + year + "-" + month + "-" + day);
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

    public Coin getLast() {
        return etherumRepository.getAll().get(etherumRepository.getAll().size() - 1);
    }


    public List<Coin> getLastMonth() {
        List<Coin> coins = etherumRepository.getAll();
        return coins.subList(coins.size() - 32, coins.size());
    }

    public List<Coin> getLastX(int number) {
        List<Coin> coins = etherumRepository.getAll();
        if (coins.size() > number)
            return coins.subList(coins.size() - number, coins.size());
        return null;
    }

    class Sorter implements Comparator<Coin> {
        @Override
        public int compare(Coin o1, Coin o2) {
            return (int) (-o1.getPrice() + o2.getPrice());
        }
    }
}
