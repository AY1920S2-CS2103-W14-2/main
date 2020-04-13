package cs2103_w14_2.inventory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.showSupplierAtIndex;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.getTypicalInventory;
import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.getTypicalAddressBook;
import static cs2103_w14_2.inventory.testutil.TypicalTransactions.getTypicalTransactionHistory;

import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import cs2103_w14_2.inventory.model.UserPrefs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListSupplierCommand.
 */
public class ListSupplierCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getInventory(),
                getTypicalTransactionHistory(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListSupplierCommand(), model, ListSupplierCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);
        assertCommandSuccess(new ListSupplierCommand(), model, ListSupplierCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_doesNotCallModelCommit() throws CommandException {
        ModelStubCommit modelStub = new ModelStubCommit();
        new ListSupplierCommand().execute(modelStub);

        assertFalse(modelStub.isCommitted());
    }
}
