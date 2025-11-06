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
        assertEquals("\"H,2\"", f.getField(1));
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
            CSVFields f = new CSVFields(line);
            assertEquals("Line either empty or last character is a comma.", f.getException().getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCSVFieldsLineEndsWithComma() {
        String line = "H1,";
        try {
            CSVFields f = new CSVFields(line);
            assertEquals("Line either empty or last character is a comma.", f.getException().getMessage());
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCSVFieldsContainsNewLine() {
        String line = "H1\n,H2";
        try {
            CSVFields f = new CSVFields(line);
            assertEquals("Line contains illegal newline character.", f.getException().getMessage());
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCSVFieldsUnterminatedQuote() {
        String line = "H1,\"H2";
        try {
            CSVFields f = new CSVFields(line);
            assertEquals("Last field in line has unterminated quote mark.", f.getException().getMessage());
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }
    }
}
