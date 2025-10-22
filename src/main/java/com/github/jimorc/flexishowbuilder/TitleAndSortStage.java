package com.github.jimorc.flexishowbuilder;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * TitleAndSortStage contains inputs for title image text and the image sort order.
 */
public class TitleAndSortStage {
    private final int stageWidth = 1080;
    private final int stageHeight = 800;
    private final int spacing = 10;
    private final TextArea titleArea;
    private final ToggleGroup sortGroup;
    private final RadioButton noneButton;
    private final RadioButton alphaFullButton;
    private final RadioButton alphaLastFirstButton;
    private final RadioButton alphaFullRevButton;
    private final RadioButton alphaLastFirstRevButton;
    private Stage stage;

    /**
     * Constructor.
     */
    public TitleAndSortStage() {
        final int fontSize = 14;
        final int tLabelMarginTop = 5;
        final int topMargin = 0;
        final int rightMargin = 10;
        final int bottomMargin = 0;
        final int leftMargin = 10;
        final int buttonTopMargin = 5;
        final int buttonRightMargin = 20;
        final int buttonBottomMargin = 5;
        final int buttonLeftMargin = 20;
        Label titleLabel = new Label("Show Title Text");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        titleArea = createTextArea();

        Label sortLabel = new Label("Sort Order");
        sortLabel.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        sortGroup = new ToggleGroup();
        noneButton = createRadioButton("As Is", sortGroup, SortOrder.AsIs);
        Tooltip noneTooltip = new Tooltip("No sorting - use the order in the CSV file.\n"
            + "All images for each person are grouped together.");
        noneButton.setTooltip(noneTooltip);
        alphaFullButton = createRadioButton("Alphabetical by Full Name", sortGroup, SortOrder.AlphabeticalByFullName);
        Tooltip alphaFullTooltip = new Tooltip("Sort by person's full name (first name then last "
            + "name).\nAll images for each person are grouped together.");
        alphaFullButton.setTooltip(alphaFullTooltip);
        alphaLastFirstButton = createRadioButton("Alphabetical by Last Name then First Name",
            sortGroup, SortOrder.AlphabeticalByLastNameThenFirstName);
        Tooltip alphaLastFirstTooltip = new Tooltip("Sort by person's last name then first name.\n"
            + "All images for each person are grouped together.");
        alphaLastFirstButton.setTooltip(alphaLastFirstTooltip);
        alphaFullRevButton = createRadioButton("Alphabetical by Full Name Reverse",
            sortGroup, SortOrder.AlphabeticalByFullNameReverse);
        Tooltip alphaFullRevTooltip = new Tooltip("Sort by person's full name (first name then last "
            + "name) in reverse order.\nAll images for each person are grouped together.");
        alphaFullRevButton.setTooltip(alphaFullRevTooltip);
        alphaLastFirstRevButton = createRadioButton("Alphabetical by Last Name then First Name Reverse",
            sortGroup, SortOrder.AlphabeticalByLastNameThenFirstNameReverse);
        Tooltip alphaLastFirstRevTooltip = new Tooltip("Sort by person's last name then first name "
            + "in reverse order.\nAll images for each person are grouped together.");
        alphaLastFirstRevButton.setTooltip(alphaLastFirstRevTooltip);
        noneButton.setSelected(true);

        Label lastNameLabel = new Label("Last name in persion slides");
        lastNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        CheckBox lastNameCheckBox = new CheckBox("Show last name as Initial");
        lastNameCheckBox.selectedProperty().set(true);
        Tooltip lastNameTooltip = new Tooltip("If checked, the person's last name will be shown "
            + "as an initial in the\nperson slides. If not selected, the full last name will be shown.");
        lastNameCheckBox.setTooltip(lastNameTooltip);
        Button cancel = new Button("Terminate");
        cancel.setCancelButton(true);
        Button gen = new Button("Generate title and person slides");
        gen.setDefaultButton(true);
        HBox buttonBox = new HBox(spacing);
        buttonBox.getChildren().addAll(cancel, gen);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        Insets buttonInsets = new Insets(buttonTopMargin, buttonRightMargin,
            buttonBottomMargin, buttonLeftMargin);
        HBox.setMargin(cancel, buttonInsets);
        HBox.setMargin(gen, buttonInsets);

        VBox vbox = new VBox(spacing);
        vbox.getChildren().addAll(titleLabel, titleArea, sortLabel, noneButton,
            alphaFullButton, alphaLastFirstButton, alphaFullRevButton, alphaLastFirstRevButton,
            lastNameLabel, lastNameCheckBox, buttonBox);
        Insets vBoxInsets = new Insets(topMargin, rightMargin, bottomMargin, leftMargin);
        Insets tLabelInsets = new Insets(tLabelMarginTop, rightMargin, bottomMargin, leftMargin);
        VBox.setMargin(titleLabel, tLabelInsets);
        VBox.setMargin(titleArea, vBoxInsets);
        VBox.setMargin(sortLabel, vBoxInsets);
        VBox.setMargin(noneButton, vBoxInsets);
        VBox.setMargin(alphaFullButton, vBoxInsets);
        VBox.setMargin(alphaLastFirstButton, vBoxInsets);
        VBox.setMargin(alphaFullRevButton, vBoxInsets);
        VBox.setMargin(alphaLastFirstRevButton, vBoxInsets);
        VBox.setMargin(lastNameLabel, vBoxInsets);
        VBox.setMargin(lastNameCheckBox, vBoxInsets);
        stage = new Stage();
        Scene scene = new Scene(vbox);
        stage.setWidth(stageWidth);
        stage.setHeight(stageHeight);
        stage.setScene(scene);
    }

    private TextArea createTextArea() {
        final int prefColumnCount = 50;
        final int prefRowCount = 2;
        TextArea textArea = new TextArea();
        textArea.setPromptText("Enter title text here");
        textArea.setPrefColumnCount(prefColumnCount);
        textArea.setPrefRowCount(prefRowCount);
        Tooltip tTooltip = new Tooltip("Enter the title text to appear on the title slide. Two lines is recommended.");
        textArea.setTooltip(tTooltip);
        // TODO: centre text
        return textArea;
    }

    private RadioButton createRadioButton(String text, ToggleGroup group, SortOrder order) {
        RadioButton button = new RadioButton(text);
        button.setToggleGroup(group);
        button.setUserData(order);
        return button;
    }

    /**
     * showAndWait displays the StartStage object and waits for a close request.
     */
    public void showAndWait() {
        stage.showAndWait();
    }
}
