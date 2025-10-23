package com.github.jimorc.flexishowbuilder;

import javafx.application.Platform;
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
        // make sure program closes when window close button is clicked.
        setOnCloseRequest(_ -> {
            Platform.exit();
            System.exit(0);
        });
    }
}
