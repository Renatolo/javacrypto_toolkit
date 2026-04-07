package crypto.toolkit;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.KeyPair;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class asymEncrypt {
    static final String ASYM_ALGORITHM = "RSA";
    static final String ASYM_CIPHER = "RSA/ECB/PKCS1Padding";

    public static void generate_asym_key_pair(String keyFilePath) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair keys = keyGen.generateKeyPair();

            byte[] privKeyEncoded = keys.getPrivate().getEncoded();
            byte[] pubKey = keys.getPublic().getEncoded();

            helpingTools.writeByteArrayToFile(keyFilePath + ".priv", privKeyEncoded);
            helpingTools.writeByteArrayToFile(keyFilePath + ".pub", pubKey);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error generating asymmetric key pair");
        }
    }

    public static String asym_sign(String plaintext, String privateKeyPath) {
        // read the private key
        PrivateKey priv = read_asym_private_key(privateKeyPath);

        // sign the data
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(priv);
            signature.update(plaintext.getBytes());
            byte[] signedData = signature.sign();

            // return data as hex string
            return helpingTools.toHex(signedData);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error signing data");
            return null;
        }
    }

    public static boolean asym_verify(String plaintext, String signatureHex, String publicKeyPath) {
        // read the public key
        PublicKey pub = read_asym_public_key(publicKeyPath);

        // verify the signature
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(pub);
            signature.update(plaintext.getBytes());
            byte[] signatureBytes = helpingTools.hexStringToByteArray(signatureHex);
            return signature.verify(signatureBytes);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error verifying signature");
            return false;
        }
    }

    public static String asym_encrypt_str(String plaintext, String publicKeyPath) {
        PublicKey pub = read_asym_public_key(publicKeyPath);

        try {
            Cipher cipher = Cipher.getInstance(ASYM_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, pub);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());

            return helpingTools.toHex(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error encrypting data");
            return null;
        }
    }

    public static String asym_decrypt_str(String encryptedHex, String privateKeyPath) {
        PrivateKey priv = read_asym_private_key(privateKeyPath);

        try {
            Cipher cipher = Cipher.getInstance(ASYM_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, priv);
            byte[] encryptedBytes = helpingTools.hexStringToByteArray(encryptedHex);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error decrypting data");
            return null;
        }
    }

    public static PublicKey read_asym_public_key(String keyFilePath) {

        try {
            byte[] pubEncoded = helpingTools.readFileToByteArray(keyFilePath);

            // Reading from a PEM file
            if (pubEncoded.length > 0 && pubEncoded[0] == 0x2D) {
                String publicKeyPEM = new String(pubEncoded, "UTF-8")
                        .replace("-----BEGIN PUBLIC KEY-----", "")
                        .replace("-----END PUBLIC KEY-----", "")
                        .replaceAll("\\s", "");;
                pubEncoded = java.util.Base64.getDecoder().decode(publicKeyPEM);
            }

            X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubEncoded);
            KeyFactory keyFacPub = KeyFactory.getInstance(ASYM_ALGORITHM);
            PublicKey pub = keyFacPub.generatePublic(pubSpec);
            return pub;
        } catch (Exception e) {
            System.out.println("public key must be in X.509 format (PEM or DER)");
            return null;
        }
    }

    public static PrivateKey read_asym_private_key(String keyFilePath) {

        try {
            byte[] privEncoded = helpingTools.readFileToByteArray(keyFilePath);

            // Reading from a PEM file
            if (privEncoded.length > 0 && privEncoded[0] == 0x2D) {
                String privateKeyPEM = new String(privEncoded, "UTF-8")
                        .replace("-----BEGIN PRIVATE KEY-----", "")
                        .replace("-----END PRIVATE KEY-----", "")
                        .replaceAll("\\s", "");;
                privEncoded = java.util.Base64.getDecoder().decode(privateKeyPEM);
            }

            PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privEncoded);
            KeyFactory keyFacPriv = KeyFactory.getInstance(ASYM_ALGORITHM);
            PrivateKey priv = keyFacPriv.generatePrivate(privSpec);
            return priv;
        } catch (Exception e) {
            System.out.println("private key must be in PKCS#8 format (PEM or DER)");
            return null;
        }
    }
}
