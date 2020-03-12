package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.good.Good;
import seedu.address.model.good.UniqueGoodList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameGood comparison)
 */
public class Inventory implements ReadOnlyInventory {

    private final UniqueGoodList goods;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        goods = new UniqueGoodList();
    }

    public Inventory() {
    }

    /**
     * Creates an Inventory using the Goods in the {@code toBeCopied}
     */
    public Inventory(ReadOnlyInventory toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the good list with {@code goods}.
     * {@code goods} must not contain duplicate goods.
     */
    public void setGoods(List<Good> goods) {
        this.goods.setGoods(goods);
    }

    /**
     * Resets the existing data of this {@code Inventory} with {@code newData}.
     */
    public void resetData(ReadOnlyInventory newData) {
        requireNonNull(newData);

        setGoods(newData.getGoodList());
    }

    //// good-level operations

    /**
     * Returns true if a good with the same identity as {@code good} exists in the address book.
     */
    public boolean hasGood(Good good) {
        requireNonNull(good);
        return goods.contains(good);
    }

    /**
     * Adds a good to the address book.
     * The good must not already exist in the address book.
     */
    public void addGood(Good p) {
        goods.add(p);
    }

    /**
     * Replaces the given good {@code target} in the list with {@code editedGood}.
     * {@code target} must exist in the address book.
     * The good identity of {@code editedGood} must not be the same as another existing good in the address book.
     */
    public void setGood(Good target, Good editedGood) {
        requireNonNull(editedGood);

        goods.setGood(target, editedGood);
    }

    /**
     * Removes {@code key} from this {@code Inventory}.
     * {@code key} must exist in the address book.
     */
    public void removeGood(Good key) {
        goods.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return goods.asUnmodifiableObservableList().size() + " goods";
        // TODO: refine later
    }

    @Override
    public ObservableList<Good> getGoodList() {
        return goods.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Inventory // instanceof handles nulls
                && goods.equals(((Inventory) other).goods));
    }

    @Override
    public int hashCode() {
        return goods.hashCode();
    }

}
