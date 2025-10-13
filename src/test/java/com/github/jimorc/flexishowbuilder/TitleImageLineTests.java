package com.github.jimorc.flexishowbuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * TitleImageLineTests contains tests for the TitleImageLine class.
 */
public class TitleImageLineTests {
    @Test
    void testConstructorValid() {
        TitleImageLine tl = new TitleImageLine("My_Title.jpg");
        assertNotNull(tl);
        TitleImageLine tl2 = new TitleImageLine("another_title.JPG");
        assertNotNull(tl2);
    }

    @Test
    void testConstructorInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
            new TitleImageLine("My_Title.png"));
        assertThrows(IllegalArgumentException.class, () ->
            new TitleImageLine("My_Titlejpeg"));
        assertThrows(IllegalArgumentException.class, () ->
            new TitleImageLine("My_Title.jpgg"));
    }
}
