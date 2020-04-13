package cs2103_w14_2.inventory.logic.parser;

import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;
import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_THIRD_SUPPLIER;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.logic.commands.CommandTestUtil;
import cs2103_w14_2.inventory.logic.commands.EditSupplierCommand;
import cs2103_w14_2.inventory.model.supplier.Address;
import cs2103_w14_2.inventory.model.supplier.Email;
import cs2103_w14_2.inventory.model.supplier.Name;
import cs2103_w14_2.inventory.model.supplier.Phone;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.model.good.GoodName;
import cs2103_w14_2.inventory.model.offer.Offer;
import cs2103_w14_2.inventory.model.offer.Price;
import cs2103_w14_2.inventory.testutil.EditSupplierDescriptorBuilder;

public class EditSupplierCommandParserTest {

    private static final String OFFER_EMPTY = " " + CliSyntax.PREFIX_OFFER;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditSupplierCommand.MESSAGE_USAGE);

    private EditSupplierCommandParser parser = new EditSupplierCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, "1", EditSupplierCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        CommandParserTestUtil.assertParseFailure(parser, "-5" + CommandTestUtil.NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, "0" + CommandTestUtil.NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_GOOD_OFFER_DESC, GoodName.MESSAGE_CONSTRAINTS); // invalid good name
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_PRICE_OFFER_DESC, Price.MESSAGE_CONSTRAINTS); // invalid offer price
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_FORMAT_OFFER_DESC, Offer.MESSAGE_CONSTRAINTS); // invalid offer price

        // invalid phone followed by valid email
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_PHONE_DESC + CommandTestUtil.EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_OFFER} alone will reset the offers of the {@code Supplier} being edited,
        // parsing it together with a valid offer results in error
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.OFFER_DESC_APPLE + CommandTestUtil.OFFER_DESC_BANANA + OFFER_EMPTY, Offer.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.OFFER_DESC_APPLE + OFFER_EMPTY + CommandTestUtil.OFFER_DESC_BANANA, Offer.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + OFFER_EMPTY + CommandTestUtil.OFFER_DESC_APPLE + CommandTestUtil.OFFER_DESC_BANANA, Offer.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.INVALID_EMAIL_DESC + CommandTestUtil.VALID_ADDRESS_AMY + CommandTestUtil.VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_SUPPLIER;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.OFFER_DESC_BANANA
                + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.ADDRESS_DESC_AMY + CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.OFFER_DESC_APPLE;

        EditSupplierCommand.EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_AMY)
                .withPhone(CommandTestUtil.VALID_PHONE_BOB).withEmail(CommandTestUtil.VALID_EMAIL_AMY).withAddress(CommandTestUtil.VALID_ADDRESS_AMY)
                .withOffers(CommandTestUtil.VALID_OFFER_BANANA, CommandTestUtil.VALID_OFFER_APPLE).build();
        EditSupplierCommand expectedCommand = new EditSupplierCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_SUPPLIER;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_AMY;

        EditSupplierCommand.EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withPhone(CommandTestUtil.VALID_PHONE_BOB)
                .withEmail(CommandTestUtil.VALID_EMAIL_AMY).build();
        EditSupplierCommand expectedCommand = new EditSupplierCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_SUPPLIER;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.NAME_DESC_AMY;
        EditSupplierCommand.EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_AMY).build();
        EditSupplierCommand expectedCommand = new EditSupplierCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + CommandTestUtil.PHONE_DESC_AMY;
        descriptor = new EditSupplierDescriptorBuilder().withPhone(CommandTestUtil.VALID_PHONE_AMY).build();
        expectedCommand = new EditSupplierCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + CommandTestUtil.EMAIL_DESC_AMY;
        descriptor = new EditSupplierDescriptorBuilder().withEmail(CommandTestUtil.VALID_EMAIL_AMY).build();
        expectedCommand = new EditSupplierCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + CommandTestUtil.ADDRESS_DESC_AMY;
        descriptor = new EditSupplierDescriptorBuilder().withAddress(CommandTestUtil.VALID_ADDRESS_AMY).build();
        expectedCommand = new EditSupplierCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // offers
        userInput = targetIndex.getOneBased() + CommandTestUtil.OFFER_DESC_APPLE;
        descriptor = new EditSupplierDescriptorBuilder().withOffers(CommandTestUtil.VALID_OFFER_APPLE).build();
        expectedCommand = new EditSupplierCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_SUPPLIER;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.PHONE_DESC_AMY + CommandTestUtil.ADDRESS_DESC_AMY + CommandTestUtil.EMAIL_DESC_AMY
                + CommandTestUtil.OFFER_DESC_APPLE + CommandTestUtil.PHONE_DESC_AMY + CommandTestUtil.ADDRESS_DESC_AMY + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.OFFER_DESC_APPLE
                + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.OFFER_DESC_BANANA;

        EditSupplierCommand.EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withPhone(CommandTestUtil.VALID_PHONE_BOB)
                .withEmail(CommandTestUtil.VALID_EMAIL_BOB).withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
                .withOffers(CommandTestUtil.VALID_OFFER_APPLE, CommandTestUtil.VALID_OFFER_APPLE, CommandTestUtil.VALID_OFFER_BANANA)
                .build();
        EditSupplierCommand expectedCommand = new EditSupplierCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_SUPPLIER;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.INVALID_PHONE_DESC + CommandTestUtil.PHONE_DESC_BOB;
        EditSupplierCommand.EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withPhone(CommandTestUtil.VALID_PHONE_BOB).build();
        EditSupplierCommand expectedCommand = new EditSupplierCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.INVALID_PHONE_DESC + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.PHONE_DESC_BOB;
        descriptor = new EditSupplierDescriptorBuilder().withPhone(CommandTestUtil.VALID_PHONE_BOB).withEmail(CommandTestUtil.VALID_EMAIL_BOB)
                .withAddress(CommandTestUtil.VALID_ADDRESS_BOB).build();
        expectedCommand = new EditSupplierCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetOffers_success() {
        Index targetIndex = INDEX_THIRD_SUPPLIER;
        String userInput = targetIndex.getOneBased() + OFFER_EMPTY;

        EditSupplierCommand.EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withOffers().build();
        EditSupplierCommand expectedCommand = new EditSupplierCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
