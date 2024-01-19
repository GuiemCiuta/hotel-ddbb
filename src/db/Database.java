package db;

import java.sql.*;

public class Database {
    // Global vars
    public static final String DB_PORT = "3306";
    public static final String DB_URL = "localhost";
    public static final String DB_NAME = "hotels";
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
}
