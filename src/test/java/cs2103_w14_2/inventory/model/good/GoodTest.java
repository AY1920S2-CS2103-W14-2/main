package cs2103_w14_2.inventory.model.good;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import cs2103_w14_2.inventory.logic.commands.CommandTestUtil;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.GoodBuilder;
import cs2103_w14_2.inventory.testutil.TypicalGoods;
import org.junit.jupiter.api.Test;

public class GoodTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // null good name
        Assert.assertThrows(NullPointerException.class, () ->
                new Good(null, new GoodQuantity(CommandTestUtil.VALID_GOOD_QUANTITY_ONE),
                        new GoodQuantity(CommandTestUtil.VALID_GOOD_QUANTITY_ONE)));

        // null good quantity
        Assert.assertThrows(NullPointerException.class, () -> new Good(new GoodName(CommandTestUtil.VALID_GOOD_NAME_AVOCADO),
                null, new GoodQuantity(CommandTestUtil.VALID_GOOD_QUANTITY_ONE)));

        // null threshold
        Assert.assertThrows(NullPointerException.class, () -> new Good(new GoodName(CommandTestUtil.VALID_GOOD_NAME_AVOCADO),
                new GoodQuantity(CommandTestUtil.VALID_GOOD_QUANTITY_ONE), null));
    }

    @Test
    public void isSameGoodTest() {
        // same object -> returns true
        assertTrue(TypicalGoods.APPLE.isSameGood(TypicalGoods.APPLE));

        // null -> returns false
        assertFalse(TypicalGoods.APPLE.isSameGood(null));

        // same good quantity, different good name -> returns false
        Good editedApple = new GoodBuilder(TypicalGoods.APPLE).withGoodName(CommandTestUtil.VALID_GOOD_NAME_AVOCADO).build();
        assertFalse(TypicalGoods.APPLE.isSameGood(editedApple));

        // same good name, different quantity -> returns true
        editedApple = new GoodBuilder(TypicalGoods.APPLE).withGoodQuantity(CommandTestUtil.VALID_GOOD_QUANTITY_ONE).build();
        assertTrue(TypicalGoods.APPLE.isSameGood(editedApple));

        // same good name, different threshold -> returns true
        editedApple = new GoodBuilder(TypicalGoods.APPLE).withThreshold(CommandTestUtil.VALID_GOOD_QUANTITY_ONE).build();
        assertTrue(TypicalGoods.APPLE.isSameGood(editedApple));
    }

    @Test
    public void equalsTest() {
        // same values -> returns true
        Good appleCopy = new GoodBuilder(TypicalGoods.APPLE).build();
        assertTrue(TypicalGoods.APPLE.equals(appleCopy));

        // same object -> returns true
        assertTrue(TypicalGoods.APPLE.equals(TypicalGoods.APPLE));

        // null -> returns false
        assertFalse(TypicalGoods.APPLE.equals(null));

        // different type -> returns false
        assertFalse(TypicalGoods.APPLE.equals(5));

        // different good -> returns false
        assertFalse(TypicalGoods.APPLE.equals(TypicalGoods.BANANA));

        // different good name -> returns false
        Good editedApple = new GoodBuilder(TypicalGoods.APPLE).withGoodName(CommandTestUtil.VALID_GOOD_NAME_AVOCADO).build();
        assertFalse(TypicalGoods.APPLE.equals(editedApple));

        // different good quantity -> returns false
        editedApple = new GoodBuilder(TypicalGoods.APPLE).withGoodQuantity(CommandTestUtil.VALID_GOOD_QUANTITY_ONE).build();
        assertFalse(TypicalGoods.APPLE.equals(editedApple));

        // different threshold -> returns false
        editedApple = new GoodBuilder(TypicalGoods.APPLE).withThreshold(CommandTestUtil.VALID_GOOD_QUANTITY_ONE).build();
        assertFalse(TypicalGoods.APPLE.equals(editedApple));
    }

    @Test
    public void toStringTest() {
        Good testGood = new GoodBuilder().withGoodName(CommandTestUtil.VALID_GOOD_NAME_AVOCADO).build();
        assertFalse(testGood.toString().equals(CommandTestUtil.VALID_GOOD_NAME_AVOCADO));
    }
}
