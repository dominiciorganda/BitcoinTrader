package com.example.demo.Services;

import com.example.demo.DTOs.TransactionDTO;
import com.example.demo.Entities.*;
import com.example.demo.Mappers.TransactionMapper;
import com.example.demo.Repositories.TransactionRepository;
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
                walletCoins.add(new WalletCoin(transaction.getCoin(), transaction.getAmount(),transaction.getPaidPrice()));
            else {
                for (WalletCoin walletCoin : walletCoins)
                    if (walletCoin.getCoinName().equals(transaction.getCoin())){
                        if(transaction.getType() == TransactionType.BUY){
                            walletCoin.setAmount(walletCoin.getAmount() + transaction.getAmount());
                            walletCoin.setPaid(walletCoin.getPaid() + transaction.getPaidPrice());
                        }
                        if(transaction.getType() == TransactionType.SELL){
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
}
