package com.example.springbootinventicodingchallenge.repository;

import com.example.springbootinventicodingchallenge.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
