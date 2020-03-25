package seedu.address.model.supplier.exceptions;

/**
 * Signals that the operation is unable to find the specified supplier.
 */
public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException() {
        super("supplier could not be found");
    }
}
