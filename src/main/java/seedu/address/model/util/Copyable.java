package seedu.address.model.util;

/**
 * Represents an object that can produce its independent copy.
 */
public interface Copyable<T> {
    public T copy();
}
