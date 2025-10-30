package com.github.jimorc.flexishowbuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * CSVLineTests contains tests for the CSVLine class.
 */
class CSVLineTests {
    @Test
    void noFieldsConstructor() {
        CSVLine csvL = new CSVLine();
        assertEquals(0, csvL.length());
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
        final int fieldLen = 3;
        CSVLine csvL = new CSVLine();
        String[] fields = {"a", "b", "c"};
        csvL.addFields(fields);
        assertEquals(fieldLen, csvL.length());
        assertEquals("a", csvL.field(0));
        assertEquals("b", csvL.field(1));
        assertEquals("c", csvL.field(2));
    }
}
