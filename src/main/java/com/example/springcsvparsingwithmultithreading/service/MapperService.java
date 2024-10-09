package com.example.springcsvparsingwithmultithreading.service;


import com.example.springcsvparsingwithmultithreading.entity.Account;
import com.example.springcsvparsingwithmultithreading.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class MapperService {


//    @Autowired
//    private static EncryptedService encryptedService;

    public static Account mapToAccount(String[] line)  {

//        SecretKey secretKey = encryptedService.generateKey();
        Account account = new Account();
        account.setRecordNumber(Long.parseLong(line[0]));
        account.setAccountNumber(line[1]);
//        account.setEncryptedAccountNumber(encryptedService.encryptString(line[1], secretKey));
        account.setAccountType(Integer.parseInt(line[2]));
        account.setCustomerId(Integer.parseInt(line[3]));
        account.setAccountLimit(line[4].isEmpty() ? 0 : Double.parseDouble(line[4].replace("$", "")));
        account.setAccountOpenDate(line[5]);
        account.setAccountBalance(line[6].isEmpty() ? 0 : Double.parseDouble(line[6].replace("$", "")));
//        account.setEncryptedAccountBalance(encryptedService.encryptString(line[6] , secretKey));

        return account;
    }

    public static Customer mapToCustomer(String[] line){
//        SecretKey secretKey = encryptedService.generateKey();
        Customer customer = new Customer();
        customer.setRecordNumber(Long.parseLong(line[0]));
        customer.setCustomerId(Integer.parseInt(line[1]));
        customer.setCustomerName(line[2]);
//        customer.setEncryptedCustomerName(encryptedService.encryptString(line[2], secretKey));
        customer.setCustomerSurname(line[3]);
//        customer.setEncryptedCustomerSurname(encryptedService.encryptString(line[3], secretKey));
        customer.setCustomerAddress(line[4]);
        customer.setCustomerZipCode(Long.parseLong(line[5]));
        customer.setCustomerNationalId(line[6]);
//        customer.setEncryptedCustomerNationalId(encryptedService.encryptString(line[6], secretKey));
        customer.setCustomerBirthDate(line[7]);
        return customer;
    }
}
