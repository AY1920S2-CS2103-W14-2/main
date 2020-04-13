package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;

/**
 * Parses input arguments and creates a new BuyCommand object
 */
public class BuyCommandParser implements Parser<BuyCommand> {
    @Override
    public BuyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUANTITY, PREFIX_GOOD_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    BuyCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_QUANTITY, PREFIX_GOOD_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE));
        }

        GoodName goodName = ParserUtil.parseGoodName(argMultimap.getValue(PREFIX_GOOD_NAME).get());
        GoodQuantity goodQuantity = ParserUtil.parseGoodQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());

        return new BuyCommand(goodName, goodQuantity, index);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
