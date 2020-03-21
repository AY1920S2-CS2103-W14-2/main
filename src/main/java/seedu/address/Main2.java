package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import java.util.UUID;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;
import seedu.address.model.offer.Offer;
import seedu.address.model.offer.Price;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.transaction.BuyTransaction;
import seedu.address.model.transaction.SellTransaction;
import seedu.address.model.transaction.TransactionId;
import seedu.address.storage.JsonAdaptedTransaction;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class Main2 {

    /**
     * A ui for the status bar that is displayed at the header of the application.
     */
    public static void main(String[] args) throws IOException, DataConversionException, IllegalValueException {
        Offer offer = new Offer(new GoodName("apple"),
                new Price("1.50"));
        Set<Offer> offers = new HashSet<>();
        offers.add(offer);
        Good g = new Good(new GoodName("apple"),
                new GoodQuantity(20));
        Person p = new Person(new Name("james"),
                new Phone("920341"),
                new Email("dfs@sdg.com"),
                new Address("sggwewf"),
                offers);
        TransactionId id = new TransactionId(UUID.randomUUID().toString());
        BuyTransaction buy = new BuyTransaction(id, g, p, new Price("1.50"));
        JsonAdaptedTransaction jsonAdaptedTransaction = new JsonAdaptedTransaction(buy);
        Path path = Path.of("abc");
        JsonUtil.saveJsonFile(jsonAdaptedTransaction, path);
        SellTransaction sell = new SellTransaction(id, g, new Price("1.50"));
        JsonAdaptedTransaction jsonAdaptedTransaction2 = new JsonAdaptedTransaction(sell);
        JsonUtil.saveJsonFile(jsonAdaptedTransaction2, path);

        Optional<JsonAdaptedTransaction> j1 = JsonUtil.readJsonFile(path, JsonAdaptedTransaction.class);
        System.out.println(j1.get().toModelType());
    }
}
