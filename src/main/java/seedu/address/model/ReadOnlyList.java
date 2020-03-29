package seedu.address.model;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a list of objects.
 */
public interface ReadOnlyList<T> {

    /**
     * Returns an unmodifiable view of the object list.
     * This list will not contain any duplicate object.
     */
    ObservableList<T> getReadOnlyList();
}
