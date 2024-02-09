package ui;

import javax.swing.*;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import db.Database;
import db.Utils;
import ui.components.Components;
import ui.components.DatePicker;
import ui.components.InputWLabel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

public class Reservation {
    private JFrame frame;
    private JComboBox roomTypeSelector;
    private JTextField peopleNumTextField;
    private JDatePickerImpl fromDatePicker, toDatePicker;
    private JCheckBox breakfastCheckbox, lunchCheckbox, dinnerCheckbox;

    private void searchBooks(ActionEvent e) {
        try {

            String roomType = roomTypeSelector.getSelectedItem().toString();
            int peopleNum = Integer.valueOf(peopleNumTextField.getText());
            Date fromDate = (Date) fromDatePicker.getModel().getValue();
            Date toDate = (Date) toDatePicker.getModel().getValue();
            String fromDateStr = Utils.convertDateToString(fromDate);
            String toDateStr = Utils.convertDateToString(toDate);

            System.out.println(roomType + " " + peopleNum + " " + fromDateStr + " " + toDateStr);

            Database.countEmptyRooms(roomType, peopleNum, fromDateStr, toDateStr);

        } catch (Exception err) {
            UIManager.getIcon("OptionPane.warningIcon");
            JOptionPane.showMessageDialog(frame, "Please fill upp all the fields to search.", "Error", 2);

        }
    }

    public Reservation() {
        // Create the frame
        frame = new JFrame();

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

        this.roomTypeSelector = new JComboBox(roomTypes);
        InputWLabel roomTypeInput = new InputWLabel("Room Type", roomTypeSelector);
        formPanel.add(roomTypeInput.getPanel());

        this.peopleNumTextField = new JTextField();
        peopleNumTextField.setColumns(3);
        InputWLabel peopleNumInput = new InputWLabel("Number of People", peopleNumTextField);
        formPanel.add(peopleNumInput.getPanel());

        this.fromDatePicker = DatePicker.createDP();
        InputWLabel fromDateInput = new InputWLabel("From date", fromDatePicker);
        formPanel.add(fromDateInput.getPanel());

        this.toDatePicker = DatePicker.createDP();
        InputWLabel toDateInput = new InputWLabel("To date", toDatePicker);
        formPanel.add(toDateInput.getPanel());

        this.breakfastCheckbox = new JCheckBox("Breakfast");
        formPanel.add(breakfastCheckbox);

        this.lunchCheckbox = new JCheckBox("Lunch");
        formPanel.add(lunchCheckbox);

        this.dinnerCheckbox = new JCheckBox("Dinner");
        formPanel.add(dinnerCheckbox);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> this.searchBooks(e));
        formPanel.add(searchButton);

        frame.add(formPanel);

        // Display everything
        frame.pack();
        frame.setSize(Init.VP_WIDTH, Init.VP_HEIGHT);
        frame.setVisible(true);

    }
}
