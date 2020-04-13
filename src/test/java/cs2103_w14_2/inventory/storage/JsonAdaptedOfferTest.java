package cs2103_w14_2.inventory.storage;

import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import cs2103_w14_2.inventory.commons.exceptions.IllegalValueException;
import cs2103_w14_2.inventory.model.good.GoodName;
import cs2103_w14_2.inventory.model.offer.Price;
import cs2103_w14_2.inventory.testutil.Assert;
import org.junit.jupiter.api.Test;

public class JsonAdaptedOfferTest {

    private static final String VALID_GOOD_NAME = "blueberry";
    private static final String VALID_PRICE = "5.00";

    private static final String INVALID_GOOD_NAME = "inv@lid";
    private static final String INVALID_PRICE = "-55";

    @Test
    public void toModelType_anyNull_throwsIllegalValueException() {
        // if both null, GoodName takes precedence
        JsonAdaptedOffer offer = new JsonAdaptedOffer(null, null);
        String expectedMessage = String.format(JsonAdaptedOffer.MISSING_FIELD_MESSAGE_FORMAT,
                GoodName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, offer::toModelType);

        // null good name
        offer = new JsonAdaptedOffer(null, VALID_PRICE);
        expectedMessage = String.format(JsonAdaptedOffer.MISSING_FIELD_MESSAGE_FORMAT, GoodName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, offer::toModelType);

        // null price
        offer = new JsonAdaptedOffer(VALID_GOOD_NAME, null);
        expectedMessage = String.format(JsonAdaptedOffer.MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, offer::toModelType);
    }

    @Test
    public void toModelType_invalidGoodName_throwsIllegalValueException() {
        JsonAdaptedOffer offer = new JsonAdaptedOffer(INVALID_GOOD_NAME, VALID_PRICE);
        String expectedMessage = GoodName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, offer::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedOffer offer = new JsonAdaptedOffer(VALID_GOOD_NAME, INVALID_PRICE);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, offer::toModelType);
    }

    @Test
    public void toModelType_invalidGoodNameAndInvalidPrice_throwsIllegalValueExceptionForGoodName() {
        JsonAdaptedOffer offer = new JsonAdaptedOffer(INVALID_GOOD_NAME, INVALID_PRICE);
        String expectedMessage = GoodName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, offer::toModelType);
    }
}
