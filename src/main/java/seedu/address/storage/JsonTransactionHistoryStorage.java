package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyTransactionHistory;

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
    public Optional<ReadOnlyTransactionHistory> readTransactionHistory() throws DataConversionException {
        return readTransactionHistory(filePath);
    }

    /**
     * Similar to {@link #readTransactionHistory()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTransactionHistory> readTransactionHistory(Path filePath) throws DataConversionException {
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
    public void saveTransactionHistory(ReadOnlyTransactionHistory transactionHistory) throws IOException {
        saveTransactionHistory(transactionHistory, filePath);
    }

    /**
     * Similar to {@link #saveTransactionHistory(ReadOnlyTransactionHistory)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTransactionHistory(ReadOnlyTransactionHistory transactionHistory,
                                       Path filePath) throws IOException {
        requireNonNull(transactionHistory);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTransactionHistory(transactionHistory), filePath);
    }

}
