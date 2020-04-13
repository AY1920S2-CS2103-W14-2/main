package cs2103_w14_2.inventory.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import cs2103_w14_2.inventory.commons.exceptions.IllegalValueException;
import cs2103_w14_2.inventory.commons.util.JsonUtil;
import cs2103_w14_2.inventory.model.TransactionHistory;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.TypicalTransactions;
import org.junit.jupiter.api.Test;

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
        Assert.assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTransactions_throwsIllegalValueException() throws Exception {
        JsonSerializableTransactionHistory dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TRANSACTION_FILE,
                JsonSerializableTransactionHistory.class).get();
        Assert.assertThrows(IllegalValueException.class, JsonSerializableTransactionHistory.MESSAGE_DUPLICATE_TRANSACTION,
                dataFromFile::toModelType);
    }

}
