package cs2103_w14_2.inventory.model.transaction;

import static cs2103_w14_2.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import cs2103_w14_2.inventory.commons.util.CollectionUtil;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.good.Good;
import cs2103_w14_2.inventory.model.offer.Price;

/**
 * Represents a BuyTransaction in the transaction history.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class BuyTransaction extends Transaction {
    // Identity fields
    private final Supplier supplier;
    private final Price buyPrice;

    public BuyTransaction(TransactionId transactionId, Good good, Supplier supplier, Price buyPrice) {
        super(transactionId, good);
        CollectionUtil.requireAllNonNull(supplier, buyPrice);
        this.supplier = supplier;
        this.buyPrice = buyPrice;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Price getBuyPrice() {
        return buyPrice;
    }

    /**
     * Returns true if both buy transactions have id .
     * This defines a weaker notion of equality between two buy transactions.
     */
    public boolean isSameBuyTransaction(BuyTransaction otherBuyTransaction) {
        if (otherBuyTransaction == this) {
            return true;
        }

        return otherBuyTransaction != null
                && otherBuyTransaction.getId().equals(getId());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BuyTransaction)) {
            return false;
        }

        BuyTransaction otherBuyTransaction = (BuyTransaction) other;
        return otherBuyTransaction.getId().equals(getId())
                && otherBuyTransaction.getGood().equals(getGood())
                && otherBuyTransaction.getSupplier().equals(getSupplier())
                && otherBuyTransaction.getBuyPrice().equals(getBuyPrice());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getId(), getGood(), getSupplier(), getBuyPrice());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getId())
                .append(getGood())
                .append(getSupplier())
                .append(getBuyPrice());
        return builder.toString();
    }
}
