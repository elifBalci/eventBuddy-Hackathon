package com.example.eventbuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

        private String displayName;
        private ImageButton sendButton;
        private ListView chatListView;
        private ImageView addButton;
        private EventAdapter eventAdapter;


        private DatabaseReference databaseReference;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            setupDisplayName();

            databaseReference = FirebaseDatabase.getInstance().getReference();

            addButton = findViewById(R.id.eventAddButton2);
            chatListView = findViewById(R.id.chat_list_view);

        }
        public void addEventHandler(View view) {
            Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
            finish();
            startActivity(intent);
        }

        private void setupDisplayName() {
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            displayName = sharedPref.getString("username", null);
            if (displayName == null) {
                displayName = "anonymous";
            }
        }

    @Override
    protected void onStart() {
        super.onStart();
        eventAdapter= new EventAdapter(this, databaseReference, displayName);
        chatListView.setAdapter(eventAdapter);
        chatListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        chatListView.setStackFromBottom(true);
    }
    @Override
    protected void onStop() {
        super.onStop();
        eventAdapter.cleanup();
    }



}

