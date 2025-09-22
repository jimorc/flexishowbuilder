package flexishowbuilder;

import java.io.File;
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
        // TODO: implement this method
    }
}