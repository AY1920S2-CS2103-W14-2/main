package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Returns to a version of the application before the last execution of an UndoCommand.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Managed to redo undone action!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}