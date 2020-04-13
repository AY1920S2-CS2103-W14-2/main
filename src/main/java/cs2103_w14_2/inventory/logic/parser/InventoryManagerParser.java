package cs2103_w14_2.inventory.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.logic.commands.AddSupplierCommand;
import cs2103_w14_2.inventory.logic.commands.ClearSupplierCommand;
import cs2103_w14_2.inventory.logic.commands.Command;
import cs2103_w14_2.inventory.logic.commands.DeleteGoodCommand;
import cs2103_w14_2.inventory.logic.commands.DeleteGoodPricePairFromSupplierCommand;
import cs2103_w14_2.inventory.logic.commands.DeleteSupplierCommand;
import cs2103_w14_2.inventory.logic.commands.EditSupplierCommand;
import cs2103_w14_2.inventory.logic.commands.ExitCommand;
import cs2103_w14_2.inventory.logic.commands.FindGoodCommand;
import cs2103_w14_2.inventory.logic.commands.FindSupplierCommand;
import cs2103_w14_2.inventory.logic.commands.HelpCommand;
import cs2103_w14_2.inventory.logic.commands.RedoCommand;
import cs2103_w14_2.inventory.logic.commands.SellCommand;
import cs2103_w14_2.inventory.logic.commands.SetThresholdCommand;
import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;
import cs2103_w14_2.inventory.logic.commands.BuyCommand;
import cs2103_w14_2.inventory.logic.commands.FindTransactionCommand;
import cs2103_w14_2.inventory.logic.commands.ListSupplierCommand;
import cs2103_w14_2.inventory.logic.commands.ListTransactionCommand;
import cs2103_w14_2.inventory.logic.commands.UndoCommand;

/**
 * Parses user input.
 */
public class InventoryManagerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws cs2103_w14_2.inventory.logic.parser.exceptions.ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddSupplierCommand.COMMAND_WORD:
            return new AddSupplierCommandParser().parse(arguments);

        case EditSupplierCommand.COMMAND_WORD:
            return new EditSupplierCommandParser().parse(arguments);

        case DeleteSupplierCommand.COMMAND_WORD:
            return new DeleteSupplierCommandParser().parse(arguments);

        case DeleteGoodCommand.COMMAND_WORD:
            return new DeleteGoodCommandParser().parse(arguments);

        case DeleteGoodPricePairFromSupplierCommand.COMMAND_WORD:
            return new DeleteGoodPricePairFromSupplierCommandParser().parse(arguments);

        case FindSupplierCommand.COMMAND_WORD:
            return new FindSupplierCommandParser().parse(arguments);

        case FindGoodCommand.COMMAND_WORD:
            return new FindGoodCommandParser().parse(arguments);

        case BuyCommand.COMMAND_WORD:
            return new BuyCommandParser().parse(arguments);

        case SellCommand.COMMAND_WORD:
            return new SellCommandParser().parse(arguments);

        case ClearSupplierCommand.COMMAND_WORD:
            return new ClearSupplierCommand();

        case ListSupplierCommand.COMMAND_WORD:
            return new ListSupplierCommand();

        case ListTransactionCommand.COMMAND_WORD:
            return new ListTransactionCommand();

        case SetThresholdCommand.COMMAND_WORD:
            return new SetThresholdCommandParser().parse(arguments);

        case FindTransactionCommand.COMMAND_WORD:
            return new FindTransactionCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
