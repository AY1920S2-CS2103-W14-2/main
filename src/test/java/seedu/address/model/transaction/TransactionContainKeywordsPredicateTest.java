package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.FindTransactionCommandParser.TransactionType.BUY_TRANSACTION;
import static seedu.address.logic.parser.FindTransactionCommandParser.TransactionType.EMPTY;
import static seedu.address.logic.parser.FindTransactionCommandParser.TransactionType.SELL_TRANSACTION;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.good.Good;
import seedu.address.model.supplier.Supplier;
import seedu.address.testutil.BuyTransactionBuilder;
import seedu.address.testutil.GoodBuilder;
import seedu.address.testutil.SellTransactionBuilder;
import seedu.address.testutil.SupplierBuilder;

public class TransactionContainKeywordsPredicateTest {

    private static final List<String> EMPTY_LIST = Collections.emptyList();

    private static final List<String> FIRST_SUPPLIER_LIST = Collections.singletonList("Alice");
    private static final List<String> SECOND_SUPPLIER_LIST = Arrays.asList("Alice", "Bob");
    private static final List<String> SECOND_SUPPLIER_LIST_MIXED = Arrays.asList("AlIcE", "bOb");

    private static final List<String> FIRST_GOOD_LIST = Collections.singletonList("Apple");
    private static final List<String> SECOND_GOOD_LIST = Arrays.asList("Apple", "Banana");
    private static final List<String> SECOND_GOOD_LIST_MIXED = Arrays.asList("ApPlE", "BAnaNa");

    private static final Supplier ALICE = new SupplierBuilder().withName("Alice").build();

    private static final Good APPLE = new GoodBuilder().withGoodName("Fuji Apple").build();

    private static final TransactionContainKeywordsPredicate BUY_PREDICATE =
            new TransactionContainKeywordsPredicate(BUY_TRANSACTION,
                    EMPTY_LIST, EMPTY_LIST);

    private static final TransactionContainKeywordsPredicate SELL_PREDICATE =
            new TransactionContainKeywordsPredicate(SELL_TRANSACTION,
                    EMPTY_LIST, EMPTY_LIST);

    @Test
    public void equalsTest() {

        // same object -> returns true
        assertTrue(BUY_PREDICATE.equals(BUY_PREDICATE));

        // same values -> returns true
        TransactionContainKeywordsPredicate copy =
                new TransactionContainKeywordsPredicate(BUY_TRANSACTION,
                        EMPTY_LIST, EMPTY_LIST);
        assertTrue(BUY_PREDICATE.equals(copy));

        // different types -> returns false
        assertFalse(BUY_PREDICATE.equals(1));

        // null -> returns false
        assertFalse(BUY_PREDICATE.equals(null));

        // different transaction type -> returns false
        assertFalse(BUY_PREDICATE.equals(SELL_PREDICATE));

        // different supplier names
        assertFalse(BUY_PREDICATE.equals(new TransactionContainKeywordsPredicate(BUY_TRANSACTION,
                FIRST_SUPPLIER_LIST, EMPTY_LIST)));

        // different good names
        assertFalse(BUY_PREDICATE.equals(new TransactionContainKeywordsPredicate(BUY_TRANSACTION,
                EMPTY_LIST, FIRST_GOOD_LIST)));
    }

    @Test
    public void test_transactionContainsKeywordsTypeOfTransaction_returnsTrue() {
        // buy transaction
        assertTrue(BUY_PREDICATE.test(new BuyTransactionBuilder().build()));

        // sell transaction
        assertTrue(SELL_PREDICATE.test(new SellTransactionBuilder().build()));
    }

    @Test
    public void test_transactionContainsKeywordsSupplierName_returnsTrue() {
        // One supplier keyword
        assertTrue(new TransactionContainKeywordsPredicate(EMPTY,
                FIRST_SUPPLIER_LIST, EMPTY_LIST).test(new BuyTransactionBuilder()
                .withSupplier(ALICE).build()));

        // multiple supplier keyword
        assertTrue(new TransactionContainKeywordsPredicate(EMPTY,
                SECOND_SUPPLIER_LIST, EMPTY_LIST).test(new BuyTransactionBuilder()
                .withSupplier(ALICE).build()));

        // Only one matching keyword
        assertTrue(new TransactionContainKeywordsPredicate(EMPTY,
                FIRST_SUPPLIER_LIST, EMPTY_LIST).test(new BuyTransactionBuilder()
                .withSupplier(new SupplierBuilder(ALICE).withName("Alice Lee").build()).build()));

        // Mixed-case keywords
        assertTrue(new TransactionContainKeywordsPredicate(EMPTY,
                SECOND_SUPPLIER_LIST_MIXED, EMPTY_LIST).test(new BuyTransactionBuilder()
                .withSupplier(new SupplierBuilder(ALICE).withName("Alice Lee").build()).build()));
    }

    @Test
    public void test_transactionContainsKeywordsGoodName_returnsTrue() {
        // One good keyword
        assertTrue(new TransactionContainKeywordsPredicate(EMPTY,
                EMPTY_LIST, FIRST_GOOD_LIST).test(new BuyTransactionBuilder()
                .withGood(APPLE).build()));

        // multiple good keyword
        assertTrue(new TransactionContainKeywordsPredicate(EMPTY,
                EMPTY_LIST, SECOND_GOOD_LIST).test(new BuyTransactionBuilder()
                .withGood(APPLE).build()));

        // Only one matching keyword
        assertTrue(new TransactionContainKeywordsPredicate(EMPTY,
                EMPTY_LIST, FIRST_GOOD_LIST).test(new BuyTransactionBuilder()
                .withGood(new GoodBuilder(APPLE).withGoodName("Fuji Apple").build()).build()));

        // Mixed-case keywords
        assertTrue(new TransactionContainKeywordsPredicate(EMPTY,
                EMPTY_LIST, SECOND_GOOD_LIST_MIXED).test(new BuyTransactionBuilder()
                .withGood(new GoodBuilder(APPLE).withGoodName("Fuji Apple").build()).build()));
    }

    @Test
    public void test_transactionDoesNotContainKeywords_returnsFalse() {
        // different type of transaction
        assertFalse(BUY_PREDICATE.test(new SellTransactionBuilder().build()));

        // no keyword
        TransactionContainKeywordsPredicate predicate =
                new TransactionContainKeywordsPredicate(EMPTY, EMPTY_LIST, EMPTY_LIST);
        assertTrue(predicate.test(new BuyTransactionBuilder()
                .withGood(APPLE).build()));

        // Non-matching supplier keyword
        predicate = new TransactionContainKeywordsPredicate(EMPTY, Arrays.asList("James"), EMPTY_LIST);
        assertFalse(predicate.test(new BuyTransactionBuilder().withSupplier(
                new SupplierBuilder().withName("Alice Lee").build()).build()));

        // only type of transaction matches, supplier name differs
        predicate = new TransactionContainKeywordsPredicate(BUY_TRANSACTION, Arrays.asList("James"), EMPTY_LIST);
        assertFalse(predicate.test(new BuyTransactionBuilder().withSupplier(
                new SupplierBuilder().withName("Alice Lee").build()).build()));

        // supplier name and type of transaction match, good name differs
        predicate = new TransactionContainKeywordsPredicate(BUY_TRANSACTION,
                Arrays.asList("James"), Arrays.asList("Orange"));
        assertFalse(predicate.test(new BuyTransactionBuilder()
                .withSupplier(new SupplierBuilder().withName("Alice Lee").build())
                .withGood(new GoodBuilder().withGoodName("Fuji Apple").build())
                .build()));

        // suppler name and good name match, type of transaction differs
        predicate = new TransactionContainKeywordsPredicate(SELL_TRANSACTION,
                Arrays.asList("Alice"), Arrays.asList("Apple"));
        assertFalse(predicate.test(new BuyTransactionBuilder()
                .withSupplier(new SupplierBuilder().withName("Alice Lee").build())
                .withGood(new GoodBuilder().withGoodName("Fuji Apple").build())
                .build()));

        // type of transaction and good name match, suppler name differs
        predicate = new TransactionContainKeywordsPredicate(BUY_TRANSACTION,
                Arrays.asList("Bob"), Arrays.asList("Apple"));
        assertFalse(predicate.test(new BuyTransactionBuilder()
                .withSupplier(new SupplierBuilder().withName("Alice Lee").build())
                .withGood(new GoodBuilder().withGoodName("Fuji Apple").build())
                .build()));
    }
}
