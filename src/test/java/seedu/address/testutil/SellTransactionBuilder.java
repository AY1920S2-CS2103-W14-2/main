package seedu.address.testutil;

import static seedu.address.testutil.TypicalGoods.APPLE;

import seedu.address.model.good.Good;
import seedu.address.model.offer.Price;
import seedu.address.model.transaction.SellTransaction;
import seedu.address.model.transaction.TransactionId;

/**
 * A utility class to help with building SellTransaction objects.
 */
public class SellTransactionBuilder {

    public static final String UNIQUE_SELL_ID = "cbd83eae-1e34-4c6e-90c1-e9fbc3d0e3ef";
    private static final String VALID_PRICE_TWO_DECIMAL_PLACES = "16.58";

    private TransactionId id;
    private Good good;
    private Price sellPrice;

    public SellTransactionBuilder() {
        id = new TransactionId(UNIQUE_SELL_ID);
        good = APPLE;
        sellPrice = new Price(VALID_PRICE_TWO_DECIMAL_PLACES);
    }

    /**
     * Initializes the TransactionBuilder with the data of {@code sellTransactionToCopy}.
     */
    public SellTransactionBuilder(SellTransaction sellTransactionToCopy) {
        id = sellTransactionToCopy.getId();
        good = sellTransactionToCopy.getGood();
        sellPrice = sellTransactionToCopy.getSellPrice();
    }

    /**
     * Sets the {@code Id} of the {@code SellTransaction} that we are building.
     */
    public SellTransactionBuilder withId(String id) {
        this.id = new TransactionId(id);
        return this;
    }

    /**
     * Sets the {@code Good} of the {@code SellTransaction} that we are building.
     */
    public SellTransactionBuilder withGood(Good good) {
        this.good = good;
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code SellTransaction} that we are building.
     */
    public SellTransactionBuilder withPrice(String sellPrice) {
        this.sellPrice = new Price(sellPrice);
        return this;
    }

    public SellTransaction build() {
        return new SellTransaction(id, good, sellPrice);
    }
}
