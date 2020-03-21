package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.good.Good;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Good> PREDICATE_SHOW_ALL_GOODS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Transaction> PREDICATE_SHOW_ALL_TRANSACTIONS = unused -> true;

    //=========== UserPrefs ==================================================================================

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    //=========== Address Book ==================================================================================

    /**
     * Returns the user prefs' inventory file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);


    //=========== Inventory ==================================================================================
    /**
     * Returns the user prefs' inventory file path.
     */
    Path getInventoryFilePath();

    /**
     * Sets the user prefs' inventory file path.
     */
    void setInventoryFilePath(Path inventoryFilePath);

    /**
     * Replaces inventory data with the data in {@code inventory}.
     */
    void setInventory(ReadOnlyInventory inventory);

    /** Returns the Inventory */
    ReadOnlyInventory getInventory();

    /**
     * Returns true if a good with the same identity as {@code good} exists in the inventory.
     */
    boolean hasGood(Good good);

    /**
     * Deletes the given good.
     * The good must exist in the inventory.
     */
    void deleteGood(Good target);

    /**
     * Adds the given good.
     * {@code good} must not already exist in the inventory.
     */
    void addGood(Good good);

    /**
     * Returns the zero-based index of the first occurrence of good if found, -1 otherwise.
     * @param good to be found
     * @return zero based index of good to be found
     */
    int indexOfGood(Good good);

    /**
     * Replaces the given good {@code target} with {@code editedGood}.
     * {@code target} must exist in the inventory.
     * The good identity of {@code editedGood} must not be the same as another existing good in the inventory.
     */
    void setGood(Good target, Good editedGood);

    /** Returns an unmodifiable view of the filtered good list */
    ObservableList<Good> getFilteredGoodList();

    /**
     * Updates the filter of the filtered good list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGoodList(Predicate<Good> predicate);

    //=========== Transaction History ==================================================================================

    /**
     * Returns the user prefs' transaction history file path.
     */
    Path getTransactionHistoryFilePath();

    /**
     * Sets the user prefs' transaction history file path.
     */
    void setTransactionHistoryFilePath(Path transactionHistoryFilePath);

    /**
     * Replaces transaction history data with the data in {@code transactionHistory}.
     */
    void setTransactionHistory(ReadOnlyTransactionHistory transactionHistory);

    /** Returns the transaction history */
    ReadOnlyTransactionHistory getTransactionHistory();

    /**
     * Returns true if a person with the same identity as {@code transaction} exists in the transaction history.
     */
    boolean hasTransaction(Transaction transaction);

    /**
     * Deletes the given transaction.
     * The transaction must exist in the transaction history.
     */
    void deleteTransaction(Transaction target);

    /**
     * Adds the given transaction.
     * {@code transaction} must not already exist in the transaction history.
     */
    void addTransaction(Transaction transaction);

    /** Returns an unmodifiable view of the filtered transaction list */
    ObservableList<Transaction> getFilteredTransactionList();

    /**
     * Updates the filter of the filtered transaction list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransactionList(Predicate<Transaction> predicate);
}
