package ui;

public class ClientUtilities {

    // Checks if the username is valid or invalid
    // ATM, we don't do any validations, but we already implement it, so it'll be easier to add some validations
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

    public static void login(int accountId) {
        new Reservation(accountId);
    }
}
