package com.github.jimorc.flexishowbuilder;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * BuilderGUI is the main App.
 */
public class BuilderGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        InputCSV csv = null;
        try {
            csv = new InputCSV.Builder().build();
            if (csv != null) {
                csv.validateCSVFile();
            }
        } catch (CSVException csve) {
            handleCSVException(csve); // no return
        } catch (IOException ioe) {
            handleIOException(ioe, csv); // no return
        }
        if (csv != null) {
            TitleAndSortData data = new TitleAndSortDialog().showAndWait().orElse(null);
            if (data != null) {
                OutputCSV out = generateOutputCSV(csv, data);
                System.out.println(out);
                XLSWorkbook workbook = new XLSWorkbook(out);
                workbook.writeToFile(csv.getFileDir() + "/output.xls");
            }
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void handleCSVException(CSVException csve) {  // no return
        Alert alert = new Alert(null);
        String msg = csve.getMessage();
        alert.setAlertType(AlertType.ERROR);
        alert.setTitle("CSV File Error");
        alert.setHeaderText("Error processing CSV file");
        alert.setContentText(msg + "\nProgram will now terminate.");
        alert.showAndWait();
        System.exit(1);
    }

    private void handleIOException(IOException ioe, InputCSV csv) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("CSV File Error");
        alert.setHeaderText("IOException attempting to read CSV file: "
            + (csv != null ? csv.getFileName() : "No file"));
        alert.setContentText(ioe.getMessage() + "\nProgram will now exit.");
        alert.showAndWait();
        System.exit(1);
    }

    private void handlePersonException(Exception e) {  // no return
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Person Error");
        alert.setHeaderText("Mismatch between requested person's name and CSV");
        alert.setContentText(e.getMessage() + "\nProgram will now exit.");
        alert.showAndWait();
        System.exit(1);
    }

    private OutputCSV generateOutputCSV(InputCSV csv, TitleAndSortData data) {
        OutputCSV out = new OutputCSV();
        try {
            out.appendLine(csv.getLine(0));
            String dir = csv.getFileDir();
            String titleFileName = dir + "/title.jpg";
            System.out.println(dir);
            TitleImage.generateTitleImage(data.getTitle(), titleFileName);
            out.appendLine(new TitleImageLine("title.jpg"));

            csv.sortNames(data.getOrder());
            ArrayList<String> fullNames = csv.getSortedFullNames();
            for (String name : fullNames) {
                Person person = csv.getPerson(name);
                String fName = name.replaceAll(" ", "_");
                String fileName = dir + "/" + fName + ".jpg";
                String title = "";
                if (data.isLastNameAsInitial()) {
                    title = person.getFirstPlusInitial();
                } else {
                    title = person.getFullName();
                }
                TitleImage.generateTitleImage(title, fileName);
                out.appendLine(new TitleImageLine(fName + ".jpg"));
                ImageAndPersonLine[] lines = csv.getImageLines(name);
                for (ImageAndPersonLine line: lines) {
                    out.appendLine(line);
                }
            }
            out.appendLine(new TitleImageLine("title.jpg"));
        } catch (CSVException e) {
            handlePersonException(e);
        } catch (IOException ioe) {
            handleIOException(ioe, csv);
        }
        return out;
    }
}
