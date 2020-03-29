package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.transaction.Transaction;

/**
 * Represents a storage for {@link seedu.address.model.TransactionHistory}.
 */
public interface TransactionHistoryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTransactionHistoryFilePath();

    /**
     * Returns TransactionHistory data as a {@link ReadOnlyList}&lt;Transaction&gt;.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyList<Transaction>> readTransactionHistory() throws DataConversionException, IOException;

    /**
     * @see #getTransactionHistoryFilePath()
     */
    Optional<ReadOnlyList<Transaction>> readTransactionHistory(Path filePath)
            throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.model.ReadOnlyList}&lt;Transaction&gt; to the storage.
     *
     * @param transactionHistory cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTransactionHistory(ReadOnlyList<Transaction> transactionHistory) throws IOException;

    /**
     * @see #saveTransactionHistory(ReadOnlyList)
     */
    void saveTransactionHistory(ReadOnlyList<Transaction> transactionHistory, Path filePath) throws IOException;

}
