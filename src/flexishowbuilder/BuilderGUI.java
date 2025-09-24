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
        TitleAndSortData data = new TitleAndSortDialog().showAndWait().orElse(null);
        if (data != null) {
            System.out.println("Title: " + data.getTitle());
            System.out.println("Sort Order: " + data.getOrder());
            csv.sort(data.getOrder());
        }
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
