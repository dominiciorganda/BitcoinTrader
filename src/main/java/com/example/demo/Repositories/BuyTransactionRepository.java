package com.example.demo.Repositories;


import com.example.demo.Entities.BuytransactionEntity;
import com.example.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuyTransactionRepository extends JpaRepository<BuytransactionEntity, Long> {
    List<BuytransactionEntity> findAllByUser(User user);

}
