package seedu.address.testutil;

import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.UUID;

import seedu.address.model.good.Good;
import seedu.address.model.offer.Price;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.BuyTransaction;

/**
 * A utility class to help with building BuyTransaction objects.
 */
public class BuyTransactionBuilder {

    public static final UUID UNIQUE_BUY_ID = UUID.randomUUID();
    private static final String VALID_PRICE_TWO_DECIMAL_PLACES = "6.58";

    // id good buyPrice person

    private UUID id;
    private Good good;
    private Person person;
    private Price buyPrice;

    public BuyTransactionBuilder() {
        id = UNIQUE_BUY_ID;
        good = APPLE;
        person = ALICE;
        buyPrice = new Price(VALID_PRICE_TWO_DECIMAL_PLACES);
    }

    /**
     * Initializes the TransactionBuilder with the data of {@code buyTransactionToCopy}.
     */
    public BuyTransactionBuilder(BuyTransaction buyTransactionToCopy) {
        id = buyTransactionToCopy.getId();
        good = buyTransactionToCopy.getGood();
        person = buyTransactionToCopy.getPerson();
        buyPrice = buyTransactionToCopy.getBuyPrice();
    }

    /**
     * Sets the {@code Id} of the {@code BuyTransaction} that we are building.
     */
    public BuyTransactionBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the {@code Good} of the {@code BuyTransaction} that we are building.
     */
    public BuyTransactionBuilder withGood(Good good) {
        this.good = good;
        return this;
    }

    /**
     * Sets the {@code Person} of the {@code BuyTransaction} that we are building.
     */
    public BuyTransactionBuilder withPerson(Person person) {
        this.person = person;
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code BuyTransaction} that we are building.
     */
    public BuyTransactionBuilder withPrice(String buyPrice) {
        this.buyPrice = new Price(buyPrice);
        return this;
    }

    public BuyTransaction build() {
        return new BuyTransaction(id, good, person, buyPrice);
    }
}
