package cs2103_w14_2.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import cs2103_w14_2.inventory.commons.exceptions.DataConversionException;
import cs2103_w14_2.inventory.model.ReadOnlyList;
import cs2103_w14_2.inventory.model.good.Good;

/**
 * Represents a storage for {@link cs2103_w14_2.inventory.model.Inventory}.
 */
public interface InventoryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getInventoryFilePath();

    /**
     * Returns Inventory data as a {@link ReadOnlyList}&lt;Good&gt;.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws cs2103_w14_2.inventory.commons.exceptions.DataConversionException if the data in storage is not in the expected format.
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
