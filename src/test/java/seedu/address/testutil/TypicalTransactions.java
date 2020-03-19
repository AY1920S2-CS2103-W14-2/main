package seedu.address.testutil;

import static seedu.address.testutil.BuyTransactionBuilder.UNIQUE_BUY_ID;
import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalGoods.BANANA;
import static seedu.address.testutil.TypicalGoods.CITRUS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import seedu.address.model.transaction.BuyTransaction;
import seedu.address.model.transaction.SellTransaction;

/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {

    public static final BuyTransaction BUY_APPLE_TRANSACTION = new BuyTransactionBuilder()
        .withId(UNIQUE_BUY_ID).withGood(APPLE).withPerson(ALICE).withPrice("5.20").build();
    public static final BuyTransaction BUY_BANANA_TRANSACTION = new BuyTransactionBuilder()
        .withId(UNIQUE_BUY_ID).withGood(BANANA).withPerson(BENSON).withPrice("55.20").build();
    public static final BuyTransaction BUY_CITRUS_TRANSACTION = new BuyTransactionBuilder()
        .withId(UNIQUE_BUY_ID).withGood(CITRUS).withPerson(CARL).withPrice("15.20").build();

    public static final SellTransaction SELL_APPLE_TRANSACTION = new SellTransactionBuilder()
        .withId(UNIQUE_BUY_ID).withGood(APPLE).withPrice("5.20").build();
    public static final SellTransaction SELL_BANANA_TRANSACTION = new SellTransactionBuilder()
        .withId(UNIQUE_BUY_ID).withGood(BANANA).withPrice("55.20").build();
    public static final SellTransaction SELL_CITRUS_TRANSACTION = new SellTransactionBuilder()
        .withId(UNIQUE_BUY_ID).withGood(CITRUS).withPrice("15.20").build();

    private TypicalTransactions() {
    } // prevents instantiation


}
