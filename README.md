# CryptoToolkit (v1.0)

A lightweight Java-based CLI for symmetric and asymmetric cryptography, built with Gradle and picocli. This tool allows for secure file protection using AES-256 and RSA signature verification.

## 🚀 Features

* **Symmetric Encryption:** Generate AES-128/256 keys and encrypt data.
* **Asymmetric Security:** Generate RSA key pairs (DER format) for signing and verification.
* **File Protection:** One-command encryption and signing to ensure data integrity.
* **CLI Powered:** Built-in help menus and command-to-command navigation.

---

## 🛠️ Installation (Option A: Local CLI)

Follow these steps to build the tool and add it as a global command on your system.

### 1. Build the Distribution
Run the Gradle `installDist` task. This compiles your code and generates the necessary shell scripts.
```bash
./gradlew installDist
```

### 2. Verify the Build
Navigate to the installation directory to ensure the binaries were created successfully:
```bash
ls app/build/install/app/bin/
```
*You should see a script named `cryptoTK`.*

### 3. Add to System Path (Linux/WSL)
To use `cryptoTK` from any folder without typing the full path, create a symbolic link in your local bin directory:
```bash
sudo ln -s $(pwd)/app/build/install/app/bin/cryptoTK /usr/local/bin/cryptoTK
```

### 4. Test the Installation
Open a new terminal session and type:
```bash
cryptoTK --help
```

---

## 📖 Usage Guide

### Generate Keys
**Symmetric (AES):**
```bash
cryptoTK gen-sym-key my_secret.key
```

**Asymmetric (RSA Pair):**
```bash
cryptoTK gen-asym-key my_rsa_key
```

### Protect and Unprotect Files
**Encrypt and Sign:**
```bash
cryptoTK protect-file input.txt output.enc my_secret.key my_rsa_key
```

**Verify and Decrypt:**
```bash
cryptoTK unprotect-file output.enc decrypted.txt my_secret.key my_rsa_key.pub
```

---

## ⚠️ Important Note on Key Formats
This tool requires  either **DER (binary)** or **PEM (text)** format for RSA keys.

> [!WARNING]
> ssh-keygen generates the rsa public key in a **ssh** specific format not accepted by ctk 

Another way to create the rsa keys is demonstrated in the following example:

```
# Generating the key-pair (PEM format)
openssl genrsa -out [filename] 2048


# Extracting the public key (PEM format)
openssl rsa -in [private-key_filename] -pubout -out [filename].pub
```

---

## 🛠️ Development

* **Language:** Java 25
* **Build System:** Gradle (Kotlin DSL)
* **Dependencies:** Google Gson, picocli

To clean the project and rebuild from scratch:
```bash
./gradlew clean installDist
```

---

### Pro-Tip for WSL Users
If you ever get a **Permission Denied** error when running the tool, ensure the script is executable by running:
`chmod +x app/build/install/app/bin/cryptoTK`
