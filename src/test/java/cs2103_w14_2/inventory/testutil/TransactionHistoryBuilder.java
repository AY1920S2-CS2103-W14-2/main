package cs2103_w14_2.inventory.testutil;

import cs2103_w14_2.inventory.model.TransactionHistory;
import cs2103_w14_2.inventory.model.transaction.Transaction;

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
