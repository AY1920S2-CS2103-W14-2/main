package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.BuyCommand;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;

class BuyCommandParserTest {
    private static final String VALID_GOOD_NAME = "Durian";
    private static final String VALID_GOOD_NAME_EXTRA = "Durian123";
    private static final String INVALID_GOOD_NAME = "Dur@_an";
    private static final String VALID_GOOD_QUANTITY = "10";
    private static final String VALID_GOOD_QUANTITY_EXTRA = "11";
    private static final String INVALID_GOOD_QUANTITY_NEGATIVE = "-1";
    private static final String INVALID_GOOD_QUANTITY_OVERFLOW_INT = "9999999999999";

    private static final String VALID_GOOD_NAME_DESC = " " + PREFIX_GOOD_NAME + VALID_GOOD_NAME;
    private static final String VALID_GOOD_NAME_EXTRA_DESC = " " + PREFIX_GOOD_NAME + VALID_GOOD_NAME_EXTRA;
    private static final String INVALID_GOOD_NAME_DESC = " " + PREFIX_GOOD_NAME + INVALID_GOOD_NAME;
    private static final String VALID_GOOD_QUANTITY_DESC = " " + PREFIX_QUANTITY + VALID_GOOD_QUANTITY;
    private static final String VALID_GOOD_QUANTITY_EXTRA_DESC = " " + PREFIX_QUANTITY + VALID_GOOD_QUANTITY_EXTRA;
    private static final String INVALID_GOOD_QUANTITY_NEGATIVE_DESC =
            " " + PREFIX_QUANTITY + INVALID_GOOD_QUANTITY_NEGATIVE;
    private static final String INVALID_GOOD_QUANTITY_OVERFLOW_INT_DESC =
            " " + PREFIX_QUANTITY + INVALID_GOOD_QUANTITY_OVERFLOW_INT;

    private BuyCommandParser parser = new BuyCommandParser();
    private Good validGood = new Good(new GoodName(VALID_GOOD_NAME), new GoodQuantity(VALID_GOOD_QUANTITY));

    @Test
    void parse_allFieldsPresent_success() {
        BuyCommand expectedCommand = new BuyCommand(validGood);
        // Happy case
        assertParseSuccess(parser, VALID_GOOD_NAME_DESC + VALID_GOOD_QUANTITY_DESC,
                new BuyCommand(validGood));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_GOOD_NAME_DESC + VALID_GOOD_QUANTITY_DESC,
                new BuyCommand(validGood));

        // multiple goodNames, only last one accepted
        assertParseSuccess(parser, VALID_GOOD_NAME_EXTRA_DESC + VALID_GOOD_NAME_DESC
                + VALID_GOOD_QUANTITY_DESC, new BuyCommand(validGood));

        // multiple quantities, only last one accepted
        assertParseSuccess(parser, VALID_GOOD_NAME_DESC + VALID_GOOD_QUANTITY_EXTRA_DESC
                + VALID_GOOD_QUANTITY_DESC, new BuyCommand(validGood));
    }

    @Test
    void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE);

        // missing good name
        assertParseFailure(parser, VALID_GOOD_QUANTITY_DESC,
                expectedMessage);

        // missing good quantity
        assertParseFailure(parser, VALID_GOOD_NAME_DESC,
                expectedMessage);
    }

    @Test
    void parse_invalidValue_failure() {
        // invalid good name
        assertParseFailure(parser, INVALID_GOOD_NAME_DESC + VALID_GOOD_QUANTITY_DESC,
                GoodName.MESSAGE_CONSTRAINTS);

        // invalid good quantities
        assertParseFailure(parser, VALID_GOOD_NAME_DESC + INVALID_GOOD_QUANTITY_NEGATIVE_DESC,
                GoodQuantity.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_GOOD_NAME_DESC + INVALID_GOOD_QUANTITY_OVERFLOW_INT_DESC,
                GoodQuantity.MESSAGE_CONSTRAINTS);
    }
}
