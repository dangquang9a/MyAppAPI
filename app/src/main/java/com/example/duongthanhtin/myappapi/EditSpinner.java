package com.example.duongthanhtin.myappapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditSpinner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.ThemeOverlay_Material_Dialog);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_spinner_item);
    }
}
