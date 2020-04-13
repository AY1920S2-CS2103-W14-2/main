package cs2103_w14_2.inventory.model.supplier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import cs2103_w14_2.inventory.testutil.Assert;
import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("12345")); // numbers only

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
