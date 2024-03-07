package ui;

import java.awt.*;

import javax.swing.*;

import db.Endpoints;
import ui.components.*;

public class Register extends JFrame {
    // This will be modifiable from all parts of the Register class
    private JLabel errorLabel;

    public Register() {
        // A string containing the validation error (that could appear)
        String validationError = "";

        // Create the window
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a main layout to place all the elements
        GridLayout mainLayout = new GridLayout(10, 3, Init.H_GAP_SIZE, Init.V_GAP_SIZE);
        
        JPanel formPanel = new JPanel();
        formPanel.setBounds(10, 10, 1400, 1500);
        formPanel.setLayout(null);

        JPanel leftColPanel = new JPanel();
        leftColPanel.setBounds(10, 10, 500, 500);
        leftColPanel.setLayout(mainLayout);

        JPanel rightColPanel = new JPanel();
        rightColPanel.setBounds(540, 10, 500, 500);
        rightColPanel.setLayout(mainLayout);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(300, 600, 500, 500);
        buttonsPanel.setLayout(mainLayout);


        // Add title
        //JLabel pageH1 = Components.createH1("eSpotifai");
        //Components.addComponent(frame, pageH1);

       /*  JPanel frame = new JPanel();
        frame.setLayout(null);
 */
        // Add inputs and their labels
        JLabel firstNameInputLabel = new JLabel("First name: ");
        Components.addComponent(leftColPanel, firstNameInputLabel);
        JTextField firstNameInput = new JTextField();
        Components.addComponent(leftColPanel, firstNameInput);

        JLabel lastNameInputLabel = new JLabel("Last name: ");
        Components.addComponent(leftColPanel, lastNameInputLabel);
        JTextField lastNameInput = new JTextField();
        Components.addComponent(leftColPanel, lastNameInput);

        JLabel nationalIdInputLabel = new JLabel("ID: ");
        Components.addComponent(leftColPanel, nationalIdInputLabel);
        JTextField nationalIdInput = new JTextField();
        Components.addComponent(leftColPanel, nationalIdInput);

        JLabel addressInputLabel = new JLabel("Adress: ");
        Components.addComponent(leftColPanel, addressInputLabel);
        JTextField addressInput = new JTextField();
        Components.addComponent(leftColPanel, addressInput);

        JLabel countryInputLabel = new JLabel("Country: ");
        Components.addComponent(leftColPanel, countryInputLabel);
        JComboBox countryInput = new JComboBox(new String[]{"ES", "US", "FR", "FI", "DE", "GB", "NO"});
        Components.addComponent(leftColPanel, countryInput);

        JLabel emailInputLabel = new JLabel("Email: ");
        Components.addComponent(rightColPanel, emailInputLabel);
        JTextField emailInput = new JTextField();
        Components.addComponent(rightColPanel, emailInput);

        JLabel phoneNumberInputLabel = new JLabel("Phone number: ");
        Components.addComponent(rightColPanel, phoneNumberInputLabel);
        JTextField phoneNumberInput = new JTextField();
        Components.addComponent(rightColPanel, phoneNumberInput);

        JLabel passwordInputLabel = new JLabel("Password: ");
        Components.addComponent(rightColPanel, passwordInputLabel);
        JPasswordField passwordInput = new JPasswordField();
        Components.addComponent(rightColPanel, passwordInput);

        JLabel confirmPasswordInputLabel = new JLabel("Confirm password: ");
        Components.addComponent(rightColPanel, confirmPasswordInputLabel);
        JPasswordField confirmPasswordInput = new JPasswordField();
        Components.addComponent(rightColPanel, confirmPasswordInput);


        // Add button
        JButton registerButton = Components.createButtonS("Register");
        Components.addComponent(buttonsPanel, registerButton);
        this.errorLabel = Components.createErrorText(validationError);
        Components.addComponent(buttonsPanel, this.errorLabel);

        // Add a goto login page button
        JButton gotoLoginButton = Components.createButtonS("Have you got an account?");
        Components.addComponent(buttonsPanel, gotoLoginButton);

        formPanel.add(leftColPanel);
        formPanel.add(rightColPanel);
        formPanel.add(buttonsPanel);
        frame.add(formPanel);

        // Display everything
        frame.setSize(1200, 850);
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

    // Validates the frame
    public static String validateframe(String firstName, String lastName, String nationalId, String address,
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
        String validationErrors = validateframe(firstName, lastName, nationalId, address, country, email, phoneNumber,
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
