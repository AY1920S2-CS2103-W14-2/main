package cs2103_w14_2.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import cs2103_w14_2.inventory.model.AddressBook;
import cs2103_w14_2.inventory.model.Model;

/**
 * Clears the address book.
 */
public class ClearSupplierCommand extends Command {

    public static final String COMMAND_WORD = "clear-s";
    public static final String MESSAGE_SUCCESS = "Supplier list has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        model.commit();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
