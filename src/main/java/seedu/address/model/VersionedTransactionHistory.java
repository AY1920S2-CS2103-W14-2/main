package seedu.address.model;

import seedu.address.model.transaction.Transaction;
import seedu.address.model.version.LinearHistory;
import seedu.address.model.version.StateNotFoundException;
import seedu.address.model.version.Version;

/**
 * A {@code TransactionHistory} that keeps track of its history. Snapshots of its state are done based on external
 * commands.
 */
public class VersionedTransactionHistory extends TransactionHistory implements Version<TransactionHistory> {
    private Version<TransactionHistory> version;

    public VersionedTransactionHistory() {
        version = new LinearHistory<>(new TransactionHistory());
    }

    /**
     * Creates a VersionedTransactionHistory with an initial state containing the {@code Transaction}s
     * in the {@code toBeCopied}.
     */
    public VersionedTransactionHistory(ReadOnlyList<Transaction> toBeCopied) {
        version = new LinearHistory<>(new TransactionHistory(toBeCopied));
        updateDisplayedTransactions();
    }

    //=========== List Overwrite Operations =========================================================================

    /**
     * Resets the existing data of this {@code VersionedTransactionHistory} with {@code newData}.
     * Resets the history to an empty state as well.
     */
    public void resetData(ReadOnlyList<Transaction> newData) {
        getCurrentState().resetData(newData);
        updateDisplayedTransactions();
    }

    //=========== Transaction-Level Operations =========================================================================

    /**
     * Returns true if a transaction with the same identity as {@code transaction} exists in the current state.
     */
    public boolean hasTransaction(Transaction transaction) {
        return getCurrentState().hasTransaction(transaction);
    }

    /**
     * Adds a transaction to the current state.
     * The transaction must not already exist in the current state.
     */
    public void addTransaction(Transaction p) {
        getCurrentState().addTransaction(p);
        updateDisplayedTransactions();
    }

    /**
     * Removes {@code key} from the current state.
     * {@code key} must exist in the current state.
     */
    public void removeTransaction(Transaction key) {
        getCurrentState().removeTransaction(key);
        updateDisplayedTransactions();
    }

    //=========== Versioning Methods =========================================================================

    @Override
    public void commit() {
        version.commit();
    }

    @Override
    public void undo() throws StateNotFoundException {
        version.undo();
        updateDisplayedTransactions();
    }

    @Override
    public void redo() throws StateNotFoundException {
        version.redo();
        updateDisplayedTransactions();
    }

    @Override
    public TransactionHistory getCurrentState() {
        return version.getCurrentState();
    }

    //=========== Util Methods =========================================================================

    /**
     * Updates the list of suppliers to be shown in the UI.
     */
    private void updateDisplayedTransactions() {
        super.resetData(getCurrentState());
    }
}
