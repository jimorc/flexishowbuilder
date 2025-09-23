package flexishowbuilder;

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
public class CSVTests {
    @Test
    void testBuildFileExists() {
        CSV csv = new CSV.Builder()
            .fileName(System.getProperty("user.dir")+"/testing/data/test.csv")
            .build();

        assertNotNull(csv);
        assertEquals(System.getProperty("user.dir")+"/testing/data", csv.getFileDir());
        assertEquals("test.csv", csv.getFileName());
    }

    @Test
    void testBiuldFileDoesntExist() {
        CSV csv2 = new CSV.Builder()
            .fileName("nonexistentfile.csv")
            .build();  

            assertNull(csv2);
    }

    @Test
    void testBuildNullFile() {
        // RuntimeException is thrown if no file is provided because
        // the file chooser dialog cannot be used in junit tests.
         assertThrows(RuntimeException.class, () -> {
            new CSV.Builder()
                .fileName(null)
                .build();
        });
    }

    @Test
    void testLoadCSVFile() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/test.csv")
            .build();
       String expected = "Filename,Title,Full Name,First Name,Last Name\n" +
                      "image1.jpg,Image One,John Doe,John,Doe\n" +
                          "image2.jpg,\"Image, Two\",Jane Smith,Jane,Smith\n";
        assertEquals(expected, csv.toString());
    }

    @Test
    void testLoadEmptyCSVFile() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/empty.csv")
            .build();
        String expected = "";
        assertEquals(expected, csv.toString());
    }

    @Test
    void testInsertAt() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/test.csv")
            .build();
        CSVLine newLine = new ImageAndPersonLine("image4.jpg,\"Image, Two\",Bob Brown,Bob,Brown");
        csv.insertAt(1, newLine);
        assertEquals(4, csv.getNumberOfLines());
        assertEquals("image4.jpg", ((ImageAndPersonLine)csv.getLine(1)).getImageFileName());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.getLine(2)).getImageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.getLine(3)).getImageFileName());
    }

    @Test
    void testInsertAtBeginning() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/test.csv")
            .build();
        CSVLine newLine = new ImageAndPersonLine("image4.jpg,Image Four,Bob Brown,Bob,Brown");
        csv.insertAt(0, newLine);
        assertEquals(4, csv.getNumberOfLines());
        assertEquals("image4.jpg", ((ImageAndPersonLine)csv.getLine(0)).getImageFileName());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.getLine(2)).getImageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.getLine(3)).getImageFileName());
    }

    @Test
    void testInsertAtEnd() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/test.csv")
            .build();
       CSVLine newLine = new ImageAndPersonLine("image4.jpg,Image Three,Bob Brown,Bob,Brown");
        csv.insertAt(3, newLine);
        assertEquals(4, csv.getNumberOfLines());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.getLine(1)).getImageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.getLine(2)).getImageFileName());
        assertEquals("image4.jpg", ((ImageAndPersonLine)csv.getLine(3)).getImageFileName());
    }

    @Test
    void testInsertAtInvalidIndex() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/test.csv")
            .build();
        CSVLine newLine = new ImageAndPersonLine("image3.jpg,Image Three,Bob Brown,Bob,Brown");
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
            csv.insertAt(-1, newLine));
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
            csv.insertAt(4, newLine));
    }

        @Test
    void testAppend() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/test.csv")
            .build();
        CSVLine newLine = new ImageAndPersonLine("image4.jpg,Image Four,Bob Brown,Bob,Brown");
        csv.append(newLine);
        assertEquals(4, csv.getNumberOfLines());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.getLine(1)).getImageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.getLine(2)).getImageFileName());
        assertEquals("image4.jpg", ((ImageAndPersonLine)csv.getLine(3)).getImageFileName());
    }

    @Test
    void testAppendToEmptyCSV() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/empty.csv")
            .build();
        CSVLine newLine = new ImageAndPersonLine("image1.jpg,Image One,John Doe,John,Doe");
        csv.append(newLine);
        assertEquals(1, csv.getNumberOfLines());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.getLine(0)).getImageFileName());
    }

    @Test
    void testAppendMultiple() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/empty.csv")
            .build();
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
        CSV csv = new CSV.Builder()
            .fileName("testing/data/sort.csv")
            .build();
        csv.sort(sortOrder.ALPHABETICAL_BY_FULL_NAME);
        assertEquals(7, csv.getNumberOfLines());
        assertEquals("Barney Rubble", ((ImageAndPersonLine)csv.getLine(1)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(2)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(3)).getPersonFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.getLine(4)).getPersonFullName());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.getLine(5)).getPersonFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine)csv.getLine(6)).getPersonFullName());
    }

    @Test
    void testSortAlphaByFullNameReverseOrder() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/sort.csv")
            .build();
        csv.sort(sortOrder.ALPHABETICAL_BY_FULL_NAME_REVERSE);
        assertEquals(7, csv.getNumberOfLines());
        assertEquals("Barney Rubble", ((ImageAndPersonLine)csv.getLine(6)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(4)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(5)).getPersonFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.getLine(3)).getPersonFullName());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.getLine(2)).getPersonFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine)csv.getLine(1)).getPersonFullName());
    }

    @Test
    void testSortAlphaByLastNameFirstName() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/sort.csv")
            .build();
        csv.sort(sortOrder.ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME);
        assertEquals(7, csv.getNumberOfLines());
        assertEquals("Barney Rubble", ((ImageAndPersonLine)csv.getLine(5)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(3)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(2)).getPersonFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine)csv.getLine(4)).getPersonFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.getLine(6)).getPersonFullName());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.getLine(1)).getPersonFullName());
    }

@Test
    void testSortAlphaByLastNameFirstNameREverseOrder() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/sort.csv")
            .build();
        csv.sort(sortOrder.ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME_REVERSE);
        assertEquals(7, csv.getNumberOfLines());
        assertEquals("Barney Rubble", ((ImageAndPersonLine)csv.getLine(2)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(4)).getPersonFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.getLine(5)).getPersonFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine)csv.getLine(3)).getPersonFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.getLine(1)).getPersonFullName());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.getLine(6)).getPersonFullName());
    }
}