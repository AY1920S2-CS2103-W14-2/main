package seedu.address.testutil;

import static seedu.address.testutil.TypicalGoods.APPLE;

import java.util.UUID;

import seedu.address.model.good.Good;
import seedu.address.model.offer.Price;
import seedu.address.model.transaction.SellTransaction;

/**
 * A utility class to help with building SellTransaction objects.
 */
public class SellTransactionBuilder {

    public static final UUID UNIQUE_SELL_ID = UUID.randomUUID();
    private static final String VALID_PRICE_TWO_DECIMAL_PLACES = "6.58";

    // id good sellPrice person

    private UUID id;
    private Good good;
    private Price sellPrice;

    public SellTransactionBuilder() {
        id = UNIQUE_SELL_ID;
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
    public SellTransactionBuilder withId(UUID id) {
        this.id = id;
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
