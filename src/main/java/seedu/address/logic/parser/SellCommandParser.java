package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.logic.commands.SellCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;

public class SellCommandParser implements Parser<SellCommand> {

    @Override
    public SellCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUANTITY, PREFIX_GOOD_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_QUANTITY, PREFIX_GOOD_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE));
        }

        GoodName goodName = ParserUtil.parseGoodName(argMultimap.getValue(PREFIX_GOOD_NAME).get());
        GoodQuantity goodQuantity = ParserUtil.parseGoodQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());

        Good good = new Good(goodName, goodQuantity);

        return new SellCommand(good);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
