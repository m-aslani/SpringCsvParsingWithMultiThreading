package com.example.springcsvparsingwithmultithreading.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "Customer")
public class Customer {

    @Id
    private Long recordNumber;

    @NotNull(message = "Customer ID must NOT be NULL!")
    private int customerId;

    @NotBlank(message = "Customer name must NOT be NULL!")
    private String customerName; //encrypted

    @NotBlank(message = "Customer surName must NOT be NULL!")
    private String customerSurname; //encrypted

    @NotBlank(message = "Customer Address must NOT be NULL!")
    private String customerAddress;

    @NotNull(message = "Customer ZIP code must NOT be NULL!")
    private Long customerZipCode;

    @NotBlank(message = "Customer nation ID must NOT be NULL!")
    @Size(min = 10 , max = 10 , message = "Customer National ID must include 10 digits.")
    private String customerNationalId; //encrypted

    @NotBlank(message = "Customer Birth date must NOT be NULL!")
    private String customerBirthDate;

///   @NotNull(message = "Customer Name must NOT be NULL!")
//@Column(length = 512)
//    private String encryptedCustomerName;
////    @NotNull(message = "Customer Name must NOT be NULL!")
//@Column(length = 512)
//    private String encryptedCustomerSurname;
////    @NotNull(message = "Customer Name must NOT be NULL!")
//@Column(length = 512)
//    private String encryptedCustomerNationalId;

    public boolean isBirthDateValid(){
        if(this.customerBirthDate == null || this.customerBirthDate.isEmpty() || this.customerBirthDate.equals("")){
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        return LocalDate.parse(this.customerBirthDate , formatter).isAfter(LocalDate.of(1995,1,1));
    }

}
