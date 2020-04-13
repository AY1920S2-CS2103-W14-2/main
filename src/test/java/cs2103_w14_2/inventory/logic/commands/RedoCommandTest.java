package cs2103_w14_2.inventory.logic.commands;

import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.APPLE;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.ALICE;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import org.junit.jupiter.api.Test;

public class RedoCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RedoCommand().execute(null));
    }

    @Test
    public void execute_changesCommitted_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getInventory(),
                model.getTransactionHistory(), model.getUserPrefs());
        expectedModel.addGood(APPLE);
        expectedModel.addSupplier(ALICE);

        model.addGood(APPLE);
        model.addSupplier(ALICE);
        model.commit();
        model.undo();

        CommandTestUtil.assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noChangesUndone_throwsCommandException() {
        CommandTestUtil.assertCommandFailure(new RedoCommand(), model, Messages.MESSAGE_REDO_AT_LATEST_STATE);

        // fails also after committing
        model.commit();
        CommandTestUtil.assertCommandFailure(new RedoCommand(), model, Messages.MESSAGE_REDO_AT_LATEST_STATE);
    }
}
