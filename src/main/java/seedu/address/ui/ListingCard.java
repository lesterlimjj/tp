package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.listing.Listing;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class ListingCard extends UiPart<Region> {

    private static final String FXML = "ListingListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Listing listing;
    @FXML
    private StackPane ownerListPanelPlaceholder;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label postalCode;
    @FXML
    private Label houseNumber;
    @FXML
    private Label unitNumber;
    @FXML
    private Label name;
    @FXML
    private Label priceRange;

    /**
     * Creates a {@code PropertyCode} with the given {@code Property} and index to display.
     */
    public ListingCard(Listing listing, int displayedIndex) {
        super(FXML);
        this.listing = listing;
        id.setText(displayedIndex + ". ");
        postalCode.setText("Postal Code: " + listing.getPostalCode());

        houseNumber.setVisible(listing.getHouseNumber() != null);
        houseNumber.setManaged(listing.getHouseNumber() != null);
        houseNumber.setText("House Number: " + listing.getHouseNumber());

        unitNumber.setVisible(listing.getUnitNumber() != null);
        unitNumber.setManaged(listing.getUnitNumber() != null);
        unitNumber.setText("Unit Number: " + listing.getUnitNumber());


        name.setVisible(listing.getPropertyName() != null);
        name.setVisible(listing.getPropertyName() != null);
        if (listing.getPropertyName() != null) {
            name.setText(listing.getPropertyName().toString());
        }

        priceRange.setText(listing.getPriceRange().toString());
    }
}


