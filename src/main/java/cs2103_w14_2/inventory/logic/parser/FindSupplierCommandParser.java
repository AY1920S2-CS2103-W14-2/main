package cs2103_w14_2.inventory.logic.parser;

import static cs2103_w14_2.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;
import cs2103_w14_2.inventory.logic.commands.FindSupplierCommand;
import cs2103_w14_2.inventory.model.supplier.SupplierNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindSupplierCommand object
 */
public class FindSupplierCommandParser implements Parser<FindSupplierCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindSupplierCommand
     * and returns a FindSupplierCommand object for execution.
     * @throws cs2103_w14_2.inventory.logic.parser.exceptions.ParseException if the user input does not conform the expected format
     */
    public FindSupplierCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSupplierCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindSupplierCommand(new SupplierNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
