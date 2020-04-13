package cs2103_w14_2.inventory.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.logic.commands.EditSupplierCommand;
import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;
import cs2103_w14_2.inventory.model.offer.Offer;

/**
 * Parses input arguments and creates a new EditSupplierCommand object
 */
public class EditSupplierCommandParser implements Parser<EditSupplierCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditSupplierCommand
     * and returns an EditSupplierCommand object for execution.
     * @throws cs2103_w14_2.inventory.logic.parser.exceptions.ParseException if the user input does not conform the expected format
     */
    public EditSupplierCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME,
                CliSyntax.PREFIX_CONTACT, CliSyntax.PREFIX_EMAIL, CliSyntax.PREFIX_ADDRESS, CliSyntax.PREFIX_OFFER);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    EditSupplierCommand.MESSAGE_USAGE), pe);
        }

        EditSupplierCommand.EditSupplierDescriptor editSupplierDescriptor = new EditSupplierCommand.EditSupplierDescriptor();
        if (argMultimap.getValue(CliSyntax.PREFIX_NAME).isPresent()) {
            editSupplierDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_CONTACT).isPresent()) {
            editSupplierDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(CliSyntax.PREFIX_CONTACT).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_EMAIL).isPresent()) {
            editSupplierDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(CliSyntax.PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_ADDRESS).isPresent()) {
            editSupplierDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(CliSyntax.PREFIX_ADDRESS).get()));
        }
        parseOffersForEdit(argMultimap.getAllValues(CliSyntax.PREFIX_OFFER)).ifPresent(editSupplierDescriptor::setOffers);

        if (!editSupplierDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditSupplierCommand.MESSAGE_NOT_EDITED);
        }

        return new EditSupplierCommand(index, editSupplierDescriptor);
    }

    /**
     * Parses {@code Collection<String> offers} into a {@code Set<Offer>} if {@code offers} is non-empty.
     * If {@code offers} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Offer>} containing zero offers.
     */
    private Optional<Set<Offer>> parseOffersForEdit(Collection<String> offers) throws ParseException {
        assert offers != null;

        if (offers.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> offerList = offers.size() == 1 && offers.contains("") ? Collections.emptyList() : offers;
        return Optional.of(ParserUtil.parseOffers(offerList));
    }

}
