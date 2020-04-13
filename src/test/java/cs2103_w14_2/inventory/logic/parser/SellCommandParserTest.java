package cs2103_w14_2.inventory.logic.parser;

import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_FIRST_GOOD;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.logic.commands.CommandTestUtil;
import cs2103_w14_2.inventory.logic.commands.SellCommand;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.model.good.GoodQuantity;
import cs2103_w14_2.inventory.model.offer.Price;

class SellCommandParserTest {

    private static final String VALID_GOOD_QUANTITY_STRING = "10";
    private static final String VALID_GOOD_QUANTITY_EXTRA_STRING = "11";
    private static final String INVALID_GOOD_QUANTITY_NEGATIVE_STRING = "-1";
    private static final String INVALID_GOOD_QUANTITY_OVERFLOW_STRING = "9999999999999";
    private static final String VALID_SELLING_PRICE_STRING = "6.9";
    private static final String INVALID_SELLING_PRICE_STRING = "-6.9";
    private static final String VALID_GOOD_INDEX_PREAMBLE = " 1 ";


    private static final GoodQuantity VALID_GOOD_QUANTITY = new GoodQuantity(VALID_GOOD_QUANTITY_STRING);
    private static final Price VALID_SELLING_PRICE = new Price(VALID_SELLING_PRICE_STRING);

    private static final String VALID_GOOD_QUANTITY_DESC = " " + CliSyntax.PREFIX_QUANTITY + VALID_GOOD_QUANTITY_STRING;
    private static final String VALID_GOOD_QUANTITY_EXTRA_DESC =
            " " + CliSyntax.PREFIX_QUANTITY + VALID_GOOD_QUANTITY_EXTRA_STRING;

    private static final String VALID_SELLING_PRICE_DESC = " " + CliSyntax.PREFIX_PRICE + VALID_SELLING_PRICE_STRING;
    private static final String INVALID_SELLING_PRICE_DESC = " " + CliSyntax.PREFIX_PRICE + INVALID_SELLING_PRICE_STRING;

    private static final String INVALID_GOOD_QUANTITY_NEGATIVE_DESC =
            " " + CliSyntax.PREFIX_QUANTITY + INVALID_GOOD_QUANTITY_NEGATIVE_STRING;
    private static final String INVALID_GOOD_QUANTITY_OVERFLOW_DESC =
            " " + CliSyntax.PREFIX_QUANTITY + INVALID_GOOD_QUANTITY_OVERFLOW_STRING;


    private SellCommandParser parser = new SellCommandParser();

    @Test
    void parse_allFieldsPresent_success() {
        SellCommand expectedCommand = new SellCommand(VALID_GOOD_QUANTITY, VALID_SELLING_PRICE, INDEX_FIRST_GOOD);

        // Happy case
        CommandParserTestUtil.assertParseSuccess(parser, VALID_GOOD_INDEX_PREAMBLE + VALID_GOOD_QUANTITY_DESC
                + VALID_SELLING_PRICE_DESC, expectedCommand);

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + VALID_GOOD_INDEX_PREAMBLE + VALID_GOOD_QUANTITY_DESC
                + VALID_SELLING_PRICE_DESC, expectedCommand);

        // multiple quantities, only last one accepted
        CommandParserTestUtil.assertParseSuccess(parser, VALID_GOOD_INDEX_PREAMBLE + VALID_GOOD_QUANTITY_EXTRA_DESC
                + VALID_GOOD_QUANTITY_DESC + VALID_SELLING_PRICE_DESC, expectedCommand);
    }

    @Test
    void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE);

        // missing good display index
        CommandParserTestUtil.assertParseFailure(parser, VALID_GOOD_QUANTITY_DESC + VALID_SELLING_PRICE_DESC,
                expectedMessage);

        // missing good quantity
        CommandParserTestUtil.assertParseFailure(parser, VALID_GOOD_INDEX_PREAMBLE + VALID_SELLING_PRICE_DESC,
                expectedMessage);

        // missing selling price
        CommandParserTestUtil.assertParseFailure(parser, VALID_GOOD_INDEX_PREAMBLE + VALID_GOOD_QUANTITY_DESC,
                expectedMessage);
    }

    @Test
    void parse_invalidValue_failure() {
        // invalid price
        CommandParserTestUtil.assertParseFailure(parser, VALID_GOOD_INDEX_PREAMBLE + VALID_GOOD_QUANTITY_DESC + INVALID_SELLING_PRICE_DESC,
                Price.MESSAGE_CONSTRAINTS);

        // invalid good quantities
        CommandParserTestUtil.assertParseFailure(parser, VALID_GOOD_INDEX_PREAMBLE + INVALID_GOOD_QUANTITY_NEGATIVE_DESC
                + VALID_SELLING_PRICE_DESC, GoodQuantity.MESSAGE_CONSTRAINTS);

        CommandParserTestUtil.assertParseFailure(parser, VALID_GOOD_INDEX_PREAMBLE + INVALID_GOOD_QUANTITY_OVERFLOW_DESC
                + VALID_SELLING_PRICE_DESC, GoodQuantity.MESSAGE_CONSTRAINTS);
    }
}

