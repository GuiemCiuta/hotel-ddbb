package db;

import java.sql.*;

import db.schemas.*;

public class Setup {
    // The order is the following: Standard rooms, Suite rooms
    public static double[] ROOMS_PRICE = new double[] { 70, 120 };

    // The order is the most logical one: breakfast, lunch & dinner
    public static double[] MEALS_PRICES = new double[] {7.5, 10.5, 10.5};

    public static Company company = new Company("B07316763", "Hotel, SA", "C/Fusters n7, POICI 07760");

    // Constructor
    public Setup() {
        createTables();
    }

    // Creates all the tables the app needs
    private void createTables() {
        try {

            createRoomsTable();
            createBooksTable();
            createCustomersTable();
            createInvoicesTable();
            createRoomsPerBookTable();
            createPersonCheckinTable();
            createCheckInsTable();
            createInternalUsersTable();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createRoomsTable() throws Exception {
        final String ID_FIELD = "ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT";
        final String NAME_FIELD = RoomsSchema.NAME + " VARCHAR(20)";
        final String PRICE_FIELD = RoomsSchema.PRICE + " DECIMAL";
        final String TYPE_FIELD = RoomsSchema.TYPE + " VARCHAR(20)";
        final String PEOPLE_CAPACITY_FIELD = RoomsSchema.PEOPLE_CAPACITY + " INT";
        final String FLOOR_FIELD = RoomsSchema.FLOOR + " INT";
        final String ROOM_NUM_FIELD = RoomsSchema.ROOM_NUM + " INT";

        Statement stm = Database.createStatement();
        String createRoomsTableSQL = String.format("CREATE TABLE IF NOT EXISTS ROOMS (%s, %s, %s, %s, %s, %s, %s);",
                ID_FIELD, PRICE_FIELD, NAME_FIELD, TYPE_FIELD, PEOPLE_CAPACITY_FIELD, FLOOR_FIELD, ROOM_NUM_FIELD);

        stm.execute(createRoomsTableSQL);
    }

    private void createBooksTable() throws Exception {
        final String ID_FIELD = "ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT";
        final String CUSTOMER_ID_FIELD = BooksSchema.CUSTOMER_ID + " VARCHAR(50)";
        final String START_DATE_FIELD = BooksSchema.START_DATE + " DATE";
        final String END_DATE_FIELD = BooksSchema.END_DATE + " DATE";
        final String AMOUNT_FIELD = BooksSchema.AMOUNT + " DECIMAL(10,2)";
        final String CANCELED_FIELD = BooksSchema.CANCELED + " BOOLEAN";

        Statement stm = Database.createStatement();
        String createBooksTableSQL = String.format("CREATE TABLE IF NOT EXISTS BOOKS (%s, %s, %s, %s, %s, %s);",
                ID_FIELD, CUSTOMER_ID_FIELD, START_DATE_FIELD, END_DATE_FIELD, AMOUNT_FIELD, CANCELED_FIELD);

        stm.execute(createBooksTableSQL);
    }

    private void createCustomersTable() throws Exception {
        final String ID_FIELD = "ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT";
        final String FIRST_NAME_FIELD = CustomersSchema.FIRST_NAME + " VARCHAR(20)";
        final String LAST_NAMES_FIELD = CustomersSchema.LAST_NAMES + " VARCHAR(50)";
        final String NATIONAL_ID_FIELD = CustomersSchema.NATIONAL_ID + " VARCHAR(20)"; // 20 for an ID might seem a
                                                                                       // really big number, but we want
                                                                                       // to make sure any county's ID
                                                                                       // is valid here, just in case
        final String ADDRESS_FIELD = CustomersSchema.ADDRESS + " VARCHAR(60)";
        final String EMAIL_FIELD = CustomersSchema.EMAIL + " VARCHAR(255)";
        final String PHONE_FIELD = CustomersSchema.PHONE + " VARCHAR(15)";
        final String COUNTRY_FIELD = CustomersSchema.COUNTRY + " VARCHAR(2)"; // We only store two characters (ae ES for
                                                                              // Spain)
        final String PASSWORD_FIELD = CustomersSchema.PASSWORD + " VARCHAR(255)"; // Only used by users that use our
                                                                                  // online booking system
                                                                                  // Our passwords will be stored after
                                                                                  // a sha256 hash
        Statement stm = Database.createStatement();
        String createCustomersTableSQL = String.format(
                "CREATE TABLE IF NOT EXISTS CUSTOMERS (%s, %s, %s, %s, %s, %s, %s, %s, %s);",
                ID_FIELD, FIRST_NAME_FIELD, LAST_NAMES_FIELD, NATIONAL_ID_FIELD, ADDRESS_FIELD, EMAIL_FIELD,
                PHONE_FIELD, COUNTRY_FIELD, PASSWORD_FIELD);

        stm.execute(createCustomersTableSQL);
    }

    private void createInvoicesTable() throws Exception {
        final String ID_FIELD = "ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT";
        final String BOOK_ID_FIELD = InvoicesSchema.BOOK_ID + " INT";
        final String DATE_FIELD = InvoicesSchema.DATE + " DATE";
        final String PAID_FIELD = InvoicesSchema.PAID + " BOOLEAN";

        Statement stm = Database.createStatement();
        String createInvoicesTableSQL = String.format("CREATE TABLE IF NOT EXISTS INVOICES (%s, %s, %s, %s);",
                ID_FIELD, BOOK_ID_FIELD, DATE_FIELD, PAID_FIELD);

        stm.execute(createInvoicesTableSQL);
    }

    // You don't have an assigned room until you make the check-in
    // So, when you book a room, you only will select the type and the amount of
    // people
    private void createRoomsPerBookTable() throws Exception {
        final String ID_FIELD = "ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT";
        final String BOOK_ID_FIELD = RoomsPerBookSchema.BOOK_ID + " INT";
        final String ROOM_TYPE_FIELD = RoomsPerBookSchema.ROOM_TYPE + " VARCHAR(20)";
        final String PEOPLE_NUM_FIELD = RoomsPerBookSchema.PEOPLE_NUM + " INT";
        final String BREAKFAST_FIELD = RoomsPerBookSchema.BREAKFAST + " BOOLEAN";
        final String LUNCH_FIELD = RoomsPerBookSchema.LUNCH + " BOOLEAN";
        final String DINNER_FIELD = RoomsPerBookSchema.DINNER + " BOOLEAN";

        Statement stm = Database.createStatement();
        String createRoomsPerBookTableSQL = String.format(
                "CREATE TABLE IF NOT EXISTS ROOMS_PER_BOOK (%s, %s, %s, %s, %s, %s, %s);",
                ID_FIELD, BOOK_ID_FIELD, ROOM_TYPE_FIELD, PEOPLE_NUM_FIELD, BREAKFAST_FIELD, LUNCH_FIELD, DINNER_FIELD);

        stm.execute(createRoomsPerBookTableSQL);
    }

    private void createPersonCheckinTable() throws Exception {
        final String CHECKIN_ID_FIELD = PersonCheckin.CHECKIN_ID + " INT";
        final String PERSON_NATIONAL_ID_FIELD = PersonCheckin.PERSON_NATIONAL_ID + " VARCHAR(20)";

        Statement stm = Database.createStatement();
        String createPersonCheckinSQL = String.format(
                "CREATE TABLE IF NOT EXISTS PERSON_CHECKIN (%s, %s);",
                CHECKIN_ID_FIELD, PERSON_NATIONAL_ID_FIELD);

        stm.execute(createPersonCheckinSQL);
    }

    // You don't have an assigned room until you make the check-in
    // So, when you book a room, you only will select the type and the amount of
    // people
    private void createCheckInsTable() throws Exception {
        final String ID_FIELD = "ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT";
        final String BOOK_ID_FIELD = CheckInsSchema.BOOK_ID + " INT";
        final String DATE_FIELD = CheckInsSchema.DATE + " DATE";
        final String ROOM_ID_FIELD = CheckInsSchema.ROOM_ID + " INT";
        final String RECEPTIONIST_ID_FIELD = CheckInsSchema.RECEPTIONIST_ID + " INT";

        Statement stm = Database.createStatement();

        String createCheckInsTableSQL = String.format("CREATE TABLE IF NOT EXISTS CHECK_INS (%s, %s, %s, %s, %s);",
                ID_FIELD, BOOK_ID_FIELD, DATE_FIELD, ROOM_ID_FIELD, RECEPTIONIST_ID_FIELD);

        stm.execute(createCheckInsTableSQL);
    }

    private void createInternalUsersTable() throws Exception {
        final String ID_FIELD = "ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT";
        final String FIRST_NAME_FIELD = InternalUsersSchema.FIRST_NAME + " VARCHAR(20)";
        final String LAST_NAMES_FIELD = InternalUsersSchema.LAST_NAMES + " VARCHAR(50)";
        final String NATIONAL_ID_FIELD = InternalUsersSchema.NATIONAL_ID + " VARCHAR(20)";
        final String PASSWORD_FIELD = InternalUsersSchema.PASSWORD + " VARCHAR(255)";
        final String ROLE_FIELD = InternalUsersSchema.ROLE + " VARCHAR(255)";

        Statement stm = Database.createStatement();

        String createInternalUsersTableSQL = String.format(
                "CREATE TABLE IF NOT EXISTS INTERNAL_USERS (%s, %s, %s, %s, %s, %s);",
                ID_FIELD, FIRST_NAME_FIELD, LAST_NAMES_FIELD, NATIONAL_ID_FIELD,
                PASSWORD_FIELD, ROLE_FIELD);

        stm.execute(createInternalUsersTableSQL);
    }
}
