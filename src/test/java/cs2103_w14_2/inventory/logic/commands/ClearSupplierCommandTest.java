package cs2103_w14_2.inventory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.getTypicalInventory;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.getTypicalAddressBook;
import static cs2103_w14_2.inventory.testutil.TypicalTransactions.getTypicalTransactionHistory;

import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.model.AddressBook;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import cs2103_w14_2.inventory.model.UserPrefs;

public class ClearSupplierCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearSupplierCommand(), model, ClearSupplierCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearSupplierCommand(), model, ClearSupplierCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_callsModelCommit() throws CommandException {
        ModelStubCommit modelStub = new ModelStubCommit();
        new ClearSupplierCommand().execute(modelStub);

        assertTrue(modelStub.isCommitted());
    }

}
