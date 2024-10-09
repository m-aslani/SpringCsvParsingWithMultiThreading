package com.example.springcsvparsingwithmultithreading;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationResult {

    private boolean valid;
    private String message;
}
