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
import cs2103_w14_2.inventory.model.good.Good;

/**
 * A class to access Inventory data stored as a json file on the hard disk.
 */
public class JsonInventoryStorage implements InventoryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonInventoryStorage.class);

    private Path filePath;

    public JsonInventoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getInventoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyList<Good>> readInventory() throws DataConversionException {
        return readInventory(filePath);
    }

    /**
     * Similar to {@link #readInventory()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyList<Good>> readInventory(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableInventory> jsonInventory = JsonUtil.readJsonFile(
                filePath, JsonSerializableInventory.class);
        if (!jsonInventory.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonInventory.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveInventory(ReadOnlyList<Good> inventory) throws IOException {
        saveInventory(inventory, filePath);
    }

    /**
     * Similar to {@link #saveInventory(ReadOnlyList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveInventory(ReadOnlyList<Good> inventory, Path filePath) throws IOException {
        requireNonNull(inventory);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableInventory(inventory), filePath);
    }

}
