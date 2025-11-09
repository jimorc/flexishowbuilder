package com.github.jimorc.flexishowbuilder;

import javafx.geometry.Insets;
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
        return box;
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
