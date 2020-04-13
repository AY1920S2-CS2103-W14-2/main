package cs2103_w14_2.inventory.model.supplier;

import java.util.List;
import java.util.function.Predicate;

import cs2103_w14_2.inventory.commons.util.StringUtil;

/**
 * Tests that a {@code Supplier}'s {@code Name} matches any of the keywords given.
 */
public class SupplierNameContainsKeywordsPredicate implements Predicate<Supplier> {
    private final List<String> keywords;

    public SupplierNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Supplier supplier) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(supplier.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SupplierNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SupplierNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
