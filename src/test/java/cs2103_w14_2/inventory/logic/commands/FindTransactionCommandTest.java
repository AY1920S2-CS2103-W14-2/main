package cs2103_w14_2.inventory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.getTypicalInventory;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.getTypicalAddressBook;
import static cs2103_w14_2.inventory.testutil.TypicalTransactions.BUY_APPLE_TRANSACTION;
import static cs2103_w14_2.inventory.testutil.TypicalTransactions.BUY_BANANA_TRANSACTION;
import static cs2103_w14_2.inventory.testutil.TypicalTransactions.BUY_CITRUS_TRANSACTION;
import static cs2103_w14_2.inventory.testutil.TypicalTransactions.getTypicalTransactionHistory;

import java.util.Arrays;
import java.util.Collections;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.logic.parser.FindTransactionCommandParser;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import cs2103_w14_2.inventory.model.UserPrefs;
import cs2103_w14_2.inventory.model.transaction.TransactionContainKeywordsPredicate;
import org.junit.jupiter.api.Test;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTransactionCommand}.
 */
public class FindTransactionCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
            getTypicalTransactionHistory(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
            getTypicalTransactionHistory(), new UserPrefs());

    @Test
    public void equals() {
        TransactionContainKeywordsPredicate firstPredicate =
                new TransactionContainKeywordsPredicate(FindTransactionCommandParser.TransactionType.BUY_TRANSACTION,
                        Collections.singletonList("Alice"), Collections.singletonList("Apple"));
        TransactionContainKeywordsPredicate secondPredicate =
                new TransactionContainKeywordsPredicate(FindTransactionCommandParser.TransactionType.SELL_TRANSACTION,
                        Collections.singletonList("Alice"), Collections.singletonList("Apple"));
        TransactionContainKeywordsPredicate thirdPredicate =
                new TransactionContainKeywordsPredicate(FindTransactionCommandParser.TransactionType.BUY_TRANSACTION,
                        Arrays.asList("Alice", "Bob"), Collections.singletonList("Apple"));
        TransactionContainKeywordsPredicate fourthPredicate =
                new TransactionContainKeywordsPredicate(FindTransactionCommandParser.TransactionType.BUY_TRANSACTION,
                        Collections.singletonList("Alice"), Arrays.asList("Apple", "Banana"));

        FindTransactionCommand findFirstCommand = new FindTransactionCommand(firstPredicate);
        FindTransactionCommand findSecondCommand = new FindTransactionCommand(secondPredicate);
        FindTransactionCommand findThirdCommand = new FindTransactionCommand(thirdPredicate);
        FindTransactionCommand findFourthCommand = new FindTransactionCommand(fourthPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindTransactionCommand findFirstCommandCopy = new FindTransactionCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different type of transaction -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);

        // different supplier name list -> returns false
        assertNotEquals(findFirstCommand, findThirdCommand);

        // different good name list -> returns false
        assertNotEquals(findFirstCommand, findFourthCommand);
    }

    @Test
    public void execute_zeroKeywords_noTransactionFound() {
        String expectedMessage = String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 3);
        // user input of " " will not be taken as a keyword.
        TransactionContainKeywordsPredicate predicate = prepareSupplierPredicate(" ");
        FindTransactionCommand command = new FindTransactionCommand(predicate);
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredTransactionList(), model.getFilteredTransactionList());
    }

    @Test
    public void execute_multipleSupplierKeywords_multipleTransactionsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 3);
        TransactionContainKeywordsPredicate predicate = prepareSupplierPredicate("Alice Benson Carl");
        FindTransactionCommand command = new FindTransactionCommand(predicate);
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BUY_APPLE_TRANSACTION, BUY_BANANA_TRANSACTION, BUY_CITRUS_TRANSACTION),
                model.getFilteredTransactionList());
    }

    @Test
    public void execute_multipleGoodKeywords_multipleTransactionsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 3);
        TransactionContainKeywordsPredicate predicate = prepareGoodPredicate("Apple Banana Citrus");
        FindTransactionCommand command = new FindTransactionCommand(predicate);
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BUY_APPLE_TRANSACTION, BUY_BANANA_TRANSACTION, BUY_CITRUS_TRANSACTION),
                model.getFilteredTransactionList());
    }

    @Test
    public void execute_doesNotCallModelCommit() {
        ModelStubCommit modelStub = new ModelStubCommit();
        new FindTransactionCommand(prepareSupplierPredicate("commit")).execute(modelStub);

        assertFalse(modelStub.isCommitted());
    }

    /**
     * Parses supplier {@code userInput} into a {@code TransactionContainKeywordsPredicate}.
     */
    private TransactionContainKeywordsPredicate prepareSupplierPredicate(String userInput) {
        return new TransactionContainKeywordsPredicate(FindTransactionCommandParser.TransactionType.BUY_TRANSACTION,
                Arrays.asList(userInput.split("\\s+")), Collections.emptyList());
    }

    /**
     * Parses good {@code userInput} into a {@code TransactionContainKeywordsPredicate}.
     */
    private TransactionContainKeywordsPredicate prepareGoodPredicate(String userInput) {
        return new TransactionContainKeywordsPredicate(FindTransactionCommandParser.TransactionType.BUY_TRANSACTION,
                Collections.emptyList(), Arrays.asList(userInput.split("\\s+")));
    }
}
