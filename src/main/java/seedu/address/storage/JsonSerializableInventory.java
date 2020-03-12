package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.good.Good;

/**
 * An Immutable Inventory that is serializable to JSON format.
 */
@JsonRootName(value = "inventory")
class JsonSerializableInventory {

    public static final String MESSAGE_DUPLICATE_GOOD = "Goods list contains duplicate good(s).";

    private final List<JsonAdaptedGood> goods = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableInventory} with the given goods.
     */
    @JsonCreator
    public JsonSerializableInventory(@JsonProperty("goods") List<JsonAdaptedGood> goods) {
        this.goods.addAll(goods);
    }

    /**
     * Converts a given {@code ReadOnlyInventory} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableInventory}.
     */
    public JsonSerializableInventory(ReadOnlyInventory source) {
        goods.addAll(source.getGoodList().stream().map(JsonAdaptedGood::new).collect(Collectors.toList()));
    }

    /**
     * Converts this inventory into the model's {@code Inventory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Inventory toModelType() throws IllegalValueException {
        Inventory inventory = new Inventory();
        for (JsonAdaptedGood jsonAdaptedGood : goods) {
            Good good = jsonAdaptedGood.toModelType();
            if (inventory.hasGood(good)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_GOOD);
            }
            inventory.addGood(good);
        }
        return inventory;
    }

}
