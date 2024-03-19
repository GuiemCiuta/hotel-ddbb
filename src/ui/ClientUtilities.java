package ui;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import db.Booking;
import db.Customer;
import db.Database;
import db.Invoice;

public class ClientUtilities {

    // Checks if the username is valid or invalid
    // ATM, we don't do any validations, but we already implement it, so it'll be
    // easier to add some validations
    public static boolean validateFirstName(String firstName) {
        // If the code reaches here, it means everything is correct
        return true;
    }

    public static boolean validateLastName(String lastName) {
        // If the code reaches here, it means everything is correct
        return true;
    }

    public static boolean validateEmail(String username) {
        // If the code reaches here, it means everything is correct
        return true;
    }

    public static boolean validatePhoneNumber(String username) {
        // If the code reaches here, it means everything is correct
        return true;
    }

    public static boolean validateNationalId(String username) {
        // If the code reaches here, it means everything is correct
        return true;
    }

    public static boolean validateAddress(String username) {
        // If the code reaches here, it means everything is correct
        return true;
    }

    // Checks if the password is valid or invalid
    public static boolean validatePassword(String password) {
        // Check if the password length reaches the minimum
        if (password.length() < Init.MIN_PASSWORD_LENGTH) {
            return false;
        }

        // If the code reaches here, it means everything is correct
        return true;
    }

    public static void printError(Exception err) {
        System.out.println(err);
        System.out.println(err.getStackTrace()[0].getLineNumber());
        err.getStackTrace();
    }

    public static Customer loadCustomer(ResultSet retrivedCustomerData) {
        try {
            return new Customer(retrivedCustomerData.getString("NATIONAL_ID"),
                    retrivedCustomerData.getString("FIRST_NAME"), retrivedCustomerData.getString("LAST_NAMES"),
                    retrivedCustomerData.getString("COUNTRY"), retrivedCustomerData.getInt("ID"),
                    retrivedCustomerData.getString("ADDRESS"), retrivedCustomerData.getString("EMAIL"),
                    retrivedCustomerData.getString("PHONE"));

        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    public static Invoice loadInvoice(Customer customer, String bookingIdStr) {
        String query = "SELECT * FROM INVOICES I JOIN BOOKS B ON I.BOOK_ID = B.ID WHERE B.ID = " + bookingIdStr
                + " AND B.CUSTOMER_ID = " + customer.getUserId();

        ResultSet rs = Database.executeQueryRS(query);

        try {
            if (rs.next()) {
                return loadInvoice(customer, rs);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;

    }

    // We will load customer using
    public static Invoice loadInvoice(Customer customer, ResultSet retrivedInvoiceData) {
        try {
            Booking booking = new Booking(customer, retrivedInvoiceData.getString("START_DATE"),
                    retrivedInvoiceData.getString("END_DATE"), retrivedInvoiceData.getDouble("AMOUNT"),
                    retrivedInvoiceData.getBoolean("CANCELED"));

            return new Invoice(retrivedInvoiceData.getInt("ID"), booking);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    public static void login(JFrame currentFrame, ResultSet customerData) {
        gotoCustomerArea(currentFrame, loadCustomer(customerData));
    }

    public static void gotoCustomerArea(JFrame currentFrame, Customer customer) {
        new CustomerArea(customer);
        currentFrame.dispose();
    }

    public static void gotoReservation(JFrame currentFrame, Customer customer) {
        new Reservation(customer);
        currentFrame.dispose();
    }

    public static void gotoInvoice(JFrame currentFrame, Invoice invoice) {
        new DisplayInvoice(invoice);
        currentFrame.dispose();
    }

    public static void login(JFrame currentFrame, int customerId) {
        try {
            ResultSet customerData = Database.selectAllWhere("CUSTOMERS", "ID = " + customerId);

            login(currentFrame, customerData);

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
