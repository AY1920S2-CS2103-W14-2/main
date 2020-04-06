package seedu.address.model.dataAnalytic.exceptions;

public class PdfIoException extends RuntimeException {
    public PdfIoException() {
        super("Error in generating PDF file");
    }
}
