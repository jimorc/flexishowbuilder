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
        // Used in switch cases to identify columns.
        // Cases must be constants, so can't use IPLColumn.ordinal().
        final int imageColumn = 0;              // same as IPLColumn.IMAGE_FILE_NAME.ordinal();
        final int titleColumn = 1;              // same as IPLColumn.IMAGE_TITLE.ordinal();
        final int personFullNameColumn = 2;     // same as IPLColumn.PERSON_FULL_NAME.ordinal();
        final int personFirstNameColumn = 3;    // same as IPLColumn.PERSON_FIRST_NAME.ordinal();
        final int personLastNameColumn = 4;     // same as IPLColumn.PERSON_LAST_NAME.ordinal();

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
            for (int col = 0; col < ipLine.getChildren().size(); col++) {
                IPLField ipField = (IPLField) ipLine.getChildren().get(col);
                switch (col) {
                    case imageColumn:
                        ipField.setFieldWidth(fieldWidths.getImageFileNameWidth());
                        break;
                    case titleColumn:
                        ipField.setFieldWidth(fieldWidths.getImageTitleWidth());
                        break;
                    case personFullNameColumn:
                        ipField.setFieldWidth(fieldWidths.getPersonFullNameWidth());
                        break;
                    case personFirstNameColumn:
                        ipField.setFieldWidth(fieldWidths.getPersonFirstNameWidth());
                        break;
                    case personLastNameColumn:
                        ipField.setFieldWidth(fieldWidths.getPersonLastNameWidth());
                        break;
                    default:
                        // handle more than 5 columns
                        break;
                }
            }
        }
    }
}
