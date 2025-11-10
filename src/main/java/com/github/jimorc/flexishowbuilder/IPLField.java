package com.github.jimorc.flexishowbuilder;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * IPLField is a Text node used to display a field in an ImageAndPersonLine.
 */
public class IPLField  extends StackPane {
    private static final double PADDING = 6.0;
    private Rectangle background;
    private Text text;

    /**
     * Constructor.
     * @param text  the text to display.
     */
    public IPLField(String text) {
        super();
        this.text = new Text(text);
        double width = this.text.getLayoutBounds().getWidth() + PADDING;
        double height = this.text.getLayoutBounds().getHeight() + PADDING;
        background = new Rectangle(width, height);
        background.setFill(Color.TRANSPARENT);
        this.getChildren().addAll(background, this.text);
    }

    /**
     * Returns the width of the IPLField.
     * @return the width of the IPLField.
     */
    public double getFieldWidth() {
        return text.getLayoutBounds().getWidth() + PADDING;
    }

    /**
     * Sets the width of the IPLField.
     * @param width the width to set.
     */
    public void setFieldWidth(double width) {
        background.setWidth(width);
        text.setWrappingWidth(width - PADDING);
    }

    /**
     * Sets the background color of the IPLField.
     * @param color the color to set.
     */
    public void setBackgroundColor(Color color) {
        background.setFill(color);
    }
}
