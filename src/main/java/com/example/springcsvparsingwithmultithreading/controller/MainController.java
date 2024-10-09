package com.example.springcsvparsingwithmultithreading.controller;

import com.example.springcsvparsingwithmultithreading.service.FileReaderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@Data
@RestController
public class MainController {

    @Autowired
    private FileReaderService fileReaderService;

    @PostMapping("/csv/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("accountFile") MultipartFile accountFile, @RequestParam("customerFile") MultipartFile customerFile) {
        try {
            fileReaderService.processFile(accountFile, customerFile);
            return ResponseEntity.ok("File processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("file processing failed" + e.getMessage());
        }
    }
}
