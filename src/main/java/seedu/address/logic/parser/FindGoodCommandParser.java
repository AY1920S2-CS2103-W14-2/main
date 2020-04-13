package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindGoodCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.good.GoodSupplierPairContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindGoodCommand object
 */
public class FindGoodCommandParser implements Parser<FindGoodCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindGoodCommand
     * and returns a FindGoodCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindGoodCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindGoodCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindGoodCommand(new GoodSupplierPairContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
