package cs2103_w14_2.inventory.model.good;

import cs2103_w14_2.inventory.commons.util.CollectionUtil;

import static java.util.Objects.requireNonNull;
import static cs2103_w14_2.inventory.commons.util.CollectionUtil.requireAllNonNull;

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
    private final GoodQuantity threshold;

    /**
     * Constructor for creating a complete Good class.
     *
     * @param goodName name of good
     * @param goodQuantity quantity of good
     * @param threshold threshold quantity before warning
     */
    public Good(GoodName goodName, GoodQuantity goodQuantity, GoodQuantity threshold) {
        CollectionUtil.requireAllNonNull(goodName, goodQuantity, threshold);

        this.goodName = goodName;
        this.goodQuantity = goodQuantity;
        this.threshold = threshold;
    }

    /**
     * Constructor for creating a new good entry. It will have the given goodName and goodQuantity,
     * but start with the default threshold value of o
     *
     * @param goodName name of good
     * @param goodQuantity quantity of good
     * @return Good created with the given good name and quantity, but with a threshold of 0
     */
    public static Good newGoodEntry(GoodName goodName, GoodQuantity goodQuantity) {
        CollectionUtil.requireAllNonNull(goodName, goodQuantity);

        return new Good(goodName, goodQuantity, new GoodQuantity(GoodQuantity.DEFAULT_QUANTITY));
    }

    /**
     * Returns a Good that has the given goodName. This is mainly for querying the existence of
     * a good in the inventory, where only the goodName is checked, and other fields are redundant.
     *
     * @param goodName name of good
     * @return Good created with the given good name, but a default good quantity and threshold value of 0
     */
    public static Good goodWithName(GoodName goodName) {
        requireNonNull(goodName);

        return new Good(goodName, new GoodQuantity(GoodQuantity.DEFAULT_QUANTITY), new GoodQuantity(GoodQuantity.DEFAULT_QUANTITY));
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

    public GoodQuantity getThreshold() {
        return threshold;
    }

    /**
     * Checks whether the quantity of the good is lower or equal to the threshold.
     *
     * @return true if lower than or equals to the threshold.
     */
    public boolean isNoMoreThanThresholdQuantity() {
        return getGoodQuantity().goodQuantity <= getThreshold().goodQuantity;
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
                && otherGood.getGoodQuantity().equals(getGoodQuantity())
                && otherGood.getThreshold().equals(getThreshold());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(goodName, goodQuantity, threshold);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getGoodName())
                .append(" Quantity: " + goodQuantity)
                .append(" Threshold: " + threshold);
        return builder.toString();
    }
}
