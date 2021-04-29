package com.example.demo.Repositories;


import com.example.demo.Entities.TransactionEntity;
import com.example.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findAllByUser(User user);

}
