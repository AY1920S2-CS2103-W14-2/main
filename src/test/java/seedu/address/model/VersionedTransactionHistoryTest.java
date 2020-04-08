package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTransactions.BUY_APPLE_TRANSACTION;
import static seedu.address.testutil.TypicalTransactions.BUY_DURIAN_TRANSACTION;
import static seedu.address.testutil.TypicalTransactions.SELL_BANANA_TRANSACTION;
import static seedu.address.testutil.TypicalTransactions.SELL_CITRUS_TRANSACTION;

import org.junit.jupiter.api.Test;

import seedu.address.model.transaction.Transaction;
import seedu.address.model.version.StateNotFoundException;
import seedu.address.testutil.BuyTransactionBuilder;
import seedu.address.testutil.GoodBuilder;

public class VersionedTransactionHistoryTest {
    private VersionedTransactionHistory versionedTransactionHistory = new VersionedTransactionHistory();

    @Test
    public void undo_withoutCommits_throwsStateNotFoundException() {
        assertThrows(StateNotFoundException.class, () -> versionedTransactionHistory.undo());
    }

    @Test
    public void undo_afterOneCommit_removeChanges() {
        TransactionHistory expectedTransactionHistory = new TransactionHistory(versionedTransactionHistory);

        versionedTransactionHistory.addTransaction(BUY_APPLE_TRANSACTION);
        versionedTransactionHistory.commit();
        versionedTransactionHistory.undo();
        assertEquals(versionedTransactionHistory, expectedTransactionHistory);
    }

    @Test
    public void undo_afterMultipleCommits_returnsToMostRecentCommit() {
        versionedTransactionHistory.addTransaction(BUY_APPLE_TRANSACTION);
        versionedTransactionHistory.commit();
        TransactionHistory expectedTransactionHistoryFirstCommit = new TransactionHistory(versionedTransactionHistory);

        versionedTransactionHistory.addTransaction(SELL_BANANA_TRANSACTION);
        versionedTransactionHistory.commit();
        TransactionHistory expectedTransactionHistorySecondCommit = new TransactionHistory(versionedTransactionHistory);

        versionedTransactionHistory.addTransaction(SELL_CITRUS_TRANSACTION);
        versionedTransactionHistory.commit();

        // first undo goes to second commit
        versionedTransactionHistory.undo();
        assertEquals(versionedTransactionHistory, expectedTransactionHistorySecondCommit);

        // second undo goes to first commit
        versionedTransactionHistory.undo();
        assertEquals(versionedTransactionHistory, expectedTransactionHistoryFirstCommit);
    }

    @Test
    public void undo_afterUnsavedChanges_removesUnsavedAndPreviousChanges() {
        TransactionHistory expectedTransactionHistory = new TransactionHistory(versionedTransactionHistory);
        versionedTransactionHistory.addTransaction(BUY_APPLE_TRANSACTION);
        versionedTransactionHistory.commit();

        Transaction t = new BuyTransactionBuilder().withGood(
                new GoodBuilder().withGoodName("Erased Ignored").build()
        ).build();
        versionedTransactionHistory.addTransaction(t);
        versionedTransactionHistory.undo();

        assertEquals(versionedTransactionHistory, expectedTransactionHistory);
    }

    @Test
    public void redo_withoutUndo_throwsStateNotFoundException() {
        assertThrows(StateNotFoundException.class, () -> versionedTransactionHistory.redo());
    }

    @Test
    public void redo_afterOneUndo_redoChanges() {
        TransactionHistory expectedTransactionHistory = new TransactionHistory(versionedTransactionHistory);
        expectedTransactionHistory.addTransaction(BUY_APPLE_TRANSACTION);

        versionedTransactionHistory.addTransaction(BUY_APPLE_TRANSACTION);
        versionedTransactionHistory.commit();
        versionedTransactionHistory.undo();
        versionedTransactionHistory.redo();
        assertEquals(versionedTransactionHistory, expectedTransactionHistory);
    }

    @Test
    public void redo_afterMultipleUndo_returnsToMostRecentUndo() {
        versionedTransactionHistory.addTransaction(BUY_APPLE_TRANSACTION);
        versionedTransactionHistory.commit();
        versionedTransactionHistory.addTransaction(SELL_BANANA_TRANSACTION);
        versionedTransactionHistory.commit();
        TransactionHistory expectedTransactionHistorySecondCommit = new TransactionHistory(versionedTransactionHistory);

        versionedTransactionHistory.addTransaction(SELL_CITRUS_TRANSACTION);
        versionedTransactionHistory.commit();
        TransactionHistory expectedTransactionHistoryThirdCommit = new TransactionHistory(versionedTransactionHistory);

        versionedTransactionHistory.undo();
        versionedTransactionHistory.undo();

        versionedTransactionHistory.redo();
        assertEquals(versionedTransactionHistory, expectedTransactionHistorySecondCommit);

        versionedTransactionHistory.redo();
        assertEquals(versionedTransactionHistory, expectedTransactionHistoryThirdCommit);
    }

    @Test
    public void redo_afterUnsavedChanges_removesUnsavedChangesAndRedoPreviousChanges() {
        TransactionHistory expectedTransactionHistory = new TransactionHistory(versionedTransactionHistory);
        expectedTransactionHistory.addTransaction(BUY_APPLE_TRANSACTION);

        versionedTransactionHistory.addTransaction(BUY_APPLE_TRANSACTION);
        versionedTransactionHistory.commit();
        versionedTransactionHistory.undo();

        Transaction t = new BuyTransactionBuilder().withGood(
                new GoodBuilder().withGoodName("Erased Ignored").build()
        ).build();
        versionedTransactionHistory.addTransaction(t);
        versionedTransactionHistory.redo();

        assertEquals(versionedTransactionHistory, expectedTransactionHistory);
    }

    @Test
    public void commit_afterUndo_removesFutureHistory() {
        TransactionHistory expectedTransactionHistoryAfterRewrite = new TransactionHistory(versionedTransactionHistory);
        expectedTransactionHistoryAfterRewrite.addTransaction(BUY_APPLE_TRANSACTION);
        expectedTransactionHistoryAfterRewrite.addTransaction(SELL_BANANA_TRANSACTION);
        expectedTransactionHistoryAfterRewrite.addTransaction(BUY_DURIAN_TRANSACTION);

        TransactionHistory expectedTransactionHistoryAfterUndoFromRewrite =
                new TransactionHistory(versionedTransactionHistory);
        expectedTransactionHistoryAfterUndoFromRewrite.addTransaction(BUY_APPLE_TRANSACTION);
        expectedTransactionHistoryAfterUndoFromRewrite.addTransaction(SELL_BANANA_TRANSACTION);

        versionedTransactionHistory.addTransaction(BUY_APPLE_TRANSACTION);
        versionedTransactionHistory.commit();
        versionedTransactionHistory.addTransaction(SELL_BANANA_TRANSACTION);
        versionedTransactionHistory.commit();
        versionedTransactionHistory.addTransaction(SELL_CITRUS_TRANSACTION);
        versionedTransactionHistory.commit();

        // ensures the current state points to the most recent commit
        versionedTransactionHistory.undo();
        versionedTransactionHistory.addTransaction(BUY_DURIAN_TRANSACTION);
        versionedTransactionHistory.commit();
        assertEquals(versionedTransactionHistory, expectedTransactionHistoryAfterRewrite);

        // ensures that current state is not added on top of deleted history
        versionedTransactionHistory.undo();
        assertEquals(versionedTransactionHistory, expectedTransactionHistoryAfterUndoFromRewrite);

        // ensures that deleted history is inaccessible after undo from rewrite
        versionedTransactionHistory.redo();
        assertEquals(versionedTransactionHistory, expectedTransactionHistoryAfterRewrite);
    }
}
