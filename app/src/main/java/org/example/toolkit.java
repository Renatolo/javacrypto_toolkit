package crypto.toolkit;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class toolkit {

    private static final String SYM_ALGORITHM = "AES";
    private static final String SYM_CIPHER = "AES/CBC/PKCS5Padding";

    /** Symmetric cryptography algorithm. */

    // Str + Key -> Encrypted Str
    public static void sym_encrypt_str(String plaintext, Key key) throws Exception {
        System.out.println("Encrypting string: " + plaintext);
        byte[] plainBytes = plaintext.getBytes();

        System.out.println("The binary representation of the sentence (in hexadecimal) is:");
		System.out.println(plainBytes.toString());

        Cipher cipher = Cipher.getInstance(SYM_CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainBytes);
        System.out.println("The encrypted string (in hexadecimal) is:");
        System.out.println(encryptedBytes.toString());

    }

    public static Key read_sym_key(String keyPath) throws Exception {
        
		System.out.println("Reading key from file " + keyPath + " ...");
		FileInputStream fis = new FileInputStream(keyPath);
		byte[] encoded = new byte[fis.available()];
		fis.read(encoded);
		fis.close();

        if (encoded.length != 16) {
            throw new Exception("Invalid key length: " + encoded.length + " bytes. Expected 16 bytes for AES-128.");
        }

		return new SecretKeySpec(encoded, SYM_ALGORITHM);
	}
}
