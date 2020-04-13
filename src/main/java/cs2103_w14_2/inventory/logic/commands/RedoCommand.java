package cs2103_w14_2.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.version.StateNotFoundException;

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
