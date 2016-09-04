package com.example.mallannius.fitnessgps;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Database extends AppCompatActivity {

    TextView results;
    MyDBManager db;
    LinearLayout container;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database);

        container = (LinearLayout) findViewById(R.id.data);
        results = (TextView) findViewById(R.id.database);

        db = new MyDBManager(this);

        //// Open Database and get rows
        db.open();

        //Get list of users
        results.append("\n List of users \n");

        getRows();

        //Get list of journeys
        results.append("\n List of journeys \n");
        getRowsJourney();

        db.close();
    }


    //---Get all user details rows---
    public void getRows() {
        Cursor c = db.getAllTask();
        if (c.moveToFirst()) {
            do {
                ShowTask(c);

            }
            while (c.moveToNext());
        }
    }

    //---Get all journey details rows---
    public void getRowsJourney() {
        Cursor c = db.getAllJourney();
        if (c.moveToFirst()) {
            do {
                ShowJourney(c);

            }
            while (c.moveToNext());
        }
    }
    //Show user details
    public void ShowTask(Cursor c) {

        TextView text = new TextView(this);

        results.append("\n" + "id: " + c.getString(0) + "\n" +
                "Username: " + c.getString(1) + "\n" +
                "Password: " + c.getString(2) + "\n" +
                "Gender: " + c.getString(3) + "\n" +
                "Preference: " + c.getString(4) + "\n");

        container.addView(text);

    }
    //Show journey details
    public void ShowJourney(Cursor c) {

        TextView text = new TextView(this);

        results.append("\n" + "id: " + c.getString(0) + "\n" +
                "User: " + c.getString(1) + "\n" +
                "Mode: " + c.getString(2) + "\n" +
                "Start_Lat: " + c.getString(3) + "\n" +
                "Start_Long: " + c.getString(4) + "\n" +
                "Interim_Location: " + c.getString(5) + "\n"+
                "End_Lat: " + c.getString(6) + "\n"+
                "End_Long: " + c.getString(7) + "\n"+
                "Max_speed: " + c.getString(8) + "\n"+
                "Duration: " + c.getString(9) + "\n"+
                "Distance: " + c.getString(10) + "\n"+
                "StartTime: " + c.getString(11) + "\n"+
                "EndTime: " + c.getString(12) + "\n");



        container.addView(text);

    }
}
