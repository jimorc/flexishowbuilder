package com.github.jimorc.flexishowbuilder;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CSVExceptionTests {
    @Test
    public void testConstructor() {
        CSVException ex = new CSVException("Exception message");
        assertEquals("Exception message", ex.getMessage());
    }
}