package cs2103_w14_2.inventory.model.good;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cs2103_w14_2.inventory.testutil.GoodBuilder;
import org.junit.jupiter.api.Test;

public class GoodNameContainsKeywordsPredicateTest {

    @Test
    public void equalsTest() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        GoodNameContainsKeywordsPredicate firstPredicate =
                new GoodNameContainsKeywordsPredicate(firstPredicateKeywordList);
        GoodNameContainsKeywordsPredicate secondPredicate =
                new GoodNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GoodNameContainsKeywordsPredicate firstPredicateCopy =
                new GoodNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different goods -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_goodNameContainsKeywords_returnsTrue() {
        // One keyword
        GoodNameContainsKeywordsPredicate predicate =
                new GoodNameContainsKeywordsPredicate(Collections.singletonList("Fuji"));
        assertTrue(predicate.test(new GoodBuilder().withGoodName("Fuji apple").build()));

        // Multiple keywords
        predicate = new GoodNameContainsKeywordsPredicate(Arrays.asList("Fuji", "Apple"));
        assertTrue(predicate.test(new GoodBuilder().withGoodName("Fuji Apple").build()));

        // Only one matching keyword
        predicate = new GoodNameContainsKeywordsPredicate(Arrays.asList("Fuji", "Apple"));
        assertTrue(predicate.test(new GoodBuilder().withGoodName("China Apple").build()));

        // Mixed-case keywords
        predicate = new GoodNameContainsKeywordsPredicate(Arrays.asList("fUJi", "aPPlE"));
        assertTrue(predicate.test(new GoodBuilder().withGoodName("Fuji Apple").build()));
    }

    @Test
    public void test_goodNameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GoodNameContainsKeywordsPredicate predicate = new GoodNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GoodBuilder().withGoodName("Apple").build()));

        // Non-matching keyword
        predicate = new GoodNameContainsKeywordsPredicate(Arrays.asList("Cranberry"));
        assertFalse(predicate.test(new GoodBuilder().withGoodName("Apple Banana").build()));
    }
}
