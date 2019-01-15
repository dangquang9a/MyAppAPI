package com.example.duongthanhtin.myappapi;

import java.text.Normalizer;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.regex.Pattern;

/*
    Class that help manipulate with string.
    All members are static.

 */
public class StringUtils {
    private StringUtils() {}

    public static int countMatchedChar(String string, char c){
        int result = 0;
        for (int i = 0; i < string.length(); i++)
            if (string.charAt(i) == c)
                result++;

        return result;
    }

    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D');
    }

    public static boolean isValidDay(int day, int month, int year) {
        if (month < 0 || month > 12)    return false;
        if (day < 0 || day > 31)        return false;

        if (month == 2) {
            // Leap year
            if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
                if (day > 29)
                    return false;
            } else {
                if (day > 28)
                    return false;
            }
        }

        if (month == 2 || month == 4 || month == 6 ||
                month == 9 || month == 11) {
            if (day == 31)
                return false;
        }

        return true;
    }
}
