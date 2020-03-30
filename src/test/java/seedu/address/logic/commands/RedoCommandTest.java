package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalSuppliers.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

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

        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noChangesUndone_throwsCommandException() {
        assertCommandFailure(new RedoCommand(), model, Messages.MESSAGE_REDO_AT_LATEST_STATE);

        // fails also after committing
        model.commit();
        assertCommandFailure(new RedoCommand(), model, Messages.MESSAGE_REDO_AT_LATEST_STATE);
    }
}
