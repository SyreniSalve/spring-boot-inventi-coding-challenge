package com.example.springbootinventicodingchallenge.service;

import com.example.springbootinventicodingchallenge.entity.AccountEntity;
import com.example.springbootinventicodingchallenge.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountEntity createAccount(AccountEntity account) {
        return this.accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
