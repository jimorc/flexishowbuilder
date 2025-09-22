package flexishowbuilder;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

// Most of these tests read from a file named "testing/data/test.csv"
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
}