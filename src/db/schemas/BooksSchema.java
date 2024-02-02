package db.schemas;

import java.util.Arrays;

public enum BooksSchema {
    CUSTOMER_ID, START_DATE, END_DATE, AMOUNT, CANCELED; // Amount is money

    public static String[] toStringValues() {
        String[] result = new String[0];

        for (BooksSchema field : values()) {
            result = Arrays.copyOf(result, result.length + 1);
            result[result.length - 1] = field.toString();
        }


        return result;
    }
}
