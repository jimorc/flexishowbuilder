package com.github.jimorc.flexishowbuilder;

import javafx.stage.Stage;

/**
 * FlexiStage encapsulates the common functionality for all Stage objects in this program.
 */
public class FlexiStage extends Stage {
    private final String title = "Flexishowbuilder";
    private final int width = 1080;
    private final int height = 800;

    /**
     * Flexistage constructor.
     */
    public FlexiStage() {
        setTitle(title);
        setWidth(width);
        setHeight(height);
    }
}
