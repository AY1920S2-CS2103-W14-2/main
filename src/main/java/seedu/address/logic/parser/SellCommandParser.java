package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SellCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.good.GoodQuantity;
import seedu.address.model.offer.Price;

/**
 * Parses input arguments and creates a new SellCommand object
 */
public class SellCommandParser implements Parser<SellCommand> {

    @Override
    public SellCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUANTITY, PREFIX_PRICE);

        if (!arePrefixesPresent(argMultimap, PREFIX_QUANTITY, PREFIX_PRICE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SellCommand.MESSAGE_USAGE), pe);
        }

        GoodQuantity goodQuantity = ParserUtil.parseGoodQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());

        return new SellCommand(goodQuantity, price, index);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
