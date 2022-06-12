package com.example.springbootinventicodingchallenge.controller;

import com.example.springbootinventicodingchallenge.entity.TransactionEntity;
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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
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
        response.setContentType("text/csv");
        String fileName = "transaction.csv";

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;

        response.setHeader(headerKey, headerValue);

        List<TransactionEntity> transactionList = this.transactionService.findAll();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"Account number", "Operation date", "Beneficiary", "Comment", "Amount", "Currency", "Status"};
        String[] nameMapping = {"accountNumber", "operationDate", "beneficiary", "comment", "amount", "currency", "status"};

        csvWriter.writeHeader(csvHeader);

        for(TransactionEntity transaction : transactionList) {
            csvWriter.write(transaction, nameMapping);
        }

        csvWriter.close();
    }


    @PostMapping("/save")
    public ResponseEntity<TransactionEntity> saveTransaction(@RequestBody TransactionEntity transaction) {
        TransactionEntity newTransaction  = this.transactionService.saveTransaction(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }
}
