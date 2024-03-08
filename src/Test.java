import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.mysql.cj.util.Util;

import db.Database;
import db.Setup;
import db.Utils;
import ui.Login;
import ui.Register;
import ui.Reservation;
import db.Endpoints;

public class Test {
    public static void main(String[] args) {
        Setup setup = new Setup();

        /* Endpoints.makeReservation(10,
                "2024-02-10",
                "2024-02-16",
                "REGULAR",
                2,
                true,
                true, true); */

        // Endpoints.cancelBook(3);

        //Endpoints.createRoom(56.4, "SUITE", "Suite de lujo", 20, 1, 2);

        // Database.countEmptyRooms("REGULAR", 2, "2024-02-12", "2024-02-15");

        /*
         * try {
         * Utils.printResultSet(rs);
         * } catch(Exception e) {
         * System.out.println(e);
         * }
         */

        new Reservation(0);
        //new Login();
    }
}
