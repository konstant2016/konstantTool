package com.konstant.cardprotocol.util;

public class LuhnUtils {

    public static String appendCheckDigit(String numberStr) {
        if (!numberStr.matches("^[0-9A-Fa-f]+$")) {
            throw new IllegalArgumentException("All characters should be numeric in Luhn");
        }

        return numberStr.concat(LuhnUtils.getCheckDigit(numberStr));
    }

    private static String getCheckDigit(String numberStr) {
        int sum = sum(numberStr);
        int checkDigit = sum % 10 == 0 ? 0 : 10 - sum % 10;
        return String.valueOf(checkDigit);
    }

    private static int sum(String numberStr) {
        String[] arr = numberStr.split("\\B");

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            int j = Integer.valueOf(arr[i], 16);
            j = i % 2 == 1 ? j * 2 / 10 + j * 2 % 10 : j;
            sum += j;
        }

        return sum;
    }

}
