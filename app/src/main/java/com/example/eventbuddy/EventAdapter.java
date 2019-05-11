package com.example.eventbuddy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class EventAdapter extends BaseAdapter {
    private Activity activity;
    private DatabaseReference databaseReference;
    private String displayname;
    private ArrayList<DataSnapshot> snapshotArrayList;

    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            snapshotArrayList.add(dataSnapshot);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    public EventAdapter(Activity activity, DatabaseReference ref, String name) {

        this.activity = activity;
        displayname = name;
        // common error: typo in the db location. Needs to match what's in MainChatActivity.
        databaseReference = ref.child("events");
        databaseReference.addChildEventListener(childEventListener);

        snapshotArrayList = new ArrayList<>();
    }

    private static class ViewHolder{
        TextView location;
        TextView date;
        TextView listAct;
        TextView description;
        TextView authorName;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return snapshotArrayList.size();
    }

    @Override
    public Event getItem(int position) {

        DataSnapshot snapshot = snapshotArrayList.get(position);
        return snapshot.getValue(Event.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_row, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.date = convertView.findViewById(R.id.date);
            holder.description= convertView.findViewById(R.id.description);
            holder.listAct = convertView.findViewById(R.id.listAct);
            holder.location = convertView.findViewById(R.id.location);
            holder.authorName = convertView.findViewById(R.id.authorName);
            holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            convertView.setTag(holder);

        }

        final Event event = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.authorName.setText(event.getAuthor());
        holder.location.setText(event.getLocation());
        holder.listAct.setText(event.getListAct());
        holder.date.setText(event.getDate());
        holder.description.setText(event.getDescription());

        return convertView;
    }

    void cleanup() {

        databaseReference.removeEventListener(childEventListener);
    }

}
