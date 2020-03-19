package seedu.address.model.transaction;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.UUID;

import seedu.address.model.good.Good;

/**
 * Represents a Transaction in the transaction history.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Transaction {

    private final UUID id;
    private final Good good;

    public Transaction(UUID id, Good good) {
        requireAllNonNull(id, good);
        this.id = id;
        this.good = good;
    }

    public UUID getId() {
        return id;
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
