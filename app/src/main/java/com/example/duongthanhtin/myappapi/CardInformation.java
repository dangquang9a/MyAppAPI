package com.example.duongthanhtin.myappapi;

import java.util.ArrayList;
import java.util.Calendar;

public class CardInformation {
    public CardInformation() {
        this.addresses = new ArrayList<String>();
        this.eventNames = new ArrayList<String>();
        this.times = new ArrayList<MyDateTime>();
        this.dates = new ArrayList<MyDateTime>();
    }

    private ArrayList<String> eventNames;
    private ArrayList<String> addresses;
    private ArrayList<MyDateTime> times;
    private ArrayList<MyDateTime> dates;

    public ArrayList<String> getEventNames() {
        return eventNames;
    }

    public void setEventNames(ArrayList<String> eventNames) {
        this.eventNames = eventNames;
    }

    public void addEventName(String eventName) {
        this.eventNames.add(eventName);
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

    public ArrayList<MyDateTime> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<MyDateTime> times) {
        this.times = times;
    }

    public void addTime(MyDateTime time){
        this.times.add(time);
    }

    public ArrayList<MyDateTime> getDates() {
        return dates;
    }

    public void setDates(ArrayList<MyDateTime> dates) {
        this.dates = dates;
    }

    public void addDate(MyDateTime date){
        this.dates.add(date);
    }
}
