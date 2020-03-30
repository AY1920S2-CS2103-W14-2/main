package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.UniqueTransactionList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameTransaction comparison)
 */
public class TransactionHistory implements ReadOnlyList<Transaction> {

    private final UniqueTransactionList transactions;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        transactions = new UniqueTransactionList();
    }

    public TransactionHistory() {
    }

    /**
     * Creates an TransactionHistory using the Transactions in the {@code toBeCopied}
     */
    public TransactionHistory(ReadOnlyList<Transaction> toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //=========== List Overwrite Operations =========================================================================

    /**
     * Replaces the contents of the transaction list with {@code transactions}.
     * {@code transactions} must not contain duplicate transactions.
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions.setTransactions(transactions);
    }

    /**
     * Resets the existing data of this {@code TransactionHistory} with {@code newData}.
     */
    public void resetData(ReadOnlyList<Transaction> newData) {
        requireNonNull(newData);

        setTransactions(newData.getReadOnlyList());
    }

    //=========== Transaction-Level Operations =========================================================================

    /**
     * Returns true if a transaction with the same identity as {@code transaction} exists in the transaction history.
     */
    public boolean hasTransaction(Transaction transaction) {
        requireNonNull(transaction);
        return transactions.contains(transaction);
    }

    /**
     * Adds a transaction to the transaction history.
     * The transaction must not already exist in the transaction history.
     */
    public void addTransaction(Transaction p) {
        transactions.add(p);
    }

    /**
     * Removes {@code key} from this {@code TransactionHistory}.
     * {@code key} must exist in the transaction history.
     */
    public void removeTransaction(Transaction key) {
        transactions.remove(key);
    }

    protected UniqueTransactionList getTransactions() {
        return transactions;
    }

    //=========== Util Methods =========================================================================

    @Override
    public String toString() {
        return transactions.asUnmodifiableObservableList().size() + " transactions";
        // TODO: refine later
    }

    @Override
    public ObservableList<Transaction> getReadOnlyList() {
        return transactions.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TransactionHistory // instanceof handles nulls
            && transactions.equals(((TransactionHistory) other).transactions));
    }

    @Override
    public int hashCode() {
        return transactions.hashCode();
    }

}
