package com.example.springbootinventicodingchallenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor
public class AccountEntity extends AbstractEntity{

    @NotNull
    @Column(name = "account_number", unique = true)
    @Size(min = 20, message = "{validation.name.size.too_short}")
    @Size(max = 20, message = "{validation.name.size.too_long}")
    protected String accountNumber;

}
