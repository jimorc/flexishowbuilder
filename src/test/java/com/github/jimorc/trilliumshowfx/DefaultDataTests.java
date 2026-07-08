package com.github.jimorc.trilliumshowfx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit tests for DefaultData class.
 */
public class DefaultDataTests {
    @Test
    public void testConstructorValidString() {
        final int width = 1920;
        final int height = 1080;
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"true\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\" }";

        DefaultData data = new DefaultData(jsonContent);
        SlideSize slideSize = data.getSlideSize();
        assertEquals(width, slideSize.getWidth());
        assertEquals(height, slideSize.getHeight());
        assertTrue(data.getCreateStartEndSlides());
        assertEquals("start", data.getStartTitle());
        assertEquals("end", data.getEndTitle());
        assertEquals(SortOrder.AlphabeticalByFullNameReverse, data.getSortOrder());
        assertTrue(data.getGeneratePersonSlides());
    }

    @Test
    public void testConstructorDefaultFile() {
        final int width = 1024;
        final int height = 768;
        File jsonFile = new File("testing/data/defaults.json");
        DefaultData data = new DefaultData(jsonFile);
        SlideSize slideSize = data.getSlideSize();
        assertEquals(width, slideSize.getWidth());
        assertEquals(height, slideSize.getHeight());
        assertTrue(data.getCreateStartEndSlides());
        assertEquals("start title", data.getStartTitle());
        assertEquals("end title", data.getEndTitle());
        assertEquals(SortOrder.DontSort, data.getSortOrder());
        assertFalse(data.getGeneratePersonSlides());
    }

    @Test
    public void testConstructorMissingFile() {
        final int width = 1400;
        final int height = 1050;
        File jsonFile = new File("testing/data/nonexistent.json");
        DefaultData data = new DefaultData(jsonFile);
        SlideSize slideSize = data.getSlideSize();
        boolean ses = data.getCreateStartEndSlides();
        assertEquals(width, slideSize.getWidth());
        assertEquals(height, slideSize.getHeight());
        assertTrue(ses);
        assertEquals("", data.getStartTitle());
        assertEquals("", data.getEndTitle());
        assertFalse(data.getGeneratePersonSlides());
        assertEquals(SortOrder.DontSort, data.getSortOrder());
        assertFalse(data.getGeneratePersonSlides());
        if (jsonFile.exists()) {
            jsonFile.delete();
        }
    }

    @Test
    public void testConstructorInvalidJson() {
        final int width = 1400;
        final int height = 1050;
        String invalidJson = "{ invalid json ";
        DefaultData data = new DefaultData(invalidJson);
        SlideSize slideSize = data.getSlideSize();
        assertEquals(width, slideSize.getWidth());
        assertEquals(height, slideSize.getHeight());
        assertTrue(data.getCreateStartEndSlides());
        assertEquals("", data.getStartTitle());
        assertEquals("", data.getEndTitle());
        assertEquals(SortOrder.DontSort, data.getSortOrder());
        assertFalse(data.getGeneratePersonSlides());
    }

    @Test
    public void testSaveDefaults() {
        File jsonFile = new File("temp_defaults.json");
        DefaultData data = new DefaultData(jsonFile);
        // force changes to trigger save
        data.getSlideSize();
        data.getCreateStartEndSlides();
        data.getStartTitle();
        data.getEndTitle();
        data.getSortOrder();
        data.getGeneratePersonSlides();
        try (BufferedReader jsonFileReader = new BufferedReader(
                new FileReader(jsonFile.getAbsolutePath()))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = jsonFileReader.readLine()) != null) {
                sb.append(line);
            }
            String fileContent = sb.toString();
            assertTrue(fileContent.contains("createStartEndSlides\":\"true\""));
            assertTrue(fileContent.contains("\"slide_size\":{\"width\":1400,\"height\":1050}"));
            assertTrue(fileContent.contains("\"startTitle\":\"\""));
            assertTrue(fileContent.contains("\"endTitle\":\"\""));
            assertTrue(fileContent.contains("\"sortOrder\":\"DontSort\""));
            assertTrue(fileContent.contains("\"generatePersonSlides\":\"false\""));
        } catch (IOException e) {
            fail(e.getCause().toString());
        }
        if (jsonFile.exists()) {
            jsonFile.delete();
        }
    }

    @Test
    public void testGetSlideSizeNoValue() {
        String jsonContent = "{ "
            + "\"createStartEndSlides\": \"true\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\" }";
        DefaultData data = new DefaultData(jsonContent);
        SlideSize slideSize = data.getSlideSize();
        assertEquals(SlideSize.DEFAULT_WIDTH, slideSize.getWidth());
        assertEquals(SlideSize.DEFAULT_HEIGHT, slideSize.getHeight());

    }

    @Test
    void testGetSlideSizeValid() {
        final int width = 1920;
        final int height = 1080;
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"true\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": true }";
        DefaultData data = new DefaultData(jsonContent);
        SlideSize slideSize = data.getSlideSize();
        assertEquals(width, slideSize.getWidth());
        assertEquals(height, slideSize.getHeight());
    }

    @Test
    void testGetSlideSizeTooSmall() {
        String jsonContent = "{ \"slide_size\": { \"width\": 42, \"height\": 19 },"
            + "\"createStartEndSlides\": \"true\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": \"invalid\" }";
        DefaultData data = new DefaultData(jsonContent);
        SlideSize slideSize = data.getSlideSize();
        assertEquals(SlideSize.DEFAULT_WIDTH, slideSize.getWidth());
        assertEquals(SlideSize.DEFAULT_HEIGHT, slideSize.getHeight());
    }

    @Test
    void testGetSlideSizeTooLarge() {
        String jsonContent = "{ \"slide_size\": { \"width\": 10222, \"height\": 10000 },"
            + "\"createStartEndSlides\": \"true\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": \"invalid\" }";
        DefaultData data = new DefaultData(jsonContent);
        SlideSize slideSize = data.getSlideSize();
        assertEquals(SlideSize.DEFAULT_WIDTH, slideSize.getWidth());
        assertEquals(SlideSize.DEFAULT_HEIGHT, slideSize.getHeight());
    }

    @Test
    void testGetSlideSizeInvalid() {
        String jsonContent = "{ \"slide_size\": { \"width\": \"fred\", \"height\": \"wally\" }"
            + ", \"createStartEndSlides\": \"true\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": true }";
        DefaultData data = new DefaultData(jsonContent);
        SlideSize slideSize = data.getSlideSize();
        assertEquals(SlideSize.DEFAULT_WIDTH, slideSize.getWidth());
        assertEquals(SlideSize.DEFAULT_HEIGHT, slideSize.getHeight());
    }

    @Test
    public void testGetCreateStartEndSlidesNoValue() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 }"
            + ", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\" }";
        DefaultData data = new DefaultData(jsonContent);
        assertTrue(data.getCreateStartEndSlides());
    }

    @Test
    void testGetCreateStartEndSlidesTrue() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"true\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": true }";
        DefaultData data = new DefaultData(jsonContent);
        assertTrue(data.getCreateStartEndSlides());
    }

    @Test
    void testGetCreateStartEndSlidesFalse() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"false\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": false }";
        DefaultData data = new DefaultData(jsonContent);
        assertFalse(data.getCreateStartEndSlides());
    }

    @Test
    void testGetCreateStartEndSlidesInvalid() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"invalid\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": \"invalid\" }";
        DefaultData data = new DefaultData(jsonContent);
        assertFalse(data.getCreateStartEndSlides());
    }

    @Test
    void testSetCreateStartEndSlides() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"false\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": false }";
        DefaultData data = new DefaultData(jsonContent);
        assertFalse(data.getCreateStartEndSlides());
        data.setCreateStartEndSlides(true);
        assertTrue(data.getCreateStartEndSlides());
    }

    @Test
    void testGetStartTitleValid() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"false\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": false }";
        DefaultData data = new DefaultData(jsonContent);
        assertEquals("start", data.getStartTitle());
    }

    @Test
    void testGetStartTitleNone() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"false\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": false }";
        DefaultData data = new DefaultData(jsonContent);
        assertEquals("", data.getStartTitle());
    }

    @Test
    void testSetStartTitle() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"false\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": false }";
        DefaultData data = new DefaultData(jsonContent);
        assertFalse(data.getCreateStartEndSlides());
        data.setCreateStartEndSlides(true);
        assertTrue(data.getCreateStartEndSlides());
    }

    @Test
    void testGetEndTitleValid() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"false\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": false }";
        DefaultData data = new DefaultData(jsonContent);
        assertEquals("end", data.getEndTitle());
    }

    @Test
    void testGetEndTitleNone() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"false\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": false }";
        DefaultData data = new DefaultData(jsonContent);
        assertEquals("", data.getEndTitle());
    }

    @Test
    void testSetEndTitle() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"false\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": false }";
        DefaultData data = new DefaultData(jsonContent);
        assertEquals("end", data.getEndTitle());
        data.setEndTitle("newEnd");
        assertEquals("newEnd", data.getEndTitle());
    }

    @Test
    public void testGetSortSlidesByTitleNumberNoValue() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"true\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\" }";
        DefaultData data = new DefaultData(jsonContent);
        assertFalse(data.getSortSlidesByTitleNumber());
    }

    @Test
    void testGetSortSlidesByTitleNumberTrue() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"true\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": true }";
        DefaultData data = new DefaultData(jsonContent);
        assertTrue(data.getSortSlidesByTitleNumber());
    }

    @Test
    void testGetSortSlidesByTitleNumberFalse() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"true\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": false }";
        DefaultData data = new DefaultData(jsonContent);
        assertFalse(data.getSortSlidesByTitleNumber());
    }

    @Test
    void testGetSortSlidesByTitleNumberInvalid() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"true\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": \"invalid\" }";
        DefaultData data = new DefaultData(jsonContent);
        assertFalse(data.getSortSlidesByTitleNumber());
    }

    @Test
    void testSetSortSlidesByTitleNumber() {
        String jsonContent = "{ \"slide_size\": { \"width\": 1920, \"height\": 1080 },"
            + "\"createStartEndSlides\": \"true\", \"startTitle\": \"start\", \"endTitle\": \"end\""
            + ", \"sortOrder\": \"AlphabeticalByFullNameReverse\""
            + ", \"generatePersonSlides\": \"true\", \"sortSlidesByTitleNumber\": false }";
        DefaultData data = new DefaultData(jsonContent);
        assertFalse(data.getSortSlidesByTitleNumber());
        data.setSortSlidesByTitleNumber(true);
        assertTrue(data.getSortSlidesByTitleNumber());
    }
}
