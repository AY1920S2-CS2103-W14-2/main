package cs2103_w14_2.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import cs2103_w14_2.inventory.commons.exceptions.DataConversionException;
import cs2103_w14_2.inventory.model.ReadOnlyUserPrefs;
import cs2103_w14_2.inventory.model.UserPrefs;
import cs2103_w14_2.inventory.model.ReadOnlyList;
import cs2103_w14_2.inventory.model.good.Good;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.transaction.Transaction;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, InventoryStorage,
        TransactionHistoryStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyList<Supplier>> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyList<Supplier> addressBook) throws IOException;

    @Override
    Path getInventoryFilePath();

    @Override
    Optional<ReadOnlyList<Good>> readInventory() throws DataConversionException, IOException;

    @Override
    void saveInventory(ReadOnlyList<Good> inventory) throws IOException;

    @Override
    Path getTransactionHistoryFilePath();

    @Override
    Optional<ReadOnlyList<Transaction>> readTransactionHistory() throws DataConversionException, IOException;

    @Override
    void saveTransactionHistory(ReadOnlyList<Transaction> transactionHistory) throws IOException;

}
