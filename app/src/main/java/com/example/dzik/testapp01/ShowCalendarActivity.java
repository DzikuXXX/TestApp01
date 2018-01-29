package com.example.dzik.testapp01;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowCalendarActivity extends AppCompatActivity {

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_calendar);

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);
        Date currMonth = new Date();
        actionBar.setTitle(dateFormatMonth.format(currMonth));

        String stringTime = null;
        long epochTime = 0;
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        EventsDatabaseHelper dbHelper = EventsDatabaseHelper.getsInstance(this);
        List<EventDB> eventDBs = dbHelper.getAllEvents();

        for (int i = 0; i< eventDBs.size(); i++){
            try {
                //convert date and time to epoch
                stringTime = eventDBs.get(i).getExecutionTime();
                date = simpleDateFormat.parse(stringTime);
                epochTime = date.getTime();

                //add event to calendar

                Event event = new Event(Color.RED, epochTime, "Title: " +
                        eventDBs.get(i).getTitle() +
                        "\nDescription: " +
                        eventDBs.get(i).getDescription());
                Log.wtf("calendar event", event.toString());
                compactCalendar.addEvent(event, true);
            } catch (Exception e) {
                Log.wtf("ShowCalendar",e.toString());
            }
        }

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendar.getEvents(dateClicked);
                TextView tv = (TextView) findViewById(R.id.eventDescription);
                tv.setText("");
                String eventDate = null;
                String eventInfo = "";
                for (int i=0; i<events.size(); i++){
                    try {
                        Date date = new Date(events.get(i).getTimeInMillis());
                        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                        String dateFormatted = formatter.format(date);
                        eventDate = "Time: " + dateFormatted;
                        eventInfo = events.get(i).getData().toString();
                        tv.setText(tv.getText() + eventDate + "\n" + eventInfo + "\n\n");
                    } catch (Exception e) {
                        Log.wtf("ShowCalendar", e.toString());
                    }
                }
                //Log.wtf("calendar", "Day: " + dateClicked + " Event: " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }
}
