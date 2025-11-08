package com.github.jimorc.flexishowbuilder;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * CSVFields converts a CSV string into an array of fields.
 */
public class CSVFields {
    private final ArrayList<String> fields;
    private final MutableBoolean insideQuote;
    private boolean lineEmpty;
    private boolean lineEndsWithComma;
    private boolean lineContainsNewline;

    /**
     * Constructor.
     * @param line the line to convert to fields.
     */
    public CSVFields(String line) {
        lineEmpty = line.isEmpty();
        lineEndsWithComma = line.endsWith(",");
        lineContainsNewline = line.contains("\n");
        fields = new ArrayList<String>(0);
        IntStream cpStream = line.codePoints();
        insideQuote = new MutableBoolean(false);
        StringBuffer field = new StringBuffer();
        cpStream.forEach(cp -> processCodePoint(field, cp));
        // last field has not been added, so do it now;
        if (field.length() != 0) {
            fields.add(field.toString());
        } else {
            fields.add("");
        }
    }

    private void processCodePoint(StringBuffer field, int cp) {
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
                break;
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

    /**
     * Retrieve whether the line was empty.
     * @return if the line was empty.
     */
    public boolean getLineEmpty() {
        return lineEmpty;
    }

    /**
     * Retrieve whether the line ends with a comma.
     * @return if the line ends with a comma.
     */
    public boolean getLineEndsWithComma() {
        return lineEndsWithComma;
    }

    /**
     * Retrieve whether the line contains a newline character.
     * @return
     */
    public boolean getLineContainsNewline() {
        return lineContainsNewline;
    }
}
