package com.example.springcsvparsingwithmultithreading.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private String fileName;
    private Long recordNumber;
    private String errorCode;
    private String errorClassificationName;
    private String errorDescription;
    private String errorDate;
}
