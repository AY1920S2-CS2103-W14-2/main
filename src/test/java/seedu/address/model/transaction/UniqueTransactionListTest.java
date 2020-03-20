package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.BANANA;
import static seedu.address.testutil.TypicalTransactions.BUY_APPLE_TRANSACTION;

import org.junit.jupiter.api.Test;

import seedu.address.model.transaction.exceptions.DuplicateTransactionException;
import seedu.address.model.transaction.exceptions.TransactionNotFoundException;
import seedu.address.testutil.BuyTransactionBuilder;

public class UniqueTransactionListTest {

    private final UniqueTransactionList uniqueTransactionList = new UniqueTransactionList();

    @Test
    public void contains_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTransactionList.contains(null));
    }

    @Test
    public void contains_transactionNotInList_returnsFalse() {
        assertFalse(uniqueTransactionList.contains(BUY_APPLE_TRANSACTION));
    }

    @Test
    public void contains_transactionInList_returnsTrue() {
        uniqueTransactionList.add(BUY_APPLE_TRANSACTION);
        assertTrue(uniqueTransactionList.contains(BUY_APPLE_TRANSACTION));
    }

    @Test
    public void contains_transactionWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTransactionList.add(BUY_APPLE_TRANSACTION);
        Transaction editedAlice = new BuyTransactionBuilder(BUY_APPLE_TRANSACTION).withGood(BANANA)
            .build();
        assertTrue(uniqueTransactionList.contains(editedAlice));
    }

    @Test
    public void add_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTransactionList.add(null));
    }

    @Test
    public void add_duplicateTransaction_throwsDuplicateTransactionException() {
        uniqueTransactionList.add(BUY_APPLE_TRANSACTION);
        assertThrows(DuplicateTransactionException.class, () -> uniqueTransactionList.add(BUY_APPLE_TRANSACTION));
    }

    @Test
    public void remove_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTransactionList.remove(null));
    }

    @Test
    public void remove_transactionDoesNotExist_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> uniqueTransactionList.remove(BUY_APPLE_TRANSACTION));
    }

    @Test
    public void remove_existingTransaction_removesTransaction() {
        uniqueTransactionList.add(BUY_APPLE_TRANSACTION);
        uniqueTransactionList.remove(BUY_APPLE_TRANSACTION);
        UniqueTransactionList expectedUniqueTransactionList = new UniqueTransactionList();
        assertEquals(expectedUniqueTransactionList, uniqueTransactionList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTransactionList.asUnmodifiableObservableList().remove(0));
    }
}
