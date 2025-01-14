package Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding"; // Use explicit mode and padding
    private static final String ENCODING = "UTF-8";
    private static final String sudoText = "1234567890abcdefghijkl";

    // Encrypt a given string using a key
    public static String encrypt(String data, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        String adjustedKey = key.concat(sudoText.substring(sudoText.length() - (16 - key.length())));
        if (adjustedKey.length() != 16 && adjustedKey.length() != 24 && adjustedKey.length() != 32) {
            throw new IllegalArgumentException("Key length must be 16, 24, or 32 bytes for AES.");
        }

        // Initialize Cipher
        SecretKeySpec keySpec = new SecretKeySpec(adjustedKey.getBytes(ENCODING), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        // Perform encryption
        byte[] encrypted = cipher.doFinal(data.getBytes(ENCODING));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // Decrypt a given string using a key
    public static String decrypt(String encryptedData, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(ENCODING), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        byte[] original = cipher.doFinal(decoded);
        return new String(original, ENCODING);
    }

    // Generate a random AES key (optional)
    public static String generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}
