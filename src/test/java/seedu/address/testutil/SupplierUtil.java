package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFER;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditSupplierCommand.EditSupplierDescriptor;
import seedu.address.model.offer.Offer;
import seedu.address.model.supplier.Supplier;

/**
 * A utility class for Supplier.
 */
public class SupplierUtil {

    /**
     * Returns an add command string for adding the {@code supplier}.
     */
    public static String getAddCommand(Supplier supplier) {
        return AddCommand.COMMAND_WORD + " " + getSupplierDetails(supplier);
    }

    /**
     * Returns the part of command string for the given {@code supplier}'s details.
     */
    public static String getSupplierDetails(Supplier supplier) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + supplier.getName().fullName + " ");
        sb.append(PREFIX_CONTACT + supplier.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + supplier.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + supplier.getAddress().value + " ");
        supplier.getOffers().stream().forEach(
            s -> sb.append(PREFIX_OFFER
                    + s.getGoodName().toString() + " "
                    + s.getPrice().getValue() + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditSupplierDescriptor}'s details.
     */
    public static String getEditSupplierDescriptorDetails(EditSupplierDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_CONTACT).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getOffers().isPresent()) {
            Set<Offer> offers = descriptor.getOffers().get();
            if (offers.isEmpty()) {
                sb.append(PREFIX_OFFER);
            } else {
                offers.forEach(s -> sb.append(PREFIX_OFFER)
                        .append(s.getGoodName().toString())
                        .append(" ")
                        .append(s.getPrice().getValue())
                        .append(" "));
            }
        }
        return sb.toString();
    }
}
