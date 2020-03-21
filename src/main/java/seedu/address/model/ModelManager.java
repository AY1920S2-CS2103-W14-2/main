package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.good.Good;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final Inventory inventory;
    private final TransactionHistory transactionHistory;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Good> filteredGoods;
    private final FilteredList<Transaction> filteredTransactions;

    /**
     * Initializes a ModelManager with the given addressBook, inventory, transaction history and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyInventory inventory,
                        ReadOnlyTransactionHistory transactionHistory, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, inventory, transactionHistory, userPrefs);

        logger.fine("Initializing with address book: " + addressBook
                + ", inventory: " + inventory
                + ", transaction history: " + transactionHistory
                + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.inventory = new Inventory(inventory);
        this.transactionHistory = new TransactionHistory(transactionHistory);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredGoods = new FilteredList<>(this.inventory.getGoodList());
        filteredTransactions = new FilteredList<>(this.transactionHistory.getTransactionList());
    }

    public ModelManager() {
        this(new AddressBook(), new Inventory(), new TransactionHistory(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getInventoryFilePath() {
        return userPrefs.getInventoryFilePath();
    }

    @Override
    public void setInventoryFilePath(Path inventoryFilePath) {
        requireNonNull(inventoryFilePath);
        userPrefs.setInventoryFilePath(inventoryFilePath);
    }

    @Override
    public Path getTransactionHistoryFilePath() {
        return userPrefs.getTransactionHistoryFilePath();
    }

    @Override
    public void setTransactionHistoryFilePath(Path transactionHistoryFilePath) {
        requireNonNull(transactionHistoryFilePath);
        userPrefs.setTransactionHistoryFilePath(transactionHistoryFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Inventory ================================================================================

    @Override
    public void setInventory(ReadOnlyInventory inventory) {
        this.inventory.resetData(inventory);
    }

    @Override
    public ReadOnlyInventory getInventory() {
        return inventory;
    }

    @Override
    public boolean hasGood(Good good) {
        requireNonNull(good);
        return inventory.hasGood(good);
    }

    @Override
    public void deleteGood(Good target) {
        inventory.removeGood(target);
    }

    @Override
    public void addGood(Good good) {
        inventory.addGood(good);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public int indexOfGood(Good good) {
        return inventory.index(good);
    }

    @Override
    public void setGood(Good target, Good editedGood) {
        requireAllNonNull(target, editedGood);

        inventory.setGood(target, editedGood);
    }

    //=========== Transaction History ================================================================================

    @Override
    public void setTransactionHistory(ReadOnlyTransactionHistory transactionHistory) {
        this.transactionHistory.resetData(transactionHistory);
    }

    @Override
    public ReadOnlyTransactionHistory getTransactionHistory() {
        return transactionHistory;
    }

    @Override
    public boolean hasTransaction(Transaction transaction) {
        requireNonNull(transaction);
        return transactionHistory.hasTransaction(transaction);
    }

    @Override
    public void deleteTransaction(Transaction target) {
        transactionHistory.removeTransaction(target);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactionHistory.addTransaction(transaction);
        updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Good List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Good} backed by the internal list of
     * {@code versionedInventory}
     */
    @Override
    public ObservableList<Good> getFilteredGoodList() {
        return filteredGoods;
    }

    @Override
    public void updateFilteredGoodList(Predicate<Good> predicate) {
        requireNonNull(predicate);
        filteredGoods.setPredicate(predicate);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Transaction} backed by the internal list of
     * {@code versionedTransactionHistory}
     */
    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return filteredTransactions;
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && inventory.equals(other.inventory)
                && transactionHistory.equals(other.transactionHistory)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredGoods.equals(other.filteredGoods)
                && filteredTransactions.equals(other.filteredTransactions);
    }

}
