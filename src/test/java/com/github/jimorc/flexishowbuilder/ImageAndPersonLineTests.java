package com.github.jimorc.flexishowbuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
