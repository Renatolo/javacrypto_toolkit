package crypto.toolkit;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class symEncrypt {

    private static final String SYM_ALGORITHM = "AES";
    private static final String SYM_CIPHER = "AES/CBC/PKCS5Padding";

    public static void generate_sym_key(String keyFilePath) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(SYM_ALGORITHM);
        keyGen.init(128); // AES-128
        Key key = keyGen.generateKey();
        helpingTools.writeByteArrayToFile(keyFilePath, key.getEncoded());
    }

    public static void sym_encrypt_file(String inputFilePath, String outputFilePath, String keyFilePath) throws Exception {
        byte[] fileData = helpingTools.readFileToByteArray(inputFilePath);
        String encryptedHex = sym_encrypt_str(new String(fileData), keyFilePath);

        helpingTools.writeByteArrayToFile(outputFilePath, encryptedHex.getBytes());
    }

    public static void sym_decrypt_file(String inputFilePath, String outputFilePath, String keyFilePath) throws Exception {
        byte[] encryptedData = helpingTools.readFileToByteArray(inputFilePath);
        String decryptedStr = sym_decrypt_str(new String(encryptedData), keyFilePath);

        helpingTools.writeByteArrayToFile(outputFilePath, decryptedStr.getBytes());
    }

    // Str + Key -> Encrypted Str (Hex)
    public static String sym_encrypt_str(String plaintext, String keyFilePath) throws Exception {
        Key key = read_sym_key(keyFilePath);
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
    public static String sym_decrypt_str(String encryptedHex, String keyFilePath) throws Exception {
        Key key = read_sym_key(keyFilePath);
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
