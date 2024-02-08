package ui.components;

import javax.swing.*;

import org.jdatepicker.JDatePicker;

import ui.components.Components;

import java.awt.*;

// Creates the layout for an input and its label
public class InputWLabel {
    private JPanel panel;

    public InputWLabel(String labelStr, JComponent input) {
        panel = new JPanel();

        JLabel label = new JLabel(labelStr);
        panel.add(label);
        panel.add(input);
    }



    /**
     * @return JPanel return the panel
     */
    public JPanel getPanel() {
        return panel;
    }

}
