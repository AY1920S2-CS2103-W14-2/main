package cs2103_w14_2.inventory.logic.parser;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.logic.commands.DeleteGoodCommand;
import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteSupplierCommand object
 */
public class DeleteGoodCommandParser implements Parser<DeleteGoodCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGoodCommand
     * and returns a DeleteGoodCommand object for execution.
     * @throws cs2103_w14_2.inventory.logic.parser.exceptions.ParseException if the user input does not conform to the expected format
     */
    public DeleteGoodCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteGoodCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteGoodCommand.MESSAGE_USAGE), pe);
        }
    }

}
