package cs2103_w14_2.inventory.model;

import cs2103_w14_2.inventory.model.transaction.Transaction;
import cs2103_w14_2.inventory.model.version.LinearHistory;
import cs2103_w14_2.inventory.model.version.StateNotFoundException;
import cs2103_w14_2.inventory.model.version.Version;
import cs2103_w14_2.inventory.model.version.Versionable;

/**
 * A {@code TransactionHistory} that keeps track of its history. Snapshots of its state are done based on external
 * commands.
 */
public class VersionedTransactionHistory extends TransactionHistory implements Versionable {
    private Version<TransactionHistory> version;

    /**
     * Creates a VersionedTransactionHistory with an empty initial state.
     */
    public VersionedTransactionHistory() {
        super();
        version = new LinearHistory<>(new TransactionHistory());
    }

    /**
     * Creates a VersionedTransactionHistory with an initial state containing the list of {@code Transaction}
     * in the {@code toBeCopied}.
     */
    public VersionedTransactionHistory(ReadOnlyList<Transaction> toBeCopied) {
        super();
        version = new LinearHistory<>(new TransactionHistory(toBeCopied));
        updateDisplayedTransactions();
    }

    //=========== List Overwrite Operations =========================================================================

    @Override
    public void resetData(ReadOnlyList<Transaction> newData) {
        version.getCurrentState().resetData(newData);
        updateDisplayedTransactions();
    }

    //=========== Transaction-Level Operations =========================================================================

    @Override
    public boolean hasTransaction(Transaction transaction) {
        return version.getCurrentState().hasTransaction(transaction);
    }

    @Override
    public void addTransaction(Transaction p) {
        version.getCurrentState().addTransaction(p);
        updateDisplayedTransactions();
    }

    @Override
    public void removeTransaction(Transaction key) {
        version.getCurrentState().removeTransaction(key);
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

    //=========== Util Methods =========================================================================

    /**
     * Updates the list of suppliers to be shown in the UI.
     */
    private void updateDisplayedTransactions() {
        super.resetData(version.getCurrentState());
    }
}
