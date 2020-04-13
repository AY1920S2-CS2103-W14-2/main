package cs2103_w14_2.inventory.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import cs2103_w14_2.inventory.commons.core.GuiSettings;
import cs2103_w14_2.inventory.commons.core.LogsCenter;
import cs2103_w14_2.inventory.logic.commands.Command;
import cs2103_w14_2.inventory.logic.commands.CommandResult;
import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.logic.parser.InventoryManagerParser;
import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ReadOnlyList;
import cs2103_w14_2.inventory.model.good.Good;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.transaction.Transaction;
import cs2103_w14_2.inventory.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final InventoryManagerParser inventoryManagerParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        inventoryManagerParser = new InventoryManagerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = inventoryManagerParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        try {
            storage.saveInventory(model.getInventory());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        try {
            storage.saveTransactionHistory(model.getTransactionHistory());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyList<Supplier> getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Supplier> getFilteredSupplierList() {
        return model.getFilteredSupplierList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public ReadOnlyList<Good> getInventory() {
        return model.getInventory();
    }

    @Override
    public ObservableList<Good> getFilteredGoodList() {
        return model.getFilteredGoodList();
    }

    @Override
    public Path getInventoryFilePath() {
        return model.getInventoryFilePath();
    }

    @Override
    public ReadOnlyList<Transaction> getTransactionHistory() {
        return model.getTransactionHistory();
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return model.getFilteredTransactionList();
    }

    @Override
    public Path getTransactionHistoryFilePath() {
        return model.getTransactionHistoryFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
