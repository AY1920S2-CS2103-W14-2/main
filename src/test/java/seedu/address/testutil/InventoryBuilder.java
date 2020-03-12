package seedu.address.testutil;

import seedu.address.model.Inventory;
import seedu.address.model.good.Good;

/**
 * A utility class to help with building Inventory objects.
 * Example usage: <br>
 *     {@code Inventory ab = new InventoryBuilder().withGood("Apple", "Banana").build();}
 */
public class InventoryBuilder {

    private Inventory inventory;

    public InventoryBuilder() {
        inventory = new Inventory();
    }

    public InventoryBuilder(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Adds a new {@code Good} to the {@code Inventory} that we are building.
     */
    public InventoryBuilder withGood(Good good) {
        inventory.addGood(good);
        return this;
    }

    public Inventory build() {
        return inventory;
    }
}
