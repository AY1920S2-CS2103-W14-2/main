package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.StateNotFoundException;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Managed to undo previous action!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            model.undo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (StateNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_UNDO_AT_INITIAL_STATE);
        }
    }
}
