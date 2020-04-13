package cs2103_w14_2.inventory.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import cs2103_w14_2.inventory.commons.core.GuiSettings;
import cs2103_w14_2.inventory.logic.commands.CommandResult;
import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;
import cs2103_w14_2.inventory.model.ReadOnlyList;
import cs2103_w14_2.inventory.model.good.Good;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.transaction.Transaction;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see cs2103_w14_2.inventory.model.Model#getAddressBook()
     */
    ReadOnlyList<Supplier> getAddressBook();

    /**
     * Returns the Inventory.
     *
     * @see cs2103_w14_2.inventory.model.Model#getInventory()
     */
    ReadOnlyList<Good> getInventory();

    /** Returns an unmodifiable view of the filtered list of suppliers */
    ObservableList<Supplier> getFilteredSupplierList();
    /**
     * Returns the TransactionHistory.
     *
     * @see cs2103_w14_2.inventory.model.Model#getTransactionHistory()
     */
    ReadOnlyList<Transaction> getTransactionHistory();

    /** Returns an unmodifiable view of the filtered list of goods */
    ObservableList<Good> getFilteredGoodList();

    /** Returns an unmodifiable view of the filtered list of transactions */
    ObservableList<Transaction> getFilteredTransactionList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' inventory file path.
     */
    Path getInventoryFilePath();

    /**
     * Returns the user prefs' transaction history file path.
     */
    Path getTransactionHistoryFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
