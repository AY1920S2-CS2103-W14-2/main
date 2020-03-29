package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalGoods.BANANA;
import static seedu.address.testutil.TypicalGoods.CITRUS;
import static seedu.address.testutil.TypicalGoods.DURIAN;

import org.junit.jupiter.api.Test;

import seedu.address.model.good.Good;
import seedu.address.testutil.GoodBuilder;

public class VersionedInventoryTest {

    private VersionedInventory versionedInventory = new VersionedInventory();

    @Test
    public void undo_withoutCommits_throwsStateNotFoundException() {
        assertThrows(StateNotFoundException.class, () -> versionedInventory.undo());
    }

    @Test
    public void undo_afterOneCommit_removeChanges() {
        Inventory expectedInventory = new Inventory(versionedInventory);

        versionedInventory.addGood(APPLE);
        versionedInventory.commit();
        versionedInventory.undo();
        assertEquals(versionedInventory, expectedInventory);
    }

    @Test
    public void undo_afterMultipleCommits_returnsToMostRecentCommit() {
        versionedInventory.addGood(APPLE);
        versionedInventory.commit();
        Inventory expectedInventoryFirstCommit = new Inventory(versionedInventory);

        versionedInventory.addGood(BANANA);
        versionedInventory.commit();
        Inventory expectedInventorySecondCommit = new Inventory(versionedInventory);

        versionedInventory.addGood(CITRUS);
        versionedInventory.commit();

        // first undo goes to second commit
        versionedInventory.undo();
        assertEquals(versionedInventory, expectedInventorySecondCommit);

        // second undo goes to first commit
        versionedInventory.undo();
        assertEquals(versionedInventory, expectedInventoryFirstCommit);
    }

    @Test
    public void undo_afterUnsavedChanges_removesUnsavedAndPreviousChanges() {
        Inventory expectedInventory = new Inventory(versionedInventory);
        versionedInventory.addGood(APPLE);
        versionedInventory.commit();

        Good g = new GoodBuilder().withGoodName("Erased Ignored").build();
        versionedInventory.addGood(g);
        versionedInventory.undo();

        assertEquals(versionedInventory, expectedInventory);
    }

    @Test
    public void commit_afterUndo_removesFutureHistory() {
        Inventory expectedInventoryAfterRewrite = new Inventory(versionedInventory);
        expectedInventoryAfterRewrite.addGood(APPLE);
        expectedInventoryAfterRewrite.addGood(BANANA);
        expectedInventoryAfterRewrite.addGood(DURIAN);

        Inventory expectedInventoryAfterUndoFromRewrite = new Inventory(versionedInventory);
        expectedInventoryAfterUndoFromRewrite.addGood(APPLE);
        expectedInventoryAfterUndoFromRewrite.addGood(BANANA);

        versionedInventory.addGood(APPLE);
        versionedInventory.commit();
        versionedInventory.addGood(BANANA);
        versionedInventory.commit();
        versionedInventory.addGood(CITRUS);
        versionedInventory.commit();

        // ensures the current state points to the most recent commit
        versionedInventory.undo();
        versionedInventory.addGood(DURIAN);
        versionedInventory.commit();
        assertEquals(versionedInventory, expectedInventoryAfterRewrite);

        // ensures that current state is not added on top of deleted history
        versionedInventory.undo();
        assertEquals(versionedInventory, expectedInventoryAfterUndoFromRewrite);
    }
}

