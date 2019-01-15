package com.example.duongthanhtin.myappapi;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class TextAnalyzer {
    public TextAnalyzer(String text) {
        this.text = text;
    }

    private String text;                // Text that need analyzing.
    private CardInformation cardInfo = new CardInformation();

    static int LINE_ATTRIBUTE_NAME = 1;
    static int LINE_ATTRIBUTE_ADRESS = 2;
    static int LINE_ATTRIBUTE_TIME = 4;
    static int LINE_ATTRIBUTE_DATE = 8;

    public CardInformation getCardInformation() { return this.cardInfo; }

    public void analyze() {
        ArrayList<String> lines = new ArrayList(Arrays.asList(text.split("\n")));
        for (String line : lines) {
            int lineAttribute = analyzeLine(line);

            // Checking if line contains the date
            MyDateTime date = new MyDateTime();
            if (!checkLineAttribute(lineAttribute, LINE_ATTRIBUTE_TIME) &&
                    !checkLineAttribute(lineAttribute, LINE_ATTRIBUTE_DATE)) {
                if (checkIfContainDate(line, date)) {
                    lineAttribute |= LINE_ATTRIBUTE_DATE;
                    cardInfo.addDate(date);
                }
            }

            // Checking the line attribute to find the name/address
            if (!checkLineAttribute(lineAttribute, LINE_ATTRIBUTE_TIME) &&
                    !checkLineAttribute(lineAttribute, LINE_ATTRIBUTE_DATE)) {
                // This is line containing address or name of the event
                cardInfo.addEventName(line);
                cardInfo.addAddress(line);
            }
        }
    }

    public int analyzeLine(String line)
    {
        int lineAttribute = 0;

        ArrayList<String> strings = new ArrayList<>(Arrays.asList(line.split(" ")));
        for (String string : strings) {

            // Check if this is time
            MyDateTime time = new MyDateTime();
            if (isTime(string, time)) {
                cardInfo.addTime(time);
                lineAttribute |= LINE_ATTRIBUTE_TIME;
            }

            // Check if this is date
            MyDateTime date = new MyDateTime();
            if (isDate(string, date)) {
                cardInfo.addDate(date);
                lineAttribute |= LINE_ATTRIBUTE_DATE;
            }
        }

        return lineAttribute;
    }

    public boolean isDate(String string, MyDateTime date) {
        string = string.toUpperCase();
        string = StringUtils.removeAccent(string);
        ArrayList<String> numbers = new ArrayList<>();

        if (StringUtils.countMatchedChar(string, '-') == 2) {
            numbers = new ArrayList<String>(Arrays.asList(TextUtils.split(string, "-")));
        }
        else if (StringUtils.countMatchedChar(string, '/') == 2) {
            numbers = new ArrayList<String>(Arrays.asList(TextUtils.split(string, "/")));
        }

        //Check number:
        if (numbers.size() == 3){
            for (String number : numbers)
                if (TextUtils.isDigitsOnly(number) == false)
                    return false;

            // get day
            int day = Integer.parseInt(numbers.get(0));

            // get month
            int month = Integer.parseInt(numbers.get(1));

            // get year
            int year = Integer.parseInt(numbers.get(2));

            // set and check
            date.setDate(day, month, year);
            if (date.isValidDay() == false)
                return false;
        }
        else return false;

        return true;
    }

    public boolean isTime(String string, MyDateTime time){
        string = string.toUpperCase();
        ArrayList<String> numbers = new ArrayList<>();
        //if (StringUtils.countMatchedChar(string, ':') == 1){
        if (string.contains(":")){
            numbers = new ArrayList<String>(Arrays.asList(TextUtils.split(string, ":")));
        }
        else if (string.contains("H")){
            numbers = new ArrayList<String>(Arrays.asList(TextUtils.split(string, "H")));
        }
        else if (string.contains("AM")){
            numbers = new ArrayList<String>(Arrays.asList(TextUtils.split(string, "AM")));
        }
        else if (string.contains("PM")){
            numbers = new ArrayList<String>(Arrays.asList(TextUtils.split(string, "PM")));
        }
        else return false;

        //Check number:
        if (numbers != null){
            for (String number : numbers)
                if (TextUtils.isDigitsOnly(number) == false)
                    return false;

            // get hour
            int hour = -1;
            hour = Integer.parseInt(numbers.get(0));

            // get minute
            int minute = 0;
            if (numbers.size() > 1) {
                minute = Integer.parseInt(numbers.get(1));
            }

            // set & check
            time.setTime(hour, minute, 0);
            if (time.isValidTime() == false)
                return false;
        }
        else return false;
        return true;
    }

    public boolean checkLineAttribute(int lineAttribute, int attribute) {
        return ((lineAttribute & attribute) == attribute);
    }

    public boolean checkIfContainDate(String line, MyDateTime date) {
        line = line.toUpperCase();
        line = StringUtils.removeAccent(line);
        int year = -1, month = -1, day = -1;

        // Test 1:
        if (line.contains("JAN")) month = 1;
        else if (line.contains("FEB")) month = 2;
        else if (line.contains("MAR")) month = 3;
        else if (line.contains("APR")) month = 4;
        else if (line.contains("MAY")) month = 5;
        else if (line.contains("JUN")) month = 6;
        else if (line.contains("JUL")) month = 7;
        else if (line.contains("AUG")) month = 8;
        else if (line.contains("SEP")) month = 9;
        else if (line.contains("OCT")) month = 10;
        else if (line.contains("NOV")) month = 11;
        else if (line.contains("DEC")) month = 12;

        else if (line.contains("THANG") && line.contains("MOT")) month = 1;
        else if (line.contains("THANG") && line.contains("GIENG")) month = 1;
        else if (line.contains("THANG") && line.contains("HAI")) month = 2;
        else if (line.contains("THANG") && line.contains("BA")) month = 3;
        else if (line.contains("THANG") && line.contains("BON")) month = 4;
        else if (line.contains("THANG") && line.contains("NAM")) month = 5;
        else if (line.contains("THANG") && line.contains("SAU")) month = 6;
        else if (line.contains("THANG") && line.contains("BAY")) month = 7;
        else if (line.contains("THANG") && line.contains("TAM")) month = 8;
        else if (line.contains("THANG") && line.contains("CHIN")) month = 9;
        else if (line.contains("THANG") && line.contains("MUOI")) month = 10;
        else if (line.contains("THANG") && line.contains("MUOI MOT")) month = 11;
        else if (line.contains("THANG") && line.contains("HAI")) month = 12;
        else
            return false;


        // Test 2
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(TextUtils.split(line, " ")));
        for (String string : strings) {
            // remove character ';', ','
            if (string.charAt(0) == ';' ||
                    string.charAt(0) == ',')
                string = string.substring(1);
            if (string.charAt(string.length() -1) == ',' ||
                    string.charAt(string.length() - 1) == ';')
                string = string.substring(0, string.length() - 1);

            // check
            if (TextUtils.isDigitsOnly(string)) {
                int number = Integer.parseInt(string);

                // Check if a possible valid day
                if (number > 31) {
                    year = number;
                }
                else if (number > 0) {
                    day = number;
                }
            }
        }

        date.setDate(day, month, year);
        return date.isValidDay();
    }
}
