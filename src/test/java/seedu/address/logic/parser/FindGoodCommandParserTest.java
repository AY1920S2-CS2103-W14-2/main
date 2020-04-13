package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindGoodCommand;
import seedu.address.model.good.GoodSupplierPairContainsKeywordsPredicate;

public class FindGoodCommandParserTest {

    private FindGoodCommandParser parser = new FindGoodCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
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
