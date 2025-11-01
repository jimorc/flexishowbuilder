package com.github.jimorc.flexishowbuilder;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * CSVFields converts a CSV string into an array of fields.
 */
public class CSVFields {
    private final ArrayList<String> fields;
    private final MutableBoolean insideQuote;

    /**
     * Constructor.
     * @param line the line to convert to fields.
     * @throws IllegalArgumentException if line is empty.
     * @throws IllegalArgumentException i line ends with a comma.
     * @throws IllegalArgumentException if line contains a newline character.
     */
    public CSVFields(String line) throws IllegalArgumentException {
        fields = new ArrayList<String>(0);
        IntStream cpStream = line.codePoints();
        insideQuote = new MutableBoolean(false);
        StringBuffer field = new StringBuffer();
        cpStream.forEach(cp -> processCodePoint(field, cp));
        // last field has not been added, so do it now;
        if (field.length() != 0) {
            fields.add(field.toString());
        } else {
            throw new IllegalArgumentException("Line either empty or last character is a comma.");
        }
        if (insideQuote.getValue()) {
            throw new IllegalArgumentException("Last field in line has unterminated quote mark.");
        }
    }

    private void processCodePoint(StringBuffer field, int cp)
            throws IllegalArgumentException {
        switch (cp) {
            case '\u0022':
                insideQuote.setValue(!insideQuote.getValue());
                field.appendCodePoint(cp);
                break;
            case ',':
                if (insideQuote.getValue()) {
                    field.appendCodePoint(cp);
                } else {
                    fields.add(field.toString());
                    field.delete(0, field.length());
                }
                break;
            case '\n':
                throw new IllegalArgumentException("Line contains illegal newline character.");
            default:
                field.appendCodePoint(cp);
                break;
        }
    }

    /**
     * Retrieve the number of fields in the CSVFields object.
     * @return the numnber of fields.
     */
    public int size() {
        return fields.size();
    }

    /**
     * Retrieve the field specified by the index parameter.
     * @param index the index of the field to return
     * @return the field specified by the index
     * @throws IndexOutOfBoundsException if 0 > index > number of fields
     */
    public String getField(int index) throws IndexOutOfBoundsException {
        return fields.get(index);
    }

    /**
     * Retrieve whether the last field did not have an ending quote mark.
     * @return if insideQuote.
     */
    public boolean getInsideQuote() {
        return insideQuote.getValue();
    }
}
