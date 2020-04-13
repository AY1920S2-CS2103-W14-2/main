package seedu.address.model;

import seedu.address.model.good.Good;
import seedu.address.model.version.LinearHistory;
import seedu.address.model.version.StateNotFoundException;
import seedu.address.model.version.Version;
import seedu.address.model.version.Versionable;

/**
 * An {@code Inventory} that keeps track of its history. Snapshots of its state are done based on external commands.
 */
public class VersionedInventory extends Inventory implements Versionable {
    private Version<Inventory> version;

    /**
     * Creates a VersionedInventory with an empty initial state.
     */
    public VersionedInventory() {
        super();
        version = new LinearHistory<>(new Inventory());
    }

    /**
     * Creates a VersionedInventory with an initial state containing the list of {@code Good} in the {@code toBeCopied}.
     */
    public VersionedInventory(ReadOnlyList<Good> toBeCopied) {
        super();
        version = new LinearHistory<>(new Inventory(toBeCopied));
        updateDisplayedGoods();
    }

    //=========== List Overwrite Operations =========================================================================

    @Override
    public void resetData(ReadOnlyList<Good> newData) {
        version.getCurrentState().resetData(newData);
        updateDisplayedGoods();
    }

    //=========== Good-Level Operations =========================================================================

    @Override
    public boolean hasGood(Good good) {
        return version.getCurrentState().hasGood(good);
    }


    @Override
    public int index(Good toFind) {
        return version.getCurrentState().index(toFind);
    }

    @Override
    public void addGood(Good p) {
        version.getCurrentState().addGood(p);
        updateDisplayedGoods();
    }

    @Override
    public void setGood(Good target, Good editedGood) {
        version.getCurrentState().setGood(target, editedGood);
        updateDisplayedGoods();
    }

    @Override
    public void removeGood(Good key) {
        version.getCurrentState().removeGood(key);
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

    //=========== Util Methods =========================================================================

    /**
     * Updates the list of suppliers to be shown in the UI.
     */
    private void updateDisplayedGoods() {
        super.resetData(version.getCurrentState());
    }
}
