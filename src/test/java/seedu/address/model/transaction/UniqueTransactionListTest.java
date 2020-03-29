package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.BANANA;
import static seedu.address.testutil.TypicalTransactions.BUY_APPLE_TRANSACTION;
import static seedu.address.testutil.TypicalTransactions.BUY_BANANA_TRANSACTION;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public void setTransactions_nullUniqueTransactionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueTransactionList.setTransactions((UniqueTransactionList) null));
    }

    @Test
    public void setTransactions_uniqueTransactionList_replacesOwnListWithProvidedUniqueTransactionList() {
        uniqueTransactionList.add(BUY_APPLE_TRANSACTION);
        UniqueTransactionList expectedUniqueTransactionList = new UniqueTransactionList();
        expectedUniqueTransactionList.add(BUY_BANANA_TRANSACTION);
        uniqueTransactionList.setTransactions(expectedUniqueTransactionList);
        assertEquals(expectedUniqueTransactionList, uniqueTransactionList);
    }

    @Test
    public void setTransactions_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTransactionList.setTransactions((List<Transaction>) null));
    }

    @Test
    public void setTransactions_list_replacesOwnListWithProvidedList() {
        uniqueTransactionList.add(BUY_APPLE_TRANSACTION);
        List<Transaction> transactionList = Collections.singletonList(BUY_BANANA_TRANSACTION);
        uniqueTransactionList.setTransactions(transactionList);
        UniqueTransactionList expectedUniqueTransactionList = new UniqueTransactionList();
        expectedUniqueTransactionList.add(BUY_BANANA_TRANSACTION);
        assertEquals(expectedUniqueTransactionList, uniqueTransactionList);
    }

    @Test
    public void setTransactions_listWithDuplicateTransactions_throwsDuplicatePersonException() {
        List<Transaction> listWithDuplicateTransactions = Arrays.asList(BUY_APPLE_TRANSACTION, BUY_APPLE_TRANSACTION);
        assertThrows(DuplicateTransactionException.class, () ->
            uniqueTransactionList.setTransactions(listWithDuplicateTransactions));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTransactionList.asUnmodifiableObservableList().remove(0));
    }
}
