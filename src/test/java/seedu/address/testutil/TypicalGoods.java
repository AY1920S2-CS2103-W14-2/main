package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_GOOD_NAME_AVOCADO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOOD_NAME_BLUEBERRY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Inventory;
import seedu.address.model.good.Good;

/**
 * A utility class containing a list of {@code Good} objects to be used in tests.
 */
public class TypicalGoods {

    public static final Good APPLE = new GoodBuilder().withGoodName("Fuji apple").withGoodQuantity(15).build();

    public static final Good BANANA = new GoodBuilder().withGoodName("Cavendish banana").withGoodQuantity(150).build();
    public static final Good CITRUS = new GoodBuilder().withGoodName("Brazil citrus").withGoodQuantity(20).build();
    public static final Good DURIAN = new GoodBuilder().withGoodName("Malaysia durian").withGoodQuantity(11).build();
    public static final Good ENTAWAK = new GoodBuilder().withGoodName("Indonesia entawak").withGoodQuantity(0).build();

    // Manually added
    public static final Good FIG = new GoodBuilder().withGoodName("India fig").withGoodQuantity(10).build();
    public static final Good GRAPE = new GoodBuilder().withGoodName("Spain grape").withGoodQuantity(10).build();

    // Manually added - Good's details found in {@code CommandTestUtil}
    public static final Good AVOCADO = new GoodBuilder().withGoodName(VALID_GOOD_NAME_AVOCADO).build();
    public static final Good BLUEBERRY = new GoodBuilder().withGoodName(VALID_GOOD_NAME_BLUEBERRY).build();

    /**
     * Returns an {@code Inventory} with all the typical goods.
     */
    public static Inventory getTypicalInventory() {
        Inventory inventory = new Inventory();
        for (Good supplier : getTypicalGoods()) {
            inventory.addGood(supplier);
        }
        return inventory;
    }

    public static List<Good> getTypicalGoods() {
        return new ArrayList<>(Arrays.asList(APPLE, BANANA, CITRUS, DURIAN, ENTAWAK));
    }
}
