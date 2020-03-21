package seedu.address.model.transaction;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.good.Good;
import seedu.address.model.offer.Price;
import seedu.address.model.person.Person;

/**
 * Represents a BuyTransaction in the transaction history.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class BuyTransaction extends Transaction {
    // Identity fields
    private final Person person;
    private final Price buyPrice;

    public BuyTransaction(TransactionId transactionId, Good good, Person person, Price buyPrice) {
        super(transactionId, good);
        requireAllNonNull(person, buyPrice);
        this.person = person;
        this.buyPrice = buyPrice;
    }

    public Person getPerson() {
        return person;
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
            System.out.println("herhe123");
            return false;
        }

        BuyTransaction otherBuyTransaction = (BuyTransaction) other;
        return otherBuyTransaction.getId().equals(getId())
                && otherBuyTransaction.getGood().equals(getGood())
                && otherBuyTransaction.getPerson().equals(getPerson())
                && otherBuyTransaction.getBuyPrice().equals(getBuyPrice());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getId(), getGood(), getPerson(), getBuyPrice());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getId())
                .append(getGood())
                .append(getPerson())
                .append(getBuyPrice());
        return builder.toString();
    }
}
