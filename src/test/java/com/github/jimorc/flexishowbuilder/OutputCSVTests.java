package com.github.jimorc.flexishowbuilder;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class OutputCSVTests {
    @Test
    void testAppendLine() {
        String header = "Filename,Title,Full Name,First Name,Last Name";
        CSVLine line = new ImageAndPersonLine(header);
        OutputCSV csv = new OutputCSV();
        csv.appendLine(line);
        String c = csv.toString(); 
        assertEquals(header+"\n", c);

       csv.appendLine(line);
        c = csv.toString(); 
        assertEquals(header+"\n"+header+"\n", c);
    }
}