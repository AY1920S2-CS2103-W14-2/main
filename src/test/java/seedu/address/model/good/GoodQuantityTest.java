package seedu.address.model.good;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOOD_QUANTITY_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOOD_QUANTITY_ZERO;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GoodQuantityTest {

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        int invalidGoodQuantity = -1;
        assertThrows(IllegalArgumentException.class, () -> new GoodQuantity(invalidGoodQuantity));
    }

    @Test
    public void isValidGoodQuantity() {
        // invalid quantity
        assertFalse(GoodQuantity.isValidGoodQuantity(String.valueOf(-1))); // negative value

        // valid quantity
        assertTrue(GoodQuantity.isValidGoodQuantity(String.valueOf(0)));
        assertTrue(GoodQuantity.isValidGoodQuantity(String.valueOf(1)));
        assertTrue(GoodQuantity.isValidGoodQuantity(String.valueOf(+1))); // positive value with plus sign
        assertTrue(GoodQuantity.isValidGoodQuantity(String.valueOf(100)));
    }

    @Test
    public void equals() {
        GoodQuantity sampleGoodQuantity = new GoodQuantity(VALID_GOOD_QUANTITY_ONE);
        assertFalse(sampleGoodQuantity.equals(new GoodQuantity(VALID_GOOD_QUANTITY_ZERO)));

        assertTrue(sampleGoodQuantity.equals(new GoodQuantity(VALID_GOOD_QUANTITY_ONE)));
    }

    @Test
    public void toStringTest() {
        GoodQuantity sampleGoodQuantity = new GoodQuantity(VALID_GOOD_QUANTITY_ONE);
        assertEquals(sampleGoodQuantity.toString(), String.valueOf(VALID_GOOD_QUANTITY_ONE));

        assertNotEquals(sampleGoodQuantity.toString(), String.valueOf(VALID_GOOD_QUANTITY_ZERO));
    }
}
