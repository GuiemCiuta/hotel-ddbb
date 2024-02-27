package exceptions;

public class NoAvailableRoomsException extends Exception {
    @Override
    public String toString() {
        return "There are no rooms available.";
    }
}
