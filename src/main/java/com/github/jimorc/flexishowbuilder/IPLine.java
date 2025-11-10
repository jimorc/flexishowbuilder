package com.github.jimorc.flexishowbuilder;

import javafx.scene.layout.HBox;

/**
 * IPLine is an HBox that displays a CSVLine as IPLFields.
 */
public class IPLine extends HBox {
    private CSVLine line;

    /**
     * Constructor.
     * @param line the CSVLine to display.
     */
    public IPLine(CSVLine line, IPLFieldWidths fieldWidths) {
        // Used in switch cases to identify columns.
        // Cases must be constants, so can't use IPLColumn.ordinal().
        final int imageColumn = 0;              // same as IPLColumn.IMAGE_FILE_NAME.ordinal();
        final int titleColumn = 1;              // same as IPLColumn.IMAGE_TITLE.ordinal();
        final int personFullNameColumn = 2;     // same as IPLColumn.PERSON_FULL_NAME.ordinal();
        final int personFirstNameColumn = 3;    // same as IPLColumn.PERSON_FIRST_NAME.ordinal();
        final int personLastNameColumn = 4;     // same as IPLColumn.PERSON_LAST_NAME.ordinal();

        super();
        this.line = line;
        final int spacing = 5;
        setSpacing(spacing);
        for (int col = 0; col < this.line.length(); col++) {
            IPLField ipF = new IPLField(line.field(col));
            getChildren().add(ipF);
            switch (col) {
                case imageColumn:
                    fieldWidths.setMaxImageFileNameWidth(ipF.getFieldWidth());
                    break;
                case titleColumn:
                    fieldWidths.setMaxImageTitleWidth(ipF.getFieldWidth());
                    break;
                case personFullNameColumn:
                    fieldWidths.setMaxPersonFullNameWidth(ipF.getFieldWidth());
                    break;
                case personFirstNameColumn:
                    fieldWidths.setMaxPersonFirstNameWidth(ipF.getFieldWidth());
                    break;
                case personLastNameColumn:
                    fieldWidths.setMaxPersonLastNameWidth(ipF.getFieldWidth());
                    break;
                default:
                    // handle more than 5 columns
                    break;
            }
        }
    }
}
