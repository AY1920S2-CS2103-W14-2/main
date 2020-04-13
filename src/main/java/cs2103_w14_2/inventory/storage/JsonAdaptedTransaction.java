package cs2103_w14_2.inventory.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import cs2103_w14_2.inventory.commons.exceptions.IllegalValueException;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.transaction.BuyTransaction;
import cs2103_w14_2.inventory.model.transaction.SellTransaction;
import cs2103_w14_2.inventory.model.transaction.Transaction;
import cs2103_w14_2.inventory.model.transaction.TransactionId;
import cs2103_w14_2.inventory.model.good.Good;
import cs2103_w14_2.inventory.model.offer.Price;

/**
 * Jackson-friendly version of {@link cs2103_w14_2.inventory.model.transaction.Transaction}.
 */
public class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String type;
    private final String id;
    private final JsonAdaptedGood good;
    private final String price;
    private final JsonAdaptedSupplier supplier;

    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("type") String type, @JsonProperty("id") String id,
                                  @JsonProperty("good") JsonAdaptedGood good, @JsonProperty("price") String price,
                                  @JsonProperty("supplier") JsonAdaptedSupplier supplier) {
        this.type = type;
        this.id = id;
        this.good = good;
        this.price = price;
        this.supplier = supplier;
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
            supplier = new JsonAdaptedSupplier(buyTransaction.getSupplier());
            price = buyTransaction.getBuyPrice().getValue();
        } else {
            type = SellTransaction.class.getSimpleName();
            SellTransaction sellTransaction = (SellTransaction) source;
            price = sellTransaction.getSellPrice().getValue();
            supplier = null;
        }
    }

    /**
     * Converts this Jackson-friendly adapted transaction object into the model's {@code Transaction} object.
     *
     * @throws cs2103_w14_2.inventory.commons.exceptions.IllegalValueException if there were any data constraints violated in the adapted transaction.
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

        if (supplier == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Supplier.class.getSimpleName()));
        }
        if (!Supplier.isValidSupplier(supplier.toModelType())) {
            throw new IllegalValueException(Supplier.MESSAGE_CONSTRAINTS);
        }

        return new BuyTransaction(modelId, good.toModelType(), supplier.toModelType(), modelPrice);
    }


}
