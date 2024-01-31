import db.Database;
import db.Setup;
import db.Endpoints;

public class Test {
    public static void main(String[] args) {
        Setup setup = new Setup();

        Endpoints.makeReservation(10,
        "2024-02-10", "2024-02-13", 160.4,
        new String[]{"Suite", "Standard"}, new int[]{2, 3}, new boolean[]{true, true}, new boolean[]{false, false}, new boolean[]{true, false});
    }
}
