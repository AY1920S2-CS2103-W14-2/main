package cs2103_w14_2.inventory.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import cs2103_w14_2.inventory.commons.exceptions.DataConversionException;
import cs2103_w14_2.inventory.model.Inventory;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.TypicalGoods;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cs2103_w14_2.inventory.model.ReadOnlyList;
import cs2103_w14_2.inventory.model.good.Good;

public class JsonInventoryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonInventoryStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readInventory_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readInventory(null));
    }

    private java.util.Optional<ReadOnlyList<Good>> readInventory(String filePath) throws Exception {
        return new JsonInventoryStorage(Paths.get(filePath)).readInventory(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readInventory("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readInventory("notJsonFormatInventory.json"));
    }

    @Test
    public void readInventory_invalidGoodInventory_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readInventory("invalidGoodInventory.json"));
    }

    @Test
    public void readInventory_invalidAndValidGoodInventory_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readInventory("invalidAndValidGoodInventory.json"));
    }

    @Test
    public void readAndSaveInventory_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempInventory.json");
        Inventory original = TypicalGoods.getTypicalInventory();
        JsonInventoryStorage jsonInventoryStorage = new JsonInventoryStorage(filePath);

        // Save in new file and read back
        jsonInventoryStorage.saveInventory(original, filePath);
        ReadOnlyList<Good> readBack = jsonInventoryStorage.readInventory(filePath).get();
        assertEquals(original, new Inventory(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addGood(TypicalGoods.FIG);
        original.removeGood(TypicalGoods.APPLE);
        jsonInventoryStorage.saveInventory(original, filePath);
        readBack = jsonInventoryStorage.readInventory(filePath).get();
        assertEquals(original, new Inventory(readBack));

        // Save and read without specifying file path
        original.addGood(TypicalGoods.GRAPE);
        jsonInventoryStorage.saveInventory(original); // file path not specified
        readBack = jsonInventoryStorage.readInventory().get(); // file path not specified
        assertEquals(original, new Inventory(readBack));

    }

    @Test
    public void saveInventory_nullInventory_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveInventory(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveInventory(ReadOnlyList<Good> addressBook, String filePath) {
        try {
            new JsonInventoryStorage(Paths.get(filePath))
                    .saveInventory(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveInventory_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveInventory(new Inventory(), null));
    }
}
