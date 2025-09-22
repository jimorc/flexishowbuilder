package flexishowbuilder;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class BuilderGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        CSV csv = new CSV.Builder().build();
        if (csv != null) {
            System.out.println("CSV File selected " + csv.getFileName() + "..");
        }
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
