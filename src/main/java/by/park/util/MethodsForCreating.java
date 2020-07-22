package by.park.util;

public class MethodsForCreating {
    public static String createCardNumber() {
        String result = "";
        for (int i = 0; i < 4; i++) {
            result = result + (int) (1000 + Math.random() * 8999);
            if (i != 3) {
                result = result + " ";
            }
        }
        return result;
    }

    public static String createIBAN(String bankCode) {
        String result = "BY20 " + bankCode + " ";
        for (int i = 0; i < 5; i++) {
            result = result + (int) (1000 + Math.random() * 8999);
            if (i != 4) {
                result = result + " ";
            }
        }
        return result;
    }
}
