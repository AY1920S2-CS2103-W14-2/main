package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.good.Good;
import seedu.address.model.offer.Price;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.offer.Offer;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String offer} into a {@code Offer}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code offer} or its constituent is invalid.
     */
    public static Offer parseOffer(String offer) throws ParseException {
        requireNonNull(offer);
        String trimmedOffer = offer.trim();

        String goodPricePair[] = splitOnLastWhitespace(trimmedOffer);
        if (goodPricePair.length != 2) {
            throw new ParseException(Offer.MESSAGE_CONSTRAINTS);
        }

        String good = goodPricePair[0];
        String price = goodPricePair[1];
        return new Offer(parseGood(good), parsePrice(price));
    }

    /**
     * Parses a {@code String good} into a {@code Good}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code good} is invalid.
     */
    public static Good parseGood(String good) throws ParseException {
        requireNonNull(good);
        String trimmedGood = good.trim();
        if (!Good.isValidGoodName(trimmedGood)) {
            throw new ParseException(Good.MESSAGE_CONSTRAINTS);
        }
        return new Good(trimmedGood);
    }

    /**
     * Parses a {@code String price} into a {@code price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static Price parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }

        return new Price(trimmedPrice);
    }

    /**
     * Parses {@code Collection<String> offers} into a {@code List<Offer>}.
     */
    public static List<Offer> parseTags(Collection<String> offers) throws ParseException {
        requireNonNull(offers);
        final List<Offer> offerSet = new ArrayList<>();
        for (String offer : offers) {
            offerSet.add(parseOffer(offer));
        }
        return offerSet;
    }

    /**
     * Returns a {@code String} array as if {@code String.split()} is called only on its last whitespace.
     * Assumes: the {@code String} is already stripped of trailing and leading whitespaces.
     *
     * @param string the {@code String} to be split
     * @return the {@code String} array containing the split result
     */
    public static String[] splitOnLastWhitespace(String string) {
        String words[] = string.split(" ");
        String result[] = new String[2];

        result[0] = String.join(" ", Arrays.copyOfRange(words, 0, words.length - 1));
        result[1] = words[words.length - 1];

        return result;
    }
}
