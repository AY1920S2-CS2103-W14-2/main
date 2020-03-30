package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Inventory;
import seedu.address.model.Model;
import seedu.address.model.TransactionHistory;
import seedu.address.model.VersionedAddressBook;
import seedu.address.model.VersionedInventory;
import seedu.address.model.VersionedTransactionHistory;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodNameContainsKeywordsPredicate;
import seedu.address.model.supplier.NameContainsKeywordsPredicate;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.EditSupplierDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_GOOD_APPLE = "apple";
    public static final String VALID_GOOD_BANANA = "banana";
    public static final String VALID_PRICE_CHEAP = "0.50";
    public static final String VALID_PRICE_EXPENSIVE = "5.00";
    public static final String VALID_OFFER_APPLE = VALID_GOOD_APPLE + " " + VALID_PRICE_CHEAP;
    public static final String VALID_OFFER_BANANA = VALID_GOOD_BANANA + " " + VALID_PRICE_EXPENSIVE;


    public static final String VALID_GOOD_NAME_AVOCADO = "Mexican avocado";
    public static final String VALID_GOOD_NAME_BLUEBERRY = "USA blueberry";
    public static final int VALID_GOOD_QUANTITY_ZERO = 0;
    public static final int VALID_GOOD_QUANTITY_ONE = 1;

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String OFFER_DESC_APPLE = " " + PREFIX_OFFER + VALID_OFFER_APPLE;
    public static final String OFFER_DESC_BANANA = " " + PREFIX_OFFER + VALID_OFFER_BANANA;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses

    // no space separator
    public static final String INVALID_FORMAT_OFFER_DESC = " " + PREFIX_OFFER + "invalid3.45";
    //invalid good name
    public static final String INVALID_GOOD_OFFER_DESC = " " + PREFIX_OFFER + "inv@lid 404";
    //invalid good name and price
    public static final String INVALID_GOOD_AND_PRICE_OFFER_DESC = " " + PREFIX_OFFER + "inv@lid -404";
    //invalid good name and format
    public static final String INVALID_GOOD_AND_FORMAT_OFFER_DESC = " " + PREFIX_OFFER + "inv@lid404";
    // invalid price
    public static final String INVALID_PRICE_OFFER_DESC = " " + PREFIX_OFFER + "invalid -1.0";
    // invalid price and format
    public static final String INVALID_PRICE_AND_FORMAT_OFFER_DESC = " " + PREFIX_OFFER + "invalid-1.0";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditSupplierCommand.EditSupplierDescriptor DESC_AMY;
    public static final EditSupplierCommand.EditSupplierDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditSupplierDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withOffers(VALID_OFFER_APPLE).build();
        DESC_BOB = new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withOffers(VALID_OFFER_APPLE, VALID_OFFER_BANANA).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
            CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered supplier list and selected supplier in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new VersionedAddressBook(actualModel.getAddressBook());
        List<Supplier> expectedFilteredList = new ArrayList<>(actualModel.getFilteredSupplierList());
        Inventory expectedInventory = new VersionedInventory(actualModel.getInventory());
        List<Good> expectedGoodFilteredList = new ArrayList<>(actualModel.getFilteredGoodList());
        TransactionHistory expectedTransactionHistory =
                new VersionedTransactionHistory(actualModel.getTransactionHistory());
        List<Transaction> expectedTransactionFilteredList = new ArrayList<>(actualModel.getFilteredTransactionList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedInventory, actualModel.getInventory());
        assertEquals(expectedTransactionHistory, actualModel.getTransactionHistory());
        assertEquals(expectedFilteredList, actualModel.getFilteredSupplierList());
        assertEquals(expectedGoodFilteredList, actualModel.getFilteredGoodList());
        assertEquals(expectedTransactionFilteredList, actualModel.getFilteredTransactionList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the supplier at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showSupplierAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredSupplierList().size());

        Supplier supplier = model.getFilteredSupplierList().get(targetIndex.getZeroBased());
        final String[] splitName = supplier.getName().fullName.split("\\s+");
        model.updateFilteredSupplierList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredSupplierList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the good at the given {@code targetIndex} in the
     * {@code model}'s inventory.
     */
    public static void showGoodAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredGoodList().size());

        Good good = model.getFilteredGoodList().get(targetIndex.getZeroBased());
        final String[] splitName = good.getGoodName().fullGoodName.split("\\s+");
        model.updateFilteredGoodList(new GoodNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredGoodList().size());
    }

}
