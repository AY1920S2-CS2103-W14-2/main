package cs2103_w14_2.inventory.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import cs2103_w14_2.inventory.commons.exceptions.DataConversionException;
import cs2103_w14_2.inventory.model.TransactionHistory;
import cs2103_w14_2.inventory.model.transaction.Transaction;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.TypicalTransactions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cs2103_w14_2.inventory.model.ReadOnlyList;

public class JsonTransactionHistoryStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonTransactionHistoryStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTransactionHistory_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readTransactionHistory(null));
    }

    private java.util.Optional<ReadOnlyList<Transaction>> readTransactionHistory(String filePath)
            throws Exception {
        return new JsonTransactionHistoryStorage(Paths.get(filePath))
                .readTransactionHistory(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTransactionHistory("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () ->
                readTransactionHistory("notJsonFormatTransactionHistory.json"));
    }

    @Test
    public void readTransactionHistory_invalidTransactionTransactionHistory_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () ->
                readTransactionHistory("invalidTransactionTransactionHistory.json"));
    }

    @Test
    public void readTransactionHistory_invalidAndValidTransactionTransactionHistory_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () ->
                readTransactionHistory("invalidAndValidTransactionTransactionHistory.json"));
    }

    @Test
    public void readAndSaveTransactionHistory_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTransactionHistory.json");
        TransactionHistory original = TypicalTransactions.getTypicalTransactionHistory();
        JsonTransactionHistoryStorage jsonTransactionHistoryStorage =
                new JsonTransactionHistoryStorage(filePath);

        // Save in new file and read back
        jsonTransactionHistoryStorage.saveTransactionHistory(original, filePath);
        ReadOnlyList<Transaction> readBack = jsonTransactionHistoryStorage.readTransactionHistory(filePath).get();
        assertEquals(original, new TransactionHistory(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTransaction(TypicalTransactions.BUY_DURIAN_TRANSACTION);
        original.removeTransaction(TypicalTransactions.BUY_APPLE_TRANSACTION);
        jsonTransactionHistoryStorage.saveTransactionHistory(original, filePath);
        readBack = jsonTransactionHistoryStorage.readTransactionHistory(filePath).get();
        assertEquals(original, new TransactionHistory(readBack));

        // Save and read without specifying file path
        original.addTransaction(TypicalTransactions.BUY_ENTAWAK_TRANSACTION);
        jsonTransactionHistoryStorage.saveTransactionHistory(original); // file path not specified
        readBack = jsonTransactionHistoryStorage.readTransactionHistory().get(); // file path not specified
        assertEquals(original, new TransactionHistory(readBack));

    }

    @Test
    public void saveTransactionHistory_nullTransactionHistory_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                saveTransactionHistory(null, "SomeFile.json"));
    }

    /**
     * Saves {@code transactionHistory} at the specified {@code filePath}.
     */
    private void saveTransactionHistory(ReadOnlyList<Transaction> transactionHistory, String filePath) {
        try {
            new JsonTransactionHistoryStorage(Paths.get(filePath))
                    .saveTransactionHistory(transactionHistory, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTransactionHistory_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveTransactionHistory(new TransactionHistory(), null));
    }
}
