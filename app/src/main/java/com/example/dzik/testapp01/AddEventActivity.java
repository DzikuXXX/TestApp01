package com.example.dzik.testapp01;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.lang.String;
import java.util.concurrent.ExecutionException;

public class AddEventActivity extends AppCompatActivity {

    private String JSON_STRING = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void uploadNewTask(View view) throws ExecutionException, InterruptedException {
        //get event info from client
        EditText etTitle = (EditText) findViewById(R.id.editEventTitle);
        String title = etTitle.getText().toString();
        EditText etDesc = (EditText) findViewById(R.id.editEventDesc);
        String desc = etDesc.getText().toString();
        TextView tvDate = (TextView) findViewById(R.id.textView_setEventDate);
        String date = tvDate.getText().toString();
        TextView tvTime = (TextView) findViewById(R.id.textView_setEventTime);
        String time = tvTime.getText().toString();
        Log.wtf("test","p0");
        new AsyncT().execute(title, desc, date, time).get();
        Log.wtf("test","p1");
        Log.wtf("test",JSON_STRING);
        if(JSON_STRING!=""){
            Log.wtf("test","p2");
            EventDB eventDB = new EventDB(JSON_STRING);
            EventsDatabaseHelper dbHelper = EventsDatabaseHelper.getsInstance(this);
            try {
                dbHelper.addEvent(eventDB);
            } catch (Exception e){
                Log.wtf("db","add eventDB");
                Log.wtf("db",e);
            } finally {
                JSON_STRING = "";
            }
        }
    }

    class AsyncT extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... args) {
            try {
                URL url = new URL("http://0.0.0.0/toleri-backend/web/app_dev.php/events");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                httpURLConnection.setRequestProperty("Accept", "application/json; charset=UTF-8");
                httpURLConnection.connect();

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("title", args[0]);
                jsonObject.put("description", args[1]);
                jsonObject.put("executionTime", args[2] + " " + args[3]);

                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("event", jsonObject);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(jsonObject2.toString());
                wr.flush();
                wr.close();

                int responseCode = httpURLConnection.getResponseCode();
                if(responseCode == 200){
                    InputStream stream = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = reader.readLine();
                    JSON_STRING = line;
                }
                Log.wtf("info_test", String.valueOf(responseCode));

            } catch (MalformedURLException e) {
                Log.wtf("info_test", e.toString());
            } catch (IOException e) {
                Log.wtf("info_test", e.toString());
            } catch (JSONException e) {
                Log.wtf("info_test", e.toString());
            }
            return null;
        }
    }
}
