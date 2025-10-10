package com.github.jimorc.flexishowbuilder;

/**
 * XLSAppFactory ios a factory class that creates an XLSApp object (eiter LibreOffice or Excel object).
 */
public class XLSAppFactory {
    /**
     * getXLSApp creates either a LibreOffice or Excel XLSApp. The type of the returned object is
     * determined by whether Excel or LibreOffice is installed on the computer. 
     * @return the appropriate XLSApp object, or null if neither app is installed on the computer.
     * @throws Exception if neither Excel nor OpenOffice are installed on the computer. Will also
     * pass on any exceptions from XLSApp.executableFound.
     */
    public static XLSApp getXLSApp() throws Exception{
         if (XLSApp.executableFound("libreoffice", "LibreOffice")) {
            return new LibreOffice();
        }
         throw new Exception("Neither Excel nor LibreOffice appear to be installed on this\n" +
             "computer. Processing cannot continue without at least one of them.");
    }
}