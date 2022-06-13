package com.example.springbootinventicodingchallenge.entity;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class CSVObject {

    @NotNull
    private String accountNumber;

    @NotNull
    private String operationDate;

    private String beneficiary;

    private String comment;

    @NotNull
    private String amount;

    @NotNull
    private String currency;

    private String status;
}
