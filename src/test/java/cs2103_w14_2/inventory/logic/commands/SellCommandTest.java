package cs2103_w14_2.inventory.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.model.good.GoodName;
import cs2103_w14_2.inventory.model.good.GoodQuantity;
import cs2103_w14_2.inventory.model.offer.Price;
import cs2103_w14_2.inventory.model.transaction.Transaction;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.ModelStub;
import cs2103_w14_2.inventory.testutil.TypicalIndexes;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import cs2103_w14_2.inventory.model.good.Good;

public class SellCommandTest {
    private static final String VALID_GOOD_NAME_STRING = "Test good name";
    private static final String VALID_DIFF_GOOD_NAME_STRING = "Different Test good name";
    private static final String VALID_GOOD_QUANTITY_STRING = "10";
    private static final String VALID_DIFF_GOOD_QUANTITY_STRING = "20";
    private static final String VALID_INSUFFICIENT_QUANTITY_STRING = "5";
    private static final String VALID_ZERO_QUANTITY_STRING = "0";

    private static final String VALID_PRICE_STRING = "5.00";
    private static final String VALID_DIFF_PRICE_STRING = "6.69";

    private static final GoodName VALID_GOOD_NAME = new GoodName(VALID_GOOD_NAME_STRING);

    private static final GoodQuantity VALID_GOOD_QUANTITY = new GoodQuantity(VALID_GOOD_QUANTITY_STRING);
    private static final GoodQuantity VALID_DIFF_GOOD_QUANTITY = new GoodQuantity(VALID_DIFF_GOOD_QUANTITY_STRING);
    private static final GoodQuantity VALID_INSUFFICIENT_QUANTITY =
            new GoodQuantity(VALID_INSUFFICIENT_QUANTITY_STRING);
    private static final GoodQuantity VALID_ZERO_QUANTITY = new GoodQuantity(VALID_ZERO_QUANTITY_STRING);

    private static final Good soldGood = Good.newGoodEntry(VALID_GOOD_NAME, VALID_GOOD_QUANTITY);
    private static final Good existingGood = Good.newGoodEntry(VALID_GOOD_NAME, VALID_GOOD_QUANTITY);
    private static final Good sellExistingGoodResultGood = Good.newGoodEntry(VALID_GOOD_NAME, VALID_ZERO_QUANTITY);
    private static final Good insufficientQuantityGood =
            Good.newGoodEntry(VALID_GOOD_NAME, VALID_INSUFFICIENT_QUANTITY);

    private static final Price VALID_PRICE = new Price(VALID_PRICE_STRING);
    private static final Price VALID_DIFF_PRICE = new Price(VALID_DIFF_PRICE_STRING);

    @Test
    public void constructor_nullSupplier_throwsNullPointerException() {
        // missing quantity
        Assert.assertThrows(NullPointerException.class, () ->
                new SellCommand(null, VALID_PRICE, TypicalIndexes.INDEX_FIRST_GOOD));

        // missing price
        Assert.assertThrows(NullPointerException.class, () ->
                new SellCommand(VALID_GOOD_QUANTITY, null, TypicalIndexes.INDEX_FIRST_GOOD));

        // missing index
        Assert.assertThrows(NullPointerException.class, () ->
                new SellCommand(VALID_GOOD_QUANTITY, VALID_PRICE, null));
    }

    @Test
    public void equals() {
        SellCommand sellCommand = new SellCommand(VALID_GOOD_QUANTITY, VALID_PRICE, TypicalIndexes.INDEX_FIRST_GOOD);

        SellCommand sellCommandDiffQty = new SellCommand(VALID_DIFF_GOOD_QUANTITY, VALID_PRICE, TypicalIndexes.INDEX_FIRST_GOOD);
        SellCommand sellCommandDiffPrice = new SellCommand(VALID_GOOD_QUANTITY, VALID_DIFF_PRICE, TypicalIndexes.INDEX_FIRST_GOOD);
        SellCommand sellCommandDiffIndex = new SellCommand(VALID_GOOD_QUANTITY, VALID_PRICE, TypicalIndexes.INDEX_SECOND_GOOD);

        // same object -> returns true
        assertTrue(sellCommand.equals(sellCommand));

        // same values -> returns true
        SellCommand sellCommandCopy = new SellCommand(VALID_GOOD_QUANTITY, VALID_PRICE, TypicalIndexes.INDEX_FIRST_GOOD);
        assertTrue(sellCommand.equals(sellCommandCopy));

        // different types -> returns false
        assertFalse(sellCommand.equals(1));
        assertFalse(sellCommand.equals("string"));

        // null -> returns false
        assertFalse(sellCommand.equals(null));

        // different GoodQuantity -> returns false
        assertFalse(sellCommand.equals(sellCommandDiffQty));

        // different price -> returns false
        assertFalse(sellCommand.equals(sellCommandDiffPrice));

        // different index -> returns false
        assertFalse(sellCommand.equals(sellCommandDiffIndex));
    }

    @Test
    public void execute_sellExistingGood_sellSuccessful() throws CommandException {
        ModelStubWithExistingGood modelStub = new ModelStubWithExistingGood();

        CommandResult commandResult = new SellCommand(VALID_GOOD_QUANTITY, VALID_PRICE, TypicalIndexes.INDEX_FIRST_GOOD)
                .execute(modelStub);

        String expectedFeedback = String.format(SellCommand.MESSAGE_SUCCESS,
                soldGood.getGoodQuantity().goodQuantity, soldGood.getGoodName().fullGoodName,
                VALID_PRICE.toString());

        assertEquals(expectedFeedback, commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(sellExistingGoodResultGood), modelStub.inventory);
    }

    @Test
    public void execute_sellMoreThanInventoryQuantity_throwCommandException() {
        ModelStubInsufficientInventory modelStub = new ModelStubInsufficientInventory();

        SellCommand sellCommand = new SellCommand(VALID_GOOD_QUANTITY, VALID_PRICE, TypicalIndexes.INDEX_FIRST_GOOD);

        Assert.assertThrows(CommandException.class,
                SellCommand.MESSAGE_INSUFFICIENT_QUANTITY, () -> sellCommand.execute(modelStub));
    }

    @Test
    public void execute_validTransaction_callsModelCommit() throws CommandException {
        ModelStubCommit modelStub = new ModelStubCommit();
        modelStub.addGood(soldGood);
        new SellCommand(VALID_GOOD_QUANTITY, VALID_PRICE, TypicalIndexes.INDEX_FIRST_GOOD).execute(modelStub);

        assertTrue(modelStub.isCommitted());
    }

    private class ModelStubInsufficientInventory extends ModelStub {
        private ArrayList<Good> inventory = new ArrayList<>();

        public ModelStubInsufficientInventory() {
            inventory.add(insufficientQuantityGood);
        }

        @Override
        public boolean hasGood(Good good) {
            return inventory.stream().anyMatch(good::isSameGood);
        }

        @Override
        public int indexOfGood(Good good) {
            return 0;
        }

        @Override
        public ObservableList<Good> getFilteredGoodList() {
            ObservableList<Good> goodsList = FXCollections.observableArrayList();
            goodsList.add(insufficientQuantityGood);
            return goodsList;
        }

        @Override
        public void addTransaction(Transaction transaction) {
            //dummy method
        }
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

        @Override
        public void addTransaction(Transaction transaction) {
            //dummy method
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
}


