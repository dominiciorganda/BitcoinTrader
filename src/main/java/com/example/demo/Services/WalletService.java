package com.example.demo.Services;

import com.example.demo.DTOs.TransactionDTO;
import com.example.demo.Entities.*;
import com.example.demo.Mappers.TransactionMapper;
import com.example.demo.Repositories.TransactionRepository;
import com.example.demo.Repositories.UserRepository;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


import static java.time.Instant.now;


@Service
public class WalletService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BitcoinService bitcoinService;
    @Autowired
    private BinanceCoinService binanceCoinService;
    @Autowired
    private BitcoinCashService bitcoinCashService;
    @Autowired
    private DashService dashService;
    @Autowired
    private DogecoinService dogecoinService;
    @Autowired
    private ElrondService elrondService;
    @Autowired
    private EthereumService ethereumService;
    @Autowired
    private FileCoinService fileCoinService;
    @Autowired
    private LitecoinService litecoinService;
    @Autowired
    private AuthService authService;

    public TransactionEntity addTransaction(TransactionDTO transactionDTO) {
        TransactionEntity transactionEntity = TransactionMapper.mapTransactionDTOtoTransaction(transactionDTO);

        Optional<User> optionalUser;
        optionalUser = userRepository.findByUsername(authService.getCurrentUser().getUsername());
        User user1;
        user1 = optionalUser.orElse(null);
        if (transactionEntity.getType() == TransactionType.BUY) {
            double newMoney = Precision.round(user1.getMoney() - transactionEntity.getPaidPrice(), 2);
            user1.setMoney(newMoney);
            userRepository.save(user1);
        }
        if (transactionEntity.getType() == TransactionType.SELL) {
            double newMoney = Precision.round(user1.getMoney() + transactionEntity.getPaidPrice(), 2);
            user1.setMoney(newMoney);
            userRepository.save(user1);
        }

        transactionEntity.setUser(user1);
        transactionEntity.setTransactionDate(now());
        return transactionRepository.save(transactionEntity);
    }

    public List<TransactionEntity> getUserTransactions() {
        String username = authService.getCurrentUser().getUsername();
        Optional<User> optionalUser;
        optionalUser = userRepository.findByUsername(username);
        User user1;
        user1 = optionalUser.orElse(null);
        return transactionRepository.findAllByUser(user1);
    }

    public List<WalletCoin> getWalletCoins() throws IOException {
        List<WalletCoin> walletCoins = new ArrayList<>();
        List<TransactionEntity> transactions = getUserTransactions();

        for (TransactionEntity transaction : transactions) {
            if (!isPresent(walletCoins, transaction))
                walletCoins.add(new WalletCoin(transaction.getCoin(), transaction.getAmount(), transaction.getPaidPrice()));
            else {
                for (WalletCoin walletCoin : walletCoins)
                    if (walletCoin.getCoinName().equals(transaction.getCoin())) {
                        if (transaction.getType() == TransactionType.BUY) {
                            walletCoin.setAmount(walletCoin.getAmount() + transaction.getAmount());
                            walletCoin.setPaid(walletCoin.getPaid() + transaction.getPaidPrice());
                        }
                        if (transaction.getType() == TransactionType.SELL) {
                            walletCoin.setAmount(walletCoin.getAmount() - transaction.getAmount());
                            walletCoin.setPaid(walletCoin.getPaid() - transaction.getPaidPrice());
                        }
                    }
            }
        }

        for (WalletCoin walletCoin : walletCoins) {
            ICoinService service = getService(walletCoin.getCoinName());
            walletCoin.setActualPrice(service.getActual().getPrice());
            walletCoin.setValue(walletCoin.getAmount() * walletCoin.getActualPrice());
        }

        return walletCoins;
    }

    private ICoinService getService(CoinTypes coinName) {
        switch (coinName) {
            case BITCOIN:
                return bitcoinService;
            case BINANCECOIN:
                return binanceCoinService;
            case BITCOINCASH:
                return bitcoinCashService;
            case DASH:
                return dashService;
            case DOGECOIN:
                return dogecoinService;
            case ELROND:
                return elrondService;
            case ETHEREUM:
                return ethereumService;
            case FILECOIN:
                return fileCoinService;
            case LITECOIN:
                return litecoinService;
            default:
                return null;
        }
    }

    private boolean isPresent(List<WalletCoin> walletCoins, TransactionEntity transaction) {
        for (WalletCoin walletCoin : walletCoins)
            if (walletCoin.getCoinName().equals(transaction.getCoin()))
                return true;
        return false;
    }

    public List<Coin> getWalletCoinsPortfolio() throws ParseException {
        List<Coin> values = new ArrayList<>();

        Optional<User> optionalUser;
        optionalUser = userRepository.findByUsername(authService.getCurrentUser().getUsername());
        User user1;
        user1 = optionalUser.orElse(null);
        List<CoinAmount> amounts = new ArrayList<>();
        for (CoinTypes coinTypes : CoinTypes.values())
            amounts.add(new CoinAmount(coinTypes, 0));

        List<TransactionEntity> userTransactions = transactionRepository.findAllByUser(user1);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format = format.withLocale(Locale.ENGLISH);
        LocalDate actualDate = LocalDate.parse(userTransactions.get(0).getTransactionDate().toString(), format);
        List<DateCoinAmounts> dateCoinAmounts = new ArrayList<>();

//        System.out.println(actualDate);

        for (TransactionEntity transactionEntity : userTransactions) {
            LocalDate date = LocalDate.parse(transactionEntity.getTransactionDate().toString(), format);
            if (actualDate.compareTo(date) == 0) {
                CoinAmount actual = getCoinAmount(amounts, transactionEntity.getCoin());
                if (transactionEntity.getType() == TransactionType.BUY)
                    actual.setAmount(actual.getAmount() + transactionEntity.getAmount());
                else
                    actual.setAmount(actual.getAmount() - transactionEntity.getAmount());
            } else if (actualDate.compareTo(date) < 0) {
                dateCoinAmounts.add(new DateCoinAmounts(actualDate, amounts));
//                System.out.println(actualDate);
                actualDate = date;
                CoinAmount actual = getCoinAmount(amounts, transactionEntity.getCoin());
                if (transactionEntity.getType() == TransactionType.BUY)
                    actual.setAmount(actual.getAmount() + transactionEntity.getAmount());
                else
                    actual.setAmount(actual.getAmount() - transactionEntity.getAmount());
            }
        }
//        System.out.println(actualDate);
        dateCoinAmounts.add(new DateCoinAmounts(actualDate, amounts));
        LocalDate actual = LocalDate.now();
        for (int i = 0; i < dateCoinAmounts.size(); i++) {
            int length;
            if (i != dateCoinAmounts.size() - 1)
                length = (int) ChronoUnit.DAYS.between(dateCoinAmounts.get(i).getDate(), dateCoinAmounts.get(i + 1).getDate());
            else
                length = (int) ChronoUnit.DAYS.between(dateCoinAmounts.get(i).getDate(), actual);
            List<Coin> prices = calculatePrice(dateCoinAmounts.get(i), length);
            values.addAll(prices);
        }

        return values;
    }

    private List<Coin> calculatePrice(DateCoinAmounts dateCoinAmounts, int length) {
        LocalDate actual = LocalDate.now();
        long elapsedDays = ChronoUnit.DAYS.between(dateCoinAmounts.getDate(), actual);
        List<CoinAmount> amounts = dateCoinAmounts.getCoinAmountList();
        List<Coin> prices = new ArrayList<>();
        double price = 0;
        LocalDate date = dateCoinAmounts.getDate();
        int i = 0;

        while (i < length) {
            price = 0;
            price += amounts.get(0).getAmount() * bitcoinService.getLastX((int) elapsedDays).get(i).getPrice();
            price += amounts.get(1).getAmount() * binanceCoinService.getLastX((int) elapsedDays).get(i).getPrice();
            price += amounts.get(2).getAmount() * bitcoinCashService.getLastX((int) elapsedDays).get(i).getPrice();
            price += amounts.get(3).getAmount() * dashService.getLastX((int) elapsedDays).get(i).getPrice();
            price += amounts.get(4).getAmount() * dogecoinService.getLastX((int) elapsedDays).get(i).getPrice();
            price += amounts.get(5).getAmount() * elrondService.getLastX((int) elapsedDays).get(i).getPrice();
            price += amounts.get(6).getAmount() * ethereumService.getLastX((int) elapsedDays).get(i).getPrice();
            price += amounts.get(7).getAmount() * fileCoinService.getLastX((int) elapsedDays).get(i).getPrice();
            price += amounts.get(8).getAmount() * litecoinService.getLastX((int) elapsedDays).get(i).getPrice();
            prices.add(new Coin(date.toString(), price));
            date = date.plusDays(1);
            i++;
        }
        return prices;
    }

    private CoinAmount getCoinAmount(List<CoinAmount> coinAmounts, CoinTypes coinTypes) {
        for (CoinAmount coinAmount : coinAmounts)
            if (coinAmount.getName() == coinTypes)
                return coinAmount;
        return null;
    }

}
