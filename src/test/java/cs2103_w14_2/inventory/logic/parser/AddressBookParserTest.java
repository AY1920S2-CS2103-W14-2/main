package cs2103_w14_2.inventory.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;
import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.logic.commands.ClearSupplierCommand;
import cs2103_w14_2.inventory.logic.commands.DeleteSupplierCommand;
import cs2103_w14_2.inventory.logic.commands.EditSupplierCommand;
import cs2103_w14_2.inventory.logic.commands.ExitCommand;
import cs2103_w14_2.inventory.logic.commands.FindSupplierCommand;
import cs2103_w14_2.inventory.logic.commands.HelpCommand;
import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.logic.commands.AddSupplierCommand;
import cs2103_w14_2.inventory.logic.commands.ListSupplierCommand;
import cs2103_w14_2.inventory.logic.commands.RedoCommand;
import cs2103_w14_2.inventory.logic.commands.UndoCommand;
import cs2103_w14_2.inventory.model.supplier.SupplierNameContainsKeywordsPredicate;
import cs2103_w14_2.inventory.testutil.EditSupplierDescriptorBuilder;
import cs2103_w14_2.inventory.testutil.SupplierBuilder;
import cs2103_w14_2.inventory.testutil.SupplierUtil;

public class AddressBookParserTest {

    private final InventoryManagerParser parser = new InventoryManagerParser();

    @Test
    public void parseCommand_add() throws Exception {
        Supplier supplier = new SupplierBuilder().build();
        AddSupplierCommand command = (AddSupplierCommand) parser.parseCommand(SupplierUtil.getAddCommand(supplier));
        assertEquals(new AddSupplierCommand(supplier), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearSupplierCommand.COMMAND_WORD) instanceof ClearSupplierCommand);
        assertTrue(parser.parseCommand(ClearSupplierCommand.COMMAND_WORD + " 3") instanceof ClearSupplierCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteSupplierCommand command = (DeleteSupplierCommand) parser.parseCommand(
                DeleteSupplierCommand.COMMAND_WORD + " " + INDEX_FIRST_SUPPLIER.getOneBased());
        assertEquals(new DeleteSupplierCommand(INDEX_FIRST_SUPPLIER), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Supplier supplier = new SupplierBuilder().build();
        EditSupplierCommand.EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder(supplier).build();
        EditSupplierCommand command = (EditSupplierCommand) parser.parseCommand(EditSupplierCommand.COMMAND_WORD + " "
                + INDEX_FIRST_SUPPLIER.getOneBased() + " " + SupplierUtil.getEditSupplierDescriptorDetails(descriptor));
        assertEquals(new EditSupplierCommand(INDEX_FIRST_SUPPLIER, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindSupplierCommand command = (FindSupplierCommand) parser.parseCommand(
                FindSupplierCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindSupplierCommand(new SupplierNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListSupplierCommand.COMMAND_WORD) instanceof ListSupplierCommand);
        assertTrue(parser.parseCommand(ListSupplierCommand.COMMAND_WORD + " 3") instanceof ListSupplierCommand);
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 3") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, Messages.MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
