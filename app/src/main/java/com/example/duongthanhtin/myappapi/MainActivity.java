package com.example.duongthanhtin.myappapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView CreditMoneyCard, SupportCard,HistoryCard, CalendarCard;
    private Button ButtonExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ID CARD
        CreditMoneyCard =(CardView)findViewById(R.id.idCreditMoney);
        SupportCard     = (CardView)findViewById(R.id.idSupport);
        HistoryCard = (CardView)findViewById(R.id.idHistory);
        CalendarCard = (CardView)findViewById(R.id.idCalendar);
        //ID BUTTON
        ButtonExit =(Button)findViewById(R.id.btnExit);


        //ADD CLICK CARD
        CreditMoneyCard.setOnClickListener(this);
        SupportCard.setOnClickListener(this);
        HistoryCard.setOnClickListener(this);
        CalendarCard.setOnClickListener(this);

        //ADD CLICK BUTTON
        ButtonExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId())
        {
            case R.id.idCreditMoney: i = new Intent(this,CreditMoney.class); startActivity(i); break;
            case R.id.idSupport: i = new Intent(this,Support.class); startActivity(i); break;
            case R.id.idHistory: i = new Intent(this,History.class); startActivity(i); break;
            case R.id.idCalendar: i = new Intent(this,Calendar.class); startActivity(i); break;
            case R.id.btnExit: finish(); break;
            default: break;
        }
    }
}