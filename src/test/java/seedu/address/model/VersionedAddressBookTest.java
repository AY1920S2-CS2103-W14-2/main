package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSuppliers.ALICE;
import static seedu.address.testutil.TypicalSuppliers.BENSON;
import static seedu.address.testutil.TypicalSuppliers.CARL;
import static seedu.address.testutil.TypicalSuppliers.DANIEL;

import org.junit.jupiter.api.Test;

import seedu.address.model.supplier.Supplier;
import seedu.address.testutil.SupplierBuilder;

public class VersionedAddressBookTest {

    private VersionedAddressBook versionedAddressBook = new VersionedAddressBook();

    @Test
    public void undo_withoutCommits_throwsStateNotFoundException() {
        assertThrows(StateNotFoundException.class, () -> versionedAddressBook.undo());
    }

    @Test
    public void undo_afterOneCommit_removeChanges() {
        AddressBook expectedAddressBook = new AddressBook(versionedAddressBook);

        versionedAddressBook.addSupplier(ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.undo();
        assertEquals(versionedAddressBook, expectedAddressBook);
    }

    @Test
    public void undo_afterMultipleCommits_returnsToMostRecentCommit() {
        versionedAddressBook.addSupplier(ALICE);
        versionedAddressBook.commit();
        AddressBook expectedAddressBookFirstCommit = new AddressBook(versionedAddressBook);

        versionedAddressBook.addSupplier(BENSON);
        versionedAddressBook.commit();
        AddressBook expectedAddressBookSecondCommit = new AddressBook(versionedAddressBook);

        versionedAddressBook.addSupplier(CARL);
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
        versionedAddressBook.addSupplier(ALICE);
        versionedAddressBook.commit();

        Supplier p = new SupplierBuilder().withName("Erased Ignored").build();
        versionedAddressBook.addSupplier(p);
        versionedAddressBook.undo();

        assertEquals(versionedAddressBook, expectedAddressBook);
    }

    @Test
    public void commit_afterUndo_removesFutureHistory() {
        AddressBook expectedAddressBookAfterRewrite = new AddressBook(versionedAddressBook);
        expectedAddressBookAfterRewrite.addSupplier(ALICE);
        expectedAddressBookAfterRewrite.addSupplier(BENSON);
        expectedAddressBookAfterRewrite.addSupplier(DANIEL);

        AddressBook expectedAddressBookAfterUndoFromRewrite = new AddressBook(versionedAddressBook);
        expectedAddressBookAfterUndoFromRewrite.addSupplier(ALICE);
        expectedAddressBookAfterUndoFromRewrite.addSupplier(BENSON);

        versionedAddressBook.addSupplier(ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.addSupplier(BENSON);
        versionedAddressBook.commit();
        versionedAddressBook.addSupplier(CARL);
        versionedAddressBook.commit();

        // ensures the current state points to the most recent commit
        versionedAddressBook.undo();
        versionedAddressBook.addSupplier(DANIEL);
        versionedAddressBook.commit();
        assertEquals(versionedAddressBook, expectedAddressBookAfterRewrite);

        // ensures that current state is not added on top of deleted history
        versionedAddressBook.undo();
        assertEquals(versionedAddressBook, expectedAddressBookAfterUndoFromRewrite);
    }
}
