package com.UIT.QTV.ScanMaster.CalendarCard;

import java.util.ArrayList;

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

    public MyDateTime getTimes() {
        if (times.size()>0)
        return times.get(0);
        else return new MyDateTime(0,0,0,0,0,0);
    }

    public void setTimes(ArrayList<MyDateTime> times) {
        this.times = times;
    }

    public void addTime(MyDateTime time){
        this.times.add(time);
    }

    public MyDateTime getDate() {
        if (dates.size()>0)
            return dates.get(0);

        // Return default date
        else return new MyDateTime(2000, 1, 1, 0, 0, 0);
    }

    public void setDates(ArrayList<MyDateTime> dates) {
        this.dates = dates;
    }

    public void addDate(MyDateTime date){
        this.dates.add(date);
    }
}
