package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSuppliers.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.StateNotFoundException;
import seedu.address.model.good.Good;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.SupplierBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_supplierAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingSupplierAdded modelStub = new ModelStubAcceptingSupplierAdded();
        Supplier validSupplier = new SupplierBuilder().build();

        CommandResult commandResult = new AddCommand(validSupplier).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validSupplier), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validSupplier), modelStub.suppliersAdded);
    }

    @Test
    public void execute_validPerson_callsModelCommit() throws CommandException {
        ModelStubCommit modelStub = new ModelStubCommit();
        new AddCommand(ALICE).execute(modelStub);

        assertTrue(modelStub.isCommitted());
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Supplier validSupplier = new SupplierBuilder().build();
        AddCommand addCommand = new AddCommand(validSupplier);
        ModelStub modelStub = new ModelStubWithSupplier(validSupplier);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_SUPPLIER, () ->
                addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Supplier alice = new SupplierBuilder().withName("Alice").build();
        Supplier bob = new SupplierBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different supplier -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSupplier(Supplier supplier) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyList<Supplier> newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyList<Supplier> getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSupplier(Supplier supplier) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSupplier(Supplier target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSupplier(Supplier target, Supplier editedSupplier) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Supplier> getFilteredSupplierList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredSupplierList(Predicate<Supplier> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getInventoryFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInventoryFilePath(Path inventoryFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInventory(ReadOnlyList<Good> inventory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyList<Good> getInventory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGood(Good good) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGood(Good target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGood(Good good) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int indexOfGood(Good good) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGood(Good target, Good editedGood) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Good> getFilteredGoodList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGoodList(Predicate<Good> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTransactionHistoryFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransactionHistoryFilePath(Path transactionHistoryFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransactionHistory(ReadOnlyList<Transaction> transactionHistory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyList<Transaction> getTransactionHistory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTransaction(Transaction transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTransaction(Transaction target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTransaction(Transaction transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Transaction> getFilteredTransactionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commit() {
            return;
        }

        @Override
        public void undo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redo() throws StateNotFoundException {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single supplier.
     */
    private class ModelStubWithSupplier extends ModelStub {
        private final Supplier supplier;

        ModelStubWithSupplier(Supplier supplier) {
            requireNonNull(supplier);
            this.supplier = supplier;
        }

        @Override
        public ReadOnlyList<Supplier> getAddressBook() {
            return new AddressBook();
        }

        public boolean hasSupplier(Supplier supplier) {
            requireNonNull(supplier);
            return this.supplier.isSameSupplier(supplier);
        }
    }

    /**
     * A Model stub that always accept the supplier being added.
     */
    private class ModelStubAcceptingSupplierAdded extends ModelStub {
        final ArrayList<Supplier> suppliersAdded = new ArrayList<>();

        @Override
        public boolean hasSupplier(Supplier supplier) {
            requireNonNull(supplier);
            return suppliersAdded.stream().anyMatch(supplier::isSameSupplier);
        }

        @Override
        public void addSupplier(Supplier supplier) {
            requireNonNull(supplier);
            suppliersAdded.add(supplier);
        }

        public ReadOnlyList<Supplier> getAddressBook() {
            return new AddressBook();
        }
    }

}
