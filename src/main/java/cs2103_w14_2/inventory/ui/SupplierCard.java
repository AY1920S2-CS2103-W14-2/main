package cs2103_w14_2.inventory.ui;

import java.util.Comparator;

import cs2103_w14_2.inventory.model.supplier.Supplier;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * An UI component that displays information of a {@code Supplier}.
 */
public class SupplierCard extends UiPart<Region> {

    private static final String FXML = "SupplierListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Supplier supplier;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private VBox offers;

    public SupplierCard(Supplier supplier, int displayedIndex) {
        super(FXML);
        this.supplier = supplier;
        id.setText(displayedIndex + ". ");
        name.setText(supplier.getName().fullName);
        phone.setText(supplier.getPhone().value);
        address.setText(supplier.getAddress().value);
        email.setText(supplier.getEmail().value);
        supplier.getOffers().stream()
                .sorted(Comparator.comparing(offer -> offer.getGoodName().toString()))
                .forEach(offer -> offers.getChildren().add(new OfferCard(offer)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SupplierCard)) {
            return false;
        }

        // state check
        SupplierCard card = (SupplierCard) other;
        return id.getText().equals(card.id.getText())
                && supplier.equals(card.supplier);
    }
}
