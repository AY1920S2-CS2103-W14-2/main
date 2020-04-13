package cs2103_w14_2.inventory.model.good;

import cs2103_w14_2.inventory.commons.util.AppUtil;

import static java.util.Objects.requireNonNull;
import static cs2103_w14_2.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents a Good's name in the inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidGoodName(String)}
 */
public class GoodName {

    public static final String MESSAGE_CONSTRAINTS =
            "Good's name should only contain alphanumeric characters and spaces,"
                    + "and it should not be blank or fully number";

    /*
     * The first character of the good name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullGoodName;

    /**
     * Constructs a {@code GoodName}.
     *
     * @param name A valid good name.
     */
    public GoodName(String name) {
        requireNonNull(name);
        AppUtil.checkArgument(isValidGoodName(name), MESSAGE_CONSTRAINTS);
        fullGoodName = name;
    }

    /**
     * Returns true if a given string is a valid good name.
     */
    public static boolean isValidGoodName(String test) {
        try {
            Float.parseFloat(test);
            return false;
        } catch (NumberFormatException pe) {
            return test.matches(VALIDATION_REGEX);
        }
    }


    @Override
    public String toString() {
        return fullGoodName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoodName // instanceof handles nulls
                && fullGoodName.equals(((GoodName) other).fullGoodName)); // state check
    }

    @Override
    public int hashCode() {
        return fullGoodName.hashCode();
    }

}
