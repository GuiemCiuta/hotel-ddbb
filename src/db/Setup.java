package db;

import java.sql.*;

import db.schemas.*;

public class Setup {
    // Constructor
    Setup() {
        createTables();
    }

    // Creates all the tables the app needs
    private void createTables() {
        try {

            createRoomsTable();
            createBooksTable();
            createCustomersTable();
            createInvoicesTable();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createRoomsTable() throws Exception {
        final String ID_FIELD = "ID INT NOT NULL PRIMARY KEY";
        final String NAME_FIELD = RoomsSchema.NAME + " VARCHAR(50)";
        final String PRICE_FIELD = RoomsSchema.PRICE + " DECIMAL";
        final String TYPE_FIELD = RoomsSchema.TYPE + " VARCHAR(20)";
        final String PEOPLE_CAPACITY_FIELD = RoomsSchema.PEOPLE_CAPACITY + " INT";
        final String FLOOR_FIELD = RoomsSchema.FLOOR + " INT";
        final String ROOM_NUM_FIELD = RoomsSchema.ROOM_NUM + " INT";

        Statement stm = Database.createStatement();
        String createArtistSQL = String.format("CREATE TABLE IF NOT EXISTS ROOMS (%s, %s, %s, %s, %s, %s, %s);",
                ID_FIELD, NAME_FIELD, PRICE_FIELD, TYPE_FIELD, PEOPLE_CAPACITY_FIELD, FLOOR_FIELD, ROOM_NUM_FIELD);

        stm.execute(createArtistSQL);
    }

    private void createBooksTable() throws Exception {
        final String ID_FIELD = "ID INT NOT NULL PRIMARY KEY";
        final String CUSTOMER_ID_FIELD = BooksSchema.CUSTOMER_ID + " VARCHAR(50)";
        final String START_DATE_FIELD = BooksSchema.START_DATE + " DECIMAL";
        final String END_DATE_FIELD = BooksSchema.END_DATE + " VARCHAR(20)";
        final String AMOUNT_FIELD = BooksSchema.AMOUNT + " DECIMAL";

        Statement stm = Database.createStatement();
        String createArtistSQL = String.format("CREATE TABLE IF NOT EXISTS BOOKS (%s, %s, %s, %s, %s);",
                ID_FIELD, CUSTOMER_ID_FIELD, START_DATE_FIELD, END_DATE_FIELD, AMOUNT_FIELD);

        stm.execute(createArtistSQL);
    }

    private void createCustomersTable() throws Exception {
        final String ID_FIELD = "ID INT NOT NULL PRIMARY KEY";
        final String FIRST_NAME_FIELD = CustomersSchema.FIRST_NAME + " VARCHAR(20)";
        final String LAST_NAMES_FIELD = CustomersSchema.LAST_NAMES + " VARCHAR(50)";
        final String NATIONAL_ID_FIELD = CustomersSchema.NATIONAL_ID + " VARCHAR(20)"; // 20 for an ID might seem a really big number, but we want to make sure any county's ID is valid here, just in case
        final String ADDRESS_FIELD = CustomersSchema.ADDRESS + " VARCHAR(60)";
        final String EMAIL_FIELD = CustomersSchema.EMAIL + " VARCHAR(255)";
        final String PHONE_FIELD = CustomersSchema.PHONE + " VARCHAR(15)";
        final String COUNTRY_FIELD = CustomersSchema.COUNTRY + " VARCHAR(2)"; // We only store two characters (ae ES for Spain)
        final String PASSWORD_FIELD = CustomersSchema.PASSWORD + " VARCHAR(255)"; // Only used by users that use our online booking system
                                                                                    // Our passwords will be stored after a sha256 hash
        Statement stm = Database.createStatement();
        String createArtistSQL = String.format("CREATE TABLE IF NOT EXISTS BOOKS (%s, %s, %s, %s, %s, %s, %s, %s, %s);",
                ID_FIELD, FIRST_NAME_FIELD, LAST_NAMES_FIELD, NATIONAL_ID_FIELD, ADDRESS_FIELD, EMAIL_FIELD, PHONE_FIELD, COUNTRY_FIELD, PASSWORD_FIELD);

        stm.execute(createArtistSQL);
    }

    private void createInvoicesTable() throws Exception {
        final String ID_FIELD = "ID INT NOT NULL PRIMARY KEY";
        final String BOOK_ID_FIELD = InvoicesSchema.BOOK_ID + " INT";
        final String AMOUNT_FIELD = InvoicesSchema.AMOUNT + " DECIMAL";
        final String DATE_FIELD = InvoicesSchema.DATE + " DATE";
        final String PAID_FIELD = InvoicesSchema.PAID + " BOOLEAN";

        Statement stm = Database.createStatement();
        String createArtistSQL = String.format("CREATE TABLE IF NOT EXISTS BOOKS (%s, %s, %s, %s, %s);",
                ID_FIELD, BOOK_ID_FIELD, AMOUNT_FIELD, DATE_FIELD, PAID_FIELD);

        stm.execute(createArtistSQL);
    }
}
