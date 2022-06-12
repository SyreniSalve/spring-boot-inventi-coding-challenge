package com.example.springbootinventicodingchallenge.controller;

import com.example.springbootinventicodingchallenge.entity.TransactionEntity;
import com.example.springbootinventicodingchallenge.service.BalanceServiceImpl;
import com.example.springbootinventicodingchallenge.service.TransactionService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BalanceServiceImpl balanceServiceImpl;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @GetMapping("/get/{date_from}/{date_to}")
    public ResponseEntity<List<TransactionEntity>> getAllTransactionsByDate(@PathVariable("date_from")
                                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                                @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
                                                                                @PathVariable("date_to")
                                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                                @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dateTo) {
        List<TransactionEntity> transactionList = this.transactionService.getListOfTransactionsByDate(dateFrom, dateTo);
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        this.transactionService.exportToCSV(response);
    }

    @GetMapping("/export/between/{date_from}/{date_to}")
    public void exportByDateToCSV(@PathVariable("date_from")
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                      @JsonFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                  @PathVariable("date_to")
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                  @JsonFormat(pattern = "yyyy-MM-dd") LocalDate to,
                                  HttpServletResponse response) throws IOException {
        this.transactionService.exportByDateToCSV(from, to, response);
    }



    @PostMapping("/save")
    public ResponseEntity<TransactionEntity> saveTransaction(@RequestBody TransactionEntity transaction) {
        TransactionEntity newTransaction  = this.transactionService.saveTransaction(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }
}
