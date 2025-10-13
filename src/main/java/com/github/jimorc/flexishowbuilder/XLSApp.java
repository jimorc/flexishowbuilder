package com.github.jimorc.flexishowbuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * XLSApp is the base class for the LibreOffice and Excel classes that convert an OutputCSV object
 * into an XLS file.
 */
public class XLSApp {
    private static final int OK = 0;
    private static final int NOTFOUND = 127;

    /**
     * executableFound determines if the named file can be executed on the computer.
     * @param exe name of the excutable to check for. In flexishowBuilder, this argument is
     * expected to be either OpenOffice or excel, but no check is performed to ensure that
     * one of those values is used. Not checking is useful for unit testing purposes.
     * @return true if the executable is found; false otherwise.
     * @throws Exception for any ProcessBuilder, Process, or BufferedReader error condition.
     */
    public static boolean executableFound(String exe, String exeName)
        throws NullPointerException, IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        if (isWindows) {
            builder.command("cmd.exe", "/c", exe + " --version");
        } else {
            builder.command("/usr/bin/bash", "-c", exe + " --version");
        }

        Process process = builder.start();
        InputStream inputStream = process.getInputStream();
        String result = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        int exitCode = process.waitFor();
        if (exitCode != OK && exitCode != NOTFOUND) {
            System.err.println("Error: LibreOffice command failed with exit code " + exitCode);
        }
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        System.out.println(result);
        boolean found = result.contains(exeName);
        return found;
    }

    /**
     * createXLS creates an XLS file from the input CSV file.
     * @param csv OutputCSV object to create XLS file from.
     * @throws Exception because this method is not yet implemented.
     */
    public void createXLS(final OutputCSV csv) throws Exception {
        System.out.println(csv);
        throw new Exception("XLSAPP.createXLS method not implemented");
    }
}
