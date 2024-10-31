package passwordmanager;

import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class AES {
	
	private static String key;

    public void setKey(String passwordString) throws Exception {
        byte[] salt = new byte[100];

        KeySpec spec = new PBEKeySpec(passwordString.toCharArray(), salt, 1000, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();

        key = Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes).substring(0, 32);
    }

    public static String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    static int exceptionCount = 0;

    public static String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            exceptionCount++;
            if (exceptionCount == 1) {
                JOptionPane.showOptionDialog(null,
                        "An encryption was likely created under a different master password!", "Error While Decrypting",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
            }
            
            return "";
        }
    }

}
