package com.github.jimorc.flexishowbuilder;

/**
 * HeaderFields encapsulates the column position of each field needed by flexishowbuilder.
 */
public class HeaderFields {
    /**
     * NOFIELD indicates that the field value has not been set.
     */
    public static final int NOFIELD = -1;
    private int fileNameField = NOFIELD;
    private int titleField = NOFIELD;
    private int fullNameField = NOFIELD;
    private int firstNameField = NOFIELD;
    private int lastNameField = NOFIELD;

    /**
     * HeaderFields constructor parses the passed input header line to determine the column
     * number for each of "Filename", "Title", "Full Name", "First Name", and "Last Name".
     * @param headerLine the header line to parse.
     * @throws CSVException if headerLine does not contain all of the required fields.
     */
    public HeaderFields(String headerLine) throws CSVException {
        CSVFields f = new CSVFields(headerLine);
        for (int i = 0; i < f.size(); i++) {
            setFieldPos(f, i);
        }
        if (fileNameField == NOFIELD || titleField == NOFIELD || fullNameField == NOFIELD
            || firstNameField == NOFIELD || lastNameField == NOFIELD) {
            throw new CSVException("Invalid header line. Does not contain at least:\n"
                + "Filename,Title,Full Name,First Name,Last Name");
        }
    }

    private void setFieldPos(CSVFields f, int i) {
        switch (f.getField(i)) {
            case "Filename":
                fileNameField = i;
                break;
            case "Title":
                titleField = i;
                break;
            case "Full Name":
                fullNameField = i;
                break;
            case "First Name":
                firstNameField = i;
                break;
            case "Last Name":
                lastNameField = i;
                break;
            default:
                break;
        }
    }

    public int getFilenameField() {
        return fileNameField;
    }

    public int getTitleField() {
        return titleField;
    }

    public int getFullNameField() {
        return fullNameField;
    }

    public int getFirstNameField() {
        return firstNameField;
    }

    public int getLastNameField() {
        return lastNameField;
    }
}
