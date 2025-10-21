package com.github.jimorc.flexishowbuilder;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * StartStage is the first Stage that is displayed.
 */
public class StartStage {
    private final int stageWidth = 1080;
    private final int stageHeight = 800;
    private final int spacing = 100;
    private Stage stage;

    /**
     * Constructor.
     */
    public StartStage() {
        Button loadCSV = new Button("Load CSV");
        loadCSV.setOnAction(_ -> {
            System.out.println("loadCSV clicked!");
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
}
