package com.example.springbootinventicodingchallenge.service;

import com.example.springbootinventicodingchallenge.entity.BalanceEntity;
import com.example.springbootinventicodingchallenge.entity.TransactionEntity;

public interface BalanceService {

    double deposit(BalanceEntity balance, TransactionEntity transaction);

    double withdraw(BalanceEntity balance, TransactionEntity transaction);
}
