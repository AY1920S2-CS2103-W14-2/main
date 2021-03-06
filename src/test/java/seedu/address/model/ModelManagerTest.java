package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GOODS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalGoods.BANANA;
import static seedu.address.testutil.TypicalSuppliers.ALICE;
import static seedu.address.testutil.TypicalSuppliers.BENSON;
import static seedu.address.testutil.TypicalTransactions.BUY_APPLE_TRANSACTION;
import static seedu.address.testutil.TypicalTransactions.BUY_BANANA_TRANSACTION;
import static seedu.address.testutil.TypicalTransactions.SELL_APPLE_TRANSACTION;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.good.GoodNameContainsKeywordsPredicate;
import seedu.address.model.supplier.SupplierNameContainsKeywordsPredicate;
import seedu.address.model.version.StateNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.InventoryBuilder;
import seedu.address.testutil.TransactionHistoryBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new Inventory(), new Inventory(modelManager.getInventory()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setInventoryFilePath(Paths.get("inventory/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setInventoryFilePath(Paths.get("new/inventory/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setInventoryPath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setInventoryFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void setInventoryFilePath_validPath_setsInventoryFilePath() {
        Path path = Paths.get("inventory/book/file/path");
        modelManager.setInventoryFilePath(path);
        assertEquals(path, modelManager.getInventoryFilePath());
    }

    @Test
    public void hasSupplier_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasSupplier(null));
    }

    @Test
    public void hasGood_nullGood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasGood(null));
    }

    @Test
    public void hasSupplier_supplierNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasSupplier(ALICE));
    }

    @Test
    public void hasGood_goodNotInInventory_returnsFalse() {
        assertFalse(modelManager.hasGood(APPLE));
    }

    @Test
    public void hasSupplier_supplierInAddressBook_returnsTrue() {
        modelManager.addSupplier(ALICE);
        assertTrue(modelManager.hasSupplier(ALICE));
    }

    @Test
    public void hasGood_goodInInventory_returnsTrue() {
        modelManager.addGood(APPLE);
        assertTrue(modelManager.hasGood(APPLE));
    }

    @Test
    public void getFilteredSupplierList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredSupplierList().remove(0));
    }

    @Test
    public void getFilteredGoodList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredGoodList().remove(0));
    }

    @Test
    public void undo_noCommits_throwsStateNotFoundException() {
        assertThrows(StateNotFoundException.class, () -> modelManager.undo());
    }

    @Test
    public void redo_noUndo_throwsStateNotFoundException() {
        assertThrows(StateNotFoundException.class, () -> modelManager.redo());
    }

    @Test
    public void undo_affectsAllDatabases() {
        Model expectedModel = new ModelManager(modelManager.getAddressBook(), modelManager.getInventory(),
                modelManager.getTransactionHistory(), modelManager.getUserPrefs());

        modelManager.addGood(APPLE);
        modelManager.addSupplier(ALICE);
        modelManager.addTransaction(SELL_APPLE_TRANSACTION);
        modelManager.commit();

        modelManager.undo();
        assertEquals(modelManager, expectedModel);
    }

    @Test
    public void commit_savesAllDatabases() {
        Model expectedModelAfterFirstUndo = new ModelManager(modelManager.getAddressBook(), modelManager.getInventory(),
                modelManager.getTransactionHistory(), modelManager.getUserPrefs());
        expectedModelAfterFirstUndo.addGood(APPLE);
        expectedModelAfterFirstUndo.addSupplier(ALICE);

        Model expectedModelAfterSecondUndo = new ModelManager(modelManager.getAddressBook(),
                modelManager.getInventory(), modelManager.getTransactionHistory(), modelManager.getUserPrefs());
        expectedModelAfterSecondUndo.addGood(APPLE);

        modelManager.addGood(APPLE);
        modelManager.commit();
        modelManager.addSupplier(ALICE);
        modelManager.commit();
        modelManager.addTransaction(BUY_APPLE_TRANSACTION);
        modelManager.commit();
        modelManager.undo();

        // check that commit saves both databases, so one undo will only remove one item here
        assertEquals(modelManager, expectedModelAfterFirstUndo);
        modelManager.undo();
        assertEquals(modelManager, expectedModelAfterSecondUndo);
    }

    @Test
    public void redo_affectsAllDatabases() {
        Model expectedModel = new ModelManager(modelManager.getAddressBook(), modelManager.getInventory(),
                modelManager.getTransactionHistory(), modelManager.getUserPrefs());
        expectedModel.addGood(APPLE);
        expectedModel.addSupplier(ALICE);
        expectedModel.addTransaction(SELL_APPLE_TRANSACTION);

        modelManager.addGood(APPLE);
        modelManager.addSupplier(ALICE);
        modelManager.addTransaction(SELL_APPLE_TRANSACTION);
        modelManager.commit();

        modelManager.undo();
        modelManager.redo();
        assertEquals(modelManager, expectedModel);
    }

    @Test
    public void commit_afterUndo_overwritesHistory() {
        Model expectedModel = new ModelManager(modelManager.getAddressBook(), modelManager.getInventory(),
                modelManager.getTransactionHistory(), modelManager.getUserPrefs());
        expectedModel.addGood(APPLE);
        expectedModel.addSupplier(BENSON);

        modelManager.addGood(APPLE);
        modelManager.commit();
        modelManager.addSupplier(ALICE);
        modelManager.commit();
        modelManager.undo();
        modelManager.addSupplier(BENSON);
        modelManager.commit();

        // Alice should be absent
        assertEquals(modelManager, expectedModel);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withSupplier(ALICE).withSupplier(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        Inventory inventory = new InventoryBuilder().withGood(APPLE).withGood(BANANA).build();
        Inventory differentInventory = new Inventory();
        TransactionHistory transactionHistory = new TransactionHistoryBuilder()
                .withTransaction(BUY_BANANA_TRANSACTION).build();
        TransactionHistory differentTransactionHistory = new TransactionHistory();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, inventory, transactionHistory, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, inventory, transactionHistory, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, inventory,
                transactionHistory, userPrefs)));

        // different inventory -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentInventory,
                transactionHistory, userPrefs)));

        // different transaction history -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, inventory,
                differentTransactionHistory, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredSupplierList(new SupplierNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, inventory,
                transactionHistory, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);

        // different filteredList -> returns false
        keywords = APPLE.getGoodName().fullGoodName.split("\\s+");
        modelManager.updateFilteredGoodList(new GoodNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, inventory,
                transactionHistory, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredGoodList(PREDICATE_SHOW_ALL_GOODS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, inventory,
                transactionHistory, differentUserPrefs)));
    }
}
