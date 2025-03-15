package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of tags.
 */
public class TagListPanel extends UiPart<Region> {
    private static final String FXML = "TagListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TagListPanel.class);

    @FXML
    private FlowPane tags; // Use FlowPane instead of ListView

    /**
     * Creates a {@code TagListPanel} with the given {@code ObservableList}.
     */
    public TagListPanel(ObservableMap<String, Tag> tagList) {
        super(FXML);
        tagList.addListener((MapChangeListener<String, Tag>) change -> updateTags(tagList));
        updateTags(tagList); // Update the FlowPane with the tags
    }

    /**
     * Updates the FlowPane with the given list of tags.
     */
    private void updateTags(ObservableMap<String, Tag> tagList) {
        tags.getChildren().clear(); // Clear existing tags
        for (Tag tag : tagList.values()) {
            Label tagLabel = new Label(tag.tagName);
            tags.getChildren().add(tagLabel); // Add each tag as a Label
        }
    }
}
