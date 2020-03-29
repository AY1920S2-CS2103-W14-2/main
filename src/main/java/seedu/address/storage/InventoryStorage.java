package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.good.Good;

/**
 * Represents a storage for {@link seedu.address.model.Inventory}.
 */
public interface InventoryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getInventoryFilePath();

    /**
     * Returns Inventory data as a {@link ReadOnlyList}&lt;Good&gt;.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyList<Good>> readInventory() throws DataConversionException, IOException;

    /**
     * @see #getInventoryFilePath()
     */
    Optional<ReadOnlyList<Good>> readInventory(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyList}&lt;Good&gt; to the storage.
     * @param inventory cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveInventory(ReadOnlyList<Good> inventory) throws IOException;

    /**
     * @see #saveInventory(ReadOnlyList)
     */
    void saveInventory(ReadOnlyList<Good> inventory, Path filePath) throws IOException;

}
