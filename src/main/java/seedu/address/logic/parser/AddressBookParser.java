package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.commands.ClearSupplierCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteSupplierCommand;
import seedu.address.logic.commands.EditSupplierCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindSupplierCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListSupplierCommand;
import seedu.address.logic.commands.SellCommand;
import seedu.address.logic.commands.SetThresholdCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditSupplierCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteSupplierCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case FindSupplierCommand.COMMAND_WORD:
            return new FindSupplierCommandParser().parse(arguments);

        case BuyCommand.COMMAND_WORD:
            return new BuyCommandParser().parse(arguments);

        case SellCommand.COMMAND_WORD:
            return new SellCommandParser().parse(arguments);

        case ClearSupplierCommand.COMMAND_WORD:
            return new ClearSupplierCommand();

        case ListSupplierCommand.COMMAND_WORD:
            return new ListSupplierCommand();

        case SetThresholdCommand.COMMAND_WORD:
            return new SetThresholdCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
