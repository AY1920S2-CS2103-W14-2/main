package cs2103_w14_2.inventory.logic.parser;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.logic.commands.DeleteSupplierCommand;
import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteSupplierCommand object
 */
public class DeleteSupplierCommandParser implements Parser<DeleteSupplierCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSupplierCommand
     * and returns a DeleteSupplierCommand object for execution.
     * @throws cs2103_w14_2.inventory.logic.parser.exceptions.ParseException if the user input does not conform the expected format
     */
    public DeleteSupplierCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteSupplierCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteSupplierCommand(index);
    }
}
