package com.github.jimorc.flexishowbuilder;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CSVLineTests {
    @Test
    void noFieldsConstructor() {
        CSVLine csvL = new CSVLine();
        assertEquals(0, csvL.length());
    }

    @Test
    void fieldsConstructor() {
        String[] fields = {"a", "b", "c"};
        CSVLine csvL = new CSVLine(fields);
        assertEquals(3, csvL.length());
        assertEquals("a", csvL.field(0));
        assertEquals("b", csvL.field(1));
        assertEquals("c", csvL.field(2));
    }

    @Test
    void fieldOutOfBounds() {
        String[] fields = {"a", "b", "c"};
        CSVLine csvL = new CSVLine(fields);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> 
            csvL.field(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
            csvL.field(3));
    }

    @Test
    void addField() {
        CSVLine csvL = new CSVLine();
        csvL.addField("a");
        assertEquals(1, csvL.length());
        assertEquals("a", csvL.field(0));
        csvL.addField("b");
        assertEquals(2, csvL.length());
        assertEquals("a", csvL.field(0));
        assertEquals("b", csvL.field(1));
    }

    @Test
    void addFields() {
        CSVLine csvL = new CSVLine();
        String[] fields = {"a", "b", "c"};
        csvL.addFields(fields);
        assertEquals(3, csvL.length());
        assertEquals("a", csvL.field(0));
        assertEquals("b", csvL.field(1));
        assertEquals("c", csvL.field(2)); 
    } 
}