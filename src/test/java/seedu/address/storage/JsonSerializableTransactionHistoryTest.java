package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TransactionHistory;
import seedu.address.testutil.TypicalTransactions;

public class JsonSerializableTransactionHistoryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
            "data", "JsonSerializableTransactionHistoryTest");
    private static final Path TYPICAL_TRANSACTIONS_FILE =
            TEST_DATA_FOLDER.resolve("typicalTransactionsTransactionHistory.json");
    private static final Path INVALID_TRANSACTION_FILE =
            TEST_DATA_FOLDER.resolve("invalidTransactionTransactionHistory.json");
    private static final Path DUPLICATE_TRANSACTION_FILE =
            TEST_DATA_FOLDER.resolve("duplicateTransactionTransactionHistory.json");

    @Test
    public void toModelType_typicalTransactionsFile_success() throws Exception {
        JsonSerializableTransactionHistory dataFromFile = JsonUtil.readJsonFile(TYPICAL_TRANSACTIONS_FILE,
               JsonSerializableTransactionHistory.class).get();
        TransactionHistory transactionHistoryFromFile = dataFromFile.toModelType();
        TransactionHistory typicalTransactionsTransactionHistory = TypicalTransactions.getTypicalTransactionHistory();
        assertEquals(transactionHistoryFromFile, typicalTransactionsTransactionHistory);
    }

    @Test
    public void toModelType_invalidTransactionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTransactionHistory dataFromFile = JsonUtil.readJsonFile(INVALID_TRANSACTION_FILE,
                JsonSerializableTransactionHistory.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTransactions_throwsIllegalValueException() throws Exception {
        JsonSerializableTransactionHistory dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TRANSACTION_FILE,
                JsonSerializableTransactionHistory.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTransactionHistory.MESSAGE_DUPLICATE_TRANSACTION,
                dataFromFile::toModelType);
    }

}
