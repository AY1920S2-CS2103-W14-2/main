package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.transaction.TransactionContainKeywordsPredicate;

/**
 * Finds and lists all transactions in transaction history whose conditions are met.
 * Keyword matching is case insensitive.
 */
public class FindTransactionCommand extends Command {

    public static final String COMMAND_WORD = "find-t";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": filters the transaction list by type of transaction, supplier's name and good's name\n"
            + "Format: " + COMMAND_WORD + " [buy] "
            + "[" + PREFIX_NAME + "] "
            + "[" + PREFIX_GOOD_NAME + "] ";
    public static final String EMPTY_VALUE_WITH_PREFIX = "n/ or g/ is given, but the value is empty.\n"
            + MESSAGE_USAGE;
    public static final String INVALID_TRANSACTION_TYPE = "Transaction type can only be 'buy' or 'sell'.\n"
            + MESSAGE_USAGE;
    public static final String MESSAGE_NO_FIELD_PROVIDED = "At least one conditional field must be provided.\n"
            + MESSAGE_USAGE;

    private TransactionContainKeywordsPredicate predicate;

    public FindTransactionCommand(TransactionContainKeywordsPredicate transactionContainKeywordsPredicate) {
        this.predicate = transactionContainKeywordsPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTransactionList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                        model.getFilteredTransactionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTransactionCommand // instanceof handles nulls
                && predicate.equals(((FindTransactionCommand) other).predicate)); // state check
    }

}
