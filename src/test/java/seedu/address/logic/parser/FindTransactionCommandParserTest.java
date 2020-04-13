package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.FindTransactionCommandParser.TransactionType.BUY_TRANSACTION;
import static seedu.address.logic.parser.FindTransactionCommandParser.TransactionType.EMPTY;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindTransactionCommand;
import seedu.address.model.transaction.TransactionContainKeywordsPredicate;

public class FindTransactionCommandParserTest {

    private FindTransactionCommandParser parser = new FindTransactionCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "      ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindTransactionCommand.MESSAGE_NO_FIELD_PROVIDED));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindTransactionCommand expectedFindTransactionCommand =
                new FindTransactionCommand(new TransactionContainKeywordsPredicate(BUY_TRANSACTION,
                        Arrays.asList("Alice", "Bob"), Arrays.asList("Apple", "Banana")));
        assertParseSuccess(parser, "buy n/Alice Bob g/Apple Banana", expectedFindTransactionCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "buy n/" + PREAMBLE_WHITESPACE + PREAMBLE_WHITESPACE
                        + "Alice" + PREAMBLE_WHITESPACE + PREAMBLE_WHITESPACE + PREAMBLE_WHITESPACE
                        + "Bob g/Apple Banana",
                expectedFindTransactionCommand);
    }

    @Test
    public void parse_withOnlyOneCondition_returnsFindCommand() {
        // only type of transaction given
        FindTransactionCommand expectedFindTransactionCommand = new FindTransactionCommand(
                new TransactionContainKeywordsPredicate(BUY_TRANSACTION,
                        Collections.emptyList(), Collections.emptyList()));
        // extra space behind of "buy" is to account for space between preamble and prefix
        assertParseSuccess(parser, "buy" + PREAMBLE_WHITESPACE, expectedFindTransactionCommand);

        // only supplier name is given
        expectedFindTransactionCommand = new FindTransactionCommand(
                new TransactionContainKeywordsPredicate(EMPTY,
                        Arrays.asList("Alice", "Bob"), Collections.emptyList()));
        // extra space in front of "n/Alice bob" is to account for space between preamble and prefix
        assertParseSuccess(parser, " n/Alice Bob", expectedFindTransactionCommand);

        // only good name is given
        expectedFindTransactionCommand = new FindTransactionCommand(
                new TransactionContainKeywordsPredicate(EMPTY,
                        Collections.emptyList(), Arrays.asList("Apple", "Banana")));
        // extra space in front of "g/Apple Banana" is to account for space between preamble and prefix
        assertParseSuccess(parser, " g/Apple Banana", expectedFindTransactionCommand);
    }

}
