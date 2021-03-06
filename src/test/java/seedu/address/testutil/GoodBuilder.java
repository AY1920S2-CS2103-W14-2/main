package seedu.address.testutil;

import static seedu.address.model.good.GoodQuantity.DEFAULT_QUANTITY;

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
    private GoodQuantity threshold;

    public GoodBuilder() {
        goodName = new GoodName(DEFAULT_GOOD_NAME);
        goodQuantity = new GoodQuantity(DEFAULT_GOOD_QUANTITY);
        threshold = new GoodQuantity(DEFAULT_QUANTITY);
    }

    /**
     * Initializes the GoodBuilder with the data of {@code goodToCopy}.
     */
    public GoodBuilder(Good goodToCopy) {
        goodName = goodToCopy.getGoodName();
        goodQuantity = goodToCopy.getGoodQuantity();
        threshold = goodToCopy.getThreshold();
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

    /**
     * Sets the {@code Threshold} of the {@code Good} that we are building.
     */
    public GoodBuilder withThreshold(int threshold) {
        this.threshold = new GoodQuantity(threshold);
        return this;
    }

    public Good build() {
        return new Good(goodName, goodQuantity, threshold);
    }

}
