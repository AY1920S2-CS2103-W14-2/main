package cs2103_w14_2.inventory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import cs2103_w14_2.inventory.model.transaction.Transaction;
import cs2103_w14_2.inventory.model.version.StateNotFoundException;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.BuyTransactionBuilder;
import cs2103_w14_2.inventory.testutil.GoodBuilder;
import cs2103_w14_2.inventory.testutil.TypicalTransactions;
import org.junit.jupiter.api.Test;

public class VersionedTransactionHistoryTest {
    private VersionedTransactionHistory versionedTransactionHistory = new VersionedTransactionHistory();

    @Test
    public void undo_withoutCommits_throwsStateNotFoundException() {
        Assert.assertThrows(StateNotFoundException.class, () -> versionedTransactionHistory.undo());
    }

    @Test
    public void undo_afterOneCommit_removeChanges() {
        TransactionHistory expectedTransactionHistory = new TransactionHistory(versionedTransactionHistory);

        versionedTransactionHistory.addTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);
        versionedTransactionHistory.commit();
        versionedTransactionHistory.undo();
        assertEquals(versionedTransactionHistory, expectedTransactionHistory);
    }

    @Test
    public void undo_afterMultipleCommits_returnsToMostRecentCommit() {
        versionedTransactionHistory.addTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);
        versionedTransactionHistory.commit();
        TransactionHistory expectedTransactionHistoryFirstCommit = new TransactionHistory(versionedTransactionHistory);

        versionedTransactionHistory.addTransaction(TypicalTransactions.SELL_BANANA_TRANSACTION);
        versionedTransactionHistory.commit();
        TransactionHistory expectedTransactionHistorySecondCommit = new TransactionHistory(versionedTransactionHistory);

        versionedTransactionHistory.addTransaction(TypicalTransactions.SELL_CITRUS_TRANSACTION);
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
        versionedTransactionHistory.addTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);
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
        Assert.assertThrows(StateNotFoundException.class, () -> versionedTransactionHistory.redo());
    }

    @Test
    public void redo_afterOneUndo_redoChanges() {
        TransactionHistory expectedTransactionHistory = new TransactionHistory(versionedTransactionHistory);
        expectedTransactionHistory.addTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);

        versionedTransactionHistory.addTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);
        versionedTransactionHistory.commit();
        versionedTransactionHistory.undo();
        versionedTransactionHistory.redo();
        assertEquals(versionedTransactionHistory, expectedTransactionHistory);
    }

    @Test
    public void redo_afterMultipleUndo_returnsToMostRecentUndo() {
        versionedTransactionHistory.addTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);
        versionedTransactionHistory.commit();
        versionedTransactionHistory.addTransaction(TypicalTransactions.SELL_BANANA_TRANSACTION);
        versionedTransactionHistory.commit();
        TransactionHistory expectedTransactionHistorySecondCommit = new TransactionHistory(versionedTransactionHistory);

        versionedTransactionHistory.addTransaction(TypicalTransactions.SELL_CITRUS_TRANSACTION);
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
        expectedTransactionHistory.addTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);

        versionedTransactionHistory.addTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);
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
        expectedTransactionHistoryAfterRewrite.addTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);
        expectedTransactionHistoryAfterRewrite.addTransaction(TypicalTransactions.SELL_BANANA_TRANSACTION);
        expectedTransactionHistoryAfterRewrite.addTransaction(TypicalTransactions.BUY_DURIAN_TRANSACTION);

        TransactionHistory expectedTransactionHistoryAfterUndoFromRewrite =
                new TransactionHistory(versionedTransactionHistory);
        expectedTransactionHistoryAfterUndoFromRewrite.addTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);
        expectedTransactionHistoryAfterUndoFromRewrite.addTransaction(TypicalTransactions.SELL_BANANA_TRANSACTION);

        versionedTransactionHistory.addTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);
        versionedTransactionHistory.commit();
        versionedTransactionHistory.addTransaction(TypicalTransactions.SELL_BANANA_TRANSACTION);
        versionedTransactionHistory.commit();
        versionedTransactionHistory.addTransaction(TypicalTransactions.SELL_CITRUS_TRANSACTION);
        versionedTransactionHistory.commit();

        // ensures the current state points to the most recent commit
        versionedTransactionHistory.undo();
        versionedTransactionHistory.addTransaction(TypicalTransactions.BUY_DURIAN_TRANSACTION);
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
