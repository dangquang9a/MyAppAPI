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
    static int LINE_ATTRIBUTE_PHONE = 2;
    static int LINE_ATTRIBUTE_ADRESS = 4;
    static int LINE_ATTRIBUTE_EMAIL = 8;
    static int LINE_ATTRIBUTE_TIME = 16;
    static int LINE_ATTRIBUTE_DATE = 32;

    public CardInformation getCardInformation() { return this.cardInfo; }

    public void analyze() {
        ArrayList<String> lines = new ArrayList(Arrays.asList(text.split("\n")));
        for (String line : lines) {
            int lineAttribute = analyzeLine(line);

            // Checking the line attribute to find the name/address
            if (!checkLineAttribute(lineAttribute, LINE_ATTRIBUTE_PHONE) &&
                    !checkLineAttribute(lineAttribute, LINE_ATTRIBUTE_PHONE) &&
                    !checkLineAttribute(lineAttribute, LINE_ATTRIBUTE_PHONE) &&
                    !checkLineAttribute(lineAttribute, LINE_ATTRIBUTE_PHONE)) {
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
            // Check if this is phone number
            if (isPhoneNumber(string)) {
                cardInfo.addPhoneNumber(string);
                lineAttribute |= LINE_ATTRIBUTE_PHONE;
            }

            // Check if this is email
            if (isEmail(string)){
                cardInfo.addEmail(string);
                lineAttribute |= LINE_ATTRIBUTE_EMAIL;
            }

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
        return false;
    }

    public boolean isEmail(String string) {
        string = string.toUpperCase();
        boolean result = false;
        if (string.contains("@") && string.contains("."))
            result = true;

        return result;
    }

    public boolean isPhoneNumber(String string) {
        string = string.toUpperCase();
        boolean result = false;
        if (TextUtils.isDigitsOnly(string))
            result = true;

        return result;
    }

    public boolean checkLineAttribute(int lineAttribute, int attribute) {
        return ((lineAttribute & attribute) == attribute);
    }
}
