package crypto.toolkit;

import java.security.Key;

public class App {
    public String getGreeting() {
        try {
            Key key = toolkit.read_sym_key("1.txt");
            toolkit.sym_encrypt_str("Hello, World!", key);
            return "Encryption successful";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error reading key";
        }
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
