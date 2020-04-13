package cs2103_w14_2.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.supplier.SupplierNameContainsKeywordsPredicate;

/**
 * Finds and lists all suppliers in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindSupplierCommand extends Command {

    public static final String COMMAND_WORD = "find-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all suppliers whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORD]...\n"
            + "Example: " + COMMAND_WORD + " NTUC ColdStorage";

    private final SupplierNameContainsKeywordsPredicate predicate;

    public FindSupplierCommand(SupplierNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSupplierList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_SUPPLIERS_LISTED_OVERVIEW, model.getFilteredSupplierList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindSupplierCommand // instanceof handles nulls
                && predicate.equals(((FindSupplierCommand) other).predicate)); // state check
    }
}
