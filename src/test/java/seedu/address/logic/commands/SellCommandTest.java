package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.ReadOnlyTransactionHistory;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.transaction.Transaction;

public class SellCommandTest {

    private static Good soldGood = new Good(new GoodName("Testing good"),
            new GoodQuantity("10"));
    private static Good soldGoodDiffGoodName = new Good(new GoodName("Different testing good"),
            new GoodQuantity("10"));
    private static Good soldGoodDiffGoodQuantity = new Good(new GoodName("Testing good"),
            new GoodQuantity("99"));
    private static Good existingGood = new Good(new GoodName("Testing good"),
            new GoodQuantity("10"));
    private static Good sellExistingGoodResultGood = new Good(new GoodName("Testing good"),
            new GoodQuantity("0"));

    @Test
    public void constructor_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SellCommand(null));
    }

    @Test
    public void equals() {
        SellCommand SellCommand = new SellCommand(soldGood);
        SellCommand SellCommandDiffName = new SellCommand(soldGoodDiffGoodName);
        SellCommand SellCommandDiffQty = new SellCommand(soldGoodDiffGoodQuantity);

        // same object -> returns true
        assertTrue(SellCommand.equals(SellCommand));

        // same values -> returns true
        SellCommand SellCommandCopy = new SellCommand(soldGood);
        assertTrue(SellCommand.equals(SellCommandCopy));

        // different types -> returns false
        assertFalse(SellCommand.equals(1));
        assertFalse(SellCommand.equals("string"));

        // null -> returns false
        assertFalse(SellCommand.equals(null));

        // different GoodQuantity -> returns false
        assertFalse(SellCommand.equals(SellCommandDiffQty));

        // different GoodName -> returns false
        assertFalse(SellCommand.equals(SellCommandDiffName));
    }

    @Test
    public void execute_sellExistingGood_sellSuccessful() throws CommandException {
        ModelStubWithExistingGood modelStub = new ModelStubWithExistingGood();

        CommandResult commandResult = new SellCommand(soldGood)
                .execute(modelStub);

        String expectedFeedback = String.format(SellCommand.MESSAGE_SUCCESS,
                soldGood.getGoodQuantity().goodQuantity, soldGood.getGoodName().fullGoodName);
        assertEquals(expectedFeedback, commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(sellExistingGoodResultGood), modelStub.inventory);
    }

    @Test
    public void execute_sellNonExistentGood_throwCommandException() throws CommandException {
        ModelStubEmptyInventory modelStub = new ModelStubEmptyInventory();

        SellCommand sellCommand = new SellCommand(soldGood);

        assertThrows(CommandException.class, SellCommand.MESSAGE_SELLING_NONEXISTENT_GOOD,
                () -> sellCommand.execute(modelStub));
    }


    private class ModelStubWithExistingGood extends ModelStub {
        private ArrayList<Good> inventory = new ArrayList<>();

        public ModelStubWithExistingGood() {
            inventory.add(existingGood);
        }

        @Override
        public boolean hasGood(Good good) {
            requireNonNull(good);
            return inventory.stream().anyMatch(good::isSameGood);
        }

        @Override
        public int indexOfGood(Good good) {
            return 0;
        }

        @Override
        public ObservableList<Good> getFilteredGoodList() {
            ObservableList<Good> goodsList = FXCollections.observableArrayList();
            goodsList.add(existingGood);
            return goodsList;
        }

        @Override
        public void setGood(Good target, Good editedGood) {
            // test calling this method should modify the only good in inventory
            inventory.clear();
            inventory.add(editedGood);
        }
    }

    private class ModelStubEmptyInventory extends ModelStub {
        private ArrayList<Good> inventory = new ArrayList<>();

        @Override
        public boolean hasGood(Good good) {
            requireNonNull(good);
            return inventory.stream().anyMatch(good::isSameGood);
        }

        @Override
        public void addGood(Good good) {
            inventory.add(good);
        }
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
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
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
        public void setInventory(ReadOnlyInventory inventory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyInventory getInventory() {
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
        public void setTransactionHistory(ReadOnlyTransactionHistory transactionHistory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTransactionHistory getTransactionHistory() {
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
    }
}


