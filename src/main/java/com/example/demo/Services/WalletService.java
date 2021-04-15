package com.example.demo.Services;

import com.example.demo.DTOs.BuyTransactionDTO;
import com.example.demo.Entities.BuytransactionEntity;
import com.example.demo.Entities.CoinTypes;
import com.example.demo.Entities.WalletCoin;
import com.example.demo.Entities.User;
import com.example.demo.Mappers.BuyTransactionMapper;
import com.example.demo.Repositories.BuyTransactionRepository;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.Instant.now;


@Service
public class WalletService {

    @Autowired
    private BuyTransactionRepository buyTransactionRepository;

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

    public BuytransactionEntity addTransaction(BuyTransactionDTO buyTransactionDTO) {
        BuytransactionEntity buytransactionEntity = BuyTransactionMapper.mapTransactionDTOtoTransaction(buyTransactionDTO);

        Optional<User> optionalUser;
        optionalUser = userRepository.findByUsername(authService.getCurrentUser().getUsername());
        User user1;
        user1 = optionalUser.orElse(null);

        buytransactionEntity.setUser(user1);
        buytransactionEntity.setTransactionDate(now());
        return buyTransactionRepository.save(buytransactionEntity);
    }

    public List<BuytransactionEntity> getUserTransactions() {
        String username = authService.getCurrentUser().getUsername();
        Optional<User> optionalUser;
        optionalUser = userRepository.findByUsername(username);
        User user1;
        user1 = optionalUser.orElse(null);
        return buyTransactionRepository.findAllByUser(user1);
    }

    public List<WalletCoin> getWalletCoins() throws IOException {
        List<WalletCoin> walletCoins = new ArrayList<>();
        List<BuytransactionEntity> transactions = getUserTransactions();

        for (BuytransactionEntity transaction : transactions) {
            if (!isPresent(walletCoins, transaction))
                walletCoins.add(new WalletCoin(transaction.getCoin(), transaction.getAmount(),transaction.getPaidPrice()));
            else {
                for (WalletCoin walletCoin : walletCoins)
                    if (walletCoin.getCoinName().equals(transaction.getCoin())){
                        walletCoin.setAmount(walletCoin.getAmount() + transaction.getAmount());
                        walletCoin.setPaid(walletCoin.getPaid()+transaction.getPaidPrice());
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

    private boolean isPresent(List<WalletCoin> walletCoins, BuytransactionEntity transaction) {
        for (WalletCoin walletCoin : walletCoins)
            if (walletCoin.getCoinName().equals(transaction.getCoin()))
                return true;
        return false;
    }
}
