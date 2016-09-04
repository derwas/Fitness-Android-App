package com.example.mallannius.fitnessgps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBManager {


    //Create KEY_ROWID for each element of the first table

    public static final String KEY_ROWID = "_id";
    public static final String KEY_USERNAME = "Username";
    public static final String KEY_PASSWORD = "Password";
    public static final String KEY_GENDER = "Gender";
    public static final String KEY_PREFERENCE = "Preference";


// Assign name to Database and Database table

    private static final String DATABASE_NAME = "Journey";
    private static final String DATABASE_TABLE = "Login_Details";
    private static final String DATABASE_TABLE2 = "Journey_Details";
    private static final int DATABASE_VERSION = 1;

    //Table 1
    private static final String DATABASE_CREATE = "create table Login_Details " +
            "(_id integer primary key autoincrement, " +
            "Username text not null unique, " + "Password text not null, " +
            "Gender text not null, " + "Preference text not null )";

    //Create KEY_ROWID for each element o the second table
    public static final String KEY_ID = "_id";
    public static final String KEY_USER = "User";
    public static final String KEY_MODE = "Mode";
    public static final String KEY_STARTLAT = "Start_lat";
    public static final String KEY_STARTLONG = "Start_long";
    public static final String KEY_INTERIMLOCATION = "Interim_location";
    public static final String KEY_ENDLAT = "End_lat";
    public static final String KEY_ENDLONG = "End_long";
    public static final String KEY_MAXSPEED = "Max_speed";
    public static final String KEY_DURATION = "Duration";
    public static final String KEY_DISTANCE = "Distance";
    public static final String KEY_STARTTIME = "StartTime";
    public static final String KEY_ENDTIME = "EndTime";




    //Table 2
    private static final String DATABASE_CREATE2 = "create table Journey_Details " +
            "(_id integer primary key autoincrement, " +
            "User text not null, " +
            "Mode text not null, "
            + "Start_lat double not null, " + "Start_long double not null, " +
            "Interim_location text not null, "
            + "End_lat double not null, " + "End_long double not null, " +
            "Max_speed double not null, " + "Duration long not null, " + "Distance double not null, "
            + "StartTime long not null, " + "EndTime long not null)";


    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public boolean isOpen(){
        if (db == null){
            return false;
        }else {
            return db.isOpen();
        }
    }
    public MyDBManager(Context ctx) {

        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }


    //
    private static class DatabaseHelper extends SQLiteOpenHelper {

        //
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME,
                    null, DATABASE_VERSION);
        }


        @Override
        //
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE2);
        }

        @Override

        //
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            // whatever is to be changed on dB structure

        }
    }

    public MyDBManager open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    // Insert new data of table 1
    public long insertdata(String Username, String Password, String Gender, String Preference)
    {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERNAME, Username);
        initialValues.put(KEY_PASSWORD, Password);
        initialValues.put(KEY_GENDER, Gender);
        initialValues.put(KEY_PREFERENCE, Preference);
        return db.insert(DATABASE_TABLE, null, initialValues);

    }

    // Insert new data of table 2
    public long insertjourneydata(String User, String Mode, double StartLat, double StartLong , String Interimlocation, double EndLat, double EndLong, double Maxspeed, long Duration, double Distance , long StartTime, long EndTime )
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USER, User);
        initialValues.put(KEY_MODE, Mode);
        initialValues.put(KEY_STARTLAT, StartLat);
        initialValues.put(KEY_STARTLONG, StartLong);
        initialValues.put(KEY_INTERIMLOCATION, Interimlocation);
        initialValues.put(KEY_ENDLAT, EndLat);
        initialValues.put(KEY_ENDLONG, EndLong);
        initialValues.put(KEY_MAXSPEED, Maxspeed);
        initialValues.put(KEY_DURATION, Duration);
        initialValues.put(KEY_DISTANCE, Distance);
        initialValues.put(KEY_STARTTIME, StartTime);
        initialValues.put(KEY_ENDTIME, EndTime);


        return db.insert(DATABASE_TABLE2, null, initialValues);
    }

    //Delete data per row
    public boolean deleteTask(long rowId) {
        //
        return db.delete(DATABASE_TABLE, KEY_ROWID +
                "=" + rowId, null) > 0;
    }

    //
    public Cursor getAllTask() {
        return db.query(false, DATABASE_TABLE, new String[]{
                        KEY_ROWID,
                        KEY_USERNAME,
                        KEY_PASSWORD,
                        KEY_GENDER,
                        KEY_PREFERENCE
                },
                null,
                null,
                null,
                null,
                null, null);
    }
    public Cursor getAllJourney() {
        return db.query (false, DATABASE_TABLE2, new String[]{
                        KEY_ID,
                        KEY_USER,
                        KEY_MODE,
                        KEY_STARTLAT,
                        KEY_STARTLONG,
                        KEY_INTERIMLOCATION,
                        KEY_ENDLAT,
                        KEY_ENDLONG,
                        KEY_MAXSPEED,
                        KEY_DURATION,
                        KEY_DISTANCE,
                        KEY_STARTTIME,
                        KEY_ENDTIME
                },
                null,
                null,
                null,
                null,
                null,null);
    }

    public Cursor getUserDetails(String name) {
        return db.query (false, DATABASE_TABLE, new String[]{
                        KEY_ROWID,
                        KEY_USERNAME,
                        KEY_PASSWORD,
                        KEY_GENDER,
                        KEY_PREFERENCE
                },
                KEY_USERNAME + " = \"" + name + "\"" ,
                null,
                null,
                null,
                null,null);
    }

    public Cursor getUser(String name, String passwd) {
        return db.query (false, DATABASE_TABLE, new String[]{
                        KEY_ROWID,
                        KEY_USERNAME,
                        KEY_PASSWORD,
                        KEY_GENDER,
                        KEY_PREFERENCE
                },
                KEY_USERNAME + "=\"" + name + "\" AND " + KEY_PASSWORD +"=\""+passwd+"\"",
                null,
                null,
                null,
                null,null);
    }

    // Get poll results by selected Gender

    public Cursor getdatabymode(String Mode, String user) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {
                                KEY_ID,
                                KEY_USER,
                                KEY_MODE,
                                KEY_STARTLAT,
                                KEY_STARTLONG,
                                KEY_INTERIMLOCATION,
                                KEY_ENDLAT,
                                KEY_ENDLONG,
                                KEY_MAXSPEED,
                                KEY_DURATION,
                                KEY_DISTANCE,
                                KEY_STARTTIME,
                                KEY_STARTTIME

                        },
                        KEY_MODE +" = \"" + Mode +"\" and "+KEY_USER +" = \""+user+"\"",
                        null,
                        null,
                        null,
                        null,
                        null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    public Cursor getdatabyuser(String user) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {
                                KEY_ID,
                                KEY_USER,
                                KEY_MODE,
                                KEY_STARTLAT,
                                KEY_STARTLONG,
                                KEY_INTERIMLOCATION,
                                KEY_ENDLAT,
                                KEY_ENDLONG,
                                KEY_MAXSPEED,
                                KEY_DURATION,
                                KEY_DISTANCE,
                                KEY_STARTTIME,
                                KEY_ENDTIME

                        },
                       KEY_USER +" = \""+user+"\"",
                        null,
                        null,
                        null,
                        null,
                        null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    public Cursor getTop10DurationByUser(String user) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {
                                KEY_ID,
                                KEY_USER,
                                KEY_MODE,
                                KEY_STARTLAT,
                                KEY_STARTLONG,
                                KEY_INTERIMLOCATION,
                                KEY_ENDLAT,
                                KEY_ENDLONG,
                                KEY_MAXSPEED,
                                KEY_DURATION,
                                KEY_DISTANCE,
                                KEY_STARTTIME,
                                KEY_ENDTIME

                        },
                        KEY_USER +" = \""+user+"\"",
                        null,
                        null,
                        null,
                        KEY_DURATION +" DESC",
                        "10");
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor getTop10DistanceByUser(String user) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {
                                KEY_ID,
                                KEY_USER,
                                KEY_MODE,
                                KEY_STARTLAT,
                                KEY_STARTLONG,
                                KEY_INTERIMLOCATION,
                                KEY_ENDLAT,
                                KEY_ENDLONG,
                                KEY_MAXSPEED,
                                KEY_DURATION,
                                KEY_DISTANCE,
                                KEY_STARTTIME,
                                KEY_ENDTIME

                        },
                        KEY_USER +" = \""+user+"\"",
                        null,
                        null,
                        null,
                        KEY_DISTANCE +" DESC",
                        "10");
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

}







