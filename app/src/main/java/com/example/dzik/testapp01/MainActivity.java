package com.example.dzik.testapp01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void oauth(View view){
        Intent intent = new Intent(this, OauthTestActivity.class);
        startActivity(intent);
    }

    public void addEvent(View view){
        Intent intent = new Intent (this, AddEventActivity.class);
        startActivity(intent);
    }

    public void getAllEvents(View view){
        Intent intent = new Intent(this, GetAllEventsActivity.class);
        startActivity(intent);
    }

    public void getAllEventsFromDatabase(View view){
        Intent intent = new Intent(this, GetAllEventsFromDatabaseActivity.class);
        startActivity(intent);
    }

    public void showCalendar(View view){
        Intent intent = new Intent(this, ShowCalendarActivity.class);
        startActivity(intent);
    }
}
