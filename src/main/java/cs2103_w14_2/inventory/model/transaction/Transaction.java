package cs2103_w14_2.inventory.model.transaction;

import static cs2103_w14_2.inventory.commons.util.CollectionUtil.requireAllNonNull;

import cs2103_w14_2.inventory.commons.util.CollectionUtil;
import cs2103_w14_2.inventory.model.good.Good;

/**
 * Represents a Transaction in the transaction history.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Transaction {

    private final TransactionId transactionId;
    private final Good good;

    public Transaction(TransactionId transactionId, Good good) {
        CollectionUtil.requireAllNonNull(transactionId, good);
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
