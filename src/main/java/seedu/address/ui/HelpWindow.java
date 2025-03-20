package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddListingCommand;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.AddPreferenceCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListListingCommand;
import seedu.address.logic.commands.ListPersonCommand;
import seedu.address.logic.commands.ListTagCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s2-cs2103-f08-3.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "For more help, visit: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private GridPane commandGrid;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);

        String[][] helpTexts = getHelpMessages();

        // Add the help text to the GridPane
        for (int i = 0; i < helpTexts.length; i++) {

            Label commandText = new Label(helpTexts[i][0]);
            Label descriptionText = new Label(helpTexts[i][1]);

            commandText.getStyleClass().add("text-table-row");
            descriptionText.getStyleClass().add("text-table-row");

            commandGrid.addRow(i + 1,
                    commandText,
                    descriptionText);
        }
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application
     *             Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

    private String[][] getHelpMessages() {
        // Add the command summary to the GridPane
        String[][] helpTexts = {
                {AddPersonCommand.COMMAND_WORD, AddPersonCommand.MESSAGE_USAGE},
                {AddListingCommand.COMMAND_WORD, AddListingCommand.MESSAGE_USAGE},
                {AddTagCommand.COMMAND_WORD, AddTagCommand.MESSAGE_USAGE},
                {AddPreferenceCommand.COMMAND_WORD, AddPreferenceCommand.MESSAGE_USAGE},
                {ListPersonCommand.COMMAND_WORD, ListPersonCommand.MESSAGE_USAGE},
                {ListListingCommand.COMMAND_WORD, ListListingCommand.MESSAGE_USAGE},
                {ListTagCommand.COMMAND_WORD, ListTagCommand.MESSAGE_USAGE},
                {FindPersonCommand.COMMAND_WORD, FindPersonCommand.MESSAGE_USAGE},
                {HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE},
                {ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_USAGE},
        };

        return helpTexts;
    }
}
