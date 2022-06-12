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

    public void setInceptiveBalance(BalanceEntity inceptiveBalance) {
        inceptiveBalance.setBeneficiary(" ");
        inceptiveBalance.setComment("The balance for the beginning");
        inceptiveBalance.setStatus(Status.INCEPTIVE_BALANCE);
    }

    public BalanceEntity findInceptiveBalance() {
        List<BalanceEntity> balanceList = this.balanceRepository.findAll();
        BalanceEntity inceptiveBalance = balanceList.get(0);
        setInceptiveBalance(inceptiveBalance);
        return inceptiveBalance;
    }

    public void setFinalBalance(BalanceEntity finalBalance) {
        finalBalance.setBeneficiary(" ");
        finalBalance.setComment("Balance at end");
        finalBalance.setStatus(Status.FINAL_BALANCE);
    }


    public BalanceEntity findLastBalance() {
        List<BalanceEntity> balanceList = this.balanceRepository.findAll();
        BalanceEntity finalBalance = balanceList.get(balanceList.size() - 1);
        setFinalBalance(finalBalance);
        return finalBalance;
    }

    public BalanceEntity findLastBalanceInTheList(List<TransactionEntity> transactionList) {
        LocalDate from = transactionList.get(0).getOperationDate();
        LocalDate to = transactionList.get(transactionList.size() - 1).getOperationDate();
        List<BalanceEntity> balanceList = this.balanceRepository.findAllByOperationDateBetween(from, to);
        BalanceEntity inceptiveBalance = balanceList.get(balanceList.size() - 1);
        setInceptiveBalance(inceptiveBalance);
        return inceptiveBalance;
    }


    public BalanceEntity findInceptiveBalanceInTheList(List<TransactionEntity> transactionList) {
        LocalDate from = transactionList.get(0).getOperationDate();
        LocalDate to = transactionList.get(transactionList.size() - 1).getOperationDate();
        List<BalanceEntity> balanceList = this.balanceRepository.findAllByOperationDateBetween(from, to);
        BalanceEntity finalBalance = balanceList.get(0);
        setFinalBalance(finalBalance);
        return finalBalance;
    }

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
        balanceEntity.setAmount(balance);
        balanceEntity.setOperationDate(LocalDate.now());
        balanceEntity.getTransaction().add(transaction);
        balanceEntity.setAccountNumber(balanceEntity.getAccountNumber());
        return this.balanceRepository.save(balanceEntity);
    }

    public BalanceEntity findFinalBalanceByDate(LocalDate date) {
        Long count = Long.MIN_VALUE;
        BalanceEntity result = null;
        List<BalanceEntity> balanceList = this.balanceRepository.findAllByOperationDate(date);
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
        List<BalanceEntity> balanceList = this.balanceRepository.findAllByOperationDate(date);
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
        double getBalance = balance.getAmount();
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
        double getBalance = balance.getAmount();
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
