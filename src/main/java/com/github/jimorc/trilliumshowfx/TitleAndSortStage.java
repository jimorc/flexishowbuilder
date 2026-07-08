package com.github.jimorc.trilliumshowfx;

import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.swing.filechooser.FileSystemView;
import org.tinylog.Logger;

/**
 * TitleAndSortStage contains inputs for title image text and the image sort
 * order.
 */
public class TitleAndSortStage extends FlexiStage {
    private final int personSortLabelHeight = 60;
    private final int spacing = 10;

    private SortOrder sortOrder = SortOrder.DontSort;
    private SizeTextField widthField;
    private SizeTextField heightField;
    private Button saveSizesButton;
    private CheckBox createStartEndCheckBox;
    private Button saveStartEndSliderButton;
    private TextArea startTitleArea;
    private TextArea endTitleArea;
    private ToggleGroup sortGroup;
    private Button saveSortButton;
    private Label personSortLabel;
    private CheckBox generatePersonSlidesCheckBox;
    private HBox personSlidesBox;
    private RadioButton dontSortButton;
    private RadioButton noneButton;
    private DefaultData defaultData;
    private boolean dontSortSet;
    private boolean genPSlides;
    private boolean sortSlidesByTitleNumber;
    private CheckBox sortSlidesByTitleNumberCheckBox;
    private HBox sortSlidesByTitleNumberBox;

    /**
     * Constructor.
     */
    public TitleAndSortStage() {
        String defFileName = FileSystemView.getFileSystemView().getHomeDirectory()
                + System.getProperty("file.separator")
                + ".config" + System.getProperty("file.separator")
                + "trilliumshowfx" + System.getProperty("file.separator")
                + "defaults.json";
        File defFile = new File(defFileName);
        defaultData = new DefaultData(defFile);

        Logger.debug("In TitleAndSortStage constructor");
        VBox vbox = createBox();
        Scene scene = new Scene(vbox);
        this.setScene(scene);
    }

    /**
     * getData retrieves the values set in the TitleAndSortStage object.
     *
     * @return data set in stage object.
     */
    public TitleAndSortData getData() {
        int slideWidth = Integer.parseInt(widthField.getText());
        int slideHeight = Integer.parseInt(heightField.getText());
        SlideSize slideSize = new SlideSize(slideWidth, slideHeight);
        TitleAndSortData data = new TitleAndSortData(slideSize,
            createStartEndCheckBox.isSelected(),
            startTitleArea.getText(),
            endTitleArea.getText(),
            (SortOrder) sortGroup.getSelectedToggle().getUserData(),
            generatePersonSlidesCheckBox.isSelected(),
            sortSlidesByTitleNumberCheckBox.isSelected());
        return data;
    }

    private VBox createBox() {
        final int fontSize = 14;
        final int tLabelMarginTop = 5;
        final int sizeLabelMarginTop = 5;
        final int sizeLabelMarginBottom = 5;
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
        Insets sizeInsets = new Insets(sizeLabelMarginTop, rightMargin, sizeLabelMarginBottom,
                leftMargin);
        VBox sizeBox = createSizeBox(labelFont, sizeInsets);
        HBox personSortBox = createPersonSortBox(tLabelInsets);

        TitledPane startEndPane = createStartEndSlidesPane(labelFont, tLabelInsets, vBoxInsets);
        TitledPane sortPane = createPersonSlidesSortOrderPane(labelFont, tLabelInsets, vBoxInsets);
        HBox buttonBox = createButtonBox(buttonTopMargin, buttonRightMargin, buttonBottomMargin, buttonLeftMargin);

        VBox vbox = new VBox(spacing);
        vbox.getChildren().addAll(sizeBox, startEndPane, sortPane, personSortBox, buttonBox);
        return vbox;
    }

    private HBox createPersonSortBox(final Insets insets) {
        personSortLabel = new Label("Sort Order for Person Slides");
        personSortLabel.setTextFill(Color.color(0, 0, 1));
        // setPrefHeight is needed to keep the label from changing size when the sort order changes.
        personSortLabel.setPrefHeight(personSortLabelHeight);
        HBox box = new HBox();
        HBox.setMargin(personSortLabel, insets);
        box.getChildren().addAll(personSortLabel);
        return box;
    }

    private TitledPane createStartEndSlidesPane(final Font labelFont,
            final Insets labelInsets, final Insets boxInsets) {
        HBox startEndCheck = createCreateStartEndSlidesBox();
        Label startLabel = createStartEndLabel("Start Image Text", labelFont, labelInsets);
        HBox startTitleBox = createStartTextBox(boxInsets);
        Label endLabel = createStartEndLabel("End Image Text", labelFont, labelInsets);
        HBox endTitleBox = createEndTextBox(boxInsets);
        VBox box = new VBox(startEndCheck, startLabel, startTitleBox, endLabel, endTitleBox);
        VBox.setMargin(startEndCheck, labelInsets);
        TitledPane startEndPane = new TitledPane();
        Label paneLabel = new Label("Start and End Slides");
        paneLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px");
        startEndPane.setGraphic(paneLabel);
        startEndPane.setContent(box);
        startEndPane.setCollapsible(false);
        // must call here to enable/disable widgets correctly when first displayed.
        saveStartEndSliderButtonAction(null);
        return startEndPane;
    }

    private HBox createCreateStartEndSlidesBox() {
        createStartEndCheckBox = new CheckBox("Create Start and End Slides");
        createStartEndCheckBox.setSelected(defaultData.getCreateStartEndSlides());
        createStartEndCheckBox.setOnAction(e -> {
            saveStartEndSliderButtonAction(e);
        });
        Tooltip tooltip = new Tooltip("Check this to create start and end slides");
        createStartEndCheckBox.setTooltip(tooltip);
        saveStartEndSliderButton = createSaveStartEndSlidesButton();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox box = new HBox(createStartEndCheckBox, spacer, saveStartEndSliderButton);
        return box;
    }

    private void saveStartEndSliderButtonAction(Event e) {
        boolean checked = createStartEndCheckBox.isSelected();
        startTitleArea.setDisable(!checked);
        endTitleArea.setDisable(!checked);
        boolean sameAsDefault = createStartEndCheckBox.isSelected() == defaultData.getCreateStartEndSlides();
        saveStartEndSliderButton.setDisable(sameAsDefault);
    }

    private Button createSaveStartEndSlidesButton() {
        Button button = new Button("Save Create Slides as Default");
        button.setDisable(true);
        button.setOnAction(_ -> {
            defaultData.setCreateStartEndSlides(createStartEndCheckBox.isSelected());
            defaultData.saveDefaults();
            button.setDisable(true);
        });
        return button;
    }

    private VBox createSizeBox(final Font labelFont, final Insets insets) {
        Label sizeLabel = new Label("Slide Size");
        sizeLabel.setFont(labelFont);
        sizeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px");
        VBox.setMargin(sizeLabel, insets);
        ChangeListener<String> createS = createSizeFieldChangeListener();
        SlideSize slideSize = defaultData.getSlideSize();
        widthField = new SizeTextField(slideSize.getWidth(), createS);
        heightField = new SizeTextField(slideSize.getHeight(), createS);
        Label x = new Label(" x ");
        Label pixels = new Label(" pixels");
        saveSizesButton = createSaveSizesButton();
        saveSizesButton.setDisable(true);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox sizePane = new HBox(spacing);
        sizePane.getChildren().addAll(widthField, x, heightField, pixels, spacer, saveSizesButton);
        VBox.setMargin(sizePane, insets);
        VBox sizeBox = new VBox();
        sizeBox.getChildren().addAll(sizeLabel, sizePane);
        return sizeBox;
    }

    private Button createSaveSizesButton() {
        Button button = new Button("Save Slide Size as Default");
        button.setOnAction(_ -> {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            defaultData.setSlideSize(new SlideSize(width, height));
            defaultData.saveDefaults();
            button.setDisable(true);
        });
        return button;
    }

    private TitledPane createPersonSlidesSortOrderPane(final Font labelFont,
            final Insets labelInsets, final Insets boxInsets) {
        TitledPane sortPane = new TitledPane();
        Label paneLabel = new Label("Person Slides and Sort Order");
        paneLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px");
        sortPane.setGraphic(paneLabel);
        VBox box = new VBox(spacing);
        BorderPane sortOrderPane = createSortOrderPane(boxInsets);
        box.getChildren().add(sortOrderPane);
        sortPane.setContent(box);
        sortPane.setCollapsible(false);
        return sortPane;
    }

    private BorderPane createSortOrderPane(final Insets insets) {
        VBox pBox = createGenPersonSlidesBox(insets);
        VBox sBox = createSortSlidesByTitleNumberBox(insets);
        pBox.getChildren().add(sBox);
        sortGroup = new ToggleGroup();
        dontSortButton = createSortRadioButton(SortOrder.DontSort, insets);
        noneButton = createSortRadioButton(SortOrder.CurrentPersonOrder, insets);
        RadioButton alphaFullButton = createSortRadioButton(SortOrder.AlphabeticalByFullName, insets);
        RadioButton alphaLastFirstButton = createSortRadioButton(
            SortOrder.AlphabeticalByLastNameThenFirstName, insets);
        RadioButton alphaFullRevButton = createSortRadioButton(
            SortOrder.AlphabeticalByFullNameReverse, insets);
        RadioButton alphaLastFirstRevButton = createSortRadioButton(
            SortOrder.AlphabeticalByLastNameThenFirstNameReverse, insets);
        VBox sortBox = new VBox(spacing);
        sortBox.getChildren().addAll(dontSortButton, noneButton, alphaFullButton,
                alphaLastFirstButton, alphaFullRevButton, alphaLastFirstRevButton);
        VBox.setMargin(sortBox, insets);
        saveSortButton = new Button("Save Sort Order as Default");
        saveSortButton.setDisable(true);
        saveSortButton.setOnAction(_ -> {
            SortOrder order = (SortOrder) sortGroup.getSelectedToggle().getUserData();
            defaultData.setSortOrder(order);
            defaultData.saveDefaults();
            saveSortButton.setDisable(true);
        });
        VBox saveBox = new VBox(saveSortButton);
        saveBox.setAlignment(Pos.CENTER_RIGHT);
        return new BorderPane(null, pBox, saveBox, null, sortBox);
    }

    private VBox createGenPersonSlidesBox(final Insets insets) {
        Button savePersonButton = new Button("Save as Default Choice");
        savePersonButton.setOnAction(_ -> {
            defaultData.setGeneratePersonSlides(generatePersonSlidesCheckBox.isSelected());
            defaultData.saveDefaults();
            savePersonButton.setDisable(true);
        });
        savePersonButton.setDisable(true);
        generatePersonSlidesCheckBox = new CheckBox("Generate Person Slides");
        generatePersonSlidesCheckBox.setOnAction(_ -> {
            savePersonButton.setDisable(
                generatePersonSlidesCheckBox.isSelected() == defaultData.getGeneratePersonSlides());
        });
        Tooltip tooltip = new Tooltip("Check this to generate slides for each person.\n");
        generatePersonSlidesCheckBox.setTooltip(tooltip);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setMargin(generatePersonSlidesCheckBox, insets);
        personSlidesBox = new HBox(generatePersonSlidesCheckBox, spacer, savePersonButton);
        // Following line commented out because the personSlidesBox will be moved elsewhere in
        // future, and another separator is added later.
        // Separator separator = new Separator();
        VBox pBox = new VBox(spacing);
        // See comment above about the separator.
        pBox.getChildren().addAll(personSlidesBox); //, separator);
        generatePersonSlidesCheckBox.setAlignment(Pos.CENTER_LEFT);
        genPSlides = defaultData.getGeneratePersonSlides();
        if (defaultData.getSortOrder() == SortOrder.DontSort) {
            dontSortSet = true;
            generatePersonSlidesCheckBox.setSelected(false);
            personSlidesBox.setDisable(true);
        } else {
            generatePersonSlidesCheckBox.setSelected(defaultData.getGeneratePersonSlides());
        }
        return pBox;
    }

    private VBox createSortSlidesByTitleNumberBox(final Insets insets) {
        Button saveByNumberButton = new Button("Save as Default Choice");
        saveByNumberButton.setOnAction(_ -> {
            defaultData.setSortSlidesByTitleNumber(sortSlidesByTitleNumberCheckBox.isSelected());
            defaultData.saveDefaults();
            saveByNumberButton.setDisable(true);
        });
        saveByNumberButton.setDisable(true);
        sortSlidesByTitleNumberCheckBox = new CheckBox("Sort Each Person's Slides by Title Number");
        sortSlidesByTitleNumberCheckBox.setOnAction(_ -> {
            saveByNumberButton.setDisable(
                sortSlidesByTitleNumberCheckBox.isSelected() == defaultData.getSortSlidesByTitleNumber());
        });
        Tooltip tooltip = new Tooltip("Check this to sort each person's slides by title number.\n");
        sortSlidesByTitleNumberCheckBox.setTooltip(tooltip);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setMargin(sortSlidesByTitleNumberCheckBox, insets);
        sortSlidesByTitleNumberBox = new HBox(sortSlidesByTitleNumberCheckBox, spacer, saveByNumberButton);
        Separator separator = new Separator();
        VBox pBox = new VBox(spacing);
        pBox.getChildren().addAll(sortSlidesByTitleNumberBox, separator);
        sortSlidesByTitleNumberCheckBox.setAlignment(Pos.CENTER_LEFT);
        sortSlidesByTitleNumber = defaultData.getSortSlidesByTitleNumber();
        if (defaultData.getSortOrder() == SortOrder.DontSort) {
            dontSortSet = true;
            sortSlidesByTitleNumberCheckBox.setSelected(false);
            sortSlidesByTitleNumberBox.setDisable(true);
        } else {
            sortSlidesByTitleNumberCheckBox.setSelected(defaultData.getGeneratePersonSlides());
        }
        return pBox;
    }

    private HBox createButtonBox(final int buttonTopMargin, final int buttonRightMargin,
            final int buttonBottomMargin, final int buttonLeftMargin) {
        QuitButton quit = new QuitButton();
        Button gen = createGenButton();
        HBox buttonBox = new HBox(spacing);
        buttonBox.getChildren().addAll(quit, gen);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        Insets buttonInsets = new Insets(buttonTopMargin, buttonRightMargin,
                buttonBottomMargin, buttonLeftMargin);
        HBox.setMargin(quit, buttonInsets);
        HBox.setMargin(gen, buttonInsets);
        return buttonBox;
    }

    private FlexiButton createGenButton() {
        FlexiButton gen = new FlexiButton("Sort Slides / Create Slides");
        Tooltip tooltip = new Tooltip("Sort slides, create start, end slides, and person slides if selected");
        gen.setTooltip(tooltip);
        gen.setOnAction(_ -> {
            this.close();
        });
        return gen;
    }

    private Label createStartEndLabel(final String text, final Font labelFont, final Insets insets) {
        Label startLabel = new Label(text);
        startLabel.setFont(labelFont);
        VBox.setMargin(startLabel, insets);
        return startLabel;
    }

    private RadioButton createSortRadioButton(final SortOrder order, final Insets insets) {
        RadioButton button = new RadioButton(order.getButtonLabel());
        button.setToggleGroup(sortGroup);
        button.setUserData(order);
        Tooltip tooltip = new Tooltip(order.getTooltipText());
        button.setTooltip(tooltip);
        button.setOnAction(_ -> {
            sortOrder = order;
            saveSortButton.setDisable(sortOrder == defaultData.getSortOrder());
            if (sortOrder == SortOrder.DontSort) {
                dontSortSet = true;
                genPSlides = generatePersonSlidesCheckBox.isSelected();
                sortSlidesByTitleNumber = sortSlidesByTitleNumberCheckBox.isSelected();
                generatePersonSlidesCheckBox.setSelected(false);
                sortSlidesByTitleNumberBox.setDisable(true);
                sortSlidesByTitleNumberCheckBox.setSelected(false);
                personSlidesBox.setDisable(true);

                personSortLabel.setText("Person slides will not be sorted");
            } else {
                if (dontSortSet) {
                    generatePersonSlidesCheckBox.setSelected(genPSlides);
                    sortSlidesByTitleNumberCheckBox.setSelected(sortSlidesByTitleNumber);
                    dontSortSet = false;
                }
                personSlidesBox.setDisable(false);
                sortSlidesByTitleNumberBox.setDisable(false);
                personSortLabel.setText(order.getSortOrderLabel());
            }
            personSortLabel.setText(order.getSortOrderLabel());
        });
        button.setSelected(order == defaultData.getSortOrder());
        if (order == defaultData.getSortOrder()) {
            personSortLabel.setText(order.getSortOrderLabel());
        }
        VBox.setMargin(button, insets);
        return button;
    }

    private HBox createStartTextBox(final Insets insets) {
        Button saveButton = new Button("Save as Default");
        saveButton.setOnAction(_ -> {
            String text = startTitleArea.getText();
            defaultData.setStartTitle(text);
            defaultData.saveDefaults();
            saveButton.setDisable(true);
        });
        saveButton.setDisable(true);
        String defaultStartTitle = defaultData.getStartTitle();
        startTitleArea = createTextArea("Start", defaultStartTitle, insets);
        startTitleArea.setOnKeyTyped(_ -> {
            String defaultTitleText = defaultData.getStartTitle();
            String text = startTitleArea.getText();
            saveButton.setDisable(text.equals(defaultTitleText));
        });

        return createTextBox(startTitleArea, saveButton, insets);
    }

    private HBox createEndTextBox(Insets insets) {
        Button saveButton = new Button("Save as Default");
        saveButton.setOnAction(_ -> {
            String text = endTitleArea.getText();
            defaultData.setEndTitle(text);
            defaultData.saveDefaults();
            saveButton.setDisable(true);
        });
        saveButton.setDisable(true);
        String defaultEndTitle = defaultData.getEndTitle();
        endTitleArea = createTextArea("End", defaultEndTitle, insets);
        endTitleArea.setOnKeyTyped(_ -> {
            String defaultTitleText = defaultData.getEndTitle();
            String text = endTitleArea.getText();
            saveButton.setDisable(text.equals(defaultTitleText));
        });

        return createTextBox(endTitleArea, saveButton, insets);
    }

    private TextArea createTextArea(final String startEnd, final String defaultText, final Insets insets) {
        final int prefColumnCount = 50;
        final int prefRowCount = 2;
        TextArea textArea = new TextArea();
        textArea.setText(defaultText);
        textArea.setPromptText("Enter title text here");
        textArea.setPrefColumnCount(prefColumnCount);
        textArea.setPrefRowCount(prefRowCount);
        StringBuffer sb = new StringBuffer("Enter the title text to appear on the ");
        sb.append(startEnd);
        sb.append(" slide. Two or three lines of text is recommended.");
        Tooltip tTooltip = new Tooltip(sb.toString());
        textArea.setTooltip(tTooltip);

        VBox.setMargin(textArea, insets);
        return textArea;
    }

    private HBox createTextBox(final TextArea tArea, final Button saveButton, final Insets insets) {
        HBox box = new HBox(spacing);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.getChildren().addAll(tArea, spacer, saveButton);
        VBox.setMargin(box, insets);
        return box;
    }

    private ChangeListener<String> createSizeFieldChangeListener() {
        ChangeListener<String> listener = (observable, oldValue, newValue) -> {
            String widthText = widthField.getText();
            String heightText = heightField.getText();
            int width = Integer.parseInt(widthText);
            int height = Integer.parseInt(heightText);
            SlideSize slideSize = defaultData.getSlideSize();
            boolean disableButton = false;
            if (width == slideSize.getWidth()
                    && height == slideSize.getHeight()) {
                disableButton = true;
            }
            if (width < SlideSize.MIN_SIZE) {
                widthField.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
                disableButton = true;
            } else {
                widthField.setStyle("-fx-border-color: transparent; -fx-border-width: 3px;");
            }
            if (height < SlideSize.MIN_SIZE) {
                heightField.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
                disableButton = true;
            } else {
                heightField.setStyle("-fx-border-color: transparent; -fx-border-width: 3px;");
            }
            saveSizesButton.setDisable(disableButton);
        };
        return listener;
    }
}
