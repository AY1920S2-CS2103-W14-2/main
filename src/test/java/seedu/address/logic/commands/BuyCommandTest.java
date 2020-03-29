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


        // same object -> returns true


        // same values -> returns true

        // different types -> returns false

        // null -> returns false

        // different supplier -> returns false
    }
}