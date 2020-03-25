package seedu.address.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.model.offer.Offer;

/**
 * A box containing the details of an offer.
 */
public class OfferCard extends VBox {

    private static final String FXML = "/view/OfferCard.fxml";

    public final Offer offer;

    @FXML
    private Label good;
    @FXML
    private Label price;

    public OfferCard(Offer offer) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource(FXML));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.offer = offer;
        good.setText("Good: " + offer.getGood().toString());
        price.setText("Price: " + offer.getPrice().toString());
    }
}
