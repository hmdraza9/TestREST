package com.restAPI.practiceClasses;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PasswordEncryption {

    // Encrypt the password
    public static String encrypt(String password, String secretKey) {
        byte[] encryptedBytes;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES"));
            encryptedBytes = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Decrypt the password
    public static String decrypt(String encryptedPassword, String secretKey) {
        byte[] decryptedBytes;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES"));
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
            decryptedBytes = cipher.doFinal(decodedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try {

            String password = "MySecurePassword";
            System.out.println("Original Password: " + password);

            // Encrypt the password
            String encryptedPassword = encrypt(password, "dddddddddddddddd");
            System.out.println("Encrypted Password: " + encryptedPassword);

            // Decrypt the password
            String decryptedPassword = decrypt(encryptedPassword, "dddddddddddddddd");
            System.out.println("Decrypted Password: " + decryptedPassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
