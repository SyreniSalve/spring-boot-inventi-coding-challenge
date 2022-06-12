package com.example.springbootinventicodingchallenge.controller;

import com.example.springbootinventicodingchallenge.entity.AccountEntity;
import com.example.springbootinventicodingchallenge.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountEntity> createAccount(@RequestBody AccountEntity account) {
        AccountEntity newAccount = this.accountService.createAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{account_id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("account_id") Long account_id) {
        this.accountService.deleteAccount(account_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
