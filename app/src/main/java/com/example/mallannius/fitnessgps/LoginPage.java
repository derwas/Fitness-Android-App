package com.example.mallannius.fitnessgps;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    Button login, register, database;
    EditText username, password;
    TextView msg;

    String db_username;
    MyDBManager db;

    static final int REGISTRATION_REQUEST = 1;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        db = new MyDBManager(this);

        msg = (TextView) findViewById(R.id.msg);
        login = (Button) findViewById(R.id.button);
        register = (Button) findViewById(R.id.button2);
        database = (Button) findViewById(R.id.button3);
        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.pass);

        // Click and move to the next activity
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();

                Cursor c = db.getUser(username.getText().toString(), password.getText().toString());

                if (c.moveToFirst()) {
                    db.close();
                    Intent intent = new Intent(LoginPage.this, Menu.class);
                    intent.putExtra("Username", username.getText().toString());
                    startActivity(intent);
                } else {
                    db.close();

                    // set error message when the username and/or password is not valid
                    msg.setText("Unable to login: wrong username or password!");
                }
            }
        });

        // Click and move to the next activity
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, Registration.class);
                startActivityForResult(intent,REGISTRATION_REQUEST);
            }
        });

        // Click and move to the next activity
        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, Database.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REGISTRATION_REQUEST){
            //if(resultCode == Registration.RESULT_OK){
            Log.d("Pikatchu", "User successfully registered!");

            Bundle res = data.getExtras();
            String result = res.getString("result");
            System.out.println(result);
            msg.setText(result);


        }

    }
}
