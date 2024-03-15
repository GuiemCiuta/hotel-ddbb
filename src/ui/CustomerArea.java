package ui;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import db.Customer;
import db.Database;
import db.Endpoints;
import ui.components.Components;

public class CustomerArea extends JFrame {
    private Customer customer;

    public CustomerArea(Customer customer) {
        // Adoc
        this.customer = new Customer("41747470D", "Guiem", "Bagur Moll", "ES", 0, "Camí Capelletes s/n",
                "guillembagurmoll@gmail.com", "682572005");

        this.setLayout(null);
        this.setSize(1000, 700);

        // Add title
        JLabel pageH1 = Components.createH1("Hotelsa Booking | Customer Area");
        pageH1.setBounds(10, 10, 1000, 50);
        this.add(pageH1);

        /*
         * JLabel pageH2 = Components.createH2("How's your day, " +
         * customer.getFirstName() + "?");
         * pageH2.setBounds(10, 55, 1000, 50);
         * this.add(pageH2);
         */

        JButton bookButton = new JButton("Make a booking");
        bookButton.addActionListener(e -> ClientUtilities.gotoReservation(this, this.customer));
        bookButton.setBounds(10, 110, 150, 40);
        this.add(bookButton);

        JLabel pageH2PreviousBookings = Components.createH2("These are your last bookings in our hotel");
        pageH2PreviousBookings.setBounds(10, 170, 1100, 25);
        this.add(pageH2PreviousBookings);

        ResultSet customerBookings = Endpoints.retrieveCustomerBooks(this.customer);

        try {
            for (int i = 0; customerBookings.next(); i++) {
                JLabel bookingLabel = new JLabel("Booking from " + customerBookings.getString("START_DATE") + " to "
                        + customerBookings.getString("END_DATE") + " (" + customerBookings.getString("AMOUNT") + ")");
                bookingLabel.setBounds(10, 200 + (25 * i), 500, 25);
                this.add(bookingLabel);

                String bookingId = customerBookings.getString("ID");
                boolean bookingCanceled = customerBookings.getBoolean("CANCELED");

                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate bookingStartDate = LocalDate.parse(customerBookings.getString(("START_DATE")), df);

                if (bookingStartDate.compareTo(LocalDate.now()) > -1 && !bookingCanceled) {
                    JButton cancelBookButton = new JButton("Cancel book");
                    cancelBookButton.addActionListener(e -> cancelBook(bookingId));
                    cancelBookButton.setBounds(510, 200 + (25 * i), 200, 25);
                    this.add(cancelBookButton);
                }

            }

        } catch (SQLException e) {
            JLabel errorLabel = new JLabel("An error has occurred... We cannot display your booking at this moment");
            errorLabel.setBounds(10, 200, 1000, 25);
            this.add(errorLabel);
        }

        // Display everything
        this.setSize(1200, 850);
        this.setVisible(true);
    }

    private void cancelBook(String bookIdStr) {
        int bookId = Integer.parseInt(bookIdStr);

        if (Endpoints.cancelBook(bookId)) {
            JOptionPane.showMessageDialog(this, "Booking canceled, but we won't refund the amount...", "Info", 2);

        } else {
            JOptionPane.showMessageDialog(this, "Something went wrong", "Warning", 2);
        }

        this.removeAll();
        this.invalidate();
        this.validate();
        this.repaint();
    }
}
