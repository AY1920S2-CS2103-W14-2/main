package cs2103_w14_2.inventory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.getTypicalInventory;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.getTypicalAddressBook;
import static cs2103_w14_2.inventory.testutil.TypicalTransactions.getTypicalTransactionHistory;

import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import cs2103_w14_2.inventory.model.UserPrefs;
import cs2103_w14_2.inventory.testutil.TypicalIndexes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        CommandTestUtil.showTransactionAtIndex(model, TypicalIndexes.INDEX_FIRST_SUPPLIER);
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
