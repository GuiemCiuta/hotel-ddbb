package ui.components;

import javax.swing.*;

import org.jdatepicker.JDatePicker;

import ui.components.Components;

import java.awt.*;

// Creates the layout for an input and its label
public class InputWLabel extends JPanel {

    public InputWLabel(String labelStr, JComponent input) {

        this.setLayout(new FlowLayout());
        JLabel label = new JLabel(labelStr);
        //label.setBounds(0, 0, 200, 30);
        this.add(label);
        //input.setBounds(0, 30, 200, 50);
        this.add(input);
    }

}
