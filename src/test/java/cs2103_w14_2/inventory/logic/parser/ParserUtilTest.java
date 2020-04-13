package cs2103_w14_2.inventory.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;
import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;
import cs2103_w14_2.inventory.model.supplier.Email;
import cs2103_w14_2.inventory.model.supplier.Phone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.model.good.GoodName;
import cs2103_w14_2.inventory.model.offer.Offer;
import cs2103_w14_2.inventory.model.offer.Price;
import cs2103_w14_2.inventory.model.supplier.Address;
import cs2103_w14_2.inventory.model.supplier.Name;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_OFFER = "nospace420";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_GOOD_1 = "potato";
    private static final String VALID_GOOD_2 = "chicken";
    private static final String VALID_PRICE_1 = "6.0";
    private static final String VALID_PRICE_2 = "4";
    private static final String VALID_OFFER_1 = VALID_GOOD_1 + " " + VALID_PRICE_1;
    private static final String VALID_OFFER_2 = VALID_GOOD_2 + " " + VALID_PRICE_2;

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, ParserUtil.MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_SUPPLIER, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_SUPPLIER, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        Assertions.assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        Assertions.assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        Assertions.assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        Assertions.assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseOffer_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseOffer(null));
    }

    @Test
    public void parseOffer_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseOffer(INVALID_OFFER));
    }

    @Test
    public void parseOffer_validValueWithoutWhitespace_returnsOffer() throws Exception {
        Offer expectedOffer = new Offer(new GoodName(VALID_GOOD_1), new Price(VALID_PRICE_1));
        assertEquals(expectedOffer, ParserUtil.parseOffer(VALID_OFFER_1));
    }

    @Test
    public void parseOffer_validValueWithWhitespace_returnsTrimmedOffer() throws Exception {
        String offerWithWhitespace = WHITESPACE + VALID_OFFER_1 + WHITESPACE;
        Offer expectedOffer = new Offer(new GoodName(VALID_GOOD_1), new Price(VALID_PRICE_1));
        assertEquals(expectedOffer, ParserUtil.parseOffer(offerWithWhitespace));
    }

    @Test
    public void parseOffers_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseOffers(null));
    }

    @Test
    public void parseOffers_collectionWithInvalidOffers_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseOffers(Arrays.asList(VALID_OFFER_1, INVALID_OFFER)));
    }

    @Test
    public void parseOffers_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseOffers(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseOffers_collectionWithValidOffers_returnsOfferSet() throws Exception {
        Set<Offer> actualOfferSet = ParserUtil.parseOffers(Arrays.asList(VALID_OFFER_1, VALID_OFFER_2));
        Set<Offer> expectedOfferSet = new HashSet<>(Arrays.asList(
                new Offer(new GoodName(VALID_GOOD_1), new Price(VALID_PRICE_1)),
                new Offer(new GoodName(VALID_GOOD_2), new Price(VALID_PRICE_2))
        ));

        assertEquals(actualOfferSet, expectedOfferSet);
    }
}
