package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button button;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button1);
        spinner = findViewById(R.id.spinner4);
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("Tennis");
        arraylist.add("Swimming");
        arraylist.add("Balling");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
  android.R.layout.simple_dropdown_item_1line,arraylist);

                spinner.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}
