package com.example.springcsvparsingwithmultithreading.service;

import com.example.springcsvparsingwithmultithreading.model.Error;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Service
public class ErrorLoggingService {

    @Autowired
    private Validator validator;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Lock lock = new ReentrantLock();

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
