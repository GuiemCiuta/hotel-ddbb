package ui;

import javax.swing.*;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import ui.components.Components;
import ui.components.DatePicker;
import ui.components.InputWLabel;

import java.awt.*;
import java.sql.Date;
import java.util.Properties;

public class Reservation {
    public Reservation() {
        // Create the frame
        JFrame frame = new JFrame();

        // Create a main layout to place all the elements
        GridLayout mainLayout = new GridLayout(10, 3, 10, 10);
        frame.setLayout(mainLayout);

        // Add title
        JLabel pageH1 = Components.createH1("Hotelsa Booking");
        Components.addComponent(frame, pageH1);

        // Form
        JPanel formPanel = new JPanel();
        GridLayout formLayout = new GridLayout();
        formPanel.setLayout(formLayout);

        // Room type input
        String[] roomTypes = { "Regular", "Suite" };

        JComboBox roomTypeSelector = new JComboBox(roomTypes);
        InputWLabel roomTypeInput = new InputWLabel("Room Type", roomTypeSelector);
        formPanel.add(roomTypeInput.getPanel());

        JTextField peopleNumTextField = new JTextField();
        peopleNumTextField.setColumns(3);
        InputWLabel peopleNumInput = new InputWLabel("Number of People", peopleNumTextField);
        formPanel.add(peopleNumInput.getPanel());

        JDatePickerImpl fromDatePicker = DatePicker.createDP();
        InputWLabel formDateInput = new InputWLabel("From date", fromDatePicker);
        formPanel.add(formDateInput.getPanel());

        JCheckBox breakfastCheckbox = new JCheckBox("Breakfast");
        formPanel.add(breakfastCheckbox);

        JCheckBox lunchCheckbox = new JCheckBox("Lunch");
        formPanel.add(lunchCheckbox);

        JCheckBox dinnerCheckbox = new JCheckBox("Dinner");
        formPanel.add(dinnerCheckbox);



        frame.add(formPanel);

        // Display everything
        frame.pack();
        frame.setSize(Init.VP_WIDTH, Init.VP_HEIGHT);
        frame.setVisible(true);

    }
}
