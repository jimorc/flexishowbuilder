package flexishowbuilder;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CSVExceptionTests {
    @Test
    public void testGetMessageNoHeader() {
        CSVException ex = new CSVException(CSVExceptionType.NOHEADER, "test.csv", null);
        assertEquals("CSVException: No header found in CSV file test.csv", ex.getMessage());
    }

    @Test
    public void testGetMessageInvalidHeader() {
        CSVException ex = new CSVException(CSVExceptionType.INVALIDHEADER, "test.csv", null);
        assertEquals("CSVException: Invalid header found in CSV file test.csv", ex.getMessage());
    }

    @Test
    public void testGetMessageEmpty() {
        CSVException ex = new CSVException(CSVExceptionType.EMPTY, "test.csv", null);
        assertEquals("CSVException: No data found in CSV file test.csv", ex.getMessage());
    }

    @Test
    public void testGetMessageMissingImages() {
        List<String> missingImages = List.of("image1.jpg", "image2.jpg");
        CSVException ex = new CSVException(CSVExceptionType.MISSINGIMAGES, "test.csv", missingImages);
        String expectedMessage = "CSVException: Some images listed in CSV file test.csv are missing:\n" +
                                 "image1.jpg\n" +
                                 "image2.jpg\n";
        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    public void testGetMessageUnknown() {
        CSVException ex = new CSVException(CSVExceptionType.UNKNOWN, "test.csv", null);
        assertEquals("CSVException: Unknown error with CSV file test.csv", ex.getMessage());
    }
}
