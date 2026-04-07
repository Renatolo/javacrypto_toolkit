package crypto.toolkit;

import java.security.Key;

public class App {

    private static final String helpText = "Usage: java -jar CryptoToolkit.jar <command> [args]\n" +
            "Commands:\n" +
            "  help - Show this help message\n" +
            "  gen-sym-key <keyFilePath> - Generate a symmetric key and save to file\n" +
            "  gen-asym-key <keyFilePath> - Generate an asymmetric key pair and save to files\n" +
            "  protect-file <inputFilePath> <outputFilePath> <keyFilePath> <privKeyPath> - Encrypt a file and sign it\n" +
            "  unprotect-file <inputFilePath> <outputFilePath> <keyFilePath> <pubKeyPath> - Decrypt a file and verify its signature";

    public static void main(String[] args) {
        System.out.println(args.length + " args provided");
        System.out.println("args: " + String.join(", ", args));

        if (args.length < 1) {
            System.out.println("No command provided. Exiting.");
            return;
        }

        String command = args[0];
        switch(command) {
            case "help":
                System.out.println(helpText);
                break;
            case "gen-sym-key":
                if (args.length < 2) {
                    System.out.println("Usage: gen-sym-key <keyFilePath>");
                    return;
                }
                symEncrypt.generate_sym_key(args[1]);
                break;
            case "gen-asym-key":
                if (args.length < 2) {
                    System.out.println("Usage: gen-asym-key <keyFilePath>");
                    return;
                }
                asymEncrypt.generate_asym_key_pair(args[1]);
                break;
            case "protect-file":
                if (args.length < 5) {
                    System.out.println("Usage: protect-file <inputFilePath> <outputFilePath> <keyFilePath> <privKeyPath>");
                    return;
                }
                safeFile.protectFile(args[1], args[2], args[3], args[4]);
                break;
            case "unprotect-file":
                if (args.length < 5) {
                    System.out.println("Usage: unprotect-file <inputFilePath> <outputFilePath> <keyFilePath> <pubKeyPath>");
                    return;
                }
                safeFile.unprotectFile(args[1], args[2], args[3], args[4]);
                break;
            default:
                System.out.println("Unknown command: " + command);
                System.out.println("Use 'help' command for usage instructions.");
        }
    }
}
