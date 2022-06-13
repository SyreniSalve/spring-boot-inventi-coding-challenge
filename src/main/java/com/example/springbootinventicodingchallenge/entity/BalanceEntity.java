package com.example.springbootinventicodingchallenge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "balance")
@ToString
public class BalanceEntity extends AbstractEntity {

    @NotNull
    @Column(name = "account_number")
    @Size(min = 20, max = 20)
    private String accountNumber;

    @NotNull
    @Column(name = "operation_date")
    @JsonSerialize(using = LocalDateSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate operationDate;

    @Column(name = "beneficiary")
    private String beneficiary;

    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "amount")
    private double amount;

    @NotNull
    @Column(name = "currency")
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private Status status;

    @OneToMany
    @JoinTable(name = "balance_transaction",
            joinColumns = @JoinColumn(name = "balance_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    private List<TransactionEntity> transaction;

    public BalanceEntity(String accountNumber) {
        this.accountNumber = accountNumber;
        this.amount = 0;
    }

}
