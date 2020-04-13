package cs2103_w14_2.inventory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import cs2103_w14_2.inventory.model.good.Good;
import cs2103_w14_2.inventory.model.version.StateNotFoundException;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.GoodBuilder;
import cs2103_w14_2.inventory.testutil.TypicalGoods;
import org.junit.jupiter.api.Test;

public class VersionedInventoryTest {

    private VersionedInventory versionedInventory = new VersionedInventory();

    @Test
    public void undo_withoutCommits_throwsStateNotFoundException() {
        Assert.assertThrows(StateNotFoundException.class, () -> versionedInventory.undo());
    }

    @Test
    public void undo_afterOneCommit_removeChanges() {
        Inventory expectedInventory = new Inventory(versionedInventory);

        versionedInventory.addGood(TypicalGoods.APPLE);
        versionedInventory.commit();
        versionedInventory.undo();
        assertEquals(versionedInventory, expectedInventory);
    }

    @Test
    public void undo_afterMultipleCommits_returnsToMostRecentCommit() {
        versionedInventory.addGood(TypicalGoods.APPLE);
        versionedInventory.commit();
        Inventory expectedInventoryFirstCommit = new Inventory(versionedInventory);

        versionedInventory.addGood(TypicalGoods.BANANA);
        versionedInventory.commit();
        Inventory expectedInventorySecondCommit = new Inventory(versionedInventory);

        versionedInventory.addGood(TypicalGoods.CITRUS);
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
        versionedInventory.addGood(TypicalGoods.APPLE);
        versionedInventory.commit();

        Good g = new GoodBuilder().withGoodName("Erased Ignored").build();
        versionedInventory.addGood(g);
        versionedInventory.undo();

        assertEquals(versionedInventory, expectedInventory);
    }

    @Test
    public void redo_withoutUndo_throwsStateNotFoundException() {
        Assert.assertThrows(StateNotFoundException.class, () -> versionedInventory.redo());
    }

    @Test
    public void redo_afterOneUndo_redoChanges() {
        Inventory expectedInventory = new Inventory(versionedInventory);
        expectedInventory.addGood(TypicalGoods.APPLE);

        versionedInventory.addGood(TypicalGoods.APPLE);
        versionedInventory.commit();
        versionedInventory.undo();
        versionedInventory.redo();
        assertEquals(versionedInventory, expectedInventory);
    }

    @Test
    public void redo_afterMultipleUndo_returnsToMostRecentUndo() {
        versionedInventory.addGood(TypicalGoods.APPLE);
        versionedInventory.commit();
        versionedInventory.addGood(TypicalGoods.BANANA);
        versionedInventory.commit();
        Inventory expectedInventorySecondCommit = new Inventory(versionedInventory);

        versionedInventory.addGood(TypicalGoods.CITRUS);
        versionedInventory.commit();
        Inventory expectedInventoryThirdCommit = new Inventory(versionedInventory);

        versionedInventory.undo();
        versionedInventory.undo();

        versionedInventory.redo();
        assertEquals(versionedInventory, expectedInventorySecondCommit);

        versionedInventory.redo();
        assertEquals(versionedInventory, expectedInventoryThirdCommit);
    }

    @Test
    public void redo_afterUnsavedChanges_removesUnsavedChangesAndRedoPreviousChanges() {
        Inventory expectedInventory = new Inventory(versionedInventory);
        expectedInventory.addGood(TypicalGoods.APPLE);

        versionedInventory.addGood(TypicalGoods.APPLE);
        versionedInventory.commit();
        versionedInventory.undo();

        Good g = new GoodBuilder().withGoodName("Erased Ignored").build();
        versionedInventory.addGood(g);

        versionedInventory.redo();

        assertEquals(versionedInventory, expectedInventory);
    }

    @Test
    public void commit_afterUndo_removesFutureHistory() {
        Inventory expectedInventoryAfterRewrite = new Inventory(versionedInventory);
        expectedInventoryAfterRewrite.addGood(TypicalGoods.APPLE);
        expectedInventoryAfterRewrite.addGood(TypicalGoods.BANANA);
        expectedInventoryAfterRewrite.addGood(TypicalGoods.DURIAN);

        Inventory expectedInventoryAfterUndoFromRewrite = new Inventory(versionedInventory);
        expectedInventoryAfterUndoFromRewrite.addGood(TypicalGoods.APPLE);
        expectedInventoryAfterUndoFromRewrite.addGood(TypicalGoods.BANANA);

        versionedInventory.addGood(TypicalGoods.APPLE);
        versionedInventory.commit();
        versionedInventory.addGood(TypicalGoods.BANANA);
        versionedInventory.commit();
        versionedInventory.addGood(TypicalGoods.CITRUS);
        versionedInventory.commit();

        // ensures the current state points to the most recent commit
        versionedInventory.undo();
        versionedInventory.addGood(TypicalGoods.DURIAN);
        versionedInventory.commit();
        assertEquals(versionedInventory, expectedInventoryAfterRewrite);

        // ensures that current state is not added on top of deleted history
        versionedInventory.undo();
        assertEquals(versionedInventory, expectedInventoryAfterUndoFromRewrite);

        // ensures that deleted history is inaccessible after undo from rewrite
        versionedInventory.redo();
        assertEquals(versionedInventory, expectedInventoryAfterRewrite);
    }
}

