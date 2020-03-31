package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetThresholdCommand;
import seedu.address.model.good.GoodQuantity;

public class SetThresholdCommandParserTest {

    private static final String VALID_GOOD_QUANTITY = "10";
    private static final String INVALID_GOOD_QUANTITY_NEGATIVE = "-1";

    private static final String VALID_INDEX_DESC = "1";
    private static final String VALID_GOOD_QUANTITY_DESC = " " + PREFIX_QUANTITY + VALID_GOOD_QUANTITY;

    private static final Index VALID_INDEX = Index.fromOneBased(1);
    private static final GoodQuantity VALID_THRESHOLD = new GoodQuantity(VALID_GOOD_QUANTITY);

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetThresholdCommand.MESSAGE_USAGE);

    private SetThresholdCommandParser parser = new SetThresholdCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        // Happy case
        assertParseSuccess(parser, VALID_INDEX_DESC + VALID_GOOD_QUANTITY_DESC,
                new SetThresholdCommand(VALID_INDEX, VALID_THRESHOLD));

        // multiple threshold, only last one accepted
        assertParseSuccess(parser, VALID_INDEX_DESC + VALID_GOOD_QUANTITY_DESC + VALID_GOOD_QUANTITY_DESC,
                new SetThresholdCommand(VALID_INDEX, VALID_THRESHOLD));
    }

    @Test
    void parse_missingParts_failure() {

        // no index specified
        assertParseFailure(parser, VALID_GOOD_QUANTITY, MESSAGE_INVALID_FORMAT);

        // missing threshold
        assertParseFailure(parser, VALID_INDEX_DESC, MESSAGE_INVALID_FORMAT);

        //  no index and missing threshold
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_GOOD_QUANTITY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_GOOD_QUANTITY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid threshold
        assertParseFailure(parser, VALID_INDEX_DESC + INVALID_GOOD_QUANTITY_NEGATIVE, MESSAGE_INVALID_FORMAT);
    }

}
