package seedu.address.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import seedu.address.commons.exceptions.CryptoException;

/**
 * Encrypts readable data file into unreadable data and decrypts the unreadable data back to readable file
 */
public class FileCryptoUtil {

    private static final String KEY = "E(H+MbQeThWmZq4t";
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public static void encrypt(File inputFile, File outputFile) throws CryptoException {
        crypto(Cipher.ENCRYPT_MODE, inputFile, outputFile);
    }

    public static void decrypt(File inputFile, File outputFile) throws CryptoException {
        crypto(Cipher.DECRYPT_MODE, inputFile, outputFile);
    }

    /**
     * Encrypts/decrypts data from input file to output file using a specific key.
     * @param cipherMode can be either encrypt mode or decrypt mode.
     * @param inputFile must be valid file
     * @param outputFile must be valid file
     * @throws CryptoException if the file is invalid, key is invalid.
     */
    private static void crypto(int cipherMode, File inputFile, File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }


}
