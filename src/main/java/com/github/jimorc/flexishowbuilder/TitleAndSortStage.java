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

/**
 * TitleAndSortStage contains inputs for title image text and the image sort order.
 */
public class TitleAndSortStage extends FlexiStage {
    private final int spacing = 10;
    private SortOrder sortOrder = SortOrder.AsIs;
    private TextArea titleArea;
    private ToggleGroup sortGroup;
    private RadioButton noneButton;
    private RadioButton alphaFullButton;
    private RadioButton alphaLastFirstButton;
    private RadioButton alphaFullRevButton;
    private RadioButton alphaLastFirstRevButton;
    private CheckBox lastNameCheckBox;

    /**
     * Constructor.
     */
    public TitleAndSortStage() {
        VBox vbox = createBox();
        Scene scene = new Scene(vbox);
        this.setScene(scene);
    }

    /**
     * getData retrieves the values set in the TitleAndSortStage object.
     * @return data set in stage object.
     */
    public TitleAndSortData getData() {
        TitleAndSortData data = new TitleAndSortData(titleArea.getText(), sortOrder,
            lastNameCheckBox.isSelected());
        return data;
    }

    private VBox createBox() {
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
        final Font labelFont = Font.font("Arial", FontWeight.BOLD, fontSize);
        Insets vBoxInsets = new Insets(topMargin, rightMargin, bottomMargin, leftMargin);
        Insets tLabelInsets = new Insets(tLabelMarginTop, rightMargin, bottomMargin, leftMargin);
        Label titleLabel = createTitleLabel(labelFont, tLabelInsets);
        titleArea = createTextArea(vBoxInsets);

        Label sortLabel = createSortLabel(labelFont, vBoxInsets);
        sortGroup = new ToggleGroup();
        createNoneButton(vBoxInsets);
        createAlphaFullButton(vBoxInsets);
        createAlphaLastFirstButton(vBoxInsets);
        createAlphaFullRevButton(vBoxInsets);
        createAlphaLastFirstRevButton(vBoxInsets);

        Label lastNameLabel = createLastNameLabel(fontSize, vBoxInsets);
        createLastNameCheckBox(vBoxInsets);

        HBox buttonBox = createButtonBox(buttonTopMargin, buttonRightMargin, buttonBottomMargin, buttonLeftMargin);

        VBox vbox = new VBox(spacing);
        vbox.getChildren().addAll(titleLabel, titleArea, sortLabel, noneButton,
            alphaFullButton, alphaLastFirstButton, alphaFullRevButton, alphaLastFirstRevButton,
            lastNameLabel, lastNameCheckBox, buttonBox);
        return vbox;
    }

    private HBox createButtonBox(final int buttonTopMargin, final int buttonRightMargin, final int buttonBottomMargin,
            final int buttonLeftMargin) {
        Button cancel = createCancelButton();
        Button gen = createGenButton();
        HBox buttonBox = new HBox(spacing);
        buttonBox.getChildren().addAll(cancel, gen);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        Insets buttonInsets = new Insets(buttonTopMargin, buttonRightMargin,
            buttonBottomMargin, buttonLeftMargin);
        HBox.setMargin(cancel, buttonInsets);
        HBox.setMargin(gen, buttonInsets);
        return buttonBox;
    }

    private Button createGenButton() {
        Button gen = new Button("Generate title and person slides");
        gen.setDefaultButton(true);
        gen.setOnAction(_ -> {
            this.close();
        });
        return gen;
    }

    private Button createCancelButton() {
        Button cancel = new Button("Quit");
        cancel.setCancelButton(true);
        cancel.setOnAction(_ -> System.exit(0));
        return cancel;
    }

    private void createLastNameCheckBox(Insets insets) {
        lastNameCheckBox = new CheckBox("Show last name as Initial");
        lastNameCheckBox.selectedProperty().set(true);
        Tooltip lastNameTooltip = new Tooltip("If checked, the person's last name will be shown "
            + "as an initial in the\nperson slides. If not selected, the full last name will be shown.");
        lastNameCheckBox.setTooltip(lastNameTooltip);
        VBox.setMargin(lastNameCheckBox, insets);
    }

    private Label createLastNameLabel(final int fontSize, final Insets insets) {
        Label lastNameLabel = new Label("Last name in persion slides");
        lastNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        VBox.setMargin(lastNameLabel, insets);
        return lastNameLabel;
    }

    private Label createTitleLabel(final Font labelFont, final Insets insets) {
        Label titleLabel = new Label("Show Title Text");
        titleLabel.setFont(labelFont);
        VBox.setMargin(titleLabel, insets);
        return titleLabel;
    }

    private Label createSortLabel(final Font labelFont, final Insets insets) {
        Label sortLabel = new Label("Sort Order");
        sortLabel.setFont(labelFont);
        VBox.setMargin(sortLabel, insets);
        return sortLabel;
    }

    private void createNoneButton(Insets insets) {
        noneButton = createRadioButton("As Is", sortGroup, SortOrder.AsIs);
        Tooltip noneTooltip = new Tooltip("No sorting - use the order in the CSV file.\n"
            + "All images for each person are grouped together.");
        noneButton.setTooltip(noneTooltip);
        noneButton.setOnAction(_ -> {
            sortOrder = SortOrder.AsIs;
        });
        noneButton.setSelected(true);
        VBox.setMargin(noneButton, insets);
    }

    private void createAlphaFullButton(Insets insets) {
        alphaFullButton = createRadioButton("Alphabetical by Full Name", sortGroup, SortOrder.AlphabeticalByFullName);
        alphaFullButton.setOnAction(_ -> {
            sortOrder = SortOrder.AlphabeticalByFullName;
        });
        Tooltip alphaFullTooltip = new Tooltip("Sort by person's full name (first name then last "
            + "name).\nAll images for each person are grouped together.");
        alphaFullButton.setTooltip(alphaFullTooltip);
        VBox.setMargin(alphaFullButton, insets);
    }

    private void createAlphaLastFirstRevButton(Insets insets) {
        alphaLastFirstRevButton = createRadioButton("Alphabetical by Last Name then First Name Reverse",
            sortGroup, SortOrder.AlphabeticalByLastNameThenFirstNameReverse);
        alphaLastFirstRevButton.setOnAction(_ -> {
            sortOrder = SortOrder.AlphabeticalByLastNameThenFirstNameReverse;
        });
        Tooltip alphaLastFirstRevTooltip = new Tooltip("Sort by person's last name then first name "
            + "in reverse order.\nAll images for each person are grouped together.");
        alphaLastFirstRevButton.setTooltip(alphaLastFirstRevTooltip);
        VBox.setMargin(alphaLastFirstRevButton, insets);
    }

    private void createAlphaLastFirstButton(Insets insets) {
        alphaLastFirstButton = createRadioButton("Alphabetical by Last Name then First Name",
            sortGroup, SortOrder.AlphabeticalByLastNameThenFirstName);
        alphaLastFirstButton.setOnAction(_ -> {
            sortOrder = SortOrder.AlphabeticalByLastNameThenFirstName;
        });
        Tooltip alphaLastFirstTooltip = new Tooltip("Sort by person's last name then first name.\n"
            + "All images for each person are grouped together.");
        alphaLastFirstButton.setTooltip(alphaLastFirstTooltip);
        VBox.setMargin(alphaLastFirstButton, insets);
    }

    private void createAlphaFullRevButton(Insets insets) {
        alphaFullRevButton = createRadioButton("Alphabetical by Full Name Reverse",
            sortGroup, SortOrder.AlphabeticalByFullNameReverse);
        alphaFullRevButton.setOnAction(_ -> {
            sortOrder = SortOrder.AlphabeticalByFullNameReverse;
        });
        Tooltip alphaFullRevTooltip = new Tooltip("Sort by person's full name (first name then last "
            + "name) in reverse order.\nAll images for each person are grouped together.");
        alphaFullRevButton.setTooltip(alphaFullRevTooltip);
        VBox.setMargin(alphaFullRevButton, insets);
    }

    private TextArea createTextArea(Insets insets) {
        final int prefColumnCount = 50;
        final int prefRowCount = 2;
        TextArea textArea = new TextArea();
        textArea.setPromptText("Enter title text here");
        textArea.setPrefColumnCount(prefColumnCount);
        textArea.setPrefRowCount(prefRowCount);
        Tooltip tTooltip = new Tooltip("Enter the title text to appear on the title slide. Two lines is recommended.");
        textArea.setTooltip(tTooltip);
        // TODO: centre text
        VBox.setMargin(textArea, insets);
        return textArea;
    }

    private RadioButton createRadioButton(String text, ToggleGroup group, SortOrder order) {
        RadioButton button = new RadioButton(text);
        button.setToggleGroup(group);
        button.setUserData(order);
        return button;
    }

    /**
     * Retrieve the TitleAndSortData object representing the settings in the TitleAndSortStage
     * object.
     * @return TitleAndSortData object for the settings in this stage.
     */
    public TitleAndSortData getSortData() {
        return new TitleAndSortData(titleArea.getText(), sortOrder,
            lastNameCheckBox.isSelected());
    }
}
