package com.example.springbootinventicodingchallenge.repository;

import com.example.springbootinventicodingchallenge.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
