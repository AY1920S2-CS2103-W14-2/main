package cs2103_w14_2.inventory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.showSupplierAtIndex;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.getTypicalInventory;
import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.ALICE;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.getTypicalAddressBook;
import static cs2103_w14_2.inventory.testutil.TypicalTransactions.getTypicalTransactionHistory;

import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import cs2103_w14_2.inventory.model.UserPrefs;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.model.supplier.Supplier;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteSupplierCommand}.
 */
public class DeleteSupplierCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
            getTypicalTransactionHistory(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Supplier supplierToDelete = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        DeleteSupplierCommand deleteSupplierCommand = new DeleteSupplierCommand(INDEX_FIRST_SUPPLIER);

        String expectedMessage = String.format(DeleteSupplierCommand.MESSAGE_DELETE_SUPPLIER_SUCCESS, supplierToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());
        expectedModel.deleteSupplier(supplierToDelete);

        assertCommandSuccess(deleteSupplierCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSupplierList().size() + 1);
        DeleteSupplierCommand deleteSupplierCommand = new DeleteSupplierCommand(outOfBoundIndex);

        assertCommandFailure(deleteSupplierCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);

        Supplier supplierToDelete = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        DeleteSupplierCommand deleteSupplierCommand = new DeleteSupplierCommand(INDEX_FIRST_SUPPLIER);

        String expectedMessage = String.format(DeleteSupplierCommand.MESSAGE_DELETE_SUPPLIER_SUCCESS, supplierToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());
        expectedModel.deleteSupplier(supplierToDelete);
        showNoSupplier(expectedModel);

        assertCommandSuccess(deleteSupplierCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);

        Index outOfBoundIndex = INDEX_SECOND_SUPPLIER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getReadOnlyList().size());

        DeleteSupplierCommand deleteSupplierCommand = new DeleteSupplierCommand(outOfBoundIndex);

        assertCommandFailure(deleteSupplierCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndex_callsModelCommit() throws CommandException {
        ModelStubCommit modelStub = new ModelStubCommit();
        modelStub.addSupplier(ALICE);
        new DeleteSupplierCommand(INDEX_FIRST_SUPPLIER).execute(modelStub);

        assertTrue(modelStub.isCommitted());
    }

    @Test
    public void equals() {
        DeleteSupplierCommand deleteFirstCommand = new DeleteSupplierCommand(INDEX_FIRST_SUPPLIER);
        DeleteSupplierCommand deleteSecondCommand = new DeleteSupplierCommand(INDEX_SECOND_SUPPLIER);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteSupplierCommand deleteFirstCommandCopy = new DeleteSupplierCommand(INDEX_FIRST_SUPPLIER);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different supplier -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoSupplier(Model model) {
        model.updateFilteredSupplierList(p -> false);

        assertTrue(model.getFilteredSupplierList().isEmpty());
    }
}
