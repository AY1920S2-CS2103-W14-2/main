package cs2103_w14_2.inventory.model.offer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import cs2103_w14_2.inventory.testutil.Assert;
import org.junit.jupiter.api.Test;

public class PriceTest {

    private static final String INVALID_PRICE_NEGATIVE = "-1234.56";
    private static final String INVALID_PRICE_PLUS_SIGN = "+1234.56";
    private static final String INVALID_PRICE_ELEVEN_INTEGRAL_DIGITS = "12345678912.53";
    private static final String INVALID_PRICE_THREE_DECIMAL_PLACES = "12.123";
    private static final String INVALID_PRICE_TEXT = "one hundred";
    private static final String INVALID_PRICE_SPECIAL_CHARACTERS = "@!)#";

    private static final String VALID_PRICE_NO_DECIMAL_POINT = "2";
    private static final String VALID_PRICE_DECIMAL_POINT_ONLY = "5.";
    private static final String VALID_PRICE_ONE_DECIMAL_PLACE = "3.5";
    private static final String VALID_PRICE_TWO_DECIMAL_PLACES = "6.58";
    private static final String VALID_PRICE_TEN_INTEGRAL_DIGITS = "1234567891.12";

    private static final int VALID_CENTS = 123;
    private static final String VALID_CENTS_IN_DOLLARS = "1.23";

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void isValidPrice_null_returnsFalse() {
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));
    }

    @Test
    public void isValidPrice_emptyString_returnsFalse() {
        assertFalse(Price.isValidPrice(""));
    }

    @Test
    public void isValidPrice_negativeNumber_returnsFalse() {
        assertFalse(Price.isValidPrice(INVALID_PRICE_NEGATIVE));
    }

    @Test
    public void isValidPrice_positiveSign_returnsFalse() {
        assertFalse(Price.isValidPrice(INVALID_PRICE_PLUS_SIGN));
    }

    @Test
    public void isValidPrice_elevenDigitsIntegralPart_returnsFalse() {
        assertFalse(Price.isValidPrice(INVALID_PRICE_ELEVEN_INTEGRAL_DIGITS));
    }

    @Test
    public void isValidPrice_moreThanTwoDecimalPlaces_returnsFalse() {
        assertFalse(Price.isValidPrice(INVALID_PRICE_THREE_DECIMAL_PLACES));
    }

    @Test
    public void isValidPrice_alphabeticalText_returnsFalse() {
        assertFalse(Price.isValidPrice(INVALID_PRICE_TEXT));
    }

    @Test
    public void isValidPrice_specialCharacters_returnsFalse() {
        assertFalse(Price.isValidPrice(INVALID_PRICE_SPECIAL_CHARACTERS)); // special characters
    }

    @Test
    public void isValidPrice_validPrices_returnsTrue() {
        // valid prices
        assertTrue(Price.isValidPrice(VALID_PRICE_NO_DECIMAL_POINT));
        assertTrue(Price.isValidPrice(VALID_PRICE_DECIMAL_POINT_ONLY));
        assertTrue(Price.isValidPrice(VALID_PRICE_ONE_DECIMAL_PLACE));
        assertTrue(Price.isValidPrice(VALID_PRICE_TWO_DECIMAL_PLACES));
        assertTrue(Price.isValidPrice(VALID_PRICE_TEN_INTEGRAL_DIGITS));
    }

    @Test
    public void parseCents_validPrices_shouldConvertToCents() {
        assertEquals(new Price(VALID_CENTS_IN_DOLLARS).getCentValue(), VALID_CENTS);
    }

    @Test
    public void toString_shouldHaveDollarSignAndTwoDecimalPlaces() {
        assertEquals(new Price(VALID_PRICE_NO_DECIMAL_POINT).toString(), "$" + VALID_PRICE_NO_DECIMAL_POINT + ".00");
        assertEquals(new Price(VALID_PRICE_DECIMAL_POINT_ONLY).toString(), "$" + VALID_PRICE_DECIMAL_POINT_ONLY + "00");
        assertEquals(new Price(VALID_PRICE_ONE_DECIMAL_PLACE).toString(), "$" + VALID_PRICE_ONE_DECIMAL_PLACE + "0");
        assertEquals(new Price(VALID_PRICE_TWO_DECIMAL_PLACES).toString(), "$" + VALID_PRICE_TWO_DECIMAL_PLACES);
    }
}
