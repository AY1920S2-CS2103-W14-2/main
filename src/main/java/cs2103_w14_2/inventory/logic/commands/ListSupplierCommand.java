package cs2103_w14_2.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import cs2103_w14_2.inventory.model.Model;

/**
 * Lists all suppliers in the address book to the user.
 */
public class ListSupplierCommand extends Command {

    public static final String COMMAND_WORD = "list-s";

    public static final String MESSAGE_SUCCESS = "Listed all suppliers";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSupplierList(Model.PREDICATE_SHOW_ALL_SUPPLIERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
