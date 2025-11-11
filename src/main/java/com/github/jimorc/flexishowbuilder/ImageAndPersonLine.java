package com.github.jimorc.flexishowbuilder;

import javafx.scene.control.Tooltip;
import javafx.util.Duration;

/**
 * ImageAndPersonLine represents the image and person information for each image to be displayed by
 * flexishow as downloaded into an InputCSV object.
 */
public class ImageAndPersonLine extends CSVLine {
    private static final double TOOLTIP_SHOW_DURATION_SECONDS = 10.0;
    /**
     * NUMFIELDS is the number of fields that an ImageAndPersonLine object should contain.
     */
    private boolean insideQuote;
    private boolean lineEmpty;
    private boolean lineEndsWithComma;
    private boolean lineContainsNewline;
    private boolean imageFileNotFound;
    private String[] fields = new String[IPLColumn.values().length];

    /**
     * Constructor - creates an ImageAndPersonLine object from a CSV line. Special
     * processing is required because the image title may contain commas. In that case,
     * the title must be enclosed in double quotes, although no checking is done to
     * ensure that this is the case.
     * @param line - the CSV input line. The fields must be in the following order:<br>
     *  0. image file name<br>
     *  1. image title<br>
     *  2. person's full name<br>
     *  3. person's first name<br>
     *  4. person's last name<br>
     * @throws ArrayIndexOutOfBoundsException if the line does not contain at least
     * five fields.
     */
    public ImageAndPersonLine(String line, HeaderFields hf) {
        super();
        CSVFields f = null;
        f = new CSVFields(line);
        insideQuote = f.getInsideQuote();
        lineEmpty = f.getLineEmpty();
        lineEndsWithComma = f.getLineEndsWithComma();
        lineContainsNewline = f.getLineContainsNewline();

        addImageField(f, hf);
        addTitleField(f, hf);
        addFullNameField(f, hf);
        addFirstNameField(f, hf);
        addLastNameField(f, hf);
        addFields(fields);
    }

    /**
     * Returns the image file name.
     * @return the image file name.
     */
    public String getImageFileName() {
        return field(IPLColumn.IMAGE_FILE_NAME.ordinal());
    }

    /**
     * Returns the image title.
     * @return the image title.
     */
    public String getImageTitle() {
        return field(IPLColumn.IMAGE_TITLE.ordinal());
    }

    /**imageFileNotJpeg
     * Returns the person's full name.
     * @return the person's full name.
     */
    public String getPersonFullName() {
        return field(IPLColumn.PERSON_FULL_NAME.ordinal());
    }

    /**
     * Returns the person's first name.
     * @return the person's first name.
     */
    public String getPersonFirstName() {
        return field(IPLColumn.PERSON_FIRST_NAME.ordinal());
    }

    /**
     * Returns the person's last name.
     * @return the person's last name.
     */
    public String getPersonLastName() {
        return field(IPLColumn.PERSON_LAST_NAME.ordinal());
    }

    /**
     * Returns whether the image title field was inside quotes.
     * @return whether the image title field was inside quotes.
     */
    public boolean getInsideQuote() {
        return insideQuote;
    }

    /**
     * Returns whether the line was empty.
     * @return true if line was empty, false otherwise.
     */
    public boolean getLineEmpty() {
        return lineEmpty;
    }

    /**
     * Returns whether the line contains a newline character.
     * @return true if the line contains a newline character, false otherwise.
     */
    public boolean getLineContainsNewline() {
        return lineContainsNewline;
    }
    /**
     * Returns whether the line ends with a comma.
     * @return true if the line ends with a comma, false otherwise.
     */

    public boolean getLineEndsWithComma() {
        return lineEndsWithComma;
    }

    /**
     * Returns whether the image file field was missing.
     * @return true if the image file field was missing, false otherwise.
     */
    public boolean getNoImageFile() {
        return fields[IPLColumn.IMAGE_FILE_NAME.ordinal()].isBlank();
    }

    /**
     * Returns whether the image title field was missing.
     * @return true if the image title field was missing, false otherwise.
     */
    public boolean getNoImageTitle() {
        return fields[IPLColumn.IMAGE_TITLE.ordinal()].isBlank();
    }

    /**
     * Returns whether the person's full name field was missing.
    * @return true if full name field was missing, false otherwise.
    */
    public boolean getNoPersonFullName() {
        return fields[IPLColumn.PERSON_FULL_NAME.ordinal()].isBlank();
    }

    /**
     * Returns whether the person's first name field was missing.
     * @returntrue if first name field was missing, false otherwise.
     */
    public boolean getNoPersonFirstName() {
        return fields[IPLColumn.PERSON_FIRST_NAME.ordinal()].isBlank();
    }

    /**
     * Returns whether the person's last name field was missing.
     * @return true if last name field was missing, false otherwise.
     */
    public boolean getNoPersonLastName() {
        return fields[IPLColumn.PERSON_LAST_NAME.ordinal()].isBlank();
    }

    /**
     * Sets whether the image file has been found.
     * @param b true if the image file was not found, false otherwise.
     */
    public void setImageFileNotFound(boolean b) {
        imageFileNotFound = b;
    }

    /**
     * Returns whether the image file was not found.
     * @return true if the image file was not found, false otherwise.
     */
    public boolean getImageFileNotFound() {
        return imageFileNotFound;
    }

    /**
     * Returns whether the image file is not a jpg file.
     * @return true if the image file is not "" and not a jpg, false otherwise.
     */
    public boolean getImageNotJpeg() {
        return !fields[IPLColumn.IMAGE_FILE_NAME.ordinal()].isBlank()
            && !fields[IPLColumn.IMAGE_FILE_NAME.ordinal()].toLowerCase().endsWith(".jpg")
            && !fields[IPLColumn.IMAGE_FILE_NAME.ordinal()].toLowerCase().endsWith(".jpeg");
    }

    /**
     * Returns a tooltip explaining that the image file is not a jpg file.
     * @return a tooltip explaining that the image file is not a jpg file.
     */
    public Tooltip getImageNotJpegTooltip() {
        Tooltip tt = new Tooltip("RunFlexishow requires the image file to be a JPEG.\n"
            + "The file name must end with .jpg or .jpeg\n"
            + "Flexishowbuilder will not generate an XLS file for\n"
            + "RunFlexishow until this is fixed.");
        tt.setShowDuration(Duration.seconds(TOOLTIP_SHOW_DURATION_SECONDS));
        return tt;
    }

    /**
     * Returns a tooltip explaining that the image file was not found.
     * @return a tooltip explaining that the image file was not found.
     */
    public Tooltip getImageNotFoundTooltip() {
        Tooltip tt = new Tooltip("The image file was not found in the image folder.\n"
            + "Flexishowbuilder will not generate an XLS file for\n"
            + "RunFlexishow until this is fixed.");
        tt.setShowDuration(Duration.seconds(TOOLTIP_SHOW_DURATION_SECONDS));
        return tt;
    }

    /**
     * Returns a tooltip stating that the line is empty.
     * @return a tooltip for an empty line.
     */
    public Tooltip getLineEmptyTooltip() {
        Tooltip tt = new Tooltip("The line is empty.\n"
            + "It can be deleted using the context menu.");
        tt.setShowDuration(Duration.seconds(TOOLTIP_SHOW_DURATION_SECONDS));
        return tt;
    }

    /**
     * Returns a String representation of the object.
     * @return a String representation of the object.
     * @throws ArrayIndexOutOfBoundsException if the object does not
     * contain at least five fields.
     */
    @Override
    public String toString() {
        return getImageFileName() + "," + getImageTitle() + "," + getPersonFullName() + ","
            + getPersonFirstName() + "," + getPersonLastName();
    }

    private void addImageField(CSVFields f, HeaderFields hf) {
        int csvFieldPos = hf.getFilenameField();
        if (csvFieldPos == -1) {
            return;
        }
        try {
            fields[IPLColumn.IMAGE_FILE_NAME.ordinal()] = f.getField(csvFieldPos);
        } catch (IndexOutOfBoundsException e) {
            fields[IPLColumn.IMAGE_FILE_NAME.ordinal()] = "";
        }
    }

    private void addTitleField(CSVFields f, HeaderFields hf) {
        int csvFieldPos = hf.getTitleField();
        if (csvFieldPos == -1) {
            return;
        }
        try {
            fields[IPLColumn.IMAGE_TITLE.ordinal()] = f.getField(csvFieldPos);
        } catch (IndexOutOfBoundsException e) {
            fields[IPLColumn.IMAGE_TITLE.ordinal()] = "";
        }
    }

    private void addFullNameField(CSVFields f, HeaderFields hf) {
        int csvFieldPos = hf.getFullNameField();
        if (csvFieldPos == -1) {
            return;
        }
        try {
            fields[IPLColumn.PERSON_FULL_NAME.ordinal()] = f.getField(csvFieldPos);
        } catch (IndexOutOfBoundsException e) {
            fields[IPLColumn.PERSON_FULL_NAME.ordinal()] = "";
        }
    }

    private void addFirstNameField(CSVFields f, HeaderFields hf) {
        int csvFieldPos = hf.getFirstNameField();
        if (csvFieldPos == -1) {
            return;
        }
        try {
            fields[IPLColumn.PERSON_FIRST_NAME.ordinal()] = f.getField(csvFieldPos);
        } catch (IndexOutOfBoundsException e) {
            fields[IPLColumn.PERSON_FIRST_NAME.ordinal()] = "";
        }
    }

    private void addLastNameField(CSVFields f, HeaderFields hf) {
        int csvFieldPos = hf.getLastNameField();
        if (csvFieldPos == -1) {
            return;
        }
        try {
            fields[IPLColumn.PERSON_LAST_NAME.ordinal()] = f.getField(csvFieldPos);
        } catch (IndexOutOfBoundsException e) {
            fields[IPLColumn.PERSON_LAST_NAME.ordinal()] = "";
        }
    }
}
