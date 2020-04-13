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
import cs2103_w14_2.inventory.model.ReadOnlyList;
import cs2103_w14_2.inventory.model.supplier.Supplier;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    public JsonAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyList<Supplier>> readAddressBook() throws DataConversionException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyList<Supplier>> readAddressBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAddressBook> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAddressBook.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyList<Supplier> addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyList<Supplier> addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
    }

}
