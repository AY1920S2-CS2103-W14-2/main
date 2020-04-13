package cs2103_w14_2.inventory.model.version;

/**
 * Represents a collection of the states of an object at different points in time.
 */
public interface Version<T> extends Versionable {
    public T getCurrentState();

    @Override
    void commit();

    @Override
    void undo() throws StateNotFoundException;

    @Override
    void redo() throws StateNotFoundException;
}

