package com.example.springcsvparsingwithmultithreading.service;

import aj.org.objectweb.asm.TypeReference;
import com.example.springcsvparsingwithmultithreading.ValidationResult;
import com.example.springcsvparsingwithmultithreading.entity.Account;
import com.example.springcsvparsingwithmultithreading.entity.Customer;
import com.example.springcsvparsingwithmultithreading.model.Error;
import com.example.springcsvparsingwithmultithreading.repository.AccountRepository;
import com.example.springcsvparsingwithmultithreading.repository.CustomerRepository;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class FileReaderService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Validator validator;

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Lock lock = new ReentrantLock();

    public void processFile(MultipartFile accountFile, MultipartFile customerFile) {

        executorService.submit(() -> processAccountFile(accountFile));
        executorService.submit(() -> processCustomerFile(customerFile));
        executorService.shutdown();

//        processAccountFile(accountFile);
//        processCustomerFile(customerFile);
    }

    private void processAccountFile(MultipartFile file) {
        String errorMessage = "";
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] line;
            csvReader.readNext();
//            System.out.println("5555555555555555555");
            while ((line = csvReader.readNext()) != null) {
                Account account = mapToAccount(line);



                ValidationResult validationResult = validateAccount(account, errorMessage);

                if (validationResult.isValid()) {
                    System.out.println("AAAAAAAAAAAAAAAAccount " + account.getAccountNumber() + " is valid");
                    accountRepository.save(account);
                    System.out.println("acc " + account.getAccountBalance());
                } else {
                    writeErrorLog("Account", account.getRecordNumber(), validationResult.getMessage());
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private void processCustomerFile(MultipartFile file) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] line;
            csvReader.readNext();
            while ((line = csvReader.readNext()) != null) {
//                System.out.println("***********************");
                Customer customer = mapToCustomer(line);

//                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&");

                ValidationResult validationResult = validateCustomer(customer);

//                System.out.println("9999999999999999999999999999");
//                System.out.println(validationResult.isValid());
                if (validationResult.isValid()) {
//                    System.out.println("777777777777777777777777777777777777");
                    customerRepository.save(customer);
                    System.out.println("in cus " + customer.getCustomerId());
                } else {
//                    System.out.println("44444444444444444444444");
                    writeErrorLog("Customer", customer.getRecordNumber(), validationResult.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public Account mapToAccount(String[] line) {
        Account account = new Account();
//        for (int i = 0; i < line.length; i++) {
//            System.out.println(line[i]);
//        }

        System.out.println("111111111111111111111111111111");
        account.setRecordNumber(Long.parseLong(line[0]));
        System.out.println("2222222222222222222222222222222222");
        account.setAccountNumber(line[1]);
        System.out.println("3333333333333333333333333333333333");
        account.setAccountType(Integer.parseInt(line[2]));
        System.out.println("4444444444444444444444444444444444");
        account.setCustomerId(Integer.parseInt(line[3]));
        System.out.println("555555555555555555555555555555" + Double.parseDouble(line[4].replace("$", "")));
        account.setAccountLimit(line[4].isEmpty() ? 0 : Double.parseDouble(line[4].replace("$", "")));
        System.out.println("66666666666666666666666666666666");
        account.setAccountOpenDate(line[5]);
        System.out.println("777777777777777777777777777777");
        account.setAccountBalance(line[6].isEmpty() ? 0 : Double.parseDouble(line[6].replace("$", "")));
        System.out.println("888888888888888888888888888888");
        return account;
    }

    public Customer mapToCustomer(String[] line) {
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

    public void writeErrorLog(String fileName, Long recordNumber, String errorMessage) {
        lock.lock();
        try {
            System.out.println("********************** ERROR **************************");
            Error error = new Error(fileName, recordNumber, "ErrorCode1", "Validation Error", errorMessage, LocalDate.now().toString());
            File errorFile = new File("ErrorLog.json");

            List<Error> errors;

            if (errorFile.exists()) {
                errors = new ArrayList<>(Arrays.asList(objectMapper.readValue(errorFile, Error[].class)));
            } else {
                errors = new ArrayList<>();
            }

            errors.add(error);

            objectMapper.writeValue(errorFile, errors);


        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }
}
