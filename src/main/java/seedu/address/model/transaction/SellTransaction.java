package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.UUID;

import seedu.address.model.good.Good;
import seedu.address.model.offer.Price;

/**
 * Represents a SellTransaction in the transaction history.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class SellTransaction extends Transaction {

    private final Price sellPrice;

    public SellTransaction(UUID id, Good good, Price sellPrice) {
        super(id, good);
        requireNonNull(sellPrice);
        this.sellPrice = sellPrice;
    }

    public Price getSellPrice() {
        return sellPrice;
    }

    /**
     * Returns true if both sell transactions have id .
     * This defines a weaker notion of equality between two sell transactions.
     */
    public boolean isSameSellTransaction(SellTransaction otherSellTransaction) {
        if (otherSellTransaction == this) {
            return true;
        }

        return otherSellTransaction != null
            && otherSellTransaction.getId().equals(getId());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SellTransaction)) {
            return false;
        }

        SellTransaction otherSellTransaction = (SellTransaction) other;
        return otherSellTransaction.getId().equals(getId())
            && otherSellTransaction.getGood().equals(getGood())
            && otherSellTransaction.getSellPrice().equals(getSellPrice());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getId(), getGood(), getSellPrice());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getId())
            .append(getGood())
            .append(getSellPrice());
        return builder.toString();
    }
}
