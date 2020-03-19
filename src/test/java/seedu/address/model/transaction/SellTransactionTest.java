package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalGoods.BANANA;
import static seedu.address.testutil.TypicalTransactions.SELL_APPLE_TRANSACTION;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.model.offer.Price;
import seedu.address.testutil.SellTransactionBuilder;

public class SellTransactionTest {

    public static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final String DEFAULT_PRICE = "26.58";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new SellTransaction(null, APPLE, new Price(DEFAULT_PRICE)));
        assertThrows(NullPointerException.class, () ->
            new SellTransaction(DEFAULT_ID, null, new Price(DEFAULT_PRICE)));
        assertThrows(NullPointerException.class, () ->
            new SellTransaction(DEFAULT_ID, APPLE, null));
    }


    @Test
    public void isSameSellTransactionTest() {

        SellTransaction sellTransaction = new SellTransactionBuilder(SELL_APPLE_TRANSACTION).build();

        // same object -> returns true
        assertTrue(sellTransaction.isSameSellTransaction(SELL_APPLE_TRANSACTION));

        // null -> returns false
        assertFalse(sellTransaction.isSameSellTransaction(null));

        // different id, same good, same person, same buy price -> returns false
        SellTransaction editedSellTransaction = new SellTransactionBuilder(SELL_APPLE_TRANSACTION)
            .withId(DEFAULT_ID).build();
        assertFalse(sellTransaction.isSameSellTransaction(editedSellTransaction));

        // same id, different good, same person, same buy price ->return true
        editedSellTransaction = new SellTransactionBuilder(SELL_APPLE_TRANSACTION)
            .withGood(BANANA).build();
        assertTrue(sellTransaction.isSameSellTransaction(editedSellTransaction));
    }


    @Test
    public void equalsTest() {
        // same values -> returns true
        SellTransaction sellTransactionCopy = new SellTransactionBuilder(SELL_APPLE_TRANSACTION).build();
        assertEquals(sellTransactionCopy, new SellTransactionBuilder(SELL_APPLE_TRANSACTION).build());

        // same object -> returns true
        assertEquals(sellTransactionCopy, SELL_APPLE_TRANSACTION);

        // null -> returns false
        assertNotEquals(sellTransactionCopy, null);

        // different type -> returns false
        assertNotEquals(sellTransactionCopy, 5);

        // different good -> returns false
        SellTransaction editedSellTransaction = new SellTransactionBuilder(SELL_APPLE_TRANSACTION)
            .withGood(BANANA).build();
        assertNotEquals(sellTransactionCopy, editedSellTransaction);

        // different id -> returns false
        editedSellTransaction = new SellTransactionBuilder(SELL_APPLE_TRANSACTION)
            .withId(DEFAULT_ID).build();
        assertNotEquals(sellTransactionCopy, editedSellTransaction);

        // different buy price -> returns false
        editedSellTransaction = new SellTransactionBuilder(SELL_APPLE_TRANSACTION)
            .withPrice(DEFAULT_PRICE).build();
        assertNotEquals(sellTransactionCopy, editedSellTransaction);
    }

}

