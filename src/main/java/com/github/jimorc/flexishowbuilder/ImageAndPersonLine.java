package com.github.jimorc.flexishowbuilder;

public class ImageAndPersonLine extends CSVLine {
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
        String[] fields = new String[5];
        if (first == -1 || second == -1 || third == -1 || fourth == -1) {
            throw new ArrayIndexOutOfBoundsException("Line does not contain at least five fields: " + line);
        }
        if (first == second || second == third || third == fourth) {
            throw new ArrayIndexOutOfBoundsException("Line does not contain at least five fields: " + line);
        }
        fields[0] = line.substring(0, first);
        fields[1] = line.substring(first + 1, second);
        fields[2] = line.substring(second + 1, third);
        fields[3] = line.substring(third + 1, fourth);
        fields[4] = line.substring(fourth + 1);
        addFields(fields);
    }

    /**
     * Returns the image file name.
     * @return the image file name.
     */
    public String getImageFileName() {
        return field(0);
    }

    /**
     * Returns the image title.
     * @return the image title.
     */
    public String getImageTitle() {
        return field(1);
    }

    /**
     * Returns the person's full name.
     * @return the person's full name.
     */
    public String getPersonFullName() {
        return field(2);
    }
    
    /**
     * Returns the person's first name.
     * @return the person's first name.
     */
    public String getPersonFirstName() {
        return field(3);
    }

    /**
     * Returns the person's last name.
     * @return the person's last name.
     */
    public String getPersonLastName() {
        return field(4);
    }

    /**
     * Returns a String representation of the object.
     * @return a String representation of the object.
     * @throws ArrayIndexOutOfBoundsException if the object does not
     * contain at least five fields.
     */
    @Override
    public String toString() {
        return getImageFileName() + "," + getImageTitle() + "," + getPersonFullName() + "," +
            getPersonFirstName() + "," + getPersonLastName();
    }
}