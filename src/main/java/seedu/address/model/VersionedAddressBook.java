package seedu.address.model;

import seedu.address.model.supplier.Supplier;
import seedu.address.model.version.LinearHistory;
import seedu.address.model.version.StateNotFoundException;
import seedu.address.model.version.Version;
import seedu.address.model.version.Versionable;

/**
 * An {@code AddressBook} that keeps track of its history. Snapshots of its state are done based on external commands.
 */
public class VersionedAddressBook extends AddressBook implements Versionable {
    private Version<AddressBook> version;

    /**
     * Creates a VersionedAddressBook with an empty initial state.
     */
    public VersionedAddressBook() {
        super();
        version = new LinearHistory<>(new AddressBook());
    }

    /**
     * Creates a VersionedAddressBook with an initial state containing the list of {@code Supplier} in the
     * {@code toBeCopied}.
     */
    public VersionedAddressBook(ReadOnlyList<Supplier> toBeCopied) {
        super();
        version = new LinearHistory<>(new AddressBook(toBeCopied));
        updateDisplayedSuppliers();
    }

    //=========== List Overwrite Operations =========================================================================

    @Override
    public void resetData(ReadOnlyList<Supplier> newData) {
        version.getCurrentState().resetData(newData);
        updateDisplayedSuppliers();
    }

    //=========== Supplier-Level Operations =========================================================================

    @Override
    public boolean hasSupplier(Supplier supplier) {
        return version.getCurrentState().hasSupplier(supplier);
    }


    @Override
    public void addSupplier(Supplier p) {
        version.getCurrentState().addSupplier(p);
        updateDisplayedSuppliers();
    }

    @Override
    public void setSupplier(Supplier target, Supplier editedSupplier) {
        version.getCurrentState().setSupplier(target, editedSupplier);
        updateDisplayedSuppliers();
    }

    @Override
    public void removeSupplier(Supplier key) {
        version.getCurrentState().removeSupplier(key);
        updateDisplayedSuppliers();
    }

    //=========== Versioning Methods =========================================================================

    @Override
    public void commit() {
        version.commit();
    }

    @Override
    public void undo() throws StateNotFoundException {
        version.undo();
        updateDisplayedSuppliers();
    }

    @Override
    public void redo() throws StateNotFoundException {
        version.redo();
        updateDisplayedSuppliers();
    }

    //=========== Util Methods =========================================================================

    /**
     * Updates the list of suppliers to be shown in the UI.
     */
    private void updateDisplayedSuppliers() {
        super.resetData(version.getCurrentState());
    }
}
