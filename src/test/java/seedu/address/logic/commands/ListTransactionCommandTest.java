package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTransactionAtIndex;
import static seedu.address.testutil.TypicalGoods.getTypicalInventory;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTransactions.getTypicalTransactionHistory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTransactionCommand.
 */
public class ListTransactionCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getInventory(),
                model.getTransactionHistory(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListTransactionCommand(),
                model, ListTransactionCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTransactionAtIndex(model, INDEX_FIRST_SUPPLIER);
        assertCommandSuccess(new ListTransactionCommand(), model,
                ListTransactionCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_doesNotCallModelCommit() throws CommandException {
        ModelStubCommit modelStub = new ModelStubCommit();
        new ListTransactionCommand().execute(modelStub);

        assertFalse(modelStub.isCommitted());
    }
}
