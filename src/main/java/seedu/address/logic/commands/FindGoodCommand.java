package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.good.GoodSupplierPairContainsKeywordsPredicate;

/**
 * Finds and lists all suppliers in Inventory Management who sell specific goods identified by keywords
 * Keyword matching is case insensitive.
 */
public class FindGoodCommand extends Command {

    public static final String COMMAND_WORD = "source";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all suppliers who sell specific good(s) "
            + "identified with a good's or goods' name(s) which is/are identified with keyword(s) (case-insensitive)\n"
            + "Parameters: g/GOOD_NAME [g/MORE_GOOD_NAME]...\n"
            + "Example: " + COMMAND_WORD + " banana apple";

    private final GoodSupplierPairContainsKeywordsPredicate predicate;

    public FindGoodCommand(GoodSupplierPairContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindGoodCommand // instanceof handles nulls
                && predicate.equals(((FindGoodCommand) other).predicate)); // state check
    }
}
