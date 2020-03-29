package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.good.Good;
import seedu.address.model.good.UniqueGoodList;

/**
 * An {@code Inventory} that keeps track of its history. Snapshots of its state are done based on external commands.
 */
public class VersionedInventory extends Inventory implements Version<Inventory> {

    private List<Inventory> stateList;
    private Inventory currentState;
    private int statePointer;

    public VersionedInventory() {
        statePointer = -1;
        stateList = new ArrayList<>();
        currentState = new Inventory();
        commit();
    }

    /**
     * Creates a VersionedInventory using the {@code Good}s in the {@code toBeCopied}.
     */
    public VersionedInventory(ReadOnlyList<Good> toBeCopied) {
        statePointer = -1;
        stateList = new ArrayList<>();
        currentState = new Inventory(toBeCopied);
        commit();
    }

    //=========== List Overwrite Operations =========================================================================

    /**
     * Replaces the contents of the goods list in current state with {@code goods}.
     * {@code goods} must not contain duplicate goods.
     */
    public void setGoods(List<Good> goods) {
        getCurrentState().setGoods(goods);
    }

    public int index(Good toFind) {
        return getCurrentState().index(toFind);
    }

    /**
     * Resets the existing data in the current state with {@code newData}.
     */
    public void resetData(ReadOnlyList<Good> newData) {
        setGoods(newData.getReadOnlyList());
    }

    //=========== Good-Level Operations =========================================================================

    /**
     * Returns true if a good with the same identity as {@code good} exists in the address book.
     */
    public boolean hasGood(Good good) {
        return getCurrentState().hasGood(good);
    }

    /**
     * Adds a good to the current state.
     * The good must not already exist in the current state.
     */
    public void addGood(Good p) {
        getCurrentState().addGood(p);
    }

    /**
     * Replaces the given good {@code target} in the current state with {@code editedGood}.
     * {@code target} must exist in the current state.
     * The good identity of {@code editedGood} must not be the same as another existing good in the current state.
     */
    public void setGood(Good target, Good editedGood) {
        getCurrentState().setGood(target, editedGood);
    }

    /**
     * Removes {@code key} from this {@code Inventory}.
     * {@code key} must exist in the current state.
     */
    public void removeGood(Good key) {
        getCurrentState().removeGood(key);
    }

    //=========== Versioning Methods =========================================================================

    @Override
    public void commit() {
        Inventory i = new Inventory(getCurrentState());
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
    public Inventory getCurrentState() {
        return currentState;
    }

    //=========== Util Methods =========================================================================

    @Override
    public String toString() {
        return getCurrentState().toString();
        // TODO: refine later
    }

    @Override
    public ObservableList<Good> getReadOnlyList() {
        return getCurrentState().getReadOnlyList();
    }

    @Override
    protected UniqueGoodList getGoods() {
        return getCurrentState().getGoods();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VersionedInventory // instanceof handles nulls
                && getCurrentState().equals(((VersionedInventory) other).getCurrentState()))
                || (other instanceof Inventory // instanceof handles nulls
                && getCurrentState().equals(((Inventory) other)));
    }

    @Override
    public int hashCode() {
        return getCurrentState().hashCode();
    }
}
