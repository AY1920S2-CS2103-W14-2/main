package seedu.address.model.offer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a price for a {@code Good} in buying or selling of goods.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {

    public static final String MESSAGE_CONSTRAINTS = "Price should only be\n"
            + "1. a positive number with\n"
            + "2. at most 2 digits after the decimal point and\n"
            + "3. at most 10 digits before the decimal point and\n"
            + "4. no special characters, including the dollar sign.";
    public static final String VALIDATION_REGEX = "^\\d{1,10}(\\.(\\d{0,2})?)?$";
    public static final String DEFAULT_PRICE = "1.0";

    final long centValue;

    /**
     * Constructs a {@code Price}
     * @param dollars the string representation of the price in dollars
     */
    public Price(String dollars) {
        requireNonNull(dollars);
        checkArgument(isValidPrice(dollars), MESSAGE_CONSTRAINTS);
        centValue = parseCents(dollars);
    }

    /**
     * Returns the value in cents of the given value in dollars.
     * @param dollars the string representation of the value in dollars
     * @return the value in cents
     */
    private long parseCents(String dollars) {
        // rounding is necessary due to floating point approximation
        double dollarValue = Double.parseDouble(dollars);
        return Math.round(dollarValue * 100);
    }

    /**
     * Returns the value of the price in cents for accurate calculations.
     * @return
     */
    public long getCentValue() {
        return centValue;
    }

    public String getValue() {
        return String.format("%d.%02d", centValue / 100, centValue % 100);
    }

    /**
     * Returns if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Price price = (Price) o;
        return getValue().equals(price.getValue());
    }

    @Override
    public String toString() {
        return String.format("$%d.%02d", centValue / 100, centValue % 100);
    }
}

