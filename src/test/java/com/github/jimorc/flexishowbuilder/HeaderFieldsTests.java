package com.github.jimorc.flexishowbuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * HeadFieldsTests contain tests for the HeaderFields class.
 */
public class HeaderFieldsTests {
    @Test
    public void test5FieldHeader() {
        final int filenamePos = 0;
        final int titlePos = 1;
        final int fullNamePos = 2;
        final int firstNamePos = 3;
        final int lastNamePos = 4;
        String line = "Filename,Title,Full Name,First Name,Last Name";
        try {
            HeaderFields hf = new HeaderFields(line);
            assertEquals(filenamePos, hf.getFilenameField());
            assertEquals(titlePos, hf.getTitleField());
            assertEquals(fullNamePos, hf.getFullNameField());
            assertEquals(firstNamePos, hf.getFirstNameField());
            assertEquals(lastNamePos, hf.getLastNameField());
        } catch (CSVException e) {
            fail("HeaderFields threw CSVException: " + e.getMessage());
        }
    }

    @Test
    public void test5FieldHeaderOutOfExpectedOrder() {
        final int filenamePos = 1;
        final int titlePos = 3;
        final int fullNamePos = 0;
        final int firstNamePos = 4;
        final int lastNamePos = 2;
        String line = "Full Name,Filename,Last Name,Title,First Name";
        try {
            HeaderFields hf = new HeaderFields(line);
            assertEquals(filenamePos, hf.getFilenameField());
            assertEquals(titlePos, hf.getTitleField());
            assertEquals(fullNamePos, hf.getFullNameField());
            assertEquals(firstNamePos, hf.getFirstNameField());
            assertEquals(lastNamePos, hf.getLastNameField());
        } catch (CSVException e) {
            fail("HeaderFields threw CSVException: " + e.getMessage());
        }
    }

    @Test
    public void testMoreThan5Fields() {
        final int filenamePos = 2;
        final int titlePos = 3;
        final int fullNamePos = 4;
        final int firstNamePos = 5;
        final int lastNamePos = 6;
        String line = "F1,F2,Filename,Title,Full Name,First Name,Last Name,F8,F9";
        try {
            HeaderFields hf = new HeaderFields(line);
            assertEquals(filenamePos, hf.getFilenameField());
            assertEquals(titlePos, hf.getTitleField());
            assertEquals(fullNamePos, hf.getFullNameField());
            assertEquals(firstNamePos, hf.getFirstNameField());
            assertEquals(lastNamePos, hf.getLastNameField());
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
                    + "Filename,Title,Full Name,First Name,Last Name", e.getMessage());
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
                    + "Filename,Title,Full Name,First Name,Last Name", e.getMessage());
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
                    + "Filename,Title,Full Name,First Name,Last Name", e.getMessage());
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
                    + "Filename,Title,Full Name,First Name,Last Name", e.getMessage());
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
                    + "Filename,Title,Full Name,First Name,Last Name", e.getMessage());
        }
    }

    @Test
    public void testFewerThan5Fields() {
        String line = "Filename,Title,Full Name,First Name";
        try {
            new HeaderFields(line);
            fail("Did not throw exception for fewer than 5 fields");
        } catch (CSVException e) {
            assertEquals("Invalid header line. Does not contain at least:\n"
                    + "Filename,Title,Full Name,First Name,Last Name", e.getMessage());
        }
    }       
}
