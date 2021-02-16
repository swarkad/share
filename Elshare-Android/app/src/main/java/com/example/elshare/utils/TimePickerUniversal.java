package com.example.elshare.utils;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Main file for Time Picker.
 */
public class TimePickerUniversal implements View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    private EditText mEditText;
    private Calendar mCalendar;
    private SimpleDateFormat mFormat;
    private boolean withAMPM;

    /**
     * Constructor
     * @param editText your EditText
     * @param withAMPM true if you want AM/PM, false otherwise.
     */
    public TimePickerUniversal(EditText editText, boolean withAMPM) {
        this.mEditText = editText;
        mEditText.setOnFocusChangeListener(this);
        mEditText.setOnClickListener(this);
        this.withAMPM = withAMPM;
    }

    public TimePickerUniversal() {
      //default Constructor
    }

    /**
     * method
     * @param editText your EditText
     * @param withAMPM true if you want AM/PM, false otherwise.
     */
    public void setTimePickerUniversal(EditText editText, boolean withAMPM) {
        this.mEditText = editText;
        mEditText.setOnFocusChangeListener(this);
        mEditText.setOnClickListener(this);
        this.withAMPM = withAMPM;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            showPicker(view);
        }
    }

    @Override
    public void onClick(View view) {
        showPicker(view);
    }

    private void showPicker(View view) {
        if (mCalendar == null)
            mCalendar = Calendar.getInstance();

        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);

        new TimePickerDialog(view.getContext(), this, hour, minute, !withAMPM).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);

        if (mFormat == null)
            mFormat = new SimpleDateFormat(withAMPM ? "hh:mm a" : "HH:mm", Locale.getDefault());

        this.mEditText.setText(mFormat.format(mCalendar.getTime()));
    }

}