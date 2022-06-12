package com.example.springbootinventicodingchallenge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "balance")
public class BalanceEntity extends AbstractEntity {

    @OneToOne
    @JoinColumn(insertable = false, updatable = false, name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity account;

    @NotBlank
    private double balance;

    @NotBlank
    @Column(name = "date")
    @JsonSerialize(using = LocalDateSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @OneToMany
    @JoinTable(name = "balance_transaction",
            joinColumns = @JoinColumn(name = "balance_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    private List<TransactionEntity> transaction;

    public BalanceEntity(AccountEntity account) {
        this.account = account;
        this.balance = 0;
    }

}
