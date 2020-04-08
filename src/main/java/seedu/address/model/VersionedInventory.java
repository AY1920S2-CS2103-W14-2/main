package seedu.address.model;

import seedu.address.model.good.Good;
import seedu.address.model.version.LinearHistory;
import seedu.address.model.version.StateNotFoundException;
import seedu.address.model.version.Version;

/**
 * An {@code Inventory} that keeps track of its history. Snapshots of its state are done based on external commands.
 */
public class VersionedInventory extends Inventory implements Version<Inventory> {
    private Version<Inventory> version;

    public VersionedInventory() {
        version = new LinearHistory<>(new Inventory());
    }

    /**
     * Creates a VersionedInventory with an initial state containing the {@code Good}s in the {@code toBeCopied}.
     */
    public VersionedInventory(ReadOnlyList<Good> toBeCopied) {
        version = new LinearHistory<>(new Inventory(toBeCopied));
        updateDisplayedGoods();
    }

    //=========== List Overwrite Operations =========================================================================

    /**
     * Resets the existing data in the current state with {@code newData}.
     */
    public void resetData(ReadOnlyList<Good> newData) {
        getCurrentState().resetData(newData);
        updateDisplayedGoods();
    }

    //=========== Good-Level Operations =========================================================================

    /**
     * Returns true if a good with the same identity as {@code good} exists in the address book.
     */
    public boolean hasGood(Good good) {
        return getCurrentState().hasGood(good);
    }

    public int index(Good toFind) {
        return getCurrentState().index(toFind);
    }

    /**
     * Adds a good to the current state.
     * The good must not already exist in the current state.
     */
    public void addGood(Good p) {
        getCurrentState().addGood(p);
        updateDisplayedGoods();
    }

    /**
     * Replaces the given good {@code target} in the current state with {@code editedGood}.
     * {@code target} must exist in the current state.
     * The good identity of {@code editedGood} must not be the same as another existing good in the current state.
     */
    public void setGood(Good target, Good editedGood) {
        getCurrentState().setGood(target, editedGood);
        updateDisplayedGoods();
    }

    /**
     * Removes {@code key} from this {@code Inventory}.
     * {@code key} must exist in the current state.
     */
    public void removeGood(Good key) {
        getCurrentState().removeGood(key);
        updateDisplayedGoods();
    }

    //=========== Versioning Methods =========================================================================

    @Override
    public void commit() {
        version.commit();
    }

    @Override
    public void undo() throws StateNotFoundException {
        version.undo();
        updateDisplayedGoods();
    }

    @Override
    public void redo() throws StateNotFoundException {
        version.redo();
        updateDisplayedGoods();
    }

    @Override
    public Inventory getCurrentState() {
        return version.getCurrentState();
    }

    //=========== Util Methods =========================================================================

    /**
     * Updates the list of suppliers to be shown in the UI.
     */
    private void updateDisplayedGoods() {
        super.resetData(getCurrentState());
    }
}
