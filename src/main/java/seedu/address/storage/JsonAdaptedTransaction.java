package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.good.Good;
import seedu.address.model.offer.Price;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.BuyTransaction;
import seedu.address.model.transaction.SellTransaction;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionId;

/**
 * Jackson-friendly version of {@link Transaction}.
 */
public class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String type;
    private final String id;
    private final JsonAdaptedGood good;
    private final String price;
    private final JsonAdaptedPerson person;

    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("type") String type, @JsonProperty("id") String id,
                                  @JsonProperty("good") JsonAdaptedGood good, @JsonProperty("price") String price,
                                  @JsonProperty("person") JsonAdaptedPerson person) {
        this.type = type;
        this.id = id;
        this.good = good;
        this.price = price;
        this.person = person;
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedTransaction(Transaction source) {
        id = source.getId().toString();
        good = new JsonAdaptedGood(source.getGood());

        if (source instanceof BuyTransaction) {
            type = BuyTransaction.class.getSimpleName();
            BuyTransaction buyTransaction = (BuyTransaction) source;
            person = new JsonAdaptedPerson(buyTransaction.getPerson());
            price = buyTransaction.getBuyPrice().getValue();
        } else {
            type = SellTransaction.class.getSimpleName();
            SellTransaction sellTransaction = (SellTransaction) source;
            price = sellTransaction.getSellPrice().getValue();
            person = null;
        }
    }

    /**
     * Converts this Jackson-friendly adapted transaction object into the model's {@code Transaction} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transaction.
     */
    public Transaction toModelType() throws IllegalValueException {
        if (id == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TransactionId.class.getSimpleName())));
        }
        if (!TransactionId.isValidTransactionId(id)) {
            throw new IllegalValueException(TransactionId.MESSAGE_CONSTRAINTS);
        }
        final TransactionId modelId = new TransactionId(id);

        if (good == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Good.class.getSimpleName()));
        }
        if (!Good.isValidGood(good.toModelType())) {
            throw new IllegalValueException(Good.MESSAGE_CONSTRAINTS);
        }

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        if (type.equals(SellTransaction.class.getSimpleName())) {
            return new SellTransaction(new TransactionId(id), good.toModelType(), modelPrice);
        }

        if (person == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        if (!Person.isValidPerson(person.toModelType())) {
            throw new IllegalValueException(Person.MESSAGE_CONSTRAINTS);
        }

        return new BuyTransaction(modelId, good.toModelType(), person.toModelType(), modelPrice);
    }


}
