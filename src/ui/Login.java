package ui;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import db.Customer;
import db.Endpoints;
import ui.components.Components;

public class Login extends JFrame {
    JLabel errorLabel;

    public Login() {
        String validationError = "";

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create a main layout to place all the elements
        GridLayout mainLayout = new GridLayout(10, 3, Init.H_GAP_SIZE, Init.V_GAP_SIZE);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(mainLayout);
        formPanel.setBounds(10, 10, 500, 500);

        // Add inputs
        JLabel emailInputLabel = new JLabel("Email: ");
        Components.addComponent(formPanel, emailInputLabel);
        JTextField emailInput = new JTextField();
        Components.addComponent(formPanel, emailInput);

        JLabel passwordInputLabel = new JLabel("Password: ");
        Components.addComponent(formPanel, passwordInputLabel);
        JPasswordField passwordInput = new JPasswordField();
        Components.addComponent(formPanel, passwordInput);

        // Add button
        JButton loginButton = Components.createButtonS("Login");
        Components.addComponent(formPanel, loginButton);

        this.errorLabel = Components.createErrorText(validationError);
        Components.addComponent(formPanel, this.errorLabel);

        // Add a goto register page button
        JButton gotoRegisterButton = Components.createButtonS("Have you already got an account?");
        Components.addComponent(formPanel, gotoRegisterButton);

        frame.add(formPanel);

        // Display everything
        frame.pack();
        frame.setSize(520, 450);
        frame.setVisible(true); // By default, it'll be visible

        // As passwordInput.getText() is deprecated, we could upgrade it using:
        // https://stackoverflow.com/questions/10443308/why-gettext-in-jpasswordfield-was-deprecated
        // GetText is not recommended because the string stays in memory until garbage
        // collector removes it
        loginButton.addActionListener(e -> login(frame, emailInput.getText(), passwordInput.getText()));
        gotoRegisterButton.addActionListener(e -> gotoRegister(frame));
    }

    private void gotoRegister(JFrame frame) {
        frame.dispose();
        new Register();
    }

    // Login func
    private void login(JFrame frame, String email, String password) {
        String formError = validateForm(email, password);

        this.errorLabel.setText(formError);

        if (formError.length() != 0) {
            return;
        }

        ResultSet customerData = Endpoints.customerLogin(email, password);

        try {
            // Check if user exists
            if (customerData.next()) {
                frame.dispose();
                ClientUtilities.login(this, customerData);

            } else {
                UIManager.getIcon("OptionPane.warningIcon");
                JOptionPane.showMessageDialog(frame, "Login data is not correct", "Info", 2);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    // Checks if the form contains data
    public static String validateForm(String email, String password) {
        if (email.length() == 0 || password.length() == 0) {
            return Init.ERROR_VALIDATION_EMPTY_FIELD;
        }

        // If the code reaches here, it means everything is correct
        return "";
    }
}
