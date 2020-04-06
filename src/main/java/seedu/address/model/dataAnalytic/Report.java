package seedu.address.model.dataAnalytic;

import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.commands.SellCommand;
import seedu.address.model.transaction.BuyTransaction;
import seedu.address.model.transaction.SellTransaction;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.UniqueTransactionList;

import java.util.ArrayList;

/**
 * A class to store the information of the transaction details, and use for report generation.
 */
public class Report {

    private static final String SPACING = "   ";
    private static final String PATH_NAME = "Sales Report";
    private static final String SUMMARY = "Total Cost: %1$s " + SPACING
            + "Total Revenue: %2$s" + SPACING
            + "Total profit: %3$s";

    private String path;
    private UniqueTransactionList list;
    private ArrayList<String> transactionDetails = new ArrayList<>();
    private static long totalCost;
    private static long totalRevenue;

    public Report(UniqueTransactionList list) {
        totalCost = 0;
        totalRevenue = 0;
        // naming using the system current time
        path = PATH_NAME + " #" + System.currentTimeMillis() + ".pdf";
        this.list = list;
        writeInformation();
    }

    private void writeInformation() {
        transactionDetails.add(PATH_NAME);
        list.forEach(transaction -> transactionDetails.add(formatTransaction(transaction)));
        transactionDetails.add(String.format(SUMMARY, formatValue(totalCost), formatValue(totalRevenue), formatValue(totalRevenue - totalCost)));
    }

    public String getPath() {
        return path;
    }

    public ArrayList<String> getInformation() {
        return transactionDetails;
    }

    private String formatTransaction(Transaction transaction) {
        final StringBuilder builder = new StringBuilder();
        String good = transaction.getGood().getGoodName().fullGoodName;
        int quantity = transaction.getGood().getGoodQuantity().goodQuantity;
        if (transaction instanceof BuyTransaction) {
            long price = ((BuyTransaction) transaction).getBuyPrice().getCentValue();
            builder.append(BuyCommand.COMMAND_WORD + SPACING);
            builder.append(good + SPACING);
            builder.append(quantity + SPACING);
            builder.append(((BuyTransaction) transaction).getBuyPrice().getValue() + SPACING);
            builder.append(((BuyTransaction) transaction).getSupplier().getName() + SPACING);
            totalCost += price * quantity;
            System.out.println(totalCost + "=" + price + "*" + quantity);
        } else {
            long price = ((SellTransaction) transaction).getSellPrice().getCentValue();
            builder.append(SellCommand.COMMAND_WORD + SPACING);
            builder.append(transaction.getGood().getGoodName() + SPACING);
            builder.append(transaction.getGood().getGoodQuantity() + SPACING);
            builder.append(((SellTransaction) transaction).getSellPrice().getValue() + SPACING);
            totalRevenue += price * quantity;
            System.out.println(totalRevenue + "=" + price + "*" + quantity);
        }
        return builder.toString();
    }

    private String formatValue(long value) {
        return String.format("%d.%02d", value / 100, value % 100);
    }

}
