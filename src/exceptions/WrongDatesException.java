package exceptions;

public class WrongDatesException extends Exception {
    @Override
    public String getMessage() {
        return "Second date must be later than first one";
    }
}
