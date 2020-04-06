package seedu.address.model.dataAnalytic;

import static java.util.Objects.requireNonNull;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.encoding.WinAnsiEncoding;
import seedu.address.model.dataAnalytic.exceptions.PdfIoException;
import seedu.address.model.transaction.UniqueTransactionList;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 * A class to generate data insight.
 */
public class DataAnalyticManager {

    public static final String REPORT_DIRECTORY = "reports";
    private File directory;

    public DataAnalyticManager() {
        try {
            directory = new File(REPORT_DIRECTORY);
            if (!directory.exists()) {
                directory.createNewFile();
            }
        } catch (IOException ex) {
            throw new PdfIoException();
        }
    }

    /**
     * generates sales report in PDF format, and saves in the specific folder for reference.
     *
     * @param transactionList contains the details of transactions
     */
    public void generateReport(UniqueTransactionList transactionList) {
        requireNonNull(transactionList);

        // set up PDF document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // generate report information
        Report report = new Report(transactionList);

        try {
            // set up PDF format and style
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 700);

            // write in the sales information
            for (int i = 0; i < report.getInformation().size(); i++) {
                contentStream.showText(remove(report.getInformation().get(i)));
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            // save report in the specified directory
            document.save(System.getProperty("user.dir") + System.getProperty("file.separator") +
                    REPORT_DIRECTORY + System.getProperty("file.separator") + report.getPath());
            document.close();

            // open pdf file in the directory
            File myFile = new File(directory, report.getPath());
            Desktop.getDesktop().open(myFile);

        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
            throw new PdfIoException();
        }
    }

    private String remove(String test) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < test.length(); i++) {
            if (WinAnsiEncoding.INSTANCE.contains(test.charAt(i))) {
                b.append(test.charAt(i));
            }
        }
        return b.toString();
    }


}
