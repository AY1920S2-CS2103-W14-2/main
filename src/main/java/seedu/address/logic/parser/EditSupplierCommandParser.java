package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFER;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditSupplierCommand;
import seedu.address.logic.commands.EditSupplierCommand.EditSupplierDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.offer.Offer;

/**
 * Parses input arguments and creates a new EditSupplierCommand object
 */
public class EditSupplierCommandParser implements Parser<EditSupplierCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditSupplierCommand
     * and returns an EditSupplierCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditSupplierCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                PREFIX_CONTACT, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_OFFER);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditSupplierCommand.MESSAGE_USAGE), pe);
        }

        EditSupplierDescriptor editSupplierDescriptor = new EditSupplierDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editSupplierDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTACT).isPresent()) {
            editSupplierDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_CONTACT).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editSupplierDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editSupplierDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        parseOffersForEdit(argMultimap.getAllValues(PREFIX_OFFER)).ifPresent(editSupplierDescriptor::setOffers);

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
