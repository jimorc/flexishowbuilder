package flexishowbuilder;

import java.io.File;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class BuilderGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
        Stage savedStage = stage;
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));

        File csv = fileChooser.showOpenDialog(savedStage);

        if (csv != null) {
            System.out.println("CSV File selected " + csv.getName() + "..");
        } else {
            System.out.println("CSV file selection cancelled.");
        }
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
