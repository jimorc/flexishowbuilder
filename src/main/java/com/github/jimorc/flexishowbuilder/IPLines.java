package com.github.jimorc.flexishowbuilder;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

/**
 * IPLines is a VBox that displays multiple IPLine objects.
 */
public class IPLines extends VBox {
    private static final Insets PADDING = new Insets(6.0);
    private static final int SPACING = 5;

    /**
     * Constructor.
     * @param csv the InputCSV to display.
     * @param fieldWidths the IPLFieldWidths object to update.
     */
    public IPLines(InputCSV csv, IPLFieldWidths fieldWidths) {
        super();
        setSpacing(SPACING);
        setPadding(PADDING);
        for (int row = 0; row < csv.getLines().length; row++) {
            CSVLine csvLine = csv.getLine(row);
            IPLine ipLine = new IPLine(csvLine, fieldWidths);
            getChildren().add(ipLine);
        }
        for (int i = 0; i < getChildren().size(); i++) {
            IPLine ipLine = (IPLine) getChildren().get(i);
            ipLine.setFieldWidths(fieldWidths);
        }
    }
}
