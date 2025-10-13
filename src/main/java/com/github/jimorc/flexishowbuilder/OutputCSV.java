package com.github.jimorc.flexishowbuilder;

/**
 * The OutputCSV class is used to build the CSV lines to be passed to LibreOffice or Excel
 * to generate an XLS file representing the slide show.
 */
public class OutputCSV {
    private CSVLine[] lines;

    /**
     * Constructor creates an empty OutputCSV object.
     */
    public OutputCSV() {
        lines = new CSVLine[0];
    }

    /**
     * Appends a CSVLine from the original InputCSV object or an ImageLine for a generated
     * image file.
     * @param line the CSVLine to append to the OutputCSV object.
     */
    public void appendLine(CSVLine line) {
        CSVLine[] newLines = new CSVLine[lines.length + 1];
        for (int i = 0; i < lines.length; i++) {
            newLines[i] = lines[i];
        }
        newLines[lines.length] = line;
        lines = newLines;
    }

    /**
     * Returns a string representation of the OutputCSV object.
     * @return string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CSVLine line : lines) {
            for (int i = 0; i < line.length(); i++) {
                sb.append(line.field(i));
                if (i < line.length() - 1) {
                    sb.append(",");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
