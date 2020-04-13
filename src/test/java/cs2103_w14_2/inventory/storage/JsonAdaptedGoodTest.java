package cs2103_w14_2.inventory.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static cs2103_w14_2.inventory.storage.JsonAdaptedGood.MISSING_FIELD_MESSAGE_FORMAT;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import cs2103_w14_2.inventory.commons.exceptions.IllegalValueException;
import cs2103_w14_2.inventory.model.good.GoodName;
import cs2103_w14_2.inventory.model.good.GoodQuantity;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.TypicalGoods;
import org.junit.jupiter.api.Test;

public class JsonAdaptedGoodTest {
    private static final String INVALID_GOOD_NAME = "A@pple";
    private static final int INVALID_GOOD_QUANTITY = -1;
    private static final int INVALID_GOOD_THRESHOLD = -1;

    private static final String VALID_GOOD_NAME = TypicalGoods.APPLE.getGoodName().toString();
    private static final int VALID_GOOD_QUANTITY = TypicalGoods.APPLE.getGoodQuantity().goodQuantity;
    private static final int VALID_GOOD_THRESHOLD = 100;


    @Test
    public void toModelType_validGoodDetails_returnsGood() throws Exception {
        JsonAdaptedGood good = new JsonAdaptedGood(TypicalGoods.BANANA);
        assertEquals(TypicalGoods.BANANA, good.toModelType());
    }

    @Test
    public void toModelType_invalidGoodName_throwsIllegalValueException() {
        JsonAdaptedGood good =
                new JsonAdaptedGood(INVALID_GOOD_NAME, VALID_GOOD_QUANTITY, VALID_GOOD_THRESHOLD);
        String expectedMessage = GoodName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, good::toModelType);
    }

    @Test
    public void toModelType_nullGoodName_throwsIllegalValueException() {
        JsonAdaptedGood good = new JsonAdaptedGood(null, VALID_GOOD_QUANTITY, VALID_GOOD_THRESHOLD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, GoodName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, good::toModelType);
    }

    @Test
    public void toModelType_invalidGoodQuantity_throwsIllegalValueException() {
        JsonAdaptedGood good =
                new JsonAdaptedGood(VALID_GOOD_NAME, INVALID_GOOD_QUANTITY, VALID_GOOD_THRESHOLD);
        String expectedMessage = GoodQuantity.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, good::toModelType);
    }

    @Test
    public void toModelType_invalidThreshold_throwsIllegalValueException() {
        JsonAdaptedGood good =
                new JsonAdaptedGood(VALID_GOOD_NAME, VALID_GOOD_QUANTITY, INVALID_GOOD_THRESHOLD);
        String expectedMessage = GoodQuantity.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, good::toModelType);
    }

}
