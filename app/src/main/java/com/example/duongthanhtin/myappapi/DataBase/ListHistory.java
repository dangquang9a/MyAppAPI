package com.example.duongthanhtin.myappapi.DataBase;

public class ListHistory
{
    public String Name;
    public String Time;
    public String Date;
    public byte[] Image;

    public ListHistory(String name, String time, String date, byte[] image) {
        Name = name;
        Time = time;
        Date = date;
        Image = image;
    }
}