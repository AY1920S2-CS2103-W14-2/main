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
import seedu.address.model.dataAnalytic.DataAnalyticManager;
import seedu.address.model.good.Good;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.transaction.Transaction;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook addressBook;
    private final VersionedInventory inventory;
    private final VersionedTransactionHistory transactionHistory;
    private final UserPrefs userPrefs;
    private final FilteredList<Supplier> filteredSuppliers;
    private final FilteredList<Good> filteredGoods;
    private final FilteredList<Transaction> filteredTransactions;
    private final DataAnalyticManager dataAnalyticManager;

    /**
     * Initializes a ModelManager with the given addressBook, inventory, transaction history and userPrefs.
     */
    public ModelManager(ReadOnlyList<Supplier> addressBook, ReadOnlyList<Good> inventory,
                        ReadOnlyList<Transaction> transactionHistory, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, inventory, transactionHistory, userPrefs);

        logger.fine("Initializing with address book: " + addressBook
                + ", inventory: " + inventory
                + ", transaction history: " + transactionHistory
                + " and user prefs " + userPrefs);

        this.addressBook = new VersionedAddressBook(addressBook);
        this.inventory = new VersionedInventory(inventory);
        this.transactionHistory = new VersionedTransactionHistory(transactionHistory);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredSuppliers = new FilteredList<>(this.addressBook.getReadOnlyList());
        filteredGoods = new FilteredList<>(this.inventory.getReadOnlyList());
        filteredTransactions = new FilteredList<>(this.transactionHistory.getReadOnlyList());
        dataAnalyticManager = new DataAnalyticManager();
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
    public void setAddressBook(ReadOnlyList<Supplier> addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyList<Supplier> getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasSupplier(Supplier supplier) {
        requireNonNull(supplier);
        return addressBook.hasSupplier(supplier);
    }

    @Override
    public void deleteSupplier(Supplier target) {
        addressBook.removeSupplier(target);
    }

    @Override
    public void addSupplier(Supplier supplier) {
        addressBook.addSupplier(supplier);
        updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
    }

    @Override
    public void setSupplier(Supplier target, Supplier editedSupplier) {
        requireAllNonNull(target, editedSupplier);

        addressBook.setSupplier(target, editedSupplier);
    }

    //=========== Inventory ================================================================================

    @Override
    public void setInventory(ReadOnlyList<Good> inventory) {
        this.inventory.resetData(inventory);
    }

    @Override
    public ReadOnlyList<Good> getInventory() {
        return inventory.getCurrentState();
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
        updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
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
    public void setTransactionHistory(ReadOnlyList<Transaction> transactionHistory) {
        this.transactionHistory.resetData(transactionHistory);
    }

    @Override
    public ReadOnlyList<Transaction> getTransactionHistory() {
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

    //=========== Filtered Supplier List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Supplier} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Supplier> getFilteredSupplierList() {
        return filteredSuppliers;
    }

    @Override
    public void updateFilteredSupplierList(Predicate<Supplier> predicate) {
        requireNonNull(predicate);
        filteredSuppliers.setPredicate(predicate);
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

    //=========== Filtered Transaction List Accessors =============================================================

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

    //=========== Versioning Commands ===========================================================================

    @Override
    public void commit() {
        addressBook.commit();
        inventory.commit();
        transactionHistory.commit();
    }

    @Override
    public void undo() throws StateNotFoundException {
        addressBook.undo();
        inventory.undo();
        transactionHistory.undo();
    }

    @Override
    public void redo() throws StateNotFoundException {
        addressBook.redo();
        inventory.redo();
        transactionHistory.redo();
    }

    //=========== Data Analysis Commands ===========================================================================

    @Override
    public void displaySalesReport() {
        dataAnalyticManager.generateReport(transactionHistory.getTransactions());
//        displayReport(report);
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
                && filteredSuppliers.equals(other.filteredSuppliers)
                && filteredGoods.equals(other.filteredGoods)
                && filteredTransactions.equals(other.filteredTransactions);
    }

}
