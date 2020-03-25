package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditSupplierCommand.EditSupplierDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.good.GoodName;
import seedu.address.model.offer.Offer;
import seedu.address.model.offer.Price;
import seedu.address.model.supplier.Address;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
import seedu.address.model.supplier.Supplier;

/**
 * A utility class to help with building EditSupplierDescriptor objects.
 */
public class EditSupplierDescriptorBuilder {

    private EditSupplierDescriptor descriptor;

    public EditSupplierDescriptorBuilder() {
        descriptor = new EditSupplierDescriptor();
    }

    public EditSupplierDescriptorBuilder(EditSupplierDescriptor descriptor) {
        this.descriptor = new EditSupplierDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditSupplierDescriptor} with fields containing {@code supplier}'s details
     */
    public EditSupplierDescriptorBuilder(Supplier supplier) {
        descriptor = new EditSupplierDescriptor();
        descriptor.setName(supplier.getName());
        descriptor.setPhone(supplier.getPhone());
        descriptor.setEmail(supplier.getEmail());
        descriptor.setAddress(supplier.getAddress());
        descriptor.setOffers(supplier.getOffers());
    }

    /**
     * Sets the {@code Name} of the {@code EditSupplierDescriptor} that we are building.
     */
    public EditSupplierDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditSupplierDescriptor} that we are building.
     */
    public EditSupplierDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditSupplierDescriptor} that we are building.
     */
    public EditSupplierDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditSupplierDescriptor} that we are building.
     */
    public EditSupplierDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code offers} into a {@code Set<Offer>} and set it to the {@code EditSupplierDescriptor}
     * that we are building.
     */
    public EditSupplierDescriptorBuilder withOffers(String... offers) {
        Set<Offer> offerSet = Stream.of(offers)
                .map(ParserUtil::splitOnLastWhitespace)
                .map(ParserUtil::getGoodPricePair)
                .map(x -> new Offer((GoodName) x[0], (Price) x[1]))
                .collect(Collectors.toSet());
        descriptor.setOffers(offerSet);
        return this;
    }

    public EditSupplierDescriptor build() {
        return descriptor;
    }
}
