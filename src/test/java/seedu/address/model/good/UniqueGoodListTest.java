package seedu.address.model.good;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalGoods.BANANA;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.good.exceptions.DuplicateGoodException;
import seedu.address.model.good.exceptions.GoodNotFoundException;
import seedu.address.testutil.GoodBuilder;

public class UniqueGoodListTest {

    private final UniqueGoodList uniqueGoodList = new UniqueGoodList();

    @Test
    public void contains_nullGood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGoodList.contains(null));
    }

    @Test
    public void contains_goodNotInList_returnsFalse() {
        assertFalse(uniqueGoodList.contains(APPLE));
    }

    @Test
    public void contains_goodInList_returnsTrue() {
        uniqueGoodList.add(APPLE);
        assertTrue(uniqueGoodList.contains(APPLE));
    }

    @Test
    public void contains_goodWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGoodList.add(APPLE);
        Good editedApple = new GoodBuilder(APPLE).build();
        assertTrue(uniqueGoodList.contains(editedApple));
    }

    @Test
    public void add_nullGood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGoodList.add(null));
    }

    @Test
    public void add_duplicateGood_throwsDuplicateGoodException() {
        uniqueGoodList.add(APPLE);
        assertThrows(DuplicateGoodException.class, () -> uniqueGoodList.add(APPLE));
    }

    @Test
    public void setGood_nullTargetGood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGoodList.setGood(null, APPLE));
    }

    @Test
    public void setGood_nullEditedGood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGoodList.setGood(APPLE, null));
    }

    @Test
    public void setGood_targetGoodNotInList_throwsGoodNotFoundException() {
        assertThrows(GoodNotFoundException.class, () -> uniqueGoodList.setGood(APPLE, APPLE));
    }

    @Test
    public void setGood_editedGoodIsSameGood_success() {
        uniqueGoodList.add(APPLE);
        uniqueGoodList.setGood(APPLE, APPLE);
        UniqueGoodList expectedUniqueGoodList = new UniqueGoodList();
        expectedUniqueGoodList.add(APPLE);
        assertEquals(expectedUniqueGoodList, uniqueGoodList);
    }

    @Test
    public void setGood_editedGoodHasSameIdentity_success() {
        uniqueGoodList.add(APPLE);
        Good editedApple = new GoodBuilder(APPLE).build();
        uniqueGoodList.setGood(APPLE, editedApple);
        UniqueGoodList expectedUniqueGoodList = new UniqueGoodList();
        expectedUniqueGoodList.add(editedApple);
        assertEquals(expectedUniqueGoodList, uniqueGoodList);
    }

    @Test
    public void setGood_editedGoodHasDifferentIdentity_success() {
        uniqueGoodList.add(APPLE);
        uniqueGoodList.setGood(APPLE, BANANA);
        UniqueGoodList expectedUniqueGoodList = new UniqueGoodList();
        expectedUniqueGoodList.add(BANANA);
        assertEquals(expectedUniqueGoodList, uniqueGoodList);
    }

    @Test
    public void setGood_editedGoodHasNonUniqueIdentity_throwsDuplicateGoodException() {
        uniqueGoodList.add(APPLE);
        uniqueGoodList.add(BANANA);
        assertThrows(DuplicateGoodException.class, () -> uniqueGoodList.setGood(APPLE, BANANA));
    }

    @Test
    public void remove_nullGood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGoodList.remove(null));
    }

    @Test
    public void remove_goodDoesNotExist_throwsGoodNotFoundException() {
        assertThrows(GoodNotFoundException.class, () -> uniqueGoodList.remove(APPLE));
    }

    @Test
    public void remove_existingGood_removesGood() {
        uniqueGoodList.add(APPLE);
        uniqueGoodList.remove(APPLE);
        UniqueGoodList expectedUniqueGoodList = new UniqueGoodList();
        assertEquals(expectedUniqueGoodList, uniqueGoodList);
    }

    @Test
    public void setGoods_nullUniqueGoodList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGoodList.setGoods((UniqueGoodList) null));
    }

    @Test
    public void setGoods_uniqueGoodList_replacesOwnListWithProvidedUniqueGoodList() {
        uniqueGoodList.add(APPLE);
        UniqueGoodList expectedUniqueGoodList = new UniqueGoodList();
        expectedUniqueGoodList.add(BANANA);
        uniqueGoodList.setGoods(expectedUniqueGoodList);
        assertEquals(expectedUniqueGoodList, uniqueGoodList);
    }

    @Test
    public void setGoods_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGoodList.setGoods((List<Good>) null));
    }

    @Test
    public void setGoods_list_replacesOwnListWithProvidedList() {
        uniqueGoodList.add(APPLE);
        List<Good> goodList = Collections.singletonList(BANANA);
        uniqueGoodList.setGoods(goodList);
        UniqueGoodList expectedUniqueGoodList = new UniqueGoodList();
        expectedUniqueGoodList.add(BANANA);
        assertEquals(expectedUniqueGoodList, uniqueGoodList);
    }

    @Test
    public void setGoods_listWithDuplicateGoods_throwsDuplicateGoodException() {
        List<Good> listWithDuplicateGoods = Arrays.asList(APPLE, APPLE);
        assertThrows(DuplicateGoodException.class, () -> uniqueGoodList.setGoods(listWithDuplicateGoods));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueGoodList.asUnmodifiableObservableList().remove(0));
    }

}
