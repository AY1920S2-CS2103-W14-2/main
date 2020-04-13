package cs2103_w14_2.inventory.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import cs2103_w14_2.inventory.commons.core.LogsCenter;
import cs2103_w14_2.inventory.commons.exceptions.DataConversionException;
import cs2103_w14_2.inventory.commons.exceptions.IllegalValueException;
import cs2103_w14_2.inventory.commons.util.FileUtil;
import cs2103_w14_2.inventory.commons.util.JsonUtil;
import cs2103_w14_2.inventory.model.transaction.Transaction;
import cs2103_w14_2.inventory.model.ReadOnlyList;

/**
 * A class to access TransactionHistory data stored as a json file on the hard disk.
 */
public class JsonTransactionHistoryStorage implements TransactionHistoryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTransactionHistoryStorage.class);

    private Path filePath;

    public JsonTransactionHistoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTransactionHistoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyList<Transaction>> readTransactionHistory() throws DataConversionException {
        return readTransactionHistory(filePath);
    }

    /**
     * Similar to {@link #readTransactionHistory()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyList<Transaction>> readTransactionHistory(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableTransactionHistory> jsonTransactionHistory = JsonUtil.readJsonFile(
                filePath, JsonSerializableTransactionHistory.class);
        if (!jsonTransactionHistory.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTransactionHistory.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTransactionHistory(ReadOnlyList<Transaction> transactionHistory) throws IOException {
        saveTransactionHistory(transactionHistory, filePath);
    }

    /**
     * Similar to {@link #saveTransactionHistory(cs2103_w14_2.inventory.model.ReadOnlyList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTransactionHistory(ReadOnlyList<Transaction> transactionHistory,
                                       Path filePath) throws IOException {
        requireNonNull(transactionHistory);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTransactionHistory(transactionHistory), filePath);
    }

}
