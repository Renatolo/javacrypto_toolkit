package crypto.toolkit;

import java.security.Key;

public class App {

    public static void main(String[] args) {
        // new App().symEncryptFile("inputMsg.txt", "encryptedOutputMsg.txt", "1.txt");
        // new App().symDecryptFile("encryptedOutputMsg.txt", "decryptedOutputMsg.txt", "1.txt");
        safeFile.protectFile("inputMsg.txt", "protectedFile.json", "1.txt", "../rsa.priv");
        safeFile.unprotectFile("protectedFile.json", "unprotectedOutputMsg.txt", "1.txt", "../rsa_pub.der");
    }
}
