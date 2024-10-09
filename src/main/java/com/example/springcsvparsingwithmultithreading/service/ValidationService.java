package com.example.springcsvparsingwithmultithreading.service;

import com.example.springcsvparsingwithmultithreading.ValidationResult;
import com.example.springcsvparsingwithmultithreading.entity.Account;
import com.example.springcsvparsingwithmultithreading.entity.Customer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Service
public class ValidationService {

    @Autowired
    private Validator validator;

    public ValidationResult validateAccount(Account account, String errorMessage) {

        ValidationResult validationResult = new ValidationResult();
        boolean valid = true;

        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Account> violation : violations) {
                errorMessage += violation.getMessage() + ", ";
            }
            valid = false;
        }

        if (!account.isAccountTypeValid()) {
            errorMessage += "Account type is not valid, ";
            valid = false;
        }


        if (account.isBalanceValid()) {
            errorMessage += "Account balance is not valid";
            valid = false;
        }

        validationResult.setValid(valid);
        validationResult.setMessage(errorMessage);

        return validationResult;
    }

    public ValidationResult validateCustomer(Customer customer) {
        ValidationResult validationResult = new ValidationResult();
        String errorMessage = "";
        boolean valid = true;
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
//        System.out.println("5555555555555555555555555555555");
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Customer> violation : violations) {
                errorMessage += violation.getMessage() + ", ";
            }
            valid = false;
        }

//        System.out.println("33333333333333333333333333333");

        if (!customer.isBirthDateValid()) {
            errorMessage += "Customer birth date is not valid";
            valid = false;
        }

//        System.out.println("66666666666666666666666666666666666");

        validationResult.setValid(valid);
        validationResult.setMessage(errorMessage);
//        System.out.println("0000000000000000000000000");
        return validationResult;
    }
}
