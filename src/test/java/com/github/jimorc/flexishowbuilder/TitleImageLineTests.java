package com.github.jimorc.flexishowbuilder;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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