package flexishowbuilder;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CSVExceptionTests {
    @Test
    public void testGetMessageInvalidHeader() {
        CSVException ex = new CSVException(CSVExceptionType.INVALIDHEADER, 0, "test.csv", null);
        assertEquals("CSVException: Invalid header found in CSV file test.csv", ex.getMessage());
    }

    @Test
    public void testGetMessageInvalidLine() {
        CSVException ex = new CSVException(CSVExceptionType.INVALIDLINE, 3, "test.csv", null);
        assertEquals("CSVException: Invalid line number 4 found in CSV file test.csv", ex.getMessage());
    }

    @Test
    public void testGetMessageEmpty() {
        CSVException ex = new CSVException(CSVExceptionType.EMPTY, 0, "test.csv", null);
        assertEquals("CSVException: No data found in CSV file test.csv", ex.getMessage());
    }

    @Test
    public void testGetMessageMissingImages() {
        List<String> missingImages = List.of("image1.jpg", "image2.jpg");
        CSVException ex = new CSVException(CSVExceptionType.MISSINGIMAGES, 0, "test.csv", missingImages);
        String expectedMessage = "CSVException: Some images listed in CSV file test.csv are missing:\n" +
                                 "image1.jpg\n" +
                                 "image2.jpg\n";
        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    public void testGetMessageUnknown() {
        CSVException ex = new CSVException(CSVExceptionType.UNKNOWN, 0, "test.csv", null);
        assertEquals("CSVException: Unknown error with CSV file test.csv", ex.getMessage());
    }
}
