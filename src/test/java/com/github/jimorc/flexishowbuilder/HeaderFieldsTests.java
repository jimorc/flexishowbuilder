package com.github.jimorc.flexishowbuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class HeaderFieldsTests {
    @Test
    public void test5FieldHeader() {
        String line = "Filename,Title,Full Name,First Name,Last Name";
        try {
            HeaderFields hf = new HeaderFields(line);
            assertEquals(0, hf.getFilenameField());
            assertEquals(1, hf.getTitleField());
            assertEquals(2, hf.getFullNameField());
            assertEquals(3, hf.getFirstNameField());
            assertEquals(4, hf.getLastNameField());
        } catch (CSVException e) {
            fail("HeaderFields threw CSVException: " + e.getMessage());
        }
    }

    @Test
    public void test5FieldHeaderOutOfExpectedOrder() {
        String line = "Full Name,Filename,Last Name,Title,First Name";
        try {
            HeaderFields hf = new HeaderFields(line);
            assertEquals(1, hf.getFilenameField());
            assertEquals(3, hf.getTitleField());
            assertEquals(0, hf.getFullNameField());
            assertEquals(4, hf.getFirstNameField());
            assertEquals(2, hf.getLastNameField());
        } catch (CSVException e) {
            fail("HeaderFields threw CSVException: " + e.getMessage());
        }
    }

    @Test
    public void testMoreThan5Fields() {
        String line = "F1,F2,Filename,Title,Full Name,First Name,Last Name,F8,F9";
        try {
            HeaderFields hf = new HeaderFields(line);
            assertEquals(2, hf.getFilenameField());
            assertEquals(3, hf.getTitleField());
            assertEquals(4, hf.getFullNameField());
            assertEquals(5, hf.getFirstNameField());
            assertEquals(6, hf.getLastNameField());
        } catch (CSVException e) {
            fail("HeaderFields threw CSVException: " + e.getMessage());
        }
    }

    @Test
    public void testNoFilename() {
        String line = "No Filename,Title,Full Name,First Name,LastName";
        try {
            new HeaderFields(line);
            fail("Did not throw exception for No Filename");
        } catch (CSVException e) {
            assertEquals("Invalid header line. Does not contain at least:\n"
                    + "Filename,Title,Full Name,First tName,Last Name", e.getMessage());
        }
    }

    @Test
    public void testNoTitle() {
        String line = "Filename,No Title,Full Name,First Name,LastName";
        try {
            new HeaderFields(line);
            fail("Did not throw exception for No Filename");
        } catch (CSVException e) {
            assertEquals("Invalid header line. Does not contain at least:\n"
                    + "Filename,Title,Full Name,First tName,Last Name", e.getMessage());
        }
    }

    @Test
    public void testNoFullName() {
        String line = "Filename,Title,No Full Name,First Name,LastName";
        try {
            new HeaderFields(line);
            fail("Did not throw exception for No Filename");
        } catch (CSVException e) {
            assertEquals("Invalid header line. Does not contain at least:\n"
                    + "Filename,Title,Full Name,First tName,Last Name", e.getMessage());
        }
    }

    @Test
    public void testNoFirst() {
        String line = "Filename,Title,Full Name,No First Name,LastName";
        try {
            new HeaderFields(line);
            fail("Did not throw exception for No Filename");
        } catch (CSVException e) {
            assertEquals("Invalid header line. Does not contain at least:\n"
                    + "Filename,Title,Full Name,First tName,Last Name", e.getMessage());
        }
    }

    @Test
    public void testNoLast() {
        String line = "Filename,Title,Full Name,First Name,No LastName";
        try {
            new HeaderFields(line);
            fail("Did not throw exception for No Filename");
        } catch (CSVException e) {
            assertEquals("Invalid header line. Does not contain at least:\n"
                    + "Filename,Title,Full Name,First tName,Last Name", e.getMessage());
        }
    }
}
