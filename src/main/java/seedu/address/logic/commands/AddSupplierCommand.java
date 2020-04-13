package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFER;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.supplier.Supplier;

/**
 * Adds a supplier to the address book.
 */
public class AddSupplierCommand extends Command {

    public static final String COMMAND_WORD = "add-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a supplier to the address book.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_CONTACT + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_OFFER + "GOOD PRICE]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "NTUC Fairprice Macpherson Mall "
            + PREFIX_CONTACT + "63521728 "
            + PREFIX_EMAIL + "MacphersonMall@NTUCFairprice.com "
            + PREFIX_ADDRESS + "401, #02-22 MacPherson Rd, Macpherson Mall, 368125 "
            + PREFIX_OFFER + "banana 5 "
            + PREFIX_OFFER + "tissue paper 0.55";

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
