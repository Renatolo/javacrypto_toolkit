package crypto.toolkit;

import java.security.Key;

public class App {

    public void symEncryptFile(String inputFilePath, String outputFilePath, String keyFilePath) {
        try {
            toolkit.sym_encrypt_file(inputFilePath, outputFilePath, keyFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error encrypting file");
        }
    }

    public void symDecryptFile(String inputFilePath, String outputFilePath, String keyFilePath) {
        try {
            toolkit.sym_decrypt_file(inputFilePath, outputFilePath, keyFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error decrypting file");
        }
    }

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
        new App().symEncryptFile("inputMsg.txt", "encryptedOutputMsg.txt", "1.txt");
        new App().symDecryptFile("encryptedOutputMsg.txt", "decryptedOutputMsg.txt", "1.txt");
        safeFile.protectFile("inputMsg.txt", "protectedFile.json", "1.txt", "privateKey.pem");
    }
}
