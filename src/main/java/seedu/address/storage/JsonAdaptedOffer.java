package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.good.GoodName;
import seedu.address.model.offer.Offer;
import seedu.address.model.offer.Price;

/**
 * Jackson-friendly version of {@link Offer}.
 */
class JsonAdaptedOffer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Offer's %s field is missing!";

    private final String good;
    private final String price;

    /**
     * Constructs a {@code JsonAdaptedOffer} with the given {@code good} and {@price}.
     */
    @JsonCreator
    public JsonAdaptedOffer(@JsonProperty("good") String good, @JsonProperty("price") String price) {
        this.good = good;
        this.price = price;
    }

    /**
     * Converts a given {@code Offer} into this class for Jackson use.
     */
    public JsonAdaptedOffer(Offer source) {
        good = source.getGoodName().fullGoodName;
        price = source.getPrice().getValue();
    }

    /**
     * Converts this Jackson-friendly adapted offer object into the model's {@code Offer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted offer.
     */
    public Offer toModelType() throws IllegalValueException {
        if (good == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    GoodName.class.getSimpleName()));
        }
        if (!GoodName.isValidGoodName(good)) {
            throw new IllegalValueException(GoodName.MESSAGE_CONSTRAINTS);
        }
        final GoodName modelGood = new GoodName(good);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        return new Offer(modelGood, modelPrice);
    }

}
