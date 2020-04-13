package cs2103_w14_2.inventory.ui;

import java.io.IOException;

import cs2103_w14_2.inventory.model.offer.Offer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
        good.setText("Good: " + offer.getGoodName().toString());
        price.setText("Price: " + offer.getPrice().toString());
    }
}
