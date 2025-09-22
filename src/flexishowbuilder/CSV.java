package flexishowbuilder;

import java.io.File;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * The CSV class stores multiple CSVLine objects.
 * 
 * It uses the Builder pattern to create a CSV object. The only required
 * parameter is the io.File containing the CSV lines to read. If no file is provided,
 * a FileChooser dialog is displayed to select a CSV file.
 * ```java
 * CSV csv = new CSV.Builder()
 *     .file(fileName))  // optional, if not provided a file chooser dialog is displayed
 *     .build();
 * ```
 */
public class CSV {
    private File csvFile = null;
    private CSVLine[] lines = new CSVLine[0];

    /**
     * This constructor is private. Use the Builder class to create a CSV object.
     * @param builder - the Builder object used to create the CSV object.
     */
    private CSV(Builder builder) {
        this.csvFile = builder.csvFile;
    }

    /**
     * The Builder class is used to create a CSV object.
     */
    public static class Builder {
        private File csvFile = null;

        /**
         * Sets the CSV file.
         * @param fileName the full path to the CSV file.
         * @return the Builder object.
         */
        public Builder fileName(String fileName) {
            if (fileName == null) {
                this.csvFile = null;
                return this;
            }
            this.csvFile = new File(fileName);
            return this;
        }

        /**
         * Builds the CSV object.
         * @return the CSV object.
         * @throws RuntimeException in junit tests if csvFile is null.
         */
        public CSV build() throws RuntimeException {
            if (csvFile == null) {
                csvFile = CSV.getCSVFile();
            }
            if (csvFile.isFile()) {
                CSV csv = new CSV(this);
                csv.loadCSVFile();
                return csv;
            } else {
                return null;
            }
        }
    }

    /**
     * Returns the name of the CSV file.
     * @return the CSV file name
     */
    public String getFileName() {
        if (csvFile != null) {
            return csvFile.getName();
        } else {
            return null;
        }
    }

    /**
     * Returns the directory containing the CSV file.
     * @return the CSV file directory, or null if no file is loaded.
     */
    public String getFileDir() {
        if (csvFile != null) {
            return csvFile.getParent();
        } else {
            return null;
        }
    }

    /**
     * Retrieve the lines in the CSV file.
     * @return an array of CSVLine objects.
     */
    protected CSVLine[] getLines() {
        return lines;
    }

    /**
     * Retrieve the number of lines in the CSV.
     * @return the number of lines in the CSV.
     */
    protected int getNumberOfLines() {
        return lines.length;
    }

    /**
     * Opens a file chooser dialog to select a CSV file.
     * @return The name of the selected CSV file, or null if no file was selected.
     */
    private static File getCSVFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));

        File csv = fileChooser.showOpenDialog(null);
        if (csv == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("CSV File Selection");
            alert.setHeaderText("CSV File Not Selected");
            alert.setContentText("Program will exit.");
            alert.showAndWait();
        }
        return csv;
    }

   /**
     * Loads the CSV file specified by the fileName field.
     * The lines field is populated with CSVLine objects.
     * If there is an error reading the file, an error message is
     * displayed and the program exits.
     * 
     * This file is protected rather than private so that
     * it can called for testing purposes.
     */
    protected void loadCSVFile() {
        String dir = getFileDir();
        java.nio.file.Path path = java.nio.file.Paths.get(dir, getFileName());
        try {
            List<String> allLines = java.nio.file.Files.readAllLines(path);
            lines = new CSVLine[allLines.size()];
            for (int i = 0; i < allLines.size(); i++) {
                lines[i] = new ImageAndPersonLine(allLines.get(i));
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("CSV File Error");
            alert.setHeaderText("Error attempting to read CSV file: " + path.toString());
            alert.setContentText("See if you can open the file by double-clicking on it.\n" +
                "If you can open it, check that it is a valid CSV file.\n" +
                "by double clicking on it.\n" +
                "If you can, then report a programming error.\n" +
                "Otherwise, there is a file error. Report it to the person who\n" +
                "sent you the file.\n" +
                "Program will now exit.");
            alert.showAndWait();
            System.exit(0);
        }
      }

    /**
     * Returns a string representation of the CSV object.
     * @return a string representation of the CSV object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CSVLine line : lines) {
            for (int i = 0; i < line.length(); i++) {
                sb.append(line.field(i));
                if (i < line.length() - 1) {
                    sb.append(",");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Inserts a CSVLine at the specified index.   
     * @param index - the index at which to insert the line
     * @param line  - the line to insert
     * @throws ArrayIndexOutOfBoundsException if the index is negative, or
     * greater than the number of lines.
     */
    public void insertAt(int index, CSVLine line) {
        if (index < 0 || index > lines.length) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds: " + index);
        }
        CSVLine[] newLines = new CSVLine[lines.length + 1];
        for (int i = 0; i < index; i++) {
            newLines[i] = lines[i];
        }
        newLines[index] = line;
        for (int i = index; i < lines.length; i++) {
            newLines[i + 1] = lines[i];
        }
        lines = newLines;
    }

   /**
     * Appends a CSVLine to the end of the CSV object.
     * @param line - the line to append
     */
    public void append(CSVLine line) {
        insertAt(lines.length, line);
    }

    public CSVLine getLine(int index) {
        if (index < 0 || index >= lines.length) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return lines[index];
    }

}