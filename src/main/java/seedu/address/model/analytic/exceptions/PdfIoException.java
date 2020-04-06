package seedu.address.model.analytic.exceptions;

/**
 * Signals that the creating of PDF file has IO error.
 */
public class PdfIoException extends RuntimeException {
    public PdfIoException() {
        super("Error in generating PDF file");
    }
}
