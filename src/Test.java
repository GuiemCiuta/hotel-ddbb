import java.sql.ResultSet;

import com.mysql.cj.util.Util;

import db.Database;
import db.Setup;
import db.Utils;
import db.Endpoints;

public class Test {
    public static void main(String[] args) {
        Setup setup = new Setup();

        /* Endpoints.makeReservation(10,
        "2024-02-10", "2024-02-16", 160.4,
        new String[]{"Suite", "Standard"}, new int[]{2, 3}, new boolean[]{true, true}, new boolean[]{false, false}, new boolean[]{true, false}); */

        //Endpoints.cancelBook(3);

        //Endpoints.createRoom(56.4, "REGULAR", "Habitación Estándar", 2, 10, 2);

        ResultSet rs = Database.countEmptyRooms("Standard", 2, "2024-01-06", "2024-02-18");

        try {
            Utils.printResultSet(rs);
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
