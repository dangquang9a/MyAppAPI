package com.example.duongthanhtin.myappapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Support extends AppCompatActivity {


    private EditText Name,Content;
    private Button Send, Cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        Name=findViewById(R.id.input_name);
        Content=findViewById(R.id.input_content);

        Send=findViewById(R.id.btn_send);
        Cancel=findViewById(R.id.btn_cancel);


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strName = Name.getText().toString();
                final String strContent = Content.getText().toString();
                if(strName.equals("") || strContent.equals(""))
                {
                    Toast.makeText(Support.this,"Please insert data to both field",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"dangquang9a@gmail.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "ScanThisCard Support");
                    i.putExtra(Intent.EXTRA_TEXT, "I'm " + Name.getText() + ", ơlease help me with: " + Content.getText());
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(Support.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
