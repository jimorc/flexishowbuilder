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
import java.util.Set;

import javax.swing.filechooser.FileSystemView;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

enum sortOrder {
    ASIS,
    ALPHABETICAL_BY_FULL_NAME,
    ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME,
    ALPHABETICAL_BY_FULL_NAME_REVERSE,
    ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME_REVERSE
}

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
public class InputCSV {
    private File csvFile = null;
    private CSVLine[] lines = new CSVLine[0];
    private HashMap<String, ImageAndPersonLine[]> fullNameHashMap = null;
    private Set<String> fullNameKeys = null;
    private ArrayList<String> sortedFullNames = null;

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
        public InputCSV build() throws CSVException, IOException, FileNotFoundException {
            InputCSV csv = null;
            if (csvFile == null) {
                try {
                    csvFile = InputCSV.getCSVFile();
                } catch (CSVException csve) {
                    // in junit tests, we don't want to display a dialog, so we throw an exception
                   throw new RuntimeException("No CSV file selected.");
                }
            }
            if (csvFile.isFile()) {
                csv = new InputCSV(this);
                csv.loadCSVFile();
                csv.buildFullNameHashMap();
                csv.fullNameKeys = csv.fullNameHashMap.keySet();
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
    protected HashMap<String, ImageAndPersonLine[]> getHashMap() {
        return fullNameHashMap;
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
        if (fullNameHashMap.containsKey(name)) {
            ImageAndPersonLine[] lines = fullNameHashMap.get(name);
            return new Person(lines[0].getPersonFirstName(), lines[0].getPersonLastName());
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
                    throw new CSVException("Invalid line number " + (i+1) + " found in CSV file " + getFileName());
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
     * Retrieve the input CSV lines corresponding to the name provided by the argument
     * @param fullName the full name of the person to retrieve the lines for.
     * @return All input CSV lines for the named person
     * @throws CSVException when there are no lines for the specified person.
     */
    public ImageAndPersonLine[] getImageLines(String fullName) throws CSVException {
        if (fullNameHashMap.containsKey(fullName)) {
            return fullNameHashMap.get(fullName);
        } else {
            throw new CSVException("The CSV file does not contain lines for " + fullName);
        }
    }

       private void buildFullNameHashMap() {
        fullNameHashMap = new HashMap<>();
        boolean firstLine = true;
        for (CSVLine line : lines) {
            if (firstLine) {
                firstLine = false;
                continue; // skip header line
            }
            ImageAndPersonLine ipLine = (ImageAndPersonLine) line;
            String fullName = ipLine.getPersonFullName();
            if (!fullNameHashMap.containsKey(fullName)) {
                fullNameHashMap.put(fullName, new ImageAndPersonLine[] { ipLine });
            } else {
                ImageAndPersonLine[] existingLines = fullNameHashMap.get(fullName);
                ImageAndPersonLine[] newLines = new ImageAndPersonLine[existingLines.length + 1];
                System.arraycopy(existingLines, 0, newLines, 0, existingLines.length);
                newLines[existingLines.length] = ipLine;
                fullNameHashMap.put(fullName, newLines);
            }
        }
    }

        /**
     * Sorts the full names in the CSV object according to the specified order.
     * @param order - the sort order. See the sortOrder enum for possible values.
     * This file is protected rather than private so that
     * it can called for testing purposes.
     */
    protected void sortNames(sortOrder order) {
        switch (order) {
            case ASIS:
                sortNamesAsIs();
                break;
            case ALPHABETICAL_BY_FULL_NAME:
                sortNamesAlphabeticallyByFullName();
                break;
            case ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME:
                sortNamesAlphabeticallyByLastNamelFirstName();
                break;
            case ALPHABETICAL_BY_FULL_NAME_REVERSE:
                sortNamesAlphabeticallyByFullNameReverse();
                break;
            case ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME_REVERSE:
                sortNamesAlphabeticallytByLastNamelFirstNameReverse();
                break;
        }       
    }

    private void sortNamesAlphabeticallyByFullName() {
        List<ImageAndPersonLine> entries = new ArrayList<ImageAndPersonLine>();
        entries.add((ImageAndPersonLine)this.lines[0]);
        String[] fullNames = fullNameKeys.toArray(new String[fullNameHashMap.size()]);
        Arrays.sort(fullNames);
        sortedFullNames = new ArrayList<>(Arrays.asList(fullNames));
        for (String fName: fullNames) {
            ImageAndPersonLine[] linesForName = fullNameHashMap.get(fName);
            for (ImageAndPersonLine line: linesForName) {
                entries.add(line);
            }
        }
        this.lines = entries.toArray(new CSVLine[entries.size()]);
    }

    private void sortNamesAlphabeticallyByFullNameReverse() {
        List<ImageAndPersonLine> entries = new ArrayList<ImageAndPersonLine>();
        entries.add((ImageAndPersonLine)this.lines[0]);
        String[] fullNames = fullNameKeys.toArray(new String[fullNameHashMap.size()]);
        Arrays.sort(fullNames, Collections.reverseOrder());
        sortedFullNames = new ArrayList<>(Arrays.asList(fullNames));
        for (String fName: fullNames) {
            ImageAndPersonLine[] linesForName = fullNameHashMap.get(fName);
            for (ImageAndPersonLine line: linesForName) {
                entries.add(line);
            }
        }
        this.lines = entries.toArray(new CSVLine[entries.size()]);
    }

private void sortNamesAlphabeticallyByLastNamelFirstName() {
        List<ImageAndPersonLine> entries = new ArrayList<ImageAndPersonLine>();
        entries.add((ImageAndPersonLine)this.lines[0]);
        String[] fullNames = fullNameKeys.toArray(new String[fullNameHashMap.size()]);
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
            ImageAndPersonLine[] linesForName = fullNameHashMap.get(fName);
            for (ImageAndPersonLine line: linesForName) {
                entries.add(line);
            }
        }
        this.lines = entries.toArray(new CSVLine[entries.size()]);
    }

    private void sortNamesAlphabeticallytByLastNamelFirstNameReverse() {
        List<ImageAndPersonLine> entries = new ArrayList<ImageAndPersonLine>();
        entries.add((ImageAndPersonLine)this.lines[0]);
        String[] fullNames = fullNameKeys.toArray(new String[fullNameHashMap.size()]);
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
            ImageAndPersonLine[] linesForName = fullNameHashMap.get(fName);
            for (ImageAndPersonLine line: linesForName) {
                entries.add(line);
            }
        }
        this.lines = entries.toArray(new CSVLine[entries.size()]);
    }

   // This actually sorts the names so that all entries for a given full name are
    // together, but the order of the names is not changed.
    private void sortNamesAsIs() {
        List<ImageAndPersonLine> entries = new ArrayList<ImageAndPersonLine>();
        entries.add((ImageAndPersonLine)this.lines[0]);
        sortedFullNames = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String fullName = ((ImageAndPersonLine)lines[i]).getPersonFullName();
            if (sortedFullNames.contains(fullName)) {
                continue;
            }
            sortedFullNames.add(fullName);
            ImageAndPersonLine[] ipLines = fullNameHashMap.get(fullName);
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

    protected Exception validateCSVFile() throws CSVException {
        if (lines.length == 0) {
            throw new CSVException("No data found in CSV file " + getFileName());
        }
        // validate header line
        CSVLine headerLine = lines[0];
        if (headerLine.length() != 5) {
            throw new CSVException("Invalid header found in CSV file " + getFileName());
        }
        if (!headerLine.field(0).equalsIgnoreCase("Filename") ||
            !headerLine.field(1).equalsIgnoreCase("Title") ||
            !headerLine.field(2).equalsIgnoreCase("Full Name") ||
            !headerLine.field(3).equalsIgnoreCase("First Name") ||
            !headerLine.field(4).equalsIgnoreCase("Last Name")) {
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