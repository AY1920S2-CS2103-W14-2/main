package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.transaction.Transaction;

/**
 * Unmodifiable view of a transaction history
 */
public interface ReadOnlyTransactionHistory {

    /**
     * Returns an unmodifiable view of the goods list.
     * This list will not contain any duplicate goods.
     */
    ObservableList<Transaction> getTransactionList();

}
