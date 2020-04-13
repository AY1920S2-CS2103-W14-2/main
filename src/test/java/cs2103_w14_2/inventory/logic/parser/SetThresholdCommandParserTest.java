package cs2103_w14_2.inventory.logic.parser;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.logic.commands.SetThresholdCommand;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.model.good.GoodQuantity;

public class SetThresholdCommandParserTest {

    private static final String VALID_GOOD_QUANTITY = "10";
    private static final String INVALID_GOOD_QUANTITY_NEGATIVE = "-1";

    private static final String VALID_INDEX_DESC = "1";
    private static final String VALID_GOOD_QUANTITY_DESC = " " + CliSyntax.PREFIX_QUANTITY + VALID_GOOD_QUANTITY;

    private static final Index VALID_INDEX = Index.fromOneBased(1);
    private static final GoodQuantity VALID_THRESHOLD = new GoodQuantity(VALID_GOOD_QUANTITY);

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetThresholdCommand.MESSAGE_USAGE);

    private SetThresholdCommandParser parser = new SetThresholdCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        // Happy case
        CommandParserTestUtil.assertParseSuccess(parser, VALID_INDEX_DESC + VALID_GOOD_QUANTITY_DESC,
                new SetThresholdCommand(VALID_INDEX, VALID_THRESHOLD));

        // multiple threshold, only last one accepted
        CommandParserTestUtil.assertParseSuccess(parser, VALID_INDEX_DESC + VALID_GOOD_QUANTITY_DESC + VALID_GOOD_QUANTITY_DESC,
                new SetThresholdCommand(VALID_INDEX, VALID_THRESHOLD));
    }

    @Test
    void parse_missingParts_failure() {

        // no index specified
        CommandParserTestUtil.assertParseFailure(parser, VALID_GOOD_QUANTITY, MESSAGE_INVALID_FORMAT);

        // missing threshold
        CommandParserTestUtil.assertParseFailure(parser, VALID_INDEX_DESC, MESSAGE_INVALID_FORMAT);

        //  no index and missing threshold
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        CommandParserTestUtil.assertParseFailure(parser, "-5" + VALID_GOOD_QUANTITY, MESSAGE_INVALID_FORMAT);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, "0" + VALID_GOOD_QUANTITY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid threshold
        CommandParserTestUtil.assertParseFailure(parser, VALID_INDEX_DESC + INVALID_GOOD_QUANTITY_NEGATIVE, MESSAGE_INVALID_FORMAT);
    }

}
