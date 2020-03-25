package seedu.address.model.offer;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.good.GoodName;

/**
 * Represents an offer given by a supplier for supplying a certain good at a certain price.
 */
public class Offer {

    public static final String MESSAGE_CONSTRAINTS = "Offer must consist of a valid good name and a valid price, "
            + "separated by a whitespace";

    private final GoodName good;
    private final Price price;

    /**
     * Constructs an {@code Offer}.
     *
     * @param good  the good to be offered
     * @param price the price of the good in the offer
     */
    public Offer(GoodName good, Price price) {
        requireAllNonNull(good, price);
        this.good = good;
        this.price = price;
    }

    public GoodName getGood() {
        return good;
    }

    public Price getPrice() {
        return price;
    }

    /**
     * Returns true if a given offer is a valid offer.
     */
    public static boolean isValidOffer(Offer offer) {
        return GoodName.isValidGoodName(offer.getGood().toString())
                && Price.isValidPrice(offer.getPrice().getValue());
    }

    @Override
    public String toString() {
        return "Good: " + good + " | Price: " + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Offer offer = (Offer) o;
        return getGood().equals(offer.getGood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGood());
    }
}
