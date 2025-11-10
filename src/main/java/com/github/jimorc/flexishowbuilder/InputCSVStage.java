package com.github.jimorc.flexishowbuilder;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * InputCSVStage is the panel that displays lines from the InputCSV object.
 * Lines that are in error are highlighted in red, and a tooltip explains the errors.
 * A context menu allows the user to fix certain errors.
 * Images that are not found are highlighted in orange and the tooltip explains the error.
 * Images that are not JPEGs are highlighted in yellow and the tooltp explains the error.
 */
public class InputCSVStage extends FlexiStage {
    private InputCSV csv;
    private IPLFieldWidths fieldWidths;

    /**
     * InputCSVStage constructor.
     * @param csv the InputCSV object containing the lines from the CSV file.
     */
    public InputCSVStage(InputCSV csv) {
        this.csv = csv;
        fieldWidths = new IPLFieldWidths();
        VBox lineBox = createLineBox();
        ScrollPane sPane = new ScrollPane();
        sPane.setContent(lineBox);
        Scene scene = new Scene(sPane);
        this.setScene(scene);
    }

    private VBox createLineBox() {
        final int spacing = 5;
        final int padding = 10;
        VBox box = new VBox();
        box.setPadding(new Insets(padding));
        box.setSpacing(spacing);

        CSVLine[] lines = csv.getLines();
        for (CSVLine line : lines) {
            switch (line) {
                case ImageAndPersonLine ipl:
                    IPLine ipLine = new IPLine(ipl, fieldWidths);
                    box.getChildren().add(ipLine);
                    break;
                default:
                // handle error.
            }
        }
        setFieldWidths(padding, box);
        return box;
    }

    private void setFieldWidths(final int padding, VBox box) {
        // Used in switch cases to identify columns.
        // Cases must be constants, so can't use IPLColumn.ordinal().
        final int imageColumn = 0;              // same as IPLColumn.IMAGE_FILE_NAME.ordinal();
        final int titleColumn = 1;              // same as IPLColumn.IMAGE_TITLE.ordinal();
        final int personFullNameColumn = 2;     // same as IPLColumn.PERSON_FULL_NAME.ordinal();
        final int personFirstNameColumn = 3;    // same as IPLColumn.PERSON_FIRST_NAME.ordinal();
        final int personLastNameColumn = 4;     // same as IPLColumn.PERSON_LAST_NAME.ordinal();

        for (Node line : box.getChildren()) {
            switch (line) {
                case HBox hbox:
                    for (Node field : hbox.getChildren()) {
                        switch (field) {
                            case IPLField iplF:
                                int col = hbox.getChildren().indexOf(field);
                                switch (col) {
                                    case imageColumn: // image
                                        iplF.setFieldWidth(fieldWidths.getImageFileNameWidth() + padding);
                                        break;
                                    case titleColumn: // title
                                        iplF.setFieldWidth(fieldWidths.getImageTitleWidth() + padding);
                                        break;
                                    case personFullNameColumn: // person full name
                                        iplF.setFieldWidth(fieldWidths.getPersonFullNameWidth() + padding);
                                        break;
                                    case personFirstNameColumn: // person first name
                                        iplF.setFieldWidth(fieldWidths.getPersonFirstNameWidth() + padding);
                                        break;
                                    case personLastNameColumn: // person last name
                                        iplF.setFieldWidth(fieldWidths.getPersonLastNameWidth() + padding);
                                        break;
                                    default:
                                    // handle error.
                                }
                                break;
                            default:
                            // handle error.
                        }
                    }
                    break;
                default:
                // handle error.
            }
        }
    }
}
