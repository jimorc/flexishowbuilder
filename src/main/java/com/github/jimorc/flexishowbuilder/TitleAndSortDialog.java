package com.github.jimorc.flexishowbuilder;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * TitleAndSortDialog displays a dialog for entry of title and sort order input.
 */
public class TitleAndSortDialog extends Dialog<TitleAndSortData> {
    private final TextArea titleArea;
    private final ToggleGroup sortGroup;
    private final RadioButton noneButton;
    private final RadioButton alphaFullButton;
    private final RadioButton alphaLastFirstButton;
    private final RadioButton alphaFullRevButton;
    private final RadioButton alphaLastFirstRevButton;

    /**
     * Constructor.
     */
    public TitleAndSortDialog() {
        final int spacing = 10;
        final int fontSize = 14;
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
        VBox vbox = new VBox(spacing);
        vbox.getChildren().addAll(titleLabel, titleArea, sortLabel, noneButton,
            alphaFullButton, alphaLastFirstButton, alphaFullRevButton, alphaLastFirstRevButton,
            lastNameLabel, lastNameCheckBox);
        getDialogPane().setContent(vbox);
        setTitle("Set Title and Sort Order");
        setResizable(true);
        getDialogPane().getButtonTypes().addAll(javafx.scene.control.ButtonType.OK,
            javafx.scene.control.ButtonType.CANCEL);
        Button cancel = (Button) this.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.setText("Terminate");
        Button ok = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
        ok.setText("Generate title and person slides");
        setResultConverter(dialogButton -> {
            if (dialogButton == javafx.scene.control.ButtonType.OK) {
                String title = titleArea.getText();
                SortOrder order = (SortOrder) sortGroup.getSelectedToggle().getUserData();
                boolean lastNameAsInitial = lastNameCheckBox.isSelected();
                return new TitleAndSortData(title, order, lastNameAsInitial);
            } else {
                return null;
            }
        });
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
}
