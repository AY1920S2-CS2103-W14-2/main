package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.StateNotFoundException;

import static java.util.Objects.requireNonNull;

/**
 * Returns to a version of the application before the last execution of an UndoCommand.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Managed to redo undone action!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.redo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (StateNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_REDO_AT_LATEST_STATE);
        }
    }
}
