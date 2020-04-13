package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FORMAT_OFFER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GOOD_OFFER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_OFFER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.OFFER_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.OFFER_DESC_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFER_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFER_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_SUPPLIER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditSupplierCommand;
import seedu.address.logic.commands.EditSupplierCommand.EditSupplierDescriptor;
import seedu.address.model.good.GoodName;
import seedu.address.model.offer.Offer;
import seedu.address.model.offer.Price;
import seedu.address.model.supplier.Address;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
import seedu.address.testutil.EditSupplierDescriptorBuilder;

public class EditSupplierCommandParserTest {

    private static final String OFFER_EMPTY = " " + PREFIX_OFFER;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSupplierCommand.MESSAGE_USAGE);

    private EditSupplierCommandParser parser = new EditSupplierCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditSupplierCommand.MESSAGE_NOT_EDITED);

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
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_GOOD_OFFER_DESC, GoodName.MESSAGE_CONSTRAINTS); // invalid good name
        assertParseFailure(parser, "1" + INVALID_PRICE_OFFER_DESC, Price.MESSAGE_CONSTRAINTS); // invalid offer price
        assertParseFailure(parser, "1" + INVALID_FORMAT_OFFER_DESC, Offer.MESSAGE_CONSTRAINTS); // invalid offer price

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_OFFER} alone will reset the offers of the {@code Supplier} being edited,
        // parsing it together with a valid offer results in error
        assertParseFailure(parser, "1" + OFFER_DESC_APPLE + OFFER_DESC_BANANA + OFFER_EMPTY, Offer.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + OFFER_DESC_APPLE + OFFER_EMPTY + OFFER_DESC_BANANA, Offer.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + OFFER_EMPTY + OFFER_DESC_APPLE + OFFER_DESC_BANANA, Offer.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_SUPPLIER;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + OFFER_DESC_BANANA
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + OFFER_DESC_APPLE;

        EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withOffers(VALID_OFFER_BANANA, VALID_OFFER_APPLE).build();
        EditSupplierCommand expectedCommand = new EditSupplierCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_SUPPLIER;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditSupplierCommand expectedCommand = new EditSupplierCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_SUPPLIER;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditSupplierCommand expectedCommand = new EditSupplierCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditSupplierDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditSupplierCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditSupplierDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditSupplierCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditSupplierDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditSupplierCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // offers
        userInput = targetIndex.getOneBased() + OFFER_DESC_APPLE;
        descriptor = new EditSupplierDescriptorBuilder().withOffers(VALID_OFFER_APPLE).build();
        expectedCommand = new EditSupplierCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_SUPPLIER;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + OFFER_DESC_APPLE + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + OFFER_DESC_APPLE
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + OFFER_DESC_BANANA;

        EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withOffers(VALID_OFFER_APPLE, VALID_OFFER_APPLE, VALID_OFFER_BANANA)
                .build();
        EditSupplierCommand expectedCommand = new EditSupplierCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_SUPPLIER;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditSupplierCommand expectedCommand = new EditSupplierCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditSupplierDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditSupplierCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetOffers_success() {
        Index targetIndex = INDEX_THIRD_SUPPLIER;
        String userInput = targetIndex.getOneBased() + OFFER_EMPTY;

        EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withOffers().build();
        EditSupplierCommand expectedCommand = new EditSupplierCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
