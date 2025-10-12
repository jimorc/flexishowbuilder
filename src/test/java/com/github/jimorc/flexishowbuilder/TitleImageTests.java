package com.github.jimorc.flexishowbuilder;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class TitleImageTests {
    @Start
    private void start(Stage stage) {
       try {
            TitleImage.generateTitleImage("Test Caption Line1\nLine2", "test.jpg");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testGenerateTitleImage(FxRobot robot) {
        Path path1 = Path.of("testing/data/test_image.jpg");
        Path path2 = Path.of("test.jpg");
        try (RandomAccessFile randomAccessFile1 = new RandomAccessFile(path1.toFile(), "r");
                RandomAccessFile randomAccessFile2 = new RandomAccessFile(path2.toFile(), "r")) {

            FileChannel ch1 = randomAccessFile1.getChannel();
            FileChannel ch2 = randomAccessFile2.getChannel();
            System.out.println(ch1.size());
            System.out.println(ch2.size());
            if (ch1.size() != ch2.size()) {
                fail("Files are not identical size");
            }
            long size = ch1.size();
            MappedByteBuffer m1 = ch1.map(FileChannel.MapMode.READ_ONLY, 0L, size);
            MappedByteBuffer m2 = ch2.map(FileChannel.MapMode.READ_ONLY, 0L, size);

            if (!m1.equals(m2)) {
                fail("Files are not identical content");
            }
        } catch (Exception e) {
            fail("Exception comparing files: " + e.getMessage());
        } finally {
            // cleanup
            Path.of("test.jpg").toFile().delete();
        }
    }
}