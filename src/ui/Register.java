package ui;

import java.awt.*;
import java.net.http.HttpResponse;

import javax.swing.*;

import db.Endpoints;
import db.schemas.CustomersSchema;
import ui.components.*;

public class Register extends JFrame {
    // This will be modifiable from all parts of the Register class
    private JLabel errorLabel;

    public Register() {
        // A string containing the validation error (that could appear)
        String validationError = "";

        // Create the window
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a main layout to place all the elements
        GridLayout mainLayout = new GridLayout(12, 3, Init.H_GAP_SIZE, Init.V_GAP_SIZE);
        frame.setLayout(mainLayout);

        // Add title
        //JLabel pageH1 = Components.createH1("eSpotifai");
        //Components.addComponent(frame, pageH1);

        // Add inputs and their labels
        JLabel firstNameInputLabel = new JLabel("First name: ");
        Components.addComponent(frame, firstNameInputLabel);
        JTextField firstNameInput = new JTextField();
        Components.addComponent(frame, firstNameInput);

        JLabel lastNameInputLabel = new JLabel("Last name: ");
        Components.addComponent(frame, lastNameInputLabel);
        JTextField lastNameInput = new JTextField();
        Components.addComponent(frame, lastNameInput);

        JLabel nationalIdInputLabel = new JLabel("ID: ");
        Components.addComponent(frame, nationalIdInputLabel);
        JTextField nationalIdInput = new JTextField();
        Components.addComponent(frame, nationalIdInput);

        JLabel addressInputLabel = new JLabel("Adress: ");
        Components.addComponent(frame, addressInputLabel);
        JTextField addressInput = new JTextField();
        Components.addComponent(frame, addressInput);

        JLabel countryInputLabel = new JLabel("Country: ");
        Components.addComponent(frame, countryInputLabel);
        JComboBox countryInput = new JComboBox(new String[]{"ES", "US", "FR", "FI", "DE", "GB", "NO"});
        Components.addComponent(frame, countryInput);

        JLabel emailInputLabel = new JLabel("Email: ");
        Components.addComponent(frame, emailInputLabel);
        JTextField emailInput = new JTextField();
        Components.addComponent(frame, emailInput);

        JLabel phoneNumberInputLabel = new JLabel("Phone number: ");
        Components.addComponent(frame, phoneNumberInputLabel);
        JTextField phoneNumberInput = new JTextField();
        Components.addComponent(frame, phoneNumberInput);

        JLabel passwordInputLabel = new JLabel("Password: ");
        Components.addComponent(frame, passwordInputLabel);
        JPasswordField passwordInput = new JPasswordField();
        Components.addComponent(frame, passwordInput);

        JLabel confirmPasswordInputLabel = new JLabel("Confirm password: ");
        Components.addComponent(frame, confirmPasswordInputLabel);
        JPasswordField confirmPasswordInput = new JPasswordField();
        Components.addComponent(frame, confirmPasswordInput);

        // Add button
        JButton registerButton = Components.createButtonS("Register");
        Components.addComponent(frame, registerButton);
        this.errorLabel = Components.createErrorText(validationError);
        Components.addComponent(frame, this.errorLabel);

        // Add a goto login page button
        JButton gotoLoginButton = Components.createButtonS("Have you got an account?");
        Components.addComponent(frame, gotoLoginButton);

        // Display everything
        frame.pack();
        frame.setSize(Init.VP_WIDTH, Init.VP_HEIGHT);
        frame.setVisible(true);

        // As passwordInput.getText() is deprecated, we could upgrade it using:
        // https://stackoverflow.com/questions/10443308/why-gettext-in-jpasswordfield-was-deprecated
        // GetText is not recommended because the string stays in memory until garbage
        // collector removes it
        registerButton.addActionListener(
                e -> register(frame, firstNameInput.getText(), lastNameInput.getText(), nationalIdInput.getText(),
                        addressInput.getText(), countryInput.getSelectedItem().toString(), emailInput.getText(), phoneNumberInput.getText(),
                        passwordInput.getText(), confirmPasswordInput.getText()));

        gotoLoginButton.addActionListener(e -> gotoLogin(frame));

    }

    private void gotoLogin(JFrame frame) {
        frame.dispose();
        new Login();
    }

    // Validates the form
    public static String validateForm(String firstName, String lastName, String nationalId, String address,
            String country,
            String email, String phoneNumber,
            String password, String confirmPassword) {

        // We check if any field is empty. I thought this elegant way to do it: multiply
        // all the lengths. If theres any 0, the whole result will be 0.
        if ((firstName.length() * lastName.length() * nationalId.length() * address.length() * country.length()
                * email.length()
                * phoneNumber.length() * password.length() * confirmPassword.length()) == 0) {
            return Init.ERROR_VALIDATION_EMPTY_FIELD;
        }

        if (!ClientUtilities.validateFirstName(firstName)) {
            // Give some feedback
            return Init.ERROR_VALIDATION_INCORRECT_FIRST_NAME;
        }

        if (!ClientUtilities.validateLastName(lastName)) {
            // Give some feedback
            return Init.ERROR_VALIDATION_INCORRECT_LAST_NAME;
        }

        if (!ClientUtilities.validateNationalId(nationalId)) {
            // Give some feedback
            return Init.ERROR_VALIDATION_INCORRECT_NATIONAL_ID;
        }

        if (!ClientUtilities.validateAddress(address)) {
            // Give some feedback
            return Init.ERROR_VALIDATION_INCORRECT_ADDRESS;
        }

        if (!ClientUtilities.validateEmail(email)) {
            // Give some feedback
            return Init.ERROR_VALIDATION_INCORRECT_EMAIL;
        }

        if (!ClientUtilities.validatePhoneNumber(phoneNumber)) {
            // Give some feedback
            return Init.ERROR_VALIDATION_INCORRECT_PHONE_NUMBER;
        }

        // We must validate if password is strong enought before comparing if both
        // passwords are correct.
        // Imagine the following situation: someone introduces a weak password and it's
        // not equal the confirm password.
        // If we force him to make passwords be the same, later it'll get another error.
        // That's annoying.
        if (!ClientUtilities.validatePassword(password)) {
            // Give some feedback
            return Init.ERROR_VALIDATION_INCORRECT_PASSWORD;
        }

        // If passwords don't match, exit the func without sending nothing
        if (!password.equals(confirmPassword)) {
            // Give some feedback
            return Init.ERROR_VALIDATION_PASSWORDS_DONT_MATCH;
        }

        // If the code reaches here, it means everything is correct
        return "";
    }

    // Register func
    // Sends the username and password inputs to the server, via POST
    private void register(JFrame frame, String firstName, String lastName, String nationalId, String address, String country,
            String email,
            String phoneNumber, String password, String confirmPassword) {
        // Check if there are some errors
        // If do, stop the func and notice the user
        String validationErrors = validateForm(firstName, lastName, nationalId, address, country, email, phoneNumber,
                password,
                confirmPassword);
        // Update the feedback we're giving to the user
        this.errorLabel.setText(validationErrors);

        // If there's an error, return
        if (validationErrors.length() != 0) {
            return;
        }

        int accountId = Endpoints.customerRegister(firstName, lastName, nationalId, address, email, phoneNumber,
                country, password);

        // If registration is OK, we'll also login user.
        // I hate websites that first they force to register you and then you have to
        // login.
        if (accountId >= 0) {
            frame.dispose();
            ClientUtilities.login(accountId);
        }
    }
}
