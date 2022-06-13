package com.example.springbootinventicodingchallenge.service;

import com.example.springbootinventicodingchallenge.entity.BalanceEntity;
import com.example.springbootinventicodingchallenge.entity.CSVObject;
import com.example.springbootinventicodingchallenge.entity.Status;
import com.example.springbootinventicodingchallenge.entity.TransactionEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReadCSVFile {

    private static List<CSVObject> CSVFileReader(String fileName) throws IOException {

        final CellProcessor[] processors = new CellProcessor[]{
                new NotNull(), // accountNumber
                new NotNull(), // operationDate
                new Optional(), // beneficiary
                new Optional(), // comment
                new NotNull(), // amount
                new NotNull(), // currency
                new Optional() //status
        };

        try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE)) {

            final String[] headers = new String[]{"accountNumber", "operationDate", "beneficiary", "comment", "amount", "currency", "status"};


            CSVObject csvObject;
            List<CSVObject> objects = new ArrayList<>();
            while ((csvObject = beanReader.read(CSVObject.class, headers, processors)) != null) {
                objects.add(csvObject);
            }
            return objects;
        }
    }

    public List<TransactionEntity> getTListOfTransactions(String fileName) throws IOException {
       List<CSVObject> csvObjects =  CSVFileReader(fileName);
        return convertCsvObjectListIntoTransactionsList(csvObjects);
    }

    public List<BalanceEntity> getListOfBalance(String fileName) throws IOException {
        List<CSVObject> csvObjects = CSVFileReader(fileName);
        return convertCsvObjectListIntoBalanceList(csvObjects);
    }

    private static List<TransactionEntity> convertCsvObjectListIntoTransactionsList(List<CSVObject> list) {
        List<CSVObject> newList = transactionFilter(list);
        List<TransactionEntity> transactionList = new ArrayList<>();
        for (CSVObject object : newList) {
            TransactionEntity transaction = new TransactionEntity();
            transaction.setAccountNumber(object.getAccountNumber());
            transaction.setOperationDate(formatDateFromString(object.getOperationDate()));
            transaction.setBeneficiary(object.getBeneficiary());
            transaction.setComment(object.getComment());
            transaction.setAmount(convertDoubleFromString(object.getAmount()));
            transaction.setCurrency(object.getCurrency());
            if (object.getStatus().equals("WITHDRAW")) {
                transaction.setStatus(Status.WITHDRAW);
            } else {
                transaction.setStatus(Status.DEPOSIT);
            }
            transactionList.add(transaction);
        }
        return transactionList;
    }


    private static List<BalanceEntity> convertCsvObjectListIntoBalanceList(List<CSVObject> list) {
        List<CSVObject> newList = balanceFilter(list);
        List<BalanceEntity> balanceList = new ArrayList<>();
        for (CSVObject object : newList) {
            BalanceEntity balance = new BalanceEntity();
            balance.setAccountNumber(object.getAccountNumber());
            balance.setOperationDate(formatDateFromString(object.getOperationDate()));
            balance.setBeneficiary(object.getBeneficiary());
            balance.setComment(object.getComment());
            balance.setAmount(convertDoubleFromString(object.getAmount()));
            balance.setCurrency(object.getCurrency());
            if (object.getStatus().equals("INCEPTIVE_BALANCE")) {
                balance.setStatus(Status.INCEPTIVE_BALANCE);
            } else {
                balance.setStatus(Status.FINAL_BALANCE);
            }
            balanceList.add(balance);
        }
        return balanceList;
    }

    private static LocalDate formatDateFromString(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        return LocalDate.parse(date, dateTimeFormatter);
    }

    private static double convertDoubleFromString(String amountString) {
        return Double.parseDouble(amountString);
    }


    private static List<CSVObject> transactionFilter(List<CSVObject> list) {
        return list.stream()
                .filter(object -> object.getStatus().equals("WITHDRAW") || object.getStatus().equals("DEPOSIT"))
                .collect(Collectors.toList());
    }

    private static List<CSVObject> balanceFilter(List<CSVObject> list) {
        return list.stream()
                .filter(object -> object.getStatus().equals("INCEPTIVE_BALANCE") || object.getStatus().equals("FINAL_BALANCE"))
                .collect(Collectors.toList());
    }

}

