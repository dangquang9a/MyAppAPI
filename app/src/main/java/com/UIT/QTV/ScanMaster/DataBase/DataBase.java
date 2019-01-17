package com.UIT.QTV.ScanMaster.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DataBase extends SQLiteOpenHelper {
    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //Truy vấn không trả kết quả CREATE,INSERT,UPDATE,DELETE
    public void QueryData(String sql)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public void INSERT_SUBJECT(String name,String time,String day, byte[] image)
    {
        SQLiteDatabase database=getWritableDatabase();
        String sql="INSERT INTO Table_works VALUES(null,?,?,?,?)";
        SQLiteStatement statement=database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindString(2,time);
        statement.bindString(3,day);
        statement.bindBlob(4,image);
        statement.executeInsert();
    }


    public Cursor GetData(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }


    //Truy vấn có trả kết quả :SELECT
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
