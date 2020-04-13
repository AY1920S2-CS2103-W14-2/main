package cs2103_w14_2.inventory.logic.parser;

import static cs2103_w14_2.inventory.logic.parser.FindTransactionCommandParser.TransactionType.BUY_TRANSACTION;
import static cs2103_w14_2.inventory.logic.parser.FindTransactionCommandParser.TransactionType.EMPTY;

import java.util.Arrays;
import java.util.Collections;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.logic.commands.CommandTestUtil;
import cs2103_w14_2.inventory.model.transaction.TransactionContainKeywordsPredicate;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.logic.commands.FindTransactionCommand;

public class FindTransactionCommandParserTest {

    private FindTransactionCommandParser parser = new FindTransactionCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "      ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindTransactionCommand.MESSAGE_NO_FIELD_PROVIDED));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindTransactionCommand expectedFindTransactionCommand =
                new FindTransactionCommand(new TransactionContainKeywordsPredicate(BUY_TRANSACTION,
                        Arrays.asList("Alice", "Bob"), Arrays.asList("Apple", "Banana")));
        CommandParserTestUtil.assertParseSuccess(parser, "buy n/Alice Bob g/Apple Banana", expectedFindTransactionCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, "buy n/" + CommandTestUtil.PREAMBLE_WHITESPACE + CommandTestUtil.PREAMBLE_WHITESPACE
                        + "Alice" + CommandTestUtil.PREAMBLE_WHITESPACE + CommandTestUtil.PREAMBLE_WHITESPACE + CommandTestUtil.PREAMBLE_WHITESPACE
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
        CommandParserTestUtil.assertParseSuccess(parser, "buy" + CommandTestUtil.PREAMBLE_WHITESPACE, expectedFindTransactionCommand);

        // only supplier name is given
        expectedFindTransactionCommand = new FindTransactionCommand(
                new TransactionContainKeywordsPredicate(EMPTY,
                        Arrays.asList("Alice", "Bob"), Collections.emptyList()));
        // extra space in front of "n/Alice bob" is to account for space between preamble and prefix
        CommandParserTestUtil.assertParseSuccess(parser, " n/Alice Bob", expectedFindTransactionCommand);

        // only good name is given
        expectedFindTransactionCommand = new FindTransactionCommand(
                new TransactionContainKeywordsPredicate(EMPTY,
                        Collections.emptyList(), Arrays.asList("Apple", "Banana")));
        // extra space in front of "g/Apple Banana" is to account for space between preamble and prefix
        CommandParserTestUtil.assertParseSuccess(parser, " g/Apple Banana", expectedFindTransactionCommand);
    }

}
