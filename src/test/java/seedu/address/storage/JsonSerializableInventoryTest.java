package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Inventory;
import seedu.address.testutil.TypicalGoods;

public class JsonSerializableInventoryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableInventoryTest");
    private static final Path TYPICAL_GOODS_FILE = TEST_DATA_FOLDER.resolve("typicalGoodsInventory.json");
    private static final Path INVALID_GOOD_FILE = TEST_DATA_FOLDER.resolve("invalidGoodInventory.json");
    private static final Path DUPLICATE_GOOD_FILE = TEST_DATA_FOLDER.resolve("duplicateGoodInventory.json");

    @Test
    public void toModelType_typicalGoodsFile_success() throws Exception {
        JsonSerializableInventory dataFromFile = JsonUtil.readJsonFile(TYPICAL_GOODS_FILE,
                JsonSerializableInventory.class).get();
        Inventory addressBookFromFile = dataFromFile.toModelType();
        Inventory typicalGoodsInventory = TypicalGoods.getTypicalInventory();
        assertEquals(addressBookFromFile, typicalGoodsInventory);
    }

    @Test
    public void toModelType_invalidGoodFile_throwsIllegalValueException() throws Exception {
        JsonSerializableInventory dataFromFile = JsonUtil.readJsonFile(INVALID_GOOD_FILE,
                JsonSerializableInventory.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateGoods_throwsIllegalValueException() throws Exception {
        JsonSerializableInventory dataFromFile = JsonUtil.readJsonFile(DUPLICATE_GOOD_FILE,
                JsonSerializableInventory.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableInventory.MESSAGE_DUPLICATE_GOOD,
                dataFromFile::toModelType);
    }

}
