package seedu.address.model.analytic;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.commands.SellCommand;
import seedu.address.model.transaction.BuyTransaction;
import seedu.address.model.transaction.SellTransaction;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.UniqueTransactionList;

/**
 * A class to store the information of the transaction details, and use for report generation.
 */
public class Report {

    public static final int STANDARD_STRING_LENGTH = 20;
    public static final String SPACING = "   ";
    private static final String PATH_NAME = "Sales Report";
    private static final String SUMMARY = "Total Cost: %1$s " + SPACING
            + "Total Revenue: %2$s" + SPACING
            + "Total profit: %3$s";
    private static final ArrayList<String> EMPTY_LINE = new ArrayList<>();
    private static long totalCost;
    private static long totalRevenue;

    private final String category = formatStringLength("Type") + formatStringLength("Good")
            + formatStringLength("Quantity") + formatStringLength("Price") + formatStringLength("Supplier");
    private String path;
    private UniqueTransactionList list;
    private ArrayList<ArrayList<String>> transactionDetails = new ArrayList<>();

    public Report(UniqueTransactionList list) {
        totalCost = 0;
        totalRevenue = 0;
        // naming using the system current time
        path = PATH_NAME + " #" + System.currentTimeMillis() + ".pdf";
        this.list = list;
        writeInformation();
    }

    /**
     * Writes the data in correct format.
     */
    private void writeInformation() {
        transactionDetails.add(new ArrayList<>(List.of(PATH_NAME)));
        transactionDetails.add(EMPTY_LINE);
        transactionDetails.add(new ArrayList<>(List.of(category)));
        transactionDetails.add(EMPTY_LINE);
        list.forEach(transaction -> transactionDetails.add(formatTransaction(transaction)));
        transactionDetails.add(EMPTY_LINE);
        transactionDetails.add(new ArrayList<>(List.of(String.format(SUMMARY, formatValue(totalCost),
                formatValue(totalRevenue), formatValue(totalRevenue - totalCost)))));
    }

    public String getPath() {
        return path;
    }

    public ArrayList<ArrayList<String>> getInformation() {
        return transactionDetails;
    }

    /**
     * obtain the transaction detail and convert to proper format.
     * @param transaction details of the transaction.
     * @return the array of string that contain valid details.
     */
    private ArrayList<String> formatTransaction(Transaction transaction) {
        ArrayList<String> details = new ArrayList<>();

        String good = transaction.getGood().getGoodName().fullGoodName;
        int quantity = transaction.getGood().getGoodQuantity().goodQuantity;

        if (transaction instanceof BuyTransaction) {
            details.add(formatStringLength(BuyCommand.COMMAND_WORD));
            details.add(formatStringLength(good));
            details.add(formatStringLength("" + quantity));
            details.add(formatStringLength(((BuyTransaction) transaction).getBuyPrice().getValue()));
            details.add(formatStringLength(((BuyTransaction) transaction).getSupplier().getName().fullName));

            long price = ((BuyTransaction) transaction).getBuyPrice().getCentValue();
            totalCost += price * quantity;
        } else {
            details.add(formatStringLength(SellCommand.COMMAND_WORD));
            details.add(formatStringLength(good));
            details.add(formatStringLength("" + quantity));
            details.add(formatStringLength(((SellTransaction) transaction).getSellPrice().getValue()));
            details.add(formatStringLength("---"));
            long price = ((SellTransaction) transaction).getSellPrice().getCentValue();
            totalRevenue += price * quantity;
        }
        return details;
    }

    private String formatValue(long value) {
        return String.format("%d.%02d", value / 100, value % 100);
    }

    private String formatStringLength(String string) {
        return String.format("%1$" + STANDARD_STRING_LENGTH + "s", string);
    }

}
