package ui.components;

import java.awt.*;
import javax.swing.*;

import ui.Init;


public class Components {
    // Adds a component
    public static void addComponent(JFrame frame, Component component) {
        frame.getContentPane().add(component);
    }

    public static void addComponent(JPanel panel, Component component) {
        panel.add(component);
    }

    // Returns a main title (as h1 in HTML)
    public static JLabel createH1(String content) {
        JLabel title = new JLabel(content);
        title.setFont(new Font(Init.MAIN_FONT, Font.BOLD, Init.TITLE_FONT_SIZE));
        return title;
    }

    // Returns a main title (as h2 in HTML)
    public static JLabel createH2(String content) {
        JLabel title = new JLabel(content);
        title.setFont(new Font(Init.MAIN_FONT, Font.BOLD, Init.SUBTITLE_FONT_SIZE));
        return title;
    }

    // Returns a small button
    public static JButton createButtonS(String content) {
        JButton button = new JButton(content);
        button.setBounds(10, 10, Init.BUTTON_S_HEIGHT, Init.BUTTON_S_WIDTH);
        return button;
    }

    public static JLabel createErrorText(String content) {
        JLabel errorText = new JLabel(content, JLabel.CENTER);
        errorText.setFont(new Font(Init.MAIN_FONT, Font.PLAIN, Init.NORMAL_FONT_SIZE));
        errorText.setForeground(Color.RED);

        return errorText;
    }

}
