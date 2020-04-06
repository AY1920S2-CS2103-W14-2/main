package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

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
