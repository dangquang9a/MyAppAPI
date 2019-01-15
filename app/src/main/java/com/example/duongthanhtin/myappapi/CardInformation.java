package com.example.duongthanhtin.myappapi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CardInformation {
    public CardInformation() {
        this.addresses = new ArrayList<String>();
        this.emails = new ArrayList<String>();
        this.phoneNumbers = new ArrayList<String>();
        this.eventNames = new ArrayList<String>();
        this.times = new ArrayList<Date>();
        this.dates = new ArrayList<Date>();
    }

    private ArrayList<String> eventNames;
    private ArrayList<String> phoneNumbers;
    private ArrayList<String> emails;
    private ArrayList<String> addresses;
    private ArrayList<Date> times;
    private ArrayList<Date> dates;

    public ArrayList<String> getEventNames() {
        return eventNames;
    }

    public void setEventNames(ArrayList<String> eventNames) {
        this.eventNames = eventNames;
    }

    public void addEventName(String eventName) {
        this.eventNames.add(eventName);
    }

    public ArrayList<String> getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void addPhoneNumber(String phoneNumber) {
        this.phoneNumbers.add(phoneNumber);
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public void addEmail(String email) {
        this.emails.add(email);
    }

    public ArrayList<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<String> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(String address){
        this.addresses.add(address);
    }

    public ArrayList<Date> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<Date> times) {
        this.times = times;
    }

    public void addTime(Date time){
        this.times.add(time);
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Date> dates) {
        this.dates = dates;
    }

    public void addDate(Date date){
        this.dates.add(date);
    }
}
