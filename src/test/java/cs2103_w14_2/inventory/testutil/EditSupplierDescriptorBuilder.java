package cs2103_w14_2.inventory.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cs2103_w14_2.inventory.logic.commands.EditSupplierCommand;
import cs2103_w14_2.inventory.logic.parser.ParserUtil;
import cs2103_w14_2.inventory.model.supplier.Address;
import cs2103_w14_2.inventory.model.supplier.Email;
import cs2103_w14_2.inventory.model.supplier.Name;
import cs2103_w14_2.inventory.model.supplier.Phone;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.good.GoodName;
import cs2103_w14_2.inventory.model.offer.Offer;
import cs2103_w14_2.inventory.model.offer.Price;

/**
 * A utility class to help with building EditSupplierDescriptor objects.
 */
public class EditSupplierDescriptorBuilder {

    private EditSupplierCommand.EditSupplierDescriptor descriptor;

    public EditSupplierDescriptorBuilder() {
        descriptor = new EditSupplierCommand.EditSupplierDescriptor();
    }

    public EditSupplierDescriptorBuilder(EditSupplierCommand.EditSupplierDescriptor descriptor) {
        this.descriptor = new EditSupplierCommand.EditSupplierDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditSupplierDescriptor} with fields containing {@code supplier}'s details
     */
    public EditSupplierDescriptorBuilder(Supplier supplier) {
        descriptor = new EditSupplierCommand.EditSupplierDescriptor();
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

    public EditSupplierCommand.EditSupplierDescriptor build() {
        return descriptor;
    }
}
