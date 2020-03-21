package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalTransactions.BUY_APPLE_TRANSACTION;
import static seedu.address.testutil.TypicalTransactions.SELL_APPLE_TRANSACTION;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodQuantity;
import seedu.address.model.offer.Price;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.BuyTransaction;
import seedu.address.model.transaction.SellTransaction;
import seedu.address.model.transaction.TransactionId;

public class JsonAdaptedTransactionTest {

    private static final String INVALID_NAME = "R@chel";

    private static final String VALID_GOOD_NAME = "anything";
    private static final int INVALID_GOOD_QUANTITY = -1;

    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final Set<JsonAdaptedOffer> VALID_OFFERS = BENSON.getOffers().stream()
            .map(JsonAdaptedOffer::new)
            .collect(Collectors.toSet());

    private static final String VALID_TYPE_BUY_TRANSACTION = BuyTransaction.class.getSimpleName();
    private static final String VALID_TYPE_SELL_TRANSACTION = SellTransaction.class.getSimpleName();

    private static final String VALID_TRANSACTION_ID = "dce857b1-36db-4f96-83a6-4dfc9a1e4ad9";
    private static final String INVALID_TRANSACTION_ID = "this is id";

    private static final JsonAdaptedGood VALID_GOOD = new JsonAdaptedGood(APPLE);
    private static final JsonAdaptedGood INVALID_GOOD = new JsonAdaptedGood(VALID_GOOD_NAME, INVALID_GOOD_QUANTITY);

    private static final String VALID_PRICE = "12.46";
    private static final String INVALID_PRICE = "-1.0";

    private static final JsonAdaptedPerson VALID_PERSON = new JsonAdaptedPerson(ALICE);
    private static final JsonAdaptedPerson INVALID_PERSON = new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE,
            VALID_EMAIL, VALID_ADDRESS, VALID_OFFERS);

    @Test
    public void toModelType_validTransactionDetails_returnsTransaction() throws Exception {
        JsonAdaptedTransaction buyTransaction = new JsonAdaptedTransaction(BUY_APPLE_TRANSACTION);
        assertEquals(BUY_APPLE_TRANSACTION, buyTransaction.toModelType());

        JsonAdaptedTransaction sellTransaction = new JsonAdaptedTransaction(SELL_APPLE_TRANSACTION);
        assertEquals(SELL_APPLE_TRANSACTION, sellTransaction.toModelType());
    }

    @Test
    public void toModelType_invalidTransactionId_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, INVALID_TRANSACTION_ID,
                        VALID_GOOD, VALID_PRICE, VALID_PERSON);
        String expectedMessage = TransactionId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);

        JsonAdaptedTransaction sellTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_SELL_TRANSACTION, INVALID_TRANSACTION_ID,
                        VALID_GOOD, VALID_PRICE, VALID_PERSON);
        assertThrows(IllegalValueException.class, expectedMessage, sellTransaction::toModelType);
    }

    @Test
    public void toModelType_nullTransactionId_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, null,
                        VALID_GOOD, VALID_PRICE, VALID_PERSON);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TransactionId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);

        JsonAdaptedTransaction sellTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_SELL_TRANSACTION, null,
                        VALID_GOOD, VALID_PRICE, VALID_PERSON);
        assertThrows(IllegalValueException.class, expectedMessage, sellTransaction::toModelType);
    }

    @Test
    public void toModelType_invalidGood_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, VALID_TRANSACTION_ID,
                        INVALID_GOOD, VALID_PRICE, VALID_PERSON);
        String expectedMessage = GoodQuantity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);

        JsonAdaptedTransaction sellTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_SELL_TRANSACTION, VALID_TRANSACTION_ID,
                        INVALID_GOOD, VALID_PRICE, VALID_PERSON);
        assertThrows(IllegalValueException.class, expectedMessage, sellTransaction::toModelType);
    }

    @Test
    public void toModelType_nullGood_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, VALID_TRANSACTION_ID,
                        null, VALID_PRICE, VALID_PERSON);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Good.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);

        JsonAdaptedTransaction sellTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_SELL_TRANSACTION, VALID_TRANSACTION_ID,
                        null, VALID_PRICE, VALID_PERSON);
        assertThrows(IllegalValueException.class, expectedMessage, sellTransaction::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, VALID_TRANSACTION_ID,
                        VALID_GOOD, INVALID_PRICE, VALID_PERSON);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);

        JsonAdaptedTransaction sellTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_SELL_TRANSACTION, VALID_TRANSACTION_ID,
                        VALID_GOOD, INVALID_PRICE, VALID_PERSON);
        assertThrows(IllegalValueException.class, expectedMessage, sellTransaction::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, VALID_TRANSACTION_ID,
                        VALID_GOOD, null, VALID_PERSON);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);

        JsonAdaptedTransaction sellTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_SELL_TRANSACTION, VALID_TRANSACTION_ID,
                        VALID_GOOD, null, VALID_PERSON);
        assertThrows(IllegalValueException.class, expectedMessage, sellTransaction::toModelType);
    }

    @Test
    public void toModelType_invalidPerson_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, VALID_TRANSACTION_ID,
                        VALID_GOOD, VALID_PRICE, INVALID_PERSON);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);
    }

    @Test
    public void toModelType_nullPerson_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, VALID_TRANSACTION_ID,
                        VALID_GOOD, VALID_PRICE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);
    }


}
