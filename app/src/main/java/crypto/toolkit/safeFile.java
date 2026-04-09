package crypto.toolkit;

import crypto.toolkit.helpingTools;
import crypto.toolkit.symEncrypt;

public class safeFile {
    public static void protectFile(String inputFilePath, String outputFilePath, String keyFilePath, String privKeyPath) {
        try {
            String message = new String(helpingTools.readFileToByteArray(inputFilePath), "UTF-8");
            String secret = symEncrypt.sym_encrypt_str(message, keyFilePath);

            String digest =helpingTools.digestMessage(message);
            String signature = asymEncrypt.asym_sign(digest, privKeyPath);

            helpingTools.writeJsonToFile(outputFilePath, secret, signature);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error encrypting file");
        }
    }

    public static void unprotectFile(String inputFilePath, String outputFilePath, String keyFilePath, String pubKeyPath) {
        try {
            String[] jsonData = helpingTools.readJsonFile(inputFilePath);
            String secret = jsonData[0];
            String signature = jsonData[1];

            String message = symEncrypt.sym_decrypt_str(secret, keyFilePath);
            String digest = helpingTools.digestMessage(message);

            boolean isVerified = asymEncrypt.asym_verify(digest, signature, pubKeyPath);
            if (isVerified) {
                helpingTools.writeByteArrayToFile(outputFilePath, message.getBytes());
                System.out.println("File decrypted and signature verified successfully");
            } else {
                System.out.println("[WARNING]Signature verification failed. File may be tampered.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error decrypting file");
        }
    }
}
