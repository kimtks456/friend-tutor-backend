package com.gdsc.solutionChallenge.global.config;
import jakarta.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AES {

    private static String ALGORITHM;
    private static String SECRET_KEY;
    private static final int KEY_LENGTH = 256;


    public AES(@Value("${email.secret}") String secretKey,
               @Value("${email.encrypt.algorithm}") String algorithm) {
        ALGORITHM = algorithm;
        SECRET_KEY = secretKey;
    }

    public static String encrypt(String plainText) throws Exception {
        SecretKey key = generateKey(SECRET_KEY);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printBase64Binary(encryptedBytes);
    }

    public static String decrypt(String encryptedText) throws Exception {
        SecretKey key = generateKey(SECRET_KEY);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(DatatypeConverter.parseBase64Binary(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private static SecretKey generateKey(String key) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        keyBytes = sha.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, KEY_LENGTH / 8);
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }
}
