package cs2103_w14_2.inventory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.DESC_AMY;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.DESC_BOB;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.VALID_OFFER_BANANA;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.showSupplierAtIndex;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.getTypicalInventory;
import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.ALICE;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.CARL;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.getTypicalAddressBook;
import static cs2103_w14_2.inventory.testutil.TypicalTransactions.getTypicalTransactionHistory;

import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.model.AddressBook;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import cs2103_w14_2.inventory.model.UserPrefs;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.testutil.EditSupplierDescriptorBuilder;
import cs2103_w14_2.inventory.testutil.SupplierBuilder;

/**
 * Contains integration tests (interaction with the Model,
 * UndoCommand and RedoCommand) and unit tests for EditSupplierCommand.
 */
public class EditSupplierCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
            getTypicalTransactionHistory(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Supplier editedSupplier = ALICE;

        EditSupplierCommand.EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder(editedSupplier).build();
        EditSupplierCommand editSupplierCommand = new EditSupplierCommand(INDEX_FIRST_SUPPLIER, descriptor);

        String expectedMessage = String.format(EditSupplierCommand.MESSAGE_EDIT_SUPPLIER_SUCCESS, editedSupplier);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());
        expectedModel.setSupplier(model.getFilteredSupplierList().get(0), editedSupplier);

        assertCommandSuccess(editSupplierCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastSupplier = Index.fromOneBased(model.getFilteredSupplierList().size());
        Supplier lastSupplier = model.getFilteredSupplierList().get(indexLastSupplier.getZeroBased());

        SupplierBuilder supplierInList = new SupplierBuilder(lastSupplier);
        Supplier editedSupplier = supplierInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withOffers(VALID_OFFER_BANANA).build();

        EditSupplierCommand.EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withOffers(VALID_OFFER_BANANA).build();
        EditSupplierCommand editSupplierCommand = new EditSupplierCommand(indexLastSupplier, descriptor);

        String expectedMessage = String.format(EditSupplierCommand.MESSAGE_EDIT_SUPPLIER_SUCCESS, editedSupplier);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());
        expectedModel.setSupplier(lastSupplier, editedSupplier);

        assertCommandSuccess(editSupplierCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditSupplierCommand editSupplierCommand = new EditSupplierCommand(INDEX_FIRST_SUPPLIER,
                new EditSupplierCommand.EditSupplierDescriptor());
        Supplier editedSupplier = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());

        String expectedMessage = String.format(EditSupplierCommand.MESSAGE_EDIT_SUPPLIER_SUCCESS, editedSupplier);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());

        assertCommandSuccess(editSupplierCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);

        Supplier supplierInFilteredList = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        Supplier editedSupplier = new SupplierBuilder(supplierInFilteredList).withName(VALID_NAME_BOB).build();
        EditSupplierCommand editSupplierCommand = new EditSupplierCommand(INDEX_FIRST_SUPPLIER,
                new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditSupplierCommand.MESSAGE_EDIT_SUPPLIER_SUCCESS, editedSupplier);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());
        expectedModel.setSupplier(model.getFilteredSupplierList().get(0), editedSupplier);

        assertCommandSuccess(editSupplierCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateSupplierUnfilteredList_failure() {
        Supplier firstSupplier = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        EditSupplierCommand.EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder(firstSupplier).build();
        EditSupplierCommand editSupplierCommand = new EditSupplierCommand(INDEX_SECOND_SUPPLIER, descriptor);

        assertCommandFailure(editSupplierCommand, model, EditSupplierCommand.MESSAGE_DUPLICATE_SUPPLIER);
    }

    @Test
    public void execute_duplicateSupplierFilteredList_failure() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);

        // edit supplier in filtered list into a duplicate in address book
        Supplier supplierInList = model.getAddressBook().getReadOnlyList().get(INDEX_SECOND_SUPPLIER.getZeroBased());
        EditSupplierCommand editSupplierCommand = new EditSupplierCommand(INDEX_FIRST_SUPPLIER,
                new EditSupplierDescriptorBuilder(supplierInList).build());

        assertCommandFailure(editSupplierCommand, model, EditSupplierCommand.MESSAGE_DUPLICATE_SUPPLIER);
    }

    @Test
    public void execute_invalidSupplierIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSupplierList().size() + 1);
        EditSupplierCommand.EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditSupplierCommand editSupplierCommand = new EditSupplierCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editSupplierCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidSupplierIndexFilteredList_failure() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);
        Index outOfBoundIndex = INDEX_SECOND_SUPPLIER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getReadOnlyList().size());

        EditSupplierCommand editSupplierCommand = new EditSupplierCommand(outOfBoundIndex,
                new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editSupplierCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_valid_callsModelCommit() throws CommandException {
        ModelStubCommit modelStub = new ModelStubCommit();
        modelStub.addSupplier(CARL);
        new EditSupplierCommand(
                INDEX_FIRST_SUPPLIER, new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB).build()
        ).execute(modelStub);

        assertTrue(modelStub.isCommitted());
    }

    @Test
    public void equals() {
        final EditSupplierCommand standardCommand = new EditSupplierCommand(INDEX_FIRST_SUPPLIER, DESC_AMY);

        // same values -> returns true
        EditSupplierCommand.EditSupplierDescriptor copyDescriptor = new EditSupplierCommand.EditSupplierDescriptor(DESC_AMY);
        EditSupplierCommand commandWithSameValues = new EditSupplierCommand(INDEX_FIRST_SUPPLIER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearSupplierCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditSupplierCommand(INDEX_SECOND_SUPPLIER, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditSupplierCommand(INDEX_FIRST_SUPPLIER, DESC_BOB)));
    }

}
