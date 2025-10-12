package com.github.jimorc.flexishowbuilder;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class TitleImage {
    private TitleImage() {}

    public static void generateTitleImage(String caption, String imageFileName) throws IOException{
        Text cap = new Text(caption);
        cap.setFill(Color.YELLOW);
        cap.setFont(Font.font("System", FontWeight.BLACK, 48));
        cap.setTextAlignment(TextAlignment.CENTER);

        StackPane root = new StackPane(cap);
        Scene scene = new Scene(root, 1400, 1050);
        scene.setFill(Color.BLACK);

        WritableImage writableImage = scene.snapshot(null);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
        BufferedImage imageRGB = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.OPAQUE);
        Graphics2D graphics = imageRGB.createGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        ImageIO.write(imageRGB, "jpg", new File(imageFileName));
    }

}