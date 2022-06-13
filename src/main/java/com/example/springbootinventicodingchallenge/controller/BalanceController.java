package com.example.springbootinventicodingchallenge.controller;

import com.example.springbootinventicodingchallenge.entity.BalanceEntity;
import com.example.springbootinventicodingchallenge.service.BalanceServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/balance")
public class BalanceController {

    @Autowired
    private BalanceServiceImpl balanceServiceImpl;

    @JsonSerialize(using = LocalDateSerializer.class)
    @GetMapping("/get/final/{date}")
    public ResponseEntity<BalanceEntity> getFinalBalanceByDate(@PathVariable("date")
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                              @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        BalanceEntity balance = this.balanceServiceImpl.findFinalBalanceByDate(date);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    @GetMapping("/get/inceptive/{date}")
    public ResponseEntity<BalanceEntity> getInceptiveBalanceByDate(@PathVariable("date")
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        BalanceEntity balance = this.balanceServiceImpl.findInceptiveBalanceByDate(date);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BalanceEntity>> findAll() {
        return new ResponseEntity<>(this.balanceServiceImpl.findAllBalances(), HttpStatus.OK);
    }

}
