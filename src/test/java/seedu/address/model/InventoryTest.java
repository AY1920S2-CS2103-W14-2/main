package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalGoods.getTypicalInventory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.good.Good;
import seedu.address.model.good.exceptions.DuplicateGoodException;
import seedu.address.testutil.GoodBuilder;

public class InventoryTest {

    private final Inventory inventory = new Inventory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), inventory.getReadOnlyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> inventory.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyGoodList_replacesData() {
        Inventory newData = getTypicalInventory();
        inventory.resetData(newData);
        assertEquals(newData, inventory);
    }

    @Test
    public void resetData_withDuplicateGoods_throwsDuplicateGoodException() {
        // Two goods with the same identity fields
        Good editedAlice = new GoodBuilder(APPLE).build();
        List<Good> newGoods = Arrays.asList(APPLE, editedAlice);
        InventoryStub newData = new InventoryStub(newGoods);

        assertThrows(DuplicateGoodException.class, () -> inventory.resetData(newData));
    }

    @Test
    public void hasGood_nullGood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> inventory.hasGood(null));
    }

    @Test
    public void hasGood_goodNotInInventory_returnsFalse() {
        assertFalse(inventory.hasGood(APPLE));
    }

    @Test
    public void hasGood_goodInInventory_returnsTrue() {
        inventory.addGood(APPLE);
        assertTrue(inventory.hasGood(APPLE));
    }

    @Test
    public void hasGood_goodWithSameIdentityFieldsInInventory_returnsTrue() {
        inventory.addGood(APPLE);
        Good editedAlice = new GoodBuilder(APPLE).build();
        assertTrue(inventory.hasGood(editedAlice));
    }

    @Test
    public void getGoodList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> inventory.getReadOnlyList().remove(0));
    }

    /**
     * A stub ReadOnlyList&lt;Good&gt; whose goods list can violate interface constraints.
     */
    private static class InventoryStub implements ReadOnlyList<Good> {
        private final ObservableList<Good> goods = FXCollections.observableArrayList();

        InventoryStub(Collection<Good> goods) {
            this.goods.setAll(goods);
        }

        @Override
        public ObservableList<Good> getReadOnlyList() {
            return goods;
        }
    }

}
