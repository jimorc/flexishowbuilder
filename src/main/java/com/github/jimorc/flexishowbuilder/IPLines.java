package com.github.jimorc.flexishowbuilder;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

/**
 * IPLines is a VBox that displays multiple IPLine objects.
 */
public class IPLines extends VBox {
    private static final Insets PADDING = new Insets(6.0);
    private static final int SPACING = 5;
    private CSVLine[] csvLines;

    /**
     * Constructor.
     * @param lines the CSV lines to display.
     * @param fieldWidths the IPLFieldWidths object to update.
     */
    public IPLines(CSVLine[] lines, IPLFieldWidths fieldWidths) {
        super();
        setSpacing(SPACING);
        setPadding(PADDING);
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
}
