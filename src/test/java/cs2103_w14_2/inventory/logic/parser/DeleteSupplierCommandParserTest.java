package cs2103_w14_2.inventory.logic.parser;

import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.logic.commands.DeleteSupplierCommand;
import org.junit.jupiter.api.Test;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteSupplierCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteSupplierCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteSupplierCommandParserTest {

    private DeleteSupplierCommandParser parser = new DeleteSupplierCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1", new DeleteSupplierCommand(INDEX_FIRST_SUPPLIER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteSupplierCommand.MESSAGE_USAGE));
    }
}
