package seedu.address.model.good;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Good}'s {@code GoodName} matches any of the keywords given.
 */
public class GoodNameContainsKeywordsPredicate implements Predicate<Good> {
    private final List<String> keywords;

    public GoodNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Good good) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(good.getGoodName().fullGoodName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoodNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((GoodNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
