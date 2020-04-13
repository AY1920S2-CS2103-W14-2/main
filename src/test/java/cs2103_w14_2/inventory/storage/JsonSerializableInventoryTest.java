package cs2103_w14_2.inventory.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import cs2103_w14_2.inventory.commons.exceptions.IllegalValueException;
import cs2103_w14_2.inventory.commons.util.JsonUtil;
import cs2103_w14_2.inventory.model.Inventory;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.TypicalGoods;
import org.junit.jupiter.api.Test;

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
        Assert.assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateGoods_throwsIllegalValueException() throws Exception {
        JsonSerializableInventory dataFromFile = JsonUtil.readJsonFile(DUPLICATE_GOOD_FILE,
                JsonSerializableInventory.class).get();
        Assert.assertThrows(IllegalValueException.class, JsonSerializableInventory.MESSAGE_DUPLICATE_GOOD,
                dataFromFile::toModelType);
    }

}
