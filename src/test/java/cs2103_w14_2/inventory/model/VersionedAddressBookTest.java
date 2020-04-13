package cs2103_w14_2.inventory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.version.StateNotFoundException;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.SupplierBuilder;
import cs2103_w14_2.inventory.testutil.TypicalSuppliers;
import org.junit.jupiter.api.Test;

public class VersionedAddressBookTest {

    private VersionedAddressBook versionedAddressBook = new VersionedAddressBook();

    @Test
    public void undo_withoutCommits_throwsStateNotFoundException() {
        Assert.assertThrows(StateNotFoundException.class, () -> versionedAddressBook.undo());
    }

    @Test
    public void undo_afterOneCommit_removeChanges() {
        AddressBook expectedAddressBook = new AddressBook(versionedAddressBook);

        versionedAddressBook.addSupplier(TypicalSuppliers.ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.undo();
        assertEquals(versionedAddressBook, expectedAddressBook);
    }

    @Test
    public void undo_afterMultipleCommits_returnsToMostRecentCommit() {
        versionedAddressBook.addSupplier(TypicalSuppliers.ALICE);
        versionedAddressBook.commit();
        AddressBook expectedAddressBookFirstCommit = new AddressBook(versionedAddressBook);

        versionedAddressBook.addSupplier(TypicalSuppliers.BENSON);
        versionedAddressBook.commit();
        AddressBook expectedAddressBookSecondCommit = new AddressBook(versionedAddressBook);

        versionedAddressBook.addSupplier(TypicalSuppliers.CARL);
        versionedAddressBook.commit();

        // first undo goes to second commit
        versionedAddressBook.undo();
        assertEquals(versionedAddressBook, expectedAddressBookSecondCommit);

        // second undo goes to first commit
        versionedAddressBook.undo();
        assertEquals(versionedAddressBook, expectedAddressBookFirstCommit);
    }

    @Test
    public void undo_afterUnsavedChanges_removesUnsavedAndPreviousChanges() {
        AddressBook expectedAddressBook = new AddressBook(versionedAddressBook);
        versionedAddressBook.addSupplier(TypicalSuppliers.ALICE);
        versionedAddressBook.commit();

        Supplier p = new SupplierBuilder().withName("Erased Ignored").build();
        versionedAddressBook.addSupplier(p);
        versionedAddressBook.undo();

        assertEquals(versionedAddressBook, expectedAddressBook);
    }

    @Test
    public void redo_withoutUndo_throwsStateNotFoundException() {
        Assert.assertThrows(StateNotFoundException.class, () -> versionedAddressBook.redo());
    }

    @Test
    public void redo_afterOneUndo_redoChanges() {
        AddressBook expectedAddressBook = new AddressBook(versionedAddressBook);
        expectedAddressBook.addSupplier(TypicalSuppliers.ALICE);

        versionedAddressBook.addSupplier(TypicalSuppliers.ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.undo();
        versionedAddressBook.redo();
        assertEquals(versionedAddressBook, expectedAddressBook);
    }

    @Test
    public void redo_afterMultipleUndo_returnsToMostRecentUndo() {
        versionedAddressBook.addSupplier(TypicalSuppliers.ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.addSupplier(TypicalSuppliers.BENSON);
        versionedAddressBook.commit();
        AddressBook expectedAddressBookSecondCommit = new AddressBook(versionedAddressBook);

        versionedAddressBook.addSupplier(TypicalSuppliers.CARL);
        versionedAddressBook.commit();
        AddressBook expectedAddressBookThirdCommit = new AddressBook(versionedAddressBook);

        versionedAddressBook.undo();
        versionedAddressBook.undo();

        versionedAddressBook.redo();
        assertEquals(versionedAddressBook, expectedAddressBookSecondCommit);

        versionedAddressBook.redo();
        assertEquals(versionedAddressBook, expectedAddressBookThirdCommit);
    }

    @Test
    public void redo_afterUnsavedChanges_removesUnsavedChangesAndRedoPreviousChanges() {
        AddressBook expectedAddressBook = new AddressBook(versionedAddressBook);
        expectedAddressBook.addSupplier(TypicalSuppliers.ALICE);

        versionedAddressBook.addSupplier(TypicalSuppliers.ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.undo();

        Supplier p = new SupplierBuilder().withName("Erased Ignored").build();
        versionedAddressBook.addSupplier(p);

        versionedAddressBook.redo();

        assertEquals(versionedAddressBook, expectedAddressBook);
    }

    @Test
    public void commit_afterUndo_removesFutureHistory() {
        AddressBook expectedAddressBookAfterRewrite = new AddressBook(versionedAddressBook);
        expectedAddressBookAfterRewrite.addSupplier(TypicalSuppliers.ALICE);
        expectedAddressBookAfterRewrite.addSupplier(TypicalSuppliers.BENSON);
        expectedAddressBookAfterRewrite.addSupplier(TypicalSuppliers.DANIEL);

        AddressBook expectedAddressBookAfterUndoFromRewrite = new AddressBook(versionedAddressBook);
        expectedAddressBookAfterUndoFromRewrite.addSupplier(TypicalSuppliers.ALICE);
        expectedAddressBookAfterUndoFromRewrite.addSupplier(TypicalSuppliers.BENSON);

        versionedAddressBook.addSupplier(TypicalSuppliers.ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.addSupplier(TypicalSuppliers.BENSON);
        versionedAddressBook.commit();
        versionedAddressBook.addSupplier(TypicalSuppliers.CARL);
        versionedAddressBook.commit();

        // ensures the current state points to the most recent commit
        versionedAddressBook.undo();
        versionedAddressBook.addSupplier(TypicalSuppliers.DANIEL);
        versionedAddressBook.commit();
        assertEquals(versionedAddressBook, expectedAddressBookAfterRewrite);

        // ensures that current state is not added on top of deleted history
        versionedAddressBook.undo();
        assertEquals(versionedAddressBook, expectedAddressBookAfterUndoFromRewrite);

        // ensures that deleted history is inaccessible after undo from rewrite
        versionedAddressBook.redo();
        assertEquals(versionedAddressBook, expectedAddressBookAfterRewrite);
    }
}
