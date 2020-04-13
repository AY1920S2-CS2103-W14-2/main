package seedu.address.model.good;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.SupplierBuilder;

public class GoodSupplierPairContainsKeywordsPredicateTest {

    private static final String VALID_OFFER_APPLE = "fugi apple 0.05";

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        GoodSupplierPairContainsKeywordsPredicate firstPredicate =
                new GoodSupplierPairContainsKeywordsPredicate(firstPredicateKeywordList);
        GoodSupplierPairContainsKeywordsPredicate secondPredicate =
                new GoodSupplierPairContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GoodSupplierPairContainsKeywordsPredicate firstPredicateCopy =
                new GoodSupplierPairContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different good -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        GoodSupplierPairContainsKeywordsPredicate predicate =
                new GoodSupplierPairContainsKeywordsPredicate(Collections.singletonList("apple"));
        assertTrue(predicate.test(new SupplierBuilder().withOffers(VALID_OFFER_APPLE).build()));

        // Multiple keywords
        predicate = new GoodSupplierPairContainsKeywordsPredicate(Arrays.asList("apple", "fugi"));
        assertTrue(predicate.test(new SupplierBuilder().withOffers(VALID_OFFER_APPLE).build()));

        // Only one matching keyword
        predicate = new GoodSupplierPairContainsKeywordsPredicate(Arrays.asList("fugi", "potato"));
        assertTrue(predicate.test(new SupplierBuilder().withOffers(VALID_OFFER_APPLE).build()));

        // Mixed-case keywords
        predicate = new GoodSupplierPairContainsKeywordsPredicate(Arrays.asList("FuGi", "aPPle"));
        assertTrue(predicate.test(new SupplierBuilder().withOffers(VALID_OFFER_APPLE).build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GoodSupplierPairContainsKeywordsPredicate predicate =
                new GoodSupplierPairContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new SupplierBuilder().withOffers(VALID_OFFER_APPLE).build()));

        // Non-matching keyword
        predicate = new GoodSupplierPairContainsKeywordsPredicate(Arrays.asList("banana"));
        assertFalse(predicate.test(new SupplierBuilder().withOffers(VALID_OFFER_APPLE).build()));
    }

}
