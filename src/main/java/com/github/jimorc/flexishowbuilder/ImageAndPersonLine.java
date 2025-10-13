package com.github.jimorc.flexishowbuilder;

/**
 * ImageAndPersonLine represents the image and person information for each image to be displayed by
 * flexishow as downloaded into an InputCSV object.
 */
public class ImageAndPersonLine extends CSVLine {
    private final int imageFilePosition = 0;
    private final int imageTitlePosition = 1;
    private final int personFullNamePosition = 2;
    private final int personFirstNamePosition = 3;
    private final int personLastNamePosition = 4;

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
    public ImageAndPersonLine(String line) {
        super();
        int first = line.indexOf(',');
        int fourth = line.lastIndexOf(',');
        int third = line.lastIndexOf(',', fourth - 1);
        int second = line.lastIndexOf(',', third - 1);
        String[] fields = new String[personLastNamePosition + 1];
        if (first == -1 || second == -1 || third == -1 || fourth == -1) {
            throw new ArrayIndexOutOfBoundsException("Line does not contain at least five fields: " + line);
        }
        if (first == second || second == third || third == fourth) {
            throw new ArrayIndexOutOfBoundsException("Line does not contain at least five fields: " + line);
        }
        fields[imageFilePosition] = line.substring(0, first);
        fields[imageTitlePosition] = line.substring(first + 1, second);
        fields[personFullNamePosition] = line.substring(second + 1, third);
        fields[personFirstNamePosition] = line.substring(third + 1, fourth);
        fields[personLastNamePosition] = line.substring(fourth + 1);
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

    /**
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
}
