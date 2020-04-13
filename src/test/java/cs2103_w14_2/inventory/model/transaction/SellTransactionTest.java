package cs2103_w14_2.inventory.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.APPLE;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.BANANA;

import java.util.UUID;

import cs2103_w14_2.inventory.model.offer.Price;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.SellTransactionBuilder;
import cs2103_w14_2.inventory.testutil.TypicalTransactions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SellTransactionTest {

    public static final String DEFAULT_ID = UUID.randomUUID().toString();
    private static final String DEFAULT_PRICE = "36.58";

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new SellTransaction(null, APPLE, new Price(DEFAULT_PRICE)));
        Assert.assertThrows(NullPointerException.class, () ->
                new SellTransaction(new TransactionId(DEFAULT_ID), null, new Price(DEFAULT_PRICE)));
        Assert.assertThrows(NullPointerException.class, () ->
                new SellTransaction(new TransactionId(DEFAULT_ID), APPLE, null));
    }


    @Test
    public void isSameSellTransactionTest() {

        SellTransaction sellTransaction = new SellTransactionBuilder(TypicalTransactions.SELL_APPLE_TRANSACTION).build();

        // same object -> returns true
        assertTrue(sellTransaction.isSameSellTransaction(TypicalTransactions.SELL_APPLE_TRANSACTION));

        // null -> returns false
        assertFalse(sellTransaction.isSameSellTransaction(null));

        // different id, same good, same person, same buy price -> returns false
        SellTransaction editedSellTransaction = new SellTransactionBuilder(TypicalTransactions.SELL_APPLE_TRANSACTION)
                .withId(DEFAULT_ID).build();
        assertFalse(sellTransaction.isSameSellTransaction(editedSellTransaction));

        // same id, different good, same person, same buy price ->return true
        editedSellTransaction = new SellTransactionBuilder(TypicalTransactions.SELL_APPLE_TRANSACTION)
                .withGood(BANANA).build();
        assertTrue(sellTransaction.isSameSellTransaction(editedSellTransaction));
    }


    @Test
    public void equalsTest() {
        // same values -> returns true
        SellTransaction sellTransactionCopy = new SellTransactionBuilder(TypicalTransactions.SELL_APPLE_TRANSACTION).build();
        assertEquals(sellTransactionCopy, new SellTransactionBuilder(TypicalTransactions.SELL_APPLE_TRANSACTION).build());

        // same object -> returns true
        Assertions.assertEquals(sellTransactionCopy, TypicalTransactions.SELL_APPLE_TRANSACTION);

        // null -> returns false
        assertNotEquals(sellTransactionCopy, null);

        // different type -> returns false
        assertNotEquals(sellTransactionCopy, 5);

        // different good -> returns false
        SellTransaction editedSellTransaction = new SellTransactionBuilder(TypicalTransactions.SELL_APPLE_TRANSACTION)
                .withGood(BANANA).build();
        assertNotEquals(sellTransactionCopy, editedSellTransaction);

        // different id -> returns false
        editedSellTransaction = new SellTransactionBuilder(TypicalTransactions.SELL_APPLE_TRANSACTION)
                .withId(DEFAULT_ID).build();
        assertNotEquals(sellTransactionCopy, editedSellTransaction);

        // different buy price -> returns false
        editedSellTransaction = new SellTransactionBuilder(TypicalTransactions.SELL_APPLE_TRANSACTION)
                .withPrice(DEFAULT_PRICE).build();
        assertNotEquals(sellTransactionCopy, editedSellTransaction);
    }

}

