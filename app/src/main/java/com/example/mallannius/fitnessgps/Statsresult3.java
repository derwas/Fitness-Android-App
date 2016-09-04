package com.example.mallannius.fitnessgps;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class Statsresult3 extends AppCompatActivity {

    TextView result;
    MyDBManager db;
    String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statsresult3);

        result = (TextView) findViewById(R.id.result3);

        db = new MyDBManager(this);

        Bundle bundle = getIntent().getExtras();
         user = bundle.getString("User");

        db.open();
        getJourneyRows( user);
        db.close();
    }

    public void getJourneyRows( String user) {
        Cursor c  = db.getTop10DistanceByUser(user);

        int n=0;
        if (c.moveToFirst()) {
            do {
                ShowJourney(c);
            }
            while (c.moveToNext());
        }




    }
    public void ShowJourney(Cursor c) {


        Calendar cl1 = Calendar.getInstance(); // Instance of the calendar to store start time
        Calendar cl2 = Calendar.getInstance(); // Instance of the calendar to store the end time

        long stime = c.getLong(11); // get the start time from the cursor
        cl1.setTimeInMillis(stime);// set cl1 to the start time

        long etime = c.getLong(12); // get the end time from the cursor
        cl1.setTimeInMillis(etime);// set cl2 to the end time

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy-HH:mm"); // define the format of the time to be displayed

        long  dtime =c.getLong(9); //get the duration from the cursor
        long hh = TimeUnit.MILLISECONDS.toHours(dtime); // compute the number of hours in the duration
        long mn = TimeUnit.MILLISECONDS.toMinutes(dtime) - hh *60; // compute the number of minutes in the duration
        long s = TimeUnit.MILLISECONDS.toSeconds(dtime) - hh *60 * 60 - mn * 60; // compute the number of seconds in the duration




        result.append(
                "\nStart Time: " + sdf.format(cl1.getTime()) + "\n" +
                        "End Time: " + sdf.format(cl2.getTime()) + "\n" +
                        "Duration: " + hh + " h(s), " + mn + " mn(s) " + s + "s\n" +
                        "Distance: " + distance(c.getDouble(10)) + "\n");

    }

    public String distance (double dist){ // dist is the distance to convert from double expressed in m (meters)
        // --> to convert either to KM or MI ( depending on the user preference)
        String result=""; // result to be sent back
        double d = 0; // the distance after conversion
        String u="n/a"; // unit of measurement after conversion
        boolean dbOpen = db.isOpen();

        if (!dbOpen) {
            db.open();
        }
        Cursor c = db.getUserDetails(user); // get the user details from the database
        if(c.moveToFirst()){ // if there is a result  --> it should be the user
            String unit = c.getString(4); // get the user preference
            if (unit.toLowerCase().contains("km")){ // verify that the user preference contains KM --> it should be either km/hr or mi/hr
                d = dist ; // convert the value from m to km
                u = "km"; // the unit should be km
            }
            else{ // this must be miles
                d = dist  * 0.6214; // convert from m to mi
                u = "miles"; // the unit should be mi
            }
        }
        if (!dbOpen) {
            db.close();
        }
        DecimalFormat df = new DecimalFormat("#.###"); // formatter for getting only 3 numbers after the decimal point
        result = df.format(d) +" " + u; // create the result string ==> value + unit
        return result;
    }
}
