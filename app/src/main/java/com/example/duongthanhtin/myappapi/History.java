package com.example.duongthanhtin.myappapi;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.duongthanhtin.myappapi.DataBase.DataBase;
import com.example.duongthanhtin.myappapi.DataBase.ListAdapter;
import com.example.duongthanhtin.myappapi.DataBase.ListHistory;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    ListView listView;
    ArrayList<ListHistory> array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = (ListView)findViewById(R.id.idListViewHistory);

        array= new ArrayList<ListHistory>();

        //DataBase
        final DataBase db = new DataBase(getApplicationContext(),"Subject_History",null,1);
        Cursor listHistory = db.GetData("Select * from Table_works");
        while(listHistory.moveToNext())
        {
            array.add(new ListHistory(
                    listHistory.getString(1),
                    listHistory.getString(2),
                    listHistory.getString(3),
                    listHistory.getBlob(4)
            ));
        }
        ListAdapter adapter = new ListAdapter(getApplicationContext(),R.layout.activity_interface_row,array);
        listView.setAdapter(adapter);
    }
}
