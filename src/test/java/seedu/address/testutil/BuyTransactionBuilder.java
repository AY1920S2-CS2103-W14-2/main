package seedu.address.testutil;

import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalSuppliers.ALICE;

import seedu.address.model.good.Good;
import seedu.address.model.offer.Price;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.transaction.BuyTransaction;
import seedu.address.model.transaction.TransactionId;

/**
 * A utility class to help with building BuyTransaction objects.
 */
public class BuyTransactionBuilder {

    public static final String UNIQUE_BUY_ID = "dce857b1-36db-4f96-83a6-4dfc9a1e4ad9";
    private static final String VALID_PRICE_TWO_DECIMAL_PLACES = "6.58";

    private TransactionId id;
    private Good good;
    private Supplier supplier;
    private Price buyPrice;

    public BuyTransactionBuilder() {
        id = new TransactionId(UNIQUE_BUY_ID);
        good = APPLE;
        supplier = ALICE;
        buyPrice = new Price(VALID_PRICE_TWO_DECIMAL_PLACES);
    }

    /**
     * Initializes the TransactionBuilder with the data of {@code buyTransactionToCopy}.
     */
    public BuyTransactionBuilder(BuyTransaction buyTransactionToCopy) {
        id = buyTransactionToCopy.getId();
        good = buyTransactionToCopy.getGood();
        supplier = buyTransactionToCopy.getSupplier();
        buyPrice = buyTransactionToCopy.getBuyPrice();
    }

    /**
     * Sets the {@code Id} of the {@code BuyTransaction} that we are building.
     */
    public BuyTransactionBuilder withId(String id) {
        this.id = new TransactionId(id);
        return this;
    }

    /**
     * Sets the {@code Good} of the {@code BuyTransaction} that we are building.
     */
    public BuyTransactionBuilder withGood(Good good) {
        this.good = good;
        return this;
    }

    /**
     * Sets the {@code Supplier} of the {@code BuyTransaction} that we are building.
     */
    public BuyTransactionBuilder withSupplier(Supplier supplier) {
        this.supplier = supplier;
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code BuyTransaction} that we are building.
     */
    public BuyTransactionBuilder withPrice(String buyPrice) {
        this.buyPrice = new Price(buyPrice);
        return this;
    }

    public BuyTransaction build() {
        return new BuyTransaction(id, good, supplier, buyPrice);
    }
}
