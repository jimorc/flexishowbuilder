package com.github.jimorc.flexishowbuilder;

import java.io.IOException;
import java.io.FileNotFoundException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

// Many of these tests read from a file named "testing/data/test.csv"
// with the following content:
// Filename,Title,Full Name,First Name,Last Name
// image1.jpg,Image One,John Doe,John,Doe
// image2.jpg,"Image, Two",Jane Smith,Jane,Smith
//
// Other tests read from "testing/data/empty.csv" which is an empty file.
// Sort tests read from "testing/data/sort.csv" with the following content:
// Filename,Title,Full Name,First Name,Last Name
// image1.jpg,Image One,John Doe,John,Doe
// image2.jpg,"Image, Two",Jane Smith,Jane,Smith
// image3.jpg,Image Three,Bob Brown,Bob,Brown
// image4.jpg,Image Four,Wilma Flintstone,Wilma,Flintstone
// image5.jpg,Image Five,Fred Flintstone,Fred,Flintstone
// image6.jpg,Image Six,Fred Flintstone,Fred,Flintstone
// image7.jpg,Image Seven,Barney Rubble,Barney,Rubble
public class InputCSVTests {
    @Test
    void testBuildFileExists() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/test.csv")
                .build();
        } catch (FileNotFoundException fnfe) {
            fail("FileNotFoundException thrown: " + fnfe.getMessage());
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        assertNotNull(csv);
        assertEquals("testing/data", csv.getFileDir());
        assertEquals("test.csv", csv.getFileName());
    }

    @Test
    void testBuildFileDoesntExist() {
        assertThrows(FileNotFoundException.class, () -> {
            new InputCSV.Builder()
                .fileName("nonexistentfile.csv")
                .build(); });
    }

    @Test
    void testBuildNullFile() {
        // RuntimeException is thrown if no file is provided because
        // the file chooser dialog cannot be used in junit tests.
         assertThrows(RuntimeException.class, () -> {
            new InputCSV.Builder()
                .fileName(null)
                .build();
        });
    }

    @Test
    void testLoadCSVFile() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/test.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        }
        catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
       String expected = "Filename,Title,Full Name,First Name,Last Name\n" +
                      "image1.jpg,Image One,John Doe,John,Doe\n" +
                          "image2.jpg,\"Image, Two\",Jane Smith,Jane,Smith\n";
        assertEquals(expected, csv.toString());
    }

    @Test
    void testLoadEmptyCSVFile() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/empty.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        String expected = "";
        assertEquals(expected, csv.toString());
    }

    @Test
    void testInsertAt() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/test.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        CSVLine newLine = new ImageAndPersonLine("image4.jpg,\"Image, Two\",Bob Brown,Bob,Brown");
        csv.insertAt(1, newLine);
        assertEquals(4, csv.getNumberOfLines());
        assertEquals("image4.jpg", ((ImageAndPersonLine)csv.getLine(1)).getImageFileName());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.getLine(2)).getImageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.getLine(3)).getImageFileName());
    }

    @Test
    void testInsertAtBeginning() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/test.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }

        CSVLine newLine = new ImageAndPersonLine("image4.jpg,Image Four,Bob Brown,Bob,Brown");
        csv.insertAt(0, newLine);
        assertEquals(4, csv.getNumberOfLines());
        assertEquals("image4.jpg", ((ImageAndPersonLine)csv.getLine(0)).getImageFileName());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.getLine(2)).getImageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.getLine(3)).getImageFileName());
    }

    @Test
    void testInsertAtEnd() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/test.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
       CSVLine newLine = new ImageAndPersonLine("image4.jpg,Image Three,Bob Brown,Bob,Brown");
        csv.insertAt(3, newLine);
        assertEquals(4, csv.getNumberOfLines());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.getLine(1)).getImageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.getLine(2)).getImageFileName());
        assertEquals("image4.jpg", ((ImageAndPersonLine)csv.getLine(3)).getImageFileName());
    }

    @Test
    void testInsertAtInvalidIndex() {
       InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/test.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        CSVLine newLine = new ImageAndPersonLine("image3.jpg,Image Three,Bob Brown,Bob,Brown");
        try { 
            csv.insertAt(-1, newLine);
            fail("ArrayIndexOutOfBoundsException not thrown for negative index");
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            // expected
        }
        try {
            csv.insertAt(4, newLine);
            fail("ArrayIndexOutOfBoundsException not thrown for index greater than number of lines");
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            // expected
        }
    }

        @Test
    void testAppend() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/test.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        CSVLine newLine = new ImageAndPersonLine("image4.jpg,Image Four,Bob Brown,Bob,Brown");
        csv.append(newLine);
        assertEquals(4, csv.getNumberOfLines());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.getLine(1)).getImageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.getLine(2)).getImageFileName());
        assertEquals("image4.jpg", ((ImageAndPersonLine)csv.getLine(3)).getImageFileName());
    }

    @Test
    void testAppendToEmptyCSV() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/empty.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        CSVLine newLine = new ImageAndPersonLine("image1.jpg,Image One,John Doe,John,Doe");
        csv.append(newLine);
        assertEquals(1, csv.getNumberOfLines());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.getLine(0)).getImageFileName());
    }

    @Test
    void testAppendMultiple() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/empty.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        CSVLine line1 = new ImageAndPersonLine("image1.jpg,Image One,John Doe,John,Doe");
        CSVLine line2 = new ImageAndPersonLine("image2.jpg,\"Image, Two\",Jane Smith,Jane,Smith");
        CSVLine line3 = new ImageAndPersonLine("image3.jpg,Image Three,Bob Brown,Bob,Brown");
        csv.append(line1);
        csv.append(line2);
        csv.append(line3);
        assertEquals(3, csv.getNumberOfLines());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.getLine(0)).getImageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.getLine(1)).getImageFileName());
        assertEquals("image3.jpg", ((ImageAndPersonLine)csv.getLine(2)).getImageFileName());
    }

        @Test
    void testSortAlphaByFullName() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/sort.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        csv.sortNames(sortOrder.ALPHABETICAL_BY_FULL_NAME);
        assertEquals(5, csv.getSortedFullNames().size());
        assertEquals("Barney Rubble", csv.getSortedFullNames().get(0));
        assertEquals("Fred Flintstone", csv.getSortedFullNames().get(1));
        assertEquals("Jane Smith", csv.getSortedFullNames().get(2));
        assertEquals("John Doe", csv.getSortedFullNames().get(3));
        assertEquals("Wilma Flintstone", csv.getSortedFullNames().get(4));
        assertEquals(7, csv.getNumberOfLines());
        assertEquals("Barney Rubble", ((ImageAndPersonLine)csv.getLine(1)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(2)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(3)).getPersonFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.getLine(4)).getPersonFullName());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.getLine(5)).getPersonFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine)csv.getLine(6)).getPersonFullName());
        assertEquals(5, csv.getHashMap().size());
        assertEquals(5, csv.getFullNameKeys().size());
    }

    @Test
    void testSortAlphaByFullNameReverseOrder() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/sort.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        csv.sortNames(sortOrder.ALPHABETICAL_BY_FULL_NAME_REVERSE);
        assertEquals(5, csv.getSortedFullNames().size());
        assertEquals("Wilma Flintstone", csv.getSortedFullNames().get(0));
        assertEquals("John Doe", csv.getSortedFullNames().get(1));
        assertEquals("Jane Smith", csv.getSortedFullNames().get(2));
        assertEquals("Fred Flintstone", csv.getSortedFullNames().get(3));
        assertEquals("Barney Rubble", csv.getSortedFullNames().get(4));
        assertEquals(7, csv.getNumberOfLines());
        assertEquals("Barney Rubble", ((ImageAndPersonLine)csv.getLine(6)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(4)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(5)).getPersonFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.getLine(3)).getPersonFullName());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.getLine(2)).getPersonFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine)csv.getLine(1)).getPersonFullName());
        assertEquals(5, csv.getHashMap().size());
        assertEquals(5, csv.getFullNameKeys().size());
    }

    @Test
    void testSortAlphaByLastNameFirstName() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/sort.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        csv.sortNames(sortOrder.ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME);
        assertEquals(5, csv.getSortedFullNames().size());
        assertEquals("John Doe", csv.getSortedFullNames().get(0));
        assertEquals("Fred Flintstone", csv.getSortedFullNames().get(1));
        assertEquals("Wilma Flintstone", csv.getSortedFullNames().get(2));
        assertEquals("Barney Rubble", csv.getSortedFullNames().get(3));
        assertEquals("Jane Smith", csv.getSortedFullNames().get(4));
        assertEquals(7, csv.getNumberOfLines());
        assertEquals("Barney Rubble", ((ImageAndPersonLine)csv.getLine(5)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(3)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(2)).getPersonFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine)csv.getLine(4)).getPersonFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.getLine(6)).getPersonFullName());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.getLine(1)).getPersonFullName());
        assertEquals(5, csv.getHashMap().size());
        assertEquals(5, csv.getFullNameKeys().size());
    }

@Test
    void testSortAlphaByLastNameFirstNameReverseOrder() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/sort.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        csv.sortNames(sortOrder.ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME_REVERSE);
        assertEquals(5, csv.getSortedFullNames().size());
        assertEquals("Jane Smith", csv.getSortedFullNames().get(0));
        assertEquals("Barney Rubble", csv.getSortedFullNames().get(1));
        assertEquals("Wilma Flintstone", csv.getSortedFullNames().get(2));
        assertEquals("Fred Flintstone", csv.getSortedFullNames().get(3));
        assertEquals("John Doe", csv.getSortedFullNames().get(4));
        assertEquals(7, csv.getNumberOfLines());
        assertEquals("Barney Rubble", ((ImageAndPersonLine)csv.getLine(2)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(4)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(5)).getPersonFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine)csv.getLine(3)).getPersonFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.getLine(1)).getPersonFullName());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.getLine(6)).getPersonFullName());
        assertEquals(5, csv.getHashMap().size());
        assertEquals(5, csv.getFullNameKeys().size());
    }

   @Test
    void testSortNone() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/sort.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        csv.sortNames(sortOrder.ASIS);
        assertEquals(5, csv.getSortedFullNames().size());
        assertEquals("John Doe", csv.getSortedFullNames().get(0));
        assertEquals("Jane Smith", csv.getSortedFullNames().get(1));
        assertEquals("Fred Flintstone", csv.getSortedFullNames().get(2));
        assertEquals("Barney Rubble", csv.getSortedFullNames().get(3));
        assertEquals("Wilma Flintstone", csv.getSortedFullNames().get(4));
        assertEquals(7, csv.getNumberOfLines());
        assertEquals("Barney Rubble", ((ImageAndPersonLine)csv.getLine(5)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(3)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(4)).getPersonFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine)csv.getLine(6)).getPersonFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.getLine(2)).getPersonFullName());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.getLine(1)).getPersonFullName());
        assertEquals(5, csv.getHashMap().size());
        assertEquals(5, csv.getFullNameKeys().size());
    }

    @Test
    void testValidateCSVFileEmpty() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/empty.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        try {
            csv.validateCSVFile();
            fail ("CSVException not thrown for empty CSV file");
        } catch (CSVException csve) {
            String expectedMessage = "No data found in CSV file empty.csv";
            String actualMessage = csve.getMessage();
            assertEquals(expectedMessage, actualMessage);
        }
    }  

    @Test
    void testValidateCSVFileZerodHeaderLength() {
        try {
            new InputCSV.Builder()
                .fileName("testing/data/zeroheaderlength.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            String expectedMessage = "Invalid header found in CSV file zeroheaderlength.csv";
            String actualMessage = csve.getMessage();
            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Test
    void testValidateCSVFileInvalidHeader() {
        try {
            new InputCSV.Builder()
                .fileName("testing/data/invalidline.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            String expectedMessage = "Invalid line number 2 found in CSV file invalidline.csv";
            String actualMessage = csve.getMessage();
            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Test
    void testGetPersonValid() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/test.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " +csve.getMessage());
        }

        try {
        Person john = csv.getPerson("John Doe");
        assertNotNull(john);
        assertEquals("John Doe", john.getFullName());
        } catch(Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }


    @Test
    void testGetPersonInvalid() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/test.csv")
                .build();
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " +csve.getMessage());
        }

        try {
            csv.getPerson("Fred Smith");
            fail("No exception thrown for invalid name");
        } catch (CSVException e) {
            String expectedMessage = "Programming error: Trying to retrieve info for Fred Smith but it does not exist.";
            String actualMessage = e.getMessage();
            assertEquals(expectedMessage, actualMessage);

        }
    }

    @Test
    void testGetImageLinesValidName() {
                InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/sort.csv")
                .build();
            csv.sortNames(sortOrder.ALPHABETICAL_BY_FULL_NAME);
            ImageAndPersonLine[] lines = csv.getImageLines("John Doe");
            assertEquals(1, lines.length);
            assertEquals("image1.jpg,Image One,John Doe,John,Doe", lines[0].toString());

            lines = csv.getImageLines("Fred Flintstone");
            assertEquals(2, lines.length);
            assertEquals("DSC-0424.jpg,My Image,Fred Flintstone,Fred,Flintstone", lines[0].toString());
            assertEquals("IMG-276.jpg,My Next Image,Fred Flintstone,Fred,Flintstone", lines[1].toString());
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
    }

    @Test
    void testGetImageLinesInvalidName() {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder()
                .fileName("testing/data/sort.csv")
                .build();
            csv.sortNames(sortOrder.ALPHABETICAL_BY_FULL_NAME);
            csv.getImageLines("Bob Brown");
            fail("Should have thrown CSVException for invalid name.");
        } catch(CSVException csve) {
            assertEquals("The CSV file does not contain lines for Bob Brown", csve.getMessage());
        } catch(IOException ioe) {
            fail(ioe.getMessage());
        }
    }
}