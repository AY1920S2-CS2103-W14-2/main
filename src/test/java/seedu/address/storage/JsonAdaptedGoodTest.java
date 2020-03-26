package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedGood.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalGoods.BANANA;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;

public class JsonAdaptedGoodTest {
    private static final String INVALID_GOOD_NAME = "A@pple";
    private static final int INVALID_GOOD_QUANTITY = -1;
    private static final int INVALID_GOOD_THRESHOLD = -1;

    private static final String VALID_GOOD_NAME = APPLE.getGoodName().toString();
    private static final int VALID_GOOD_QUANTITY = APPLE.getGoodQuantity().goodQuantity;
    private static final int VALID_GOOD_THRESHOLD = 100;


    @Test
    public void toModelType_validGoodDetails_returnsGood() throws Exception {
        JsonAdaptedGood good = new JsonAdaptedGood(BANANA);
        assertEquals(BANANA, good.toModelType());
    }

    @Test
    public void toModelType_invalidGoodName_throwsIllegalValueException() {
        JsonAdaptedGood good =
                new JsonAdaptedGood(INVALID_GOOD_NAME, VALID_GOOD_QUANTITY, VALID_GOOD_THRESHOLD);
        String expectedMessage = GoodName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, good::toModelType);
    }

    @Test
    public void toModelType_nullGoodName_throwsIllegalValueException() {
        JsonAdaptedGood good = new JsonAdaptedGood(null, VALID_GOOD_QUANTITY, VALID_GOOD_THRESHOLD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, GoodName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, good::toModelType);
    }

    @Test
    public void toModelType_invalidGoodQuantity_throwsIllegalValueException() {
        JsonAdaptedGood good =
                new JsonAdaptedGood(VALID_GOOD_NAME, INVALID_GOOD_QUANTITY, VALID_GOOD_THRESHOLD);
        String expectedMessage = GoodQuantity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, good::toModelType);
    }

    @Test
    public void toModelType_invalidThreshold_throwsIllegalValueException() {
        JsonAdaptedGood good =
                new JsonAdaptedGood(VALID_GOOD_NAME, VALID_GOOD_QUANTITY, INVALID_GOOD_THRESHOLD);
        String expectedMessage = GoodQuantity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, good::toModelType);
    }

}
