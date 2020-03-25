package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.good.Good;

/**
 * Unmodifiable view of an inventory
 */
public interface ReadOnlyInventory {

    /**
     * Returns an unmodifiable view of the goods list.
     * This list will not contain any duplicate goods.
     */
    ObservableList<Good> getGoodList();

}
