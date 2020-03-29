package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFER_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showSupplierAtIndex;
import static seedu.address.testutil.TypicalGoods.getTypicalInventory;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTransactions.getTypicalTransactionHistory;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditSupplierCommand.EditSupplierDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.supplier.Supplier;
import seedu.address.testutil.EditSupplierDescriptorBuilder;
import seedu.address.testutil.SupplierBuilder;

/**
 * Contains integration tests (interaction with the Model,
 * UndoCommand and RedoCommand) and unit tests for EditSupplierCommand.
 */
public class EditSupplierCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
            getTypicalTransactionHistory(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Supplier editedSupplier = new SupplierBuilder().build();
        EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder(editedSupplier).build();
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

        EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB)
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
                new EditSupplierDescriptor());
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
        EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder(firstSupplier).build();
        EditSupplierCommand editSupplierCommand = new EditSupplierCommand(INDEX_SECOND_SUPPLIER, descriptor);

        assertCommandFailure(editSupplierCommand, model, EditSupplierCommand.MESSAGE_DUPLICATE_SUPPLIER);
    }

    @Test
    public void execute_duplicateSupplierFilteredList_failure() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);

        // edit supplier in filtered list into a duplicate in address book
        Supplier supplierInList = model.getAddressBook().getSupplierList().get(INDEX_SECOND_SUPPLIER.getZeroBased());
        EditSupplierCommand editSupplierCommand = new EditSupplierCommand(INDEX_FIRST_SUPPLIER,
                new EditSupplierDescriptorBuilder(supplierInList).build());

        assertCommandFailure(editSupplierCommand, model, EditSupplierCommand.MESSAGE_DUPLICATE_SUPPLIER);
    }

    @Test
    public void execute_invalidSupplierIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSupplierList().size() + 1);
        EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB).build();
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
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getSupplierList().size());

        EditSupplierCommand editSupplierCommand = new EditSupplierCommand(outOfBoundIndex,
                new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editSupplierCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditSupplierCommand standardCommand = new EditSupplierCommand(INDEX_FIRST_SUPPLIER, DESC_AMY);

        // same values -> returns true
        EditSupplierDescriptor copyDescriptor = new EditSupplierDescriptor(DESC_AMY);
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
