package seedu.address.model.good;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.supplier.Supplier;

/**
 * Tests that a {@code Supplier}'s good's {@code goodName} matches any of the keywords given.
 */
public class GoodSupplierPairContainsKeywordsPredicate implements Predicate<Supplier> {
    private final List<String> keywords;

    public GoodSupplierPairContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Supplier supplier) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(supplier.getOffers().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoodSupplierPairContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((GoodSupplierPairContainsKeywordsPredicate) other).keywords)); // state check
    }

}
