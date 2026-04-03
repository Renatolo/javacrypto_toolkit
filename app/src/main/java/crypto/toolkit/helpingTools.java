package crypto.toolkit;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class helpingTools {
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

    public static byte[] readFileToByteArray(String filePath) throws Exception {
        System.out.println("Reading key from file " + filePath + " ...");
		FileInputStream fis = new FileInputStream(filePath);
		byte[] encoded = new byte[fis.available()];
		fis.read(encoded);
		fis.close();
        return encoded;
    }

    public static void writeByteArrayToFile(String filePath, byte[] data) throws Exception {
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(data);
        fos.close();
    }
}
