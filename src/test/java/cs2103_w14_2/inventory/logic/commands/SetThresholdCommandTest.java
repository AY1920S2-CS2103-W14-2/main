package cs2103_w14_2.inventory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.model.Inventory;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import cs2103_w14_2.inventory.model.UserPrefs;
import cs2103_w14_2.inventory.model.good.GoodQuantity;
import cs2103_w14_2.inventory.testutil.GoodBuilder;
import cs2103_w14_2.inventory.testutil.TypicalGoods;
import cs2103_w14_2.inventory.testutil.TypicalIndexes;
import cs2103_w14_2.inventory.testutil.TypicalSuppliers;
import cs2103_w14_2.inventory.testutil.TypicalTransactions;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.model.good.Good;

public class SetThresholdCommandTest {

    private static final GoodQuantity VALID_THRESHOLD = new GoodQuantity("100");
    private Model model = new ModelManager(TypicalSuppliers.getTypicalAddressBook(), TypicalGoods.getTypicalInventory(),
            TypicalTransactions.getTypicalTransactionHistory(), new UserPrefs());


    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Index index = Index.fromOneBased(1);
        Good good = model.getFilteredGoodList().get(0);
        Good editedGood = new Good(good.getGoodName(), good.getGoodQuantity(), VALID_THRESHOLD);
        SetThresholdCommand setThresholdCommand = new SetThresholdCommand(index, VALID_THRESHOLD);

        String expectedMessage = String.format(SetThresholdCommand.MESSAGE_SUCCESS, VALID_THRESHOLD.goodQuantity,
                good.getGoodName().fullGoodName);

        Model expectedModel = new ModelManager(TypicalSuppliers.getTypicalAddressBook(), new Inventory(model.getInventory()),
                TypicalTransactions.getTypicalTransactionHistory(), new UserPrefs());
        expectedModel.setGood(good, editedGood);
        CommandTestUtil.assertCommandSuccess(setThresholdCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showGoodAtIndex(model, TypicalIndexes.INDEX_FIRST_SUPPLIER);

        Good goodInFilteredList = model.getFilteredGoodList().get(TypicalIndexes.INDEX_FIRST_SUPPLIER.getZeroBased());
        Good editedGood = new GoodBuilder(goodInFilteredList).withThreshold(100).build();
        SetThresholdCommand setThresholdCommand = new SetThresholdCommand(TypicalIndexes.INDEX_FIRST_SUPPLIER,
                editedGood.getThreshold());

        String expectedMessage = String.format(SetThresholdCommand.MESSAGE_SUCCESS,
                100, editedGood.getGoodName().fullGoodName);

        Model expectedModel = new ModelManager(TypicalSuppliers.getTypicalAddressBook(), new Inventory(model.getInventory()),
                TypicalTransactions.getTypicalTransactionHistory(), new UserPrefs());
        expectedModel.setGood(model.getFilteredGoodList().get(0), editedGood);

        CommandTestUtil.assertCommandSuccess(setThresholdCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidGoodIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoodList().size() + 1);
        SetThresholdCommand setThresholdCommand = new SetThresholdCommand(outOfBoundIndex, VALID_THRESHOLD);
        CommandTestUtil.assertCommandFailure(setThresholdCommand, model, Messages.MESSAGE_INVALID_GOOD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidGoodIndexFilteredList_failure() {
        CommandTestUtil.showGoodAtIndex(model, TypicalIndexes.INDEX_FIRST_SUPPLIER);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_SUPPLIER;
        // ensures that outOfBoundIndex is still in bounds of inventory list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventory().getReadOnlyList().size());

        SetThresholdCommand setThresholdCommand = new SetThresholdCommand(outOfBoundIndex, VALID_THRESHOLD);

        CommandTestUtil.assertCommandFailure(setThresholdCommand, model, Messages.MESSAGE_INVALID_GOOD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final SetThresholdCommand standardCommand = new SetThresholdCommand(TypicalIndexes.INDEX_FIRST_SUPPLIER, VALID_THRESHOLD);

        // same values -> returns true
        SetThresholdCommand commandWithSameValues = new SetThresholdCommand(TypicalIndexes.INDEX_FIRST_SUPPLIER, VALID_THRESHOLD);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearSupplierCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new SetThresholdCommand(TypicalIndexes.INDEX_SECOND_SUPPLIER, VALID_THRESHOLD)));

        // different descriptor -> returns false
        GoodQuantity diffThreshold = new GoodQuantity("200");
        assertFalse(standardCommand.equals(new SetThresholdCommand(TypicalIndexes.INDEX_FIRST_SUPPLIER, diffThreshold)));
    }

}
