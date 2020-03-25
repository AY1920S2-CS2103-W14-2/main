package seedu.address.testutil;

import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;

/**
 * A utility class to help with building Good objects.
 */
public class GoodBuilder {

    public static final String DEFAULT_GOOD_NAME = "Fuji Apple";
    public static final int DEFAULT_GOOD_QUANTITY = 10;

    private GoodName goodName;
    private GoodQuantity goodQuantity;

    public GoodBuilder() {
        goodName = new GoodName(DEFAULT_GOOD_NAME);
        goodQuantity = new GoodQuantity(DEFAULT_GOOD_QUANTITY);
    }

    /**
     * Initializes the GoodBuilder with the data of {@code goodToCopy}.
     */
    public GoodBuilder(Good goodToCopy) {
        goodName = goodToCopy.getGoodName();
        goodQuantity = goodToCopy.getGoodQuantity();
    }

    /**
     * Sets the {@code GoodName} of the {@code Good} that we are building.
     */
    public GoodBuilder withGoodName(String goodName) {
        this.goodName = new GoodName(goodName);
        return this;
    }

    /**
     * Sets the {@code GoodQuantity} of the {@code Good} that we are building.
     */
    public GoodBuilder withGoodQuantity(int goodQuantity) {
        this.goodQuantity = new GoodQuantity(goodQuantity);
        return this;
    }

    public Good build() {
        return new Good(goodName, goodQuantity);
    }

}
