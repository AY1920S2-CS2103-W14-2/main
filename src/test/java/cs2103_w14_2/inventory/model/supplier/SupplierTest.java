package cs2103_w14_2.inventory.model.supplier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import cs2103_w14_2.inventory.logic.commands.CommandTestUtil;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.SupplierBuilder;
import cs2103_w14_2.inventory.testutil.TypicalSuppliers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SupplierTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Supplier supplier = new SupplierBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> supplier.getOffers().remove(0));
    }

    @Test
    public void isSameSupplier() {
        // same object -> returns true
        Assertions.assertTrue(TypicalSuppliers.ALICE.isSameSupplier(TypicalSuppliers.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalSuppliers.ALICE.isSameSupplier(null));

        // different phone and email -> returns false
        Supplier editedAlice = new SupplierBuilder(TypicalSuppliers.ALICE).withPhone(CommandTestUtil.VALID_PHONE_BOB).withEmail(CommandTestUtil.VALID_EMAIL_BOB).build();
        Assertions.assertFalse(TypicalSuppliers.ALICE.isSameSupplier(editedAlice));

        // different name -> returns false
        editedAlice = new SupplierBuilder(TypicalSuppliers.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalSuppliers.ALICE.isSameSupplier(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new SupplierBuilder(TypicalSuppliers.ALICE).withEmail(CommandTestUtil.VALID_EMAIL_BOB).withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
                .withOffers(CommandTestUtil.VALID_OFFER_BANANA).build();
        Assertions.assertTrue(TypicalSuppliers.ALICE.isSameSupplier(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new SupplierBuilder(TypicalSuppliers.ALICE).withPhone(CommandTestUtil.VALID_PHONE_BOB).withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
                .withOffers(CommandTestUtil.VALID_OFFER_BANANA).build();
        Assertions.assertTrue(TypicalSuppliers.ALICE.isSameSupplier(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new SupplierBuilder(TypicalSuppliers.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withOffers(CommandTestUtil.VALID_OFFER_BANANA).build();
        Assertions.assertTrue(TypicalSuppliers.ALICE.isSameSupplier(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Supplier aliceCopy = new SupplierBuilder(TypicalSuppliers.ALICE).build();
        Assertions.assertTrue(TypicalSuppliers.ALICE.equals(aliceCopy));

        // same object -> returns true
        Assertions.assertTrue(TypicalSuppliers.ALICE.equals(TypicalSuppliers.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalSuppliers.ALICE.equals(null));

        // different type -> returns false
        Assertions.assertFalse(TypicalSuppliers.ALICE.equals(5));

        // different supplier -> returns false
        Assertions.assertFalse(TypicalSuppliers.ALICE.equals(TypicalSuppliers.BOB));

        // different name -> returns false
        Supplier editedAlice = new SupplierBuilder(TypicalSuppliers.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalSuppliers.ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new SupplierBuilder(TypicalSuppliers.ALICE).withPhone(CommandTestUtil.VALID_PHONE_BOB).build();
        Assertions.assertFalse(TypicalSuppliers.ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new SupplierBuilder(TypicalSuppliers.ALICE).withEmail(CommandTestUtil.VALID_EMAIL_BOB).build();
        Assertions.assertFalse(TypicalSuppliers.ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new SupplierBuilder(TypicalSuppliers.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).build();
        Assertions.assertFalse(TypicalSuppliers.ALICE.equals(editedAlice));

        // different offers -> returns false
        editedAlice = new SupplierBuilder(TypicalSuppliers.ALICE).withOffers(CommandTestUtil.VALID_OFFER_BANANA).build();
        Assertions.assertFalse(TypicalSuppliers.ALICE.equals(editedAlice));
    }
}
