package com.github.jimorc.flexishowbuilder;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ProcessBuilder;

public class TitleImage {
    private TitleImage() {}

    public static void generateTitleImage(String caption, String imageFileName) throws Exception{
        String imCommand = "convert -size 1400x1050 -stroke yellow -fill yellow -font Helvetica" +
            " -pointsize 48 -gravity Center caption:\"" + caption;
        imCommand += "\" -fill black -opaque white " + imageFileName;
        System.out.println(imCommand);
        ProcessBuilder builder = new ProcessBuilder();
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        if (isWindows) {
            builder.command("cmd.exe", "/c", imCommand);
        } else {
            builder.command("bash", "-c", imCommand);
        }

        File locDirFile = new File(System.getProperty("user.dir"));
        System.out.println(locDirFile.getName());
        builder.directory(locDirFile);

        Process process = builder.start();
        OutputStream outputStream = process.getOutputStream();
        InputStream inputStream = process.getInputStream();
        InputStream errorStream = process.getErrorStream();

        printStream(inputStream);
        printStream(errorStream);

        int exitCode = process.waitFor();
        outputStream.flush();
        outputStream.close();
        if (exitCode != 0) {
            System.err.println("Error: ImageMagick command failed with exit code " + exitCode);
            throw new Exception("ImageMagick command failed with exit code " + exitCode);
        }
    }

        private static void printStream(InputStream inputStream) throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

}