package seedu.address.model.supplier;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.good.GoodName;
import seedu.address.model.offer.Offer;


/**
 * Represents a Supplier in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Supplier {

    public static final String MESSAGE_CONSTRAINTS =
            "Person should consist of valid name, phone, email, address and tags.";

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Offer> offers = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Supplier(Name name, Phone phone, Email email, Address address, Set<Offer> offers) {
        requireAllNonNull(name, phone, email, address, offers);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.offers.addAll(offers);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable offer set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Offer> getOffers() {
        return Collections.unmodifiableSet(offers);
    }

    /**
     * This is to remove a good from supplier's list after giving the good's name
     */
    public boolean removeGood(GoodName goodName) {
        Iterator<Offer> itr = offers.iterator();
        while (itr.hasNext()) {
            Offer tempOffer = itr.next();
            if (tempOffer.getGoodName().equals(goodName)) {
                itr.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if a given person is a valid person.
     */
    public static boolean isValidSupplier(Supplier test) {
        return Name.isValidName(test.getName().toString())
                && Phone.isValidPhone(test.getPhone().toString())
                && Email.isValidEmail(test.getEmail().toString())
                && Address.isValidAddress(test.getAddress().toString())
                && test.getOffers().stream().allMatch(offer -> Offer.isValidOffer(offer));
    }

    /**
     * Returns true if both suppliers of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two suppliers.
     */
    public boolean isSameSupplier(Supplier otherSupplier) {
        if (otherSupplier == this) {
            return true;
        }

        return otherSupplier != null
                && otherSupplier.getName().equals(getName())
                && (otherSupplier.getPhone().equals(getPhone()) || otherSupplier.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both suppliers have the same identity and data fields.
     * This defines a stronger notion of equality between two suppliers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Supplier)) {
            return false;
        }

        Supplier otherSupplier = (Supplier) other;
        return otherSupplier.getName().equals(getName())
                && otherSupplier.getPhone().equals(getPhone())
                && otherSupplier.getEmail().equals(getEmail())
                && otherSupplier.getAddress().equals(getAddress())
                && otherSupplier.getOffers().equals(getOffers());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, offers);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Offers: ");
        getOffers().forEach(builder::append);
        return builder.toString();
    }

}
