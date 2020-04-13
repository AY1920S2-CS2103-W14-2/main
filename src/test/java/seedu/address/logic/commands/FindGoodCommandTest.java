package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_SUPPLIERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGoods.getTypicalInventory;
import static seedu.address.testutil.TypicalSuppliers.ALICE;
import static seedu.address.testutil.TypicalSuppliers.BENSON;
import static seedu.address.testutil.TypicalSuppliers.DANIEL;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTransactions.getTypicalTransactionHistory;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.good.GoodSupplierPairContainsKeywordsPredicate;

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
        String expectedMessage = String.format(MESSAGE_SUPPLIERS_LISTED_OVERVIEW, 0);
        GoodSupplierPairContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindGoodCommand command = new FindGoodCommand(predicate);
        expectedModel.updateFilteredSupplierList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSupplierList());
    }

    @Test
    public void execute_multipleKeywords_multipleSuppliersFound() {
        String expectedMessage = String.format(MESSAGE_SUPPLIERS_LISTED_OVERVIEW, 3);
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
