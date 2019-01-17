package com.UIT.QTV.ScanMaster.CalendarCard;

import java.text.Normalizer;
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


}
