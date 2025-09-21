package flexishowbuilder;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class BuilderGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        StackPane pane = new StackPane();
        pane.getChildren().add(new Label("Hello, FlexiShow Builder!"));
        Scene scene = new Scene(pane, 400, 300);
        stage.setScene(scene);
        stage.setTitle("FlexiShow Builder");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
