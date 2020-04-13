package cs2103_w14_2.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import cs2103_w14_2.inventory.commons.core.LogsCenter;
import cs2103_w14_2.inventory.commons.exceptions.DataConversionException;
import cs2103_w14_2.inventory.model.ReadOnlyUserPrefs;
import cs2103_w14_2.inventory.model.UserPrefs;
import cs2103_w14_2.inventory.model.ReadOnlyList;
import cs2103_w14_2.inventory.model.good.Good;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.transaction.Transaction;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private InventoryStorage inventoryStorage;
    private TransactionHistoryStorage transactionHistoryStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, InventoryStorage inventoryStorage,
                          TransactionHistoryStorage transactionHistoryStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.inventoryStorage = inventoryStorage;
        this.transactionHistoryStorage = transactionHistoryStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyList<Supplier>> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyList<Supplier>> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyList<Supplier> addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyList<Supplier> addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ Inventory methods ==============================

    @Override
    public Path getInventoryFilePath() {
        return inventoryStorage.getInventoryFilePath();
    }

    @Override
    public Optional<ReadOnlyList<Good>> readInventory() throws DataConversionException, IOException {
        return readInventory(inventoryStorage.getInventoryFilePath());
    }

    @Override
    public Optional<ReadOnlyList<Good>> readInventory(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return inventoryStorage.readInventory(filePath);
    }

    @Override
    public void saveInventory(ReadOnlyList<Good> inventory) throws IOException {
        saveInventory(inventory, inventoryStorage.getInventoryFilePath());
    }

    @Override
    public void saveInventory(ReadOnlyList<Good> inventory, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        inventoryStorage.saveInventory(inventory, filePath);
    }

    // ================ Transaction History methods ==============================

    @Override
    public Path getTransactionHistoryFilePath() {
        return transactionHistoryStorage.getTransactionHistoryFilePath();
    }

    @Override
    public Optional<ReadOnlyList<Transaction>> readTransactionHistory() throws DataConversionException, IOException {
        return readTransactionHistory(transactionHistoryStorage.getTransactionHistoryFilePath());
    }

    @Override
    public Optional<ReadOnlyList<Transaction>> readTransactionHistory(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return transactionHistoryStorage.readTransactionHistory(filePath);
    }

    @Override
    public void saveTransactionHistory(ReadOnlyList<Transaction> transactionHistory) throws IOException {
        saveTransactionHistory(transactionHistory, transactionHistoryStorage.getTransactionHistoryFilePath());
    }

    @Override
    public void saveTransactionHistory(ReadOnlyList<Transaction> transactionHistory, Path filePath)
            throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        transactionHistoryStorage.saveTransactionHistory(transactionHistory, filePath);
    }

}
