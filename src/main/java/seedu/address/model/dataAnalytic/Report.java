package seedu.address.model.dataAnalytic;

import javafx.collections.ObservableList;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.UniqueTransactionList;

import java.nio.file.Path;
import java.util.ArrayList;

public class Report {

    private static final String PATH_NAME = "Sales Report";
    private String path;
    private UniqueTransactionList list;
    private ArrayList<String> transactionDetails = new ArrayList<>();

    public Report(UniqueTransactionList list) {
        // naming using the system current time
        path = PATH_NAME+" #"+System.currentTimeMillis()+".pdf";
        this.list = list;
        writeInformation();
    }

    private void writeInformation() {
        list.forEach(transaction -> transactionDetails.add(transaction.toString()));
    }

    public ArrayList<String> getInformation() {
        return transactionDetails;
    }

    public String getPath() {
        return path;
    }
}
