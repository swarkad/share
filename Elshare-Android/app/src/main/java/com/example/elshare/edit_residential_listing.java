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

import datamodel.APIInterface;
import datamodel.Charegr_brand;
import datamodel.Connector_type;
import datamodel.Socket;
import datamodel.charger_model;
import datamodel.charger_type;
import datamodel.residential_board;
import datamodel.residential_board_socket;
import datamodel.residential_power_output;
import datamodel.residential_rate_socket;
import datamodel.residential_voltage_socket;
import datamodel.residetial_rate;
import datamodel.residrntial_voltage;
import datamodel.residential_show_list.show_residential;
import okhttp3.ResponseBody;
import residential_pojo.residential_charger_amphere;
import residential_pojo.residential_socket_amphere;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class edit_residential_listing extends Activity
{
    String newString,charger_brand_str;
    Spinner spinner_101,spinner_102,spinner_103,spinner_104,spinner_105,spinner_106,spinner_107,spinner_108,spinner_109,spinner_110,spinner_111,spinner_112,charger_A,socket_A;
    EditText address_2,country_txt,state_text,landemark_txt,zip_txt,city_txt;
    com.google.android.material.textfield.TextInputEditText address_1;
    EditText spinner_edit,start_time_txt,end_time_txt;
    int id;
    ImageButton delete_row;
    int checked;
    String format;
    TimePickerDialog timepickerdialog;
    private int CalendarHour, CalendarMinute;
    Calendar calendar;
    Button add_time_block,save_changes,all_day_button;
    TableLayout table;
    String final_board,final_rate,final_voltage,final_amphere,final_power;

    String charger_type_str,brand_str,cs_model_str,cs_connector_str,socket_str,socket_rate,power_str,vtg_str,socket_board_str,board_str,rate_str,socket_amp_str,charger_amp_str,socket_vtg_str;
    Switch instant_booking,multi_single_day;
    ArrayList<String> socket_eboard,socket_rate_array;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int RESULT_OK = -1;
    private static final int RESULT_CANCELED = -1;
    private show_residential show_resi_arry;
    float longitude,latitude;

    private List<charger_type> charger_t;
    private List<Charegr_brand> charger_brand;
    private List<datamodel.charger_model> charger_model;
    private List<residential_board> board_m;
    private List<residetial_rate> rate_m;
    private List<Connector_type> connetor_set;
    private List<Socket>  socket_set;
    private List<residential_power_output>  power_com;
    private List<residrntial_voltage>  volt_com;
    private List<residential_rate_socket>  socket_rate_res;
    private List<residential_board_socket>  socket_board_res;
    private  List<residential_voltage_socket> socket_vtg_res;
    private List<residential_charger_amphere> charger_amp_array;
    private List<residential_socket_amphere> socket_amp_array;


    private charger_type my_charger;
    private Charegr_brand my_brand;
    private charger_model my_model;
    private residential_board my_board;
    private residetial_rate my_rate;
    private Connector_type my_connector;
    private Socket my_socket;
    private residential_power_output power_c;
    private residrntial_voltage volt_c;
    private residential_rate_socket rate_socket;
    private residential_board_socket socket_board;
    private residential_voltage_socket socket_vtg;
    private residential_socket_amphere socket_amp;
    private residential_charger_amphere charger_amp;
    TextView edit_header;
    private CheckBox mon_d,tue_d,wed_d,thr_d,fri_d,sat_d,sun_d;
    Double final_profit_double;
    String final_profit;
    EditText profit_str,state_rate_edit,profit_edit;
    int selected,check,user_id,list_id;
    private Boolean isMonday,isTuesday,isWednesday,isThuresday,isFriday,isSatursday,isSunday;
    private  String all_mon,all_tue,all_wed,all_thr,all_fri,all_sat,all_sun,all_day_str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_residential_listing);
        edit_header=findViewById(R.id.edit_header);
        edit_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add_time_block=findViewById(R.id.add_another_Block_resi);
        BubbleSeekBar bubbleSeekBar2 = findViewById(R.id.buuble_resi);
        state_rate_edit=findViewById(R.id.rate_resi_edit);
        profit_edit=findViewById(R.id.profit_resi);
        save_changes=findViewById(R.id.edit_save_resi);
        all_day_button=findViewById(R.id.add_day_resi);
        all_mon="not_all_day";
        all_tue="not_all_day";
        all_wed="not_all_day";
        all_thr="not_all_day";
        all_fri="not_all_day";
        all_sat="not_all_day";
        all_sun="not_all_day";
        all_day_str="not_all_day";

        charger_type_str="";
        brand_str="";
        cs_model_str="";
        cs_connector_str="";
        socket_str="";
        socket_rate="";
        power_str="";
        vtg_str="";
        socket_board_str="";
        board_str="";
        rate_str="";




        bubbleSeekBar2.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                String s = String.format(Locale.CHINA, " %.1f", progressFloat);
                Double bubble_str = Double.valueOf(s);
                Log.i("Profit rate str:", state_rate_edit.getText().toString());

                final Double rate_integr = Double.valueOf(state_rate_edit.getText().toString());
                final_profit_double = rate_integr / (double) bubble_str;
                final_profit = String.valueOf(final_profit_double);
                Log.i("Profit is", final_profit);

                profit_str.setText(final_profit);
                Log.i("Profit is", final_profit);
            }
        });
        final ArrayList<String> single_day=new ArrayList<String>();
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
        final ArrayList<String> sun_end = new ArrayList<String>();
        final ArrayList<String> all_day = new ArrayList<String>();
        final ArrayList<String> all_day_start=new ArrayList<String>();
        final ArrayList<String> all_day_end=new ArrayList<String>();

        spinner_101=findViewById(R.id.spinner101);
        spinner_102=findViewById(R.id.spinner102);
        spinner_103=findViewById(R.id.spinner103);
        spinner_104=findViewById(R.id.spinner104);
        spinner_105 =findViewById(R.id.spinner105);
        spinner_106=findViewById(R.id.spinner106);
        spinner_107=findViewById(R.id.spinner107);
        spinner_108=findViewById(R.id.spinner108);
        spinner_109=findViewById(R.id.spinner109);
        spinner_110=findViewById(R.id.spinner110);
        spinner_111=findViewById(R.id.spinner111);
        spinner_112=findViewById(R.id.spinner112);
        charger_A=findViewById(R.id.Edit_res_A_C);
        socket_A=findViewById(R.id.Edit_res_A_S);
        instant_booking=findViewById(R.id.switch5);
        multi_single_day=findViewById(R.id.switch6);
        start_time_txt=findViewById(R.id.start_time_resi);
        end_time_txt=findViewById(R.id.end_time_resi);

        address_1=findViewById(R.id.editText90);
        address_2=findViewById(R.id.editText91);
        country_txt=findViewById(R.id.editText92);
        state_text=findViewById(R.id.editText93);
        landemark_txt=findViewById(R.id.editText94);
        zip_txt=findViewById(R.id.editText95);
        city_txt=findViewById(R.id.editText96);
        mon_d=findViewById(R.id.checkbox_1);
        tue_d=findViewById(R.id.checkbox_2);
        wed_d=findViewById(R.id.checkbox_3);
        thr_d=findViewById(R.id.checkbox_4);
        fri_d=findViewById(R.id.checkbox_5);
        sat_d=findViewById(R.id.checkbox_6);
        sun_d=findViewById(R.id.checkbox_7);
        mon_d.setChecked(false);
        tue_d.setChecked(false);
        wed_d.setChecked(false);
        thr_d.setChecked(false);
        fri_d.setChecked(false);
        sat_d.setChecked(false);
        sun_d.setChecked(false);

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
                                String demo_str = "satursday";
                                all_day.add(demo_str);

                                Toast.makeText(getApplicationContext(), demo_str, Toast.LENGTH_SHORT).show();
                            } else {

                                String demo_str = "satursday";
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

        start_time_txt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(v.getContext(),
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
                                    else if (tue_d.isChecked()&& isTuesday==true)
                                    {
                                        start_time_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                        String end_str_time=start_time_txt.getText().toString();
                                        tue_start.add(end_str_time);
                                    }
                                    else  if(wed_d.isChecked() && isWednesday==true)
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
                                    else if (fri_d.isChecked() &&  isFriday==true)
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
//                                                    end_txt.setText(hourOfDay + ":" + minute + " "+ format);
                                    start_time_txt.setText(hourOfDay + ":" + minute );
//                                    start_time_txt.setText(CalendarHour + ":" + CalendarMinute);
                                    String end_str_time=start_time_txt.getText().toString();
                                    all_day_start.add(end_str_time);
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


                timepickerdialog = new TimePickerDialog(v.getContext(),
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
                                    end_time_txt.setText(hourOfDay + ":" + minute );
//                                    end_time_txt.setText(CalendarHour + ":" + CalendarMinute);
                                    String end_str_time=end_time_txt.getText().toString();
                                    all_day_end.add(end_str_time);
                                }

                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

            }
        });
        add_time_block.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final TableRow row = (TableRow)LayoutInflater.from(v.getContext()).inflate(R.layout.sample_row, null);
                table = findViewById(R.id.table_resi_edit);

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

                        timepickerdialog = new TimePickerDialog(v.getContext(),
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
                                            end_time_txt.setText(hourOfDay + ":" + minute );
//                                    end_time_txt.setText(CalendarHour + ":" + CalendarMinute);
                                            String end_str_time=end_time_txt.getText().toString();
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


                        timepickerdialog = new TimePickerDialog(v.getContext(),
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
                                            else if (tue_d.isChecked() && isTuesday)
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

        final ArrayList<String> spinner_charger_type=new ArrayList<String>();
        final ArrayList<String> spinner_brand=new ArrayList<String>();
        final ArrayList<String> spinner_model=new ArrayList<String>();
        final ArrayList<String> spinner_board=new ArrayList<String>();
        final ArrayList<String> spinner_rate=new ArrayList<String>();
        final ArrayList<String> spinner_connector=new ArrayList<String>();
        final ArrayList<String> spinner_power_output=new ArrayList<String>();
        final ArrayList<String> spinner_voltage=new ArrayList<String>();
        final ArrayList<String> spinner_socket=new ArrayList<String>();
        final ArrayList<String> socket_rate_array= new ArrayList<>();
        final ArrayList<String> socket_eboard= new ArrayList<>();
        final ArrayList<String> socket_voltage= new ArrayList<>();
        final ArrayList<String> charger_Amphere=new ArrayList<String>();
        final ArrayList<String> socket_amphere=new ArrayList<String>();
        spinner_101.setVisibility(View.VISIBLE);
        spinner_102.setVisibility(View.GONE);
        spinner_103.setVisibility(View.GONE);
        spinner_104.setVisibility(View.GONE);
        spinner_105.setVisibility(View.GONE);
        spinner_106.setVisibility(View.GONE);
        spinner_107.setVisibility(View.GONE);
        spinner_108.setVisibility(View.GONE);
        spinner_110.setVisibility(View.GONE);
        spinner_111.setVisibility(View.GONE);
        spinner_112.setVisibility(View.GONE);

        final ArrayList<String> array1 = new ArrayList<String>();
        array1.add("Charging Station");
        array1.add("Standard Domestic Socket");
        ArrayAdapter<String> adapter101 = new ArrayAdapter<String>(edit_residential_listing.this, R.layout.sample_text, array1);
        spinner_101.setAdapter(adapter101);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {

        } else {
            newString = extras.getString("List_V_ID");
            id=Integer.parseInt(newString);
        }
        APIInterface service = SingletonRetrofit.getAPIInterface();

        Call<show_residential> call = service.getListResidential(id);
        Log.i("Call", String.valueOf(call.request().url()));

        call.enqueue(new Callback<show_residential>() {
            @Override
            public void onResponse(Call<show_residential> call, Response<show_residential> response) {

                if (response.body() != null) {
                    Log.i("Make  response: ", String.valueOf(response.body()));
                    show_resi_arry =  response.body();
                    ArrayList demo_str;
                    int id_res,id_listing,address_id;
                    int i;
//                           for (i = 0; i < make_array.size(); i++) {
                    Log.i("Day array: ", String.valueOf(show_resi_arry.getAddress()));
                    Log.i("Day array: ", String.valueOf(show_resi_arry.getAddress().getCountry()));
                    Log.i("Day array: ", String.valueOf(show_resi_arry.getDaysArray()));
                    address_1.setText(String.valueOf(show_resi_arry.getAddress().getAddress1()));
                    address_2.setText(String.valueOf(show_resi_arry.getAddress().getAddress2()));
                    country_txt.setText(String.valueOf(show_resi_arry.getAddress().getCountry()));
                    state_text.setText(String.valueOf(show_resi_arry.getAddress().getState()));
                    city_txt.setText(String.valueOf(show_resi_arry.getAddress().getCity()));
                    landemark_txt.setText(String.valueOf(show_resi_arry.getAddress().getLandmark()));
                    zip_txt.setText(String.valueOf(show_resi_arry.getAddress().getPin()));
//                     String charger_str=String.valueOf(show_resi_arry.getCharger());
                    String socket_str=String.valueOf(show_resi_arry.getSocket());
                    profit_edit.setText(String.valueOf(show_resi_arry.getProvider().getProfit()));
                    String rate_va=(String.valueOf(show_resi_arry.getRateStructure()));
                    socket_rate=String.valueOf(rate_va);
                    charger_brand_str=show_resi_arry.getChargerBrand().getChargerBrand();
                    Long value_select= (show_resi_arry.getProvider().getInstantBooking());
                    if (value_select==0)
                    {
                        instant_booking.setChecked(false);
                        selected=0;
                    }
                    else
                    {
                        instant_booking.setChecked(true);
                        selected=1;
                    }
                    Long day_value= (show_resi_arry.getProvider().getSingleDay());
                    if (day_value==0)
                    {
                        checked=0;
                        multi_single_day.setChecked(false);
                    }
                    else
                    {
                        checked=1;
                        multi_single_day.setChecked(true);
                    }
                    user_id=show_resi_arry.getData().getUserId();
                    list_id=show_resi_arry.getData().getId();

                    if(charger_brand_str!=null)
                    {
                        array1.clear();
                        array1.add("Charging Station");
                        array1.add("Standard Domestic Socket");
                        ArrayAdapter<String> adapter101 = new ArrayAdapter<String>(edit_residential_listing.this, R.layout.sample_text, array1);
                        spinner_101.setAdapter(adapter101);
                        spinner_101.setVisibility(View.VISIBLE);
                        spinner_102.setVisibility(View.VISIBLE);

                    }
                    else if(charger_brand_str.equals("null"))
                    {
                        array1.clear();
                        array1.add("Standard Domestic Socket");
                        array1.add("Charging Station");
                        ArrayAdapter<String> adapter101 = new ArrayAdapter<String>(edit_residential_listing.this,R.layout.sample_text, array1);
                        spinner_101.setAdapter(adapter101);
                        spinner_101.setVisibility(View.VISIBLE);
                        spinner_109.setVisibility(View.VISIBLE);


                    }
                    else
                    {
                        ArrayAdapter<String> adapter101 = new ArrayAdapter<String>(edit_residential_listing.this, R.layout.sample_text, array1);
                        spinner_101.setAdapter(adapter101);
                        spinner_101.setVisibility(View.VISIBLE);
                        spinner_102.setVisibility(View.VISIBLE);
                    }

                } else {
                    String new_str = "Make is empty";
                    Log.i("Make: ", new_str);
                    //  Toast.makeText(getContext(), "Please Select make", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<show_residential> call, Throwable t) {
                Log.i("Make error: ", String.valueOf(t));

            }
        });


        spinner_101.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String charger_model_str=spinner_101.getSelectedItem().toString();
                String state_str=state_text.getText().toString();

                if (state_str.isEmpty())
                {
                    Toast.makeText(edit_residential_listing.this,"State is empty",Toast.LENGTH_LONG).show();
                }
                else {
                    if (charger_model_str.equals("Charging Station")) {
                        spinner_102.setVisibility(View.VISIBLE);
                        spinner_109.setVisibility(View.GONE);
                        spinner_103.setVisibility(View.GONE);
                        spinner_104.setVisibility(View.GONE);
                        spinner_107.setVisibility(View.GONE);
                        spinner_108.setVisibility(View.GONE);
                        spinner_110.setVisibility(View.GONE);
                        spinner_111.setVisibility(View.GONE);
                        spinner_112.setVisibility(View.GONE);
                        charger_type_str = spinner_101.getSelectedItem().toString();

                        spinner_brand.clear();
                        spinner_brand.add("Charger Brand");
                        APIInterface service = SingletonRetrofit.getAPIInterface();

                        Call<List<Charegr_brand>> call = service.getBrand();
                        Log.i("Brand***********************************************************************************************  : ", String.valueOf(call.request().url()));
                        call.enqueue(new Callback<List<Charegr_brand>>() {
                            @Override
                            public void onResponse(Call<List<Charegr_brand>> call, Response<List<Charegr_brand>> response) {
                                if (response.body() != null) {
                                    charger_brand = response.body();

                                    ArrayList make_array = (ArrayList) response.body();


                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        my_brand = charger_brand.get(i);
                                        demo_str = my_brand.getChargerBrand();
                                        spinner_brand.add(demo_str);
                                        Log.i("login Array: ", spinner_brand.toString());
                                    }

                                } else {
                                    String new_str = "charger is empty";
                                    Log.i("login Array: ", new_str);

                                }
                            }

                            @Override
                            public void onFailure(Call<List<Charegr_brand>> call, Throwable t) {
                                Log.i("login error: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter_102 = new ArrayAdapter<>(edit_residential_listing.this, R.layout.sample_text, spinner_brand);
                        spinner_102.setAdapter(adapter_102);
                        spinner_102.setVisibility(View.VISIBLE);


                        spinner_rate.clear();
                        spinner_rate.add("Rate structure");
                        Retrofit retrofit4 = new Retrofit.Builder()
                                .baseUrl("http://elshare.in/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        final APIInterface service4 = retrofit4.create(APIInterface.class);
                        Call<List<residetial_rate>> call4 = service4.getResidentialRate(state_str);
                        call4.enqueue(new Callback<List<residetial_rate>>() {


                            @SuppressLint("LongLogTag")
                            @Override
                            public void onResponse(Call<List<residetial_rate>> call4, Response<List<residetial_rate>> response) {

                                if (response.body() != null) {
                                    Log.i("Residential Rate: ", String.valueOf(response.body()));
                                    rate_m = response.body();

                                    ArrayList make_array = (ArrayList) response.body();
                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        my_rate = rate_m.get(i);
                                        demo_str = my_rate.getRateStructure();
                                        spinner_rate.add(demo_str);
                                    }
                                } else {
                                    String new_str = "Residential rate is empty";
                                }
                            }


                            @SuppressLint("LongLogTag")
                            @Override
                            public void onFailure(Call<List<residetial_rate>> call4, Throwable t) {
                                Log.i("Residential rate error: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter_106 = new ArrayAdapter<>(edit_residential_listing.this, R.layout.sample_text, spinner_rate);
                        spinner_106.setAdapter(adapter_106);
                        spinner_106.setVisibility(View.VISIBLE);


//        Select socket board---------------------------------------------

                        spinner_board.clear();
                        spinner_board.add("Electricity board");
                        APIInterface service5 = SingletonRetrofit.getAPIInterface();
                        String state_str2 = state_text.getText().toString();

                        Call<List<residential_board>> call5 = service5.getResidentiallBoard(state_str2);
                        call5.enqueue(new Callback<List<residential_board>>() {
                            @Override
                            public void onResponse(Call<List<residential_board>> call5, Response<List<residential_board>> response) {
                                if (response.body() != null) {
                                    Log.i("Board residential: ", String.valueOf(response.body()));
                                    board_m = response.body();

                                    ArrayList make_array = (ArrayList) response.body();
                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        my_board = board_m.get(i);
                                        demo_str = my_board.getElectricityBoard();
                                        spinner_board.add(demo_str);
                                    }
                                } else {
                                    String new_str = "Board is empty";
                                }
                            }

                            @Override
                            public void onFailure(Call<List<residential_board>> call2, Throwable t) {
                                Log.i("login error: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter_105 = new ArrayAdapter<>(edit_residential_listing.this, R.layout.sample_text, spinner_board);
                        spinner_105.setAdapter(adapter_105);
                        spinner_105.setVisibility(View.VISIBLE);

                    } else if (charger_model_str.equals("Standard Domestic Socket")) {
                        spinner_102.setVisibility(View.GONE);
                        spinner_103.setVisibility(View.GONE);
                        spinner_104.setVisibility(View.GONE);
                        spinner_105.setVisibility(View.GONE);
                        spinner_106.setVisibility(View.GONE);
                        spinner_107.setVisibility(View.GONE);
                        spinner_108.setVisibility(View.GONE);
                        spinner_109.setVisibility(View.VISIBLE);
                        spinner_111.setVisibility(View.VISIBLE);
                        spinner_112.setVisibility(View.VISIBLE);

                        spinner_110.setVisibility(View.GONE);
                        spinner_socket.clear();
                        spinner_socket.add("Select socket");
                        APIInterface service = SingletonRetrofit.getAPIInterface();

                        Call<List<Socket>> call = service.getSocket();
                        Log.i("Socket***********************************************************************************************  : ", String.valueOf(call.request().url()));
                        call.enqueue(new Callback<List<Socket>>() {
                            @Override
                            public void onResponse(Call<List<Socket>> call, Response<List<Socket>> response) {
                                if (response.body() != null) {
                                    socket_set = response.body();
                                    ArrayList make_array = (ArrayList) response.body();
                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        my_socket = socket_set.get(i);
                                        demo_str = my_socket.getSocket();
                                        spinner_socket.add(demo_str);
                                    }

                                } else {
                                    String new_str = "charger is empty";
                                    Log.i("login Array: ", new_str);

                                }
                            }

                            @Override
                            public void onFailure(Call<List<Socket>> call, Throwable t) {
                                Log.i("login error: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter_109 = new ArrayAdapter<>(edit_residential_listing.this, R.layout.sample_text, spinner_socket);
                        spinner_109.setAdapter(adapter_109);
                        spinner_109.setVisibility(View.VISIBLE);


//        Select rate of socket------------------------------------------
                        socket_rate_array.clear();
                        socket_rate_array.add("select socket rate");
                        APIInterface service4 = SingletonRetrofit.getAPIInterface();
                        Call<List<residential_rate_socket>> call4 = service4.getRateStructureSocketRes(state_str);
                        call4.enqueue(new Callback<List<residential_rate_socket>>() {
                            @SuppressLint("LongLogTag")
                            @Override
                            public void onResponse(Call<List<residential_rate_socket>> call4, Response<List<residential_rate_socket>> response) {

                                if (response.body() != null) {
                                    Log.i("Residential socket Rate: ", String.valueOf(response.body()));
                                    socket_rate_res = response.body();

                                    ArrayList make_array = (ArrayList) response.body();


                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        rate_socket = socket_rate_res.get(i);
                                        demo_str = rate_socket.getRateStructureSocketRes();
                                        Log.i("Residential socket Rate R: ", String.valueOf(demo_str));
                                        socket_rate_array.add(demo_str);
                                        Log.i("Residential socket rate Array: ", socket_rate_array.toString());
                                    }
                                } else {
                                    String new_str = "Residential socket rate is empty";
                                }
                            }


                            @SuppressLint("LongLogTag")
                            @Override
                            public void onFailure(Call<List<residential_rate_socket>> call4, Throwable t) {
                                Log.i("Residential socket rate error: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter_112 = new ArrayAdapter<>(edit_residential_listing.this, R.layout.sample_text, socket_rate_array);
                        spinner_112.setAdapter(adapter_112);
                        spinner_112.setVisibility(View.VISIBLE);

//        Select socket board---------------------------------------------
                        socket_eboard.clear();
                        socket_eboard.add("Select board");
                        APIInterface service5 = SingletonRetrofit.getAPIInterface();

                        Call<List<residential_board_socket>> call5 = service5.getElectricityBoardSocketRes(state_str);
                        call5.enqueue(new Callback<List<residential_board_socket>>() {
                            @Override
                            public void onResponse(Call<List<residential_board_socket>> call5, Response<List<residential_board_socket>> response) {
                                if (response.body() != null) {
                                    Log.i("socket board response: ", String.valueOf(response.body()));
                                    socket_board_res = response.body();

                                    ArrayList make_array = (ArrayList) response.body();


                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        socket_board = socket_board_res.get(i);
                                        demo_str = socket_board.getElectricityBoardSocketRes();
                                        Log.i("socket board response: ", demo_str);
                                        //  make_array.add(my_make.getMy_make());
                                        socket_eboard.add(demo_str);
                                        Log.i("Socket board Array: ", socket_eboard.toString());
                                    }
                                } else {
                                    String new_str = "Socket board is empty";
                                }
                            }

                            @Override
                            public void onFailure(Call<List<residential_board_socket>> call5, Throwable t) {
                                Log.i("Socket board error: ", String.valueOf(t));

                            }
                        });


                        ArrayAdapter<String> adapter_111 = new ArrayAdapter<>(edit_residential_listing.this, R.layout.sample_text, socket_eboard);
                        spinner_111.setAdapter(adapter_111);
                        spinner_111.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        spinner_102.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brand_str=spinner_102.getSelectedItem().toString();
                String model_str=spinner_102.getSelectedItem().toString();
                if (brand_str.equals("Charger Brand"))
                {
                    spinner_103.setVisibility(View.GONE);

                }
                else {
                    spinner_model.clear();
                    spinner_model.add("Charger Model");
                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<charger_model>> call = service.getModel(model_str);
                    Log.i("Model_________________________________________________________ : ", String.valueOf(call.request().url()));

                    call.enqueue(new Callback<List<charger_model>>() {
                        @Override
                        public void onResponse(Call<List<charger_model>> call, Response<List<charger_model>> response) {
                            if (response.body() != null) {
                                charger_model = response.body();
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    my_model = charger_model.get(i);
                                    demo_str = my_model.getChargerModel();
                                    spinner_model.add(demo_str);
                                }

                            } else {
                                String new_str = "Model is empty";
                            }
                        }

                        @Override
                        public void onFailure(Call<List<charger_model>> call, Throwable t) {
                            Log.i("login error: ", String.valueOf(t));

                        }
                    });
                    ArrayAdapter<String> adapter_103 = new ArrayAdapter<>(edit_residential_listing.this, R.layout.sample_text, spinner_model);
                    spinner_103.setAdapter(adapter_103);
                    spinner_103.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_103.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String state_str=state_text.getText().toString();
                Log.i("State  : ", state_str);
                cs_model_str=spinner_103.getSelectedItem().toString();
                if (cs_model_str.equals("Charger Model"))
                {
                    spinner_104.setVisibility(View.GONE);
                    charger_A.setVisibility(View.GONE);
                    spinner_108.setVisibility(View.GONE);
                    spinner_107.setVisibility(View.GONE);

                }
                else {
                    String model_str = spinner_103.getSelectedItem().toString();
                    spinner_connector.clear();
                    spinner_connector.add("Connector Type");
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://elshare.in/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    APIInterface service = retrofit.create(APIInterface.class);

                    Call<List<Connector_type>> call = service.getConnector();
                    Log.i("Connector_api  : ", String.valueOf(call.request().url()));

                    call.enqueue(new Callback<List<Connector_type>>() {
                        @Override
                        public void onResponse(Call<List<Connector_type>> call, Response<List<Connector_type>> response) {
                            if (response.body() != null) {
                                Log.i("login response: ", String.valueOf(response.body()));
                                connetor_set = response.body();
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    my_connector = connetor_set.get(i);
                                    demo_str = my_connector.getConnectorType();
                                    spinner_connector.add(demo_str);
                                }

                            } else {
                                String new_str = "connector is empty";
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Connector_type>> call, Throwable t) {
                            Log.i("login error: ", String.valueOf(t));

                        }
                    });
                    ArrayAdapter<String> adapter_104 = new ArrayAdapter<>(edit_residential_listing.this, R.layout.sample_text, spinner_connector);
                    spinner_104.setAdapter(adapter_104);
                    spinner_104.setVisibility(View.VISIBLE);

                    spinner_power_output.clear();
                    spinner_voltage.clear();
                    charger_Amphere.clear();
                    spinner_voltage.add("Voltage");
                    spinner_power_output.add("Power output");
                    charger_Amphere.add("Amphere");

                    Call<List<residential_power_output>> call2 = service.getPowerOutputRes(brand_str ,model_str);
                    Log.i("power Res***********************************************************************************************  : ", String.valueOf(call2.request().url()));
                    call2.enqueue(new Callback<List<residential_power_output>>() {
                        @Override
                        public void onResponse(Call<List<residential_power_output>> call, Response<List<residential_power_output>> response) {
                            if (response.body() != null) {
                                power_com = response.body();

                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    power_c = power_com.get(i);
                                    demo_str = power_c.getPowerOutputRes();
                                    spinner_power_output.add(demo_str);
                                    Log.i("power output Array: ", spinner_power_output.toString());
                                }

                            } else {
                                String new_str = "Power Output is empty";
                                Log.i("Power output: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<residential_power_output>> call2, Throwable t) {
                            Log.i("Pewer output Res error: ", String.valueOf(t));

                        }
                    });

                    Call<List<residrntial_voltage>> call6 = service.getVoltageRes(brand_str ,model_str);
                    Log.i("Voltage***********************************************************************************************  : ", String.valueOf(call6.request().url()));
                    call6.enqueue(new Callback<List<residrntial_voltage>>() {
                        @Override
                        public void onResponse(Call<List<residrntial_voltage>> call6, Response<List<residrntial_voltage>> response) {
                            if (response.body() != null) {
                                volt_com = response.body();

                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    volt_c = volt_com.get(i);
                                    demo_str = volt_c.getVoltageRes();
                                    spinner_voltage.add(demo_str);
                                    Log.i("Voltage output Array: ", spinner_voltage.toString());
                                }

                            } else {
                                String new_str = "Voltage Output is empty";
                                Log.i("Voltage output: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<residrntial_voltage>> call6, Throwable t) {
                            Log.i("Voltage output error: ", String.valueOf(t));

                        }
                    });

                    Call<List<residential_charger_amphere>> call7 = service.getChargerAmphereRes(brand_str ,model_str);
                    Log.i("Amphere***********************************************************************************************  : ", String.valueOf(call6.request().url()));
                    call7.enqueue(new Callback<List<residential_charger_amphere>>() {
                        @Override
                        public void onResponse(Call<List<residential_charger_amphere>> call7, Response<List<residential_charger_amphere>> response) {
                            if (response.body() != null) {
                                charger_amp_array = response.body();

                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    charger_amp = charger_amp_array.get(i);
                                    demo_str = charger_amp.getChargerAmphereRes();
                                    charger_Amphere.add(demo_str);
                                    Log.i("Amphere output Array: ", charger_Amphere.toString());
                                }

                            } else {
                                String new_str = "Amphere Output is empty";
                                Log.i("Amphere output: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<residential_charger_amphere>> call7, Throwable t) {
                            Log.i("Amphere output error: ", String.valueOf(t));

                        }
                    });

                    if (spinner_voltage.size()==1)
                    {
                        ArrayAdapter<String> adapter_107 = new ArrayAdapter<>(edit_residential_listing.this, R.layout.sample_text, spinner_power_output);
                        spinner_107.setAdapter(adapter_107);
                        spinner_107.setVisibility(View.VISIBLE);
                        spinner_108.setVisibility(View.GONE);
                        charger_A.setVisibility(View.GONE);

                    }
                    else
                    {
                        ArrayAdapter<String> adapter_108 = new ArrayAdapter<>(edit_residential_listing.this, R.layout.sample_text, spinner_voltage);
                        spinner_108.setAdapter(adapter_108);
                        spinner_108.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter_C_A = new ArrayAdapter<>(edit_residential_listing.this, R.layout.sample_text, charger_Amphere);
                        charger_A.setAdapter(adapter_C_A);
                        charger_A.setVisibility(View.VISIBLE);
                        spinner_108.setVisibility(View.VISIBLE);
                        spinner_107.setVisibility(View.GONE);

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });//model
        spinner_104.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                cs_connector_str=spinner_104.getSelectedItem().toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });//cs_connector
        spinner_109.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_str= spinner_109.getSelectedItem().toString();
                String state_str=state_text.getText().toString();
                if(socket_str.equals("Select socket"))
                {
                    spinner_110.setVisibility(View.GONE);
                    socket_A.setVisibility(View.GONE);

                }
                else {
                    socket_voltage.clear();
                    socket_voltage.add("socket voltage");

//        Select voltage of socket------------------------------------------
                    String socket_str = spinner_109.getSelectedItem().toString();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://elshare.in/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    APIInterface service = retrofit.create(APIInterface.class);
                    Call<List<residential_voltage_socket>> call4 = service.getVoltageSocketRes( socket_str);
                    call4.enqueue(new Callback<List<residential_voltage_socket>>() {


                        @SuppressLint("LongLogTag")
                        @Override
                        public void onResponse(Call<List<residential_voltage_socket>> call4, Response<List<residential_voltage_socket>> response) {

                            if (response.body() != null) {
                                Log.i("Residential socket vtg: ", String.valueOf(response.body()));
                                socket_vtg_res = response.body();
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    //  Log.i("login response: ", String.valueOf((make_array.get(i))));
                                    socket_vtg = socket_vtg_res.get(i);
                                    demo_str = socket_vtg.getVoltageSocketRes();
                                    Log.i("Residential socket vtg R: ", String.valueOf(demo_str));
                                    //  make_array.add(my_make.getMy_make());
                                    socket_voltage.add(demo_str);
                                    Log.i("Residential socket vtg Array: ", socket_voltage.toString());
                                }
//                Log.i("final Array: ", make_array.toString());
                            } else {
                                String new_str = "Residential socket vtg is empty";
                            }
                        }


                        @SuppressLint("LongLogTag")
                        @Override
                        public void onFailure(Call<List<residential_voltage_socket>> call4, Throwable t) {
                            Log.i("Residential socket vtg error: ", String.valueOf(t));

                        }
                    });

                    ArrayAdapter<String> adapter_110 = new ArrayAdapter<>(edit_residential_listing.this, R.layout.sample_text, socket_voltage);
                    spinner_110.setAdapter(adapter_110);
                    spinner_110.setVisibility(View.VISIBLE);

                    socket_amphere.clear();
                    socket_amphere.add("Amphere");

//        Select voltage of socket------------------------------------------

                    Call<List<residential_socket_amphere>> call2 = service.getSocketAmphereRes( socket_str);
                    call2.enqueue(new Callback<List<residential_socket_amphere>>() {


                        @SuppressLint("LongLogTag")
                        @Override
                        public void onResponse(Call<List<residential_socket_amphere>> call2, Response<List<residential_socket_amphere>> response) {

                            if (response.body() != null) {
                                Log.i("Residential socket amp: ", String.valueOf(response.body()));
                                socket_amp_array = response.body();
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    //  Log.i("login response: ", String.valueOf((make_array.get(i))));
                                    socket_amp = socket_amp_array.get(i);
                                    demo_str = socket_amp.getSocketAmphereRes();
                                    Log.i("Residential socket amp R: ", String.valueOf(demo_str));
                                    //  make_array.add(my_make.getMy_make());
                                    socket_amphere.add(demo_str);
                                    Log.i("Residential socket amp Array: ", socket_amphere.toString());
                                }
                            } else {
                                String new_str = "Residential socket amp is empty";
                            }
                        }


                        @SuppressLint("LongLogTag")
                        @Override
                        public void onFailure(Call<List<residential_socket_amphere>> call2, Throwable t) {
                            Log.i("Residential socket amp error: ", String.valueOf(t));

                        }
                    });

                    ArrayAdapter<String> adapter_A_S = new ArrayAdapter<>(edit_residential_listing.this, R.layout.sample_text, socket_amphere);
                    socket_A.setAdapter(adapter_A_S);
                    socket_A.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });//socket_str

        spinner_111.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                socket_board_str=spinner_111.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_105.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                board_str=spinner_105.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_106.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                rate_str=spinner_106.getSelectedItem().toString();
                state_rate_edit.setText(spinner_106.getSelectedItem().toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_112.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                socket_rate=spinner_112.getSelectedItem().toString();
                state_rate_edit.setText(socket_rate);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_107.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                power_str=spinner_107.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_108.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                vtg_str=spinner_108.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_110.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                socket_vtg_str=spinner_110.getSelectedItem().toString();//socket_vtg


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        charger_A.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                charger_amp_str=charger_A.getSelectedItem().toString();//chrager_amp


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });//charger_amp
        socket_A.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                socket_amp_str=socket_A.getSelectedItem().toString();//socket_Amp


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });//socket_amp
        all_day_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (multi_single_day.isChecked()) {
                    all_day_str="not_all_day";

                    ///Single day
                    if (mon_d.isChecked()) {
                        if (isMonday) {

                            all_mon = "all_day";
                            single_day.add("monday");
                        } else {
                            all_mon = "not_all_day";
                        }
                    }
                    else if (tue_d.isChecked()) {
                        if (isTuesday)
                        {

                            all_tue = "all_day";
                            single_day.add("tuesday");
                        }
                        else
                        {
                            all_tue = "not_all_day";
                        }
                    }
                    else if (wed_d.isChecked()) {
                        if (isWednesday) {

                            all_wed = "all_day";
                            single_day.add("wednseday");
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
                            single_day.add("friday");
                        } else {
                            all_fri = "not_all_day";
                        }
                    }
                    else if (sat_d.isChecked()) {
                        if (isSatursday) {

                            all_sat = "all_day";
                            single_day.add("satursday");
                        } else {
                            all_sat = "not_all_day";
                        }
                    }
                    else if (sun_d.isChecked()) {
                        if (isSunday) {

                            all_sun = "all_day";
                            single_day.add("sunday");
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


        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(all_day.isEmpty())
                {
                    all_day.add(null);
                }
                Log.i("All day new:",all_day_str);
                if (brand_str.equals("Charger Brand") || brand_str.isEmpty() ||brand_str==null)
                {
                    brand_str="";
                }
                if (cs_model_str.equals("Charger Model") || cs_model_str.isEmpty() || cs_model_str==null)
                {
                    cs_model_str="";
                }
                if (socket_str.equals("Select socket") || socket_str.isEmpty() || socket_str==null)
                {
                    socket_str="";
                }
                if(mon_end.isEmpty())


                    if (all_mon.equals("not_all_day") && mon_end.isEmpty())
                    {
                        mon_end.add("");
                        mon_start.add("");
                        all_mon="not_all_day";
                    }
                    else
                    {
                        mon_end.clear();
                        mon_end.add("");
                        mon_start.clear();
                        mon_start.add("");
                        all_mon="all_day";
                    }
                if(all_tue.equals("not_all_day") && tue_end.isEmpty())
                {
                    tue_end.add("");
                    tue_start.add("");
                    all_tue="not_all_day";
                }
                else
                {
                    tue_end.clear();
                    tue_end.add("");
                    tue_start.clear();
                    tue_start.add("");
                    all_tue="all_day";

                }

                if (all_wed.equals("not_all_day") && wed_end.isEmpty())
                {
                    wed_end.add("");
                    wed_start.add("");
                    all_wed="not_all_day";
                }
                else {
                    wed_end.clear();
                    wed_end.add("");
                    wed_start.clear();
                    wed_start.add("");
                    all_wed="all_day";
                }

                if (all_thr.equals("not_all_day") && thr_end.isEmpty())
                {
                    thr_end.add("");
                    thr_start.add("");
                    all_thr="not_all_day";
                }
                else
                {
                    thr_end.clear();
                    thr_end.add("");
                    thr_start.clear();
                    thr_start.add("");
                    all_thr="all_day";

                }
                if (all_fri.equals("not_all_day") && fri_end.isEmpty())
                {
                    fri_end.add("");
                    fri_start.add("");
                    all_fri="not_all_day";
                }
                else
                {
                    fri_end.clear();
                    fri_end.add("");
                    fri_start.clear();
                    fri_start.add("");
                    all_fri="all_day";
                }
                if (all_sat.equals("not_all_day") && sat_end.isEmpty())
                {
                    sat_end.add("");
                    sat_start.add("");
                    all_sat="not_all_day";
                }
                else
                {
                    sat_end.clear();
                    sat_end.add("");
                    sat_start.clear();
                    sat_start.add("");
                    all_sat="all_day";

                }
                if (all_sun.equals("not_all_day") && sun_end.isEmpty())
                {
                    sun_end.add("");
                    sun_start.add("");
                    all_sun="not_all_day";
                }
                else
                {
                    sun_end.clear();
                    sun_end.add("");
                    sun_start.clear();
                    sun_start.add("");
                    all_sun="all_day";

                }
                if (all_day_str.equals("not_all_day") && all_day_end.isEmpty())
                {
                    all_day_end.add("");
                    all_day_start.add("");
                    all_day_str="not_all_day";
                }
                else
                {
                    all_day_end.clear();
                    all_day_end.add("");
                    all_day_start.clear();
                    all_day_start.add("");
                    all_day_str="all_day";

                }

                if (charger_type_str.equals("Charging Station"))
                {
                    final_amphere=charger_amp_str;
                    final_voltage=vtg_str;
                    final_power=power_str;
                    final_rate=rate_str;
                    final_board=board_str;
                }else if (charger_type_str.equals("Standard Domestic Socket"))
                {
                    final_amphere=socket_amp_str;
                    final_voltage=socket_vtg_str;
                    final_power=null;
                    final_rate=socket_rate;
                    final_board=socket_board_str;
                }
                Log.i("Chrger type", charger_type_str);
                Log.i("Model", cs_model_str);
                Log.i("Chrger connector", cs_connector_str);
                Log.i("Chrger brand", brand_str);
                Log.i("rate", socket_rate);
                Log.i("board", socket_board_str);
                Log.i("power ", power_str);
                Log.i("Monday", String.valueOf(mon_end));
                Log.i("Monday", String.valueOf(mon_start));

                Log.e("TAG", "Place: " + charger_type_str.trim() + ", " + brand_str.trim() + "," + cs_model_str.trim() + "," + socket_board_str + cs_connector_str + power_str.trim() + vtg_str + state_text + address_1 + address_2 + city_txt + country_txt + landemark_txt + zip_txt + latitude + longitude);
                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(edit_residential_listing.this);


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

                String[] start_time_all = new String[]{"00:00"};
                String[] end_time_all = new String[]{"00:00"};
                String address_txt = address_1.getText().toString();
                String address2_txt = address_2.getText().toString();
                String land_str = landemark_txt.getText().toString();
                String city_str = city_txt.getText().toString();
                String country_str = country_txt.getText().toString();
                String state_str = state_text.getText().toString();
                int pin = Integer.parseInt(zip_txt.getText().toString());
                String profit_string = String.valueOf(13.1);
                String socket_type = socket_str;
                Log.e("TAG","Details:" + id +user_id +address_txt +address2_txt +land_str + city_str + state_str+ country_str + latitude + longitude + pin + final_board + final_rate + profit_string + selected + charger_type_str+ cs_connector_str+brand_str+ final_voltage+final_amphere +  checked+ cs_model_str+ final_power+ custum_brand+ custum_model+ socket_str+ custom_connector_type_public+ custom_voltage_public+ custom_power_output_public+ all_day+ single_day+ all_day_start+ all_day_end+ mon_start+ mon_end+ tue_start+tue_end+ wed_start+ wed_end+ thr_start+ thr_end+ fri_start+ fri_end+ sat_start+ sat_end+ sun_start+ sun_end+ start_time_all+ end_time_all+ all_day_str+ all_mon+ all_tue+ all_wed+ all_thr+all_fri+ all_sat+ all_sun);


                Long temp_id= Long.valueOf(2);//replace with id
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://elshare.in/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIInterface service = retrofit.create(APIInterface.class);
                Log.i("Call base url", String.valueOf(retrofit.baseUrl()));
                Log.i("Call converter", String.valueOf(retrofit.converterFactories()));
                Call<ResponseBody> call = null; //service.edit_residential_listing_method(temp_id, user_id, address_txt, address2_txt, land_str, city_str, state_str, country_str, latitude, longitude, pin, final_board, final_rate, profit_string, selected, charger_type_str, cs_connector_str, brand_str, final_voltage,final_amphere, checked, cs_model_str, final_power, custum_brand, custum_model, socket_str, custom_connector_type_public, custom_voltage_public, custom_power_output_public, all_day, single_day, all_day_start, all_day_end, mon_start, mon_end, tue_start, tue_end, wed_start, wed_end, thr_start, thr_end, fri_start, fri_end, sat_start, sat_end, sun_start, sun_end, start_time_all, end_time_all, all_day_str, all_mon, all_tue, all_wed, all_thr, all_fri, all_sat, all_sun);
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
                        Toast.makeText(edit_residential_listing.this, "Edit listing succesfully", Toast.LENGTH_LONG).show();

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

                        Toast.makeText(edit_residential_listing.this, t.getMessage(), Toast.LENGTH_LONG).show();
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
                latitude=Float.parseFloat(String.valueOf(place.getLatLng().latitude));
                longitude=Float.parseFloat(String.valueOf(place.getLatLng().longitude));
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
