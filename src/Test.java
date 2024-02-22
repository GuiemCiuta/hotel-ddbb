import java.sql.ResultSet;

import com.mysql.cj.util.Util;

import db.Database;
import db.Setup;
import db.Utils;
import ui.Reservation;
import db.Endpoints;

public class Test {
    public static void main(String[] args) {
        Setup setup = new Setup();

        /* Endpoints.makeReservation(10,
                "2024-02-10", "2024-02-16", 160.4,
                new String[] { "REGULAR", "REGULAR", "REGULAR", "REGULAR", "REGULAR" },
                new int[] { 2, 2, 1, 2, 1 },
                new boolean[] { true, true, true, true, true },
                new boolean[] { true, true, true, true, true }, new boolean[] { true, true, true, true, true }); */

        // Endpoints.cancelBook(3);

        // Endpoints.createRoom(56.4, "REGULAR", "Habitación Estándar", 2, 10, 2);

        //Database.countEmptyRooms("REGULAR", 2, "2024-02-12", "2024-02-15");

        /*
         * try {
         * Utils.printResultSet(rs);
         * } catch(Exception e) {
         * System.out.println(e);
         * }
         */

        new Reservation();
    }
}
