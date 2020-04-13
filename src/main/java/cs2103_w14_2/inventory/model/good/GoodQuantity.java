package cs2103_w14_2.inventory.model.good;

import cs2103_w14_2.inventory.commons.util.AppUtil;

import static cs2103_w14_2.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents a Good's quantity in the inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidGoodQuantity(String)}
 */
public class GoodQuantity {

    public static final String MESSAGE_CONSTRAINTS =
            "Good's quantity should only contain numeric characters that is greater than or equals to zero and "
                    + "less than 1 million. It should not be blank";

    // Only accept non-negative integers
    // digits can only be 1 to 9 characters long, for a value of 0 - 999,999
    public static final String VALIDATION_REGEX = "^\\d{1,6}$";
    public static final int DEFAULT_QUANTITY = 0;

    public final int goodQuantity;

    /**
     * Constructs a {@code GoodQuantity} from an int.
     *
     * @param quantity A valid quantity.
     */
    public GoodQuantity(int quantity) throws IllegalArgumentException {
        AppUtil.checkArgument(isValidGoodQuantity(String.valueOf(quantity)), MESSAGE_CONSTRAINTS);
        goodQuantity = quantity;
    }

    /**
     * Constructs a {@code GoodQuantity} from a string.
     * @param quantity
     */
    public GoodQuantity(String quantity) throws IllegalArgumentException {
        AppUtil.checkArgument(isValidGoodQuantity(quantity), MESSAGE_CONSTRAINTS);
        goodQuantity = Integer.parseInt(quantity);
    }

    /**
     * Returns true if a given string is a valid good quantity.
     * @param test
     */
    public static boolean isValidGoodQuantity(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return Integer.toString(goodQuantity);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoodQuantity // instanceof handles nulls
                && goodQuantity == (((GoodQuantity) other).goodQuantity)); // state check
    }

    @Override
    public int hashCode() {
        return Integer.toString(goodQuantity).hashCode();
    }

}
