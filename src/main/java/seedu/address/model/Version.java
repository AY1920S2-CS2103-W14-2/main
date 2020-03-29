package seedu.address.model;

/**
 * Represents a collection of the states of an object at different points in time.
 */
public interface Version<T> extends Versionable {
    public T getCurrentState();
}

