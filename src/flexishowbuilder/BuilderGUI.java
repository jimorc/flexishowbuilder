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
            handleCSVException(csve); // no return
        }
        catch (IOException ioe) {
            handleIOException(ioe, csv); // no return
        }
        catch (Exception e) {   // catch any other exceptions
            handleException(e, csv); // no return
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

    private void handleCSVException(CSVException csve) {  // no return
        Alert alert = new Alert(null);
        String msg = csve.getMessage();
        alert.setAlertType(AlertType.ERROR);
        alert.setTitle("CSV File Error");
        alert.setHeaderText("Error processing CSV file");
        alert.setContentText(msg + "\nProgram will now terminate.");
        alert.showAndWait();
        System.exit(1);
    }
    private void handleIOException(IOException ioe, CSV csv) {  // no return
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("CSV File Error");
        alert.setHeaderText("IOException attempting to read CSV file: " + (csv != null ? csv.getFileName() : "No file"));
        alert.setContentText(ioe.getMessage() + "\nProgram will now exit.");
        alert.showAndWait();
        System.exit(1);
    }

    private void handleException(Exception e, CSV csv) {  // no return
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("CSV File Error");
        alert.setHeaderText("Exception attempting to read CSV file: " + (csv != null ? csv.getFileName() : "No file"));
        alert.setContentText(e.getMessage() + "\nProgram will now exit.");
        alert.showAndWait();
        System.exit(1);
    }
}
