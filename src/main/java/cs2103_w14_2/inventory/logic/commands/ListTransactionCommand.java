package cs2103_w14_2.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import cs2103_w14_2.inventory.model.Model;

/**
 * Lists all transactions in the transaction history to the user.
 */
public class ListTransactionCommand extends Command {

    public static final String COMMAND_WORD = "list-t";

    public static final String MESSAGE_SUCCESS = "Listed all transactions";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
