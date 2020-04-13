package cs2103_w14_2.inventory.logic.parser;

import java.util.Arrays;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.logic.commands.FindSupplierCommand;
import cs2103_w14_2.inventory.model.supplier.SupplierNameContainsKeywordsPredicate;
import org.junit.jupiter.api.Test;

public class FindSupplierCommandParserTest {

    private FindSupplierCommandParser parser = new FindSupplierCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindSupplierCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindSupplierCommand expectedFindSupplierCommand =
                new FindSupplierCommand(new SupplierNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        CommandParserTestUtil.assertParseSuccess(parser, "Alice Bob", expectedFindSupplierCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindSupplierCommand);
    }

}
