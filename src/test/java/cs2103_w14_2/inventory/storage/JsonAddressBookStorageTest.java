package cs2103_w14_2.inventory.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.ALICE;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.HOON;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.IDA;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import cs2103_w14_2.inventory.commons.exceptions.DataConversionException;
import cs2103_w14_2.inventory.model.AddressBook;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cs2103_w14_2.inventory.model.ReadOnlyList;

public class JsonAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyList<Supplier>> readAddressBook(String filePath) throws Exception {
        return new JsonAddressBookStorage(Paths.get(filePath)).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidSupplierAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidSupplierAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidSupplierAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidSupplierAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        AddressBook original = getTypicalAddressBook();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyList<Supplier> readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addSupplier(HOON);
        original.removeSupplier(ALICE);
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Save and read without specifying file path
        original.addSupplier(IDA);
        jsonAddressBookStorage.saveAddressBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readAddressBook().get(); // file path not specified
        assertEquals(original, new AddressBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyList<Supplier> addressBook, String filePath) {
        try {
            new JsonAddressBookStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new AddressBook(), null));
    }
}
