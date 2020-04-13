package cs2103_w14_2.inventory.model.supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cs2103_w14_2.inventory.logic.commands.CommandTestUtil;
import cs2103_w14_2.inventory.model.supplier.exceptions.DuplicateSupplierException;
import cs2103_w14_2.inventory.model.supplier.exceptions.SupplierNotFoundException;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.SupplierBuilder;
import cs2103_w14_2.inventory.testutil.TypicalSuppliers;
import org.junit.jupiter.api.Test;

public class UniqueSupplierListTest {

    private final UniqueSupplierList uniqueSupplierList = new UniqueSupplierList();

    @Test
    public void contains_nullSupplier_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueSupplierList.contains(null));
    }

    @Test
    public void contains_supplierNotInList_returnsFalse() {
        assertFalse(uniqueSupplierList.contains(TypicalSuppliers.ALICE));
    }

    @Test
    public void contains_supplierInList_returnsTrue() {
        uniqueSupplierList.add(TypicalSuppliers.ALICE);
        assertTrue(uniqueSupplierList.contains(TypicalSuppliers.ALICE));
    }

    @Test
    public void contains_supplierWithSameIdentityFieldsInList_returnsTrue() {
        uniqueSupplierList.add(TypicalSuppliers.ALICE);
        Supplier editedAlice = new SupplierBuilder(TypicalSuppliers.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withOffers(CommandTestUtil.VALID_OFFER_BANANA)
                .build();
        assertTrue(uniqueSupplierList.contains(editedAlice));
    }

    @Test
    public void add_nullSupplier_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueSupplierList.add(null));
    }

    @Test
    public void add_duplicateSupplier_throwsDuplicateSupplierException() {
        uniqueSupplierList.add(TypicalSuppliers.ALICE);
        Assert.assertThrows(DuplicateSupplierException.class, () -> uniqueSupplierList.add(TypicalSuppliers.ALICE));
    }

    @Test
    public void setSupplier_nullTargetSupplier_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueSupplierList.setSupplier(null, TypicalSuppliers.ALICE));
    }

    @Test
    public void setSupplier_nullEditedSupplier_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueSupplierList.setSupplier(TypicalSuppliers.ALICE, null));
    }

    @Test
    public void setSupplier_targetSupplierNotInList_throwsSupplierNotFoundException() {
        Assert.assertThrows(SupplierNotFoundException.class, () -> uniqueSupplierList.setSupplier(TypicalSuppliers.ALICE, TypicalSuppliers.ALICE));
    }

    @Test
    public void setSupplier_editedSupplierIsSameSupplier_success() {
        uniqueSupplierList.add(TypicalSuppliers.ALICE);
        uniqueSupplierList.setSupplier(TypicalSuppliers.ALICE, TypicalSuppliers.ALICE);
        UniqueSupplierList expectedUniqueSupplierList = new UniqueSupplierList();
        expectedUniqueSupplierList.add(TypicalSuppliers.ALICE);
        assertEquals(expectedUniqueSupplierList, uniqueSupplierList);
    }

    @Test
    public void setSupplier_editedSupplierHasSameIdentity_success() {
        uniqueSupplierList.add(TypicalSuppliers.ALICE);
        Supplier editedAlice = new SupplierBuilder(TypicalSuppliers.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withOffers(CommandTestUtil.VALID_OFFER_BANANA)
                .build();
        uniqueSupplierList.setSupplier(TypicalSuppliers.ALICE, editedAlice);
        UniqueSupplierList expectedUniqueSupplierList = new UniqueSupplierList();
        expectedUniqueSupplierList.add(editedAlice);
        assertEquals(expectedUniqueSupplierList, uniqueSupplierList);
    }

    @Test
    public void setSupplier_editedSupplierHasDifferentIdentity_success() {
        uniqueSupplierList.add(TypicalSuppliers.ALICE);
        uniqueSupplierList.setSupplier(TypicalSuppliers.ALICE, TypicalSuppliers.BOB);
        UniqueSupplierList expectedUniqueSupplierList = new UniqueSupplierList();
        expectedUniqueSupplierList.add(TypicalSuppliers.BOB);
        assertEquals(expectedUniqueSupplierList, uniqueSupplierList);
    }

    @Test
    public void setSupplier_editedSupplierHasNonUniqueIdentity_throwsDuplicateSupplierException() {
        uniqueSupplierList.add(TypicalSuppliers.ALICE);
        uniqueSupplierList.add(TypicalSuppliers.BOB);
        Assert.assertThrows(DuplicateSupplierException.class, () -> uniqueSupplierList.setSupplier(TypicalSuppliers.ALICE, TypicalSuppliers.BOB));
    }

    @Test
    public void remove_nullSupplier_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueSupplierList.remove(null));
    }

    @Test
    public void remove_supplierDoesNotExist_throwsSupplierNotFoundException() {
        Assert.assertThrows(SupplierNotFoundException.class, () -> uniqueSupplierList.remove(TypicalSuppliers.ALICE));
    }

    @Test
    public void remove_existingSupplier_removesSupplier() {
        uniqueSupplierList.add(TypicalSuppliers.ALICE);
        uniqueSupplierList.remove(TypicalSuppliers.ALICE);
        UniqueSupplierList expectedUniqueSupplierList = new UniqueSupplierList();
        assertEquals(expectedUniqueSupplierList, uniqueSupplierList);
    }

    @Test
    public void setSuppliers_nullUniqueSupplierList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueSupplierList.setSuppliers((UniqueSupplierList) null));
    }

    @Test
    public void setSuppliers_uniqueSupplierList_replacesOwnListWithProvidedUniqueSupplierList() {
        uniqueSupplierList.add(TypicalSuppliers.ALICE);
        UniqueSupplierList expectedUniqueSupplierList = new UniqueSupplierList();
        expectedUniqueSupplierList.add(TypicalSuppliers.BOB);
        uniqueSupplierList.setSuppliers(expectedUniqueSupplierList);
        assertEquals(expectedUniqueSupplierList, uniqueSupplierList);
    }

    @Test
    public void setSuppliers_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueSupplierList.setSuppliers((List<Supplier>) null));
    }

    @Test
    public void setSuppliers_list_replacesOwnListWithProvidedList() {
        uniqueSupplierList.add(TypicalSuppliers.ALICE);
        List<Supplier> supplierList = Collections.singletonList(TypicalSuppliers.BOB);
        uniqueSupplierList.setSuppliers(supplierList);
        UniqueSupplierList expectedUniqueSupplierList = new UniqueSupplierList();
        expectedUniqueSupplierList.add(TypicalSuppliers.BOB);
        assertEquals(expectedUniqueSupplierList, uniqueSupplierList);
    }

    @Test
    public void setSuppliers_listWithDuplicateSuppliers_throwsDuplicateSupplierException() {
        List<Supplier> listWithDuplicateSuppliers = Arrays.asList(TypicalSuppliers.ALICE, TypicalSuppliers.ALICE);
        Assert.assertThrows(DuplicateSupplierException.class, () ->
                uniqueSupplierList.setSuppliers(listWithDuplicateSuppliers));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueSupplierList.asUnmodifiableObservableList().remove(0));
    }
}
