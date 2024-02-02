package db;

import java.sql.ResultSet;
import java.util.Arrays;

import javax.xml.crypto.Data;

import db.schemas.RoomsSchema;
import db.schemas.BooksSchema;
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

    public static boolean makeReservation(int customerId, String startDate, String endDate, double amount,
            String[] roomTypes, int[] peopleNums, boolean[] breakfastIncluded, boolean[] lunchIncluded,
            boolean[] dinnerIncluded) {
        try {

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
                            startDate,
                            endDate,
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

            for (int i = 0; i < roomTypes.length; i++) {
                String roomType = roomTypes[i];
                int peopleNum = peopleNums[i];
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
                                Boolean.toString(breakfastIncluded[i]),
                                Boolean.toString(lunchIncluded[i]),
                                Boolean.toString(dinnerIncluded[i]),
                        });
            }

            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
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
                            
                    new String[]{priceStr, type, name, floorStr, roomNumStr, peopleCapacityStr});

            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
