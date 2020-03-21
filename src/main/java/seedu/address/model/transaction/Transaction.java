package seedu.address.model.transaction;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.good.Good;

/**
 * Represents a Transaction in the transaction history.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Transaction {

    private final TransactionId transactionId;
    private final Good good;

    public Transaction(TransactionId transactionId, Good good) {
        requireAllNonNull(transactionId, good);
        this.transactionId = transactionId;
        this.good = good;
    }

    public TransactionId getId() {
        return transactionId;
    }

    public Good getGood() {
        return good;
    }

    /**
     * Returns true if both transaction have the same id.
     */
    public boolean isSameTransaction(Transaction otherTransaction) {
        if (otherTransaction == this) {
            return true;
        }

        return otherTransaction != null
                && otherTransaction.getId().equals(getId());
    }

}
