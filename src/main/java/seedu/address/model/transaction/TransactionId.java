package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Transaction's id in the transaction history.
 * Guarantees: immutable; is valid as declared in {@link #isValidTransactionId(String)}
 */
public class TransactionId {

    public static final String MESSAGE_CONSTRAINTS =
            "Transaction id should only contain numbers and dashes, and it can not be blank.";

    // [a-fA-F0-9] == (a-f or A-F or 0-9)
    // 8 of [a-fA-F0-9] follow by 1 dash, then 4 of [a-fA-F0-9] follow by a dash...
    public static final String VALIDATION_REGEX =
            "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";
    private String transactionId;

    /**
     * Constructs a {@code transactionId}.
     *
     * @param transactionId A valid id number.
     */
    public TransactionId(String transactionId) {
        requireNonNull(transactionId);
        checkArgument(isValidTransactionId(transactionId), MESSAGE_CONSTRAINTS);
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Returns true if a given string is a valid transaction id.
     */
    public static boolean isValidTransactionId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return transactionId;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionId // instanceof handles nulls
                && transactionId.equals(((TransactionId) other).transactionId)); // state check
    }

    @Override
    public int hashCode() {
        return transactionId.hashCode();
    }

}
