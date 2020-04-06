package seedu.address.model.dataAnalytic;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

import seedu.address.model.dataAnalytic.exceptions.PdfIoException;

import seedu.address.model.transaction.UniqueTransactionList;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;

import static java.util.Objects.requireNonNull;

public class DataAnalyticManager {

    public static final String REPORT_DIRECTORY = "reports";
    private File directory;

    public DataAnalyticManager() {
        try {
            directory = new File(REPORT_DIRECTORY);
            if (!directory.exists()) {
                directory.createNewFile();
            }
        }catch (IOException ex){
            throw new PdfIoException();
        }
    }

    public void generateReport(UniqueTransactionList transactionList){
        requireNonNull(transactionList);
        // todo: need to close the window before generating
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        Report report = new Report(transactionList);
        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 700);

            for(int i=0;i<report.getInformation().size();i++){
                contentStream.showText(report.getInformation().get(i));
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            document.save(System.getProperty("user.dir") + System.getProperty("file.separator")+
                    REPORT_DIRECTORY+System.getProperty("file.separator")+report.getPath());
            document.close();

            // todo: need to ensure mac and window both works
            // todo: check for duplicate file creation
            File myFile = new File(directory, report.getPath());
            Desktop.getDesktop().open(myFile);
        }catch (IOException ex){
            System.out.println(ex.getStackTrace());
            throw new PdfIoException();
        }
    }



}
