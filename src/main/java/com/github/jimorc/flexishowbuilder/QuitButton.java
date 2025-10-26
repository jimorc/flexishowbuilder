package com.github.jimorc.flexishowbuilder;

import javafx.application.Platform;
import org.tinylog.Logger;

/**
 * QuitButton is a specialized FlexiButton for quit handling.
 */
public class QuitButton extends FlexiButton {

    /**
     * Constructor.
     */
    public QuitButton() {
        super("Quit");
        setOnAction(_ -> {
            Logger.debug("Quit button clicked");
            Platform.exit();
            System.exit(0);
        });
    }

}
