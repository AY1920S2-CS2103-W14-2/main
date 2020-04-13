package cs2103_w14_2.inventory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.commons.core.Messages.MESSAGE_SUPPLIERS_LISTED_OVERVIEW;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.getTypicalInventory;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.CARL;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.ELLE;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.FIONA;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.getTypicalAddressBook;
import static cs2103_w14_2.inventory.testutil.TypicalTransactions.getTypicalTransactionHistory;

import java.util.Arrays;
import java.util.Collections;

import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import cs2103_w14_2.inventory.model.UserPrefs;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.model.supplier.SupplierNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindSupplierCommand}.
 */
public class FindSupplierCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
            getTypicalTransactionHistory(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
            getTypicalTransactionHistory(), new UserPrefs());

    @Test
    public void equals() {
        SupplierNameContainsKeywordsPredicate firstPredicate =
                new SupplierNameContainsKeywordsPredicate(Collections.singletonList("first"));
        SupplierNameContainsKeywordsPredicate secondPredicate =
                new SupplierNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindSupplierCommand findFirstCommand = new FindSupplierCommand(firstPredicate);
        FindSupplierCommand findSecondCommand = new FindSupplierCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindSupplierCommand findFirstCommandCopy = new FindSupplierCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different supplier -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noSupplierFound() {
        String expectedMessage = String.format(MESSAGE_SUPPLIERS_LISTED_OVERVIEW, 0);
        SupplierNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindSupplierCommand command = new FindSupplierCommand(predicate);
        expectedModel.updateFilteredSupplierList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSupplierList());
    }

    @Test
    public void execute_multipleKeywords_multipleSuppliersFound() {
        String expectedMessage = String.format(MESSAGE_SUPPLIERS_LISTED_OVERVIEW, 3);
        SupplierNameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindSupplierCommand command = new FindSupplierCommand(predicate);
        expectedModel.updateFilteredSupplierList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredSupplierList());
    }

    @Test
    public void execute_doesNotCallModelCommit() throws CommandException {
        ModelStubCommit modelStub = new ModelStubCommit();
        new FindSupplierCommand(preparePredicate("commit")).execute(modelStub);

        assertFalse(modelStub.isCommitted());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private SupplierNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new SupplierNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
