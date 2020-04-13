package cs2103_w14_2.inventory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.getTypicalInventory;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.ALICE;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.BENSON;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.DANIEL;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.getTypicalAddressBook;
import static cs2103_w14_2.inventory.testutil.TypicalTransactions.getTypicalTransactionHistory;

import java.util.Arrays;
import java.util.Collections;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import cs2103_w14_2.inventory.model.UserPrefs;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.model.good.GoodSupplierPairContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindGoodCommand}.
 */
public class FindGoodCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
            getTypicalTransactionHistory(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
            getTypicalTransactionHistory(), new UserPrefs());

    @Test
    public void equals() {
        GoodSupplierPairContainsKeywordsPredicate firstPredicate =
                new GoodSupplierPairContainsKeywordsPredicate(Collections.singletonList("first"));
        GoodSupplierPairContainsKeywordsPredicate secondPredicate =
                new GoodSupplierPairContainsKeywordsPredicate(Collections.singletonList("second"));

        FindGoodCommand findFirstCommand = new FindGoodCommand(firstPredicate);
        FindGoodCommand findSecondCommand = new FindGoodCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindGoodCommand findFirstCommandCopy = new FindGoodCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different good -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noGoodFound() {
        String expectedMessage = String.format(Messages.MESSAGE_SUPPLIERS_LISTED_OVERVIEW, 0);
        GoodSupplierPairContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindGoodCommand command = new FindGoodCommand(predicate);
        expectedModel.updateFilteredSupplierList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSupplierList());
    }

    @Test
    public void execute_multipleKeywords_multipleSuppliersFound() {
        String expectedMessage = String.format(Messages.MESSAGE_SUPPLIERS_LISTED_OVERVIEW, 3);
        GoodSupplierPairContainsKeywordsPredicate predicate = preparePredicate(
                ALICE.getOffers().iterator().next().getGoodName().toString() + " "
                + BENSON.getOffers().iterator().next().getGoodName().toString() + " "
                + DANIEL.getOffers().iterator().next().getGoodName().toString());

        FindGoodCommand command = new FindGoodCommand(predicate);
        expectedModel.updateFilteredSupplierList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredSupplierList());
    }

    @Test
    public void execute_doesNotCallModelCommit() throws CommandException {
        ModelStubCommit modelStub = new ModelStubCommit();
        new FindGoodCommand(preparePredicate("commit")).execute(modelStub);

        assertFalse(modelStub.isCommitted());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private GoodSupplierPairContainsKeywordsPredicate preparePredicate(String userInput) {
        return new GoodSupplierPairContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
