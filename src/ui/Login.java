package ui;

import java.awt.*;
import javax.swing.*;


import db.Endpoints;
import ui.components.Components;

public class Login extends JFrame {
    JLabel errorLabel;

    public Login() {
        String validationError = "";

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a main layout to place all the elements
        GridLayout mainLayout = new GridLayout(10, 3, Init.H_GAP_SIZE, Init.V_GAP_SIZE);
        frame.setLayout(mainLayout);

        // Add inputs
        JLabel emailInputLabel = new JLabel("Email: ");
        Components.addComponent(frame, emailInputLabel);
        JTextField emailInput = new JTextField();
        Components.addComponent(frame, emailInput);

        JLabel passwordInputLabel = new JLabel("Password: ");
        Components.addComponent(frame, passwordInputLabel);
        JPasswordField passwordInput = new JPasswordField();
        Components.addComponent(frame, passwordInput);

        // Add button
        JButton loginButton = Components.createButtonS("Login");
        Components.addComponent(frame, loginButton);

        this.errorLabel = Components.createErrorText(validationError);
        Components.addComponent(frame, this.errorLabel);

        // Add a goto register page button
        JButton gotoRegisterButton = Components.createButtonS("Haven't account yet?");
        Components.addComponent(frame, gotoRegisterButton);

        // Display everything
        frame.pack();
        frame.setSize(Init.VP_WIDTH, Init.VP_HEIGHT);
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

        int userId = Endpoints.customerLogin(email, password);
        if (userId >= 0) {
            frame.dispose();
            ClientUtilities.login(userId);
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
