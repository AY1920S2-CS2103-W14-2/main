package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOOD_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGoodPricePairFromSupplierCommand;
import seedu.address.logic.commands.DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName;
import seedu.address.model.good.GoodName;
import seedu.address.testutil.DeleteSupplierGoodNameBuilder;
import seedu.address.testutil.GoodBuilder;

public class DeleteGoodPricePairFromSupplierCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteGoodPricePairFromSupplierCommand.MESSAGE_USAGE);

    private DeleteGoodPricePairFromSupplierCommandParser parser = new DeleteGoodPricePairFromSupplierCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", DeleteGoodPricePairFromSupplierCommand.MESSAGE_MUST_INCLUDE_GOODNAME);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_SUPPLIER;

        String userInput = targetIndex.getOneBased() + " " + PREFIX_GOOD_NAME + VALID_GOOD_APPLE;
        Set<GoodName> goodNames = new HashSet<>();
        goodNames.add(new GoodBuilder().withGoodName(VALID_GOOD_APPLE).build().getGoodName());

        DeleteSupplierGoodName descriptor = new DeleteSupplierGoodNameBuilder().withGoodNames(goodNames).build();

        DeleteGoodPricePairFromSupplierCommand expectedCommand =
                new DeleteGoodPricePairFromSupplierCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
