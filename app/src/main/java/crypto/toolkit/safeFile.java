package crypto.toolkit;

import crypto.toolkit.helpingTools;
import crypto.toolkit.symEncrypt;

public class safeFile {
    public static void protectFile(String inputFilePath, String outputFilePath, String keyFilePath, String privKeyPath) {
        try {
            String message = helpingTools.readFileToByteArray(inputFilePath).toString();
            String secret = symEncrypt.sym_encrypt_str(message, keyFilePath);

            String digest =helpingTools.digestMessage(message);
            String signature = asymEncrypt.asym_sign(digest, privKeyPath);

            helpingTools.writeJsonToFile(outputFilePath, secret, signature);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error encrypting file");
        }
    }
}
