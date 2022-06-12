package com.example.springbootinventicodingchallenge.service;

import com.example.springbootinventicodingchallenge.entity.BalanceEntity;
import com.example.springbootinventicodingchallenge.entity.Status;
import com.example.springbootinventicodingchallenge.entity.TransactionEntity;
import com.example.springbootinventicodingchallenge.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BalanceServiceImpl implements BalanceService {


    @Autowired
    private BalanceRepository balanceRepository;

    private static double FEE = 0;

    public BalanceEntity updateBalance(BalanceEntity balance, TransactionEntity transaction) {
        double newBalance = 0;
        if (transaction.getStatus().equals(Status.DEPOSIT)) {
            newBalance = deposit(balance, transaction);

        } else {
            newBalance = withdraw(balance, transaction);
        }
        return saveBalance(balance, newBalance, transaction);
    }

    public BalanceEntity saveBalance(BalanceEntity balanceEntity, double balance, TransactionEntity transaction) {
        balanceEntity.setBalance(balance);
        balanceEntity.setDate(LocalDate.now());
        balanceEntity.getTransaction().add(transaction);
        balanceEntity.setAccount(balanceEntity.getAccount());
        return this.balanceRepository.save(balanceEntity);
    }

    public BalanceEntity findFinalBalanceByDate(LocalDate date) {
        Long count = Long.MIN_VALUE;
        BalanceEntity result = null;
        List<BalanceEntity> balanceList = this.balanceRepository.findAllByDate(date);
        for (BalanceEntity balance : balanceList) {
            if (balance.getId() > count) {
                result = balance;
               count = balance.getId();
            }
        }
        return result;
    }

    public BalanceEntity findInceptiveBalanceByDate(LocalDate date) {
        Long count = Long.MAX_VALUE;
        BalanceEntity result = null;
        List<BalanceEntity> balanceList = this.balanceRepository.findAllByDate(date);
        for (BalanceEntity balance : balanceList) {
            if (balance.getId() < count) {
                result = balance;
                count = balance.getId();
            }
        }
        return result;
    }

    @Override
    public double deposit(BalanceEntity balance, TransactionEntity transaction) {
        double amount = transaction.getAmount();
        double getBalance = balance.getBalance();
        if (amount > 0) {
            getBalance += amount;
            getBalance -= FEE;
            return getBalance;

        } else {
            throw new NullPointerException("Negative amount cannot be deposited");
        }
    }

    @Override
    public double withdraw(BalanceEntity balance, TransactionEntity transaction) {
        double amount = transaction.getAmount();
        double getBalance = balance.getBalance();
        if (amount > 0) {
            if ((amount + FEE) <= getBalance) {
                getBalance -= amount;
                getBalance -= FEE;
            }
            return getBalance;
        } else {
            throw new NullPointerException("Negative amount cannot be withdrawn");
        }
    }
}
