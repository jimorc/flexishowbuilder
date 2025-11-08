package com.github.jimorc.flexishowbuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * ImageAndPersonLineTests contains tests for the ImageAndPersonLine class.
 */
public class ImageAndPersonLineTests {
    static final int LINES = 5;

    @Test
    void testNoCommaConstructor() {
        String header = "Filename,Title,Full Name,First Name,Last Name";
        try {
            HeaderFields hf = new HeaderFields(header);
            ImageAndPersonLine ipl = new ImageAndPersonLine("image.jpg,image title,John Doe,John,Doe", hf);
            assertEquals(LINES, ipl.length());
            assertEquals("image.jpg", ipl.getImageFileName());
            assertEquals("image title", ipl.getImageTitle());
            assertEquals("John Doe", ipl.getPersonFullName());
            assertEquals("John", ipl.getPersonFirstName());
            assertEquals("Doe", ipl.getPersonLastName());
            assertFalse(ipl.getInsideQuote());
            assertFalse(ipl.getLineEmpty());
            assertFalse(ipl.getLineEndsWithComma());
            assertFalse(ipl.getLineContainsNewline());
        } catch (CSVException e) {
            fail("CSVException: " + e.getMessage());
        }
    }

    @Test
    void testCommaInTitleConstructor() {
        String header = "Filename,Title,Full Name,First Name,Last Name";
        try {
            HeaderFields hf = new HeaderFields(header);
            ImageAndPersonLine ipl = new ImageAndPersonLine("image.jpg,\"image, title\",John Doe,John,Doe", hf);
            assertEquals(LINES, ipl.length());
            assertEquals("image.jpg", ipl.getImageFileName());
            assertEquals("\"image, title\"", ipl.getImageTitle());
            assertEquals("John Doe", ipl.getPersonFullName());
            assertEquals("John", ipl.getPersonFirstName());
            assertEquals("Doe", ipl.getPersonLastName());
            assertFalse(ipl.getInsideQuote());
            assertFalse(ipl.getLineEmpty());
            assertFalse(ipl.getLineEndsWithComma());
            assertFalse(ipl.getLineContainsNewline());
        } catch (CSVException e) {
            fail("CSVException: " + e.getMessage());
        }
    }

    @Test
    void testCommaAtLineEndConstructor() {
        String header = "Filename,Title,Full Name,First Name,Last Name";
        try {
            HeaderFields hf = new HeaderFields(header);
            ImageAndPersonLine ipl = new ImageAndPersonLine("image.jpg,image title,John Doe,John,Doe,", hf);
            assertEquals(LINES, ipl.length());
            assertEquals("image.jpg", ipl.getImageFileName());
            assertEquals("image title", ipl.getImageTitle());
            assertEquals("John Doe", ipl.getPersonFullName());
            assertEquals("John", ipl.getPersonFirstName());
            assertEquals("Doe", ipl.getPersonLastName());
            assertFalse(ipl.getInsideQuote());
            assertFalse(ipl.getLineEmpty());
            assertTrue(ipl.getLineEndsWithComma());
            assertFalse(ipl.getLineContainsNewline());
        } catch (CSVException e) {
            fail("CSVException: " + e.getMessage());
        }
    }

    @Test
    void testInsideQuoteConstructor() {
        String header = "Filename,Title,Full Name,First Name,Last Name";
        try {
            HeaderFields hf = new HeaderFields(header);
            ImageAndPersonLine ipl = new ImageAndPersonLine("image.jpg,\"image title,John Doe,John,Doe", hf);
            assertEquals(LINES, ipl.length());
            assertEquals("image.jpg", ipl.getImageFileName());
            assertEquals("\"image title,John Doe,John,Doe", ipl.getImageTitle());
            assertEquals("", ipl.getPersonFullName());
            assertEquals("", ipl.getPersonFirstName());
            assertEquals("", ipl.getPersonLastName());
            assertTrue(ipl.getInsideQuote());
            assertFalse(ipl.getLineEmpty());
            assertFalse(ipl.getLineEndsWithComma());
            assertFalse(ipl.getLineContainsNewline());
        } catch (CSVException e) {
            fail("CSVException: " + e.getMessage());
        }
    }

    @Test
    void testEmptyLineConstructor() {
        String header = "Filename,Title,Full Name,First Name,Last Name";
        try {
            HeaderFields hf = new HeaderFields(header);
            ImageAndPersonLine ipl = new ImageAndPersonLine("", hf);
            assertEquals(LINES, ipl.length());
            assertEquals("", ipl.getImageFileName());
            assertEquals("", ipl.getImageTitle());
            assertEquals("", ipl.getPersonFullName());
            assertEquals("", ipl.getPersonFirstName());
            assertEquals("", ipl.getPersonLastName());
            assertFalse(ipl.getInsideQuote());
            assertTrue(ipl.getLineEmpty());
            assertFalse(ipl.getLineEndsWithComma());
            assertFalse(ipl.getLineContainsNewline());
        } catch (CSVException e) {
            fail("CSVException: " + e.getMessage());
        }
    }

    @Test
    void testNewLineConstructor() {
        String header = "Filename,Title,Full Name,First Name,Last Name";
        try {
            HeaderFields hf = new HeaderFields(header);
            ImageAndPersonLine ipl = new ImageAndPersonLine("image.jpg,\"image title\n\",John Doe,John,Doe,", hf);
            assertEquals(LINES, ipl.length());
            assertEquals("image.jpg", ipl.getImageFileName());
            assertEquals("John Doe", ipl.getPersonFullName());
            assertEquals("John", ipl.getPersonFirstName());
            assertEquals("Doe", ipl.getPersonLastName());
            assertFalse(ipl.getInsideQuote());
            assertFalse(ipl.getLineEmpty());
            assertTrue(ipl.getLineEndsWithComma());
            assertTrue(ipl.getLineContainsNewline());
        } catch (CSVException e) {
            fail("CSVException: " + e.getMessage());
        }
    }

    @Test
    void testNoImageConstructor() {
        String header = "Filename,Title,Full Name,First Name,Last Name";
        try {
            HeaderFields hf = new HeaderFields(header);
            ImageAndPersonLine ipl = new ImageAndPersonLine(",image title,John Doe,John,Doe", hf);
            assertEquals(LINES, ipl.length());
            assertEquals("", ipl.getImageFileName());
            assertEquals("image title", ipl.getImageTitle());
            assertEquals("John Doe", ipl.getPersonFullName());
            assertEquals("John", ipl.getPersonFirstName());
            assertEquals("Doe", ipl.getPersonLastName());
            assertFalse(ipl.getInsideQuote());
            assertFalse(ipl.getLineEmpty());
            assertFalse(ipl.getLineEndsWithComma());
            assertFalse(ipl.getLineContainsNewline());
            assertTrue(ipl.getNoImageFile());
            assertFalse(ipl.getNoImageTitle());
            assertFalse(ipl.getNoPersonFullName());
            assertFalse(ipl.getNoPersonFirstName());
            assertFalse(ipl.getNoPersonLastName());
        } catch (CSVException e) {
            fail("CSVException: " + e.getMessage());
        }
    }

    @Test
    void testNoTitleConstructor() {
        String header = "Filename,Title,Full Name,First Name,Last Name";
        try {
            HeaderFields hf = new HeaderFields(header);
            ImageAndPersonLine ipl = new ImageAndPersonLine("image.jpg,,John Doe,John,Doe", hf);
            assertEquals(LINES, ipl.length());
            assertEquals("image.jpg", ipl.getImageFileName());
            assertEquals("", ipl.getImageTitle());
            assertEquals("John Doe", ipl.getPersonFullName());
            assertEquals("John", ipl.getPersonFirstName());
            assertEquals("Doe", ipl.getPersonLastName());
            assertFalse(ipl.getInsideQuote());
            assertFalse(ipl.getLineEmpty());
            assertFalse(ipl.getLineEndsWithComma());
            assertFalse(ipl.getLineContainsNewline());
            assertFalse(ipl.getNoImageFile());
            assertTrue(ipl.getNoImageTitle());
            assertFalse(ipl.getNoPersonFullName());
            assertFalse(ipl.getNoPersonFirstName());
            assertFalse(ipl.getNoPersonLastName());
        } catch (CSVException e) {
            fail("CSVException: " + e.getMessage());
        }
    }

    @Test
    void testNoFullNameConstructor() {
        String header = "Filename,Title,Full Name,First Name,Last Name";
        try {
            HeaderFields hf = new HeaderFields(header);
            ImageAndPersonLine ipl = new ImageAndPersonLine("image.jpg,image title,,John,Doe", hf);
            assertEquals(LINES, ipl.length());
            assertEquals("image.jpg", ipl.getImageFileName());
            assertEquals("image title", ipl.getImageTitle());
            assertEquals("", ipl.getPersonFullName());
            assertEquals("John", ipl.getPersonFirstName());
            assertEquals("Doe", ipl.getPersonLastName());
            assertFalse(ipl.getInsideQuote());
            assertFalse(ipl.getLineEmpty());
            assertFalse(ipl.getLineEndsWithComma());
            assertFalse(ipl.getLineContainsNewline());
            assertFalse(ipl.getNoImageFile());
            assertFalse(ipl.getNoImageTitle());
            assertTrue(ipl.getNoPersonFullName());
            assertFalse(ipl.getNoPersonFirstName());
            assertFalse(ipl.getNoPersonLastName());
        } catch (CSVException e) {
            fail("CSVException: " + e.getMessage());
        }
    }

    @Test
    void testNoFirstNameConstructor() {
        String header = "Filename,Title,Full Name,First Name,Last Name";
        try {
            HeaderFields hf = new HeaderFields(header);
            ImageAndPersonLine ipl = new ImageAndPersonLine("image.jpg,image title,John Doe,,Doe", hf);
            assertEquals(LINES, ipl.length());
            assertEquals("image.jpg", ipl.getImageFileName());
            assertEquals("image title", ipl.getImageTitle());
            assertEquals("John Doe", ipl.getPersonFullName());
            assertEquals("", ipl.getPersonFirstName());
            assertEquals("Doe", ipl.getPersonLastName());
            assertFalse(ipl.getInsideQuote());
            assertFalse(ipl.getLineEmpty());
            assertFalse(ipl.getLineEndsWithComma());
            assertFalse(ipl.getLineContainsNewline());
            assertFalse(ipl.getNoImageFile());
            assertFalse(ipl.getNoImageTitle());
            assertFalse(ipl.getNoPersonFullName());
            assertTrue(ipl.getNoPersonFirstName());
            assertFalse(ipl.getNoPersonLastName());
        } catch (CSVException e) {
            fail("CSVException: " + e.getMessage());
        }
    }

    @Test
    void testToString() {
        String header = "Filename,Title,Full Name,First Name,Last Name";
        try {
            HeaderFields hf = new HeaderFields(header);
            ImageAndPersonLine ipl = new ImageAndPersonLine("image.jpg,image title,John Doe,John,Doe", hf);
            assertEquals("image.jpg,image title,John Doe,John,Doe", ipl.toString());

            ImageAndPersonLine ipl2 = new ImageAndPersonLine("image.jpg,\"image, title\",John Doe,John,Doe", hf);
            assertEquals("image.jpg,\"image, title\",John Doe,John,Doe", ipl2.toString());
        } catch (CSVException e) {
            fail("CSVException: " + e.getMessage());
        }
    }
}
