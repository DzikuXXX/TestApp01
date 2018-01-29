package com.example.dzik.testapp01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class GetAllEventsFromDatabaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_events_from_database);

        EventsDatabaseHelper dbHelper = EventsDatabaseHelper.getsInstance(this);
        List<EventDB> eventDBs = dbHelper.getAllEvents();
        TextView tv = (TextView) findViewById(R.id.textView_dbContent);
        tv.setText("");
        for (int i = 0; i< eventDBs.size(); i++){
            tv.setText(tv.getText() + eventDBs.get(i).toString() + "\n");

            Log.wtf("DB" + i + ":", eventDBs.get(i).toString());
        }
    }

    public void deleteDataBaseContent(View view){
        EventsDatabaseHelper databaseHelper = EventsDatabaseHelper.getsInstance(this);
        databaseHelper.deleteAllEvents();
        TextView tv = (TextView) findViewById(R.id.textView_dbContent);
        tv.setText("Table is empty");
    }
}
