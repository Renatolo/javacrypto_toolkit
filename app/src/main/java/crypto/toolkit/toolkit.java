package crypto.toolkit;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class toolkit {

    private static final String SYM_ALGORITHM = "AES";
    private static final String SYM_CIPHER = "AES/CBC/PKCS5Padding";

    /** Symmetric cryptography algorithm. */

    // Str + Key -> Encrypted Str (Hex)
    public static String sym_encrypt_str(String plaintext, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(SYM_CIPHER);
        
        cipher.init(Cipher.ENCRYPT_MODE, key);
        
        byte[] iv = cipher.getIV(); 
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());

        byte[] combined = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);

        return helpingTools.toHex(combined);

    }

    // Encrypted Str (Hex) + Key -> Decrypted String
    public static String sym_decrypt_str(String encryptedHex, Key key) throws Exception {
        byte[] combined = helpingTools.hexStringToByteArray(encryptedHex);

        byte[] iv = new byte[16];
        byte[] encryptedBytes = new byte[combined.length - 16];
        System.arraycopy(combined, 0, iv, 0, 16);
        System.arraycopy(combined, 16, encryptedBytes, 0, encryptedBytes.length);

        Cipher cipher = Cipher.getInstance(SYM_CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, key, new javax.crypto.spec.IvParameterSpec(iv));
        
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    public static Key read_sym_key(String keyPath) throws Exception {

        byte[] encoded = helpingTools.readFileToByteArray(keyPath);

        if (encoded.length != 16) {
            throw new Exception("Invalid key length: " + encoded.length + " bytes. Expected 16 bytes for AES-128.");
        }

		return new SecretKeySpec(encoded, SYM_ALGORITHM);
	}
}
