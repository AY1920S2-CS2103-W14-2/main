package cs2103_w14_2.inventory.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import cs2103_w14_2.inventory.model.good.Good;

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
        warningLowQuantity(good);
    }

    /**
     * Sets an alert background for good with quantity lower or equals to threshold.
     * @param good refer to the good that need to be check for its quantity.
     */
    private void warningLowQuantity(Good good) {
        if (good.isNoMoreThanThresholdQuantity()) {
            goodQuantity.setStyle("-fx-background-color: red");
        } else {
            goodQuantity.setStyle("");
        }
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
