package com.example.demo.Services;

import com.example.demo.DTOs.FundsDTO;
import com.example.demo.DTOs.TransactionDTO;
import com.example.demo.Entities.FundsEntity;
import com.example.demo.Entities.FundsType;
import com.example.demo.Entities.TransactionEntity;
import com.example.demo.Entities.User;
import com.example.demo.Mappers.FundsMapper;
import com.example.demo.Mappers.TransactionMapper;
import com.example.demo.Repositories.FundsRepository;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.time.Instant.now;

@Service
public class FundsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private FundsRepository fundsRepository;


    public FundsEntity addFunds(FundsDTO fundsDTO) {
        FundsEntity fundsEntity = FundsMapper.mapFundsDTOtoFunds(fundsDTO);

        Optional<User> optionalUser;
        optionalUser = userRepository.findByUsername(authService.getCurrentUser().getUsername());
        User user1;
        user1 = optionalUser.orElse(null);
        if (user1 != null && fundsEntity != null) {
            user1.setMoney(user1.getMoney() + fundsEntity.getAmount());
            userRepository.save(user1);
        }

        fundsEntity.setUser(user1);
        fundsEntity.setTransactionDate(now());
        fundsEntity.setType(FundsType.CASHIN);
        return fundsRepository.save(fundsEntity);

    }

    public double getMoney() {
        Optional<User> optionalUser;
        optionalUser = userRepository.findByUsername(authService.getCurrentUser().getUsername());
        return optionalUser.get().getMoney();
    }
}
