package exceptions;

public class ExpiredDatesException extends Exception {
    @Override
    public String getMessage() {
        return "Both from and to dates must be later than today";
    }
}
