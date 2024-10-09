package com.example.springcsvparsingwithmultithreading.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class EncryptedService {

    private static final String ALGORITHM = "AES"; // AES encryption algorithm
    private static final String TRANSFORMATION = "AES";

    // Method to generate a secret key
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128); // 128-bit AES key
        return keyGen.generateKey();
    }

    public static String encryptString(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String encryptDouble(double value, SecretKey key) throws Exception {
        String data = Double.toString(value);
        return encryptString(data, key);
    }
}
