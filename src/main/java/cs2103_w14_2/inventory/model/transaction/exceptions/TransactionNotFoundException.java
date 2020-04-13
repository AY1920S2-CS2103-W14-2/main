package cs2103_w14_2.inventory.model.transaction.exceptions;

/**
 * Signals that the operation is unable to find the specified transaction.
 */
public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
        super("Operation can not find the specific good.");
    }
}
