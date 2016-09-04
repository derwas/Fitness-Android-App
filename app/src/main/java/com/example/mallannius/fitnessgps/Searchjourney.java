package com.example.mallannius.fitnessgps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Searchjourney extends AppCompatActivity {


    Button category, time, milage;
    Spinner mode;

    /* the string is where to store data when we select something */
    String db_username;
    String db_mode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchjourney);

        category = (Button) findViewById(R.id.cat);
        time = (Button) findViewById(R.id.time);
        milage = (Button) findViewById(R.id.mile);
        mode = (Spinner) findViewById(R.id.category);

        Bundle bundle = getIntent().getExtras();
        db_username = (bundle.getString("Username"));


        // Create a list of strings for the Spinner
        List<String> list1 = new ArrayList<String>();
        list1.add("All");
        list1.add("Walking");
        list1.add("Cycling");
        list1.add("Jogging");
        list1.add("Driving");

        ArrayAdapter<String> dataadapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);

        dataadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mode.setAdapter(dataadapter1);

        mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                db_mode = mode.getSelectedItem().toString();

                Toast.makeText(parent.getContext(),
                        parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchjourney.this, Statsresult1.class);
                intent.putExtra("Mode", db_mode);
                intent.putExtra("User", db_username);

                startActivity(intent);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchjourney.this, Statsresult2.class);
                intent.putExtra("User", db_username);

                startActivity(intent);
            }
        });

        milage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchjourney.this, Statsresult3.class);
                intent.putExtra("User", db_username);

                startActivity(intent);
            }
        });


            }
        }


