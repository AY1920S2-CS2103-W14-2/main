package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.BuyCommand;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;

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

    private static final String VALID_GOOD_NAME_DESC = " " + PREFIX_GOOD_NAME + VALID_GOOD_NAME_STRING;
    private static final String VALID_GOOD_NAME_EXTRA_DESC = " " + PREFIX_GOOD_NAME + VALID_GOOD_NAME_EXTRA_STRING;
    private static final String INVALID_GOOD_NAME_DESC = " " + PREFIX_GOOD_NAME + INVALID_GOOD_NAME_STRING;
    private static final String VALID_GOOD_QUANTITY_DESC = " " + PREFIX_QUANTITY + VALID_GOOD_QUANTITY_STRING;
    private static final String VALID_GOOD_QUANTITY_EXTRA_DESC =
            " " + PREFIX_QUANTITY + VALID_GOOD_QUANTITY_EXTRA_STRING;
    private static final String INVALID_GOOD_QUANTITY_NEGATIVE_DESC =
            " " + PREFIX_QUANTITY + INVALID_GOOD_QUANTITY_NEGATIVE_STRING;
    private static final String INVALID_GOOD_QUANTITY_OVERFLOW_DESC =
            " " + PREFIX_QUANTITY + INVALID_GOOD_QUANTITY_OVERFLOW_STRING;

    private BuyCommandParser parser = new BuyCommandParser();
    private Good validGood = Good.newGoodEntry(new GoodName(VALID_GOOD_NAME_STRING),
            new GoodQuantity(VALID_GOOD_QUANTITY_STRING));

    @Test
    void parse_allFieldsPresent_success() {
        BuyCommand expectedCommand = new BuyCommand(VALID_GOOD_NAME, VALID_GOOD_QUANTITY, INDEX_FIRST_SUPPLIER);

        // Happy case
        assertParseSuccess(parser, VALID_SUPPLIER_INDEX_PREAMBLE + VALID_GOOD_NAME_DESC
                + VALID_GOOD_QUANTITY_DESC, expectedCommand);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_SUPPLIER_INDEX_PREAMBLE
                + VALID_GOOD_NAME_DESC + VALID_GOOD_QUANTITY_DESC, expectedCommand);

        // multiple goodNames, only last one accepted
        assertParseSuccess(parser, VALID_SUPPLIER_INDEX_PREAMBLE + VALID_GOOD_NAME_EXTRA_DESC
                + VALID_GOOD_NAME_DESC + VALID_GOOD_QUANTITY_DESC, expectedCommand);

        // multiple quantities, only last one accepted
        assertParseSuccess(parser, VALID_SUPPLIER_INDEX_PREAMBLE + VALID_GOOD_NAME_DESC
                + VALID_GOOD_QUANTITY_EXTRA_DESC + VALID_GOOD_QUANTITY_DESC, expectedCommand);
    }

    @Test
    void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE);

        // missing good name
        assertParseFailure(parser, VALID_SUPPLIER_INDEX_PREAMBLE + VALID_GOOD_QUANTITY_DESC,
                expectedMessage);

        // missing good quantity
        assertParseFailure(parser, VALID_SUPPLIER_INDEX_PREAMBLE + VALID_GOOD_NAME_DESC,
                expectedMessage);

        // missing supplier display index
        assertParseFailure(parser, VALID_GOOD_QUANTITY_DESC + VALID_GOOD_NAME_DESC,
                expectedMessage);
    }

    @Test
    void parse_invalidValue_failure() {

        // invalid good name
        assertParseFailure(parser, VALID_SUPPLIER_INDEX_PREAMBLE
                + INVALID_GOOD_NAME_DESC + VALID_GOOD_QUANTITY_DESC, GoodName.MESSAGE_CONSTRAINTS);

        // invalid good quantities
        assertParseFailure(parser, VALID_SUPPLIER_INDEX_PREAMBLE + VALID_GOOD_NAME_DESC
                + INVALID_GOOD_QUANTITY_NEGATIVE_DESC, GoodQuantity.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, VALID_SUPPLIER_INDEX_PREAMBLE + VALID_GOOD_NAME_DESC
                + INVALID_GOOD_QUANTITY_OVERFLOW_DESC, GoodQuantity.MESSAGE_CONSTRAINTS);
    }
}
