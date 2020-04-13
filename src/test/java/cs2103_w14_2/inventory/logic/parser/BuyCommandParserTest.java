package cs2103_w14_2.inventory.logic.parser;

import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.logic.commands.CommandTestUtil;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.logic.commands.BuyCommand;
import cs2103_w14_2.inventory.model.good.Good;
import cs2103_w14_2.inventory.model.good.GoodName;
import cs2103_w14_2.inventory.model.good.GoodQuantity;

class BuyCommandParserTest {
    private static final String VALID_GOOD_NAME_STRING = "Durian";
    private static final String VALID_GOOD_NAME_EXTRA_STRING = "Durian123";
    private static final String INVALID_GOOD_NAME_STRING = "Dur@_an";
    private static final String VALID_GOOD_QUANTITY_STRING = "10";
    private static final String VALID_GOOD_QUANTITY_EXTRA_STRING = "11";
    private static final String INVALID_GOOD_QUANTITY_NEGATIVE_STRING = "-1";
    private static final String INVALID_GOOD_QUANTITY_OVERFLOW_STRING = "9999999999999";
    private static final String VALID_SUPPLIER_INDEX_PREAMBLE = " 1 ";

    private static final GoodName VALID_GOOD_NAME = new GoodName(VALID_GOOD_NAME_STRING);
    private static final GoodQuantity VALID_GOOD_QUANTITY = new GoodQuantity(VALID_GOOD_QUANTITY_STRING);

    private static final String VALID_GOOD_NAME_DESC = " " + CliSyntax.PREFIX_GOOD_NAME + VALID_GOOD_NAME_STRING;
    private static final String VALID_GOOD_NAME_EXTRA_DESC = " " + CliSyntax.PREFIX_GOOD_NAME + VALID_GOOD_NAME_EXTRA_STRING;
    private static final String INVALID_GOOD_NAME_DESC = " " + CliSyntax.PREFIX_GOOD_NAME + INVALID_GOOD_NAME_STRING;
    private static final String VALID_GOOD_QUANTITY_DESC = " " + CliSyntax.PREFIX_QUANTITY + VALID_GOOD_QUANTITY_STRING;
    private static final String VALID_GOOD_QUANTITY_EXTRA_DESC =
            " " + CliSyntax.PREFIX_QUANTITY + VALID_GOOD_QUANTITY_EXTRA_STRING;
    private static final String INVALID_GOOD_QUANTITY_NEGATIVE_DESC =
            " " + CliSyntax.PREFIX_QUANTITY + INVALID_GOOD_QUANTITY_NEGATIVE_STRING;
    private static final String INVALID_GOOD_QUANTITY_OVERFLOW_DESC =
            " " + CliSyntax.PREFIX_QUANTITY + INVALID_GOOD_QUANTITY_OVERFLOW_STRING;

    private BuyCommandParser parser = new BuyCommandParser();
    private Good validGood = Good.newGoodEntry(new GoodName(VALID_GOOD_NAME_STRING),
            new GoodQuantity(VALID_GOOD_QUANTITY_STRING));

    @Test
    void parse_allFieldsPresent_success() {
        BuyCommand expectedCommand = new BuyCommand(VALID_GOOD_NAME, VALID_GOOD_QUANTITY, INDEX_FIRST_SUPPLIER);

        // Happy case
        CommandParserTestUtil.assertParseSuccess(parser, VALID_SUPPLIER_INDEX_PREAMBLE + VALID_GOOD_NAME_DESC
                + VALID_GOOD_QUANTITY_DESC, expectedCommand);

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + VALID_SUPPLIER_INDEX_PREAMBLE
                + VALID_GOOD_NAME_DESC + VALID_GOOD_QUANTITY_DESC, expectedCommand);

        // multiple goodNames, only last one accepted
        CommandParserTestUtil.assertParseSuccess(parser, VALID_SUPPLIER_INDEX_PREAMBLE + VALID_GOOD_NAME_EXTRA_DESC
                + VALID_GOOD_NAME_DESC + VALID_GOOD_QUANTITY_DESC, expectedCommand);

        // multiple quantities, only last one accepted
        CommandParserTestUtil.assertParseSuccess(parser, VALID_SUPPLIER_INDEX_PREAMBLE + VALID_GOOD_NAME_DESC
                + VALID_GOOD_QUANTITY_EXTRA_DESC + VALID_GOOD_QUANTITY_DESC, expectedCommand);
    }

    @Test
    void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE);

        // missing good name
        CommandParserTestUtil.assertParseFailure(parser, VALID_SUPPLIER_INDEX_PREAMBLE + VALID_GOOD_QUANTITY_DESC,
                expectedMessage);

        // missing good quantity
        CommandParserTestUtil.assertParseFailure(parser, VALID_SUPPLIER_INDEX_PREAMBLE + VALID_GOOD_NAME_DESC,
                expectedMessage);

        // missing supplier display index
        CommandParserTestUtil.assertParseFailure(parser, VALID_GOOD_QUANTITY_DESC + VALID_GOOD_NAME_DESC,
                expectedMessage);
    }

    @Test
    void parse_invalidValue_failure() {

        // invalid good name
        CommandParserTestUtil.assertParseFailure(parser, VALID_SUPPLIER_INDEX_PREAMBLE
                + INVALID_GOOD_NAME_DESC + VALID_GOOD_QUANTITY_DESC, GoodName.MESSAGE_CONSTRAINTS);

        // invalid good quantities
        CommandParserTestUtil.assertParseFailure(parser, VALID_SUPPLIER_INDEX_PREAMBLE + VALID_GOOD_NAME_DESC
                + INVALID_GOOD_QUANTITY_NEGATIVE_DESC, GoodQuantity.MESSAGE_CONSTRAINTS);

        CommandParserTestUtil.assertParseFailure(parser, VALID_SUPPLIER_INDEX_PREAMBLE + VALID_GOOD_NAME_DESC
                + INVALID_GOOD_QUANTITY_OVERFLOW_DESC, GoodQuantity.MESSAGE_CONSTRAINTS);
    }
}
