package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.UniqueSupplierList;

/**
 * An {@code AddressBook} that keeps track of its history. Snapshots of its state are done based on external commands.
 */
public class VersionedAddressBook extends AddressBook implements Version<AddressBook> {
    private AddressBook currentState;
    private int statePointer;
    private List<AddressBook> stateList;

    public VersionedAddressBook() {
        statePointer = -1;
        stateList = new ArrayList<>();
        currentState = new AddressBook();
        commit();
    }

    /**
     * Creates a VersionedAddressBook using the {@code Supplier}s in the {@code toBeCopied}.
     */
    public VersionedAddressBook(ReadOnlyList<Supplier> toBeCopied) {
        statePointer = -1;
        stateList = new ArrayList<>();
        currentState = new AddressBook(toBeCopied);
        commit();
    }

    //=========== List Overwrite Operations =========================================================================

    /**
     * Replaces the contents of the current state with {@code suppliers}.
     * {@code suppliers} must not contain duplicate suppliers.
     */
    public void setSuppliers(List<Supplier> suppliers) {
        currentState.setSuppliers(suppliers);
    }

    /**
     * Resets the existing data of this {@code VersionedAddressBook} with {@code newData}.
     * Resets the history to an empty state as well.
     */
    public void resetData(ReadOnlyList<Supplier> newData) {
        setSuppliers(newData.getReadOnlyList());
    }

    //=========== Supplier-Level Operations =========================================================================

    /**
     * Returns true if a supplier with the same identity as {@code supplier} exists in the current state.
     */
    public boolean hasSupplier(Supplier supplier) {
        return currentState.hasSupplier(supplier);
    }

    /**
     * Adds a supplier to the current state.
     * The supplier must not already exist in the current state.
     */
    public void addSupplier(Supplier p) {
        currentState.addSupplier(p);
    }

    /**
     * Replaces the given supplier {@code target} in the current state with {@code editedSupplier}.
     * {@code target} must exist in the current state.
     * The supplier identity of {@code editedSupplier} must not be the same as another existing supplier in the
     * current state.
     */
    public void setSupplier(Supplier target, Supplier editedSupplier) {
        currentState.setSupplier(target, editedSupplier);
    }

    /**
     * Removes {@code key} from the current state.
     * {@code key} must exist in the current state.
     */
    public void removeSupplier(Supplier key) {
        currentState.removeSupplier(key);
    }

    //=========== Versioning Methods =========================================================================

    @Override
    public void commit() {
        AddressBook i = new AddressBook(getCurrentState());
        stateList = stateList.subList(0, statePointer + 1);
        stateList.add(i);
        statePointer++;
    }

    @Override
    public void undo() throws StateNotFoundException {
        if (statePointer == 0) {
            throw new StateNotFoundException();
        }

        statePointer--;
        currentState.resetData(stateList.get(statePointer));
    }

    @Override
    public AddressBook getCurrentState() {
        return currentState;
    }

    //=========== Util Methods =========================================================================

    @Override
    public String toString() {
        return currentState.toString();
        // TODO: refine later
    }

    @Override
    public ObservableList<Supplier> getReadOnlyList() {
        return currentState.getReadOnlyList();
    }

    @Override
    protected UniqueSupplierList getSuppliers() {
        return currentState.getSuppliers();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VersionedAddressBook // instanceof handles nulls
                && currentState.equals(((VersionedAddressBook) other).currentState))
                || (other instanceof AddressBook
                && currentState.equals((AddressBook) other));
    }

    @Override
    public int hashCode() {
        return currentState.hashCode();
    }
}
