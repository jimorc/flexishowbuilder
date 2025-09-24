package flexishowbuilder;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TitleAndSortDialog extends Dialog<TitleAndSortData> {
    private final TextArea titleArea;
    private final ToggleGroup sortGroup;
    private final RadioButton noneButton;
    private final RadioButton alphaFullButton;
    private final RadioButton alphaLastFirstButton;
    private final RadioButton alphaFullRevButton;
    private final RadioButton alphaLastFirstRevButton;

    public TitleAndSortDialog() {
        Label titleLabel = new Label("Show Title Text");
        Label titleHelp = new Label("(2 lines recommended)");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        titleArea = createTextArea();
        Label sortLabel = new Label("Sort Order");
        sortLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        sortGroup = new ToggleGroup();
        noneButton = createRadioButton("As Is", sortGroup, sortOrder.NONE);
        alphaFullButton = createRadioButton("Alphabetical by Full Name", sortGroup, sortOrder.ALPHABETICAL_BY_FULL_NAME);
        alphaLastFirstButton = createRadioButton("Alphabetical by Last Name then First Name", sortGroup, sortOrder.ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME);
        alphaFullRevButton = createRadioButton("Alphabetical by Full Name Reverse", sortGroup, sortOrder.ALPHABETICAL_BY_FULL_NAME_REVERSE);
        alphaLastFirstRevButton = createRadioButton("Alphabetical by Last Name then First Name Reverse", sortGroup, sortOrder.ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME_REVERSE);
        noneButton.setSelected(true);
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(titleLabel, titleHelp, titleArea, sortLabel, noneButton,
            alphaFullButton, alphaLastFirstButton, alphaFullRevButton, alphaLastFirstRevButton);
        getDialogPane().setContent(vbox);
        setTitle("Set Title and Sort Order");
        setResizable(true);
        getDialogPane().getButtonTypes().addAll(javafx.scene.control.ButtonType.OK,
            javafx.scene.control.ButtonType.CANCEL);
        Button cancel =(Button)this.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.setText("Terminate");
        Button ok =(Button)this.getDialogPane().lookupButton(ButtonType.OK);
        ok.setText("Generate title and person slides");
        setResultConverter(dialogButton -> {
            if (dialogButton == javafx.scene.control.ButtonType.OK) {
                String title = titleArea.getText();
                sortOrder order = (sortOrder)sortGroup.getSelectedToggle().getUserData();
                return new TitleAndSortData(title, order);
            } else {
                return null;
            }
        });
    }

    private TextArea createTextArea() {
        TextArea textArea = new TextArea();
        textArea.setPromptText("Enter title text here");
        textArea.setPrefColumnCount(50);
        textArea.setPrefRowCount(2);
        // TODO: centre text
        return textArea;
    }

    private RadioButton createRadioButton(String text, ToggleGroup group, sortOrder order) {
        RadioButton button = new RadioButton(text);
        button.setToggleGroup(group);
        button.setUserData(order);
        return button;
    }
}

class TitleAndSortData {
    private final String title;
    private final sortOrder order;

    public TitleAndSortData(String title, sortOrder order) {
        this.title = title;
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public sortOrder getOrder() {
        return order;
    }
}
