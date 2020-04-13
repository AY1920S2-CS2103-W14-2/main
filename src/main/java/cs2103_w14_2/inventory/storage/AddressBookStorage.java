package cs2103_w14_2.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import cs2103_w14_2.inventory.commons.exceptions.DataConversionException;
import cs2103_w14_2.inventory.model.ReadOnlyList;
import cs2103_w14_2.inventory.model.supplier.Supplier;

/**
 * Represents a storage for {@link cs2103_w14_2.inventory.model.AddressBook}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyList}&lt;Person&gt;.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws cs2103_w14_2.inventory.commons.exceptions.DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyList<Supplier>> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyList<Supplier>> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyList}&lt;Person&gt; to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyList<Supplier> addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyList)
     */
    void saveAddressBook(ReadOnlyList<Supplier> addressBook, Path filePath) throws IOException;

}
