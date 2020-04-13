package cs2103_w14_2.inventory.logic.parser;

import java.util.Set;
import java.util.stream.Stream;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;
import cs2103_w14_2.inventory.model.supplier.Email;
import cs2103_w14_2.inventory.model.supplier.Phone;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.logic.commands.AddSupplierCommand;
import cs2103_w14_2.inventory.model.offer.Offer;
import cs2103_w14_2.inventory.model.supplier.Address;
import cs2103_w14_2.inventory.model.supplier.Name;


/**
 * Parses input arguments and creates a new AddSupplierCommand object
 */
public class AddSupplierCommandParser implements Parser<AddSupplierCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSupplierCommand
     * and returns an AddSupplierCommand object for execution.
     * @throws cs2103_w14_2.inventory.logic.parser.exceptions.ParseException if the user input does not conform the expected format
     */
    public AddSupplierCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME,
                CliSyntax.PREFIX_CONTACT, CliSyntax.PREFIX_EMAIL, CliSyntax.PREFIX_ADDRESS, CliSyntax.PREFIX_OFFER);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_ADDRESS, CliSyntax.PREFIX_CONTACT, CliSyntax.PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddSupplierCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(CliSyntax.PREFIX_CONTACT).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(CliSyntax.PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(CliSyntax.PREFIX_ADDRESS).get());
        Set<Offer> offerList = ParserUtil.parseOffers(argMultimap.getAllValues(CliSyntax.PREFIX_OFFER));

        Supplier supplier = new Supplier(name, phone, email, address, offerList);

        return new AddSupplierCommand(supplier);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
