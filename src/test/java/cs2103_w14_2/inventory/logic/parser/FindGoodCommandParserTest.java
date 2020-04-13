package cs2103_w14_2.inventory.logic.parser;

import static cs2103_w14_2.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static cs2103_w14_2.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import cs2103_w14_2.inventory.commons.core.Messages;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.logic.commands.FindGoodCommand;
import cs2103_w14_2.inventory.model.good.GoodSupplierPairContainsKeywordsPredicate;

public class FindGoodCommandParserTest {

    private FindGoodCommandParser parser = new FindGoodCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindGoodCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindGoodCommand expectedFindGoodCommand =
                new FindGoodCommand(new GoodSupplierPairContainsKeywordsPredicate(Arrays.asList("apple", "banana")));
        assertParseSuccess(parser, "apple banana", expectedFindGoodCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n apple \n \t banana  \t", expectedFindGoodCommand);
    }

}
