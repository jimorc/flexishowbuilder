package com.github.jimorc.flexishowbuilder;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OutputCSVStage {
    private final int stageWidth = 1080;
    private final int stageHeight = 800;
    private final int spacing = 10;
    private Stage stage;

    public OutputCSVStage(OutputCSV csv) {
        GridPane grid = createGrid(csv);
        ScrollPane sPane = new ScrollPane();
        sPane.setContent(grid);
        Scene scene = new Scene(sPane);
        stage = new Stage();
        stage.setScene(scene);
        stage.setWidth(stageWidth);
        stage.setHeight(stageHeight);
    }

    /**
     * showAndWait displays the StartStage object and waits for a close request.
     */
    public void showAndWait() {
        stage.showAndWait();
    }

    private GridPane createGrid(OutputCSV csv) {
        final int imageCol = 0;
        final int titleCol = 1;
        final int fullNameCol = 2;
        final int firstNameCol = 3;
        final int lastNameCol = 4;

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(5);
        grid.setHgap(5);

        CSVLine[] lines = csv.getLines();
        int row = 1;
        for (CSVLine line : lines) {
            switch (line) {
                case TitleImageLine l:
                    grid.add(new Text(l.field(imageCol)), imageCol, row++);
                    break;
                case ImageAndPersonLine ipl:
                    grid.add(new Text(ipl.field(imageCol)), imageCol, row);
                    grid.add(new Text(ipl.field(titleCol)), titleCol, row);
                    grid.add(new Text(ipl.field(fullNameCol)), fullNameCol, row);
                    grid.add(new Text(ipl.field(firstNameCol)), firstNameCol, row);
                    grid.add(new Text(ipl.field(lastNameCol)), lastNameCol, row++);
                    break;
                default:
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Programming Error");
                    alert.setHeaderText("There appears to be a programming error.");
                    alert.setContentText("Please report it.\n"
                        + "Program will now exit.");
                    alert.showAndWait();
                    System.exit(1);
            }
        }
        return grid;
    }

}
