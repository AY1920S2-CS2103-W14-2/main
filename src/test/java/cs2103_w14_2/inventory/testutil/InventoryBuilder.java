package cs2103_w14_2.inventory.testutil;

import cs2103_w14_2.inventory.model.Inventory;
import cs2103_w14_2.inventory.model.good.Good;

/**
 * A utility class to help with building Inventory objects.
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
