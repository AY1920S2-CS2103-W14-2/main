package seedu.address.model.good;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOOD_NAME_AVOCADO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOOD_QUANTITY_ONE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalGoods.BANANA;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.GoodBuilder;

public class GoodTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Good(null, new GoodQuantity(VALID_GOOD_QUANTITY_ONE)));
        assertThrows(NullPointerException.class, () -> new Good(new GoodName(VALID_GOOD_NAME_AVOCADO),
                new GoodQuantity(VALID_GOOD_QUANTITY_ONE), null));
        assertThrows(NullPointerException.class, () -> new Good(new GoodName(VALID_GOOD_NAME_AVOCADO), null));
        assertThrows(NullPointerException.class, () -> new Good(null, null));
    }

    @Test
    public void isSameGoodTest() {
        // same object -> returns true
        assertTrue(APPLE.isSameGood(APPLE));

        // null -> returns false
        assertFalse(APPLE.isSameGood(null));

        // same good quantity, different good name -> returns false
        Good editedApple = new GoodBuilder(APPLE).withGoodName(VALID_GOOD_NAME_AVOCADO).build();
        assertFalse(APPLE.isSameGood(editedApple));

        // same good name, different quantity -> returns true
        editedApple = new GoodBuilder(APPLE).withGoodQuantity(VALID_GOOD_QUANTITY_ONE).build();
        assertTrue(APPLE.isSameGood(editedApple));

        // same good name, different threshold -> returns true
        editedApple = new GoodBuilder(APPLE).withThreshold(VALID_GOOD_QUANTITY_ONE).build();
        assertTrue(APPLE.isSameGood(editedApple));
    }

    @Test
    public void equalsTest() {
        // same values -> returns true
        Good appleCopy = new GoodBuilder(APPLE).build();
        assertTrue(APPLE.equals(appleCopy));

        // same object -> returns true
        assertTrue(APPLE.equals(APPLE));

        // null -> returns false
        assertFalse(APPLE.equals(null));

        // different type -> returns false
        assertFalse(APPLE.equals(5));

        // different good -> returns false
        assertFalse(APPLE.equals(BANANA));

        // different good name -> returns false
        Good editedApple = new GoodBuilder(APPLE).withGoodName(VALID_GOOD_NAME_AVOCADO).build();
        assertFalse(APPLE.equals(editedApple));

        // different good quantity -> returns false
        editedApple = new GoodBuilder(APPLE).withGoodQuantity(VALID_GOOD_QUANTITY_ONE).build();
        assertFalse(APPLE.equals(editedApple));

        // different threshold -> returns false
        editedApple = new GoodBuilder(APPLE).withThreshold(VALID_GOOD_QUANTITY_ONE).build();
        assertFalse(APPLE.equals(editedApple));
    }

    @Test
    public void toStringTest() {
        Good testGood = new GoodBuilder().withGoodName(VALID_GOOD_NAME_AVOCADO).build();
        assertFalse(testGood.toString().equals(VALID_GOOD_NAME_AVOCADO));
    }

}
