package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.UniqueTransactionList;

import java.util.ArrayList;
import java.util.List;

public class VersionedTransactionHistory extends TransactionHistory implements Version<TransactionHistory> {
    private TransactionHistory currentState;
    int statePointer;
    List<TransactionHistory> stateList;

    public VersionedTransactionHistory() {
        statePointer = -1;
        stateList = new ArrayList<>();
        currentState = new TransactionHistory();
        commit();
    }

    /**
     * Creates a VersionedTransactionHistory using the {@code Transaction}s in the {@code toBeCopied}.
     */
    public VersionedTransactionHistory(ReadOnlyList<Transaction> toBeCopied) {
        statePointer = -1;
        stateList = new ArrayList<>();
        currentState = new TransactionHistory(toBeCopied);
        commit();
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the current state with {@code transactions}.
     * {@code transactions} must not contain duplicate transactions.
     */
    public void setTransactions(List<Transaction> transactions) {
        currentState.setTransactions(transactions);
    }

    /**
     * Resets the existing data of this {@code VersionedTransactionHistory} with {@code newData}.
     * Resets the history to an empty state as well.
     */
    public void resetData(ReadOnlyList<Transaction> newData) {
        setTransactions(newData.getReadOnlyList());
    }

    //// transaction-level operations

    /**
     * Returns true if a transaction with the same identity as {@code transaction} exists in the current state.
     */
    public boolean hasTransaction(Transaction transaction) {
        return currentState.hasTransaction(transaction);
    }

    /**
     * Adds a transaction to the current state.
     * The transaction must not already exist in the current state.
     */
    public void addTransaction(Transaction p) {
        currentState.addTransaction(p);
    }

    /**
     * Removes {@code key} from the current state.
     * {@code key} must exist in the current state.
     */
    public void removeTransaction(Transaction key) {
        currentState.removeTransaction(key);
    }

    //// versioning methods

    @Override
    public void commit() {
        TransactionHistory i = new TransactionHistory(getCurrentState());
        stateList = stateList.subList(0, statePointer + 1);
        stateList.add(i);
        statePointer++;
    }

    @Override
    public void undo() throws StateNotFoundException {
        if (statePointer == 0) {
            throw new StateNotFoundException();
        }

        statePointer--;
        currentState.resetData(stateList.get(statePointer));
    }

    @Override
    public TransactionHistory getCurrentState() {
        return currentState;
    }

    //// util methods

    @Override
    public String toString() {
        return currentState.toString();
        // TODO: refine later
    }

    @Override
    public ObservableList<Transaction> getReadOnlyList() {
        return currentState.getReadOnlyList();
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VersionedTransactionHistory // instanceof handles nulls
                && currentState.equals(((VersionedTransactionHistory) other).currentState))
                || (other instanceof TransactionHistory
                && currentState.equals((TransactionHistory) other));
    }

    @Override
    public int hashCode() {
        return currentState.hashCode();
    }
}
