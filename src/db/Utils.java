package db;

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
            double d = Double.parseDouble(strNum);
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
}
