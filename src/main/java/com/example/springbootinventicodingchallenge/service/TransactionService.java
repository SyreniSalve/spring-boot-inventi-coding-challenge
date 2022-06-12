package com.example.springbootinventicodingchallenge.service;

import com.example.springbootinventicodingchallenge.entity.AccountEntity;
import com.example.springbootinventicodingchallenge.entity.BalanceEntity;
import com.example.springbootinventicodingchallenge.entity.TransactionEntity;
import com.example.springbootinventicodingchallenge.repository.AccountRepository;
import com.example.springbootinventicodingchallenge.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Autowired
    private BalanceServiceImpl balanceService;



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

    public void exportCSVLogic(BalanceEntity inceptiveBalance, BalanceEntity finalBalance,
                               List<TransactionEntity> transactionList, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        String fileName = "transaction.csv";

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;

        response.setHeader(headerKey, headerValue);
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"Account number", "Operation date", "Beneficiary", "Comment", "Amount", "Currency", "Status"};
        String[] nameMapping = {"accountNumber", "operationDate", "beneficiary", "comment", "amount", "currency", "status"};

        csvWriter.writeHeader(csvHeader);
        csvWriter.write(inceptiveBalance, nameMapping);

        for(TransactionEntity transaction : transactionList) {
            csvWriter.write(transaction, nameMapping);
        }

        csvWriter.write(finalBalance, nameMapping);
        csvWriter.close();
    }

    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<TransactionEntity> transactionList = findAll();
        BalanceEntity inceptiveBalance = this.balanceService.findInceptiveBalance();
        BalanceEntity finalBalance = this.balanceService.findLastBalance();
        exportCSVLogic(inceptiveBalance, finalBalance, transactionList, response);
    }

    public void exportByDateToCSV(LocalDate from, LocalDate to, HttpServletResponse response) throws IOException {
        List<TransactionEntity> transactionList = getListOfTransactionsByDate(from, to);
        BalanceEntity inceptiveBalance = this.balanceService.findInceptiveBalanceInTheList(transactionList);
        BalanceEntity finalBalance = this.balanceService.findLastBalanceInTheList(transactionList);
        exportCSVLogic(inceptiveBalance, finalBalance, transactionList, response);
    }
}
