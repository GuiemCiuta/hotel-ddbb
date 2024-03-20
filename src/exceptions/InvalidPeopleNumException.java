package exceptions;

public class InvalidPeopleNumException extends Exception {
    @Override
    public String getMessage() {
        return "Please put a valid people num";
    }
}
