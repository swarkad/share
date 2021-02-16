package com.example.elshare;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.elshare.utils.SingletonRetrofit;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import commertial_pojo.commertial_charger_amphere;
import commertial_pojo.commertial_socket_amphere;
import datamodel.APIInterface;
import datamodel.Socket_commertial;
import datamodel.commertial_board;
import datamodel.commertial_board_socket;
import datamodel.commertial_charger_model;
import datamodel.commertial_charger_type;
import datamodel.commertial_connector;
import datamodel.commertial_power_output;
import datamodel.commertial_rate;
import datamodel.commertial_rate_socket;
import datamodel.commertial_voltage;
import datamodel.commertial_voltage_socket;
import datamodel.show_commertial;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class edit_commertial_listing extends Activity
{
    String newString;
    EditText address_1,address_2,country_txt,state_text,landemark_txt,zip_txt,city_txt;
    EditText spinner_edit;
    int id;
    String final_board,final_rate,final_voltage,final_amphere,final_power;
    EditText start_time_txt,end_time_txt;
    ImageButton delete_row;
    int checked;
    String format;
    TimePickerDialog timepickerdialog;
    private int CalendarHour, CalendarMinute;
    Calendar calendar;
    Button add_time_block,save_changes,all_day_button;
    TableLayout table;
    String charger_type_str,brand_str,CS_model_str,cs_connector_str,cs_power,cs_vtg,cs_rate,cs_board,socket_str,socket_vtg,str,socket_rate_str,socket_board_str,socket_amp_str,cs_amp;
    double longitude,latitude;
    private  String all_mon,all_tue,all_wed,all_thr,all_fri,all_sat,all_sun,all_day_str;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int RESULT_OK = -1;
    private static final int RESULT_CANCELED = -1;
    private show_commertial show_com_arry;
    private List<commertial_charger_type> charger_brand;
    private List<commertial_charger_model> charger_model;
    private List<commertial_connector> connector_type;
    private List<commertial_rate> rate_type;
    private List<Socket_commertial> socket_type;
    private List<commertial_voltage_socket> socket_volt;
    private List<commertial_rate_socket> socket_rate;
    private List<commertial_board_socket> socket_board;
    private List<commertial_board> board_com;
    private List<commertial_power_output> power_com;
    private List<commertial_voltage> volt_com;
    private List<commertial_charger_amphere> charger_amp_array;
    private List<commertial_socket_amphere> socket_amp_array;




    private commertial_charger_type charger_b;
    private commertial_charger_model charger_m;
    private commertial_connector connector_c;
    private commertial_rate rate_r;
    private Socket_commertial socket_list;
    private commertial_voltage_socket com_voltage;
    private commertial_rate_socket com_rate;
    private commertial_board_socket com_board;
    private commertial_board  board_c;
    private commertial_power_output power_c;
    private commertial_voltage volt_c;
    private commertial_charger_amphere charger_amp;
    private commertial_socket_amphere socket_amp;
    TextView edit_header;
    Switch instant_booking,multi_single_day;

    private CheckBox mon_d,tue_d,wed_d,thr_d,fri_d,sat_d,sun_d;
    private Boolean isMonday,isTuesday,isWednesday,isThuresday,isFriday,isSatursday,isSunday;

    Double final_profit_double;
    String final_profit;
    EditText state_rate_edit,profit_edit;
    int selected,check,user_id,list_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_commertial_listing);


        final Spinner spinner= findViewById(R.id.spinner120);//charger type -array1
        final Spinner spinner30= findViewById(R.id.spinner121);//brand
        final Spinner spinner31= findViewById(R.id.spinner122);//model
        final Spinner spinner32= findViewById(R.id.spinner123);//connector
        final Spinner spinner33= findViewById(R.id.spinner124);//cs-board
        final Spinner spinner34= findViewById(R.id.spinner125);//cs-rate
        final Spinner spinner60= findViewById(R.id.spinner126);//socket list
        final Spinner spinner61= findViewById(R.id.spinner127);//socket-board
        final Spinner spinner62= findViewById(R.id.spinner128);//socket-rate
        final Spinner spinner63= findViewById(R.id.spinner129);//socket-vtg
        final Spinner spinner64= findViewById(R.id.spinner130);//cs_power
        final Spinner spinner65= findViewById(R.id.spinner131);//cs_vtg
        final Spinner charger_A= findViewById(R.id.com_amphere_charger_edit);
        final Spinner socket_A= findViewById(R.id.com_socket_amphere_edit);

        edit_header=findViewById(R.id.edit_header_com);
        BubbleSeekBar bubbleSeekBar2 = findViewById(R.id.buuble_com);
        state_rate_edit=findViewById(R.id.rate_com_edit);
        profit_edit=findViewById(R.id.profit_com);
        address_1=findViewById(R.id.editText97);
        address_2=findViewById(R.id.editText98);
        country_txt=findViewById(R.id.editText99);
        save_changes=findViewById(R.id.edit_save_com);
        state_text=findViewById(R.id.editText100);
        landemark_txt=findViewById(R.id.editText102);
        zip_txt=findViewById(R.id.editText103);
        city_txt=findViewById(R.id.editText101);
        instant_booking=findViewById(R.id.switch7);
        multi_single_day=findViewById(R.id.switch8);
        edit_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add_time_block=findViewById(R.id.com_add_another);
        start_time_txt=findViewById(R.id.com_start_time);
        end_time_txt=findViewById(R.id.com_end_time);
        final ArrayList<String> single_day=new ArrayList<String>();
        final ArrayList<String> all_day_start=new ArrayList<String>();
        final ArrayList<String> all_day_end=new ArrayList<String>();

        final ArrayList<String> mon_start=new ArrayList<String>();
        final ArrayList<String> mon_end=new ArrayList<String>();
        final ArrayList<String> tue_start=new ArrayList<String>();
        final ArrayList<String> tue_end=new ArrayList<String>();
        final ArrayList<String> wed_start=new ArrayList<String>();
        final ArrayList<String> wed_end=new ArrayList<String>();
        final ArrayList<String> thr_start=new ArrayList<String>();
        final ArrayList<String> thr_end=new ArrayList<String>();
        final ArrayList<String> fri_start=new ArrayList<String>();
        final ArrayList<String> fri_end=new ArrayList<String>();
        final ArrayList<String> sat_start=new ArrayList<String>();
        final ArrayList<String> sat_end=new ArrayList<String>();
        final ArrayList<String> sun_start=new ArrayList<String>();
        final ArrayList<String> sun_end=new ArrayList<String>();

        final ArrayList<String> all_day = new ArrayList<String>();
        final ArrayList<String> socket_amphere=new ArrayList<String>();
        final ArrayList<String> charger_amphere=new ArrayList<String>();

        spinner30.setVisibility(View.GONE);
        spinner31.setVisibility(View.GONE);
        spinner32.setVisibility(View.GONE);
        spinner33.setVisibility(View.GONE);
        spinner34.setVisibility(View.GONE);
        spinner60.setVisibility(View.GONE);
        spinner61.setVisibility(View.GONE);
        spinner62.setVisibility(View.GONE);
        spinner63.setVisibility(View.GONE);
        spinner64.setVisibility(View.GONE);
        spinner65.setVisibility(View.GONE);
        charger_A.setVisibility(View.GONE);
        socket_A.setVisibility(View.GONE);

        mon_d=findViewById(R.id.checkbox_1_c);
        tue_d=findViewById(R.id.checkbox_2_c);
        wed_d=findViewById(R.id.checkbox_3_c);
        thr_d =findViewById(R.id.checkbox_4_c);
        fri_d=findViewById(R.id.checkbox_5_c);
        sat_d=findViewById(R.id.checkbox_6_c);
        sun_d=findViewById(R.id.checkbox_7_c);

        mon_d.setChecked(false);
        tue_d.setChecked(false);
        wed_d.setChecked(false);
        thr_d.setChecked(false);
        fri_d.setChecked(false);
        sat_d.setChecked(false);
        sun_d.setChecked(false);

        add_time_block.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //create a new row to add

//                TableRow row =new TableRow(getContext());
                final TableRow row = (TableRow) LayoutInflater.from(edit_commertial_listing.this).inflate(R.layout.sample_row, null);
                table = findViewById(R.id.tableLayout3);

                final EditText end_txt= row.findViewById(R.id.editText200);
                delete_row=row.findViewById(R.id.image_b);
                final EditText start_txt= row.findViewById(R.id.editText201);

                end_txt.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        calendar = Calendar.getInstance();
                        CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                        CalendarMinute = calendar.get(Calendar.MINUTE);
                        Log.i("time 24 :", String.valueOf(CalendarHour + CalendarMinute));

                        timepickerdialog = new TimePickerDialog(edit_commertial_listing.this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {

                                        if (hourOfDay == 0) {

                                            hourOfDay += 12;

                                            format = "AM";
                                        }
                                        else if (hourOfDay == 12) {

                                            format = "PM";

                                        }
                                        else if (hourOfDay > 12) {

                                            hourOfDay -= 12;

                                            format = "PM";

                                        }
                                        else {

                                            format = "AM";
                                        }

                                        if (multi_single_day.isChecked())
                                        {

                                            if (mon_d.isChecked() && isMonday==true)
                                            {

                                                end_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                                String end_str_time=end_txt.getText().toString();
                                                mon_end.add(end_str_time);
                                            }
                                            else if (tue_d.isChecked() && isTuesday==true)
                                            {
                                                end_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                                String end_str_time=end_txt.getText().toString();
                                                tue_end.add(end_str_time);
                                            }
                                            else  if(wed_d.isChecked() && isWednesday==true)
                                            {
                                                end_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                                String end_str_time=end_txt.getText().toString();
                                                wed_end.add(end_str_time);
                                            }
                                            else if (thr_d.isChecked() && isThuresday==true)
                                            {
                                                end_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                                String end_str_time=end_txt.getText().toString();
                                                thr_end.add(end_str_time);
                                            }
                                            else if (fri_d.isChecked() && isFriday==true)
                                            {
                                                end_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                                String end_str_time=end_txt.getText().toString();
                                                fri_end.add(end_str_time);
                                            }
                                            else if (sat_d.isChecked() && isSatursday==true)
                                            {
                                                end_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                                String end_str_time=end_txt.getText().toString();
                                                sat_end.add(end_str_time);
                                            }
                                            else if (sun_d.isChecked() && isSunday==true)
                                            {
                                                end_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                                String end_str_time=end_txt.getText().toString();
                                                sun_end.add(end_str_time);
                                            }
                                        }else
                                        {
//                                                    end_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                            end_txt.setText(hourOfDay + ":" + minute );
                                            String end_str_time=end_txt.getText().toString();
                                            all_day_end.add(end_str_time);
                                        }


                                    }
                                }, CalendarHour, CalendarMinute, false);
                        timepickerdialog.show();

                    }

                });

                start_txt.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        calendar = Calendar.getInstance();
                        CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                        CalendarMinute = calendar.get(Calendar.MINUTE);


                        timepickerdialog = new TimePickerDialog(edit_commertial_listing.this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {

                                        if (hourOfDay == 0) {

                                            hourOfDay += 12;

                                            format = "AM";
                                        }
                                        else if (hourOfDay == 12) {

                                            format = "PM";

                                        }
                                        else if (hourOfDay > 12) {

                                            hourOfDay -= 12;

                                            format = "PM";

                                        }
                                        else {

                                            format = "AM";
                                        }


                                        if (multi_single_day.isChecked())
                                        {
                                            if (mon_d.isChecked() && isMonday==true)
                                            {

                                                start_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                                String end_str_time=start_txt.getText().toString();
                                                mon_start.add(end_str_time);
                                            }
                                            else if (tue_d.isChecked() && isTuesday==true)
                                            {
                                                start_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                                String end_str_time=start_txt.getText().toString();
                                                tue_start.add(end_str_time);
                                            }
                                            else  if(wed_d.isChecked() && isWednesday==true)
                                            {
                                                start_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                                String end_str_time=start_txt.getText().toString();
                                                wed_start.add(end_str_time);
                                            }
                                            else if (thr_d.isChecked() && isThuresday==true)
                                            {
                                                start_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                                String end_str_time=start_txt.getText().toString();
                                                thr_start.add(end_str_time);
                                            }
                                            else if (fri_d.isChecked() && isFriday==true)
                                            {
                                                start_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                                String end_str_time=start_txt.getText().toString();
                                                fri_start.add(end_str_time);
                                            }
                                            else if (sat_d.isChecked() && isSatursday==true)
                                            {
                                                start_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                                String end_str_time=start_txt.getText().toString();
                                                sat_start.add(end_str_time);
                                            }
                                            else if (sun_d.isChecked() && isSunday==true)
                                            {
                                                start_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                                String end_str_time=start_txt.getText().toString();
                                                sun_start.add(end_str_time);
                                            }
                                        }else
                                        {
//                                           start_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                            start_txt.setText(hourOfDay + ":" + minute );

                                            String start_str_time=start_txt.getText().toString();
                                            all_day_start.add(start_str_time);


                                        }
                                    }
                                }, CalendarHour, CalendarMinute, false);
                        timepickerdialog.show();

                    }

                });

                delete_row.setOnClickListener(new View.OnClickListener()
                {
                    @Override public void onClick(View v)
                    {
                        // row is your row, the parent of the clicked button
                        View row = (View) v.getParent();
                        // container contains all the rows, you could keep a variable somewhere else to the container which you can refer to here
                        ViewGroup container = ((ViewGroup)row.getParent());
                        // delete the row and invalidate your view so it gets redrawn
                        container.removeView(row);
                        Log.i("Index=", String.valueOf(v.getTag()));
                        container.invalidate();
                    }
                });
                //  row.addView(txt);
                //add your new row to the TableLayout:
                table.addView(row);

            }
        });




        Places.initialize(getApplicationContext(),"AIzaSyDNdANqUDsLyz6s3mtEJuEQBNaW4xVpK8k");
        address_1.setFocusable(false);
        address_1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG,Place.Field.ADDRESS_COMPONENTS);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields).setCountry("IN") //NIGERIA
                        .build(Objects.requireNonNull(getApplicationContext()));
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

            }
        });
        final ArrayList<String> spinner_charger_brand=new ArrayList<String>();
        final ArrayList<String> spinner_charger_model=new ArrayList<String>();
        final ArrayList<String> spinner_connector=new ArrayList<String>();
        final ArrayList<String> spinner_electricity_board=new ArrayList<String>();
        final ArrayList<String> spinner_power_output=new ArrayList<String>();
        final ArrayList<String> spinner_rate_structure=new ArrayList<String>();
        final ArrayList<String> spinner_voltage=new ArrayList<String>();

        final ArrayList<String> socket_board_array=new ArrayList<String>();
        final ArrayList<String> socket_rate_array=new ArrayList<String>();
        final ArrayList<String> socket_power=new ArrayList<String>();
        final ArrayList<String> socket_voltage=new ArrayList<String>();
        final ArrayList<String> socket_list_array=new ArrayList<String>();
        all_day_button=findViewById(R.id.add_day_com);
        all_mon="not_all_day";
        all_tue="not_all_day";
        all_wed="not_all_day";
        all_thr="not_all_day";
        all_fri="not_all_day";
        all_sat="not_all_day";
        all_sun="not_all_day";
        all_day_str="not_all_day";

        multi_single_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

//                    day selection-------------------------------
                    checked = 0;
                    Log.i("State checked", String.valueOf(checked));
                    all_day.clear();

                    mon_d.setChecked(false);
                    tue_d.setChecked(false);
                    wed_d.setChecked(false);
                    thr_d.setChecked(false);
                    fri_d.setChecked(false);
                    sat_d.setChecked(false);
                    sun_d.setChecked(false);
                    final ArrayList<String> day_select = new ArrayList<String>();
                    String result = "Selected Courses";
//                    Single day----------------------------------

                    mon_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                isMonday=true;
                                isTuesday=false;
                                isWednesday=false;
                                isThuresday=false;
                                isFriday=false;
                                isSatursday=false;
                                isSunday=false;
                                if (tue_start.isEmpty())
                                {
                                    tue_d.setChecked(false);
                                    isTuesday=false;
                                }
                                else
                                {
                                    tue_d.setChecked(true);
                                    isTuesday=false;
                                }
                                if (wed_start.isEmpty())
                                {
                                    wed_d.setChecked(false);
                                    isWednesday=false;
                                }
                                else
                                {
                                    wed_d.setChecked(true);
                                    isWednesday=false;
                                }
                                if (thr_start.isEmpty())
                                {
                                    thr_d.setChecked(false);
                                    isThuresday=false;
                                }
                                else
                                {
                                    thr_d.setChecked(true);
                                    isThuresday=false;
                                }
                                if (fri_start.isEmpty())
                                {
                                    fri_d.setChecked(false);
                                    isFriday=false;
                                }
                                else
                                {
                                    fri_d.setChecked(true);
                                    isFriday=false;
                                }
                                if (sat_start.isEmpty())
                                {
                                    sat_d.setChecked(false);
                                    isSatursday=false;
                                }
                                else
                                {
                                    sat_d.setChecked(true);
                                    isSatursday=false;
                                }
                                if (sun_start.isEmpty())
                                {
                                    sun_d.setChecked(false);
                                    isSunday=false;
                                }
                                else
                                {
                                    sun_d.setChecked(true);
                                    isSunday=false;
                                }

                                String demo_str = "monday";
                                single_day.add(demo_str);
                                if(table!=null) {
                                    table.removeAllViews();
                                }
                                String day_str = "Select time slot for Monday";
                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();
                            } else {

                                String demo_str = " monday";
                                single_day.remove(demo_str);
                                mon_end.remove(true);
                                mon_start.remove(true);
                                mon_d.setChecked(false);
                                isMonday=false;
                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    tue_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                isTuesday=true;

                                if (mon_start.isEmpty())
                                {
                                    mon_d.setChecked(false);
                                    isMonday=false;
                                }
                                else
                                {
                                    mon_d.setChecked(true);
                                    isMonday=false;
                                }
                                if (wed_start.isEmpty())
                                {
                                    wed_d.setChecked(false);
                                    isWednesday=false;
                                }
                                else
                                {
                                    wed_d.setChecked(true);
                                    isWednesday=false;
                                }
                                if (thr_start.isEmpty())
                                {
                                    thr_d.setChecked(false);
                                    isThuresday=false;
                                }
                                else
                                {
                                    thr_d.setChecked(true);
                                    isThuresday=false;
                                }
                                if (fri_start.isEmpty())
                                {
                                    fri_d.setChecked(false);
                                    isFriday=false;
                                }
                                else
                                {
                                    fri_d.setChecked(true);
                                    isFriday=false;
                                }
                                if (sat_start.isEmpty())
                                {
                                    sat_d.setChecked(false);
                                    isSatursday=false;
                                }
                                else
                                {
                                    sat_d.setChecked(true);
                                    isSatursday=false;
                                }
                                if (sun_start.isEmpty())
                                {
                                    sun_d.setChecked(false);
                                    isSunday=false;
                                }
                                else
                                {
                                    sun_d.setChecked(true);
                                    isSunday=false;
                                }

                                String demo_str = "tuesday";
                                single_day.add(demo_str);

                                if(table!=null) {
                                    table.removeAllViews();
                                }
                                String day_str = "Select time slot for Tuesday";

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            } else {

                                String demo_str = "tuesday";
                                single_day.remove(demo_str);
                                tue_end.remove(true);
                                tue_start.remove(true);
                                tue_d.setChecked(false);
                                isTuesday=false;
                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    wed_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                isWednesday=true;

                                if (tue_start.isEmpty())
                                {
                                    tue_d.setChecked(false);
                                    isTuesday=false;
                                }
                                else
                                {
                                    tue_d.setChecked(true);
                                    isTuesday=false;
                                }
                                if (mon_start.isEmpty())
                                {
                                    mon_d.setChecked(false);
                                    isMonday=false;
                                }
                                else
                                {
                                    mon_d.setChecked(true);
                                    isMonday=false;
                                }
                                if (thr_start.isEmpty())
                                {
                                    thr_d.setChecked(false);
                                    isThuresday=false;
                                }
                                else
                                {
                                    thr_d.setChecked(true);
                                    isThuresday=false;
                                }
                                if (fri_start.isEmpty())
                                {
                                    fri_d.setChecked(false);
                                    isFriday=false;
                                }
                                else
                                {
                                    fri_d.setChecked(true);
                                    isFriday=false;
                                }
                                if (sat_start.isEmpty())
                                {
                                    sat_d.setChecked(false);
                                    isSatursday=false;
                                }
                                else
                                {
                                    sat_d.setChecked(true);
                                    isSatursday=false;
                                }
                                if (sun_start.isEmpty())
                                {
                                    sun_d.setChecked(false);
                                    isSunday=false;
                                }
                                else
                                {
                                    sun_d.setChecked(true);
                                    isSunday=false;
                                }

                                String demo_str = "wednesday";
                                single_day.add(demo_str);

                                if(table!=null) {
                                    table.removeAllViews();
                                }
                                String day_str = "Select time slot for wednesday";
//                                select_day.setText(day_str);
                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            } else {

                                String demo_str = "wednesday";
                                single_day.remove(demo_str);
                                wed_end.remove(true);
                                wed_start.remove(true);
                                wed_d.setChecked(false);
                                isWednesday=false;

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    thr_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                isThuresday=true;
                                if (tue_start.isEmpty())
                                {
                                    tue_d.setChecked(false);
                                    isTuesday=false;
                                }
                                else
                                {
                                    tue_d.setChecked(true);
                                    isTuesday=false;
                                }
                                if (mon_start.isEmpty())
                                {
                                    mon_d.setChecked(false);
                                    isMonday=false;
                                }
                                else
                                {
                                    mon_d.setChecked(true);
                                    isMonday=false;
                                }
                                if (wed_start.isEmpty())
                                {
                                    wed_d.setChecked(false);
                                    isWednesday=false;
                                }
                                else
                                {
                                    wed_d.setChecked(true);
                                    isWednesday=false;
                                }
                                if (fri_start.isEmpty())
                                {
                                    fri_d.setChecked(false);
                                    isFriday=false;
                                }
                                else
                                {
                                    fri_d.setChecked(true);
                                    isFriday=false;
                                }
                                if (sat_start.isEmpty())
                                {
                                    sat_d.setChecked(false);
                                    isSatursday=false;
                                }
                                else
                                {
                                    sat_d.setChecked(true);
                                    isSatursday=false;
                                }
                                if (sun_start.isEmpty())
                                {
                                    sun_d.setChecked(false);
                                    isSunday=false;
                                }
                                else
                                {
                                    sun_d.setChecked(true);
                                    isSunday=false;
                                }
                                String demo_str = "thrusday";
                                single_day.add(demo_str);

                                if(table!=null) {
                                    table.removeAllViews();
                                }
                                String day_str = "Select time slot for Thrusday";
//                                select_day.setText(day_str);
                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            } else {

                                String demo_str = "thrusday";
                                single_day.remove(demo_str);
                                thr_end.remove(true);
                                thr_start.remove(true);
                                thr_d.setChecked(false);
                                isThuresday=false;
                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    fri_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                isFriday=true;
                                if (tue_start.isEmpty())
                                {
                                    tue_d.setChecked(false);
                                    isTuesday=false;
                                }
                                else
                                {
                                    tue_d.setChecked(true);
                                    isTuesday=false;
                                }
                                if (mon_start.isEmpty())
                                {
                                    mon_d.setChecked(false);
                                    isMonday=false;
                                }
                                else
                                {
                                    mon_d.setChecked(true);
                                    isMonday=false;
                                }
                                if (thr_start.isEmpty())
                                {
                                    thr_d.setChecked(false);
                                    isThuresday=false;
                                }
                                else
                                {
                                    thr_d.setChecked(true);
                                    isThuresday=false;
                                }
                                if (wed_start.isEmpty())
                                {
                                    wed_d.setChecked(false);
                                    isWednesday=false;
                                }
                                else
                                {
                                    wed_d.setChecked(true);
                                    isWednesday=false;
                                }
                                if (sat_start.isEmpty())
                                {
                                    sat_d.setChecked(false);
                                    isSatursday=false;
                                }
                                else
                                {
                                    sat_d.setChecked(true);
                                    isSatursday=false;
                                }
                                if (sun_start.isEmpty())
                                {
                                    sun_d.setChecked(false);
                                    isSunday=false;
                                }
                                else
                                {
                                    sun_d.setChecked(true);
                                    isSunday=false;
                                }
                                String demo_str = "friday";
                                single_day.add(demo_str);

                                if(table!=null) {
                                    table.removeAllViews();
                                }
                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            } else {

                                String demo_str = "friday";
                                single_day.remove(demo_str);
                                fri_end.remove(true);
                                fri_start.remove(true);
                                fri_d.setChecked(false);
                                isFriday=false;
                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    sat_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                isSatursday=true;
                                if (tue_start.isEmpty())
                                {
                                    tue_d.setChecked(false);
                                    isTuesday=false;
                                }
                                else
                                {
                                    tue_d.setChecked(true);
                                    isTuesday=false;
                                }
                                if (mon_start.isEmpty())
                                {
                                    mon_d.setChecked(false);
                                    isMonday=false;
                                }
                                else
                                {
                                    mon_d.setChecked(true);
                                    isMonday=false;
                                }
                                if (thr_start.isEmpty())
                                {
                                    thr_d.setChecked(false);
                                    isThuresday=false;
                                }
                                else
                                {
                                    thr_d.setChecked(true);
                                    isThuresday=false;
                                }
                                if (fri_start.isEmpty())
                                {
                                    fri_d.setChecked(false);
                                    isFriday=false;
                                }
                                else
                                {
                                    fri_d.setChecked(true);
                                    isFriday=false;
                                }
                                if (wed_start.isEmpty())
                                {
                                    wed_d.setChecked(false);
                                    isWednesday=false;
                                }
                                else
                                {
                                    wed_d.setChecked(true);
                                    isWednesday=false;
                                }
                                if (sun_start.isEmpty())
                                {
                                    sun_d.setChecked(false);
                                    isSunday=false;
                                }
                                else
                                {
                                    sun_d.setChecked(true);
                                    isSunday=false;
                                }
                                String demo_str = "satursday";
                                single_day.add(demo_str);

                                if(table!=null) {
                                    table.removeAllViews();
                                }
                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            } else {

                                String demo_str = "satursday";
                                single_day.remove(demo_str);
                                sat_end.remove(true);
                                sat_start.remove(true);
                                isSatursday=false;
                                sat_d.setChecked(false);
                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    sun_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                isSunday=true;
                                if (tue_start.isEmpty())
                                {
                                    tue_d.setChecked(false);
                                    isTuesday=false;
                                }
                                else
                                {
                                    tue_d.setChecked(true);
                                    isTuesday=false;
                                }
                                if (mon_start.isEmpty())
                                {
                                    mon_d.setChecked(false);
                                    isMonday=false;
                                }
                                else
                                {
                                    mon_d.setChecked(true);
                                    isMonday=false;
                                }
                                if (thr_start.isEmpty())
                                {
                                    thr_d.setChecked(false);
                                    isThuresday=false;
                                }
                                else
                                {
                                    thr_d.setChecked(true);
                                    isThuresday=false;
                                }
                                if (fri_start.isEmpty())
                                {
                                    fri_d.setChecked(false);
                                    isFriday=false;
                                }
                                else
                                {
                                    fri_d.setChecked(true);
                                    isFriday=false;
                                }
                                if (sat_start.isEmpty())
                                {
                                    sat_d.setChecked(false);
                                    isSatursday=false;
                                }
                                else
                                {
                                    sat_d.setChecked(true);
                                    isSatursday=false;
                                }
                                if (wed_start.isEmpty())
                                {
                                    wed_d.setChecked(false);
                                    isWednesday=false;
                                }
                                else
                                {
                                    wed_d.setChecked(true);
                                    isWednesday=false;
                                }

                                String demo_str = "sunday";
                                single_day.add(demo_str);
                                if(table!=null) {
                                    table.removeAllViews();
                                }
                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            } else {

                                String demo_str = "sunday";
                                single_day.remove(demo_str);
                                sun_end.remove(true);
                                sun_start.remove(true);
                                sun_d.setChecked(false);
                                isSunday=false;
                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

                else {
                    checked = 1;

                    Log.i("State checked", String.valueOf(checked));
                    single_day.clear();
                    checked = 1;
//                    multi_b.setBackgroundResource(R.drawable.bg_text);
//                    single_b.setBackgroundResource(R.drawable.bg_rect);
                    mon_d.setChecked(false);
                    tue_d.setChecked(false);
                    wed_d.setChecked(false);
                    thr_d.setChecked(false);
                    fri_d.setChecked(false);
                    sat_d.setChecked(false);
                    sun_d.setChecked(false);
                    mon_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                String demo_str = "monday";
                                all_day.add(demo_str);

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();
                            } else {

                                String demo_str = "monday";
                                all_day.remove(demo_str);
                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    tue_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                String demo_str = "tuesday";
                                all_day.add(demo_str);

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();


                            } else {

                                String demo_str = "tuesday";
                                all_day.remove(demo_str);

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    wed_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                String demo_str = "wednesday";
                                all_day.add(demo_str);

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();
                            } else {

                                String demo_str = "wednesday";
                                all_day.remove(demo_str);

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    thr_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                String demo_str = "thursday";
                                all_day.add(demo_str);

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();
                            } else {

                                String demo_str = "thursday";
                                all_day.remove(demo_str);

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    fri_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                String demo_str = "friday";
                                all_day.add(demo_str);

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();
                            } else {

                                String demo_str = "friday";
                                all_day.remove(demo_str);

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    sat_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                String demo_str = "saturday";
                                all_day.add(demo_str);

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();
                            } else {

                                String demo_str = "saturday";
                                all_day.remove(demo_str);

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    sun_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                String demo_str = "sunday";
                                all_day.add(demo_str);

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();
                            } else {

                                String demo_str = "sunday";
                                all_day.remove(demo_str);

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    String day_str = "Select time slot for selected days";
//                    select_day.setText(day_str);
                    // The toggle is disabled

                }
            }

        });


        instant_booking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Log.i("Select booking instant:", String.valueOf(isChecked));
                    selected=1;
                }
                else
                {
                    Log.i("Select booking not instant:", String.valueOf(isChecked));
                    selected=0;

                }
            }
        });

        final ArrayList<String> array1 = new ArrayList<String>();
        array1.add("Charging Station");
        array1.add("Standard Domestic Socket");
        ArrayAdapter<String> adapter101 = new ArrayAdapter<String>(edit_commertial_listing.this, R.layout.sample_text, array1);
        spinner.setAdapter(adapter101);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {

        } else {
            newString = extras.getString("List_V_ID_com");
            id=Integer.parseInt(newString);
        }
        APIInterface service = SingletonRetrofit.getAPIInterface();

        Call<show_commertial> call = service.getListCommertial(id);
        Log.i("Call", String.valueOf(call.request().url()));

        call.enqueue(new Callback<show_commertial>() {
            @Override
            public void onResponse(Call<show_commertial> call, Response<show_commertial> response) {

                if (response.body() != null) {
                    Log.i("Make  response: ", String.valueOf(response.body()));
                    show_com_arry =  response.body();
                    ArrayList demo_str;
                    int id_res,id_listing,address_id;
                    int i;
//                           for (i = 0; i < make_array.size(); i++) {
                    Log.i("Day array: ", String.valueOf(show_com_arry.getAddress()));
                    Log.i("Day array: ", String.valueOf(show_com_arry.getAddress().getCountry()));
                    Log.i("Day array: ", String.valueOf(show_com_arry.getDaysArray()));
                    address_1.setText(String.valueOf(show_com_arry.getAddress().getAddress1()));
                    address_2.setText(String.valueOf(show_com_arry.getAddress().getAddress2()));
                    country_txt.setText(String.valueOf(show_com_arry.getAddress().getCountry()));
                    state_text.setText(String.valueOf(show_com_arry.getAddress().getState()));
                    city_txt.setText(String.valueOf(show_com_arry.getAddress().getCity()));
                    landemark_txt.setText(String.valueOf(show_com_arry.getAddress().getLandmark()));
                    zip_txt.setText(String.valueOf(show_com_arry.getAddress().getPin()));
                    String profit_privious=String.valueOf(show_com_arry.getProfits());
                    profit_edit.setText(profit_privious);
                    latitude=show_com_arry.getAddress().getLat();
                    longitude=show_com_arry.getAddress().getLng();
                    String socket_str=String.valueOf(show_com_arry.getProvider().getSocket());
                    if(socket_str.equals("null") || socket_str==null) {

                        array1.clear();
                        array1.add("Charging Station");
                        array1.add("Standard Domestic Socket");
                        ArrayAdapter<String> adapter101 = new ArrayAdapter<String>(edit_commertial_listing.this, R.layout.sample_text, array1);
                        spinner.setAdapter(adapter101);
                        spinner.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        array1.clear();
                        array1.add("Standard Domestic Socket");
                        array1.add("Charging Station");
                        ArrayAdapter<String> adapter101 = new ArrayAdapter<String>(edit_commertial_listing.this,R.layout.sample_text, array1);
                        spinner.setAdapter(adapter101);
                        spinner.setVisibility(View.VISIBLE);
                    }


                } else {
                    String new_str = "Make is empty";
                    Log.i("Make: ", new_str);
                    //  Toast.makeText(getContext(), "Please Select make", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<show_commertial> call, Throwable t) {
                Log.i("Make error: ", String.valueOf(t));

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String state_str=state_text.getText().toString();
                if(state_str.isEmpty())
                {
                    Toast.makeText(edit_commertial_listing.this, "Select state ", Toast.LENGTH_SHORT).show();
                    spinner30.setVisibility(View.GONE);
                    spinner31.setVisibility(View.GONE);
                    spinner32.setVisibility(View.GONE);
                    spinner33.setVisibility(View.GONE);
                    spinner34.setVisibility(View.GONE);
                    spinner60.setVisibility(View.GONE);
                    spinner61.setVisibility(View.GONE);
                    spinner62.setVisibility(View.GONE);
                    spinner63.setVisibility(View.GONE);
                    spinner64.setVisibility(View.GONE);
                    spinner65.setVisibility(View.GONE);
                    charger_A.setVisibility(View.GONE);
                    socket_A.setVisibility(View.GONE);

                }
                else {
                    String type_str = spinner.getSelectedItem().toString();
                    if (type_str.equals("Charging Station")) {

                        spinner30.setVisibility(View.VISIBLE);
                        spinner31.setVisibility(View.GONE);
                        spinner32.setVisibility(View.GONE);
                        spinner33.setVisibility(View.GONE);
                        spinner34.setVisibility(View.GONE);
                        spinner60.setVisibility(View.GONE);
                        spinner61.setVisibility(View.GONE);
                        spinner62.setVisibility(View.GONE);
                        spinner63.setVisibility(View.GONE);
                        spinner64.setVisibility(View.GONE);
                        spinner65.setVisibility(View.GONE);
                        charger_A.setVisibility(View.GONE);
                        socket_A.setVisibility(View.GONE);
                        charger_type_str=spinner.getSelectedItem().toString();
                        spinner_charger_brand.clear();
                        spinner_rate_structure.clear();
                        spinner_electricity_board.clear();
                        spinner_charger_brand.add("Charger brand");
                        APIInterface service = SingletonRetrofit.getAPIInterface();

                        Call<List<commertial_charger_type>> call = service.getChargerBrand();
                        Log.i("Brand***********************************************************************************************  : ", String.valueOf(call.request().url()));
                        call.enqueue(new Callback<List<commertial_charger_type>>() {
                            @Override
                            public void onResponse(Call<List<commertial_charger_type>> call, Response<List<commertial_charger_type>> response) {
                                if (response.body() != null) {
                                    charger_brand = response.body();

                                    ArrayList make_array = (ArrayList) response.body();


                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        charger_b = charger_brand.get(i);
                                        demo_str = charger_b.getChargerBrand();
                                        spinner_charger_brand.add(demo_str);
                                        Log.i("Brand Array: ", spinner_charger_brand.toString());
                                    }

                                } else {
                                    String new_str = "Brand is empty";
                                    Log.i("Brand Array: ", new_str);

                                }
                            }

                            @Override
                            public void onFailure(Call<List<commertial_charger_type>> call, Throwable t) {
                                Log.i("Brand error: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter30 = new ArrayAdapter<>(edit_commertial_listing.this, R.layout.sample_text, spinner_charger_brand);
                        spinner30.setAdapter(adapter30);
                        spinner30.setVisibility(View.VISIBLE);

                        spinner_rate_structure.add("rate structure");
                        Call<List<commertial_rate>> call2 = service.getRateStructure(state_text.getText().toString());
                        Log.i("Commertial rate***********************************************************************************************  : ", String.valueOf(call2.request().url()));
                        call2.enqueue(new Callback<List<commertial_rate>>() {
                            @Override
                            public void onResponse(Call<List<commertial_rate>> call2, Response<List<commertial_rate>> response) {
                                if (response.body() != null) {
                                    rate_type = response.body();
                                    ArrayList make_array = (ArrayList) response.body();
                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        rate_r = rate_type.get(i);
                                        demo_str = rate_r.getRateStructure();
                                        spinner_rate_structure.add(demo_str);
                                        Log.i("rate Array: ", spinner_rate_structure.toString());
                                    }

                                } else {
                                    String new_str = "rate is empty";
                                    Log.i("rate Array: ", new_str);

                                }
                            }

                            @Override
                            public void onFailure(Call<List<commertial_rate>> call2, Throwable t) {
                                Log.i("rate error: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter34 = new ArrayAdapter<>(edit_commertial_listing.this, R.layout.sample_text, spinner_rate_structure);
                        spinner34.setAdapter(adapter34);
                        spinner34.setVisibility(View.VISIBLE);

                        spinner_electricity_board.add("Select board");
                        Call<List<commertial_board>> call4 = service.getElectricityBoardCommertial(state_text.getText().toString());
                        Log.i(" Commertial board: ***********************************************************************************************  : ", String.valueOf(call4.request().url()));
                        call4.enqueue(new Callback<List<commertial_board>>() {
                            @Override
                            public void onResponse(Call<List<commertial_board>> call4, Response<List<commertial_board>> response) {
                                if (response.body() != null) {
                                    board_com = response.body();

                                    ArrayList make_array = (ArrayList) response.body();


                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        board_c = board_com.get(i);
                                        demo_str = board_c.getElectricityBoardCommertial();
                                        spinner_electricity_board.add(demo_str);
                                        Log.i("Socket Commertial board: ", spinner_electricity_board.toString());
                                    }

                                } else {
                                    String new_str = "Socket Commertial board is empty";
                                    Log.i(" Socket Commertia board: ", new_str);

                                }
                            }

                            @Override
                            public void onFailure(Call<List<commertial_board>> call4, Throwable t) {
                                Log.i(" Socket Commertial board: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter33 = new ArrayAdapter<>(edit_commertial_listing.this, R.layout.sample_text, spinner_electricity_board);
                        spinner33.setAdapter(adapter33);
                        spinner33.setVisibility(View.VISIBLE);


                    }
                    else if (type_str.equals("Standard Domestic Socket")) {
                        spinner30.setVisibility(View.GONE);
                        spinner31.setVisibility(View.GONE);
                        spinner32.setVisibility(View.GONE);
                        spinner33.setVisibility(View.GONE);
                        spinner34.setVisibility(View.GONE);
                        spinner64.setVisibility(View.GONE);
                        spinner65.setVisibility(View.GONE);
                        spinner60.setVisibility(View.VISIBLE);
                        spinner61.setVisibility(View.VISIBLE);
                        spinner62.setVisibility(View.VISIBLE);
                        spinner63.setVisibility(View.GONE);
                        charger_A.setVisibility(View.GONE);
                        socket_A.setVisibility(View.GONE);
                        charger_type_str=spinner.getSelectedItem().toString();

                        socket_list_array.clear();
                        socket_list_array.add("Select socket");
                        APIInterface service = SingletonRetrofit.getAPIInterface();

                        Call<List<Socket_commertial>> call = service.getSocketCommertial();
                        Log.i("Socket Commertial: ***********************************************************************************************  : ", String.valueOf(call.request().url()));
                        call.enqueue(new Callback<List<Socket_commertial>>() {
                            @Override
                            public void onResponse(Call<List<Socket_commertial>> call, Response<List<Socket_commertial>> response) {
                                if (response.body() != null) {
                                    socket_type = response.body();
                                    ArrayList make_array = (ArrayList) response.body();
                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        socket_list = socket_type.get(i);
                                        demo_str = socket_list.getSocketCommertial();
                                        socket_list_array.add(demo_str);
                                    }

                                } else {
                                    String new_str = "Socket Commertial is empty";

                                }
                            }

                            @Override
                            public void onFailure(Call<List<Socket_commertial>> call, Throwable t) {
                                Log.i(" Socket Commertial: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter60 = new ArrayAdapter<>(edit_commertial_listing.this, R.layout.sample_text, socket_list_array);
                        spinner60.setAdapter(adapter60);
                        spinner60.setVisibility(View.VISIBLE);

                        socket_rate_array.clear();
                        socket_rate_array.add("Socket rate structure");
                        Call<List<commertial_rate_socket>> call2 = service.getRateStructureCommertialSocket(state_text.getText().toString());
                        Log.i("Socket Commertial rate: ***********************************************************************************************  : ", String.valueOf(call.request().url()));
                        call2.enqueue(new Callback<List<commertial_rate_socket>>() {
                            @Override
                            public void onResponse(Call<List<commertial_rate_socket>> call2, Response<List<commertial_rate_socket>> response) {
                                if (response.body() != null) {
                                    socket_rate = response.body();

                                    ArrayList make_array = (ArrayList) response.body();


                                    String demo_str = "";
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        com_rate = socket_rate.get(i);
                                        demo_str = com_rate.getRateStructureCommertialSocket();
                                        socket_rate_array.add(demo_str);
                                    }

                                } else {
                                    String new_str = "Socket Commertial rate is empty";
                                    Log.i(" Socket Commertia ratel: ", new_str);

                                }
                            }

                            @Override
                            public void onFailure(Call<List<commertial_rate_socket>> call2, Throwable t) {
                                Log.i(" Socket Commertial rate: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter62 = new ArrayAdapter<>(edit_commertial_listing.this, R.layout.sample_text, socket_rate_array);
                        spinner62.setAdapter(adapter62);
                        spinner62.setVisibility(View.VISIBLE);

                        socket_board_array.clear();
                        socket_board_array.add("Select board");
                        Call<List<commertial_board_socket>> call3 = service.getElectricityBoardCommertialSocket(state_text.getText().toString());
                        Log.i("Socket Commertial board: ***********************************************************************************************  : ", String.valueOf(call.request().url()));
                        call3.enqueue(new Callback<List<commertial_board_socket>>() {
                            @Override
                            public void onResponse(Call<List<commertial_board_socket>> call3, Response<List<commertial_board_socket>> response) {
                                if (response.body() != null) {
                                    socket_board = response.body();

                                    ArrayList make_array = (ArrayList) response.body();


                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        com_board = socket_board.get(i);
                                        demo_str = com_board.getElectricityBoardCommertialSocket();
                                        socket_board_array.add(demo_str);
                                        Log.i("Socket Commertial board: ", socket_board_array.toString());
                                    }

                                } else {
                                    String new_str = "Socket Commertial board is empty";
                                    Log.i(" Socket Commertia board: ", new_str);

                                }
                            }

                            @Override
                            public void onFailure(Call<List<commertial_board_socket>> call3, Throwable t) {
                                Log.i(" Socket Commertial board: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter61 = new ArrayAdapter<>(edit_commertial_listing.this, R.layout.sample_text, socket_board_array);
                        spinner61.setAdapter(adapter61);
                        spinner61.setVisibility(View.VISIBLE);

                    } else if (type_str.equals("ChargerType")) {
                        spinner30.setVisibility(View.GONE);
                        spinner31.setVisibility(View.GONE);
                        spinner32.setVisibility(View.GONE);
                        spinner33.setVisibility(View.GONE);
                        spinner34.setVisibility(View.GONE);
                        spinner60.setVisibility(View.GONE);
                        spinner61.setVisibility(View.GONE);
                        spinner62.setVisibility(View.GONE);
                        spinner63.setVisibility(View.GONE);
                        spinner64.setVisibility(View.GONE);
                        spinner65.setVisibility(View.GONE);
                        charger_A.setVisibility(View.GONE);
                        socket_A.setVisibility(View.GONE);


                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner30.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                brand_str=spinner30.getSelectedItem().toString();
                if (brand_str.equals("Charger brand"))
                {
                    spinner31.setVisibility(View.GONE);
                }
                else {
                    String type_brand_str = spinner30.getSelectedItem().toString();
                    spinner_charger_model.clear();
                    spinner_charger_model.add("Charger Model");


                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<commertial_charger_model>> call = service.getChargerModel(type_brand_str);
                    Log.i("Model com***********************************************************************************************  : ", String.valueOf(call.request().url()));
                    call.enqueue(new Callback<List<commertial_charger_model>>() {
                        @Override
                        public void onResponse(Call<List<commertial_charger_model>> call, Response<List<commertial_charger_model>> response) {
                            if (response.body() != null) {
                                charger_model = response.body();

                                ArrayList make_array = (ArrayList) response.body();


                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    charger_m = charger_model.get(i);
                                    demo_str = charger_m.getChargerModel();
                                    spinner_charger_model.add(demo_str);
                                    Log.i("Model com Array: ", spinner_charger_model.toString());
                                }

                            } else {
                                String new_str = "Model com is empty";
                                Log.i("v Array: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<commertial_charger_model>> call, Throwable t) {
                            Log.i("Model com error: ", String.valueOf(t));

                        }
                    });
                    ArrayAdapter<String> adapter31 = new ArrayAdapter<>(edit_commertial_listing.this, R.layout.sample_text, spinner_charger_model);
                    spinner31.setAdapter(adapter31);
                    spinner31.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner31.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CS_model_str=spinner31.getSelectedItem().toString();
                if (CS_model_str.equals("Charger Model"))
                {
                    spinner32.setVisibility(View.GONE);
                    spinner64.setVisibility(View.GONE);
                    spinner65.setVisibility(View.GONE);
                    charger_A.setVisibility(View.GONE);
                }
                else {
                    String state_str = state_text.getText().toString();
                    String model_str = spinner31.getSelectedItem().toString();
                    String brand_str = spinner30.getSelectedItem().toString();

                    spinner_connector.clear();
                    spinner_connector.add("Charger Connector");

                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<commertial_connector>> call = service.getConnectorType();
                    Log.i("Connector**********************************************************************************************  : ", String.valueOf(call.request().url()));
                    call.enqueue(new Callback<List<commertial_connector>>() {
                        @Override
                        public void onResponse(Call<List<commertial_connector>> call, Response<List<commertial_connector>> response) {
                            if (response.body() != null) {
                                connector_type = response.body();

                                ArrayList make_array = (ArrayList) response.body();


                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    connector_c = connector_type.get(i);
                                    demo_str = connector_c.getConnectorType();
                                    spinner_connector.add(demo_str);
                                    Log.i("Connector Com Array: ", spinner_connector.toString());
                                }

                            } else {
                                String new_str = "Connector is empty";
                                Log.i("Connector com: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<commertial_connector>> call, Throwable t) {
                            Log.i("login error: ", String.valueOf(t));

                        }
                    });
                    ArrayAdapter<String> adapter32 = new ArrayAdapter<>(edit_commertial_listing.this, R.layout.sample_text, spinner_connector);
                    spinner32.setAdapter(adapter32);
                    spinner32.setVisibility(View.VISIBLE);

                    spinner_power_output.clear();
                    spinner_power_output.add("Power output");
                    Retrofit retrofit2 = new Retrofit.Builder()
                            .baseUrl("http://elshare.in/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    APIInterface service2 = retrofit2.create(APIInterface.class);

                    Call<List<commertial_power_output>> call2 = service2.getPowerOutputCommertial(model_str, brand_str);
                    Log.i(" Commertial power***********************************************************************************************  : ", String.valueOf(call2.request().url()));
                    call2.enqueue(new Callback<List<commertial_power_output>>() {
                        @Override
                        public void onResponse(Call<List<commertial_power_output>> call2, Response<List<commertial_power_output>> response) {
                            if (response.body() != null) {
                                power_com = response.body();

                                ArrayList make_array = (ArrayList) response.body();


                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    power_c = power_com.get(i);
                                    demo_str = power_c.getPowerOutputCommertial();
                                    spinner_power_output.add(demo_str);
                                    Log.i("Commertial Power: ", spinner_power_output.toString());
                                }

                            } else {
                                String new_str = "Commertial Power: ";
                                Log.i("Commertial Power: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<commertial_power_output>> call2, Throwable t) {
                            Log.i("Commertial Power: ", String.valueOf(t));

                        }
                    });


                    spinner_voltage.clear();
                    spinner_voltage.add("Voltage");
                    Call<List<commertial_voltage>> call6 = service.getVoltageComSocketCommertial(model_str, brand_str);
                    Log.i(" Commertial Voltage***********************************************************************************************  : ", String.valueOf(call6.request().url()));
                    call6.enqueue(new Callback<List<commertial_voltage>>() {
                        @Override
                        public void onResponse(Call<List<commertial_voltage>> call6, Response<List<commertial_voltage>> response) {
                            if (response.body() != null) {
                                volt_com = response.body();

                                ArrayList make_array = (ArrayList) response.body();


                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    volt_c = volt_com.get(i);
                                    demo_str = volt_c.getVoltageComSocketCommertial();
                                    spinner_voltage.add(demo_str);
                                    Log.i("Commertial Voltage: ", spinner_voltage.toString());
                                }

                            } else {
                                String new_str = "Commertial Voltage: ";
                                Log.i("Commertial Voltage: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<commertial_voltage>> call6, Throwable t) {
                            Log.i("Commertial Voltage: ", String.valueOf(t));

                        }
                    });

                    charger_amphere.clear();
                    charger_amphere.add("Amphere");
                    Call<List<commertial_charger_amphere>> call7 = service.getChargerAmphereCom(model_str, brand_str);
                    Log.i(" Amphere Voltage***********************************************************************************************  : ", String.valueOf(call7.request().url()));
                    call7.enqueue(new Callback<List<commertial_charger_amphere>>() {
                        @Override
                        public void onResponse(Call<List<commertial_charger_amphere>> call7, Response<List<commertial_charger_amphere>> response) {
                            if (response.body() != null) {
                                charger_amp_array = response.body();

                                ArrayList make_array = (ArrayList) response.body();


                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    charger_amp = charger_amp_array.get(i);
                                    demo_str = charger_amp.getChargerAmphereCom();
                                    charger_amphere.add(demo_str);
                                    Log.i("Commertial Amphere: ", charger_amphere.toString());
                                }

                            } else {
                                String new_str = "Commertial Amphere: ";
                                Log.i("Commertial Amphere: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<commertial_charger_amphere>> call7, Throwable t) {
                            Log.i("Commertial Amphere: ", String.valueOf(t));

                        }
                    });

                    if (spinner_voltage.size()==1 && charger_amphere.size()==1) {
                        ArrayAdapter<String> adapter64 = new ArrayAdapter<>(edit_commertial_listing.this, R.layout.sample_text, spinner_power_output);
                        spinner64.setAdapter(adapter64);
                        spinner64.setVisibility(View.VISIBLE);
                        spinner65.setVisibility(View.GONE);
                        charger_A.setVisibility(View.GONE);
                    }
                    else {
                        ArrayAdapter<String> adapter65 = new ArrayAdapter<>(edit_commertial_listing.this, R.layout.sample_text, spinner_voltage);
                        spinner65.setAdapter(adapter65);
                        spinner65.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter_C_A = new ArrayAdapter<>(edit_commertial_listing.this, R.layout.sample_text, charger_amphere);
                        charger_A.setAdapter(adapter_C_A);
                        charger_A.setVisibility(View.VISIBLE);
                        spinner64.setVisibility(View.GONE);
                    }

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spinner32.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                cs_connector_str=spinner32.getSelectedItem().toString();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner33.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_board=spinner33.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner34.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_rate=spinner34.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner60.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                socket_str=spinner60.getSelectedItem().toString();
                if (socket_str.equals("Select socket"))
                {
                    spinner63.setVisibility(View.GONE);
                    socket_A.setVisibility(View.GONE);

                }
                else {
                    String socket_str = spinner60.getSelectedItem().toString();
                    String state_str = state_text.getText().toString();

                    socket_voltage.clear();
                    socket_voltage.add("Socket voltage");
                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<commertial_voltage_socket>> call = service.getVoltageComSocket(socket_str);
                    Log.i("Socket Commertial Voltage***********************************************************************************************  : ", String.valueOf(call.request().url()));
                    call.enqueue(new Callback<List<commertial_voltage_socket>>() {
                        @Override
                        public void onResponse(Call<List<commertial_voltage_socket>> call, Response<List<commertial_voltage_socket>> response) {
                            if (response.body() != null) {
                                socket_volt = response.body();

                                ArrayList make_array = (ArrayList) response.body();


                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    com_voltage = socket_volt.get(i);
                                    demo_str = com_voltage.getVoltageComSocket();
                                    socket_voltage.add(demo_str);
                                    Log.i("Socket Commertial Voltage: ", socket_voltage.toString());
                                }

                            } else {
                                String new_str = "Socket Commertial Voltage is empty";
                                Log.i("Socket Commertial Voltage :", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<commertial_voltage_socket>> call, Throwable t) {
                            Log.i(" Socket Commertial Voltage: ", String.valueOf(t));

                        }
                    });
                    ArrayAdapter<String> adapter63 = new ArrayAdapter<>(edit_commertial_listing.this, R.layout.sample_text, socket_voltage);
                    spinner63.setAdapter(adapter63);
                    spinner63.setVisibility(View.VISIBLE);

                    Call<List<commertial_socket_amphere>> call2 = service.getSocketAmphereCom(socket_str);
                    Log.i("Socket Commertial Amphere***********************************************************************************************  : ", String.valueOf(call2.request().url()));
                    call2.enqueue(new Callback<List<commertial_socket_amphere>>() {
                        @Override
                        public void onResponse(Call<List<commertial_socket_amphere>> call2, Response<List<commertial_socket_amphere>> response) {
                            if (response.body() != null) {
                                socket_amp_array = response.body();

                                ArrayList make_array = (ArrayList) response.body();


                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    socket_amp = socket_amp_array.get(i);
                                    demo_str = socket_amp.getSocketAmphereCom();
                                    socket_amphere.add(demo_str);
                                    Log.i("Socket Commertial Amphere: ", socket_amphere.toString());
                                }

                            } else {
                                String new_str = "Socket Commertial Amphere is empty";
                                Log.i("Socket Commertial Amphere :", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<commertial_socket_amphere>> call2, Throwable t) {
                            Log.i(" Socket Commertial Amphere: ", String.valueOf(t));

                        }
                    });
                    ArrayAdapter<String> adapter_S_A = new ArrayAdapter<>(edit_commertial_listing.this, R.layout.sample_text, socket_amphere);
                    socket_A.setAdapter(adapter_S_A);
                    socket_A.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner61.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_board_str=spinner61.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner62.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_rate_str=spinner62.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner63.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_vtg=spinner63.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner64.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_power=spinner64.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner65.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_vtg=spinner65.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        charger_A.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_amp=charger_A.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        socket_A.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_amp_str=socket_A.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bubbleSeekBar2.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                final Double rate_integr= Double.valueOf(state_rate_edit.getText().toString());
                String s = String.format(Locale.CHINA, " %.1f", progressFloat);

                Double bubble_str = Double.valueOf(s);
                final_profit_double = (double) rate_integr / (double) bubble_str;
                final_profit = String.valueOf(final_profit_double);
                profit_edit.setText(final_profit);
                Log.i("Profit is", final_profit);
            }

//            @Override
//            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
//                String s = String.format(Locale.CHINA, "onActionUp int:%d, float:%.1f", progress, progressFloat);
//                profit_value.setText(s);
//            }
//
//            @Override
//            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
//                String s = String.format(Locale.CHINA, "onFinally int:%d, float:%.1f", progress, progressFloat);
//                profit_value.setText(s);
//            }
        });

        all_day_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (multi_single_day.isChecked()) {
                    all_day_str="not_all_day";

                    ///Single day
                    if (mon_d.isChecked()) {
                        if (isMonday) {

                            all_mon = "all_day";
                        } else {
                            all_mon = "not_all_day";
                        }
                    }
                    else if (tue_d.isChecked()) {
                        if (isTuesday)
                        {

                            all_tue = "all_day";
                        }
                        else
                        {
                            all_tue = "not_all_day";
                        }
                    }
                    else if (wed_d.isChecked()) {
                        if (isWednesday) {

                            all_wed = "all_day";
                        } else {
                            all_wed = "not_all_day";
                        }
                    }
                    else if (thr_d.isChecked()) {
                        if (isThuresday) {

                            all_thr = "all_day";
                        } else {
                            all_thr = "not_all_day";
                        }
                    }
                    else if (fri_d.isChecked()) {
                        if (isFriday) {

                            all_fri = "all_day";
                        } else {
                            all_fri = "not_all_day";
                        }
                    }
                    else if (sat_d.isChecked()) {
                        if (isSatursday) {

                            all_sat = "all_day";
                        } else {
                            all_sat = "not_all_day";
                        }
                    }
                    else if (sun_d.isChecked()) {
                        if (isSunday) {

                            all_sun = "all_day";
                        } else {
                            all_sun = "not_all_day";
                        }
                    }

                }
                else
                {
                    //Multi day
                    all_day_str="all_day";
                    all_mon = "not_all_day";
                    all_tue = "not_all_day";
                    all_wed = "not_all_day";
                    all_thr = "not_all_day";
                    all_fri = "not_all_day";
                    all_sat = "not_all_day";
                    all_sun = "not_all_day";

                }
            }
        });

        start_time_txt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(edit_commertial_listing.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = "AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = "PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = "PM";

                                }
                                else {

                                    format = "AM";
                                }
                                if (multi_single_day.isChecked())
                                {
                                    if (mon_d.isChecked() &&isMonday==true)
                                    {

                                        start_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=start_time_txt.getText().toString();
                                        mon_start.add(end_str_time);
                                    }
                                    else if (tue_d.isChecked() && isTuesday==true)
                                    {
                                        start_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=start_time_txt.getText().toString();
                                        tue_start.add(end_str_time);
                                    }
                                    else  if(wed_d.isChecked() &&isWednesday==true)
                                    {
                                        start_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=start_time_txt.getText().toString();
                                        wed_start.add(end_str_time);
                                    }
                                    else if (thr_d.isChecked() && isThuresday==true)
                                    {
                                        start_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=start_time_txt.getText().toString();
                                        thr_start.add(end_str_time);
                                    }
                                    else if (fri_d.isChecked() && isFriday==true)
                                    {
                                        start_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=start_time_txt.getText().toString();
                                        fri_start.add(end_str_time);
                                    }
                                    else if (sat_d.isChecked() && isSatursday==true)
                                    {
                                        start_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=start_time_txt.getText().toString();
                                        sat_start.add(end_str_time);
                                    }
                                    else if (sun_d.isChecked() && isSunday==true)
                                    {
                                        start_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=start_time_txt.getText().toString();
                                        sun_start.add(end_str_time);
                                    }
                                }else
                                {
//                                           start_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                    start_time_txt.setText(hourOfDay + ":" + minute );

                                    String start_str_time=start_time_txt.getText().toString();
                                    all_day_start.add(start_str_time);



                                }


                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

            }

        });
        end_time_txt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(edit_commertial_listing.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = "AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = "PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = "PM";

                                }
                                else {

                                    format = "AM";
                                }

                                if (multi_single_day.isChecked())
                                {

                                    if (mon_d.isChecked() && isMonday==true)
                                    {

                                        end_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=end_time_txt.getText().toString();
                                        mon_end.add(end_str_time);
                                    }
                                    else if (tue_d.isChecked() && isTuesday==true)
                                    {
                                        end_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=end_time_txt.getText().toString();
                                        tue_end.add(end_str_time);
                                    }
                                    else  if(wed_d.isChecked() && isWednesday==true)
                                    {
                                        end_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=end_time_txt.getText().toString();
                                        wed_end.add(end_str_time);
                                    }
                                    else if (thr_d.isChecked() && isThuresday==true)
                                    {
                                        end_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=end_time_txt.getText().toString();
                                        thr_end.add(end_str_time);
                                    }
                                    else if (fri_d.isChecked() && isFriday==true)
                                    {
                                        end_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=end_time_txt.getText().toString();
                                        fri_end.add(end_str_time);
                                    }
                                    else if (sat_d.isChecked() && isSatursday==true)
                                    {
                                        end_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=end_time_txt.getText().toString();
                                        sat_end.add(end_str_time);
                                    }
                                    else if (sun_d.isChecked() && isSunday==true)
                                    {
                                        end_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=end_time_txt.getText().toString();
                                        sun_end.add(end_str_time);
                                    }
                                }else
                                {
//                                                    end_txt.setText(hourOfDay + ":" + minute + " "+ format);
//                                    editText2.setText(hourOfDay + ":" + minute );
                                    end_time_txt.setText(CalendarHour + ":" + CalendarMinute);
                                    String end_str_time=end_time_txt.getText().toString();
                                    all_day_end.add(end_str_time);
                                }

                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

            }
        });


        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (brand_str.equals("Charger Brand") || brand_str.isEmpty())
                {
                    brand_str=null;
                }
                if (CS_model_str.equals("Charger Model"))
                {
                    CS_model_str=null;
                }
                if (socket_str.equals("Select socket"))
                {
                    socket_str=null;
                }
                if(mon_end.isEmpty())
                {
                    mon_end.add("");
                    mon_start.add("");
                }


                Log.e("TAG", "Place: " + charger_type_str + ", " + charger_brand + "," + charger_model + "," + socket_board_str + cs_connector_str + cs_power + cs_vtg + state_text + address_1 + address_2 + city_txt + country_txt + landemark_txt + zip_txt + latitude + longitude);
                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(edit_commertial_listing.this);

                if (charger_type_str.equals("Charging Station"))
                {
                    final_amphere=cs_amp;
                    final_voltage=cs_vtg;
                    final_power=cs_power;
                    final_rate=cs_rate;
                    final_board=cs_board;
                }else if (charger_type_str.equals("Standard Domestic Socket"))
                {
                    final_amphere=socket_amp_str;
                    final_voltage=socket_vtg;
                    final_power=null;
                    final_rate=socket_rate_str;
                    final_board=socket_board_str;
                }
                ArrayList<String> custum_brand = new ArrayList<String>();
                ArrayList<String> custum_model = new ArrayList<String>();
                ArrayList<String> custom_connector_type_public = new ArrayList<String>();
                ArrayList<String> custom_voltage_public = new ArrayList<String>();
                ArrayList<String> custom_power_output_public = new ArrayList<String>();
                custum_brand.add("null");
                custum_model.add("null");
                custom_connector_type_public.add("null");
                custom_voltage_public.add("null");
                custom_power_output_public.add("null");


                String[] start_time_public = new String[]{"00:00"};
                String[] end_time_public = new String[]{"00:00"};


                String[] start_time_all = new String[]{"null"};
                String[] end_time_all = new String[]{"null"};
                String address_txt = address_1.getText().toString();
                String address2_txt = address_2.getText().toString();
                String land_str = landemark_txt.getText().toString();
                String city_str = city_txt.getText().toString();
                String country_str = country_txt.getText().toString();
                String state_str = state_text.getText().toString();
                int pin = Integer.parseInt(zip_txt.getText().toString());
                String profit_str=profit_edit.getText().toString();

                //id==listing id and user_id=logged user id

                APIInterface service = SingletonRetrofit.getAPIInterface();
                Log.i("Call base url", String.valueOf(SingletonRetrofit.getRetrofit().baseUrl()));
                Log.i("Call converter", String.valueOf(SingletonRetrofit.getRetrofit().converterFactories()));
                Call<ResponseBody> call = null; //service.edit_commercial_list2(id,user_id,address_txt,address2_txt,land_str,city_str,state_str,country_str,latitude,longitude,pin,final_board,final_rate,profit_str,selected,charger_type_str,cs_connector_str,brand_str,final_voltage,final_amphere,checked,CS_model_str,final_power,custum_brand,custum_model,socket_str,custom_connector_type_public,custom_voltage_public,custom_power_output_public,all_day,single_day,all_day_start,all_day_end,mon_start,mon_end,tue_start,tue_end,wed_start,wed_end,thr_start,thr_end,fri_start,fri_end,sat_start,sat_end,sun_start,sun_end,start_time_all,end_time_all,all_day_str,all_mon,all_tue,all_wed,all_thr,all_fri,all_sat,all_sun);
                Log.i("Call", String.valueOf(call.request().url()));

                Log.i("builder", String.valueOf(call.request().newBuilder()));
                Log.i("service", String.valueOf(service));
                Log.i("headers", String.valueOf(call.request().headers()));
                Log.i("call public", String.valueOf(call));
                Log.i("method", String.valueOf(call.request().method()));


                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.i("Residential response1:", String.valueOf(response.isSuccessful()));
                        Log.i("Residential response2:", String.valueOf(response.raw().isSuccessful()));
                        Log.i("Residential response3:", String.valueOf(response.errorBody()));
                        Toast.makeText(edit_commertial_listing.this, "Edit listing succesfully", Toast.LENGTH_LONG).show();

//                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(edit_residential_listing.this);
//                            alertDialogBuilder.setMessage("Edit Lsiting Succesfully!!!");
//                            alertDialogBuilder.setPositiveButton("Ok",
//                                    new DialogInterface.OnClickListener() {
//
//
//                                        @Override
//                                        public void onClick(DialogInterface arg0, int arg1) {
//
//                                        }
//                                    });
//                            AlertDialog alertDialog = alertDialogBuilder.create();
//                            alertDialog.show();
                        finish();
                        ///Future activity Navigate to MY Listing of login home navigation menu not implement at yet.
                    }


                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("Error", String.valueOf(t.getMessage()));

                        Toast.makeText(edit_commertial_listing.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });








    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId() + "," + place.getLatLng() + "," + place.getAddressComponents());
                address_1.setText(place.getAddress());
                address_2.setText(place.getName());
                latitude=place.getLatLng().latitude;
                longitude=place.getLatLng().longitude;
                Log.i("Latitude :", String.valueOf(latitude));
                Log.i("Longitude :", String.valueOf(longitude));
//                SharedPreferences.Editor editor = prefs.edit();
                List<AddressComponent> list = place.getAddressComponents().asList();
                for (int x = 0; x < list.size(); x++)
                {
                    AddressComponent ac = list.get(x);
                    List<String> types = ac.getTypes();
                    for (int y = 0; y < types.size(); y++)
                    {
                        String type = types.get(y);
                        if (type.equals("postal_code"))
                        {
                            String sZIP = ac.getName();
                            Log.i("TAG", "pincode: " + sZIP);
                            zip_txt.setText(sZIP);

                        }
                        else if (type.equals("administrative_area_level_2"))
                        {
                            String sCity = ac.getName();
                            Log.i("TAG", "city: " + sCity);
                            city_txt.setText(sCity);

                        }
                        else if (type.equals("administrative_area_level_1"))
                        {
                            String sState = ac.getName();
                            Log.i("TAG", "state: " + sState);
                            state_text.setText(sState);
                        }
                        else if (type.equals("country"))
                        {
                            String sCountry = ac.getName();
                            Log.i("TAG", "Country: " + sCountry);
                            country_txt.setText(sCountry);
                        }
                        else if (type.equals("sublocality_level_1") |(type.equals("sublocality_level_2") | (type.equals("route"))))
                        {
                            String sLM = ac.getName();
                            Log.i("TAG", "Landmark: " + sLM);
                            landemark_txt.setText(sLM);
                        }
                    }
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("TAG", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                Log.i("TAG", String.valueOf(resultCode));

            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
