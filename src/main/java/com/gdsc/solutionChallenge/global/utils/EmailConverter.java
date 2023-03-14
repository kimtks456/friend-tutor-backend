package com.gdsc.solutionChallenge.global.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Slf4j
@Converter
@Component
public class EmailConverter implements AttributeConverter<String, String> {

    @Value("${email.secret}")
    private String secretKey;

    @Value("${email.salt}")
    private String salt;

    @Override
    public String convertToDatabaseColumn(String email) {
        TextEncryptor textEncryptor = Encryptors.text(secretKey, salt);
        try {
            return textEncryptor.encrypt(email);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error encrypting email", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String encryptedEmail) {
        TextEncryptor textEncryptor = Encryptors.text(secretKey, salt);
        try {
            return textEncryptor.decrypt(encryptedEmail);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error decrypting email", e);
        }
    }
}
