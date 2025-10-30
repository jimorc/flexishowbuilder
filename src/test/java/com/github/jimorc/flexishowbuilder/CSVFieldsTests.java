package com.github.jimorc.flexishowbuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * CSVFields Tests.
 */
public class CSVFieldsTests {
    @Test
    public void testCSVFieldsSingleField() {
        String line = "H1";
        CSVFields f = new CSVFields(line);
        assertEquals(1, f.size());
        assertEquals("H1", f.getField(0));
    }

    @Test
    public void testCSVFieldsTwoFields() {
        String line = "H1,H2";
        CSVFields f = new CSVFields(line);
        assertEquals(2, f.size());
        assertEquals("H1", f.getField(0));
        assertEquals("H2", f.getField(1));
    }

    @Test
    public void testCSVFieldsFieldWithQuotes() {
        String line = "H1,\"H,2\",H3";
        CSVFields f = new CSVFields(line);
        assertEquals("H1", f.getField(0));
        assertEquals("H,2", f.getField(1));
        assertEquals("H3", f.getField(2));
    }

    @Test
    public void testCSVFieldsInvalidIndex() {
        String line = "H1,H2";
        CSVFields f = new CSVFields(line);
        try {
            f.getField(f.size() + 1);
            fail("Did not throw IndexOutOfBoundsException for index > number of fields");
        } catch (IndexOutOfBoundsException _) { }
        try {
            f.getField(-1);
            fail("Did not throw IndexOutOfBoundsException for index < 0");
        } catch (IndexOutOfBoundsException _) { }
    }

    @Test
    public void testCSVFieldsEmptyLine() {
        String line = "";
        try {
            new CSVFields(line);
            fail("Failed to throw IllegalArgumentException for empty line");
        } catch (IllegalArgumentException e) {
            assertEquals("Line either empty or last character is a comma.", e.getMessage());
        }
    }

    @Test
    public void testCSVFieldsLineEndsWithComma() {
        String line = "H1,";
        try {
            new CSVFields(line);
            fail("Failed to throw IllegalArgumentException for line ending with comma.");
        } catch (IllegalArgumentException e) {
            assertEquals("Line either empty or last character is a comma.", e.getMessage());
        }
    }

    @Test
    public void testCSVFieldsContainsNewLine() {
        String line = "H1\n,H2";
        try {
            new CSVFields(line);
            fail("Failed to throw IllegalArgumentException for line containing newline");
        } catch (IllegalArgumentException e) {
            assertEquals("Line contains illegal newline character.", e.getMessage());
        }
    }
}
