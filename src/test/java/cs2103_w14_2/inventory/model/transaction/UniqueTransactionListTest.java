package cs2103_w14_2.inventory.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.BANANA;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cs2103_w14_2.inventory.model.transaction.exceptions.DuplicateTransactionException;
import cs2103_w14_2.inventory.model.transaction.exceptions.TransactionNotFoundException;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.BuyTransactionBuilder;
import cs2103_w14_2.inventory.testutil.TypicalTransactions;
import org.junit.jupiter.api.Test;

public class UniqueTransactionListTest {

    private final UniqueTransactionList uniqueTransactionList = new UniqueTransactionList();

    @Test
    public void contains_nullTransaction_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueTransactionList.contains(null));
    }

    @Test
    public void contains_transactionNotInList_returnsFalse() {
        assertFalse(uniqueTransactionList.contains(TypicalTransactions.BUY_APPLE_TRANSACTION));
    }

    @Test
    public void contains_transactionInList_returnsTrue() {
        uniqueTransactionList.add(TypicalTransactions.BUY_APPLE_TRANSACTION);
        assertTrue(uniqueTransactionList.contains(TypicalTransactions.BUY_APPLE_TRANSACTION));
    }

    @Test
    public void contains_transactionWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTransactionList.add(TypicalTransactions.BUY_APPLE_TRANSACTION);
        Transaction editedAlice = new BuyTransactionBuilder(TypicalTransactions.BUY_APPLE_TRANSACTION).withGood(BANANA)
            .build();
        assertTrue(uniqueTransactionList.contains(editedAlice));
    }

    @Test
    public void add_nullTransaction_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueTransactionList.add(null));
    }

    @Test
    public void add_duplicateTransaction_throwsDuplicateTransactionException() {
        uniqueTransactionList.add(TypicalTransactions.BUY_APPLE_TRANSACTION);
        Assert.assertThrows(DuplicateTransactionException.class, () -> uniqueTransactionList.add(TypicalTransactions.BUY_APPLE_TRANSACTION));
    }

    @Test
    public void remove_nullTransaction_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueTransactionList.remove(null));
    }

    @Test
    public void remove_transactionDoesNotExist_throwsTransactionNotFoundException() {
        Assert.assertThrows(TransactionNotFoundException.class, () -> uniqueTransactionList.remove(TypicalTransactions.BUY_APPLE_TRANSACTION));
    }

    @Test
    public void remove_existingTransaction_removesTransaction() {
        uniqueTransactionList.add(TypicalTransactions.BUY_APPLE_TRANSACTION);
        uniqueTransactionList.remove(TypicalTransactions.BUY_APPLE_TRANSACTION);
        UniqueTransactionList expectedUniqueTransactionList = new UniqueTransactionList();
        assertEquals(expectedUniqueTransactionList, uniqueTransactionList);
    }

    @Test
    public void setTransactions_nullUniqueTransactionList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
            uniqueTransactionList.setTransactions((UniqueTransactionList) null));
    }

    @Test
    public void setTransactions_uniqueTransactionList_replacesOwnListWithProvidedUniqueTransactionList() {
        uniqueTransactionList.add(TypicalTransactions.BUY_APPLE_TRANSACTION);
        UniqueTransactionList expectedUniqueTransactionList = new UniqueTransactionList();
        expectedUniqueTransactionList.add(TypicalTransactions.BUY_BANANA_TRANSACTION);
        uniqueTransactionList.setTransactions(expectedUniqueTransactionList);
        assertEquals(expectedUniqueTransactionList, uniqueTransactionList);
    }

    @Test
    public void setTransactions_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueTransactionList.setTransactions((List<Transaction>) null));
    }

    @Test
    public void setTransactions_list_replacesOwnListWithProvidedList() {
        uniqueTransactionList.add(TypicalTransactions.BUY_APPLE_TRANSACTION);
        List<Transaction> transactionList = Collections.singletonList(TypicalTransactions.BUY_BANANA_TRANSACTION);
        uniqueTransactionList.setTransactions(transactionList);
        UniqueTransactionList expectedUniqueTransactionList = new UniqueTransactionList();
        expectedUniqueTransactionList.add(TypicalTransactions.BUY_BANANA_TRANSACTION);
        assertEquals(expectedUniqueTransactionList, uniqueTransactionList);
    }

    @Test
    public void setTransactions_listWithDuplicateTransactions_throwsDuplicatePersonException() {
        List<Transaction> listWithDuplicateTransactions = Arrays.asList(TypicalTransactions.BUY_APPLE_TRANSACTION, TypicalTransactions.BUY_APPLE_TRANSACTION);
        Assert.assertThrows(DuplicateTransactionException.class, () ->
            uniqueTransactionList.setTransactions(listWithDuplicateTransactions));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTransactionList.asUnmodifiableObservableList().remove(0));
    }
}
