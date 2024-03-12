package ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import db.Customer;
import ui.components.Components;

public class CustomerArea extends JFrame {
    private Customer customer;

    CustomerArea(Customer customer) {
        this.customer = customer;

        this.setLayout(null);

        // frame.setLayout(mainLayout);
        this.setSize(1000, 700);

        // Add title
        JLabel pageH1 = Components.createH1("Hotelsa Booking | Customer Area");
        JLabel pageH2 = Components.createH2("How's your day, " + customer.getFirstName() + "?");
        pageH1.setBounds(10, 10, 400, 50);
        this.getContentPane().add(pageH1);

        JButton bookButton = new JButton("Make a booking");

    }
}
