package cs2103_w14_2.inventory.model.good;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import cs2103_w14_2.inventory.logic.commands.CommandTestUtil;
import cs2103_w14_2.inventory.testutil.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GoodNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new GoodName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new GoodName(invalidName));
    }

    @Test
    public void isValidGoodName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> GoodName.isValidGoodName(null));

        // invalid name
        assertFalse(GoodName.isValidGoodName("")); // empty string
        assertFalse(GoodName.isValidGoodName(" ")); // spaces only
        assertFalse(GoodName.isValidGoodName("^")); // only non-alphanumeric characters
        assertFalse(GoodName.isValidGoodName("apple*")); // contains non-alphanumeric characters
        assertFalse(GoodName.isValidGoodName("12345")); // numbers only

        // valid name
        assertTrue(GoodName.isValidGoodName("fuji apple")); // alphabets only
        assertTrue(GoodName.isValidGoodName("2nd Gen fuji apple")); // alphanumeric characters
        assertTrue(GoodName.isValidGoodName("Fuji apple")); // with capital letters
        assertTrue(GoodName.isValidGoodName("fuji apple with very very very long name")); // long names
    }

    @Test
    public void equalsTest() {
        GoodName sampleGoodName = new GoodName(CommandTestUtil.VALID_GOOD_NAME_AVOCADO);
        assertFalse(sampleGoodName.equals(new GoodName(CommandTestUtil.VALID_GOOD_NAME_BLUEBERRY)));

        assertTrue(sampleGoodName.equals(new GoodName(CommandTestUtil.VALID_GOOD_NAME_AVOCADO)));
    }

    @Test
    public void toStringTest() {
        GoodName sampleGoodName = new GoodName(CommandTestUtil.VALID_GOOD_NAME_AVOCADO);
        Assertions.assertEquals(sampleGoodName.toString(), CommandTestUtil.VALID_GOOD_NAME_AVOCADO);

        Assertions.assertNotEquals(sampleGoodName.toString(), CommandTestUtil.VALID_GOOD_NAME_BLUEBERRY);
    }
}
