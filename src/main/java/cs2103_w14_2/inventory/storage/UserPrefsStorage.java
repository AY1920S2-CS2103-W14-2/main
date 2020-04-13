package cs2103_w14_2.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import cs2103_w14_2.inventory.model.ReadOnlyUserPrefs;
import cs2103_w14_2.inventory.model.UserPrefs;
import cs2103_w14_2.inventory.commons.exceptions.DataConversionException;

/**
 * Represents a storage for {@link cs2103_w14_2.inventory.model.UserPrefs}.
 */
public interface UserPrefsStorage {

    /**
     * Returns the file path of the UserPrefs data file.
     */
    Path getUserPrefsFilePath();

    /**
     * Returns UserPrefs data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    /**
     * Saves the given {@link cs2103_w14_2.inventory.model.ReadOnlyUserPrefs} to the storage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

}
