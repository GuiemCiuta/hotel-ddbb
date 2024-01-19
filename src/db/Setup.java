package db;

import java.sql.*;

import db.schemas.BooksSchema;
import db.schemas.CustomersSchema;
import db.schemas.RoomsSchema;

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
        final String FIRST_NAME_FIELD = CustomersSchema.FIRST_NAME + " VARCHAR(50)";
        final String LAST_NAMES_FIELD = CustomersSchema.FIRST_NAME + " VARCHAR(50)";
        final String NATIONAL_ID_FIELD = CustomersSchema.FIRST_NAME + " VARCHAR(50)";
        final String ADDRESS_FIELD = CustomersSchema.FIRST_NAME + " VARCHAR(50)";
        final String EMAIL_FIELD = CustomersSchema.FIRST_NAME + " VARCHAR(50)";
        final String PHONE_FIELD = CustomersSchema.FIRST_NAME + " VARCHAR(50)";
        final String COUNTRY_FIELD = CustomersSchema.FIRST_NAME + " VARCHAR(50)";
        final String PASSWORD_FIELD = CustomersSchema.FIRST_NAME + " VARCHAR(50)"; // Only used by users that use our online booking system

        Statement stm = Database.createStatement();
        String createArtistSQL = String.format("CREATE TABLE IF NOT EXISTS BOOKS (%s, %s, %s, %s, %s, %s, %s, %s, %s);",
                ID_FIELD, FIRST_NAME_FIELD, LAST_NAMES_FIELD, NATIONAL_ID_FIELD, ADDRESS_FIELD, EMAIL_FIELD, PHONE_FIELD, COUNTRY_FIELD, PASSWORD_FIELD);

        stm.execute(createArtistSQL);
    }
}
