package cs2103_w14_2.inventory.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.logic.commands.SetThresholdCommand;
import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;
import cs2103_w14_2.inventory.model.good.GoodQuantity;

/**
 * Parses input arguments and set the warning threshold quantity for good.
 */
public class SetThresholdCommandParser implements Parser<SetThresholdCommand> {

    @Override
    public SetThresholdCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_QUANTITY);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_QUANTITY)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetThresholdCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    SetThresholdCommand.MESSAGE_USAGE), pe);
        }

        GoodQuantity threshold = ParserUtil.parseGoodQuantity(argMultimap.getValue(CliSyntax.PREFIX_QUANTITY).get());

        return new SetThresholdCommand(index, threshold);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
