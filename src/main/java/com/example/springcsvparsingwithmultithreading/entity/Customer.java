package com.example.springcsvparsingwithmultithreading.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Customer {

    @Id
    private Long recordNumber;

    @NotNull(message = "Customer ID must NOT be NULL!")
    private int customerId;

    @NotNull(message = "Customer name must NOT be NULL!")
    private String customerName;

    @NotNull(message = "Customer surName must NOT be NULL!")
    private String customerSurname;

    @NotNull(message = "Customer Address must NOT be NULL!")
    private String customerAddress;

    @NotNull(message = "Customer ZIP code must NOT be NULL!")
    private Long customerZipCode;

    @NotNull(message = "Customer nation ID must NOT be NULL!")
    @Size(min = 10 , max = 10 , message = "Customer National ID must include 10 digits.")
    private String customerNationalId;

    @NotNull(message = "Customer Birth date must NOT be NULL!")
    private String customerBirthDate;

    public boolean isBirthDateValid(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
//        System.out.println("000000000000000000000000 " + this.customerBirthDate);
//        System.out.println(LocalDate.parse(this.customerBirthDate , formatter));
//        System.out.println(LocalDate.parse(this.customerBirthDate , formatter).isAfter(LocalDate.of(1995,1,1)));
        return LocalDate.parse(this.customerBirthDate , formatter).isAfter(LocalDate.of(1995,1,1));
    }

}
