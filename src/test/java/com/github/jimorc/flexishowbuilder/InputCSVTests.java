package com.github.jimorc.flexishowbuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
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
/*  *
 * InputCSVTests contains tests of methods in InputCSV class.
 */
public class InputCSVTests {
    @Test
    void testConstructor() {
        File f = new File("testing/data/sort.csv");
        try {
            InputCSV iCSV = new InputCSV(f);
            assertNotNull(iCSV);
        } catch (CSVException ce) {
            fail(ce.getMessage());
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    void testConstructorNonexistentFile() {
        File f = new File("testing/data/notafile.csv");
        assertThrows(CSVException.class, () -> new InputCSV(f));
    }

    @Test
    void testConstructorNoHeaderInFile() {
        File f = new File("testing/data/zeroheaderlength.csv");
        assertThrows(CSVException.class, () -> new InputCSV(f));
    }

    @Test
    void testConstructorInvalidLineInFile() {
        File f = new File("testing/data/invalidline.csv");
        assertThrows(CSVException.class, () -> new InputCSV(f));
    }

    @Test
    void testConstructorNotAFile() {
        File f = new File("testing/data");
        assertThrows(CSVException.class, () -> new InputCSV(f));
    }

    @Test
    void testConstructorIOError() {
        Path path = Path.of("testing/data/temp.csv");
        File f = new File("testing/data/temp.csv");
        try {
            Files.writeString(path, "A bunch of text");
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
        try {
            // make it so an IOException will be thrown when trying to read the file.
            f.setReadable(false);
            new InputCSV(f);
        } catch (CSVException ce) {
            f.delete();
            fail("Threw CSVException, not IOException");
        } catch (IOException ioe) {
            f.delete();
            return;
        }
        fail("Did not throw IOException");
    }

    @Test
    void testInsertAt() {
        final int line1 = 1;
        final int line2 = 2;
        final int line3 = 3;
        InputCSV csv = null;
        try {
            File f = new File("testing/data/test.csv");
            csv = new InputCSV(f);
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        final int numLines = csv.getNumberOfLines();
        CSVLine newLine = new ImageAndPersonLine("image4.jpg,\"Image, Two\",Bob Brown,Bob,Brown");
        csv.insertAt(1, newLine);
        assertEquals(numLines + 1, csv.getNumberOfLines());
        assertEquals("image4.jpg", ((ImageAndPersonLine) csv.getLine(line1)).getImageFileName());
        assertEquals("image1.jpg", ((ImageAndPersonLine) csv.getLine(line2)).getImageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine) csv.getLine(line3)).getImageFileName());
    }

    @Test
    void testInsertAtBeginning() {
        final int numLines = 4;
        final int line0 = 0;
        final int line2 = 2;
        final int line3 = 3;
        InputCSV csv = null;
        try {
            File f = new File("testing/data/test.csv");
            csv = new InputCSV(f);
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }

        CSVLine newLine = new ImageAndPersonLine("image4.jpg,Image Four,Bob Brown,Bob,Brown");
        csv.insertAt(0, newLine);
        assertEquals(numLines, csv.getNumberOfLines());
        assertEquals("image4.jpg", ((ImageAndPersonLine) csv.getLine(line0)).getImageFileName());
        assertEquals("image1.jpg", ((ImageAndPersonLine) csv.getLine(line2)).getImageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine) csv.getLine(line3)).getImageFileName());
    }

    @Test
    void testInsertAtEnd() {
        final int numLines = 4;
        final int line1 = 1;
        final int line2 = 2;
        final int line3 = 3;
        InputCSV csv = null;
        try {
            File f = new File("testing/data/test.csv");
            csv = new InputCSV(f);
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        CSVLine newLine = new ImageAndPersonLine("image4.jpg,Image Three,Bob Brown,Bob,Brown");
        csv.insertAt(line3, newLine);
        assertEquals(numLines, csv.getNumberOfLines());
        assertEquals("image1.jpg", ((ImageAndPersonLine) csv.getLine(line1)).getImageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine) csv.getLine(line2)).getImageFileName());
        assertEquals("image4.jpg", ((ImageAndPersonLine) csv.getLine(line3)).getImageFileName());
    }

    @Test
    void testInsertAtInvalidIndex() {
        final int minus1 = -1;
        InputCSV csv = null;
        try {
            File f = new File("testing/data/test.csv");
            csv = new InputCSV(f);
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        CSVLine newLine = new ImageAndPersonLine("image3.jpg,Image Three,Bob Brown,Bob,Brown");
        try {
            csv.insertAt(minus1, newLine);
            fail("ArrayIndexOutOfBoundsException not thrown for negative index");
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            // expected
        }
        try {
            csv.insertAt(csv.getNumberOfLines() + 1, newLine);
            fail("ArrayIndexOutOfBoundsException not thrown for index greater than number of lines");
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            // expected
        }
    }

    @Test
    void testAppend() {
        final int numLines = 4;
        final int line1 = 1;
        final int line2 = 2;
        final int line3 = 3;
        InputCSV csv = null;
        try {
            File f = new File("testing/data/test.csv");
            csv = new InputCSV(f);
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        CSVLine newLine = new ImageAndPersonLine("image4.jpg,Image Four,Bob Brown,Bob,Brown");
        csv.append(newLine);
        assertEquals(numLines, csv.getNumberOfLines());
        assertEquals("image1.jpg", ((ImageAndPersonLine) csv.getLine(line1)).getImageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine) csv.getLine(line2)).getImageFileName());
        assertEquals("image4.jpg", ((ImageAndPersonLine) csv.getLine(line3)).getImageFileName());
    }

    @Test
    void testAppendToEmptyCSV() {
        InputCSV csv = null;
        try {
            File f = new File("testing/data/empty.csv");
            csv = new InputCSV(f);
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        CSVLine newLine = new ImageAndPersonLine("image1.jpg,Image One,John Doe,John,Doe");
        csv.append(newLine);
        assertEquals(1, csv.getNumberOfLines());
        assertEquals("image1.jpg", ((ImageAndPersonLine) csv.getLine(0)).getImageFileName());
    }

    @Test
    void testAppendMultiple() {
        final int numLines = 3;
        final int firstLine = 0;
        final int secondLine = 1;
        final int thirdLine = 2;
        InputCSV csv = null;
        try {
            File f = new File("testing/data/empty.csv");
            csv = new InputCSV(f);
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
        assertEquals(numLines, csv.getNumberOfLines());
        assertEquals("image1.jpg", ((ImageAndPersonLine) csv.getLine(firstLine)).getImageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine) csv.getLine(secondLine)).getImageFileName());
        assertEquals("image3.jpg", ((ImageAndPersonLine) csv.getLine(thirdLine)).getImageFileName());
    }

    @Test
    void testSortAlphaByFullName() {
        // number of names in InputCSV.sortedFullNames array.
        final int sortedNamesSize = 5;
        // number of InputCSVLines in resorted InputCSV.
        final int sortFileLines = 7;
        // Order the full names are sorted in AlphabeticalByFullName.
        final int jsLFFullNames = 2;
        final int brLFFullNames = 0;
        final int wfLFFullNames = 4;
        final int ffLFFullNames = 1;
        final int jdLFFullNames = 3;
        // position of Barney Rubble InputAndPersonLine in sorted InputCSV.
        final int brLine = 1;
        // position of first Fred Flintstone InputAndPersonLine in sorted InputCSV.
        final int ffLine1 = 2;
        // position of second Fred Flintstone InputAndPersonLine in sorted InputCSV.
        final int ffLine2 = 3;
        // position of Wilma Flintstone InputAndPersonLine in sorted InputCSV.
        final int wfLine = 6;
        // position of Jane Smith InputAndPersonLine in sorted InputCSV.
        final int jsLine = 4;
        // position of John Doe InputAndPersonLine in sorted InputCSV.
        final int jdLine = 5;
        InputCSV csv = null;
        try {
            File f = new File("testing/data/sort.csv");
            csv = new InputCSV(f);
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        csv.sortNames(SortOrder.AlphabeticalByFullName);
        assertEquals(sortedNamesSize, csv.getSortedFullNames().size());
        assertEquals("Barney Rubble", csv.getSortedFullNames().get(brLFFullNames));
        assertEquals("Fred Flintstone", csv.getSortedFullNames().get(ffLFFullNames));
        assertEquals("Jane Smith", csv.getSortedFullNames().get(jsLFFullNames));
        assertEquals("John Doe", csv.getSortedFullNames().get(jdLFFullNames));
        assertEquals("Wilma Flintstone", csv.getSortedFullNames().get(wfLFFullNames));
        assertEquals(sortFileLines, csv.getNumberOfLines());
        assertEquals("Barney Rubble", ((ImageAndPersonLine) csv.getLine(brLine)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine) csv.getLine(ffLine1)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine) csv.getLine(ffLine2)).getPersonFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine) csv.getLine(jsLine)).getPersonFullName());
        assertEquals("John Doe", ((ImageAndPersonLine) csv.getLine(jdLine)).getPersonFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine) csv.getLine(wfLine)).getPersonFullName());
        assertEquals(sortedNamesSize, csv.getHashMap().size());
        assertEquals(sortedNamesSize, csv.getFullNameKeys().size());
    }

    @Test
    void testSortAlphaByFullNameReverseOrder() {
        // number of names in InputCSV.sortedFullNames array.
        final int sortedNamesSize = 5;
        // number of InputCSVLines in resorted InputCSV.
        final int sortFileLines = 7;
        // Order the full names are sorted in AlpahbeticalByFullNameReverse.
        final int jsLFFullNames = 2;
        final int brLFFullNames = 4;
        final int wfLFFullNames = 0;
        final int ffLFFullNames = 3;
        final int jdLFFullNames = 1;
        // position of Barney Rubble InputAndPersonLine in sorted InputCSV.
        final int brLine = 6;
        // position of first Fred Flintstone InputAndPersonLine in sorted InputCSV.
        final int ffLine1 = 4;
        // position of second Fred Flintstone InputAndPersonLine in sorted InputCSV.
        final int ffLine2 = 5;
        // position of Wilma Flintstone InputAndPersonLine in sorted InputCSV.
        final int wfLine = 1;
        // position of Jane Smith InputAndPersonLine in sorted InputCSV.
        final int jsLine = 3;
        // position of John Doe InputAndPersonLine in sorted InputCSV.
        final int jdLine = 2;
        InputCSV csv = null;
        try {
            File f = new File("testing/data/sort.csv");
            csv = new InputCSV(f);
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        csv.sortNames(SortOrder.AlphabeticalByFullNameReverse);
        assertEquals(sortedNamesSize, csv.getSortedFullNames().size());
        assertEquals("Wilma Flintstone", csv.getSortedFullNames().get(wfLFFullNames));
        assertEquals("John Doe", csv.getSortedFullNames().get(jdLFFullNames));
        assertEquals("Jane Smith", csv.getSortedFullNames().get(jsLFFullNames));
        assertEquals("Fred Flintstone", csv.getSortedFullNames().get(ffLFFullNames));
        assertEquals("Barney Rubble", csv.getSortedFullNames().get(brLFFullNames));
        assertEquals(sortFileLines, csv.getNumberOfLines());
        assertEquals("Barney Rubble", ((ImageAndPersonLine) csv.getLine(brLine)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine) csv.getLine(ffLine1)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine) csv.getLine(ffLine2)).getPersonFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine) csv.getLine(jsLine)).getPersonFullName());
        assertEquals("John Doe", ((ImageAndPersonLine) csv.getLine(jdLine)).getPersonFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine) csv.getLine(wfLine)).getPersonFullName());
        assertEquals(sortedNamesSize, csv.getHashMap().size());
        assertEquals(sortedNamesSize, csv.getFullNameKeys().size());
    }

    @Test
    void testSortAlphaByLastNameFirstName() {
        // number of names in InputCSV.sortedFullNames array.
        final int sortedNamesSize = 5;
        // number of InputCSVLines in resorted InputCSV.
        final int sortFileLines = 7;
        // Order the full names are sorted in AlpabeticalByLastNameThenFirstName.
        final int jsLFFullNames = 0;
        final int brLFFullNames = 3;
        final int wfLFFullNames = 2;
        final int ffLFFullNames = 1;
        final int jdLFFullNames = 4;
        // position of Barney Rubble InputAndPersonLine in sorted InputCSV.
        final int brLine = 5;
        // position of first Fred Flintstone InputAndPersonLine in sorted InputCSV.
        final int ffLine1 = 2;
        // position of second Fred Flintstone InputAndPersonLine in sorted InputCSV.
        final int ffLine2 = 3;
        // position of Wilma Flintstone InputAndPersonLine in sorted InputCSV.
        final int wfLine = 4;
        // position of Jane Smith InputAndPersonLine in sorted InputCSV.
        final int jsLine = 6;
        // position of John Doe InputAndPersonLine in sorted InputCSV.
        final int jdLine = 1;
        InputCSV csv = null;
        try {
            File f = new File("testing/data/sort.csv");
            csv = new InputCSV(f);
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        csv.sortNames(SortOrder.AlphabeticalByLastNameThenFirstName);
        assertEquals(sortedNamesSize, csv.getSortedFullNames().size());
        assertEquals("John Doe", csv.getSortedFullNames().get(jsLFFullNames));
        assertEquals("Fred Flintstone", csv.getSortedFullNames().get(ffLFFullNames));
        assertEquals("Wilma Flintstone", csv.getSortedFullNames().get(wfLFFullNames));
        assertEquals("Barney Rubble", csv.getSortedFullNames().get(brLFFullNames));
        assertEquals("Jane Smith", csv.getSortedFullNames().get(jdLFFullNames));
        assertEquals(sortFileLines, csv.getNumberOfLines());
        assertEquals("Barney Rubble", ((ImageAndPersonLine) csv.getLine(brLine)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine) csv.getLine(ffLine1)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine) csv.getLine(ffLine2)).getPersonFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine) csv.getLine(wfLine)).getPersonFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine) csv.getLine(jsLine)).getPersonFullName());
        assertEquals("John Doe", ((ImageAndPersonLine) csv.getLine(jdLine)).getPersonFullName());
        assertEquals(sortedNamesSize, csv.getHashMap().size());
        assertEquals(sortedNamesSize, csv.getFullNameKeys().size());
    }

    @Test
    void testSortAlphaByLastNameFirstNameReverseOrder() {
        // number of names in InputCSV.sortedFullNames array.
        final int sortedNamesSize = 5;
        // number of InputCSVLines in resorted InputCSV.
        final int sortFileLines = 7;
        // Order the full names are sorted in ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME_REVERSE.
        final int jsLFFullNames = 0;
        final int brLFFullNames = 1;
        final int wfLFFullNames = 2;
        final int ffLFFullNames = 3;
        final int jdLFFullNames = 4;
        // position of Barney Rubble InputAndPersonLine in sorted InputCSV.
        final int brLine = 2;
        // position of first Fred Flintstone InputAndPersonLine in sorted InputCSV.
        final int ffLine1 = 4;
        // position of second Fred Flintstone InputAndPersonLine in sorted InputCSV.
        final int ffLine2 = 5;
        // position of Wilma Flintstone InputAndPersonLine in sorted InputCSV.
        final int wfLine = 3;
        // position of Jane Smith InputAndPersonLine in sorted InputCSV.
        final int jsLine = 1;
        // position of John Doe InputAndPersonLine in sorted InputCSV.
        final int jdLine = 6;

        // Order the sorted CSVLines are sorted in AlpabeticalByLastNameThenFirstNameReverse.
        InputCSV csv = null;
        try {
            File f = new File("testing/data/sort.csv");
            csv = new InputCSV(f);
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        csv.sortNames(SortOrder.AlphabeticalByLastNameThenFirstNameReverse);
        assertEquals(sortedNamesSize, csv.getSortedFullNames().size());
        assertEquals("Jane Smith", csv.getSortedFullNames().get(jsLFFullNames));
        assertEquals("Barney Rubble", csv.getSortedFullNames().get(brLFFullNames));
        assertEquals("Wilma Flintstone", csv.getSortedFullNames().get(wfLFFullNames));
        assertEquals("Fred Flintstone", csv.getSortedFullNames().get(ffLFFullNames));
        assertEquals("John Doe", csv.getSortedFullNames().get(jdLFFullNames));
        assertEquals(sortFileLines, csv.getNumberOfLines());
        assertEquals("Barney Rubble", ((ImageAndPersonLine) csv.getLine(brLine)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine) csv.getLine(ffLine1)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine) csv.getLine(ffLine2)).getPersonFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine) csv.getLine(wfLine)).getPersonFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine) csv.getLine(jsLine)).getPersonFullName());
        assertEquals("John Doe", ((ImageAndPersonLine) csv.getLine(jdLine)).getPersonFullName());
        assertEquals(sortedNamesSize, csv.getHashMap().size());
        assertEquals(sortedNamesSize, csv.getFullNameKeys().size());
    }

    @Test
    void testSortNone() {
        // number of names in InputCSV.sortedFullNames array.
        final int sortedNamesSize = 5;
        // number of InputCSVLines in resorted InputCSV.
        final int sortFileLines = 7;
        // position of John Doe in sortedFullNames array.
        final int jdFullNamesAsIs = 0;
        // position of Jane Smith in sortedFullNames array.
        final int jsFullNamesAsIs = 1;
        // position of Fred Flintstone in sortedFullNames array.
        final int ffFullNamesAsIs = 2;
        // position of Barney Rubble in sortedFullNames array.
        final int brFullNamesAsIs = 3;
        // position of Wilma Flintstone in sortedFullNames array.
        final int wfFullNamesAsIs = 4;
        // position of Barney Rubble InputAndPersonLine in sorted InputCSV.
        final int brLine = 5;
        // position of first Fred Flintstone InputAndPersonLine in sorted InputCSV.
        final int ffLine1 = 3;
        // position of second Fred Flintstone InputAndPersonLine in sorted InputCSV.
        final int ffLine2 = 4;
        // position of Wilma Flintstone InputAndPersonLine in sorted InputCSV.
        final int wfLine = 6;
        // position of Jane Smith InputAndPersonLine in sorted InputCSV.
        final int jsLine = 2;
        // position of John Doe InputAndPersonLine in sorted InputCSV.
        final int jdLine = 1;

        InputCSV csv = null;
        try {
            File f = new File("testing/data/sort.csv");
            csv = new InputCSV(f);
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }
        csv.sortNames(SortOrder.AsIs);

        assertEquals(sortedNamesSize, csv.getSortedFullNames().size());
        assertEquals("John Doe", csv.getSortedFullNames().get(jdFullNamesAsIs));
        assertEquals("Jane Smith", csv.getSortedFullNames().get(jsFullNamesAsIs));
        assertEquals("Fred Flintstone", csv.getSortedFullNames().get(ffFullNamesAsIs));
        assertEquals("Barney Rubble", csv.getSortedFullNames().get(brFullNamesAsIs));
        assertEquals("Wilma Flintstone", csv.getSortedFullNames().get(wfFullNamesAsIs));
        assertEquals(sortFileLines, csv.getNumberOfLines());
        assertEquals("Barney Rubble", ((ImageAndPersonLine) csv.getLine(brLine)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine) csv.getLine(ffLine1)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine) csv.getLine(ffLine2)).getPersonFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine) csv.getLine(wfLine)).getPersonFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine) csv.getLine(jsLine)).getPersonFullName());
        assertEquals("John Doe", ((ImageAndPersonLine) csv.getLine(jdLine)).getPersonFullName());
        assertEquals(sortedNamesSize, csv.getHashMap().size());
        assertEquals(sortedNamesSize, csv.getFullNameKeys().size());
    }

    @Test
    void testValidateCSVFileEmpty() {
        try {
            File f = new File("testing/data/empty.csv");
            InputCSV csv = new InputCSV(f);
            csv.validateCSVFile();

        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            String expectedMessage = "No data found in CSV file empty.csv";
            String actualMessage = csve.getMessage();
            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Test
    void testValidateCSVFileZerodHeaderLength() {
        try {
            File f = new File("testing/data/zeroheaderlength.csv");
            InputCSV csv = new InputCSV(f);
            assertThrows(Exception.class, () -> csv.validateCSVFile());
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
            File f = new File("testing/data/invalidline.csv");
            InputCSV csv = new InputCSV(f);
            assertThrows(Exception.class, () -> csv.validateCSVFile());

        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            String expectedMessage = "Invalid line number 2 found in CSV file invalidline.csv"
                + "\nLine does not contain at least 5 fields.";
            String actualMessage = csve.getMessage();
            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Test
    void testGetPersonValid() {
        InputCSV csv = null;
        try {
            File f = new File("testing/data/test.csv");
            csv = new InputCSV(f);
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
        }

        try {
            Person john = csv.getPerson("John Doe");
            assertNotNull(john);
            assertEquals("John Doe", john.getFullName());
        } catch (CSVException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testGetPersonInvalid() {
        InputCSV csv = null;
        try {
            File f = new File("testing/data/test.csv");
            csv = new InputCSV(f);
        } catch (IOException ioe) {
            fail("IOException thrown: " + ioe.getMessage());
        } catch (CSVException csve) {
            fail("CSVException thrown: " + csve.getMessage());
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
            File f = new File("testing/data/sort.csv");
            csv = new InputCSV(f);
            csv.sortNames(SortOrder.AlphabeticalByFullName);
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
            File f = new File("testing/data/sort.csv");
            csv = new InputCSV(f);
            csv.sortNames(SortOrder.AlphabeticalByFullName);
            csv.getImageLines("Bob Brown");
            fail("Should have thrown CSVException for invalid name.");
        } catch (CSVException csve) {
            assertEquals("The CSV file does not contain lines for Bob Brown", csve.getMessage());
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }
}
