package com.github.jimorc.flexishowbuilder;

import java.io.File;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * StartStage is the first Stage that is displayed.
 */
public class StartStage extends FlexiStage {
    private final int spacing = 100;
    private InputCSV iCSV;

    /**
     * Constructor.
     */
    public StartStage() {
        BuilderGUI.LOG.debug("In StartStage constructor");
        FlexiButton loadCSV = new FlexiButton("Load CSV");
        loadCSV.setOnAction(_ -> {
            BuilderGUI.LOG.debug("Processing Load CSV button click");
            loadCSVFile();
            BuilderGUI.LOG.debug("Closing StartStage");
            this.close();
            try {
                iCSV.validateCSVFile();
            } catch (CSVException ce) {
                BuilderGUI.LOG.debug("validateCSVFile threw CSVException: ", ce);
                BuilderGUI.handleCSVException(ce);
            }
        });
        QuitButton quit = new QuitButton();
        VBox box = new VBox(spacing);
        box.getChildren().addAll(loadCSV, quit);
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box);
        this.setScene(scene);
        BuilderGUI.LOG.debug("Returning from StartStage constructor");
    }

    private void loadCSVFile() {
        BuilderGUI.LOG.debug("Building and showing FileChooser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
        File csvFile = fileChooser.showOpenDialog(null);
        BuilderGUI.LOG.debug("Back from FileChooser");

        if (csvFile != null) {
            try {
                iCSV = new InputCSV(csvFile);
            } catch (CSVException e) {
                BuilderGUI.LOG.debug("InputCSV threw CSVException: ", e);
                BuilderGUI.handleCSVException(e);
            } catch (IOException ioe) {
                BuilderGUI.LOG.debug("InputCSV constructor threw IOException: ", ioe);
                BuilderGUI.handleIOException(ioe, iCSV);
            }
        }
    }

    /**
     * Retrieve the loaded InputCSV object.
     * @return the InputCSV object.
     */
    public InputCSV getInputCSV() {
        return iCSV;
    }
}
