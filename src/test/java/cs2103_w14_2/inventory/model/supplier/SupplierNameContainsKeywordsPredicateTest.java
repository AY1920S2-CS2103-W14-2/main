package cs2103_w14_2.inventory.model.supplier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.testutil.SupplierBuilder;

public class SupplierNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        SupplierNameContainsKeywordsPredicate firstPredicate =
                new SupplierNameContainsKeywordsPredicate(firstPredicateKeywordList);
        SupplierNameContainsKeywordsPredicate secondPredicate =
                new SupplierNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SupplierNameContainsKeywordsPredicate firstPredicateCopy =
                new SupplierNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different supplier -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        SupplierNameContainsKeywordsPredicate predicate =
                new SupplierNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new SupplierBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new SupplierNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new SupplierBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new SupplierNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new SupplierBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new SupplierNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new SupplierBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SupplierNameContainsKeywordsPredicate predicate =
                new SupplierNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new SupplierBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new SupplierNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new SupplierBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new SupplierNameContainsKeywordsPredicate(Arrays.asList("12345",
                "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new SupplierBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
