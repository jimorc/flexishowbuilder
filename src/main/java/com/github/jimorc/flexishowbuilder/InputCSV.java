package com.github.jimorc.flexishowbuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.tinylog.Logger;

/**
 * The InputCSV class reads the CSV file and stores multiple CSVLine objects.
 *
 *
 * ```java
 * File f = new File("<CSV-file-name>")
 * InputCSV csv = new InputCSV(f);
 * ```
 */
public final class InputCSV {
    private final File csvFile;
    private Path csvFolder;
    private CSVLine[] lines = new CSVLine[0];
    private Map<String, ImageAndPersonLine[]> fullNameMap;
    private Set<String> fullNameKeys;
    private ArrayList<String> sortedFullNames;

    /** This constructor parses the specified CSV file and builds an InputCSV
     * object from the file's contents.
     * @param csvF is the File containing the CSV data to parse.
     * @throws CSVException if csvF is null.
     * @throws CSVException if csvF is not a file (i.e directory, link, etc.)
     * @throws CSVException if csvF contains an invalid header line.
     * @throws CSVException if csvF is empty.
     * @throws IOException if the file cannot be read.
     */
    public InputCSV(File csvF) throws CSVException, IOException {
        Logger.trace("In InputCSV constructor");
        csvFile = csvF;
        if (csvF == null) {
            Logger.error("InputCSV constructor was passed a null CSV file object");
            throw new CSVException("Trying to read a null CSVFile");
        }
        if (!csvF.isFile()) {
            Logger.error(csvF.getAbsolutePath(), " passed to InputCSV constructor, is not a file");
            throw new CSVException("Trying to read " + csvF.getAbsolutePath()
                + " which is not a file.");
        }
        csvFolder = csvF.toPath().getParent();
        Logger.debug(BuilderGUI.buildLogMessage("CSV file folder: ", csvFolder.toString()));
        loadCSVFile();
        buildFullNameHashMap();
        fullNameKeys = fullNameMap.keySet();
        for (int i = 1; i < lines.length; i++) {
            switch (lines[i]) {
                case ImageAndPersonLine ipLine:
                    String imageFile = ipLine.getImageFileName();
                    Path imagePath = csvFolder.resolve(imageFile);
                    File imageFileObj = imagePath.toFile();
                    ipLine.setImageFileNotFound(!imageFileObj.isFile());
                    break;
                default:
                    // ignore other line types
                    break;
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
        Logger.debug(BuilderGUI.buildLogMessage(
            "Retrieving Person info for ", name));
        if (fullNameMap.containsKey(name)) {
            ImageAndPersonLine[] personLines = fullNameMap.get(name);
            Person p = new Person(personLines[0].getPersonFirstName(), personLines[0].getPersonLastName());
            Logger.debug(BuilderGUI.buildLogMessage(
                "getPerson returning: ", p.toString()));
            return p;
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
     * Loads the CSV file specified by the fileName field.
     * The lines field is populated with CSVLine objects.
     * If there is an error reading the file, an error message is
     * displayed and the program exits.
     *
     * This file is protected rather than private so that
     * it can called for testing purposes.
     */
    protected void loadCSVFile() throws IOException, CSVException {
        Logger.trace("In InputCSV.loadCSVFile");
        String dir = getFileDir();
        java.nio.file.Path path = java.nio.file.Paths.get(dir, getFileName());
        List<String> allLines = java.nio.file.Files.readAllLines(path);
        Logger.debug(BuilderGUI.buildLogMessage(
            "Number of lines in InputCSV file: ", Integer.toString(allLines.size())));
        if (allLines.isEmpty()) {
            Logger.error(BuilderGUI.buildLogMessage("File ", path.toString(), " is empty"));
            throw new CSVException("File " + path.toString() + " is empty");
        }
        for (int i = 0; i < allLines.size(); i++) {
            Logger.debug(BuilderGUI.buildLogMessage(
                "Line ", Integer.toString(i), ":", allLines.get(i)));
        }
        lines = new CSVLine[allLines.size()];
        // The following line throws CSVException if header line is invalid.
        // We cannot continue if the header line is invalid.
        HeaderFields hf = new HeaderFields(allLines.get(0));

        // Header is valid, so let's add it to the lines array.
        lines[0] = new ImageAndPersonLine(allLines.get(0), hf);
        // Now process the remaining lines.
        // If a line is invalid, we will add the exception to the CSVLine object.
        // The line will be shown in the GUI as being in error.
        for (int i = 1; i < allLines.size(); i++) {
            try {
                lines[i] = new ImageAndPersonLine(allLines.get(i), hf);
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                if (i == 0) {
                    Logger.error(BuilderGUI.buildLogMessage(
                        "Header line: ", allLines.get(0), " is invalid"));
                    throw new CSVException("Invalid header found in CSV file " + getFileName());
                } else {
                    Logger.error(BuilderGUI.buildLogMessage(
                        "Line ", Integer.toString(i), " is invalid"));
                    Logger.error("ArrayIndexOutOfBoundsException: ", aioobe);
                    throw new CSVException("Invalid line number " + (i + 1) + " found in CSV file " + getFileName()
                        + "\nLine does not contain at least 5 fields.");
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
            Logger.error(BuilderGUI.buildLogMessage(
                "Index out of bounds in InputCSV.insertAt: ", Integer.toString(index)));
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
            Logger.error(BuilderGUI.buildLogMessage(
                "Invalid index in InputCSV.getLine: ", Integer.toString(index)));
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
            Logger.error(BuilderGUI.buildLogMessage(
                "There are no CSVLines for: ", fullName));
            throw new CSVException("The CSV file does not contain lines for " + fullName);
        }
    }

    private void buildFullNameHashMap() {
        Logger.trace("In InputCSV.buildFullNameHashMap");
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
                Logger.debug(BuilderGUI.buildLogMessage(
                    "Adding ", fullName, " to hash map"));
                fullNameMap.put(fullName, new ImageAndPersonLine[] {ipLine});
            } else {
                ImageAndPersonLine[] existingLines = fullNameMap.get(fullName);
                ImageAndPersonLine[] newLines = new ImageAndPersonLine[existingLines.length + 1];
                System.arraycopy(existingLines, 0, newLines, 0, existingLines.length);
                newLines[existingLines.length] = ipLine;
                fullNameMap.put(fullName, newLines);
            }
        }
        Logger.debug(BuilderGUI.buildLogMessage(
            "fullNameHashMap: ", fullNameMap.toString()));
    }

    /**
     * Sorts the full names in the CSV object according to the specified order.
     * @param order - the sort order. See the sortOrder enum for possible values.
     * This file is protected rather than private so that
     * it can called for testing purposes.
     */
    protected void sortNames(SortOrder order) throws IllegalArgumentException {
        Logger.debug(BuilderGUI.buildLogMessage(
            "Sorting names for ", order.toString()));
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
        Logger.debug(BuilderGUI.buildLogMessage(
            "Persons sorted alpha by full name: ", sortedFullNames.toString()));
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
        Logger.debug(BuilderGUI.buildLogMessage(
            "Persons sorted alpha by full name reverse: ", sortedFullNames.toString()));
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
        Logger.debug(BuilderGUI.buildLogMessage(
            "Persons sorted alpha lastName, firstName: ", sortedFullNames.toString()));
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
        Logger.debug(BuilderGUI.buildLogMessage(
            "Persons sorted alpha by lastName, firstName reversed: ", sortedFullNames.toString()));
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
        Logger.debug(BuilderGUI.buildLogMessage(
            "Persons sorted in AsIs order: ", sortedFullNames.toString()));
    }
}
