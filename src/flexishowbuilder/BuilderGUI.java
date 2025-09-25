package flexishowbuilder;

import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Application;
import javafx.stage.Stage;

public class BuilderGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        CSV csv = null;
        try {
            csv = new CSV.Builder().build();
            if (csv != null) {
                System.out.println("CSV File selected " + csv.getFileName() + "..");
                csv.validateCSVFile();
                System.out.println("CSV File validated");
            }
        } catch (CSVException csve) {
            Alert alert = new Alert(null);
            String msg = "";
            switch (csve.getType()) {
                case MISSINGIMAGES:
                    alert.setAlertType(AlertType.ERROR);
                    alert.setTitle("Missing Images");
                    alert.setHeaderText("Some images listed in CSV file " + (csv != null ? csv.getFileName() : "No file") + " are missing:");
                    msg = csve.getMessage();;
                    break;
                case EMPTY:
                    alert.setAlertType(AlertType.ERROR);
                    alert.setTitle("CSV File Error");
                    alert.setHeaderText("CSV file \"" + (csv != null ? csv.getFileName() : "No file") +"\"");
                    msg = csve.getMessage();
                    break;
                case INVALIDHEADER:
                    alert.setAlertType(AlertType.ERROR);
                    alert.setTitle("CSV File Error");
                    alert.setHeaderText("Fatal Error");
                    msg = csve.getMessage();
                    break;
                    case INVALIDLINE:
                    alert.setAlertType(AlertType.ERROR);
                    alert.setTitle("CSV File Error");
                    alert.setHeaderText("CSV File \"" + (csv != null ? csv.getFileName() : "No file") + "\"");
                    msg = csve.getMessage();
                    break;
                case UNKNOWN:
                    alert.setAlertType(AlertType.ERROR);
                    alert.setTitle("CSV File");
                    alert.setHeaderText("Unknown error attempting to read CSV file: " + (csv != null ? csv.getFileName() : "No file"));
                    msg = csve.getMessage();
                    break;
                case NOFILE:
                    alert.setAlertType(AlertType.INFORMATION);
                    alert.setTitle("CSV File");
                    alert.setHeaderText("No CSV file selected");
                    msg = "";
            }
            alert.setContentText(msg + "\nProgram will now terminate.");
            alert.showAndWait();
            System.exit(1);
        }
        catch (IOException ioe) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("CSV File Error");
            alert.setHeaderText("IOException attempting to read CSV file: " + (csv != null ? csv.getFileName() : "No file"));
            alert.setContentText(ioe.getMessage() + "\nProgram will now exit.");
            alert.showAndWait();
            System.exit(1);
        }
        catch (Exception e) {   // catch any other exceptions
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("CSV File Error");
            alert.setHeaderText("Exception attempting to read CSV file: " + (csv != null ? csv.getFileName() : "No file"));
            alert.setContentText(e.getMessage() + "\nProgram will now exit.");
            alert.showAndWait();
            System.exit(1);
        }
        if (csv != null) {
            TitleAndSortData data = new TitleAndSortDialog().showAndWait().orElse(null);
            if (data != null) {
                System.out.println("Title: " + data.getTitle());
                System.out.println("Sort Order: " + data.getOrder());
                csv.sort(data.getOrder());
            }
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
