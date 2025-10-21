package com.github.jimorc.flexishowbuilder;

import java.io.File;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.swing.filechooser.FileSystemView;

/**
 * StartStage is the first Stage that is displayed.
 */
public class StartStage {
    private final int stageWidth = 1080;
    private final int stageHeight = 800;
    private final int spacing = 100;
    private InputCSV iCSV;
    private Stage stage;

    /**
     * Constructor.
     */
    public StartStage() {
        Button loadCSV = new Button("Load CSV");
        loadCSV.setOnAction(_ -> {
            loadCSVFile();
            try {
                iCSV.validateCSVFile();
            } catch (CSVException ce) {
                BuilderGUI.handleCSVException(ce);
            }
        });
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(_ -> {
            stage.close();
        });
        VBox box = new VBox(spacing);
        box.getChildren().addAll(loadCSV, exitButton);
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box);
        stage = new Stage();
        stage.setWidth(stageWidth);
        stage.setHeight(stageHeight);
        stage.setScene(scene);
    }

    /**
     * showAndWait displays the StartStage object and waits for a close request.
     */
    public void showAndWait() {
        stage.showAndWait();
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
}
