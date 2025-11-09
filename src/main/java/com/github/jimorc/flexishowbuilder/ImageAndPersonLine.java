package com.github.jimorc.flexishowbuilder;

/**
 * ImageAndPersonLine represents the image and person information for each image to be displayed by
 * flexishow as downloaded into an InputCSV object.
 */
public class ImageAndPersonLine extends CSVLine {
    /**
     * NUMFIELDS is the number of fields that an ImageAndPersonLine object should contain.
     */
    public static final int NUMFIELDS = 5;
    private final int imageFilePosition = 0;
    private final int imageTitlePosition = 1;
    private final int personFullNamePosition = 2;
    private final int personFirstNamePosition = 3;
    private final int personLastNamePosition = 4;
    private boolean insideQuote;
    private boolean lineEmpty;
    private boolean lineEndsWithComma;
    private boolean lineContainsNewline;
    private String[] fields = new String[NUMFIELDS];

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
        return field(imageFilePosition);
    }

    /**
     * Returns the image title.
     * @return the image title.
     */
    public String getImageTitle() {
        return field(imageTitlePosition);
    }

    /**imageFileNotJpeg
     * Returns the person's full name.
     * @return the person's full name.
     */
    public String getPersonFullName() {
        return field(personFullNamePosition);
    }

    /**
     * Returns the person's first name.
     * @return the person's first name.
     */
    public String getPersonFirstName() {
        return field(personFirstNamePosition);
    }

    /**
     * Returns the person's last name.
     * @return the person's last name.
     */
    public String getPersonLastName() {
        return field(personLastNamePosition);
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
        return fields[imageFilePosition].isBlank();
    }

    /**
     * Returns whether the image title field was missing.
     * @return true if the image title field was missing, false otherwise.
     */
    public boolean getNoImageTitle() {
        return fields[imageTitlePosition].isBlank();
    }

    /**
     * Returns whether the person's full name field was missing.
    * @return true if full name field was missing, false otherwise.
    */
    public boolean getNoPersonFullName() {
        return fields[personFullNamePosition].isBlank();
    }

    /**
     * Returns whether the person's first name field was missing.
     * @returntrue if first name field was missing, false otherwise.
     */
    public boolean getNoPersonFirstName() {
        return fields[personFirstNamePosition].isBlank();
    }

    /**
     * Returns whether the person's last name field was missing.
     * @return true if last name field was missing, false otherwise.
     */
    public boolean getNoPersonLastName() {
        return fields[personLastNamePosition].isBlank();
    }

    /**
     * Returns whether the image file is not a jpg file.
     * @return true if the image file is not "" and not a jpg, false otherwise.
     */
    public boolean getImageNotJpeg() {
        return !fields[imageFilePosition].isBlank()
            && !fields[imageFilePosition].toLowerCase().endsWith(".jpg")
            && !fields[imageFilePosition].toLowerCase().endsWith(".jpeg");
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
            fields[imageFilePosition] = f.getField(csvFieldPos);
        } catch (IndexOutOfBoundsException e) {
            fields[imageFilePosition] = "";
        }
    }

    private void addTitleField(CSVFields f, HeaderFields hf) {
        int csvFieldPos = hf.getTitleField();
        if (csvFieldPos == -1) {
            return;
        }
        try {
            fields[imageTitlePosition] = f.getField(csvFieldPos);
        } catch (IndexOutOfBoundsException e) {
            fields[imageTitlePosition] = "";
        }
    }

    private void addFullNameField(CSVFields f, HeaderFields hf) {
        int csvFieldPos = hf.getFullNameField();
        if (csvFieldPos == -1) {
            return;
        }
        try {
            fields[personFullNamePosition] = f.getField(csvFieldPos);
        } catch (IndexOutOfBoundsException e) {
            fields[personFullNamePosition] = "";
        }
    }

    private void addFirstNameField(CSVFields f, HeaderFields hf) {
        int csvFieldPos = hf.getFirstNameField();
        if (csvFieldPos == -1) {
            return;
        }
        try {
            fields[personFirstNamePosition] = f.getField(csvFieldPos);
        } catch (IndexOutOfBoundsException e) {
            fields[personFirstNamePosition] = "";
        }
    }

    private void addLastNameField(CSVFields f, HeaderFields hf) {
        int csvFieldPos = hf.getLastNameField();
        if (csvFieldPos == -1) {
            return;
        }
        try {
            fields[personLastNamePosition] = f.getField(csvFieldPos);
        } catch (IndexOutOfBoundsException e) {
            fields[personLastNamePosition] = "";
        }
    }
}
