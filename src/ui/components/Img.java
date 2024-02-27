package ui.components;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.Utils;

public class Img extends JPanel {
    private String path;

    public Img(String path) {
        this.path = Utils.getExecutionPath() + path;

        try {
            BufferedImage picture = ImageIO.read(new File(path));
            JLabel picLabel = new JLabel(new ImageIcon(picture));
            this.add(picLabel);

        } catch (Exception e) {
            JLabel notFoundLabel = new JLabel("Image not found");
            this.add(notFoundLabel);

        }
    }
}
