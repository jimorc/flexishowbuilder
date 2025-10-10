package com.github.jimorc.flexishowbuilder;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.jupiter.api.Test;


public class TitleImageTests {
    @Test
    void testGenerateTitleImage() {
        try {
            TitleImage.generateTitleImage("Test Caption Line1\nLine2", "test.jpg");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
        Path path1 = Path.of("testing/data/test_image.jpg");
        Path path2 = Path.of("test.jpg");
        try (RandomAccessFile randomAccessFile1 = new RandomAccessFile(path1.toFile(), "r"); 
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(path2.toFile(), "r")) {
            
            FileChannel ch1 = randomAccessFile1.getChannel();
            FileChannel ch2 = randomAccessFile2.getChannel();
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