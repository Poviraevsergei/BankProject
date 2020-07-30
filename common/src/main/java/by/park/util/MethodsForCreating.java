package by.park.util;

public class MethodsForCreating {
    public static String createCardNumber() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            result.append((int) (1000 + Math.random() * 8999));
            if (i != 3) {
                result.append(" ");
            }
        }
        return result.toString();
    }

    public static String createIBAN(String bankCode) {
        StringBuilder result = new StringBuilder("BY20 " + bankCode + " ");
        for (int i = 0; i < 5; i++) {
            result.append((int) (1000 + Math.random() * 8999));
            if (i != 4) {
                result.append(" ");
            }
        }
        return result.toString();
    }
}