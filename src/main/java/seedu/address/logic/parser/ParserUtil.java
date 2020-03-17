package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;
import seedu.address.model.offer.Offer;
import seedu.address.model.offer.Price;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

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
     * Parses a {@code String goodName} into a {@code goodName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code goodName} is invalid.
     */
    public static GoodName parseGoodName(String goodName) throws ParseException {
        requireNonNull(goodName);
        String trimmedName = goodName.trim();
        if (!GoodName.isValidGoodName(trimmedName)) {
            throw new ParseException(GoodName.MESSAGE_CONSTRAINTS);
        }
        return new GoodName(trimmedName);
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

        if (!trimmedOffer.contains(" ")) {
            throw new ParseException(Offer.MESSAGE_CONSTRAINTS);
        }

        String[] goodPricePair = splitOnLastWhitespace(trimmedOffer);

        String goodString = goodPricePair[0];
        String priceString = goodPricePair[1];
        GoodName good = parseGoodName(goodString);
        Price price = parsePrice(priceString);
        return new Offer(good, price);
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
     * Parses {@code Collection<String> offers} into a {@code Set<Offer>}.
     */
    public static Set<Offer> parseOffers(Collection<String> offers) throws ParseException {
        requireAllNonNull(offers);
        final Set<Offer> offerSet = new HashSet<>();
        for (String offer : offers) {
            offerSet.add(parseOffer(offer));
        }
        return offerSet;
    }

    /**
     * Returns a {@code String} array as if {@code String.split()} is called only on its last whitespace.
     * Assumes: the {@code String} is already stripped of trailing and leading whitespaces,
     * and contains at least one whitespace.
     *
     * @param string the {@code String} to be split
     * @return the {@code String} array containing the split result
     */
    public static String[] splitOnLastWhitespace(String string) {
        requireNonNull(string);
        String[] words = string.split(" ");
        String[] result = new String[2];

        result[0] = String.join(" ", Arrays.copyOfRange(words, 0, words.length - 1));
        result[1] = words[words.length - 1];

        return result;
    }

    /**
     * Returns an Object array containing a Good and a Price in indices 0 and 1 respectively.
     * It is still subject to the same validation as the class constructors, but assumes that all input is valid.
     * The {@code Good} and {@code Price} corresponds to the one specified in the string.
     *
     * @param goodAndPrice the string representation of the good and price pair
     * @return an {@code Object} array containing the {@code Good} and {@code Price}
     */
    public static Object[] getGoodPricePair(String[] goodAndPrice) {
        requireAllNonNull(goodAndPrice);
        Object[] result = new Object[2];
        result[0] = new GoodName(goodAndPrice[0]);
        result[1] = new Price(goodAndPrice[1]);
        return result;
    }

    /**
     * Parses a {@code String goodQuantity} into a {@code goodQuantity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code goodQuantity} is invalid.
     */
    public static GoodQuantity parseGoodQuantity(String goodQuantity) throws ParseException {
        requireNonNull(goodQuantity);
        String trimmedQuantity = goodQuantity.trim();
        if (!GoodQuantity.isValidGoodQuantity(trimmedQuantity)) {
            throw new ParseException(GoodQuantity.MESSAGE_CONSTRAINTS);
        }
        return new GoodQuantity(trimmedQuantity);
    }
}
