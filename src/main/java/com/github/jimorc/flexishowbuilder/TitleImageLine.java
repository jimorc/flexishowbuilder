package com.github.jimorc.flexishowbuilder;

import org.tinylog.Logger;

/**
 * The TitleImageLine class stores the name of a title image.
 */
public class TitleImageLine extends CSVLine {
    /**
     * Constructor - creates a TitleImageLine object from a title.
     * @param imageName - the name of the image file.
     * @throws IllegalArgumentException if the imageName does not end with .jpg
     */
    public TitleImageLine(String imageName) {
        if (!imageName.toLowerCase().endsWith(".jpg")) {
            Logger.error(BuilderGUI.buildLogMessage(
                "File name: ", imageName, " does not end with .jpg"));
            throw new IllegalArgumentException("imageName must end with .jpg");
        }
        addField(imageName);
    }
}
