package seedu.address.commons.exceptions;

/**
 * Represents an error during encryption and decryption of data
 */
public class CryptoException extends Exception {
    public CryptoException() {
    }

    public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
