package cs2103_w14_2.inventory.testutil;

import java.util.Set;

import cs2103_w14_2.inventory.logic.commands.AddSupplierCommand;
import cs2103_w14_2.inventory.logic.commands.EditSupplierCommand;
import cs2103_w14_2.inventory.logic.parser.CliSyntax;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.offer.Offer;

/**
 * A utility class for Supplier.
 */
public class SupplierUtil {

    /**
     * Returns an add command string for adding the {@code supplier}.
     */
    public static String getAddCommand(Supplier supplier) {
        return AddSupplierCommand.COMMAND_WORD + " " + getSupplierDetails(supplier);
    }

    /**
     * Returns the part of command string for the given {@code supplier}'s details.
     */
    public static String getSupplierDetails(Supplier supplier) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + supplier.getName().fullName + " ");
        sb.append(CliSyntax.PREFIX_CONTACT + supplier.getPhone().value + " ");
        sb.append(CliSyntax.PREFIX_EMAIL + supplier.getEmail().value + " ");
        sb.append(CliSyntax.PREFIX_ADDRESS + supplier.getAddress().value + " ");
        supplier.getOffers().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_OFFER
                    + s.getGoodName().toString() + " "
                    + s.getPrice().getValue() + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditSupplierDescriptor}'s details.
     */
    public static String getEditSupplierDescriptorDetails(EditSupplierCommand.EditSupplierDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(CliSyntax.PREFIX_CONTACT).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(CliSyntax.PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(CliSyntax.PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getOffers().isPresent()) {
            Set<Offer> offers = descriptor.getOffers().get();
            if (offers.isEmpty()) {
                sb.append(CliSyntax.PREFIX_OFFER);
            } else {
                offers.forEach(s -> sb.append(CliSyntax.PREFIX_OFFER)
                        .append(s.getGoodName().toString())
                        .append(" ")
                        .append(s.getPrice().getValue())
                        .append(" "));
            }
        }
        return sb.toString();
    }
}
