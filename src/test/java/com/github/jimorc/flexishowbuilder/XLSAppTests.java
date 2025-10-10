package com.github.jimorc.flexishowbuilder;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class XLSAppTests {
    @Test
    public void testExecutableFound()  {
        boolean found = false;
        String exe = "bash";
        String exeName = "bash";
        try {
            boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
            if (isWindows) {
                exe = "cmd";
                exeName = "cmd";
            } 

            found = XLSApp.executableFound(exe, exeName);
        } catch(Exception e) {
            fail(e.toString());
        }
        assertTrue(found);
    }

    @Test
    public void testExecutableNotFound() {
        boolean found = false;
        try {
             found = XLSApp.executableFound("qwer", "qwer");
        } catch(Exception e) {
            fail(e.toString());
        }
        assertFalse(found);
    }
}