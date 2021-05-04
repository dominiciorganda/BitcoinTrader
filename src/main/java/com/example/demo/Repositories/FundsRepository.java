package com.example.demo.Repositories;

import com.example.demo.Entities.FundsEntity;
import com.example.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundsRepository extends JpaRepository<FundsEntity, Long> {
    List<FundsEntity> findAllByUser(User user);
}
