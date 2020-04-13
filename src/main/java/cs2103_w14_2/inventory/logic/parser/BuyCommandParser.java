package cs2103_w14_2.inventory.logic.parser;

import static java.util.Objects.requireNonNull;
import static cs2103_w14_2.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.logic.commands.BuyCommand;
import cs2103_w14_2.inventory.model.good.GoodName;
import cs2103_w14_2.inventory.model.good.GoodQuantity;

/**
 * Parses input arguments and creates a new BuyCommand object
 */
public class BuyCommandParser implements Parser<BuyCommand> {
    @Override
    public BuyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_QUANTITY, CliSyntax.PREFIX_GOOD_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    BuyCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_QUANTITY, CliSyntax.PREFIX_GOOD_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE));
        }

        GoodName goodName = ParserUtil.parseGoodName(argMultimap.getValue(CliSyntax.PREFIX_GOOD_NAME).get());
        GoodQuantity goodQuantity = ParserUtil.parseGoodQuantity(argMultimap.getValue(CliSyntax.PREFIX_QUANTITY).get());

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
