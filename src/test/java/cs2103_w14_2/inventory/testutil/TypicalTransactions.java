package cs2103_w14_2.inventory.testutil;

import static cs2103_w14_2.inventory.testutil.TypicalGoods.APPLE;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.BANANA;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.CITRUS;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.DURIAN;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.ENTAWAK;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.ALICE;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.BENSON;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.CARL;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.DANIEL;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.ELLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs2103_w14_2.inventory.model.TransactionHistory;
import cs2103_w14_2.inventory.model.transaction.BuyTransaction;
import cs2103_w14_2.inventory.model.transaction.SellTransaction;
import cs2103_w14_2.inventory.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {

    public static final BuyTransaction BUY_APPLE_TRANSACTION = new BuyTransactionBuilder()
            .withId("977f63d6-a9b8-43b2-b515-a7ef73d56e92").withGood(APPLE)
            .withSupplier(ALICE).withPrice("5.20").build();
    public static final BuyTransaction BUY_BANANA_TRANSACTION = new BuyTransactionBuilder()
            .withId("433c1c49-b9e7-4fc0-ad28-228c25520662").withGood(BANANA)
            .withSupplier(BENSON).withPrice("55.20").build();
    public static final BuyTransaction BUY_CITRUS_TRANSACTION = new BuyTransactionBuilder()
            .withId("b74a40d0-2938-46b1-97f7-9e16ddd55663").withGood(CITRUS)
            .withSupplier(CARL).withPrice("15.20").build();

    public static final BuyTransaction BUY_DURIAN_TRANSACTION = new BuyTransactionBuilder()
            .withId("11516529-bda9-42b7-b142-ff4d1fc0b10e").withGood(DURIAN)
            .withSupplier(DANIEL).withPrice("50.20").build();
    public static final BuyTransaction BUY_ENTAWAK_TRANSACTION = new BuyTransactionBuilder()
            .withId("5a6445a6-97a2-4b83-95b8-bd576f76e3b3").withGood(ENTAWAK)
            .withSupplier(ELLE).withPrice("11.20").build();


    public static final SellTransaction SELL_APPLE_TRANSACTION = new SellTransactionBuilder()
            .withId("a708ae13-af41-40f1-9633-9b85cd5575ce").withGood(APPLE).withPrice("5.20").build();
    public static final SellTransaction SELL_BANANA_TRANSACTION = new SellTransactionBuilder()
            .withId("b367c308-f546-403d-8c8c-994d77cac4cc").withGood(BANANA).withPrice("55.20").build();
    public static final SellTransaction SELL_CITRUS_TRANSACTION = new SellTransactionBuilder()
            .withId("5560c20d-07df-4288-a6e5-edb05a9e6d8c").withGood(CITRUS).withPrice("15.20").build();



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
