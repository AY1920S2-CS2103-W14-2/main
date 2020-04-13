package cs2103_w14_2.inventory.testutil;

import static cs2103_w14_2.inventory.testutil.TypicalGoods.APPLE;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.ALICE;

import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.transaction.BuyTransaction;
import cs2103_w14_2.inventory.model.transaction.TransactionId;
import cs2103_w14_2.inventory.model.good.Good;
import cs2103_w14_2.inventory.model.offer.Price;

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
