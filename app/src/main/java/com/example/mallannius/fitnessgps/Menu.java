package com.example.mallannius.fitnessgps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {
    Button startJourney, displayJourneys;
    TextView msg; // where to display the name of the user

    /* the string is where to store data when we select something */
    String db_username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        msg = (TextView) findViewById(R.id.msg);
        startJourney = (Button) findViewById(R.id.recordingmode);
        displayJourneys = (Button) findViewById(R.id.journey);



        Bundle bundle = getIntent().getExtras();
        db_username = (bundle.getString("Username"));

        // Set Welcome message to the user who logged in
        msg.setText("Welcome "+db_username);

        // Click and move to the next activity
        startJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Recordingmode.class);
                intent.putExtra("Username", db_username);
                startActivity(intent);
            }
        });

        // Click and move to the next activity
        displayJourneys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Searchjourney.class);
                intent.putExtra("Username", db_username);

                startActivity(intent);
            }
        });




    }
}

