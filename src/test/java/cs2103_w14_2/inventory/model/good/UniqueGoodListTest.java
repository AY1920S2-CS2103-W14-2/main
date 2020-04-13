package cs2103_w14_2.inventory.model.good;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cs2103_w14_2.inventory.model.good.exceptions.DuplicateGoodException;
import cs2103_w14_2.inventory.model.good.exceptions.GoodNotFoundException;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.GoodBuilder;
import cs2103_w14_2.inventory.testutil.TypicalGoods;
import org.junit.jupiter.api.Test;

public class UniqueGoodListTest {

    private final UniqueGoodList uniqueGoodList = new UniqueGoodList();

    @Test
    public void contains_nullGood_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueGoodList.contains(null));
    }

    @Test
    public void contains_goodNotInList_returnsFalse() {
        assertFalse(uniqueGoodList.contains(TypicalGoods.APPLE));
    }

    @Test
    public void contains_goodInList_returnsTrue() {
        uniqueGoodList.add(TypicalGoods.APPLE);
        assertTrue(uniqueGoodList.contains(TypicalGoods.APPLE));
    }

    @Test
    public void contains_goodWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGoodList.add(TypicalGoods.APPLE);
        Good editedApple = new GoodBuilder(TypicalGoods.APPLE).build();
        assertTrue(uniqueGoodList.contains(editedApple));
    }

    @Test
    public void add_nullGood_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueGoodList.add(null));
    }

    @Test
    public void add_duplicateGood_throwsDuplicateGoodException() {
        uniqueGoodList.add(TypicalGoods.APPLE);
        Assert.assertThrows(DuplicateGoodException.class, () -> uniqueGoodList.add(TypicalGoods.APPLE));
    }

    @Test
    public void setGood_nullTargetGood_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueGoodList.setGood(null, TypicalGoods.APPLE));
    }

    @Test
    public void setGood_nullEditedGood_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueGoodList.setGood(TypicalGoods.APPLE, null));
    }

    @Test
    public void setGood_targetGoodNotInList_throwsGoodNotFoundException() {
        Assert.assertThrows(GoodNotFoundException.class, () -> uniqueGoodList.setGood(TypicalGoods.APPLE, TypicalGoods.APPLE));
    }

    @Test
    public void setGood_editedGoodIsSameGood_success() {
        uniqueGoodList.add(TypicalGoods.APPLE);
        uniqueGoodList.setGood(TypicalGoods.APPLE, TypicalGoods.APPLE);
        UniqueGoodList expectedUniqueGoodList = new UniqueGoodList();
        expectedUniqueGoodList.add(TypicalGoods.APPLE);
        assertEquals(expectedUniqueGoodList, uniqueGoodList);
    }

    @Test
    public void setGood_editedGoodHasSameIdentity_success() {
        uniqueGoodList.add(TypicalGoods.APPLE);
        Good editedApple = new GoodBuilder(TypicalGoods.APPLE).build();
        uniqueGoodList.setGood(TypicalGoods.APPLE, editedApple);
        UniqueGoodList expectedUniqueGoodList = new UniqueGoodList();
        expectedUniqueGoodList.add(editedApple);
        assertEquals(expectedUniqueGoodList, uniqueGoodList);
    }

    @Test
    public void setGood_editedGoodHasDifferentIdentity_success() {
        uniqueGoodList.add(TypicalGoods.APPLE);
        uniqueGoodList.setGood(TypicalGoods.APPLE, TypicalGoods.BANANA);
        UniqueGoodList expectedUniqueGoodList = new UniqueGoodList();
        expectedUniqueGoodList.add(TypicalGoods.BANANA);
        assertEquals(expectedUniqueGoodList, uniqueGoodList);
    }

    @Test
    public void setGood_editedGoodHasNonUniqueIdentity_throwsDuplicateGoodException() {
        uniqueGoodList.add(TypicalGoods.APPLE);
        uniqueGoodList.add(TypicalGoods.BANANA);
        Assert.assertThrows(DuplicateGoodException.class, () -> uniqueGoodList.setGood(TypicalGoods.APPLE, TypicalGoods.BANANA));
    }

    @Test
    public void remove_nullGood_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueGoodList.remove(null));
    }

    @Test
    public void remove_goodDoesNotExist_throwsGoodNotFoundException() {
        Assert.assertThrows(GoodNotFoundException.class, () -> uniqueGoodList.remove(TypicalGoods.APPLE));
    }

    @Test
    public void remove_existingGood_removesGood() {
        uniqueGoodList.add(TypicalGoods.APPLE);
        uniqueGoodList.remove(TypicalGoods.APPLE);
        UniqueGoodList expectedUniqueGoodList = new UniqueGoodList();
        assertEquals(expectedUniqueGoodList, uniqueGoodList);
    }

    @Test
    public void setGoods_nullUniqueGoodList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueGoodList.setGoods((UniqueGoodList) null));
    }

    @Test
    public void setGoods_uniqueGoodList_replacesOwnListWithProvidedUniqueGoodList() {
        uniqueGoodList.add(TypicalGoods.APPLE);
        UniqueGoodList expectedUniqueGoodList = new UniqueGoodList();
        expectedUniqueGoodList.add(TypicalGoods.BANANA);
        uniqueGoodList.setGoods(expectedUniqueGoodList);
        assertEquals(expectedUniqueGoodList, uniqueGoodList);
    }

    @Test
    public void setGoods_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueGoodList.setGoods((List<Good>) null));
    }

    @Test
    public void setGoods_list_replacesOwnListWithProvidedList() {
        uniqueGoodList.add(TypicalGoods.APPLE);
        List<Good> goodList = Collections.singletonList(TypicalGoods.BANANA);
        uniqueGoodList.setGoods(goodList);
        UniqueGoodList expectedUniqueGoodList = new UniqueGoodList();
        expectedUniqueGoodList.add(TypicalGoods.BANANA);
        assertEquals(expectedUniqueGoodList, uniqueGoodList);
    }

    @Test
    public void setGoods_listWithDuplicateGoods_throwsDuplicateGoodException() {
        List<Good> listWithDuplicateGoods = Arrays.asList(TypicalGoods.APPLE, TypicalGoods.APPLE);
        Assert.assertThrows(DuplicateGoodException.class, () -> uniqueGoodList.setGoods(listWithDuplicateGoods));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueGoodList.asUnmodifiableObservableList().remove(0));
    }

}
