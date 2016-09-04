package com.example.mallannius.fitnessgps;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class Registration extends AppCompatActivity {

    Button button1;
    EditText username;
    EditText password;
    RadioGroup radiogroup1, radiogroup2;
    MyDBManager db;


    /* the string is where to store data when we select something */
    String db_Username;
    String db_Password;
    String db_Gender;
    String db_Preference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        button1 = (Button) findViewById(R.id.button);
        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.pass);
        radiogroup1 = (RadioGroup) findViewById(R.id.gender);
        db_Gender = "Male"; // Store default gender
        radiogroup2 = (RadioGroup) findViewById(R.id.speedunit);
        db_Preference = "KM/Hr"; // Store default preference
        db = new MyDBManager(this);


        radiogroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int id) {
                switch (id) {
                    case R.id.male:
                        db_Gender = "Male";

                        break;
                    case R.id.female:
                        db_Gender = "Female";

                        break;
                }
            }
        });

        radiogroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int id) {
                switch (id) {
                    case R.id.km:
                        db_Preference = "KM/Hr";

                        break;
                    case R.id.miles:
                        db_Preference = "Miles/Hr";

                        break;
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View v) {
                db_Username = username.getText().toString();
                db_Password = password.getText().toString();
                db.open();
                if(db_Username.length()==0 || db_Password.length()==0){
                    Log.d("Registration", "Unable to register a new user!");
                    Bundle resData = new Bundle();
                    resData.putString("result", "Unable to register a new user!");
                    Intent intent = new Intent();
                    intent.putExtras(resData);
                    setResult(RESULT_CANCELED, intent);
                }else {
                    Cursor c = db.getUserDetails(db_Username);
                    if (c.moveToFirst()) {
                        //if there are results from the select query ==> this means the user exists already inthe database
                        Log.d("Registration", "User already registered!");

                        Bundle resData = new Bundle();
                        resData.putString("result", "User already registered!");
                        Intent intent = new Intent();
                        intent.putExtras(resData);
                        setResult(RESULT_CANCELED, intent);
                    } else {
                        //if the user does not exist!
                        long result = db.insertdata(db_Username, db_Password, db_Gender, db_Preference);
                        if (result > 0) {
                            Log.d("Registration", "User successfully registered!");
                            Bundle resData = new Bundle();
                            resData.putString("result", "User successfully registered!");
                            Intent intent = new Intent();
                            intent.putExtras(resData);
                            setResult(RESULT_OK, intent);
                        } else{
                            Log.d("Registration", "Unable to register a new user!");

                            Bundle resData = new Bundle();
                            resData.putString("result", "Unable to register a new user!");
                            Intent intent = new Intent();
                            intent.putExtras(resData);
                            setResult(RESULT_CANCELED, intent);
                        }
                    }


                    db.close();
                }

                /* put finish() to go back to the main screen*/
                finish();

            }
        });

    }
}
