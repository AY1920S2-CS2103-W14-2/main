package seedu.address.model.supplier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFER_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSuppliers.ALICE;
import static seedu.address.testutil.TypicalSuppliers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.SupplierBuilder;

public class SupplierTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Supplier supplier = new SupplierBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> supplier.getOffers().remove(0));
    }

    @Test
    public void isSameSupplier() {
        // same object -> returns true
        assertTrue(ALICE.isSameSupplier(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameSupplier(null));

        // different phone and email -> returns false
        Supplier editedAlice = new SupplierBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameSupplier(editedAlice));

        // different name -> returns false
        editedAlice = new SupplierBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameSupplier(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new SupplierBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withOffers(VALID_OFFER_BANANA).build();
        assertTrue(ALICE.isSameSupplier(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new SupplierBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withOffers(VALID_OFFER_BANANA).build();
        assertTrue(ALICE.isSameSupplier(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new SupplierBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withOffers(VALID_OFFER_BANANA).build();
        assertTrue(ALICE.isSameSupplier(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Supplier aliceCopy = new SupplierBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different supplier -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Supplier editedAlice = new SupplierBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new SupplierBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new SupplierBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new SupplierBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different offers -> returns false
        editedAlice = new SupplierBuilder(ALICE).withOffers(VALID_OFFER_BANANA).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
