package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.TransactionHistory;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;
import seedu.address.model.offer.Offer;
import seedu.address.model.offer.Price;
import seedu.address.model.supplier.Address;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.transaction.BuyTransaction;
import seedu.address.model.transaction.SellTransaction;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionId;

/**
 * Contains utility methods for populating {@code AddressBook}, {@code Inventory}
 * and {@code TransactionHistory} with sample data.
 */
public class SampleDataUtil {

    //=========== Supplier ==================================================================================

    private static final Supplier ALEX = new Supplier(new Name("Alex Yeoh"), new Phone("87438807"),
            new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
            getOfferSet("banana 4.5"));

    private static final Supplier BERNICE = new Supplier(new Name("Bernice Yu"), new Phone("99272758"),
            new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
            getOfferSet("toilet paper 2", "tissue 70.50"));

    private static final Supplier CHARLOTTE = new Supplier(new Name("Charlotte Oliveiro"), new Phone("93210283"),
            new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
            getOfferSet("hand sanitiser 3.25"));

    private static final Supplier DAVID = new Supplier(new Name("David Li"), new Phone("91031282"),
            new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
            getOfferSet("instant noodle 0.45"));

    private static final Supplier IRFAN = new Supplier(new Name("Irfan Ibrahim"), new Phone("92492021"),
            new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
            getOfferSet("facial mask 5.75"));

    private static final Supplier ROY = new Supplier(new Name("Roy Balakrishnan"), new Phone("92624417"),
            new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
            getOfferSet("apple 50.3"));

    //=========== Good ==================================================================================

    private static final Good APPLE = new Good(new GoodName("Fuji apple"), new GoodQuantity(15));

    private static final Good BANANA = new Good(new GoodName("Cavendish banana"), new GoodQuantity(150));

    private static final Good TOILET_PAPER = new Good(new GoodName("Toilet paper"), new GoodQuantity(20));

    private static final Good TISSUE = new Good(new GoodName("Tissue"), new GoodQuantity(200));

    private static final Good HAND_SANITISER = new Good(new GoodName("Hand sanitiser"), new GoodQuantity(20));

    private static final Good INSTANT_NOODLE = new Good(new GoodName("Instant noodle"), new GoodQuantity(100));

    private static final Good FACIAL_MASK = new Good(new GoodName("Facial mask"), new GoodQuantity(100));

    //=========== Transaction ==================================================================================

    private static final BuyTransaction BUY_APPLE = new BuyTransaction(new TransactionId(UUID.randomUUID().toString()),
            APPLE, ROY, new Price("5.3"));

    private static final BuyTransaction BUY_TOILET_PAPER = new BuyTransaction(
            new TransactionId(UUID.randomUUID().toString()),
            TOILET_PAPER, BERNICE, new Price("7.50"));

    private static final BuyTransaction BUY_BANANA = new BuyTransaction(new TransactionId(UUID.randomUUID().toString()),
            BANANA, ALEX, new Price("4.50"));

    private static final BuyTransaction BUY_TISSUE = new BuyTransaction(new TransactionId(UUID.randomUUID().toString()),
            TISSUE, BERNICE, new Price("2"));

    private static final SellTransaction SELL_INSTANT_NOODLE = new SellTransaction(
            new TransactionId(UUID.randomUUID().toString()), INSTANT_NOODLE, new Price("60.30"));

    private static final SellTransaction SELL_FACIAL_MASK = new SellTransaction(
            new TransactionId(UUID.randomUUID().toString()), FACIAL_MASK, new Price("15.50"));

    private static final SellTransaction SELL_HAND_SANITISER = new SellTransaction(
            new TransactionId(UUID.randomUUID().toString()), HAND_SANITISER, new Price("10.50"));


    public static Supplier[] getSampleSuppliers() {
        return new Supplier[]{
            ALEX, BERNICE, CHARLOTTE, DAVID, IRFAN, ROY
        };
    }

    public static Good[] getSampleGoods() {
        return new Good[]{
            APPLE, BANANA, TOILET_PAPER, TISSUE, HAND_SANITISER, INSTANT_NOODLE, FACIAL_MASK
        };
    }

    public static Transaction[] getSampleTransactions() {
        return new Transaction[]{
            BUY_APPLE, SELL_INSTANT_NOODLE, SELL_HAND_SANITISER, BUY_TOILET_PAPER, SELL_FACIAL_MASK,
            BUY_BANANA, BUY_TISSUE
        };
    }

    public static ReadOnlyList<Supplier> getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Supplier sampleSupplier : getSampleSuppliers()) {
            sampleAb.addSupplier(sampleSupplier);
        }
        return sampleAb;
    }

    public static ReadOnlyList<Good> getSampleInventory() {
        Inventory sampleInventory = new Inventory();
        for (Good sampleGood : getSampleGoods()) {
            sampleInventory.addGood(sampleGood);
        }
        return sampleInventory;
    }

    public static ReadOnlyList<Transaction> getSampleTransactionHistory() {
        TransactionHistory sampleTransactionHistory = new TransactionHistory();
        for (Transaction sampleTransaction : getSampleTransactions()) {
            sampleTransactionHistory.addTransaction(sampleTransaction);
        }
        return sampleTransactionHistory;
    }

    /**
     * Returns an offer set containing the set of strings given.
     */
    public static Set<Offer> getOfferSet(String... strings) {
        return Arrays.stream(strings)
                .map(ParserUtil::splitOnLastWhitespace)
                .map(ParserUtil::getGoodPricePair)
                .map(x -> new Offer((GoodName) x[0], (Price) x[1]))
                .collect(Collectors.toSet());
    }
}
