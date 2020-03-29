package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;

public class BuyCommandTest {

    @Test
    public void constructor_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BuyCommand(null));
    }

    @Test
    void equals() {
        Good boughtGood = new Good(new GoodName("Testing good"),
                new GoodQuantity("10"));
        Good boughtGoodDiffGoodName = new Good(new GoodName("Different testing good"),
                new GoodQuantity("10"));
        Good boughtGoodDiffGoodQuantity = new Good(new GoodName("Testing good"),
                new GoodQuantity("99"));

        BuyCommand buyCommand = new BuyCommand(boughtGood);
        BuyCommand buyCommandDiffName = new BuyCommand(boughtGoodDiffGoodName);
        BuyCommand buyCommandDiffQty = new BuyCommand(boughtGoodDiffGoodQuantity);

        // same object -> returns true
        assertTrue(buyCommand.equals(buyCommand));

        // same values -> returns true
        BuyCommand buyCommandCopy = new BuyCommand(boughtGood);
        assertTrue(buyCommand.equals(buyCommandCopy));

        // different types -> returns false
        assertFalse(buyCommand.equals(1));
        assertFalse(buyCommand.equals("string"));

        // null -> returns false
        assertFalse(buyCommand.equals(null));

        // different GoodQuantity -> returns false
        assertFalse(buyCommand.equals(buyCommandDiffQty));

        // different GoodName -> returns false
        assertFalse(buyCommand.equals(buyCommandDiffName));

    }
}