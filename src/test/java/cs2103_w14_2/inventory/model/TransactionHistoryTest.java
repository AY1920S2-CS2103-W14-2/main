package cs2103_w14_2.inventory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.BANANA;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cs2103_w14_2.inventory.model.transaction.Transaction;
import cs2103_w14_2.inventory.model.transaction.exceptions.DuplicateTransactionException;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.BuyTransactionBuilder;
import cs2103_w14_2.inventory.testutil.TypicalSuppliers;
import cs2103_w14_2.inventory.testutil.TypicalTransactions;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TransactionHistoryTest {

    private final TransactionHistory transactionHistory = new TransactionHistory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), transactionHistory.getReadOnlyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> transactionHistory.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTransactionHistory_replacesData() {
        TransactionHistory newData = TypicalTransactions.getTypicalTransactionHistory();
        transactionHistory.resetData(newData);
        assertEquals(newData, transactionHistory);
    }

    @Test
    public void resetData_withDuplicateTransactions_throwsDuplicateTransactionException() {
        // Two transactions with the same identity fields
        Transaction editedTransaction = new BuyTransactionBuilder(TypicalTransactions.BUY_APPLE_TRANSACTION)
            .withGood(BANANA).withSupplier(TypicalSuppliers.BENSON).build();
        List<Transaction> newTransactions = Arrays.asList(TypicalTransactions.BUY_APPLE_TRANSACTION, editedTransaction);
        TransactionHistoryStub newData = new TransactionHistoryStub(newTransactions);

        Assert.assertThrows(DuplicateTransactionException.class, () -> transactionHistory.resetData(newData));
    }

    @Test
    public void hasTransaction_nullTransaction_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> transactionHistory.hasTransaction(null));
    }

    @Test
    public void hasTransaction_transactionNotInTransactionHistory_returnsFalse() {
        assertFalse(transactionHistory.hasTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION));
    }

    @Test
    public void hasTransaction_transactionInTransactionHistory_returnsTrue() {
        transactionHistory.addTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);
        assertTrue(transactionHistory.hasTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION));
    }

    @Test
    public void hasTransaction_transactionWithSameIdentityFieldsInTransactionHistory_returnsTrue() {
        transactionHistory.addTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);
        Transaction editedAlice = new BuyTransactionBuilder(TypicalTransactions.BUY_APPLE_TRANSACTION)
            .withGood(BANANA).withSupplier(TypicalSuppliers.BENSON).build();
        assertTrue(transactionHistory.hasTransaction(editedAlice));
    }

    @Test
    public void getTransactionList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> transactionHistory.getReadOnlyList().remove(0));
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
