package cs2103_w14_2.inventory.model.offer;

import static cs2103_w14_2.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import cs2103_w14_2.inventory.commons.util.CollectionUtil;
import cs2103_w14_2.inventory.model.good.GoodName;

/**
 * Represents an offer given by a supplier for supplying a certain good at a certain price.
 */
public class Offer {

    public static final String MESSAGE_CONSTRAINTS = "Offer must consist of a valid good name and a valid price, "
            + "separated by a whitespace";

    private final GoodName goodName;
    private final Price price;

    /**
     * Constructs an {@code Offer}.
     * @param goodName the good to be offered
     * @param price the price of the good in the offer
     */
    public Offer(GoodName goodName, Price price) {
        CollectionUtil.requireAllNonNull(goodName, price);
        this.goodName = goodName;
        this.price = price;
    }

    public GoodName getGoodName() {
        return goodName;
    }

    public Price getPrice() {
        return price;
    }

    /**
     * Returns true if a given offer is a valid offer.
     */
    public static boolean isValidOffer(Offer offer) {
        return GoodName.isValidGoodName(offer.getGoodName().toString())
                && Price.isValidPrice(offer.getPrice().getValue());
    }

    @Override
    public String toString() {
        return "Good: " + goodName + " | Price: " + price;
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
        return getGoodName().equals(offer.getGoodName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGoodName());
    }
}
