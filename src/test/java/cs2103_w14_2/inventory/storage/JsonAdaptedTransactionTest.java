package cs2103_w14_2.inventory.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import java.util.Set;
import java.util.stream.Collectors;

import cs2103_w14_2.inventory.commons.exceptions.IllegalValueException;
import cs2103_w14_2.inventory.model.good.GoodQuantity;
import cs2103_w14_2.inventory.model.offer.Price;
import cs2103_w14_2.inventory.model.supplier.Name;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.transaction.BuyTransaction;
import cs2103_w14_2.inventory.model.transaction.SellTransaction;
import cs2103_w14_2.inventory.model.transaction.TransactionId;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.TypicalGoods;
import cs2103_w14_2.inventory.testutil.TypicalSuppliers;
import cs2103_w14_2.inventory.testutil.TypicalTransactions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.model.good.Good;

public class JsonAdaptedTransactionTest {

    private static final String INVALID_NAME = "R@chel";

    private static final String VALID_GOOD_NAME = "anything";
    private static final int INVALID_GOOD_QUANTITY = -1;
    private static final int VALID_THRESHOLD = 100;

    private static final String VALID_PHONE = TypicalSuppliers.BENSON.getPhone().toString();
    private static final String VALID_EMAIL = TypicalSuppliers.BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = TypicalSuppliers.BENSON.getAddress().toString();
    private static final Set<JsonAdaptedOffer> VALID_OFFERS = TypicalSuppliers.BENSON.getOffers().stream()
            .map(JsonAdaptedOffer::new)
            .collect(Collectors.toSet());

    private static final String VALID_TYPE_BUY_TRANSACTION = BuyTransaction.class.getSimpleName();
    private static final String VALID_TYPE_SELL_TRANSACTION = SellTransaction.class.getSimpleName();

    private static final String VALID_TRANSACTION_ID = "dce857b1-36db-4f96-83a6-4dfc9a1e4ad9";
    private static final String INVALID_TRANSACTION_ID = "this is id";

    private static final JsonAdaptedGood VALID_GOOD = new JsonAdaptedGood(TypicalGoods.APPLE);
    private static final JsonAdaptedGood INVALID_GOOD = new JsonAdaptedGood(VALID_GOOD_NAME, INVALID_GOOD_QUANTITY,
            VALID_THRESHOLD);

    private static final String VALID_PRICE = "12.46";
    private static final String INVALID_PRICE = "-1.0";

    private static final JsonAdaptedSupplier VALID_PERSON = new JsonAdaptedSupplier(TypicalSuppliers.ALICE);
    private static final JsonAdaptedSupplier INVALID_PERSON = new JsonAdaptedSupplier(INVALID_NAME, VALID_PHONE,
            VALID_EMAIL, VALID_ADDRESS, VALID_OFFERS);

    @Test
    public void toModelType_validTransactionDetails_returnsTransaction() throws Exception {
        JsonAdaptedTransaction buyTransaction = new JsonAdaptedTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);
        Assertions.assertEquals(TypicalTransactions.BUY_APPLE_TRANSACTION, buyTransaction.toModelType());

        JsonAdaptedTransaction sellTransaction = new JsonAdaptedTransaction(TypicalTransactions.SELL_APPLE_TRANSACTION);
        Assertions.assertEquals(TypicalTransactions.SELL_APPLE_TRANSACTION, sellTransaction.toModelType());
    }

    @Test
    public void toModelType_invalidTransactionId_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, INVALID_TRANSACTION_ID,
                        VALID_GOOD, VALID_PRICE, VALID_PERSON);
        String expectedMessage = TransactionId.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);

        JsonAdaptedTransaction sellTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_SELL_TRANSACTION, INVALID_TRANSACTION_ID,
                        VALID_GOOD, VALID_PRICE, VALID_PERSON);
        Assert.assertThrows(IllegalValueException.class, expectedMessage, sellTransaction::toModelType);
    }

    @Test
    public void toModelType_nullTransactionId_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, null,
                        VALID_GOOD, VALID_PRICE, VALID_PERSON);
        String expectedMessage = String.format(JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT, TransactionId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);

        JsonAdaptedTransaction sellTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_SELL_TRANSACTION, null,
                        VALID_GOOD, VALID_PRICE, VALID_PERSON);
        Assert.assertThrows(IllegalValueException.class, expectedMessage, sellTransaction::toModelType);
    }

    @Test
    public void toModelType_invalidGood_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, VALID_TRANSACTION_ID,
                        INVALID_GOOD, VALID_PRICE, VALID_PERSON);
        String expectedMessage = GoodQuantity.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);

        JsonAdaptedTransaction sellTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_SELL_TRANSACTION, VALID_TRANSACTION_ID,
                        INVALID_GOOD, VALID_PRICE, VALID_PERSON);
        Assert.assertThrows(IllegalValueException.class, expectedMessage, sellTransaction::toModelType);
    }

    @Test
    public void toModelType_nullGood_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, VALID_TRANSACTION_ID,
                        null, VALID_PRICE, VALID_PERSON);
        String expectedMessage = String.format(JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT, Good.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);

        JsonAdaptedTransaction sellTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_SELL_TRANSACTION, VALID_TRANSACTION_ID,
                        null, VALID_PRICE, VALID_PERSON);
        Assert.assertThrows(IllegalValueException.class, expectedMessage, sellTransaction::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, VALID_TRANSACTION_ID,
                        VALID_GOOD, INVALID_PRICE, VALID_PERSON);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);

        JsonAdaptedTransaction sellTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_SELL_TRANSACTION, VALID_TRANSACTION_ID,
                        VALID_GOOD, INVALID_PRICE, VALID_PERSON);
        Assert.assertThrows(IllegalValueException.class, expectedMessage, sellTransaction::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, VALID_TRANSACTION_ID,
                        VALID_GOOD, null, VALID_PERSON);
        String expectedMessage = String.format(JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);

        JsonAdaptedTransaction sellTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_SELL_TRANSACTION, VALID_TRANSACTION_ID,
                        VALID_GOOD, null, VALID_PERSON);
        Assert.assertThrows(IllegalValueException.class, expectedMessage, sellTransaction::toModelType);
    }

    @Test
    public void toModelType_invalidSupplier_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, VALID_TRANSACTION_ID,
                        VALID_GOOD, VALID_PRICE, INVALID_PERSON);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);
    }

    @Test
    public void toModelType_nullSupplier_throwsIllegalValueException() {
        JsonAdaptedTransaction buyTransaction =
                new JsonAdaptedTransaction(VALID_TYPE_BUY_TRANSACTION, VALID_TRANSACTION_ID,
                        VALID_GOOD, VALID_PRICE, null);
        String expectedMessage = String.format(JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT, Supplier.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, buyTransaction::toModelType);
    }


}
