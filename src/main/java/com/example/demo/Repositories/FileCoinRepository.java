package com.example.demo.Repositories;


import com.example.demo.Entities.Coin;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class FileCoinRepository implements ICoinRepository {
    private List<Coin> coinList = new ArrayList<>();

    public FileCoinRepository() throws IOException {
        populate();
    }

    @Override
    public void populate() throws IOException {
        String webPage = "https://min-api.cryptocompare.com/data/v2/histoday?fsym=FIL&tsym=USD&limit=300";
        String apiKey = "32c1622d7adaead2982febecec83ac7de1ae67d1bc622ffa0f55b88d294748f1";
        String sufix = "&api_key={" + apiKey + "}";
        String json = new Scanner(new URL(webPage + sufix).openStream(), "UTF-8").useDelimiter("\\A").next();

        json = styleJson(json);


        json = json.replaceAll("time", "date");
        json = json.replaceAll("open", "price");
        Gson gson = new Gson();
        Coin[] coins = gson.fromJson(json, Coin[].class);
        coinList.addAll(Arrays.asList(coins));
        for (Coin coin : coinList) {
            long timestamp = Long.parseLong(coin.getDate());
            Date d = new Date(timestamp * 1000);
            String year = d.toString().substring(24).replaceAll("\\s", "");
            String day = d.toString().substring(8, 10);
            String month = d.toString().substring(4, 7);

            month = getMonth(month);

            coin.setDate("" + year + "-" + month + "-" + day);
//            System.out.println(coin);
        }

    }

    @Override
    public List<Coin> getAll() {
        List<Coin> coins = new ArrayList<>();
        coins.addAll(coinList);
        return coins;
    }

    public String styleJson(String json) {
        Pattern pattern = Pattern.compile("\\[.*\\]");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find())
            json = matcher.group();
        json = json.replaceAll("\"high\"", "");
        json = json.replaceAll("\\,\\:[0-9]*\\.[0-9]*", "");

        json = json.replaceAll("\"low\"", "");
        json = json.replaceAll("\\,\\:[0-9]*\\.[0-9]*", "");

        json = json.replaceAll("\"volumefrom\"", "");
        json = json.replaceAll("\\,\\:[0-9]*\\.[0-9]*", "");

        json = json.replaceAll("\"volumeto\"", "");
        json = json.replaceAll("\\,\\:[0-9]*\\.[0-9]*", "");

        json = json.replaceAll("\"close\"", "");
        json = json.replaceAll("\\,\\:[0-9]*\\.[0-9]*", "");

        json = json.replaceAll("\"conversionType\"", "");
        json = json.replaceAll("\"multiply\"", "");

        json = json.replaceAll("\"conversionSymbol\"", "");
        json = json.replaceAll("\"BTC\"", "");
        json = json.replaceAll("\"ETH\"", "");

        json = json.replaceAll("\\,\\:\\,\\:", "");
        json = json.replaceAll("\"direct\"", "");
        json = json.replaceAll("\\:\\,\\:\"\"", "");
        json = json.replaceAll("\\,\\}", "}");
        json = json.replaceAll("\\,\\:[0-9]*", "");
        return json;
    }

    public String getMonth(String month) {
        switch (month) {
            case "Jan":
                month = "01";
                break;
            case "Feb":
                month = "02";
                break;
            case "Mar":
                month = "03";
                break;
            case "Apr":
                month = "04";
                break;
            case "May":
                month = "05";
                break;
            case "Jun":
                month = "06";
                break;
            case "Jul":
                month = "07";
                break;
            case "Aug":
                month = "08";
                break;
            case "Sep":
                month = "09";
                break;
            case "Oct":
                month = "10";
                break;
            case "Nov":
                month = "11";
                break;
            case "Dec":
                month = "12";
                break;
        }
        return month;
    }
}
