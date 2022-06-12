package com.example.springbootinventicodingchallenge.service;

import com.example.springbootinventicodingchallenge.entity.AccountEntity;
import com.example.springbootinventicodingchallenge.entity.TransactionEntity;
import com.example.springbootinventicodingchallenge.repository.AccountRepository;
import com.example.springbootinventicodingchallenge.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;



    public TransactionEntity saveTransaction(TransactionEntity transaction) {
        AccountEntity findAccount = this.accountRepository.findByAccountNumber(transaction.getAccountNumber());
        if (findAccount.getAccountNumber().equals(transaction.getAccountNumber())) {
            return this.transactionRepository.save(transaction);
        } else {
            throw new NullPointerException("This account number doesn't exist");
        }
    }

    public List<TransactionEntity> getListOfTransactionsByDate(LocalDate dateFrom, LocalDate dateTo) {
        return this.transactionRepository.findAllByOperationDateBetween(dateFrom, dateTo);
    }

    public List<TransactionEntity> findAll() {
        return this.transactionRepository.findAll();
    }
}
