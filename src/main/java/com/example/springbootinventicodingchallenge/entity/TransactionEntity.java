package com.example.springbootinventicodingchallenge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity extends AbstractEntity {

    @NotBlank
    @Column(name = "operation_date")
    @JsonSerialize(using = LocalDateSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Instant operationDate = Instant.now();

    @NotBlank
    @Column(name = "beneficiary")
    private String beneficiary;

    @Column(name = "comment")
    private String comment;

    @NotBlank
    @Column(name = "amount")
    private double amount;

    @NotBlank
    @Column(name = "currency")
    private String currency;

}
