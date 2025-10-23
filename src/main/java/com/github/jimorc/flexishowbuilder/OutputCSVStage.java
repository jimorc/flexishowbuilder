package com.github.jimorc.flexishowbuilder;

import java.io.IOException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * OutputCSVStage is the panel that displays the OutputCSV object.
 */
public class OutputCSVStage extends FlexiStage {

    /**
     * OutputCSVStage constructor.
     * @param csv the OutputCSV object to display.
     * @param dir the folder to save the XLS file to when the "Save" button is clicked.
     */
    public OutputCSVStage(OutputCSV csv, String dir) {
        GridPane grid = createGrid(csv);
        ScrollPane sPane = new ScrollPane();
        sPane.setContent(grid);
        HBox buttonBox = createButtonBox(csv, dir);
        VBox box = new VBox(sPane, buttonBox);
        Scene scene = new Scene(box);
        this.setScene(scene);
    }

    private GridPane createGrid(OutputCSV csv) {
        final int imageCol = 0;
        final int titleCol = 1;
        final int fullNameCol = 2;
        final int firstNameCol = 3;
        final int lastNameCol = 4;
        final int gridGap = 5;
        final int padding = 10;

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(padding));
        grid.setVgap(gridGap);
        grid.setHgap(gridGap);

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

    private HBox createButtonBox(OutputCSV csv, String dir) {
        final int buttonTopMargin = 5;
        final int buttonRightMargin = 20;
        final int buttonBottomMargin = 5;
        final int buttonLeftMargin = 20;

        Insets insets = new Insets(buttonTopMargin, buttonRightMargin,
            buttonBottomMargin, buttonLeftMargin);
        QuitButton quit = new QuitButton();
        HBox.setMargin(quit, insets);

        FlexiButton save = new FlexiButton("Save to slideshow.xls");
        save.setDefaultButton(true);
        save.setOnAction(_ -> {
            XLSWorkbook workbook = new XLSWorkbook(csv);
            try {
                workbook.writeToFile(dir + "/slideshow.xls");
            } catch (IOException ioe) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("IO Error");
                alert.setHeaderText("Error encountered while writing the XLS file.");
                alert.setContentText(ioe.getMessage()
                    + "\nProgram will now exit.");
                alert.showAndWait();
                System.exit(1);
            }
            this.close();
        });
        HBox.setMargin(save, insets);
        HBox box = new HBox(quit, save);
        box.setAlignment(Pos.CENTER_RIGHT);

        return box;
    }

}
