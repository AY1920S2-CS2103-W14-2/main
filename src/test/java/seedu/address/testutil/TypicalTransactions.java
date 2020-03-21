package seedu.address.testutil;

import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalGoods.BANANA;
import static seedu.address.testutil.TypicalGoods.CITRUS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import seedu.address.model.TransactionHistory;
import seedu.address.model.transaction.BuyTransaction;
import seedu.address.model.transaction.SellTransaction;
import seedu.address.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {

    public static final BuyTransaction BUY_APPLE_TRANSACTION = new BuyTransactionBuilder()
            .withId(UUID.randomUUID().toString()).withGood(APPLE).withPerson(ALICE).withPrice("5.20").build();
    public static final BuyTransaction BUY_BANANA_TRANSACTION = new BuyTransactionBuilder()
            .withId(UUID.randomUUID().toString()).withGood(BANANA).withPerson(BENSON).withPrice("55.20").build();
    public static final BuyTransaction BUY_CITRUS_TRANSACTION = new BuyTransactionBuilder()
            .withId(UUID.randomUUID().toString()).withGood(CITRUS).withPerson(CARL).withPrice("15.20").build();

    public static final SellTransaction SELL_APPLE_TRANSACTION = new SellTransactionBuilder()
            .withId(UUID.randomUUID().toString()).withGood(APPLE).withPrice("5.20").build();
    public static final SellTransaction SELL_BANANA_TRANSACTION = new SellTransactionBuilder()
            .withId(UUID.randomUUID().toString()).withGood(BANANA).withPrice("55.20").build();
    public static final SellTransaction SELL_CITRUS_TRANSACTION = new SellTransactionBuilder()
            .withId(UUID.randomUUID().toString()).withGood(CITRUS).withPrice("15.20").build();

    private TypicalTransactions() {
    } // prevents instantiation


    /**
     * Returns an {@code TransactionHistory} with all the typical transactions.
     */
    public static TransactionHistory getTypicalTransactionHistory() {
        TransactionHistory th = new TransactionHistory();
        for (Transaction person : getTypicalTransactions()) {
            th.addTransaction(person);
        }
        return th;
    }

    public static List<Transaction> getTypicalTransactions() {
        return new ArrayList<>(Arrays.asList(BUY_APPLE_TRANSACTION, BUY_BANANA_TRANSACTION,
                BUY_CITRUS_TRANSACTION, SELL_APPLE_TRANSACTION, SELL_BANANA_TRANSACTION,
                SELL_CITRUS_TRANSACTION));
    }

}
