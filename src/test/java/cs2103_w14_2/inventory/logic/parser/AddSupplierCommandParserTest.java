package cs2103_w14_2.inventory.logic.parser;

import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.INVALID_FORMAT_OFFER_DESC;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.INVALID_GOOD_AND_FORMAT_OFFER_DESC;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.INVALID_GOOD_AND_PRICE_OFFER_DESC;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.INVALID_GOOD_OFFER_DESC;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.INVALID_PRICE_AND_FORMAT_OFFER_DESC;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.INVALID_PRICE_OFFER_DESC;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.OFFER_DESC_APPLE;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.OFFER_DESC_BANANA;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.VALID_OFFER_APPLE;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.VALID_OFFER_BANANA;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static cs2103_w14_2.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static cs2103_w14_2.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.AMY;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.BOB;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.model.supplier.Email;
import cs2103_w14_2.inventory.model.supplier.Phone;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.logic.commands.AddSupplierCommand;
import cs2103_w14_2.inventory.model.good.GoodName;
import cs2103_w14_2.inventory.model.offer.Offer;
import cs2103_w14_2.inventory.model.offer.Price;
import cs2103_w14_2.inventory.model.supplier.Address;
import cs2103_w14_2.inventory.model.supplier.Name;
import cs2103_w14_2.inventory.testutil.SupplierBuilder;

public class AddSupplierCommandParserTest {
    private AddSupplierCommandParser parser = new AddSupplierCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Supplier expectedSupplier = new SupplierBuilder(BOB).withOffers(VALID_OFFER_APPLE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + OFFER_DESC_APPLE, new AddSupplierCommand(expectedSupplier));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + OFFER_DESC_APPLE, new AddSupplierCommand(expectedSupplier));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + OFFER_DESC_APPLE, new AddSupplierCommand(expectedSupplier));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + OFFER_DESC_APPLE, new AddSupplierCommand(expectedSupplier));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + OFFER_DESC_APPLE, new AddSupplierCommand(expectedSupplier));

        // multiple offers - all accepted
        Supplier expectedSupplierMultipleOffers =
                new SupplierBuilder(BOB).withOffers(VALID_OFFER_APPLE, VALID_OFFER_BANANA).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + OFFER_DESC_APPLE + OFFER_DESC_BANANA, new AddSupplierCommand(expectedSupplierMultipleOffers));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero offers
        Supplier expectedSupplier = new SupplierBuilder(AMY).withOffers().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddSupplierCommand(expectedSupplier));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddSupplierCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + OFFER_DESC_BANANA + OFFER_DESC_APPLE, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + OFFER_DESC_BANANA + OFFER_DESC_APPLE, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + OFFER_DESC_BANANA + OFFER_DESC_APPLE, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + OFFER_DESC_BANANA + OFFER_DESC_APPLE, Address.MESSAGE_CONSTRAINTS);

        // invalid good name in offer
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_GOOD_OFFER_DESC + OFFER_DESC_APPLE, GoodName.MESSAGE_CONSTRAINTS);

        // invalid price in offer
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_PRICE_OFFER_DESC + OFFER_DESC_APPLE, Price.MESSAGE_CONSTRAINTS);

        // invalid format in offer
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_FORMAT_OFFER_DESC + OFFER_DESC_APPLE, Offer.MESSAGE_CONSTRAINTS);

        // invalid good name precedes invalid price in offer
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_GOOD_AND_PRICE_OFFER_DESC + OFFER_DESC_APPLE, GoodName.MESSAGE_CONSTRAINTS);

        // invalid format precedes invalid good name in offer
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_GOOD_AND_FORMAT_OFFER_DESC + OFFER_DESC_APPLE, Offer.MESSAGE_CONSTRAINTS);

        // invalid format precedes invalid price in offer
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_PRICE_AND_FORMAT_OFFER_DESC + OFFER_DESC_APPLE, Offer.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // two invalid offers, only first invalid offer reported
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_PRICE_OFFER_DESC + INVALID_FORMAT_OFFER_DESC, Price.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + OFFER_DESC_BANANA + OFFER_DESC_APPLE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddSupplierCommand.MESSAGE_USAGE));
    }
}
