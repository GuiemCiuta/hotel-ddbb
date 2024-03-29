package db;

import java.security.MessageDigest;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import db.schemas.BooksSchema;

public class Database {
    // Global vars
    public static final String DB_PORT = "3306";
    public static final String DB_URL = "localhost";
    public static final String DB_NAME = "hotel";
    public static final String DB_USER = "java";
    public static final String DB_PASSWORD = "password";

    // Returns a connection to the database
    public static Connection connectDB() throws Exception {
        Class.forName("com.mysql.jdbc.Driver"); // Load the mysql library Driver

        // Generate the connection Url
        String connectionUrl = String.format(
                "jdbc:mysql://%s:%s/%s",
                DB_URL, DB_PORT, DB_NAME);

        // Create the connection instance
        Connection connection = DriverManager.getConnection(connectionUrl, DB_USER, DB_PASSWORD);

        System.out.println("DB Connected!");

        return connection;

    }

    public static Statement createStatement() throws Exception {
        // Create the connection
        Connection connection = connectDB();

        // Create the statement
        Statement statement = connection.createStatement();

        return statement;
    }

    // Inserts values in the order as they appear into the schema
    public static boolean insert(String table, String[] schemaValues, String[] values) {
        try {

            if (schemaValues.length != values.length) {
                throw new Exception("Please, provide a schema and values that match.");
            }

            // We neead to wrap all values array element that isn't a number with quotation
            // marks
            Utils.prepareValuesArrayToIsertDB(values);

            String schemaStr = String.join(", ", schemaValues);
            String valuesStr = String.join(", ", values);

            // Create the query
            String queryTemplate = "INSERT INTO %s (%s) VALUES (%s);";
            String query = String.format(queryTemplate, table, schemaStr, valuesStr);

            // Execute the query
            Statement stm = Database.createStatement();
            return stm.execute(query);

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    // Same for bulk insert
    public static boolean insert(String table, String[] schemaValues, String[][] values) {
        try {

            if (schemaValues.length != values.length) {
                throw new Exception("Please, provide a schema and values that match.");
            }

            String schemaStr = String.join(", ", schemaValues);

            String valuesStr = "";

            // Concatenate all the subarrays to the valuesStr (values string)
            for (int i = 0; i < values.length; i++) {
                // We neead to wrap all values array element that isn't a number with quotation
                // marks
                Utils.prepareValuesArrayToIsertDB(values[i]);
                String valuesSetStr = String.join(", ", values[i]);

                String suffix = (i + 1 == values.length) ? "" : ") (";

                valuesStr += valuesSetStr + suffix;
            }

            // Create the query
            String queryTemplate = "INSERT INTO %s (%s) VALUES (%s);";
            String query = String.format(queryTemplate, table, schemaStr, valuesStr);

            // Execute the query
            Statement stm = Database.createStatement();
            return stm.execute(query);

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    // Inserts values in the order as they appear into the schema
    // It automatically adds an id field
    public static boolean insertWithId(String table, String[] schemaValues, String[] values) {
        try {

            if (schemaValues.length != values.length) {
                throw new Exception("Please, provide a schema and values that match.");
            }

            // We neead to wrap all values array element that isn't a number with quotation
            // marks
            Utils.prepareValuesArrayToIsertDB(values);

            String schemaStr = String.join(", ", schemaValues);
            String valuesStr = String.join(", ", values);

            // Create the query
            String queryTemplate = "INSERT INTO %s (ID, %s) VALUES (DEFAULT, %s);";
            String query = String.format(queryTemplate, table, schemaStr, valuesStr);

            System.out.println(query);

            // Execute the query
            Statement stm = Database.createStatement();
            return stm.execute(query);

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    // Same for bulk insert
    public static boolean insertWithId(String table, String[] schemaValues, String[][] values) {
        try {

            if (schemaValues.length != values.length) {
                throw new Exception("Please, provide a schema and values that match.");
            }

            String schemaStr = String.join(", ", schemaValues);

            String valuesStr = "";

            // Concatenate all the subarrays to the valuesStr (values string)
            for (int i = 0; i < values.length; i++) {
                // We neead to wrap all values array element that isn't a number with quotation
                // marks
                Utils.prepareValuesArrayToIsertDB(values[i]);
                String valuesSetStr = String.join(", ", values[i]);

                String suffix = (i + 1 == values.length) ? "" : ") (";

                valuesStr += valuesSetStr + suffix;
            }

            // Create the query
            String queryTemplate = "INSERT INTO %s (%s) VALUES (%s);";
            String query = String.format(queryTemplate, table, schemaStr, valuesStr);

            // Execute the query
            Statement stm = Database.createStatement();
            return stm.execute(query);

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    // Deletes values using a where clause
    public static boolean deleteWhere(String table, String filter) {
        try {

            // Create the query
            String queryTemplate = "DELETE FROM %s WHERE %s";
            String query = String.format(queryTemplate, table, filter);

            // Execute the query
            Statement stm = Database.createStatement();
            return stm.execute(query);

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static boolean deleteWhere(String table, String filter, int limit) {
        try {

            // Create the query
            String queryTemplate = "DELETE FROM %s WHERE %s LIMIT %s";
            String query = String.format(queryTemplate, table, filter, limit);

            // Execute the query
            Statement stm = Database.createStatement();
            return stm.execute(query);

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    // Selects all fields using a where clause
    public static ResultSet selectAllWhere(String table, String filter) {
        try {

            // Create the query
            String queryTemplate = "SELECT * FROM %s WHERE %s";
            String query = String.format(queryTemplate, table, filter);

            // Execute the query
            Statement stm = Database.createStatement();
            return stm.executeQuery(query);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static ResultSet selectAllWhere(String table, String filter, int limit) {
        try {

            // Create the query
            String queryTemplate = "SELECT * FROM %s WHERE %s LIMIT %s";
            String query = String.format(queryTemplate, table, filter, limit);

            // Execute the query
            Statement stm = Database.createStatement();
            return stm.executeQuery(query);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static ResultSet selectAllWhere(String table, String filter, String suffix) {
        try {

            // Create the query
            String queryTemplate = "SELECT * FROM %s WHERE %s %s";
            String query = String.format(queryTemplate, table, filter, suffix);

            System.out.println(query);

            // Execute the query
            Statement stm = Database.createStatement();
            return stm.executeQuery(query);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static ResultSet selectFieldsWhere(String table, String fields, String filter) {
        try {

            // Create the query
            String queryTemplate = "SELECT %s FROM %s WHERE %s";
            String query = String.format(queryTemplate, fields, table, filter);

            // Execute the query
            Statement stm = Database.createStatement();
            return stm.executeQuery(query);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static ResultSet selectFieldsWhere(String table, String fields, String filter, int limit) {
        try {

            // Create the query
            String queryTemplate = "SELECT %s FROM %s WHERE %s LIMIT %s";
            String query = String.format(queryTemplate, fields, table, filter, limit);

            // Execute the query
            Statement stm = Database.createStatement();
            return stm.executeQuery(query);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static ResultSet selectFieldsWhere(String table, String fields, String filter, String suffix) {
        try {

            // Create the query
            String queryTemplate = "SELECT %s FROM %s WHERE %s %s";
            String query = String.format(queryTemplate, fields, table, filter, suffix);

            // Execute the query
            Statement stm = Database.createStatement();
            return stm.executeQuery(query);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static ResultSet executeQueryRS(String query) {
        try {
            // Execute the query
            Statement stm = Database.createStatement();
            return stm.executeQuery(query);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    // A function to get the results of a SQL query that must return only a count
    // statement
    private static int getCountSQLQuery(String query, Statement stm) {
        try {
            ResultSet rs = stm.executeQuery(query);

            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }

    // This is an execution of my own-thought algorithm explained here:
    // https://docs.google.com/spreadsheets/d/1XhjKQmlR5vyI1FQuQbWoZWFiyaxVoEvPA8Ty8Cs4s4Q/edit?pli=1#gid=0
    public static int countEmptyRooms(String roomType, int peopleNum, java.util.Date fromDate, java.util.Date toDate) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fromDateStr = dateFormat.format(fromDate);
        String toDateStr = dateFormat.format(toDate);

        String lowerCapacityRoomsSQL = "SELECT COUNT(*) FROM ROOMS WHERE TYPE = '%s' AND PEOPLE_CAPACITY < %s";
        lowerCapacityRoomsSQL = String.format(lowerCapacityRoomsSQL, roomType, peopleNum);

        String sameOrHigherCapacityRoomsSQL = "SELECT COUNT(*) FROM ROOMS WHERE TYPE = '%s' AND PEOPLE_CAPACITY >= %s";
        sameOrHigherCapacityRoomsSQL = String.format(sameOrHigherCapacityRoomsSQL, roomType, peopleNum);

        // To make the date fit, it must meet one of these two conditions (they're
        // negative, so query MUST NOT meet any of them)
        String dateCondition1 = "B.START_DATE >= '%s' AND B.END_DATE >= '%s' AND B.START_DATE >= '%s' AND B.END_DATE >= '%s'";
        dateCondition1 = String.format(dateCondition1, fromDateStr, fromDateStr, toDateStr, toDateStr);

        String dateCondition2 = "B.START_DATE <= '%s' AND B.END_DATE <= '%s' AND B.START_DATE <= '%s' AND B.END_DATE <= '%s'";
        dateCondition2 = String.format(dateCondition2, fromDateStr, fromDateStr, toDateStr, toDateStr);

        String lowerCapacityBooksSQL = "SELECT COUNT(*) FROM ROOMS_PER_BOOK RPB LEFT JOIN BOOKS B ON RPB.BOOK_ID = B.ID WHERE RPB.ROOM_TYPE = '%s' AND RPB.PEOPLE_NUM < %s AND NOT (%s OR %s)";
        lowerCapacityBooksSQL = String.format(lowerCapacityBooksSQL, roomType, peopleNum, dateCondition1,
                dateCondition2);
        String sameOrHigherCapacityBooksSQL = "SELECT COUNT(*) FROM ROOMS_PER_BOOK RPB LEFT JOIN BOOKS B ON RPB.BOOK_ID = B.ID WHERE RPB.ROOM_TYPE = '%s' AND RPB.PEOPLE_NUM >= %s AND NOT (%s OR %s)";
        sameOrHigherCapacityBooksSQL = String.format(sameOrHigherCapacityBooksSQL, roomType, peopleNum, dateCondition1,
                dateCondition2);

        try {

            int lowerCapacityBooks, lowerCapacityRooms;
            int sameOrHigherCapacityBooks, sameOrHigherCapacityRooms;
            int lowerCapacityOverflow, sameOrHigherCapacityOverflow;
            int finalResult;

            Statement stm = Database.createStatement();

            // Calculate the amount of empty (and valid) rooms for the current reservation.

            // Get all the necessary data to make the calculus
            lowerCapacityBooks = getCountSQLQuery(lowerCapacityBooksSQL, stm);
            lowerCapacityRooms = getCountSQLQuery(lowerCapacityRoomsSQL, stm);
            sameOrHigherCapacityBooks = getCountSQLQuery(sameOrHigherCapacityBooksSQL, stm);
            sameOrHigherCapacityRooms = getCountSQLQuery(sameOrHigherCapacityRoomsSQL, stm);

            // All the overflow books must be placed into rooms that could be valid for our
            // current reservation
            lowerCapacityOverflow = lowerCapacityBooks - lowerCapacityRooms;
            // It must filter negative results, they're not useful (explained in the algo)
            if (lowerCapacityOverflow < 0) {
                lowerCapacityOverflow = 0;
            }

            sameOrHigherCapacityOverflow = sameOrHigherCapacityRooms - sameOrHigherCapacityBooks;
            // It must filter negative results, they're not useful (explained in the algo)
            if (sameOrHigherCapacityOverflow < 0) {
                sameOrHigherCapacityOverflow = 0;
            }

            // The final result will be the amount of valid rooms, substracting all the
            // already booked rooms, and also substracting the overflow from lower rooms.
            finalResult = sameOrHigherCapacityOverflow - lowerCapacityOverflow;

            System.out.println(lowerCapacityBooks);
            System.out.println(sameOrHigherCapacityOverflow);
            System.out.println(finalResult);

            return finalResult;

        } catch (Exception e) {
            System.out.println(e);

            return 0;
        }
    }

    // As it may seem a generic function to make a sql query
    // I'll use it for updating, so if I want to upgrade the function in the future,
    // all updates come here.
    public static boolean query(String query) {
        try {

            Statement stm = Database.createStatement();

            return stm.execute(query);

        } catch (Exception e) {
            System.out.println(e);

            return false;
        }
    }

    // Copied from: https://stackoverflow.com/a/11009612
    // Returns a sha256 hashed string
    // tbh idk how does it work, but it does
    public static String sha256(final String base) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static int getLastIdInTable(String table) {
        ResultSet rs = Database.selectFieldsWhere(table, "MAX(ID)", "1");

        try {
            rs.next();
            return rs.getInt("MAX(ID)");
        } catch (Exception e) {
            return -1;
        }
    }
}
