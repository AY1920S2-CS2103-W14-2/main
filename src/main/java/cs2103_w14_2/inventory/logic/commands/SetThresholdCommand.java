package cs2103_w14_2.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.logic.parser.CliSyntax;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.good.Good;
import cs2103_w14_2.inventory.model.good.GoodQuantity;

/**
 * Sets the warning threshold quantity of an existing good in the inventory.
 */
public class SetThresholdCommand extends Command {

    public static final String COMMAND_WORD = "warn";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set a warning threshold quantity for the good "
            + "identified by the index number used in the displayed inventory.\n"
            + "Existing threshold value will be overwritten by the input values.\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_QUANTITY + "10 ";

    public static final String MESSAGE_SUCCESS = "Set warning threshold quantity %1$d for %2$ss";

    private Index targetIndex;
    private GoodQuantity threshold;

    public SetThresholdCommand(Index targetIndex, GoodQuantity threshold) {
        this.targetIndex = targetIndex;
        this.threshold = threshold;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Good> lastShownList = model.getFilteredGoodList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GOOD_DISPLAYED_INDEX);
        }

        Good goodToSetThreshold = lastShownList.get(targetIndex.getZeroBased());

        Good updatedGood = new Good(goodToSetThreshold.getGoodName(), goodToSetThreshold.getGoodQuantity(),
                threshold);

        model.setGood(goodToSetThreshold, updatedGood);
        model.updateFilteredGoodList(Model.PREDICATE_SHOW_ALL_GOODS);
        model.commit();

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                threshold.goodQuantity, updatedGood.getGoodName().fullGoodName));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetThresholdCommand)) {
            return false;
        }

        // state check
        SetThresholdCommand e = (SetThresholdCommand) other;
        return targetIndex.equals(e.targetIndex)
                && threshold.equals(e.threshold);

    }
}
