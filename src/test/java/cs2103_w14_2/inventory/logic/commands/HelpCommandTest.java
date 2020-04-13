package cs2103_w14_2.inventory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;

import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import org.junit.jupiter.api.Test;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(HelpCommand.SHOWING_HELP_MESSAGE, true, false);
        CommandTestUtil.assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_doesNotCallModelCommit() throws CommandException {
        ModelStubCommit modelStub = new ModelStubCommit();
        new HelpCommand().execute(modelStub);

        assertFalse(modelStub.isCommitted());
    }
}
