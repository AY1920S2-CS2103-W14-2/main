package cs2103_w14_2.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.logic.parser.CliSyntax;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.supplier.Supplier;

/**
 * Adds a supplier to the address book.
 */
public class AddSupplierCommand extends Command {

    public static final String COMMAND_WORD = "add-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a supplier to the address book.\n"
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "NAME "
            + CliSyntax.PREFIX_CONTACT + "PHONE "
            + CliSyntax.PREFIX_EMAIL + "EMAIL "
            + CliSyntax.PREFIX_ADDRESS + "ADDRESS "
            + "[" + CliSyntax.PREFIX_OFFER + "GOOD PRICE]...\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "NTUC Fairprice Macpherson Mall "
            + CliSyntax.PREFIX_CONTACT + "63521728 "
            + CliSyntax.PREFIX_EMAIL + "MacphersonMall@NTUCFairprice.com "
            + CliSyntax.PREFIX_ADDRESS + "401, #02-22 MacPherson Rd, Macpherson Mall, 368125 "
            + CliSyntax.PREFIX_OFFER + "banana 5 "
            + CliSyntax.PREFIX_OFFER + "tissue paper 0.55";

    public static final String MESSAGE_SUCCESS = "New supplier added: %1$s";
    public static final String MESSAGE_DUPLICATE_SUPPLIER = "This supplier already exists in the address book";

    private final Supplier toAdd;

    /**
     * Creates an AddSupplierCommand to add the specified {@code Supplier}
     */
    public AddSupplierCommand(Supplier supplier) {
        requireNonNull(supplier);
        toAdd = supplier;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasSupplier(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SUPPLIER);
        }

        model.addSupplier(toAdd);
        model.commit();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddSupplierCommand // instanceof handles nulls
                && toAdd.equals(((AddSupplierCommand) other).toAdd));
    }
}
