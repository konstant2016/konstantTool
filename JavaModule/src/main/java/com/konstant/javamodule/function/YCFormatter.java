package com.konstant.javamodule.function;


import java.util.Locale;

public class YCFormatter {

    public static String formatFileSize(long roundedBytes) {
        return formatFileSize(roundedBytes, true, Locale.US);
    }


    public static String formatFileSize(long roundedBytes, Locale locale) {
        return formatFileSize(roundedBytes, true, locale);
    }

    private static String formatFileSize(long roundedBytes, boolean shorter, Locale locale) {
        float result = roundedBytes;
        String suffix = "B";
        if (result > 900) {
            suffix = "KB";
            result = result / 1024;
        }
        if (result > 900) {
            suffix = "MB";
            result = result / 1024;
        }
        if (result > 900) {
            suffix = "GB";
            result = result / 1024;
        }
        if (result > 900) {
            suffix = "TB";
            result = result / 1024;
        }
        if (result > 900) {
            suffix = "PB";
            result = result / 1024;
        }
        String value;
        if (result < 1) {
            value = String.format(locale, "%.2f", result);
        } else if (result < 10) {
            if (shorter) {
                value = String.format(locale, "%.1f", result);
            } else {
                value = String.format(locale, "%.2f", result);
            }
        } else if (result < 100) {
            if (shorter) {
                value = String.format(locale, "%.0f", result);
            } else {
                value = String.format(locale, "%.2f", result);
            }
        } else {
            value = String.format(locale, "%.0f", result);
        }
        return String.format("%s%s", value, suffix);
    }
}
