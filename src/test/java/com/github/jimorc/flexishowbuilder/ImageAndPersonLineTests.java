package com.github.jimorc.flexishowbuilder;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ImageAndPersonLineTests {
    @Test
    void testNoCommaConstructor() {
        ImageAndPersonLine ipl = new ImageAndPersonLine("image.jpg,image title,John Doe,John,Doe");
        assertEquals(5, ipl.length());
        assertEquals("image.jpg", ipl.getImageFileName());
        assertEquals("image title", ipl.getImageTitle());
        assertEquals("John Doe", ipl.getPersonFullName());
        assertEquals("John", ipl.getPersonFirstName());
        assertEquals("Doe", ipl.getPersonLastName());
    }

    @Test
    void testCommaInTitleConstructor() {
        ImageAndPersonLine ipl = new ImageAndPersonLine("image.jpg,\"image, title\",John Doe,John,Doe");
        assertEquals(5, ipl.length());
        assertEquals("image.jpg", ipl.getImageFileName());
        assertEquals("\"image, title\"", ipl.getImageTitle());
        assertEquals("John Doe", ipl.getPersonFullName());
        assertEquals("John", ipl.getPersonFirstName());
        assertEquals("Doe", ipl.getPersonLastName());
    }

    @Test
    void testToString() {
        ImageAndPersonLine ipl = new ImageAndPersonLine("image.jpg,image title,John Doe,John,Doe");
        assertEquals("image.jpg,image title,John Doe,John,Doe", ipl.toString());

        ImageAndPersonLine ipl2 = new ImageAndPersonLine("image.jpg,\"image, title\",John Doe,John,Doe");
        assertEquals("image.jpg,\"image, title\",John Doe,John,Doe", ipl2.toString());
    }   
}