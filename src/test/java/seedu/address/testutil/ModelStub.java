package seedu.address.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.good.Good;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.version.StateNotFoundException;

/**
 * A default model stub that have all of the methods failing.
 *
 * Test cases that require a more specialized ModelStub should extend this class
 * and implement only the methods required.
 */
public class ModelStub implements Model {

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
    public void setAddressBook(ReadOnlyList<Supplier> addressBook) {
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
    public void addSupplier(Supplier supplier) {
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
    public void undo() throws StateNotFoundException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void redo() throws StateNotFoundException {
        throw new AssertionError("This method should not be called.");
    }
}
