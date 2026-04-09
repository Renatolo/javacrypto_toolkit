package crypto.toolkit;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "ctk", mixinStandardHelpOptions = true) 
public class App implements Runnable {

    @Override
    public void run() {
        // Default action when no subcommand is provided
    }

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new App());
        
        // Add subcommands manually here
        cmd.addSubcommand("gen-sym-key", new GenSymKey());
        cmd.addSubcommand("gen-asym-key", new GenAsymKey());
        cmd.addSubcommand("protect-file", new ProtectFile());
        cmd.addSubcommand("unprotect-file", new UnprotectFile());
        
        System.exit(cmd.execute(args));
    }

    @Command(name = "gen-sym-key", description = "Generate a symmetric key and save to file")
    public static class GenSymKey implements Runnable {
        @Parameters(index = "0", description = "Path to the key file")
        private String keyFilePath;

        @Override
        public void run() {
            symEncrypt.generate_sym_key(keyFilePath);
        }
    }

    @Command(name = "gen-asym-key", description = "Generate an asymmetric key pair and save to files")
    public static class GenAsymKey implements Runnable {
        @Parameters(index = "0", description = "Path to the key file")
        private String keyFilePath;

        @Override
        public void run() {
            asymEncrypt.generate_asym_key_pair(keyFilePath);
        }
    }

    @Command(name = "protect-file", description = "Encrypt a file and sign it")
    public static class ProtectFile implements Runnable {
        @Parameters(index = "0", description = "Input file path")
        private String inputFilePath;

        @Parameters(index = "1", description = "Output file path")
        private String outputFilePath;

        @Parameters(index = "2", description = "Key file path")
        private String keyFilePath;

        @Parameters(index = "3", description = "Private key path")
        private String privKeyPath;

        @Override
        public void run() {
            safeFile.protectFile(inputFilePath, outputFilePath, keyFilePath, privKeyPath);
        }
    }

    @Command(name = "unprotect-file", description = "Decrypt a file and verify its signature")
    public static class UnprotectFile implements Runnable {
        @Parameters(index = "0", description = "Input file path")
        private String inputFilePath;

        @Parameters(index = "1", description = "Output file path")
        private String outputFilePath;

        @Parameters(index = "2", description = "Key file path")
        private String keyFilePath;

        @Parameters(index = "3", description = "Public key path")
        private String pubKeyPath;

        @Override
        public void run() {
            safeFile.unprotectFile(inputFilePath, outputFilePath, keyFilePath, pubKeyPath);
        }
    }
}
