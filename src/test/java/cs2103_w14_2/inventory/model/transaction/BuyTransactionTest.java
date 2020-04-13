package cs2103_w14_2.inventory.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.APPLE;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.BANANA;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.CITRUS;

import java.util.UUID;

import cs2103_w14_2.inventory.model.offer.Price;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.BuyTransactionBuilder;
import cs2103_w14_2.inventory.testutil.TypicalSuppliers;
import cs2103_w14_2.inventory.testutil.TypicalTransactions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BuyTransactionTest {

    public static final String DEFAULT_ID = UUID.randomUUID().toString();
    private static final String DEFAULT_PRICE = "26.58";

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new BuyTransaction(null, APPLE, TypicalSuppliers.ALICE, new Price(DEFAULT_PRICE)));
        Assert.assertThrows(NullPointerException.class, () ->
                new BuyTransaction(new TransactionId(DEFAULT_ID), null, TypicalSuppliers.BENSON, new Price(DEFAULT_PRICE)));
        Assert.assertThrows(NullPointerException.class, () ->
                new BuyTransaction(new TransactionId(DEFAULT_ID), BANANA, null, new Price(DEFAULT_PRICE)));
        Assert.assertThrows(NullPointerException.class, () ->
                new BuyTransaction(new TransactionId(DEFAULT_ID), CITRUS, TypicalSuppliers.CARL, null));
    }


    @Test
    public void isSameBuyTransactionTest() {

        BuyTransaction buyTransaction = new BuyTransactionBuilder(TypicalTransactions.BUY_APPLE_TRANSACTION).build();

        // same object -> returns true
        assertTrue(buyTransaction.isSameBuyTransaction(buyTransaction));

        // null -> returns false
        assertFalse(buyTransaction.isSameBuyTransaction(null));

        // different id, same good, same person, same buy price -> returns false
        BuyTransaction editedBuyTransaction = new BuyTransactionBuilder(buyTransaction)
                .withId(DEFAULT_ID).build();
        assertFalse(buyTransaction.isSameBuyTransaction(editedBuyTransaction));

        // same id, different good, same person, same buy price ->return true
        editedBuyTransaction = new BuyTransactionBuilder(buyTransaction)
                .withGood(BANANA).build();
        assertTrue(buyTransaction.isSameBuyTransaction(editedBuyTransaction));
    }


    @Test
    public void equalsTest() {
        // same values -> returns true
        BuyTransaction buyTransactionCopy = new BuyTransactionBuilder(TypicalTransactions.BUY_APPLE_TRANSACTION).build();
        assertEquals(buyTransactionCopy, new BuyTransactionBuilder(buyTransactionCopy).build());

        // same object -> returns true
        Assertions.assertEquals(buyTransactionCopy, TypicalTransactions.BUY_APPLE_TRANSACTION);

        // null -> returns false
        assertNotEquals(buyTransactionCopy, null);

        // different type -> returns false
        assertNotEquals(buyTransactionCopy, 5);

        // different good -> returns false
        BuyTransaction editedBuyTransaction = new BuyTransactionBuilder(TypicalTransactions.BUY_APPLE_TRANSACTION)
                .withGood(BANANA).build();
        assertNotEquals(buyTransactionCopy, editedBuyTransaction);

        // different id -> returns false
        editedBuyTransaction = new BuyTransactionBuilder(TypicalTransactions.BUY_APPLE_TRANSACTION)
                .withId(DEFAULT_ID).build();
        assertNotEquals(buyTransactionCopy, editedBuyTransaction);

        // different person -> returns true
        editedBuyTransaction = new BuyTransactionBuilder(TypicalTransactions.BUY_APPLE_TRANSACTION)
                .withSupplier(TypicalSuppliers.BENSON).build();
        assertNotEquals(buyTransactionCopy, editedBuyTransaction);

        // different buy price -> returns false
        editedBuyTransaction = new BuyTransactionBuilder(TypicalTransactions.BUY_APPLE_TRANSACTION)
                .withPrice(DEFAULT_PRICE).build();
        assertNotEquals(buyTransactionCopy, editedBuyTransaction);
    }

}

