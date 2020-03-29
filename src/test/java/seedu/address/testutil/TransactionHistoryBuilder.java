package seedu.address.testutil;

import seedu.address.model.TransactionHistory;
import seedu.address.model.transaction.Transaction;

/**
 * A utility class to help with building TransactionHistory objects.
 */
public class TransactionHistoryBuilder {

    private TransactionHistory transactionHistory;

    public TransactionHistoryBuilder() {
        transactionHistory = new TransactionHistory();
    }

    public TransactionHistoryBuilder(TransactionHistory transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    /**
     * Adds a new {@code Transaction} to the {@code TransactionHistory} that we are building.
     */
    public TransactionHistoryBuilder withTransaction(Transaction transaction) {
        transactionHistory.addTransaction(transaction);
        return this;
    }

    public TransactionHistory build() {
        return transactionHistory;
    }
}
