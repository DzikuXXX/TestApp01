package com.example.dzik.testapp01;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener{

    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day){
        TextView tv1= (TextView) getActivity().findViewById(R.id.textView_setEventDate);
        tv1.setText(year + "-");
        if(month<10){
            tv1.setText(tv1.getText() + "0");
        }
        tv1.setText(tv1.getText() + String.valueOf(month) + "-");
        if(day<10){
            tv1.setText(tv1.getText() + "0");
        }
        tv1.setText(tv1.getText() + String.valueOf(day));
    }

}
