package com.example.mallannius.fitnessgps;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class Statsresult1 extends AppCompatActivity {

    TextView result;

    ListView list;

    MyDBManager db;

    String user;


    ArrayList<Double> Start_Lat;

    ArrayList<Double> Start_Lon;

    ArrayList<String> Stop_Distance;

    ArrayList<String> Stop_Duration;

    ArrayList<Double> Stop_Lat;

    ArrayList<Double> Stop_Lon;

    ArrayList<String> Start_Date;

    ArrayList<ArrayList<LatLng>> interimLoc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statsresult1);

        Start_Lat = new ArrayList<>();
        Start_Lon = new ArrayList<>();

        Stop_Lat = new ArrayList<>();
        Stop_Lon = new ArrayList<>();
        Stop_Distance = new ArrayList<>();
        Stop_Duration = new ArrayList<>();

        Start_Date = new ArrayList<>();

        result = (TextView) findViewById(R.id.result);

        db = new MyDBManager(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            result.setText("Selected category is: "+bundle.getString("Mode")+"\n" );
        }
        user = bundle.getString("User");
        db.open();
        getJourneyRows(bundle.getString("Mode"), bundle.getString("User"));
        db.close();

        list = (ListView) findViewById(R.id.listcandresults);
        list.setAdapter(new MyCustomAdapter(Statsresult1.this, R.layout.row, Start_Lat, Start_Lon));


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                double lat = Start_Lat.get(position);
                double lon = Start_Lon.get(position);

                Intent i = new Intent(Statsresult1.this, showMap.class);
                i.putExtra("start_lat", lat);
                i.putExtra("start_lon", lon);

                i.putExtra("stop_lat", Stop_Lat.get(position));
                i.putExtra("stop_lon", Stop_Lon.get(position));
                startActivity(i);
            }
        });
    }



    public class MyCustomAdapter extends ArrayAdapter<Double> {

        ArrayList<Double> Adapter_Lat;
        ArrayList<Double> Adapter_Lon;
    // Creating our own adaptor because we want to be able to customise each row
    public MyCustomAdapter(Context context, int textViewResourceId,ArrayList<Double> a, ArrayList<Double> b)
    {
        super(context, textViewResourceId,a);
        // TODO Auto-generated constructor stub
        Adapter_Lat=a;
        Adapter_Lon=b;

    }

    @Override
    // This getview method is called each time a row needs to be formatted for the list

    public View getView(int position, View convertView, ViewGroup parent)
    {

        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater=getLayoutInflater();
        View row=inflater.inflate(R.layout.row, parent, false);

        TextView date=(TextView)row.findViewById(R.id.date);
        TextView mileage=(TextView)row.findViewById(R.id.mileage);
        TextView duration=(TextView)row.findViewById(R.id.duration);


        ImageView icon = (ImageView) row.findViewById(R.id.icon);
        icon.setImageResource(R.drawable.map);

        date.setText( Start_Date.get(position));
        mileage.setText(Stop_Distance.get(position));
        duration.setText(Stop_Duration.get(position));

        return row;
    }
}

    public void getJourneyRows(String Mode, String user) {
        Cursor c = null;
        if(Mode.equals("All")){
            c = db.getdatabyuser(user);
        }
        else {
            c = db.getdatabymode(Mode, user);
        }
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
        cl2.setTimeInMillis(etime);// set cl2 to the end time

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy-HH:mm"); // define the format of the time to be displayed

        long  dtime =c.getLong(9); //get the duration from the cursor
        long hh = TimeUnit.MILLISECONDS.toHours(dtime); // compute the number of hours in the duration
        long mn = TimeUnit.MILLISECONDS.toMinutes(dtime) - hh *60; // compute the number of minutes in the duration
        long s = TimeUnit.MILLISECONDS.toSeconds(dtime) - hh *60 * 60 - mn * 60; // compute the number of seconds in the duration

        Start_Date.add((sdf.format(cl1.getTime())));
        Start_Lat.add((c.getDouble(3)));
        Start_Lon.add((c.getDouble(4)));

        Stop_Lat.add((c.getDouble(6)));
        Stop_Lon.add((c.getDouble(7)));
        Stop_Duration.add((hh+" h(s), " + mn +" mn(s) " + s + "s"));
        Stop_Distance.add((distance(c.getDouble(10))));

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
                d = dist * 0.6214; // convert from m to miles
                u = "miles"; // the unit should be miles
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


