package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Displays the sales report to the user.
 */
public class SalesReportCommand extends Command {

    public static final String COMMAND_WORD = "sales";

    public static final String MESSAGE_SUCCESS = "Generating sales report...";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.displaySalesReport();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
