package seedu.address.model;

/**
 * Represents an object that is able to save its current state and return to the previous state.
 */
public interface Versionable {

    /**
     * Saves the current state.
     */
    public void commit();

    /**
     * Returns to the previous saved state.
     * @throws StateNotFoundException if currently at the first saved state.
     */
    public void undo() throws StateNotFoundException;

    /**
     * Returns to the previously undone state.
     * @throws StateNotFoundException if currently at the latest state.
     */
    public void redo() throws StateNotFoundException;
}
