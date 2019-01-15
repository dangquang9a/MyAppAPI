package com.example.duongthanhtin.myappapi;

/*
    Class describe date and time without validating.
 */
public class MyDateTime {
    private int year = -1;
    private int month = -1;
    private int day = -1;
    private int hour = -1;
    private int minute = -1;
    private int second = -1;

    public MyDateTime() {}

    public MyDateTime(int year, int month, int day, int hour, int minute, int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public MyDateTime(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public void setTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public void setDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }


    public boolean isValidDay() {
        if (month <= 0 || month > 12)    return false;
        if (day <= 0 || day > 31)        return false;

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

    public boolean isValidTime() {
        if (hour < 0 || hour > 23)
            return false;

        if (minute < 0 || minute > 59)
            return false;

        if (second < 0 || second > 59)
            return false;

        return true;
    }


    @Override
    public String toString() {
        if (this.isValidDay())
            return day + "/" + month + "/" + year;

        if (this.isValidTime())
            return hour + ":" + minute;

        return "cannot found";
    }
}
