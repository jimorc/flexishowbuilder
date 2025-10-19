package com.github.jimorc.flexishowbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * The InputCSV class reads the CSV file and stores multiple CSVLine objects.
 *
 * It uses the Builder pattern to create an InputCSV object. The only required
 * parameter is the io.File containing the CSV lines to read. If no file is provided,
 * a FileChooser dialog is displayed to select a CSV file.
 * ```java
 * InputCSV csv = new CSV.Builder()
 *     .file(fileName))  // optional, if not provided a file chooser dialog is displayed
 *     .build();
 * ```
 */
public final class InputCSV {
    private File csvFile;
    private CSVLine[] lines = new CSVLine[0];
    private Map<String, ImageAndPersonLine[]> fullNameMap;
    private Set<String> fullNameKeys;
    private ArrayList<String> sortedFullNames;

    /**
     * This constructor is private. Use the Builder class to create a CSV object.
     */
    private InputCSV() {}

    /**
     * This constructor is private. Use the Builder class to create a CSV object.
     * @param builder - the Builder object used to create the CSV object.
     */
    private InputCSV(Builder builder) {
        this.csvFile = builder.csvFile;
    }

    /**
     * The Builder class is used to create a CSV object.
     */
    public static class Builder {
        private File csvFile;

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
        public InputCSV build() throws CSVException, IOException, FileNotFoundException {
            InputCSV csv = null;
            if (csvFile == null) {
                try {
                    csvFile = InputCSV.getCSVFile();
                } catch (CSVException csve) {
                    // in junit tests, we don't want to display a dialog, so we throw an exception.
                    // CSVException is thrown when testing in VSCode.
                    throw new RuntimeException("No CSV file selected.");
                } catch (UnsatisfiedLinkError ue) {
                    // in junit tests in maven (mvn test), UnsatisfiedLinkError thrown
                    // instead of CSVException.
                    throw new RuntimeException("No CSV file selected.");
                }
            }
            if (csvFile.isFile()) {
                csv = new InputCSV(this);
                csv.loadCSVFile();
                csv.buildFullNameHashMap();
                csv.fullNameKeys = csv.fullNameMap.keySet();
                return csv;
            } else {
                throw new FileNotFoundException("CSV file does not exist: " + csvFile.getAbsolutePath());
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
     * Retrieve the HashMap for the lines in the CSV file. This method is only provided for test
     * purposes.
     * @return the HashMap containing the CSV file lines
     */
    protected Map<String, ImageAndPersonLine[]> getHashMap() {
        return fullNameMap;
    }

    /**
     * Retrieve the Set of name keys. This method is provided only for test purposes.
     * @return the Set of name keys
     */
    protected Set<String> getFullNameKeys() {
        return fullNameKeys;
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
     * Return a Person object if there is one or more lines containing that name in the CSV lines.
     * @param name - the full name of the person to retrieve a Person object for.
     * @return a Person object for the named person or null if not in CSV object.
     */
    public Person getPerson(String name) throws CSVException {
        if (fullNameMap.containsKey(name)) {
            ImageAndPersonLine[] personLines = fullNameMap.get(name);
            return new Person(personLines[0].getPersonFirstName(), personLines[0].getPersonLastName());
        }
        throw new CSVException("Programming error: Trying to retrieve info for " + name + " but it does not exist.");
    }

    /**
     * Returns a list of sorted full names that appear in the CSV file.
     * @return sorted list of full names.
     */
    protected ArrayList<String> getSortedFullNames() {
        return sortedFullNames;
    }

    /**
     * Opens a file chooser dialog to select a CSV file.
     * @return The name of the selected CSV file, or null if no file was selected.
     */
    private static File getCSVFile() throws CSVException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));

        File csv = fileChooser.showOpenDialog(null);
        if (csv == null) {
            throw new CSVException("No CSV file selected.");
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
    protected void loadCSVFile() throws IOException, CSVException {
        String dir = getFileDir();
        java.nio.file.Path path = java.nio.file.Paths.get(dir, getFileName());
        List<String> allLines = java.nio.file.Files.readAllLines(path);
        lines = new CSVLine[allLines.size()];
        for (int i = 0; i < allLines.size(); i++) {
            try {
                lines[i] = new ImageAndPersonLine(allLines.get(i));
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                if (i == 0) {
                    throw new CSVException("Invalid header found in CSV file " + getFileName());
                } else {
                    throw new CSVException("Invalid line number " + (i + 1) + " found in CSV file " + getFileName());
                }
            }
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
    public void insertAt(int index, CSVLine line) throws ArrayIndexOutOfBoundsException {
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

    /**
     * Retrieve the input CSV line corresponding to the specified index.
     * @param index The number of the line to retrieve
     * @return the line specified by index
     */
    public CSVLine getLine(int index) {
        if (index < 0 || index >= lines.length) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return lines[index];
    }

    /**
     * Retrieve the input CSV lines corresponding to the name provided by the argument.
     * @param fullName the full name of the person to retrieve the lines for.
     * @return All input CSV lines for the named person
     * @throws CSVException when there are no lines for the specified person.
     */
    public ImageAndPersonLine[] getImageLines(String fullName) throws CSVException {
        if (fullNameMap.containsKey(fullName)) {
            return fullNameMap.get(fullName);
        } else {
            throw new CSVException("The CSV file does not contain lines for " + fullName);
        }
    }

    private void buildFullNameHashMap() {
        fullNameMap = new HashMap<>();
        boolean firstLine = true;
        for (CSVLine line : lines) {
            if (firstLine) {
                firstLine = false;
                continue; // skip header line
            }
            ImageAndPersonLine ipLine = (ImageAndPersonLine) line;
            String fullName = ipLine.getPersonFullName();
            if (!fullNameMap.containsKey(fullName)) {
                fullNameMap.put(fullName, new ImageAndPersonLine[] {ipLine});
            } else {
                ImageAndPersonLine[] existingLines = fullNameMap.get(fullName);
                ImageAndPersonLine[] newLines = new ImageAndPersonLine[existingLines.length + 1];
                System.arraycopy(existingLines, 0, newLines, 0, existingLines.length);
                newLines[existingLines.length] = ipLine;
                fullNameMap.put(fullName, newLines);
            }
        }
    }

    /**
     * Sorts the full names in the CSV object according to the specified order.
     * @param order - the sort order. See the sortOrder enum for possible values.
     * This file is protected rather than private so that
     * it can called for testing purposes.
     */
    protected void sortNames(SortOrder order) throws IllegalArgumentException {
        switch (order) {
            case AsIs:
                sortNamesAsIs();
                break;
            case AlphabeticalByFullName:
                sortNamesAlphabeticallyByFullName();
                break;
            case AlphabeticalByLastNameThenFirstName:
                sortNamesAlphabeticallyByLastNamelFirstName();
                break;
            case AlphabeticalByFullNameReverse:
                sortNamesAlphabeticallyByFullNameReverse();
                break;
            case AlphabeticalByLastNameThenFirstNameReverse:
                sortNamesAlphabeticallytByLastNamelFirstNameReverse();
                break;
            default:
                throw new IllegalArgumentException("Invalid sort order: " + order);
        }
    }

    private void sortNamesAlphabeticallyByFullName() {
        final int headerLine = 0;
        List<ImageAndPersonLine> entries = new ArrayList<ImageAndPersonLine>();
        entries.add((ImageAndPersonLine) this.lines[headerLine]);
        String[] fullNames = fullNameKeys.toArray(new String[fullNameMap.size()]);
        Arrays.sort(fullNames);
        sortedFullNames = new ArrayList<>(Arrays.asList(fullNames));
        for (String fName: fullNames) {
            ImageAndPersonLine[] linesForName = fullNameMap.get(fName);
            for (ImageAndPersonLine line: linesForName) {
                entries.add(line);
            }
        }
        this.lines = entries.toArray(new CSVLine[entries.size()]);
    }

    private void sortNamesAlphabeticallyByFullNameReverse() {
        final int headerLine = 0;
        List<ImageAndPersonLine> entries = new ArrayList<ImageAndPersonLine>();
        entries.add((ImageAndPersonLine) this.lines[headerLine]);
        String[] fullNames = fullNameKeys.toArray(new String[fullNameMap.size()]);
        Arrays.sort(fullNames, Collections.reverseOrder());
        sortedFullNames = new ArrayList<>(Arrays.asList(fullNames));
        for (String fName: fullNames) {
            ImageAndPersonLine[] linesForName = fullNameMap.get(fName);
            for (ImageAndPersonLine line: linesForName) {
                entries.add(line);
            }
        }
        this.lines = entries.toArray(new CSVLine[entries.size()]);
    }

    private void sortNamesAlphabeticallyByLastNamelFirstName() {
        final int headerLine = 0;
        List<ImageAndPersonLine> entries = new ArrayList<ImageAndPersonLine>();
        entries.add((ImageAndPersonLine) this.lines[headerLine]);
        String[] fullNames = fullNameKeys.toArray(new String[fullNameMap.size()]);
        Arrays.sort(fullNames, new Comparator<String>() {
            @Override
            public int compare(String name1, String name2) {
                String[] parts1 = name1.split(" ");
                String[] parts2 = name2.split(" ");
                String lastName1 = parts1[parts1.length - 1];
                String lastName2 = parts2[parts2.length - 1];
                int lastNameComparison = lastName1.compareTo(lastName2);
                if (lastNameComparison != 0) {
                    return lastNameComparison;
                } else {
                    String firstName1 = parts1[0];
                    String firstName2 = parts2[0];
                    return firstName1.compareTo(firstName2);
                }
            }
        });
        sortedFullNames = new ArrayList<>(Arrays.asList(fullNames));
        for (String fName: fullNames) {
            ImageAndPersonLine[] linesForName = fullNameMap.get(fName);
            for (ImageAndPersonLine line: linesForName) {
                entries.add(line);
            }
        }
        this.lines = entries.toArray(new CSVLine[entries.size()]);
    }

    private void sortNamesAlphabeticallytByLastNamelFirstNameReverse() {
        List<ImageAndPersonLine> entries = new ArrayList<ImageAndPersonLine>();
        entries.add((ImageAndPersonLine) this.lines[0]);
        String[] fullNames = fullNameKeys.toArray(new String[fullNameMap.size()]);
        Arrays.sort(fullNames, new Comparator<String>() {
            @Override
            public int compare(String name1, String name2) {
                String[] parts1 = name1.split(" ");
                String[] parts2 = name2.split(" ");
                String lastName1 = parts1[parts1.length - 1];
                String lastName2 = parts2[parts2.length - 1];
                int lastNameComparison = lastName2.compareTo(lastName1);
                if (lastNameComparison != 0) {
                    return lastNameComparison;
                } else {
                    String firstName1 = parts1[0];
                    String firstName2 = parts2[0];
                    return firstName2.compareTo(firstName1);
                }
            }
        });
        sortedFullNames = new ArrayList<>(Arrays.asList(fullNames));
        for (String fName: fullNames) {
            ImageAndPersonLine[] linesForName = fullNameMap.get(fName);
            for (ImageAndPersonLine line: linesForName) {
                entries.add(line);
            }
        }
        this.lines = entries.toArray(new CSVLine[entries.size()]);
    }

    // This actually sorts the names so that all entries for a given full name are
    // together, but the order of the names is not changed.
    private void sortNamesAsIs() {
        final int headerLine = 0;
        List<ImageAndPersonLine> entries = new ArrayList<ImageAndPersonLine>();
        entries.add((ImageAndPersonLine) this.lines[headerLine]);
        sortedFullNames = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String fullName = ((ImageAndPersonLine) lines[i]).getPersonFullName();
            if (sortedFullNames.contains(fullName)) {
                continue;
            }
            sortedFullNames.add(fullName);
            ImageAndPersonLine[] ipLines = fullNameMap.get(fullName);
            if (ipLines != null) {
                for (ImageAndPersonLine ipLine: ipLines) {
                    entries.add(ipLine);
                }
            }
        }
        this.lines = entries.toArray(new CSVLine[entries.size()]);
    }

    /**
     * Returns a list of image file names that are referenced in the CSV file
     * but do not exist in the same directory as the CSV file.
     * @return a list of missing image file names.
     */
    private List<String> getListOfMissingImages() {
        ArrayList<String> missingImages = new ArrayList<>();
        String dir = getFileDir();
        for (int i = 1; i < lines.length; i++) { // skip header line
            ImageAndPersonLine ipLine = (ImageAndPersonLine) lines[i];
            String imageFileName = ipLine.getImageFileName();
            java.nio.file.Path imagePath = java.nio.file.Paths.get(dir, imageFileName);
            File imageFile = imagePath.toFile();
            if (!imageFile.isFile()) {
                missingImages.add(imageFileName);
            }
        }
        return missingImages;
    }

    /**
     * validateCSVFile validates the contents of the InputCSV file.
     * @return
     * @throws CSVException
     */
    protected Exception validateCSVFile() throws CSVException {
        final int headerLineSize = 5;
        final int fileNameLine = 0;
        final int titleLine = 1;
        final int fullNameLine = 2;
        final int firstNameLine = 3;
        final int lastNameLine = 4;
        if (lines.length == 0) {
            throw new CSVException("No data found in CSV file " + getFileName());
        }
        // validate header line
        CSVLine headerLine = lines[0];
        if (headerLine.length() != headerLineSize) {
            throw new CSVException("Invalid header found in CSV file " + getFileName());
        }
        if (!headerLine.field(fileNameLine).equalsIgnoreCase("Filename")
            || !headerLine.field(titleLine).equalsIgnoreCase("Title")
            || !headerLine.field(fullNameLine).equalsIgnoreCase("Full Name")
            || !headerLine.field(firstNameLine).equalsIgnoreCase("First Name")
            || !headerLine.field(lastNameLine).equalsIgnoreCase("Last Name")) {
            throw new CSVException("Invalid header found in CSV file " + getFileName());
        }
        // check for missing images
        List<String> missingImages = getListOfMissingImages();
        if (missingImages.size() > 0) {
            String msg = "Some images listed in CSV file " + getFileName() + " are missing:\n";
            for (String img : missingImages) {
                msg += img + "\n";
            }
            throw new CSVException(msg);
        }
        return null; // no errors found
    }
}
