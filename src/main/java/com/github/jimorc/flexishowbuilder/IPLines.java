package com.github.jimorc.flexishowbuilder;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

/**
 * IPLines is a VBox that displays multiple IPLine objects.
 */
public class IPLines extends VBox {
    private static final int HEADER_LINE_NUMBER = 0;
    private static final Insets PADDING = new Insets(6.0);
    private static final int SPACING = 5;
    private CSVLine[] csvLines;
    private InputCSVStage stage;

    /**
     * Constructor.
     * @param lines the CSV lines to display.
     * @param fieldWidths the IPLFieldWidths object to update.
     */
    public IPLines(InputCSVStage iStage, CSVLine[] lines, IPLFieldWidths fieldWidths) {
        super();
        setSpacing(SPACING);
        setPadding(PADDING);
        stage = iStage;
        csvLines = lines;
        IPLine header = new IPLine(csvLines[0], fieldWidths, this, 0);
        header.clearBackgrounds();
        header.setHeaderStyle();
        getChildren().add(header);
        for (int row = 1; row < csvLines.length; row++) {
            CSVLine csvLine = csvLines[row];
            IPLine ipLine = new IPLine(csvLine, fieldWidths, this, row);
            getChildren().add(ipLine);
        }
        for (int i = 0; i < getChildren().size(); i++) {
            IPLine ipLine = (IPLine) getChildren().get(i);
            ipLine.setFieldWidths(fieldWidths);
        }
    }

    /**
     * Delete a line from the CSV lines.
     * @param lineNumber the number of the line to delete.
     */
    public void deleteLine(int lineNumber) {
        if (lineNumber <= HEADER_LINE_NUMBER || lineNumber > csvLines.length) {
            return;
        }
        CSVLine[] lines = new CSVLine[csvLines.length - 1];
        for (int i = 0, j = 0; i < csvLines.length; i++) {
            if (i == lineNumber) {
                continue;
            }
            lines[j++] = csvLines[i];
        }
        stage.setNewScrollPane(new IPLines(stage, lines, new IPLFieldWidths()));
    }

}
