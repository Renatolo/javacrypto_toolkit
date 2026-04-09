package crypto.toolkit;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.Key;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class helpingTools {

    private final static String DIGEST_ALGO = "SHA-256";

    public static String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static String digestMessage(String message) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(DIGEST_ALGO);
        byte[] hash = digest.digest(message.getBytes("UTF-8"));
        return toHex(hash);
    }

    // File I/O utilities

    public static byte[] readFileToByteArray(String filePath) throws Exception {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] data = new byte[fis.available()];
            fis.read(data);
            return data;
        }
    }

    public static void writeByteArrayToFile(String filePath, byte[] data) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data);
        }
    }

    // Json File I/O utilities

    public static String[] readJsonFile(String filePath) throws Exception {
        try (FileReader reader = new FileReader(filePath)) {
            // Use the static parseReader instead of 'new JsonParser()'
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            
            return new String[]{
                json.get("secret").getAsString(),
                json.get("signature").getAsString()
            };
        }
    }

    public static void writeJsonToFile(String filePath, String secret, String signature) throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("secret", secret);
        json.addProperty("signature", signature);

        // Using try-with-resources to ensure the file closes properly
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(json, writer);
        }
    }

}
