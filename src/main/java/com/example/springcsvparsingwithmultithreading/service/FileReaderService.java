package com.example.springcsvparsingwithmultithreading.service;

import com.example.springcsvparsingwithmultithreading.ValidationResult;
import com.example.springcsvparsingwithmultithreading.entity.Account;
import com.example.springcsvparsingwithmultithreading.entity.Customer;
import com.example.springcsvparsingwithmultithreading.repository.AccountRepository;
import com.example.springcsvparsingwithmultithreading.repository.CustomerRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class FileReaderService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private ErrorLoggingService errorLoggingService;;


    private final ExecutorService executorService = Executors.newFixedThreadPool(2);


    public void processFile(MultipartFile accountFile, MultipartFile customerFile) {

        executorService.submit(() -> processAccountFile(accountFile));
        executorService.submit(() -> processCustomerFile(customerFile));
        executorService.shutdown();
    }

    private void processAccountFile(MultipartFile file) {
        String errorMessage = "";
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] line;
            csvReader.readNext();
            while ((line = csvReader.readNext()) != null) {
                Account account = MapperService.mapToAccount(line);
                ValidationResult validationResult = validationService.validateAccount(account, errorMessage);

                if (validationResult.isValid()) {
                    accountRepository.save(account);
                } else {
                    errorLoggingService.writeErrorLog("Account", account.getRecordNumber(), validationResult.getMessage());
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
                Customer customer = MapperService.mapToCustomer(line);
                ValidationResult validationResult = validationService.validateCustomer(customer);
                if (validationResult.isValid()) {
                    customerRepository.save(customer);
                    System.out.println("in cus " + customer.getCustomerId());
                } else {
                    errorLoggingService.writeErrorLog("Customer", customer.getRecordNumber(), validationResult.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

}
