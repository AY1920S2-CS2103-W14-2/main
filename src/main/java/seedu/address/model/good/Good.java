package seedu.address.model.good;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Good in the inventory.
 * Guarantees: details are present and not null, field values are validated, immutable.
 * Method summary:
 * Currently, the good class works as empty object for the inventory.
 * More methods may be added to enhance the functionality.
 */
public class Good {

    public static final String MESSAGE_CONSTRAINTS =
            "Good should contain valid good name and valid good quantity.";

    // Identity fields
    private final GoodName goodName;
    private final GoodQuantity goodQuantity;

    public Good(GoodName goodName, GoodQuantity goodQuantity) {
        requireAllNonNull(goodName, goodQuantity);
        this.goodName = goodName;
        this.goodQuantity = goodQuantity;
    }

    public GoodName getGoodName() {
        return goodName;
    }

    public GoodQuantity getGoodQuantity() {
        return goodQuantity;
    }

    /**
     * Returns true if a given good is a valid good.
     */
    public static boolean isValidGood(Good test) {
        return GoodName.isValidGoodName(test.getGoodName().toString())
                && GoodQuantity.isValidGoodQuantity(test.getGoodQuantity().toString());
    }

    /**
     * Returns true if both goods have the same name.
     */
    public boolean isSameGood(Good otherGood) {
        if (otherGood == this) {
            return true;
        }

        return otherGood != null
                && otherGood.getGoodName().equals(getGoodName());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Good)) {
            return false;
        }

        Good otherGood = (Good) other;
        return otherGood.getGoodName().equals(getGoodName())
                && otherGood.goodQuantity.equals(getGoodQuantity());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(goodName, goodQuantity);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getGoodName())
                .append(goodQuantity);
        return builder.toString();
    }
}
