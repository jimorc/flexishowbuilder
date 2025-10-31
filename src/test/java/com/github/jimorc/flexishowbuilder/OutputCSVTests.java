package com.github.jimorc.flexishowbuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * OutputCSVTests contains tests for the OutputCSV class.
 */
public class OutputCSVTests {
    @Test
    void testAppendLine() {
        String header = "Filename,Title,Full Name,First Name,Last Name";
        try {
            HeaderFields hf = new HeaderFields(header);
            CSVLine line = new ImageAndPersonLine(header, hf);
            OutputCSV csv = new OutputCSV();
            csv.appendLine(line);
            String c = csv.toString();
            assertEquals(header + "\n", c);

            csv.appendLine(line);
            c = csv.toString();
            assertEquals(header + "\n" + header + "\n", c);
        } catch (CSVException e) {
            fail("CSVException: " + e.getMessage());
        }
    }
}
