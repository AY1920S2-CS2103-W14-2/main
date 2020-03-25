package seedu.address.model.offer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.good.GoodName;

public class OfferTest {

    private static final String VALID_GOOD_BLUEBERRY = "blueberry";
    private static final String VALID_GOOD_APPLE = "apple";
    private static final String VALID_PRICE_CHEAP = "0.05";
    private static final String VALID_PRICE_EXPENSIVE = "50";

    @Test
    public void constructor_anyNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Offer(null, null));
        assertThrows(NullPointerException.class, () -> new Offer(new GoodName(VALID_GOOD_BLUEBERRY), null));
        assertThrows(NullPointerException.class, () -> new Offer(null, new Price(VALID_PRICE_CHEAP)));
    }

    @Test
    public void equals_self_returnsTrue() {
        Offer offer = new Offer(new GoodName(VALID_GOOD_APPLE), new Price(VALID_PRICE_EXPENSIVE));

        assertEquals(offer, offer);
    }

    @Test
    public void equals_sameGoodSamePrice_returnsTrue() {
        Offer offer = new Offer(new GoodName(VALID_GOOD_APPLE), new Price(VALID_PRICE_EXPENSIVE));
        Offer same = new Offer(new GoodName(VALID_GOOD_APPLE), new Price(VALID_PRICE_EXPENSIVE));

        assertEquals(offer, same);
    }

    @Test
    public void equals_sameGoodDifferentPrice_returnsTrue() {
        Offer offer = new Offer(new GoodName(VALID_GOOD_APPLE), new Price(VALID_PRICE_EXPENSIVE));
        Offer differentPrice = new Offer(new GoodName(VALID_GOOD_APPLE), new Price(VALID_PRICE_CHEAP));

        assertEquals(offer, differentPrice);
    }

    @Test
    public void equals_differentGoodSamePrice_returnsFalse() {
        Offer offer = new Offer(new GoodName(VALID_GOOD_APPLE), new Price(VALID_PRICE_EXPENSIVE));
        Offer differentGood = new Offer(new GoodName(VALID_GOOD_BLUEBERRY), new Price(VALID_PRICE_EXPENSIVE));

        assertNotEquals(offer, differentGood);
    }

    @Test
    public void equals_differentGoodDifferentPrice_returnsFalse() {
        Offer offer = new Offer(new GoodName(VALID_GOOD_APPLE), new Price(VALID_PRICE_EXPENSIVE));
        Offer offer2 = new Offer(new GoodName(VALID_GOOD_BLUEBERRY), new Price(VALID_PRICE_CHEAP));

        assertNotEquals(offer, offer2);
    }

}
