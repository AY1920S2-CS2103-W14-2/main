package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.good.Good;

/**
 * An UI component that displays information of a {@code Good}.
 */
public class GoodInformation extends UiPart<Region> {

    private static final String FXML = "GoodInformation.fxml";

    public final Good good;

    @FXML
    private HBox goodPane;
    @FXML
    private Label goodName;
    @FXML
    private Label id;
    @FXML
    private Label goodQuantity;

    public GoodInformation(Good good, int displayedIndex) {
        super(FXML);
        this.good = good;
        id.setText(displayedIndex + ". ");
        goodName.setText(good.getGoodName().toString());
        goodQuantity.setText(good.getGoodQuantity().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GoodInformation)) {
            return false;
        }

        // state check
        GoodInformation information = (GoodInformation) other;
        return id.getText().equals(information.id.getText())
                && good.equals(information.good);
    }

}
