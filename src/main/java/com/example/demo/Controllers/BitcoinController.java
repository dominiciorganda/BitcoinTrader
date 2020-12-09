package com.example.demo.Controllers;

import com.example.demo.Entities.Bitcoin;
import com.example.demo.Repositories.BitcoinRepository;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitcoinController {
    private BitcoinRepository bitcoinRepository = new BitcoinRepository();

    public BitcoinController() throws IOException {
    }

    public List<Bitcoin> getAll() {
        return bitcoinRepository.getAll();
    }

    public Bitcoin getAllTimeMax() {
        List<Bitcoin> bitcoins = bitcoinRepository.getAll();
        bitcoins.sort(new Sorter());
        return bitcoins.get(0);
    }

    public Bitcoin getLast() {
        return bitcoinRepository.getAll().get(bitcoinRepository.getAll().size() - 1);
    }

    public Bitcoin getActual() throws IOException {
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
        Bitcoin bitcoin = gson.fromJson(json, Bitcoin.class);
        return bitcoin;
    }

    public Bitcoin getAnualMax(){
        String year = getLast().getDate().replaceAll("-.*","");
        List<Bitcoin> bitcoins = getAll();
        bitcoins.removeIf(bitcoin -> !bitcoin.getDate().replaceAll("-.*", "").equals(year));
        bitcoins.sort(new Sorter());
        return bitcoins.get(0);
    }

    public Bitcoin getAnualMin(){
        String year = getLast().getDate().replaceAll("-.*","");
        List<Bitcoin> bitcoins = getAll();
        bitcoins.removeIf(bitcoin -> !bitcoin.getDate().replaceAll("-.*", "").equals(year));
        bitcoins.sort(new Sorter());
        return bitcoins.get(bitcoins.size()-1);
    }


    class Sorter implements Comparator<Bitcoin> {
        @Override
        public int compare(Bitcoin o1, Bitcoin o2) {
            return (int) (-o1.getPrice() + o2.getPrice());
        }
    }
}
