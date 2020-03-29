package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.transaction.BuyTransaction;
import seedu.address.model.transaction.Transaction;

/**
 * An UI component that displays information of a {@code Transaction}.
 */
public class TransactionCard extends UiPart<Region> {

    private static final String FXML = "TransactionCard.fxml";

    public final Transaction transaction;

    @FXML
    private HBox transactionPane;
    @FXML
    private Label transactionType;
    @FXML
    private Label id;
    @FXML
    private Label transactionQuantity;
    @FXML
    private Label transactionGood;
    @FXML
    private Label transactionPersonName;

    public TransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        id.setText(displayedIndex + ". ");
        if (transaction instanceof BuyTransaction) {
            transactionType.setText("Buy from: ");
            transactionPersonName.setText(((BuyTransaction) transaction).getSupplier().getName().toString());
        } else {
            transactionType.setText("Sell");
            transactionPersonName.setText("");
        }
        transactionGood.setText(transaction.getGood().getGoodName().toString());
        transactionQuantity.setText(transaction.getGood().getGoodQuantity().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionCard)) {
            return false;
        }

        // state check
        TransactionCard information = (TransactionCard) other;
        return id.getText().equals(information.id.getText())
                && transaction.equals(information.transaction);
    }

}
