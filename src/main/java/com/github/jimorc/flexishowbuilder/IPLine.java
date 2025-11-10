package com.github.jimorc.flexishowbuilder;

import javafx.scene.layout.HBox;

/**
 * IPLine is an HBox that displays a CSVLine as IPLFields.
 */
public class IPLine extends HBox {
    // Used in switch cases to identify columns.
    // Cases must be constants, so can't use IPLColumn.ordinal().
    private final int imageColumn = 0;              // same as IPLColumn.IMAGE_FILE_NAME.ordinal();
    private final int titleColumn = 1;              // same as IPLColumn.IMAGE_TITLE.ordinal();
    private final int personFullNameColumn = 2;     // same as IPLColumn.PERSON_FULL_NAME.ordinal();
    private final int personFirstNameColumn = 3;    // same as IPLColumn.PERSON_FIRST_NAME.ordinal();
    private final int personLastNameColumn = 4;     // same as IPLColumn.PERSON_LAST_NAME.ordinal();

    private CSVLine line;

    /**
     * Constructor.
     * @param line the CSVLine to display.
     */
    public IPLine(CSVLine line, IPLFieldWidths fieldWidths) {
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

    /**
     * Sets the field widths of the IPLFields in this IPLine.
     * @param fieldWidths the IPLFieldWidths object containing the widths to set.
     */
    public void setFieldWidths(IPLFieldWidths fieldWidths) {
        for (int col = 0; col < getChildren().size(); col++) {
            IPLField ipField = (IPLField) getChildren().get(col);
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
