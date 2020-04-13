package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.supplier.Supplier;

/**
 * Deletes a supplier identified using its displayed index from the address book
 */
public class DeleteSupplierCommand extends Command {

    public static final String COMMAND_WORD = "delete-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the supplier identified by the index number used in the displayed supplier list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SUPPLIER_SUCCESS = "Deleted Supplier: %1$s";

    private final Index index;

    public DeleteSupplierCommand(Index indexFirstSupplier) {
        this.index = indexFirstSupplier;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Supplier> lastShownList = model.getFilteredSupplierList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }

        Supplier supplierToDelete = lastShownList.get(index.getZeroBased());
        model.deleteSupplier(supplierToDelete);
        model.commit();

        return new CommandResult(String.format(MESSAGE_DELETE_SUPPLIER_SUCCESS, supplierToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSupplierCommand // instanceof handles nulls
                && index.equals(((DeleteSupplierCommand) other).index)); // state check
    }
}
