package ui;

import javax.swing.*;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.mysql.cj.protocol.a.PacketSplitter;

import db.Database;
import db.Endpoints;
import db.Utils;
import exceptions.ExpiredDatesException;
import exceptions.NoAvailableRoomsException;
import exceptions.WrongDatesException;
import ui.components.Components;
import ui.components.DatePicker;
import ui.components.Img;
import ui.components.InputWLabel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private JFrame frame;
    private JComboBox roomTypeSelector;
    private JTextField peopleNumTextField;
    private JDatePickerImpl fromDatePicker, toDatePicker;
    private JCheckBox breakfastCheckbox, lunchCheckbox, dinnerCheckbox;

    // The id of the logged customer
    int accountId;

    private final static String[] ROOM_TYPES = { "Regular", "Suite" };

    // https://stackoverflow.com/questions/1385737/scrollable-jpanel
    public Reservation(int accountId) {
        this.accountId = accountId;

        // Create the frame
        frame = new JFrame();

        // Create a main layout to place all the elements
        GridLayout mainLayout = new GridLayout(10, 3, 10, 10);
        // frame.setLayout(mainLayout);
        frame.setSize(1000, 700);

        // Add title
        JLabel pageH1 = Components.createH1("Hotelsa Booking");
        pageH1.setBounds(10, 10, 400, 50);
        frame.getContentPane().add(pageH1);

        // Form
        JPanel formPanel = new JPanel();
        GridLayout formLayout = new GridLayout();
        BoxLayout boxLayout = new BoxLayout(formPanel, BoxLayout.Y_AXIS);
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setSize(1000, 700);
        formPanel.setLayout(null);

        JPanel roomsWrapper = new JPanel();
        roomsWrapper.setBounds(50, 60, 400, 90);
        this.addRoom(roomsWrapper);
        formPanel.add(roomsWrapper);

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

        Img roomPic = new Img(
                "hotel.jpg");
        roomPic.setBounds(550, 100, 600, 400);
        frame.getContentPane().add(roomPic);
        roomPic.repaint();

        frame.getContentPane().add(formPanel);

        // Display everything
        frame.pack();
        frame.setSize(Init.VP_WIDTH, Init.VP_HEIGHT);
        frame.setVisible(true);

    }

    // To sell more, if there are so few rooms left, we'll motivate user to book
    private void createFomo(int roomsNum) {
        UIManager.getIcon("OptionPane.warningIcon");
        JOptionPane.showMessageDialog(frame, "Hurry up! There are only " + roomsNum + " rooms left.", "Info", 2);
    }

    private void searchBooks(ActionEvent e) {
        try {

            String roomType = roomTypeSelector.getSelectedItem().toString();
            int peopleNum = Integer.valueOf(peopleNumTextField.getText());
            Date fromDate = (Date) fromDatePicker.getModel().getValue();
            Date toDate = (Date) toDatePicker.getModel().getValue();
            Date now = new Date();

            // Check that the "to date" is later than "from date"
            // Idk why compareTo method gives the second date later even if they're equal.
            // I've found this solution...
            if (fromDate.compareTo(toDate) > 0 || fromDate.toString().equals(toDate.toString())) {
                throw new WrongDatesException();
            }

            // Check that dates are still to happen
            if ((now.compareTo(fromDate) > 0 || now.compareTo(toDate) > 0)) {
                throw new ExpiredDatesException();
            }

            System.out.println(roomType + " " + peopleNum + " " + fromDate + " " + toDate);

            int availableRooms = Database.countEmptyRooms(roomType, peopleNum, fromDate, toDate);

            if (availableRooms < 1) {
                throw new NoAvailableRoomsException();
            }

            // If so few rooms left, we'll remove buying friction using an old but gold
            // technique
            if (availableRooms <= 3) {
                this.createFomo(availableRooms);
            }

            // Show total booking amount
            double totalAmount = Endpoints.calculatePrice(fromDate, toDate, roomType,
            peopleNum,
            breakfastCheckbox.isSelected(), lunchCheckbox.isSelected(),
            dinnerCheckbox.isSelected());

            JPanel totalAmountLabelContainer = new JPanel();
            totalAmountLabelContainer.setLayout(null);
            totalAmountLabelContainer.setBounds(50, 410, 200, 50);
            totalAmountLabelContainer.revalidate();

            JLabel totalAmountLabel = new JLabel("Total amount: " + totalAmount);
            totalAmountLabel.setBounds(10, 10, 180, 40);
            totalAmountLabelContainer.add(totalAmountLabel);

            frame.getContentPane().add(totalAmountLabelContainer);
            totalAmountLabelContainer.repaint();

            // If rooms available, let's add a button to book a room
            JButton bookButton = new JButton("Book room!");
            bookButton.addActionListener(evt -> execReservation(fromDate, toDate, roomType, peopleNum));

            bookButton.setBounds(50, 480, 200, 50);
            frame.getContentPane().add(bookButton);
            bookButton.repaint();


        } catch (NoAvailableRoomsException err) {

            UIManager.getIcon("OptionPane.warningIcon");
            JOptionPane.showMessageDialog(frame, err, "Info", 2);

        } catch (WrongDatesException|ExpiredDatesException err) {

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

    private void execReservation(java.util.Date fromDate, java.util.Date toDate, String roomType,
            int peopleNum) {
        Endpoints.makeReservation(accountId, fromDate, toDate, roomType,
                peopleNum, breakfastCheckbox.isSelected(), lunchCheckbox.isSelected(),
                breakfastCheckbox.isSelected());

        JOptionPane.showMessageDialog(frame, "Booking done. Thanks!");

        // We'll close the window. That's not user friendly, but it's a really great
        // short term solution
        frame.setVisible(false); // you can't see me!
        frame.dispose(); // Destroy the JFrame object|

    }

    private void addRoom(JPanel panel) {
        this.roomTypeSelector = new JComboBox(Reservation.ROOM_TYPES);
        InputWLabel roomTypeInput = new InputWLabel("Room Type", roomTypeSelector);
        roomTypeInput.setBounds(0, 0, 100, 40);
        panel.add(roomTypeInput);

        this.peopleNumTextField = new JTextField();
        peopleNumTextField.setColumns(3);
        InputWLabel peopleNumInput = new InputWLabel("Number of People", peopleNumTextField);
        peopleNumInput.setBounds(10, 10, 50, 100);
        panel.add(peopleNumInput);
    }

    private void makeReservation(String startDate, String endDate, int[] peopleNums) {
        // Endpoints.makeReservation(0, startDate, endDate, 0, ROOM_TYPES, peopleNums,
        // null, null, null);
    }
}
