package com.github.jimorc.flexishowbuilder;

import java.io.File;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        Button loadCSV = new Button("Load CSV");
        loadCSV.setOnAction(_ -> {
            loadCSVFile();
            this.close();
            try {
                iCSV.validateCSVFile();
            } catch (CSVException ce) {
                BuilderGUI.handleCSVException(ce);
            }
        });
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(_ -> {
            System.exit(0);
        });
        VBox box = new VBox(spacing);
        box.getChildren().addAll(loadCSV, exitButton);
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box);
        this.setScene(scene);
    }

    private void loadCSVFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
        File csvFile = fileChooser.showOpenDialog(null);

        if (csvFile != null) {
            try {
                iCSV = new InputCSV(csvFile);
            } catch (CSVException e) {
                BuilderGUI.handleCSVException(e);
            } catch (IOException ioe) {
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
