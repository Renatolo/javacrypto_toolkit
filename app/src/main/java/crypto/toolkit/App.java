package crypto.toolkit;

import java.security.Key;

public class App {
    public String getGreeting() {
        try {
            Key key = toolkit.read_sym_key("1.txt");
            String encryptedHex = toolkit.sym_encrypt_str("Hello, World!", key);
            System.out.println("Encrypted hex string: " + encryptedHex);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error reading key";
        }

        // now decrypt the string
        try {
            Key key = toolkit.read_sym_key("1.txt");
            String encryptedHex = toolkit.sym_encrypt_str("Hello, World!", key);
            String decryptedStr = toolkit.sym_decrypt_str(encryptedHex, key);
            System.out.println("Decrypted string: " + decryptedStr);
            return "Encryption and decryption successful";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error decrypting string");
            return "Error decrypting string";
        }
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
