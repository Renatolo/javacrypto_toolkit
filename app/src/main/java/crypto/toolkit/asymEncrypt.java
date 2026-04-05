package crypto.toolkit;

import java.security.PrivateKey;
import java.security.PublicKey;

public class asymEncryptTools {
    static final String ASYM_ALGORITHM = "RSA";
    static final String ASYM_CIPHER = "RSA/ECB/PKCS1Padding";

    public static String asym_sign(String plaintext, String privateKeyPath) {
        // read the private key
        PrivateKey priv = read_asym_private_key(privateKeyPath);

        // sign the data
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(priv);
        signature.update(plaintext.getBytes());
        byte[] signedData = signature.sign();

        // return data as hex string
        return helpingTools.toHex(signedData);
    }

    public static boolean asym_verify(String plaintext, String signatureHex, String publicKeyPath) {
        // read the public key
        PublicKey pub = read_asym_public_key(publicKeyPath);

        // verify the signature
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(pub);
        signature.update(plaintext.getBytes());
        byte[] signatureBytes = helpingTools.hexStringToByteArray(signatureHex);

        return signature.verify(signatureBytes);
    }

    public static String asym_encrypt_str(String plaintext, String publicKeyPath) {
        PublicKey pub = read_asym_public_key(publicKeyPath);

        Cipher cipher = Cipher.getInstance(ASYM_CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, pub);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());

        return helpingTools.toHex(encryptedBytes);
    }

    public static String asym_decrypt_str(String encryptedHex, String privateKeyPath) {
        PrivateKey priv = read_asym_private_key(privateKeyPath);

        Cipher cipher = Cipher.getInstance(ASYM_CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, priv);
        byte[] encryptedBytes = helpingTools.hexStringToByteArray(encryptedHex);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes);
    }

    public static PublicKey read_asym_public_key(String keyFilePath) {
        byte[] pubEncoded = helpingTools.readFileToByteArray(keyFilePath);

        X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubEncoded);
		KeyFactory keyFacPub = KeyFactory.getInstance(ASYM_ALGO);
		PublicKey pub = keyFacPub.generatePublic(pubSpec);
        return pub;
    }

    public static PrivateKey read_asym_private_key(String keyFilePath) {
        byte[] privEncoded = helpingTools.readFileToByteArray(keyFilePath);

        PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privEncoded);
        KeyFactory keyFacPriv = KeyFactory.getInstance(ASYM_ALGO);
        PrivateKey priv = keyFacPriv.generatePrivate(privSpec);
        return priv;
    }
}
