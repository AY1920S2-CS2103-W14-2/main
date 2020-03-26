package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGoodAtIndex;
import static seedu.address.testutil.TypicalGoods.getTypicalInventory;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Inventory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodQuantity;
import seedu.address.testutil.GoodBuilder;

public class SetThresholdCommandTest {

    private static final GoodQuantity VALID_THRESHOLD = new GoodQuantity("100");
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalInventory(), new UserPrefs());


    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Index index = Index.fromOneBased(1);
        Good good = model.getFilteredGoodList().get(0);
        Good editedGood = new Good(good.getGoodName(), good.getGoodQuantity(), VALID_THRESHOLD);
        SetThresholdCommand setThresholdCommand = new SetThresholdCommand(index, VALID_THRESHOLD);

        String expectedMessage = String.format(SetThresholdCommand.MESSAGE_SUCCESS, VALID_THRESHOLD.goodQuantity,
                good.getGoodName().fullGoodName);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new Inventory(model.getInventory()),
                new UserPrefs());
        expectedModel.setGood(good, editedGood);
        assertCommandSuccess(setThresholdCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showGoodAtIndex(model, INDEX_FIRST_SUPPLIER);

        Good goodInFilteredList = model.getFilteredGoodList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        Good editedGood = new GoodBuilder(goodInFilteredList).withThreshold(100).build();
        SetThresholdCommand setThresholdCommand = new SetThresholdCommand(INDEX_FIRST_SUPPLIER,
                editedGood.getThreshold());

        String expectedMessage = String.format(SetThresholdCommand.MESSAGE_SUCCESS,
                100, editedGood.getGoodName().fullGoodName);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new Inventory(model.getInventory()),
                new UserPrefs());
        expectedModel.setGood(model.getFilteredGoodList().get(0), editedGood);

        assertCommandSuccess(setThresholdCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidGoodIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoodList().size() + 1);
        SetThresholdCommand setThresholdCommand = new SetThresholdCommand(outOfBoundIndex, VALID_THRESHOLD);
        assertCommandFailure(setThresholdCommand, model, Messages.MESSAGE_INVALID_GOOD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidGoodIndexFilteredList_failure() {
        showGoodAtIndex(model, INDEX_FIRST_SUPPLIER);
        Index outOfBoundIndex = INDEX_SECOND_SUPPLIER;
        // ensures that outOfBoundIndex is still in bounds of inventory list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventory().getGoodList().size());

        SetThresholdCommand setThresholdCommand = new SetThresholdCommand(outOfBoundIndex, VALID_THRESHOLD);

        assertCommandFailure(setThresholdCommand, model, Messages.MESSAGE_INVALID_GOOD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final SetThresholdCommand standardCommand = new SetThresholdCommand(INDEX_FIRST_SUPPLIER, VALID_THRESHOLD);

        // same values -> returns true
        GoodQuantity threshold = new GoodQuantity(VALID_THRESHOLD.goodQuantity);
        SetThresholdCommand commandWithSameValues = new SetThresholdCommand(INDEX_FIRST_SUPPLIER, threshold);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearSupplierCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new SetThresholdCommand(INDEX_SECOND_SUPPLIER, VALID_THRESHOLD)));

        // different descriptor -> returns false
        GoodQuantity diffThreshold = new GoodQuantity("200");
        assertFalse(standardCommand.equals(new SetThresholdCommand(INDEX_FIRST_SUPPLIER, diffThreshold)));
    }

}
