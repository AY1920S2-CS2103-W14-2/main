package cs2103_w14_2.inventory.model;

import java.nio.file.Path;

import cs2103_w14_2.inventory.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    Path getInventoryFilePath();

    Path getTransactionHistoryFilePath();

}
