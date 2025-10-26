package com.github.jimorc.flexishowbuilder;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.tinylog.Logger;

/**
 * BuilderGUI is the main App.
 */
public class BuilderGUI extends Application {
    @Override
    public void start(Stage stage) {
        System.setProperty("LOG_LEVEL", "trace");
        Logger.trace("flexishowbuilder starting.");
        StartStage startStage = new StartStage();

        startStage.showAndWait();
        Logger.trace("Have returned from StartStage.");
        InputCSV iCSV = startStage.getInputCSV();
        TitleAndSortStage tsStage = new TitleAndSortStage();
        tsStage.showAndWait();
        TitleAndSortData data = tsStage.getData();
        Logger.debug(BuilderGUI.buildLogMessage(
            "TitleAndSortData after return from TitleAndSortStage: ", data.toString()));

        OutputCSV out = generateOutputCSV(iCSV, data);
        Logger.debug(BuilderGUI.buildLogMessage(
            "OutputCSV after creation:\n", out.toString()));
        Logger.trace("Creating outCSVStage");
        OutputCSVStage outCSVStage = new OutputCSVStage(out, iCSV.getFileDir());
        outCSVStage.showAndWait();
        Logger.trace("Back from OutputCSVStage");
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * handleCSVException displays an error alert.
     * @param csve the CSVException to report.
     */
    protected static void handleCSVException(CSVException csve) {  // no return
        Alert alert = new Alert(null);
        String msg = csve.getMessage();
        alert.setAlertType(AlertType.ERROR);
        alert.setTitle("CSV File Error");
        alert.setHeaderText("Error processing CSV file");
        alert.setContentText(msg + "\nProgram will now terminate.");
        alert.showAndWait();
        System.exit(1);
    }

    /**
     * handleIOException displays an error alert.
     * @param ioe the IOException to report.
     * @param csv the InputCSV object that the exception occurred on.
     */
    protected static void handleIOException(IOException ioe, InputCSV csv) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("CSV File Error");
        alert.setHeaderText("IOException attempting to read CSV file: "
            + (csv != null ? csv.getFileName() : "No file"));
        alert.setContentText(ioe.getMessage() + "\nProgram will now exit.");
        alert.showAndWait();
        System.exit(1);
    }

    /**
     * handlePersonException displays an error alert.
     * @param e the PersonException
     */
    protected static void handlePersonException(Exception e) {  // no return
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Person Error");
        alert.setHeaderText("Mismatch between requested person's name and CSV");
        alert.setContentText(e.getMessage() + "\nProgram will now exit.");
        alert.showAndWait();
        System.exit(1);
    }

    private OutputCSV generateOutputCSV(InputCSV csv, TitleAndSortData data) {
        Logger.trace("In BuilderGUI.generateOutputCSV");
        OutputCSV out = new OutputCSV();
        try {
            out.appendLine(csv.getLine(0));
            String dir = csv.getFileDir();
            String titleFileName = dir + "/title.jpg";
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
            Logger.error("CSVException thrown in generateOutputCSV: ", e);
            handlePersonException(e);
        } catch (IOException ioe) {
            Logger.error("IOException thrown in generateOutputCSV: ", ioe);
            handleIOException(ioe, csv);
        }
        return out;
    }

    /**
     * buildLogMessage combines various String objects into a single string.
     * @param parts the Strings to combine into a single String object.
     * @return
     */
    public static String buildLogMessage(String... parts) {
        StringBuffer sb = new StringBuffer();
        for (String part: parts) {
            sb.append(part);
        }
        return sb.toString();
    }
}
