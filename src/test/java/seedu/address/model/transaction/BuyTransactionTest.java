package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalGoods.BANANA;
import static seedu.address.testutil.TypicalGoods.CITRUS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalTransactions.BUY_APPLE_TRANSACTION;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.model.offer.Price;
import seedu.address.testutil.BuyTransactionBuilder;

public class BuyTransactionTest {

    public static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final String DEFAULT_PRICE = "26.58";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new BuyTransaction(null, APPLE, ALICE, new Price(DEFAULT_PRICE)));
        assertThrows(NullPointerException.class, () ->
            new BuyTransaction(DEFAULT_ID, null, BENSON, new Price(DEFAULT_PRICE)));
        assertThrows(NullPointerException.class, () ->
            new BuyTransaction(DEFAULT_ID, BANANA, null, new Price(DEFAULT_PRICE)));
        assertThrows(NullPointerException.class, () ->
            new BuyTransaction(DEFAULT_ID, CITRUS, CARL, null));
    }


    @Test
    public void isSameBuyTransactionTest() {

        BuyTransaction buyTransaction = new BuyTransactionBuilder(BUY_APPLE_TRANSACTION).build();

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
        BuyTransaction buyTransactionCopy = new BuyTransactionBuilder(BUY_APPLE_TRANSACTION).build();
        assertEquals(buyTransactionCopy, new BuyTransactionBuilder(buyTransactionCopy).build());

        // same object -> returns true
        assertEquals(buyTransactionCopy, BUY_APPLE_TRANSACTION);

        // null -> returns false
        assertNotEquals(buyTransactionCopy, null);

        // different type -> returns false
        assertNotEquals(buyTransactionCopy, 5);

        // different good -> returns false
        BuyTransaction editedBuyTransaction = new BuyTransactionBuilder(BUY_APPLE_TRANSACTION)
            .withGood(BANANA).build();
        assertNotEquals(buyTransactionCopy, editedBuyTransaction);

        // different id -> returns false
        editedBuyTransaction = new BuyTransactionBuilder(BUY_APPLE_TRANSACTION)
            .withId(DEFAULT_ID).build();
        assertNotEquals(buyTransactionCopy, editedBuyTransaction);

        // different person -> returns true
        editedBuyTransaction = new BuyTransactionBuilder(BUY_APPLE_TRANSACTION)
            .withPerson(BENSON).build();
        assertNotEquals(buyTransactionCopy, editedBuyTransaction);

        // different buy price -> returns false
        editedBuyTransaction = new BuyTransactionBuilder(BUY_APPLE_TRANSACTION)
            .withPrice(DEFAULT_PRICE).build();
        assertNotEquals(buyTransactionCopy, editedBuyTransaction);
    }

}

