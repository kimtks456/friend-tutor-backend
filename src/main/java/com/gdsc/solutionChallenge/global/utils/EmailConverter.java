package com.gdsc.solutionChallenge.global.utils;

import jakarta.annotation.Resource;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Slf4j
@Converter
@Component
@PropertySource("classpath:application-secure.properties")

public class EmailConverter implements AttributeConverter<String, String> {

//    private String ALGORITHM;


//    private static final String ALGORITHM = "AES";
//    private static final byte[] KEY = "1234567890123456".getBytes();
//    private static final byte[] ivBytes = "1234567890123456".getBytes();

    private static final String secretKey = "sdfetdfef";
    private static final String salt = "1234567890123456";
//    private String secretKey;
//    private String salt;
//    public EmailConverter(@Value("${email.secret}") String secretKey,
//                          @Value("${email.salt}") String salt) {
//        this.secretKey = secretKey;
//        this.salt = salt;
//    }
//    public void setSecretKey(@Value("${email.secret}") String secretKey) {
//        this.secretKey = secretKey;
//    }
//
//    public void setSalt(@Value("${email.salt}") String salt) {
//        this.salt = salt;
//    }

//    @Value("${email.secret}")
//    private String secretKey;
//
//    @Value("${email.salt}")
//    private String salt;

    public String toString() {
        return secretKey + " " + salt;
    }

    @Override
    public String convertToDatabaseColumn(String email) {
        TextEncryptor textEncryptor = Encryptors.text(secretKey, salt);
        try {
            return textEncryptor.encrypt(email);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error encrypting email", e);
        }

//        log.error("convertToDatabaseColumn");
//        if (email == null) {
//            return null;
//        }
//
//        try {
//            SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
//            Cipher cipher = Cipher.getInstance(ALGORITHM);
//            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
//            byte[] encryptedBytes = cipher.doFinal(email.getBytes());
//            return Base64.encodeBase64String(encryptedBytes);
//        } catch (Exception e) {
//            throw new RuntimeException("Error encrypting email", e);
//        }
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
//        if (encryptedEmail == null) {
//            return null;
//        }
//
//        try {
//            byte[] encryptedBytes = Base64.decodeBase64(encryptedEmail);
//            SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
//            Cipher cipher = Cipher.getInstance(ALGORITHM);
//            cipher.init(Cipher.DECRYPT_MODE, keySpec);
//            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
//            return new String(decryptedBytes);
//        } catch (Exception e) {
//            throw new RuntimeException("Error decrypting email", e);
//        }
    }
}
