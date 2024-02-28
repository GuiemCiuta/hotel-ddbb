package db;

import java.sql.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

import javax.xml.crypto.Data;

import db.schemas.RoomsSchema;
import db.schemas.BooksSchema;
import db.schemas.InvoicesSchema;
import db.schemas.RoomsPerBookSchema;

public class Endpoints {
    public static boolean customerLogin(String email, String password) {
        try {

            // Encrypt password
            password = Database.sha256(password);

            ResultSet loggedUser = Database.selectAllWhere("CUSTOMERS",
                    "EMAIL = " + email + " AND PASSWORD = " + password);

            return loggedUser.next();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static boolean receptionistLogin(String id, String password) {
        try {

            // Encrypt password
            password = Database.sha256(password);

            ResultSet loggedUser = Database.selectAllWhere("INTERNAL_USERS",
                    "NATIONAL_ID = " + id + " AND PASSWORD = " + password);

            return loggedUser.next();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /*
     * public static boolean makeReservation(int customerId, String startDate,
     * String endDate, double amount,
     * String[] roomTypes,
     * int[] peopleNums,
     * boolean[] breakfastIncluded,
     * boolean[] lunchIncluded,
     * boolean[] dinnerIncluded)
     * {
     * try {
     * 
     * String customerIdStr = Integer.toString(customerId);
     * String amountStr = Double.toString(amount);
     * 
     * // Insert data into bookings register
     * Database.insertWithId("BOOKS",
     * new String[] {
     * BooksSchema.CUSTOMER_ID.toString(),
     * BooksSchema.START_DATE.toString(),
     * BooksSchema.END_DATE.toString(),
     * BooksSchema.AMOUNT.toString(),
     * BooksSchema.CANCELED.toString(),
     * },
     * new String[] {
     * customerIdStr,
     * startDate,
     * endDate,
     * amountStr,
     * "false"
     * });
     * 
     * // Id of the current booking
     * int bookingId = Database.getLastIdInTable("BOOKS");
     * 
     * // It means that has been an error
     * if (bookingId < 0) {
     * throw new Exception();
     * }
     * 
     * String bookingIdStr = Integer.toString(bookingId);
     * 
     * for (int i = 0; i < roomTypes.length; i++) {
     * String roomType = roomTypes[i];
     * int peopleNum = peopleNums[i];
     * String peopleNumStr = Integer.toString(peopleNum);
     * 
     * // Saves all the booking's details into the "ROOMS_PER_BOOK" table
     * // It's related with the "BOOKS" table via id.
     * Database.insertWithId("ROOMS_PER_BOOK",
     * new String[] {
     * RoomsPerBookSchema.BOOK_ID.toString(),
     * RoomsPerBookSchema.ROOM_TYPE.toString(),
     * RoomsPerBookSchema.PEOPLE_NUM.toString(),
     * RoomsPerBookSchema.BREAKFAST.toString(),
     * RoomsPerBookSchema.LUNCH.toString(),
     * RoomsPerBookSchema.DINNER.toString(),
     * },
     * new String[] {
     * bookingIdStr,
     * roomType,
     * peopleNumStr,
     * Boolean.toString(breakfastIncluded[i]),
     * Boolean.toString(lunchIncluded[i]),
     * Boolean.toString(dinnerIncluded[i]),
     * });
     * }
     * 
     * return true;
     * 
     * } catch (Exception e) {
     * System.out.println(e);
     * return false;
     * }
     * }
     */

    public static boolean makeReservation(int customerId, String startDateString, String endDateString,
            String roomType, int peopleNum, boolean breakfastIncluded, boolean lunchIncluded,
            boolean dinnerIncluded) {
        try {

            double amount = Endpoints.calculatePrice(startDateString, endDateString, roomType, peopleNum,
                    breakfastIncluded, lunchIncluded, dinnerIncluded);

            String customerIdStr = Integer.toString(customerId);
            String amountStr = Double.toString(amount);

            // Insert data into bookings register
            Database.insertWithId("BOOKS",
                    new String[] {
                            BooksSchema.CUSTOMER_ID.toString(),
                            BooksSchema.START_DATE.toString(),
                            BooksSchema.END_DATE.toString(),
                            BooksSchema.AMOUNT.toString(),
                            BooksSchema.CANCELED.toString(),
                    },
                    new String[] {
                            customerIdStr,
                            startDateString,
                            endDateString,
                            amountStr,
                            "false"
                    });

            // Id of the current booking
            int bookingId = Database.getLastIdInTable("BOOKS");

            // It means that has been an error
            if (bookingId < 0) {
                throw new Exception();
            }

            String bookingIdStr = Integer.toString(bookingId);

            String peopleNumStr = Integer.toString(peopleNum);

            // Saves all the booking's details into the "ROOMS_PER_BOOK" table
            // It's related with the "BOOKS" table via id.
            Database.insertWithId("ROOMS_PER_BOOK",
                    new String[] {
                            RoomsPerBookSchema.BOOK_ID.toString(),
                            RoomsPerBookSchema.ROOM_TYPE.toString(),
                            RoomsPerBookSchema.PEOPLE_NUM.toString(),
                            RoomsPerBookSchema.BREAKFAST.toString(),
                            RoomsPerBookSchema.LUNCH.toString(),
                            RoomsPerBookSchema.DINNER.toString(),
                    },
                    new String[] {
                            bookingIdStr,
                            roomType,
                            peopleNumStr,
                            Boolean.toString(breakfastIncluded),
                            Boolean.toString(lunchIncluded),
                            Boolean.toString(dinnerIncluded),
                    });

            // Store onvoice of the booking
            Endpoints.createInvoice(bookingId);

            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    // Methods overload, so this method also will accept two Dates as params
    public static boolean makeReservation(int customerId, java.util.Date fromDate, java.util.Date toDate,
            String roomType, int peopleNum, boolean breakfastIncluded, boolean lunchIncluded,
            boolean dinnerIncluded) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fromDateStr = dateFormat.format(fromDate);
        String toDateStr = dateFormat.format(toDate);

        return Endpoints.makeReservation(customerId, fromDateStr, toDateStr, roomType, peopleNum, breakfastIncluded,
                lunchIncluded, dinnerIncluded);
    }

    public static boolean cancelBook(int bookId) {
        try {

            Database.update("UPDATE BOOKS SET CANCELED = TRUE WHERE ID = " + bookId);

            // FOR ROOMS
            Database.update("UPDATE ROOMS_PER_BOOK SET CANCELED = TRUE WHERE BOOK_ID = " + bookId);

            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static double calculatePrice(String startDateString, String endDateString,
            String roomType, int peopleNum, boolean breakfastIncluded, boolean lunchIncluded,
            boolean dinnerIncluded) {

        // Parse dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

        LocalDate startDate = LocalDate.parse(startDateString, formatter);
        LocalDate endDate = LocalDate.parse(endDateString, formatter);

        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);
        // Booking a room for two people should be more expensive
        // The multiplier for booking for one person will be 1, for 2, it'll be 1.5, for
        // 3, it'll be 2 and so on
        double peopleNumMultiplier = 0.5 + peopleNum / 2;

        // Calculate each meal price, depending if it's selected or not
        double breakfastAmount = breakfastIncluded ? Setup.MEALS_PRICES[0] * peopleNum : 0;
        double lunchAmount = lunchIncluded ? Setup.MEALS_PRICES[1] * peopleNum : 0;
        double dinnerAmount = dinnerIncluded ? Setup.MEALS_PRICES[2] * peopleNum : 0;
        double mealsPrice = breakfastAmount + lunchAmount + dinnerAmount;

        // Calculate the price per night of the room depending on room type
        double roomPrice = roomType.toUpperCase().equals("REGULAR") ? Setup.ROOMS_PRICE[0] : Setup.ROOMS_PRICE[1];

        // Calculate the total amount
        return days * (roomPrice * peopleNumMultiplier + mealsPrice);
    }

    public static double calculatePrice(java.util.Date startDate, java.util.Date endDate,
            String roomType, int peopleNum, boolean breakfastIncluded, boolean lunchIncluded,
            boolean dinnerIncluded) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fromDateStr = dateFormat.format(startDate);
        String toDateStr = dateFormat.format(endDate);

        return Endpoints.calculatePrice(fromDateStr, toDateStr, roomType, peopleNum, breakfastIncluded, lunchIncluded,
                dinnerIncluded);

    }

    public static boolean createRoom(double price, String type, String name, int floor, int roomNum,
            int peopleCapacity) {
        try {

            String priceStr = Double.toString(price);
            String floorStr = Integer.toString(floor);
            String roomNumStr = Integer.toString(roomNum);
            String peopleCapacityStr = Integer.toString(peopleCapacity);

            Database.insertWithId("ROOMS",
                    new String[] { RoomsSchema.PRICE.toString(), RoomsSchema.TYPE.toString(),
                            RoomsSchema.NAME.toString(), RoomsSchema.FLOOR.toString(), RoomsSchema.ROOM_NUM.toString(),
                            RoomsSchema.PEOPLE_CAPACITY.toString() },

                    new String[] { priceStr, type, name, floorStr, roomNumStr, peopleCapacityStr });

            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static boolean createInvoice(int bookId) {
        try {

            // Create today date (invoice will be created with this date by default)
            LocalDate today = LocalDate.now();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateStr = df.format(today);

            Database.insertWithId("INVOICES",
                    new String[] { InvoicesSchema.BOOK_ID.toString(), InvoicesSchema.DATE.toString(),
                            InvoicesSchema.PAID.toString() },

                    new String[] { Integer.toString(bookId), dateStr, "false" });

            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
