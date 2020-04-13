package cs2103_w14_2.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.good.Good;

/**
 * Deletes a good identified using its displayed index from the address book.
 */
public class DeleteGoodCommand extends Command {

    public static final String COMMAND_WORD = "delete-g";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the good identified by the index number used in the displayed inventory list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_GOOD_SUCCESS = "Deleted good: %1$s";

    private final Index targetIndex;

    public DeleteGoodCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Good> lastShownList = model.getFilteredGoodList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GOOD_DISPLAYED_INDEX);
        }

        Good goodToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteGood(goodToDelete);
        model.commit();
        return new CommandResult(String.format(MESSAGE_DELETE_GOOD_SUCCESS, goodToDelete.getGoodName().fullGoodName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGoodCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteGoodCommand) other).targetIndex)); // state check
    }
}
