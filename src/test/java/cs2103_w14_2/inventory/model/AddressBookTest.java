package cs2103_w14_2.inventory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.ALICE;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cs2103_w14_2.inventory.logic.commands.CommandTestUtil;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.supplier.exceptions.DuplicateSupplierException;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import cs2103_w14_2.inventory.testutil.SupplierBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getReadOnlyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyPersonList_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateSuppliers_throwsDuplicateSupplierException() {
        // Two suppliers with the same identity fields
        Supplier editedAlice = new SupplierBuilder(ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withOffers(CommandTestUtil.VALID_OFFER_BANANA)
                .build();
        List<Supplier> newSuppliers = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newSuppliers);

        assertThrows(DuplicateSupplierException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasSupplier_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasSupplier(null));
    }

    @Test
    public void hasSupplier_supplierNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasSupplier(ALICE));
    }

    @Test
    public void hasSupplier_supplierInAddressBook_returnsTrue() {
        addressBook.addSupplier(ALICE);
        assertTrue(addressBook.hasSupplier(ALICE));
    }

    @Test
    public void hasSupplier_supplierWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addSupplier(ALICE);
        Supplier editedAlice = new SupplierBuilder(ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withOffers(CommandTestUtil.VALID_OFFER_BANANA)
                .build();
        assertTrue(addressBook.hasSupplier(editedAlice));
    }

    @Test
    public void getSupplierList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getReadOnlyList().remove(0));
    }

    /**
     * A stub ReadOnlyList&lt;Supplier&gt; whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyList<Supplier> {
        private final ObservableList<Supplier> suppliers = FXCollections.observableArrayList();

        AddressBookStub(Collection<Supplier> suppliers) {
            this.suppliers.setAll(suppliers);
        }

        @Override
        public ObservableList<Supplier> getReadOnlyList() {
            return suppliers;
        }
    }

}
