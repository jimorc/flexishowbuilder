package flexishowbuilder;

import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class BuilderGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        CSV csv = null;
        try {
            csv = new CSV.Builder().build();
            if (csv != null) {
                System.out.println("CSV File selected " + csv.getFileName() + "..");
            }
        } catch (CSVException csve) {
            Alert alert = new Alert(null);
            String msg;
            if (csve.getType() == CSVExceptionType.NOFILE) {
                msg = "";
                alert.setTitle("CSV File");
                alert.setAlertType(AlertType.INFORMATION);
                alert.setHeaderText("No CSV file selected");
            } else {
                msg = csve.getMessage();
                alert.setTitle("CSV File Error");
                alert.setAlertType(AlertType.ERROR);
                alert.setHeaderText("Error attempting to read CSV file: " + (csv != null ? csv.getFileName() : "No file"));
            }
            alert.setContentText(msg + "\nProgram will now exit.");
            alert.showAndWait();
            Platform.exit();
        }
        catch (IOException ioe) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("CSV File Error");
            alert.setHeaderText("IOException attempting to read CSV file: " + (csv != null ? csv.getFileName() : "No file"));
            alert.setContentText(ioe.getMessage() + "\nProgram will now exit.");
            alert.showAndWait();
            Platform.exit();
        }
        if (csv != null) {
            TitleAndSortData data = new TitleAndSortDialog().showAndWait().orElse(null);
            if (data != null) {
                System.out.println("Title: " + data.getTitle());
                System.out.println("Sort Order: " + data.getOrder());
                csv.sort(data.getOrder());
            }
        }
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
