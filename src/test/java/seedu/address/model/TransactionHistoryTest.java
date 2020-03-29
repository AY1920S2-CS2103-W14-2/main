package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.BANANA;
import static seedu.address.testutil.TypicalSuppliers.BENSON;
import static seedu.address.testutil.TypicalTransactions.BUY_APPLE_TRANSACTION;
import static seedu.address.testutil.TypicalTransactions.getTypicalTransactionHistory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.exceptions.DuplicateTransactionException;
import seedu.address.testutil.BuyTransactionBuilder;

public class TransactionHistoryTest {

    private final TransactionHistory transactionHistory = new TransactionHistory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), transactionHistory.getReadOnlyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionHistory.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTransactionHistory_replacesData() {
        TransactionHistory newData = getTypicalTransactionHistory();
        transactionHistory.resetData(newData);
        assertEquals(newData, transactionHistory);
    }

    @Test
    public void resetData_withDuplicateTransactions_throwsDuplicateTransactionException() {
        // Two transactions with the same identity fields
        Transaction editedTransaction = new BuyTransactionBuilder(BUY_APPLE_TRANSACTION)
            .withGood(BANANA).withSupplier(BENSON).build();
        List<Transaction> newTransactions = Arrays.asList(BUY_APPLE_TRANSACTION, editedTransaction);
        TransactionHistoryStub newData = new TransactionHistoryStub(newTransactions);

        assertThrows(DuplicateTransactionException.class, () -> transactionHistory.resetData(newData));
    }

    @Test
    public void hasTransaction_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionHistory.hasTransaction(null));
    }

    @Test
    public void hasTransaction_transactionNotInTransactionHistory_returnsFalse() {
        assertFalse(transactionHistory.hasTransaction(BUY_APPLE_TRANSACTION));
    }

    @Test
    public void hasTransaction_transactionInTransactionHistory_returnsTrue() {
        transactionHistory.addTransaction(BUY_APPLE_TRANSACTION);
        assertTrue(transactionHistory.hasTransaction(BUY_APPLE_TRANSACTION));
    }

    @Test
    public void hasTransaction_transactionWithSameIdentityFieldsInTransactionHistory_returnsTrue() {
        transactionHistory.addTransaction(BUY_APPLE_TRANSACTION);
        Transaction editedAlice = new BuyTransactionBuilder(BUY_APPLE_TRANSACTION)
            .withGood(BANANA).withSupplier(BENSON).build();
        assertTrue(transactionHistory.hasTransaction(editedAlice));
    }

    @Test
    public void getTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> transactionHistory.getReadOnlyList().remove(0));
    }

    /**
     * A stub ReadOnlyTransactionHistory whose transactions list can violate interface constraints.
     */
    private static class TransactionHistoryStub implements ReadOnlyList<Transaction> {
        private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();

        TransactionHistoryStub(Collection<Transaction> transactions) {
            this.transactions.setAll(transactions);
        }

        @Override
        public ObservableList<Transaction> getReadOnlyList() {
            return transactions;
        }
    }

}
