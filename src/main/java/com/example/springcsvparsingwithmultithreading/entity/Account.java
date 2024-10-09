package com.example.springcsvparsingwithmultithreading.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "My-Account")
public class Account {

    @Id
    private Long recordNumber;

    @NotNull(message = "Account Number must NOT be NULL!")
    @Size(min = 22, max = 22 , message = "Account Number must include 22 digits")
    private String accountNumber;

    @NotNull(message = "Account Type must NOT be NULL!")
//    @Pattern(regexp = "1|2|3" , message = "Account type must be 1, 2 or 3.")
    private int accountType;

    @NotNull(message = "Customer ID must NOT be NULL!")
    private int customerId;

    @NotNull(message = "Account Limit must NOT be NULL!")
    private double accountLimit;

    @NotNull(message = "Account open data must NOT be NULL!")
    private String accountOpenDate;

    @NotNull(message = "Account Balance must NOT be NULL!")
    private double accountBalance;

    public boolean isBalanceValid(){
        return this.accountBalance >= this.accountLimit;
    }

    public boolean isAccountTypeValid(){
        return this.accountType == 1 || this.accountType == 2 || this.accountType == 3;
    }


}
