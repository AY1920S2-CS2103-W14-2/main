package cs2103_w14_2.inventory.logic.parser;

import java.util.stream.Stream;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.logic.commands.SellCommand;
import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;
import cs2103_w14_2.inventory.model.good.GoodQuantity;
import cs2103_w14_2.inventory.model.offer.Price;

/**
 * Parses input arguments and creates a new SellCommand object
 */
public class SellCommandParser implements Parser<SellCommand> {

    @Override
    public SellCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_QUANTITY, CliSyntax.PREFIX_PRICE);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_QUANTITY, CliSyntax.PREFIX_PRICE)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    SellCommand.MESSAGE_USAGE), pe);
        }

        GoodQuantity goodQuantity = ParserUtil.parseGoodQuantity(argMultimap.getValue(CliSyntax.PREFIX_QUANTITY).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(CliSyntax.PREFIX_PRICE).get());

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
