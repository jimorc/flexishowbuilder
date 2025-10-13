package com.github.jimorc.flexishowbuilder;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javax.imageio.ImageIO;

/**
 * TitleImage generates title slide images.
 */
public final class TitleImage {
    private static final int IMAGEWIDTH = 1400;
    private static final int IMAGEHEIGHT = 1050;
    private static final int FONTSIZE = 48;

    private TitleImage() {}

    /**
     * generateTitleImage generates a title image slide with the specified caption.
     * @param caption is the caption to place in the slide.
     * @param imageFileName is the name to use when writing out the image file.
     * @throws IOException when there is an error writing the file.
     */
    public static void generateTitleImage(String caption, String imageFileName) throws IOException {
        Text cap = new Text(caption);
        cap.setFill(Color.YELLOW);
        cap.setFont(Font.font("System", FontWeight.BLACK, FONTSIZE));
        cap.setTextAlignment(TextAlignment.CENTER);

        StackPane root = new StackPane(cap);
        Scene scene = new Scene(root, IMAGEWIDTH, IMAGEHEIGHT);
        scene.setFill(Color.BLACK);

        WritableImage writableImage = scene.snapshot(null);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
        BufferedImage imageRGB = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
            BufferedImage.OPAQUE);
        Graphics2D graphics = imageRGB.createGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        ImageIO.write(imageRGB, "jpg", new File(imageFileName));
    }
}
