package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;
import seedu.address.model.supplier.Supplier;

public class SellCommandTest {

    private static Good soldGood = new Good(new GoodName("Testing good"),
            new GoodQuantity("10"));
    private static Good soldGoodDiffGoodName = new Good(new GoodName("Different testing good"),
            new GoodQuantity("10"));
    private static Good soldGoodDiffGoodQuantity = new Good(new GoodName("Testing good"),
            new GoodQuantity("99"));

    @Test
    public void constructor_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SellCommand(null));
    }

    @Test
    public void equals() {
        SellCommand SellCommand = new SellCommand(soldGood);
        SellCommand SellCommandDiffName = new SellCommand(soldGoodDiffGoodName);
        SellCommand SellCommandDiffQty = new SellCommand(soldGoodDiffGoodQuantity);

        // same object -> returns true
        assertTrue(SellCommand.equals(SellCommand));

        // same values -> returns true
        SellCommand SellCommandCopy = new SellCommand(soldGood);
        assertTrue(SellCommand.equals(SellCommandCopy));

        // different types -> returns false
        assertFalse(SellCommand.equals(1));
        assertFalse(SellCommand.equals("string"));

        // null -> returns false
        assertFalse(SellCommand.equals(null));

        // different GoodQuantity -> returns false
        assertFalse(SellCommand.equals(SellCommandDiffQty));

        // different GoodName -> returns false
        assertFalse(SellCommand.equals(SellCommandDiffName));
    }
}

