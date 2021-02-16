package com.example.elshare.utils;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DatePickerUniversal implements DatePickerDialog.OnDateSetListener{

    final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
    Context context;
    EditText editText;
    AppCompatActivity currentActivity;
    String selectedDate = ""; // Format : yyyy-mm-dd

    public DatePickerUniversal(Context context, EditText editText, AppCompatActivity currentActivity) {
        this.context = context;
        this.editText = editText;
        this.currentActivity = currentActivity;
    }


    public void setDateOnEditText(ArrayList<Integer> disableDaysList) {
        DatePickerDialog datePickerDialog ;
        int Year, Month, Day;
        Calendar calendar ;
        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);



        datePickerDialog = DatePickerDialog.newInstance(this, Year, Month, Day);
        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setTitle("Date Picker");


        // Setting Min Date to today date
        Calendar min_date_c = Calendar.getInstance();
        datePickerDialog.setMinDate(min_date_c);

        // Setting Max Date to next 2 month
        Calendar max_date_c = Calendar.getInstance();
        max_date_c.set(Calendar.MONTH, Month + 2);
        datePickerDialog.setMaxDate(max_date_c);

        //Disable all SUNDAYS and SATURDAYS between Min and Max Dates
        for (Calendar loopdate = min_date_c; min_date_c.before(max_date_c); min_date_c.add(Calendar.DATE, 1), loopdate = min_date_c) {
            int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
            //if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
            if (disableDaysList.contains(dayOfWeek)) {
                Calendar[] disabledDays =  new Calendar[1];
                disabledDays[0] = loopdate;
                datePickerDialog.setDisabledDays(disabledDays);
            }
        }

        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialogInterface) {

                Toast.makeText(context, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        datePickerDialog.show(currentActivity.getSupportFragmentManager(), "Date Picker Dialog");

    }


    @Override
    public void onDateSet(DatePickerDialog view, int currentYear, int currentMonth, int currentDay) {
        selectedDate = ""+ currentYear + "-" + (currentMonth + 1) + "-" + currentDay;
        Toast.makeText(context, selectedDate, Toast.LENGTH_LONG).show();

        Date date1 = new Date(currentYear, currentMonth, currentDay - 1);
        String dayOfWeek = simpledateformat.format(date1);
//        editText.setText(dayOfWeek + ", "+ MONTHS[currentMonth] + " " + currentDay + ", " + currentYear);
        editText.setText(dayOfWeek +"," +selectedDate);
    }

    public String getSelectedDate(){
        return selectedDate;
    }

}
