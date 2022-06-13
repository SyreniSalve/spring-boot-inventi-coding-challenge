package com.example.springbootinventicodingchallenge.service;

import com.example.springbootinventicodingchallenge.entity.BalanceEntity;
import com.example.springbootinventicodingchallenge.entity.Status;
import com.example.springbootinventicodingchallenge.entity.TransactionEntity;
import com.example.springbootinventicodingchallenge.repository.BalanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@Slf4j
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

    public BalanceEntity newBalance(BalanceEntity balance, TransactionEntity transaction) {
        double newAmount = 0;
        if (transaction.getStatus().equals(Status.DEPOSIT)) {
            newAmount = deposit(balance, transaction);

        } else {
            newAmount = withdraw(balance, transaction);
        }
        BalanceEntity newBalance = new BalanceEntity();
        newBalance.setAccountNumber(transaction.getAccountNumber());
        newBalance.setOperationDate(transaction.getOperationDate());
        newBalance.setBeneficiary(transaction.getBeneficiary());
        newBalance.setComment(transaction.getComment());
        newBalance.setAmount(newAmount);
        newBalance.setCurrency(transaction.getCurrency());
        newBalance.setStatus(null);
        return newBalance;
    }

    public void saveBalanceWithTransaction(BalanceEntity balanceEntity, TransactionEntity transaction) {
        balanceEntity.getTransaction().add(transaction);
        this.balanceRepository.save(balanceEntity);
    }

    public void saveBalance(BalanceEntity balanceEntity) {
        try {
            log.info("Object: {}", balanceEntity);
            this.balanceRepository.save(balanceEntity);
            log.info("Object after saving: {}", balanceEntity);
        } catch (Exception e) {
            throw new NullPointerException("Balance not exits");
        }
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

     public List<BalanceEntity> findAllBalances() {
        return this.balanceRepository.findAll();
    }
}
