package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.good.Good;

/**
 * Panel containing the list of goods.
 */
public class GoodListPanel extends UiPart<Region> {
    private static final String FXML = "GoodListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GoodListPanel.class);

    @FXML
    private ListView<Good> goodListView;

    public GoodListPanel(ObservableList<Good> goodList) {
        super(FXML);
        goodListView.setItems(goodList);
        goodListView.setCellFactory(listView -> new GoodListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Good} using a {@code GoodInformation}.
     */
    class GoodListViewCell extends ListCell<Good> {
        @Override
        protected void updateItem(Good good, boolean empty) {
            super.updateItem(good, empty);

            if (empty || good == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new GoodInformation(good, getIndex() + 1).getRoot());
            }
        }
    }

}
