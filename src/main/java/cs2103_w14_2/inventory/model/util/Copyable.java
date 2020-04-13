package cs2103_w14_2.inventory.model.util;

/**
 * Represents an object that can produce its independent copy.
 */
public interface Copyable<T> {
    public T copy();
}
