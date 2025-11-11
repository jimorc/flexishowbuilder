package com.github.jimorc.flexishowbuilder;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;

/**
 * InputCSVStage is the panel that displays lines from the InputCSV object.
 * Lines that are in error are highlighted in red, and a tooltip explains the errors.
 * A context menu allows the user to fix certain errors.
 * Images that are not found are highlighted in orange and the tooltip explains the error.
 * Images that are not JPEGs are highlighted in yellow and the tooltp explains the error.
 */
public class InputCSVStage extends FlexiStage {
    private IPLFieldWidths fieldWidths;

    /**
     * InputCSVStage constructor.
     * @param csv the InputCSV object containing the lines from the CSV file.
     */
    public InputCSVStage(InputCSV csv) {
        fieldWidths = new IPLFieldWidths();
        IPLines linesBox = new IPLines(this, csv.getLines(), fieldWidths);
        setNewScrollPane(linesBox);
    }

    /**
     * Update stage with new contents for ScrollPane.
     * @param linesBox the box containing all CSV lines.
     */
    public void setNewScrollPane(IPLines linesBox) {
        ScrollPane sPane = new ScrollPane();
        sPane.setContent(linesBox);
        Scene scene = new Scene(sPane);
        this.setScene(scene);
    }
}
