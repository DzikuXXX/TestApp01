package com.example.dzik.testapp01;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.json.JSONArray;

public class GetAllEventsActivity extends AppCompatActivity {

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_events);
        try {
            new AsyncT().execute().get();
            JSONArray jsonArray = new JSONArray(JSON_STRING);
            EventsDatabaseHelper dbHelper = EventsDatabaseHelper.getsInstance(this);
            for (int i=0; i<jsonArray.length(); i++){
                EventDB eventDB = new EventDB(jsonArray.getJSONObject(i));
                Log.wtf("eventDB" + String.valueOf(i) + ": ", eventDB.toString());
                dbHelper.addEvent(eventDB);
            }
        } catch (Exception e){
            Log.wtf("get all events", e.toString());
        }
    }

    class AsyncT extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            try{
                URL url = new URL("http://0.0.0.0/toleri-backend/web/app_dev.php/events");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = bufferedReader.readLine();
                JSON_STRING = line;
                Log.wtf("all events", line);
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
