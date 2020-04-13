package cs2103_w14_2.inventory.logic.parser;

import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.logic.commands.DeleteGoodCommand;
import org.junit.jupiter.api.Test;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteGoodCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteGoodCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteGoodCommandParserTest {

    private DeleteGoodCommandParser parser = new DeleteGoodCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1", new DeleteGoodCommand(INDEX_FIRST_SUPPLIER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteGoodCommand.MESSAGE_USAGE));
    }
}
