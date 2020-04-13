package cs2103_w14_2.inventory.logic.parser;

import java.util.Arrays;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;
import cs2103_w14_2.inventory.logic.commands.FindGoodCommand;
import cs2103_w14_2.inventory.model.good.GoodSupplierPairContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindGoodCommand object
 */
public class FindGoodCommandParser implements Parser<FindGoodCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindGoodCommand
     * and returns a FindGoodCommand object for execution.
     * @throws cs2103_w14_2.inventory.logic.parser.exceptions.ParseException if the user input does not conform the expected format
     */
    public FindGoodCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindGoodCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindGoodCommand(new GoodSupplierPairContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
