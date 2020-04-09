package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;

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
import seedu.address.model.ReadOnlyList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.version.StateNotFoundException;
import seedu.address.testutil.SupplierBuilder;

public class BuyCommandTest {
    private static String VALID_GOOD_NAME_STRING = "Test good name";
    private static String VALID_DIFF_GOOD_NAME_STRING = "Different Test good name";
    private static String VALID_GOOD_QUANTITY_STRING = "10";
    private static String VALID_DIFF_GOOD_QUANTITY_STRING = "20";
    private static String VALID_SUPPLIER_NAME_STRING = "Test supplier";
    private static String VALID_GOOD_PRICE_STRING = "6.90";
    private static String WILL_OVERFLOW_QUANTITY_STRING = "999999";

    private static GoodName VALID_GOOD_NAME = new GoodName(VALID_GOOD_NAME_STRING);
    private static GoodName VALID_DIFF_GOOD_NAME = new GoodName(VALID_DIFF_GOOD_NAME_STRING);

    private static GoodQuantity VALID_GOOD_QUANTITY = new GoodQuantity(VALID_GOOD_QUANTITY_STRING);
    private static GoodQuantity VALID_DIFF_GOOD_QUANTITY = new GoodQuantity(VALID_DIFF_GOOD_QUANTITY_STRING);
    private static GoodQuantity WILL_OVERFLOW_QUANTITY = new GoodQuantity(WILL_OVERFLOW_QUANTITY_STRING);

    private static Good boughtGood = Good.newGoodEntry(VALID_GOOD_NAME, VALID_GOOD_QUANTITY);
    private static Good boughtGoodDiffGoodName = Good.newGoodEntry(VALID_DIFF_GOOD_NAME, VALID_GOOD_QUANTITY);
    private static Good boughtGoodDiffGoodQuantity = Good.newGoodEntry(VALID_GOOD_NAME, VALID_DIFF_GOOD_QUANTITY);
    private static Good existingGood = Good.newGoodEntry(VALID_GOOD_NAME, VALID_GOOD_QUANTITY);
    private static Good buyExistingGoodResultGood = Good.newGoodEntry(VALID_GOOD_NAME, VALID_DIFF_GOOD_QUANTITY);

    private static Supplier supplierSellingBoughtGood = new SupplierBuilder()
            .withName(VALID_SUPPLIER_NAME_STRING)
            .withOffers(VALID_GOOD_NAME_STRING + " " + VALID_GOOD_PRICE_STRING)
            .build();

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new BuyCommand(null, VALID_GOOD_QUANTITY, INDEX_FIRST_SUPPLIER));

        assertThrows(NullPointerException.class,
                () -> new BuyCommand(VALID_GOOD_NAME, null, INDEX_FIRST_SUPPLIER));

        assertThrows(NullPointerException.class,
                () -> new BuyCommand(VALID_GOOD_NAME, VALID_GOOD_QUANTITY, null));
    }

    @Test
    public void equals() {
        BuyCommand buyCommand = new BuyCommand(VALID_GOOD_NAME, VALID_GOOD_QUANTITY, INDEX_FIRST_SUPPLIER);
        BuyCommand buyCommandDiffName = new BuyCommand(VALID_DIFF_GOOD_NAME, VALID_GOOD_QUANTITY, INDEX_FIRST_SUPPLIER);
        BuyCommand buyCommandDiffQty = new BuyCommand(VALID_GOOD_NAME, VALID_DIFF_GOOD_QUANTITY, INDEX_FIRST_SUPPLIER);
        BuyCommand buyCommandDiffIndex = new BuyCommand(VALID_GOOD_NAME, VALID_GOOD_QUANTITY, INDEX_SECOND_SUPPLIER);

        // same object -> returns true
        assertTrue(buyCommand.equals(buyCommand));

        // same values -> returns true
        BuyCommand buyCommandCopy = new BuyCommand(VALID_GOOD_NAME, VALID_GOOD_QUANTITY, INDEX_FIRST_SUPPLIER);
        assertTrue(buyCommand.equals(buyCommandCopy));

        // different types -> returns false
        assertFalse(buyCommand.equals(1));
        assertFalse(buyCommand.equals("string"));

        // null -> returns false
        assertFalse(buyCommand.equals(null));

        // different GoodQuantity -> returns false
        assertFalse(buyCommand.equals(buyCommandDiffQty));

        // different GoodName -> returns false
        assertFalse(buyCommand.equals(buyCommandDiffName));

        // different Index -> returns false
        assertFalse(buyCommand.equals(buyCommandDiffIndex));
    }

    @Test
    public void execute_buyExistingGood_buySuccessful() throws CommandException {
        ModelStubWithExistingGood modelStub = new ModelStubWithExistingGood();

        CommandResult commandResult = new BuyCommand(VALID_GOOD_NAME, VALID_GOOD_QUANTITY, INDEX_FIRST_SUPPLIER)
                .execute(modelStub);

        String expectedFeedback = String.format(BuyCommand.MESSAGE_SUCCESS, boughtGood.getGoodQuantity().goodQuantity,
                boughtGood.getGoodName().fullGoodName, VALID_GOOD_PRICE_STRING);

        assertEquals(expectedFeedback, commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(buyExistingGoodResultGood), modelStub.inventory);
    }

    @Test
    public void execute_buyNewGood_buySuccessful() throws CommandException {
        ModelStubEmptyInventory modelStub = new ModelStubEmptyInventory();

        CommandResult commandResult = new BuyCommand(VALID_GOOD_NAME, VALID_GOOD_QUANTITY, INDEX_FIRST_SUPPLIER)
                .execute(modelStub);

        String expectedFeedback = String.format(BuyCommand.MESSAGE_SUCCESS, boughtGood.getGoodQuantity().goodQuantity,
                boughtGood.getGoodName().fullGoodName, VALID_GOOD_PRICE_STRING);

        assertEquals(expectedFeedback, commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(boughtGood), modelStub.inventory);
    }

    @Test
    public void execute_buyOverflowInventory_throwsCommandException() throws CommandException {
        ModelStubWithExistingGood modelStub = new ModelStubWithExistingGood();

        BuyCommand buyCommand = new BuyCommand(VALID_GOOD_NAME, WILL_OVERFLOW_QUANTITY, INDEX_FIRST_SUPPLIER);

        assertThrows(CommandException.class, () -> buyCommand.execute(modelStub));
    }

    @Test
    public void execute_validTransaction_callsModelCommit() throws CommandException {
        ModelStubWithExistingGood modelStub = new ModelStubWithExistingGood();
        new BuyCommand(VALID_GOOD_NAME, VALID_GOOD_QUANTITY, INDEX_FIRST_SUPPLIER).execute(modelStub);

        assertTrue(modelStub.isCommitted());
    }

    private class ModelStubWithExistingGood extends ModelStub {
        private ArrayList<Good> inventory = new ArrayList<>();
        private boolean isCommitted = false;

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

        @Override
        public ObservableList<Supplier> getFilteredSupplierList() {
            ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
            supplierList.add(supplierSellingBoughtGood);
            return supplierList;
        }

        @Override
        public void commit() {
            this.isCommitted = true;
        }

        public boolean isCommitted() {
            return this.isCommitted;
        }

        @Override
        public void addTransaction(Transaction transaction) {
            // dummy method
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

        @Override
        public ObservableList<Supplier> getFilteredSupplierList() {
            ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
            supplierList.add(supplierSellingBoughtGood);
            return supplierList;
        }

        @Override
        public void addTransaction(Transaction transaction) {
            // dummy method
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

}

