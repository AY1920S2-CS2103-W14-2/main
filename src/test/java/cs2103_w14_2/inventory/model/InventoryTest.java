package cs2103_w14_2.inventory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cs2103_w14_2.inventory.model.good.exceptions.DuplicateGoodException;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.GoodBuilder;
import cs2103_w14_2.inventory.testutil.TypicalGoods;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import cs2103_w14_2.inventory.model.good.Good;

public class InventoryTest {

    private final Inventory inventory = new Inventory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), inventory.getReadOnlyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> inventory.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyGoodList_replacesData() {
        Inventory newData = TypicalGoods.getTypicalInventory();
        inventory.resetData(newData);
        assertEquals(newData, inventory);
    }

    @Test
    public void resetData_withDuplicateGoods_throwsDuplicateGoodException() {
        // Two goods with the same identity fields
        Good editedAlice = new GoodBuilder(TypicalGoods.APPLE).build();
        List<Good> newGoods = Arrays.asList(TypicalGoods.APPLE, editedAlice);
        InventoryStub newData = new InventoryStub(newGoods);

        Assert.assertThrows(DuplicateGoodException.class, () -> inventory.resetData(newData));
    }

    @Test
    public void hasGood_nullGood_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> inventory.hasGood(null));
    }

    @Test
    public void hasGood_goodNotInInventory_returnsFalse() {
        assertFalse(inventory.hasGood(TypicalGoods.APPLE));
    }

    @Test
    public void hasGood_goodInInventory_returnsTrue() {
        inventory.addGood(TypicalGoods.APPLE);
        assertTrue(inventory.hasGood(TypicalGoods.APPLE));
    }

    @Test
    public void hasGood_goodWithSameIdentityFieldsInInventory_returnsTrue() {
        inventory.addGood(TypicalGoods.APPLE);
        Good editedAlice = new GoodBuilder(TypicalGoods.APPLE).build();
        assertTrue(inventory.hasGood(editedAlice));
    }

    @Test
    public void getGoodList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> inventory.getReadOnlyList().remove(0));
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
