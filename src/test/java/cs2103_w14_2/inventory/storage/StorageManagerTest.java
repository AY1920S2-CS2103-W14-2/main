package cs2103_w14_2.inventory.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.getTypicalInventory;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.getTypicalAddressBook;
import static cs2103_w14_2.inventory.testutil.TypicalTransactions.getTypicalTransactionHistory;

import java.nio.file.Path;

import cs2103_w14_2.inventory.commons.core.GuiSettings;
import cs2103_w14_2.inventory.model.AddressBook;
import cs2103_w14_2.inventory.model.TransactionHistory;
import cs2103_w14_2.inventory.model.UserPrefs;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cs2103_w14_2.inventory.model.Inventory;
import cs2103_w14_2.inventory.model.ReadOnlyList;
import cs2103_w14_2.inventory.model.good.Good;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonInventoryStorage inventoryStorage = new JsonInventoryStorage(getTempFilePath("inventory"));
        JsonTransactionHistoryStorage transactionHistoryStorage =
                new JsonTransactionHistoryStorage(getTempFilePath("transactionHistory"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));

        storageManager = new StorageManager(addressBookStorage, inventoryStorage,
                transactionHistoryStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyList<Supplier> retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void getInventoryFilePath() {
        assertNotNull(storageManager.getInventoryFilePath());
    }

    @Test
    public void inventoryReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonInventoryStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonInventoryStorageTest} class.
         */
        Inventory original = getTypicalInventory();
        storageManager.saveInventory(original);
        ReadOnlyList<Good> retrieved = storageManager.readInventory().get();
        assertEquals(original, new Inventory(retrieved));
    }

    @Test
    public void getTransactionHistoryFilePath() {
        assertNotNull(storageManager.getTransactionHistoryFilePath());
    }

    @Test
    public void transactionHistoryReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonInventoryStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonInventoryStorageTest} class.
         */
        TransactionHistory original = getTypicalTransactionHistory();
        storageManager.saveTransactionHistory(original);
        ReadOnlyList<Transaction> retrieved = storageManager.readTransactionHistory().get();
        assertEquals(original, new TransactionHistory(retrieved));
    }

}
