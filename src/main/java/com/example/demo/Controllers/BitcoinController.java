package com.example.demo.Controllers;

import com.example.demo.Entities.Bitcoin;
import com.example.demo.Repositories.BitcoinRepository;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class BitcoinController {
    private BitcoinRepository bitcoinRepository = new BitcoinRepository();

    public BitcoinController() throws IOException {
    }

    public List<Bitcoin> getAll() {
        return bitcoinRepository.getAll();
    }

    public Bitcoin getAllTimeMax() {
        List<Bitcoin>bitcoins = bitcoinRepository.getAll();
        bitcoins.sort(new Sorter());
        return bitcoins.get(0);
    }

    public Bitcoin getActual() {
       return bitcoinRepository.getAll().get(bitcoinRepository.getAll().size()-1);
    }

    class Sorter implements Comparator<Bitcoin> {
        @Override
        public int compare(Bitcoin o1, Bitcoin o2) {
            return (int) (-o1.getPrice() + o2.getPrice());
        }
    }
}
