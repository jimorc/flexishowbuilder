package com.github.jimorc.flexishowbuilder;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * XLSAppTests contains tests for the XLSApp class.
 */
public class XLSAppTests {
    @Test
    public void testExecutableFound()  {
        boolean found = false;
        String exe = "/usr/bin/bash";
        String exeName = "GNU bash";
        try {
            boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
            if (isWindows) {
                exe = "cmd";
                exeName = "cmd";
            }

            found = XLSApp.executableFound(exe, exeName);
        } catch (NullPointerException e) {
            fail(e.toString());
        } catch (IOException ioe) {
            fail(ioe.toString());
        } catch (InterruptedException ie) {
            fail(ie.toString());
        }
        assertTrue(found);
    }

    @Test
    public void testExecutableNotFound() {
        boolean found = false;
        try {
            found = XLSApp.executableFound("qwer", "qwer");
        } catch (NullPointerException e) {
            fail(e.toString());
        } catch (IOException ioe) {
            fail(ioe.toString());
        } catch (InterruptedException ie) {
            fail(ie.toString());
        }
        assertFalse(found);
    }
}
