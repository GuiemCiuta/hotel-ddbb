package db;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {
    // Wraps with quotation marks the string
    public static String addQuotationMarksToString(String str) {
        return "\"" + str + "\"";
    }

    // Wraps with quotation marks each element in array
    // It overrides original array
    public static void addQuotationmarksToArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = addQuotationMarksToString(arr[i]);
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            float f = Float.parseFloat(strNum);

            // As sometimes, float parses numbers with some letters (e.g. it doesn't return
            // the expected results with National IDs, that mix numbers and a letter), we'll
            // make a second comparison

            // So, we'll also check if it contains any letter
            // In that case, it'll return false
            if (strNum.matches(".*[a-zA-Z].*")) {
                return false;
            }

        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // Wraps with quotation marks each element in array that isn't a number
    // It overrides original array
    public static void prepareValuesArrayToIsertDB(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // We avoid those that aren't number-only
            if (isNumeric(arr[i]) || arr[i].toLowerCase() == "true" || arr[i].toLowerCase() == "false") {
                continue;
            }

            arr[i] = addQuotationMarksToString(arr[i]);
        }
    }

    public static void printResultSet(ResultSet rs) {
        try {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }
        } catch (Exception e) {

        }
    }

    public static String convertDateToString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static LocalDate convertStringToLocalDate(String dateStr) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateStr, df);

    }

    public static String getExecutionPath() {
        return System.getProperty("user.dir");
    }
}
