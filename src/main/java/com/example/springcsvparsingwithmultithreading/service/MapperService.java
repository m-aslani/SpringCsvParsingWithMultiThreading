package com.example.springcsvparsingwithmultithreading.service;


import com.example.springcsvparsingwithmultithreading.entity.Account;
import com.example.springcsvparsingwithmultithreading.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;

public class MapperService {


    public static Account mapToAccount(String[] line) {
        Account account = new Account();
//        for (int i = 0; i < line.length; i++) {
//            System.out.println(line[i]);
//        }

//        System.out.println("111111111111111111111111111111");
        account.setRecordNumber(Long.parseLong(line[0]));
//        System.out.println("2222222222222222222222222222222222");
        account.setAccountNumber(line[1]);
//        System.out.println("3333333333333333333333333333333333");
        account.setAccountType(Integer.parseInt(line[2]));
//        System.out.println("4444444444444444444444444444444444");
        account.setCustomerId(Integer.parseInt(line[3]));
//        System.out.println("555555555555555555555555555555" + Double.parseDouble(line[4].replace("$", "")));
        account.setAccountLimit(line[4].isEmpty() ? 0 : Double.parseDouble(line[4].replace("$", "")));
//        System.out.println("66666666666666666666666666666666");
        account.setAccountOpenDate(line[5]);
//        System.out.println("777777777777777777777777777777");
        account.setAccountBalance(line[6].isEmpty() ? 0 : Double.parseDouble(line[6].replace("$", "")));
//        System.out.println("888888888888888888888888888888");
        return account;
    }

    public static Customer mapToCustomer(String[] line) {
        Customer customer = new Customer();
        customer.setRecordNumber(Long.parseLong(line[0]));
        customer.setCustomerId(Integer.parseInt(line[1]));
        customer.setCustomerName(line[2]);
        customer.setCustomerSurname(line[3]);
        customer.setCustomerAddress(line[4]);
        customer.setCustomerZipCode(Long.parseLong(line[5]));
        customer.setCustomerNationalId(line[6]);
        customer.setCustomerBirthDate(line[7]);
        return customer;
    }
}
