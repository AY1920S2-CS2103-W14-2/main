package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.StateNotFoundException;

/**
 * Returns to a version of the application before the last execution of a data-modifying Command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Managed to undo previous action!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.undo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (StateNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_UNDO_AT_INITIAL_STATE);
        }
    }
}
