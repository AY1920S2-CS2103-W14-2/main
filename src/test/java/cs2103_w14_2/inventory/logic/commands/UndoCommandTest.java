package cs2103_w14_2.inventory.logic.commands;

import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.TypicalGoods;
import cs2103_w14_2.inventory.testutil.TypicalSuppliers;
import org.junit.jupiter.api.Test;

public class UndoCommandTest {
    private Model model = new ModelManager();

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new UndoCommand().execute(null));
    }

    @Test
    public void execute_changesCommitted_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getInventory(),
                model.getTransactionHistory(), model.getUserPrefs());
        model.addGood(TypicalGoods.APPLE);
        model.addSupplier(TypicalSuppliers.ALICE);
        model.commit();

        CommandTestUtil.assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noChangesCommitted_throwsCommandException() {
        CommandTestUtil.assertCommandFailure(new UndoCommand(), model, Messages.MESSAGE_UNDO_AT_INITIAL_STATE);
    }
}
