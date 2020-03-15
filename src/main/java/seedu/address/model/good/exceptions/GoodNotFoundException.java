package seedu.address.model.good.exceptions;

/**
 * Signals that the operation is unable to find the specified good.
 */
public class GoodNotFoundException extends RuntimeException {
    public GoodNotFoundException() {
        super("Operation can not find the good.");
    }
}
