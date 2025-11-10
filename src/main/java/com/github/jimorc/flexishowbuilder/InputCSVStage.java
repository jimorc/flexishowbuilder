package com.github.jimorc.flexishowbuilder;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * InputCSVStage is the panel that displays lines from the InputCSV object.
 * Lines that are in error are highlighted in red, and a tooltip explains the errors.
 * A context menu allows the user to fix certain errors.
 * Images that are not found are highlighted in orange and the tooltip explains the error.
 * Images that are not JPEGs are highlighted in yellow and the tooltp explains the error.
 */
public class InputCSVStage extends FlexiStage {
    private InputCSV csv;

    /**
     * InputCSVStage constructor.
     * @param csv the InputCSV object containing the lines from the CSV file.
     */
    public InputCSVStage(InputCSV csv) {
        this.csv = csv;
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
                    HBox lineHBox = createLineHBox(ipl);
                    box.getChildren().add(lineHBox);
                    break;
                default:
                // handle error.
            }
        }
        IPLFieldWidths fieldWidths = setMaxFieldWidths(box);
        setFieldWidths(padding, box, fieldWidths);
        return box;
    }

    private IPLFieldWidths setMaxFieldWidths(VBox box) {
        // Used in switch cases to identify columns.
        // Cases must be constants, so can't use IPLColumn.ordinal().
        final int imageColumn = 0;              // same as IPLColumn.IMAGE_FILE_NAME.ordinal();
        final int titleColumn = 1;              // same as IPLColumn.IMAGE_TITLE.ordinal();
        final int personFullNameColumn = 2;     // same as IPLColumn.PERSON_FULL_NAME.ordinal();
        final int personFirstNameColumn = 3;    // same as IPLColumn.PERSON_FIRST_NAME.ordinal();
        final int personLastNameColumn = 4;     // same as IPLColumn.PERSON_LAST_NAME.ordinal();

        IPLFieldWidths fieldWidths = new IPLFieldWidths();
        for (Node line : box.getChildren()) {
            switch (line) {
                case HBox hbox:
                    for (Node field : hbox.getChildren()) {
                        switch (field) {
                            case Text text:
                                int col = hbox.getChildren().indexOf(field);
                                switch (col) {
                                    case imageColumn: // image
                                        fieldWidths.setMaxImageFileNameWidth(text.getLayoutBounds().getWidth());
                                        break;
                                    case titleColumn: // title
                                        fieldWidths.setMaxImageTitleWidth(text.getLayoutBounds().getWidth());
                                        break;
                                    case personFullNameColumn: // person full name
                                        fieldWidths.setMaxPersonFullNameWidth(text.getLayoutBounds().getWidth());
                                        break;
                                    case personFirstNameColumn: // person first name
                                        fieldWidths.setMaxPersonFirstNameWidth(text.getLayoutBounds().getWidth());
                                        break;
                                    case personLastNameColumn: // person last name
                                        fieldWidths.setMaxPersonLastNameWidth(text.getLayoutBounds().getWidth());
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
        return fieldWidths;
    }

    private void setFieldWidths(final int padding, VBox box, IPLFieldWidths fieldWidths) {
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
                            case Text text:
                                int col = hbox.getChildren().indexOf(field);
                                switch (col) {
                                    case imageColumn: // image
                                        text.setWrappingWidth(fieldWidths.getImageFileNameWidth() + padding);
                                        break;
                                    case titleColumn: // title
                                        text.setWrappingWidth(fieldWidths.getImageTitleWidth() + padding);
                                        break;
                                    case personFullNameColumn: // person full name
                                        text.setWrappingWidth(fieldWidths.getPersonFullNameWidth() + padding);
                                        break;
                                    case personFirstNameColumn: // person first name
                                        text.setWrappingWidth(fieldWidths.getPersonFirstNameWidth() + padding);
                                        break;
                                    case personLastNameColumn: // person last name
                                        text.setWrappingWidth(fieldWidths.getPersonLastNameWidth() + padding);
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

    private HBox createLineHBox(ImageAndPersonLine line) {
        final int spacing = 5;
        HBox box = new HBox();
        box.setSpacing(spacing);
        for (int col = 0; col < line.length(); col++) {
            Text fieldText = new Text(line.field(col));
            // set style based on error status.
            box.getChildren().add(fieldText);
        }
        return box;
    }
}
