package com.example.dzik.testapp01;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView tv1= (TextView) getActivity().findViewById(R.id.textView_setEventTime);
        if(hourOfDay<10){
            tv1.setText("0" + String.valueOf(hourOfDay) + ":");
        }
        else{
            tv1.setText(String.valueOf(hourOfDay) + ":");
        }
        if(minute<10){
            tv1.setText(tv1.getText() + "0" + String.valueOf(minute) + ":00");
        }
        else{
            tv1.setText(tv1.getText() + String.valueOf(minute) + ":00");
        }
    }
}
