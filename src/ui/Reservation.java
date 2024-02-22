package ui;

import javax.swing.*;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.mysql.cj.protocol.a.PacketSplitter;

import db.Database;
import db.Utils;
import exceptions.WrongDatesException;
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

            // Check that the "to date" is later than "from date"
            // Idk why compareTo method gives the second date later even if they're equal.
            // I've found this solution...
            if (fromDate.compareTo(toDate) > 0 || fromDate.toString().equals(toDate.toString())) {
                throw new WrongDatesException();
            }

            String fromDateStr = Utils.convertDateToString(fromDate);
            String toDateStr = Utils.convertDateToString(toDate);

            System.out.println(roomType + " " + peopleNum + " " + fromDateStr + " " + toDateStr);

            int availableRooms = Database.countEmptyRooms(roomType, peopleNum, fromDateStr, toDateStr);

            System.out.println(availableRooms);

        } catch (WrongDatesException err) {

            UIManager.getIcon("OptionPane.warningIcon");
            JOptionPane.showMessageDialog(frame, err.getMessage(), "Error", 2);

        } catch (NullPointerException | NumberFormatException err) {

            UIManager.getIcon("OptionPane.warningIcon");
            JOptionPane.showMessageDialog(frame, "Please fill up all the fields to search.", "Error", 2);

        } catch (Exception err) {

            UIManager.getIcon("OptionPane.warningIcon");
            JOptionPane.showMessageDialog(frame, "Unexpected error occurred", "Error", 2);

        }
    }

    public Reservation() {
        // Create the frame
        frame = new JFrame();

        // Create a main layout to place all the elements
        GridLayout mainLayout = new GridLayout(10, 3, 10, 10);
        // frame.setLayout(mainLayout);
        frame.setSize(1000, 700);
        frame.setBackground(new Color(0, 255, 0));

        // Add title
        JLabel pageH1 = Components.createH1("Hotelsa Booking");
        pageH1.setBounds(10, 10, 400, 50);
        frame.add(pageH1);

        // Form
        JPanel formPanel = new JPanel();
        GridLayout formLayout = new GridLayout();
        BoxLayout boxLayout = new BoxLayout(formPanel, BoxLayout.Y_AXIS);
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setSize(1000, 700);
        formPanel.setLayout(null);

        // Room type input
        String[] roomTypes = { "Regular", "Suite" };

        this.roomTypeSelector = new JComboBox(roomTypes);
        InputWLabel roomTypeInput = new InputWLabel("Room Type", roomTypeSelector);
        roomTypeInput.setBounds(50, 70, 95, 60);
        formPanel.add(roomTypeInput);

        this.peopleNumTextField = new JTextField();
        peopleNumTextField.setColumns(3);
        InputWLabel peopleNumInput = new InputWLabel("Number of People", peopleNumTextField);
        peopleNumInput.setBounds(150, 70, 150, 60);
        formPanel.add(peopleNumInput);

        this.fromDatePicker = DatePicker.createDP();
        InputWLabel fromDateInput = new InputWLabel("From date", fromDatePicker);
        fromDateInput.setBounds(50, 150, 250, 80);
        formPanel.add(fromDateInput);

        this.toDatePicker = DatePicker.createDP();
        InputWLabel toDateInput = new InputWLabel("To date", toDatePicker);
        toDateInput.setBounds(300, 150, 250, 80);
        formPanel.add(toDateInput);

        this.breakfastCheckbox = new JCheckBox("Breakfast");
        breakfastCheckbox.setBounds(50, 250, 100, 60);
        formPanel.add(breakfastCheckbox);

        this.lunchCheckbox = new JCheckBox("Lunch");
        lunchCheckbox.setBounds(200, 250, 80, 60);
        formPanel.add(lunchCheckbox);

        this.dinnerCheckbox = new JCheckBox("Dinner");
        dinnerCheckbox.setBounds(350, 250, 80, 60);
        formPanel.add(dinnerCheckbox);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> this.searchBooks(e));
        searchButton.setBounds(50, 350, 100, 50);
        formPanel.add(searchButton);

        frame.add(formPanel);

        // Display everything
        frame.pack();
        frame.setSize(Init.VP_WIDTH, Init.VP_HEIGHT);
        frame.setVisible(true);

    }
}
