package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.UniqueSupplierList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameSupplier comparison)
 */
public class AddressBook implements ReadOnlyList<Supplier> {

    private final UniqueSupplierList suppliers;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        suppliers = new UniqueSupplierList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Suppliers in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyList<Supplier> toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the supplier list with {@code suppliers}.
     * {@code suppliers} must not contain duplicate suppliers.
     */
    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers.setSuppliers(suppliers);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyList<Supplier> newData) {
        requireNonNull(newData);

        setSuppliers(newData.getReadOnlyList());
    }

    //// supplier-level operations

    /**
     * Returns true if a supplier with the same identity as {@code supplier} exists in the address book.
     */
    public boolean hasSupplier(Supplier supplier) {
        requireNonNull(supplier);
        return suppliers.contains(supplier);
    }

    /**
     * Adds a supplier to the address book.
     * The supplier must not already exist in the address book.
     */
    public void addSupplier(Supplier p) {
        suppliers.add(p);
    }

    /**
     * Replaces the given supplier {@code target} in the list with {@code editedSupplier}.
     * {@code target} must exist in the address book.
     * The supplier identity of {@code editedSupplier} must not be the same as another
     * existing supplier in the address book.
     */
    public void setSupplier(Supplier target, Supplier editedSupplier) {
        requireNonNull(editedSupplier);

        suppliers.setSupplier(target, editedSupplier);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeSupplier(Supplier key) {
        suppliers.remove(key);
    }

    protected UniqueSupplierList getSuppliers() {
        return suppliers;
    }

    //// util methods

    @Override
    public String toString() {
        return suppliers.asUnmodifiableObservableList().size() + " suppliers";
        // TODO: refine later
    }

    @Override
    public ObservableList<Supplier> getReadOnlyList() {
        return suppliers.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && getSuppliers().equals(((AddressBook) other).getSuppliers()));
    }

    @Override
    public int hashCode() {
        return suppliers.hashCode();
    }
}
