package com.example.eventbuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddEventActivity extends AppCompatActivity {
    private EditText locationText;
    private EditText dateText;
    private EditText descriptionText;
    private Button button;
    private Spinner spinner;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        locationText = findViewById(R.id.locationEditText);
        dateText = findViewById(R.id.dateEditText);
        descriptionText = findViewById(R.id.descriptionEditText);
        button = findViewById(R.id.button1);
        spinner = findViewById(R.id.spinner4);
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("Tennis");
        arraylist.add("Swimming");
        arraylist.add("Balling");

        databaseReference = FirebaseDatabase.getInstance().getReference();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,arraylist);

        spinner.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEvent();
            }
        });

    }

    private void addNewEvent() {
       String location = locationText.getText().toString();
       String date = dateText.getText().toString();
       String description = descriptionText.getText().toString();
       String spinnerChoice = spinner.getSelectedItem().toString();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
       String eventCreatorName = sharedPref.getString("username", null);

       Event event = new Event(location, date, spinnerChoice, description, eventCreatorName);
       databaseReference.child("events").push().setValue(event);
       Intent intent = new Intent(AddEventActivity.this, MainActivity.class);
       finish();
       startActivity(intent);

    }


}