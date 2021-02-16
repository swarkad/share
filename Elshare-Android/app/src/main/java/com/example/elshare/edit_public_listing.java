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
import java.util.Objects;

import datamodel.APIInterface;
import datamodel.Charegr_brand;
import datamodel.Connector_type;
import datamodel.Socket;
import datamodel.Socket_public;
import datamodel.charger_model;
import datamodel.charger_type;
import datamodel.public_board;
import datamodel.public_board_socket;
import datamodel.public_charegr_brand;
import datamodel.public_charger_model;
import datamodel.public_connector;
import datamodel.public_power_output;
import datamodel.public_rate;
import datamodel.public_rate_socket;
import datamodel.public_voltage;
import datamodel.public_voltage_socket;
import datamodel.residential_board;
import datamodel.residential_board_socket;
import datamodel.residential_power_output;
import datamodel.residential_rate_socket;
import datamodel.residential_voltage_socket;
import datamodel.residetial_rate;
import datamodel.residrntial_voltage;
import datamodel.residential_show_list.show_residential;
import datamodel.show_public;
import okhttp3.ResponseBody;
import public_pojo.public_charger_amphere;
import public_pojo.public_socket_amphere;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class edit_public_listing extends Activity
{
    String newString;
    EditText address_1,address_2,country_txt,state_text,landemark_txt,zip_txt,city_txt;
    EditText spinner_edit,start_time_txt,end_time_txt;
    int id;
    String final_board,final_rate,final_voltage,final_amphere,final_power;
    private String charger_t,br_t,model_t,connect_t,power_t,rate_t,vtg_t,amp_str,board_t,socekt_public_str,socket_board_str,socket_amp_str,socket_vtg_str,socket_rate_str;
    TimePickerDialog timepickerdialog;
    private int CalendarHour, CalendarMinute;
    Calendar calendar;
    String format;
    Switch instant_booking,multi_single_day;
    int checked,selected,user_id;
    Button add_time_block,save_changes,all_day_button;
    TableLayout table;
    ImageButton delete_row;
    EditText profit_str,state_rate_edit,profit_edit;
    private Boolean isMonday,isTuesday,isWednesday,isThuresday,isFriday,isSatursday,isSunday;
    private  String all_mon,all_tue,all_wed,all_thr,all_fri,all_sat,all_sun,all_day_str;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int RESULT_OK = -1;
    private static final int RESULT_CANCELED = -1;
    private show_public show_resi_arry;
    float longitude,latitude;
    private List<public_charegr_brand> charger_brand;
    private List<public_charger_model> charger_model;
    private List<public_connector> connector_type;
    private List<public_rate> rate_type;
    private List<Socket_public> socket_type;
    private List<public_voltage_socket> socket_volt;
    private List<public_rate_socket> socket_rate;
    private List<public_board_socket> socket_board;
    private List<public_board> board_com;
    private List<public_power_output> power_com;
    private List<public_voltage> volt_com;
    private List<public_charger_amphere> charger_amp_array;
    private List<public_socket_amphere> socket_amp_array;
    private CheckBox mon_d,tue_d,wed_d,thr_d,fri_d,sat_d,sun_d;

    private public_charger_model charger_m;
    private public_connector connector_c;
    private public_rate rate_r;
    private Socket_public socket_list;
    private public_voltage_socket com_voltage;
    private public_rate_socket com_rate;
    private public_board_socket com_board;
    private public_board  board_c;
    private public_power_output power_c;
    private public_voltage volt_c;
    private public_socket_amphere socket_amp;
    private public_charger_amphere charger_amp;
    private public_charegr_brand charger_b;


    TextView edit_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_public_listing);


        final Spinner spinner_11= findViewById(R.id.spinner140);//charger type
        final Spinner spinner_12= findViewById(R.id.spinner141);//charger brand
        final Spinner spinner_38= findViewById(R.id.spinner142);//charger model
        final Spinner spinner_39= findViewById(R.id.spinner143);//cs_board
        final Spinner spinner_40= findViewById(R.id.spinner146);//charger rate
        final Spinner spinner_70= findViewById(R.id.spinner147);//connector
        final Spinner spinner_71= findViewById(R.id.spinner148);//charger power
        final Spinner spinner_72= findViewById(R.id.spinner149);//chareger vtg
        final Spinner spinner_73= findViewById(R.id.spinner150);//socket list
        final Spinner spinner_74=findViewById(R.id.spinner151);//socket rate
        final Spinner spinner_75= findViewById(R.id.spinner152);//socket board
        final Spinner spinner_76= findViewById(R.id.spinner153);//socket vtg
        final Spinner charger_A= findViewById(R.id.charger_Public_A_edit);
        final Spinner socket_A= findViewById(R.id.socket_A_public_edit);

        add_time_block=findViewById(R.id.public_add_another);
        state_rate_edit=findViewById(R.id.rate_public_edit);
        profit_edit=findViewById(R.id.profit_public);
        save_changes=findViewById(R.id.edit_save_public);
        all_day_button=findViewById(R.id.add_day_public_edit);
        instant_booking=findViewById(R.id.switch9);
        multi_single_day=findViewById(R.id.switch10);
        start_time_txt=findViewById(R.id.public_start);
        end_time_txt=findViewById(R.id.public_end);//buuble_public

        edit_header=findViewById(R.id.edit_header_public);
        edit_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        instant_booking=findViewById(R.id.switch9);
        multi_single_day=findViewById(R.id.switch10);
        address_1=findViewById(R.id.editText104);
        address_2=findViewById(R.id.editText105);
        country_txt=findViewById(R.id.editText106);
        state_text=findViewById(R.id.editText107);
        landemark_txt=findViewById(R.id.editText109);
        zip_txt=findViewById(R.id.editText110);
        city_txt=findViewById(R.id.editText108);
        mon_d=findViewById(R.id.checkbox_1_p);
        tue_d=findViewById(R.id.checkbox_2_p);
        wed_d=findViewById(R.id.checkbox_3_p);
        thr_d=findViewById(R.id.checkbox_4_p);
        fri_d=findViewById(R.id.checkbox_5_p);
        sat_d=findViewById(R.id.checkbox_6_p);
        sun_d=findViewById(R.id.checkbox_7_p);
        mon_d.setChecked(false);
        tue_d.setChecked(false);
        wed_d.setChecked(false);
        thr_d.setChecked(false);
        fri_d.setChecked(false);
        sat_d.setChecked(false);
        sun_d.setChecked(false);

        spinner_12.setVisibility(View.GONE);
        spinner_38.setVisibility(View.GONE);
        spinner_39.setVisibility(View.GONE);
        spinner_40.setVisibility(View.GONE);
        spinner_70.setVisibility(View.GONE);
        spinner_71.setVisibility(View.GONE);
        spinner_72.setVisibility(View.GONE);
        spinner_73.setVisibility(View.GONE);
        spinner_74.setVisibility(View.GONE);
        spinner_75.setVisibility(View.GONE);
        spinner_76.setVisibility(View.GONE);
        charger_A.setVisibility(View.GONE);
        socket_A.setVisibility(View.GONE);

        all_mon="not_all_day";
        all_tue="not_all_day";
        all_wed="not_all_day";
        all_thr="not_all_day";
        all_fri="not_all_day";
        all_sat="not_all_day";
        all_sun="not_all_day";
        all_day_str="not_all_day";

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
        final ArrayList<String> charger_Amphere=new ArrayList<String>();
        final ArrayList<String> socket_Amphere=new ArrayList<String>();




        Bundle extras = getIntent().getExtras();
        if (extras == null) {

        } else {
            newString = extras.getString("List_V_ID_public");
            id=Integer.parseInt(newString);
        }
        APIInterface service = SingletonRetrofit.getAPIInterface();

        Call<show_public> call = service.getListPublic(id);
        Log.i("Call", String.valueOf(call.request().url()));

        call.enqueue(new Callback<show_public>() {
            @Override
            public void onResponse(Call<show_public> call, Response<show_public> response) {

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
                    user_id=show_resi_arry.getData().getUserId();

                    String charger_str=String.valueOf(show_resi_arry.getProvider().getChargerBrand());
                    String socket_str=String.valueOf(show_resi_arry.getProvider().getSocket());
                    if(charger_str.equals("null")) {
//                        Select socket detail
                        socket_rate_array.add(String.valueOf(show_resi_arry.getProvider().getRateStructure()));
                        socket_voltage.add(String.valueOf(show_resi_arry.getProvider().getVoltage()));

                        String[] array1  = {"Standard Domestic Socket","Charging Station"};
                        ArrayAdapter<String> adapter101 = new ArrayAdapter<String>(edit_public_listing.this,R.layout.sample_text, array1);
                        spinner_11.setAdapter(adapter101);
                        spinner_11.setVisibility(View.VISIBLE);



                    }
                    else if (socket_str.equals("null") )
                    {

                        String[] array1  = {"Charging Station","Standard Domestic Socket"};
                        ArrayAdapter<String> adapter101 = new ArrayAdapter<String>(edit_public_listing.this,R.layout.sample_text, array1);
                        spinner_11.setAdapter(adapter101);
                        spinner_11.setVisibility(View.VISIBLE);
                    }






                } else {
                    String new_str = "Make is empty";
                    Log.i("Make: ", new_str);
                    //  Toast.makeText(getContext(), "Please Select make", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<show_public> call, Throwable t) {
                Log.i("Make error: ", String.valueOf(t));

            }
        });


        spinner_11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String type_str=spinner_11.getSelectedItem().toString();
                String state_str2=state_text.getText().toString();
                if (state_str2.isEmpty())
                {
                    Toast.makeText(edit_public_listing.this, "All field are mandatory", Toast.LENGTH_SHORT).show();
                    spinner_73.setVisibility(View.GONE);
                    spinner_74.setVisibility(View.GONE);
                    spinner_75.setVisibility(View.GONE);
                    spinner_76.setVisibility(View.GONE);
                    spinner_12.setVisibility(View.GONE);
                    spinner_38.setVisibility(View.GONE);
                    spinner_39.setVisibility(View.GONE);
                    spinner_40.setVisibility(View.GONE);
                    spinner_70.setVisibility(View.GONE);
                    spinner_71.setVisibility(View.GONE);
                    spinner_72.setVisibility(View.GONE);
                    charger_A.setVisibility(View.GONE);
                    socket_A.setVisibility(View.GONE);
                }
                else {
                    if (type_str.equals("Charging Station")) {
                        spinner_73.setVisibility(View.GONE);
                        spinner_74.setVisibility(View.GONE);
                        spinner_75.setVisibility(View.GONE);
                        spinner_76.setVisibility(View.GONE);
                        spinner_12.setVisibility(View.VISIBLE);
                        spinner_38.setVisibility(View.GONE);
                        spinner_39.setVisibility(View.GONE);
                        spinner_40.setVisibility(View.GONE);
                        spinner_70.setVisibility(View.GONE);
                        spinner_71.setVisibility(View.GONE);
                        spinner_72.setVisibility(View.GONE);
                        charger_A.setVisibility(View.GONE);
                        socket_A.setVisibility(View.GONE);

                        charger_t=spinner_11.getSelectedItem().toString();


                        spinner_charger_brand.clear();
                        spinner_charger_brand.add("Charger brand");
                        APIInterface service = SingletonRetrofit.getAPIInterface();

                        Call<List<public_charegr_brand>> call = service.getChargerBrandPublic();
                        Log.i("Public brand***********************************************************************************************  : ", String.valueOf(call.request().url()));
                        call.enqueue(new Callback<List<public_charegr_brand>>() {
                            @Override
                            public void onResponse(Call<List<public_charegr_brand>> call, Response<List<public_charegr_brand>> response) {
                                if (response.body() != null) {
                                    charger_brand = response.body();

                                    ArrayList make_array = (ArrayList) response.body();


                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        charger_b = charger_brand.get(i);
                                        demo_str = charger_b.getChargerBrandPublic();
                                        spinner_charger_brand.add(demo_str);
                                        Log.i("Brand Array: ", spinner_charger_brand.toString());
                                    }

                                } else {
                                    String new_str = "Brand is empty";
                                    Log.i("Brand Array: ", new_str);

                                }
                            }

                            @Override
                            public void onFailure(Call<List<public_charegr_brand>> call, Throwable t) {
                                Log.i("Brand error: ", String.valueOf(t));

                            }
                        });

                        ArrayAdapter<String> adapter_12 = new ArrayAdapter<>(edit_public_listing.this, R.layout.sample_text, spinner_charger_brand);
                        spinner_12.setAdapter(adapter_12);
                        spinner_12.setVisibility(View.VISIBLE);

                        spinner_rate_structure.clear();
                        spinner_electricity_board.clear();
                        spinner_rate_structure.add("Rate structure");
                        spinner_electricity_board.add("Electricity board");
                        Retrofit retrofit2 = new Retrofit.Builder()
                                .baseUrl("http://elshare.in/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        APIInterface service2 = retrofit2.create(APIInterface.class);

                        Call<List<public_rate>> call2 = service2.getRateStructurePublic(state_text.getText().toString());
                        Log.i("Public  rate***********************************************************************************************  : ", String.valueOf(call2.request().url()));
                        call2.enqueue(new Callback<List<public_rate>>() {
                            @Override
                            public void onResponse(Call<List<public_rate>> call2, Response<List<public_rate>> response) {
                                if (response.body() != null) {
                                    rate_type = response.body();

                                    ArrayList make_array = (ArrayList) response.body();


                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        rate_r = rate_type.get(i);
                                        demo_str = rate_r.getRateStructurePublic();
                                        spinner_rate_structure.add(String.valueOf(demo_str));
                                        Log.i("Public rate Array: ", spinner_rate_structure.toString());
                                    }

                                } else {
                                    String new_str = "Public is empty";
                                    Log.i("Public rate Array: ", new_str);

                                }
                            }

                            @Override
                            public void onFailure(Call<List<public_rate>> call2, Throwable t) {
                                Log.i("Public rate error: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter40 = new ArrayAdapter<>(edit_public_listing.this, R.layout.sample_text, spinner_rate_structure);
                        spinner_40.setAdapter(adapter40);
                        spinner_40.setVisibility(View.VISIBLE);

                        Retrofit retrofit3 = new Retrofit.Builder()
                                .baseUrl("http://elshare.in/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        APIInterface service3 = retrofit3.create(APIInterface.class);
                        Call<List<public_board>> call4 = service3.getElectricityBoardPublic(state_text.getText().toString());
                        Log.i(" Public board: ***********************************************************************************************  : ", String.valueOf(call4.request().url()));
                        call4.enqueue(new Callback<List<public_board>>() {
                            @Override
                            public void onResponse(Call<List<public_board>> call4, Response<List<public_board>> response) {
                                if (response.body() != null) {
                                    board_com = response.body();

                                    ArrayList make_array = (ArrayList) response.body();


                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        board_c = board_com.get(i);
                                        demo_str = board_c.getElectricityBoardPublic();
                                        spinner_electricity_board.add(demo_str);
                                        Log.i("Public  board: ", spinner_electricity_board.toString());
                                    }

                                } else {
                                    String new_str = " Public board is empty";
                                    Log.i("  Public board: ", new_str);

                                }
                            }

                            @Override
                            public void onFailure(Call<List<public_board>> call4, Throwable t) {
                                Log.i("  Public board: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter39= new ArrayAdapter<>(edit_public_listing.this, R.layout.sample_text, spinner_electricity_board);
                        spinner_39.setAdapter(adapter39);
                        spinner_39.setVisibility(View.VISIBLE);




                    }
                    else if (type_str.equals("Charger Type"))
                    {
                        spinner_73.setVisibility(View.GONE);
                        spinner_74.setVisibility(View.GONE);
                        spinner_75.setVisibility(View.GONE);
                        spinner_76.setVisibility(View.GONE);
                        spinner_12.setVisibility(View.GONE);
                        spinner_38.setVisibility(View.GONE);
                        spinner_39.setVisibility(View.GONE);
                        spinner_40.setVisibility(View.GONE
                        );
                        spinner_70.setVisibility(View.GONE);
                        spinner_71.setVisibility(View.GONE);
                        spinner_72.setVisibility(View.GONE);
                        charger_A.setVisibility(View.GONE);
                        socket_A.setVisibility(View.GONE);
                        charger_t=spinner_11.getSelectedItem().toString();


                    }
                    else if (type_str.equals("Standard Domestic Socket")) {
                        spinner_73.setVisibility(View.VISIBLE);
                        spinner_74.setVisibility(View.VISIBLE);
                        spinner_75.setVisibility(View.VISIBLE);
                        spinner_76.setVisibility(View.VISIBLE);
                        spinner_12.setVisibility(View.GONE);
                        spinner_38.setVisibility(View.GONE);
                        spinner_39.setVisibility(View.GONE);
                        spinner_40.setVisibility(View.GONE);
                        spinner_70.setVisibility(View.GONE);
                        spinner_71.setVisibility(View.GONE);
                        spinner_72.setVisibility(View.GONE);
                        charger_A.setVisibility(View.GONE);
                        socket_A.setVisibility(View.GONE);

                        charger_t=spinner_11.getSelectedItem().toString();

                        socket_list_array.clear();
                        socket_rate_array.clear();
                        socket_board_array.clear();
                        socket_list_array.add("Socket");
                        APIInterface service = SingletonRetrofit.getAPIInterface();

                        Call<List<Socket_public>> call = service.getSocketPublic();
                        Log.i("Public Socket***********************************************************************************************  : ", String.valueOf(call.request().url()));
                        call.enqueue(new Callback<List<Socket_public>>() {
                            @Override
                            public void onResponse(Call<List<Socket_public>> call, Response<List<Socket_public>> response) {
                                if (response.body() != null) {
                                    socket_type = response.body();

                                    ArrayList make_array = (ArrayList) response.body();


                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        socket_list = socket_type.get(i);
                                        demo_str = socket_list.getSocketPublic();
                                        socket_list_array.add(demo_str);
                                        Log.i("Socket Array: ", socket_list_array.toString());
                                    }

                                } else {
                                    String new_str = "Socket is empty";
                                    Log.i("Socket Array: ", new_str);

                                }
                            }

                            @Override
                            public void onFailure(Call<List<Socket_public>> call, Throwable t) {
                                Log.i("Socket error: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter_73 = new ArrayAdapter<>(edit_public_listing.this, R.layout.sample_text, socket_list_array);
                        spinner_73.setAdapter(adapter_73);
                        spinner_73.setVisibility(View.VISIBLE);


                        socket_rate_array.clear();
                        socket_rate_array.add("Rate structure");
                        Call<List<public_rate_socket>> call2 = service.getRateStructureSocketPublic(state_text.getText().toString());
                        Log.i("Socket Public rate: ***********************************************************************************************  : ", String.valueOf(call2.request().url()));
                        call2.enqueue(new Callback<List<public_rate_socket>>() {
                            @Override
                            public void onResponse(Call<List<public_rate_socket>> call2, Response<List<public_rate_socket>> response) {
                                if (response.body() != null) {
                                    socket_rate = response.body();

                                    ArrayList make_array = (ArrayList) response.body();


                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        com_rate = socket_rate.get(i);
                                        demo_str = com_rate.getRateStructureSocketPublic();
                                        socket_rate_array.add(String.valueOf(demo_str));
                                        Log.i("Socket Public rate: ", socket_rate_array.toString());
                                    }

                                } else {
                                    String new_str = "Socket Public rate is empty";
                                    Log.i(" Socket Public ratel: ", new_str);

                                }
                            }

                            @Override
                            public void onFailure(Call<List<public_rate_socket>> call2, Throwable t) {
                                Log.i(" Socket Public rate: ", String.valueOf(t));

                            }
                        });

                        ArrayAdapter<String> adapter74 = new ArrayAdapter<>(edit_public_listing.this, R.layout.sample_text, socket_rate_array);
                        spinner_74.setAdapter(adapter74);
                        spinner_74.setVisibility(View.VISIBLE);


                        socket_board_array.clear();
                        socket_board_array.add("Electricity board");
                        Call<List<public_board_socket>> call3 = service.getElectricityBoardSocketPublic(state_text.getText().toString());
                        Log.i("Socket Public board: ***********************************************************************************************  : ", String.valueOf(call3.request().url()));
                        call3.enqueue(new Callback<List<public_board_socket>>() {
                            @Override
                            public void onResponse(Call<List<public_board_socket>> call3, Response<List<public_board_socket>> response) {
                                if (response.body() != null) {
                                    socket_board = response.body();

                                    ArrayList make_array = (ArrayList) response.body();


                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        com_board = socket_board.get(i);
                                        demo_str = com_board.getElectricityBoardSocketPublic();
                                        socket_board_array.add(demo_str);
                                        Log.i("Socket Public board: ", socket_board_array.toString());
                                    }

                                } else {
                                    String new_str = "Socket Public board is empty";
                                    Log.i(" Socket Public board: ", new_str);

                                }
                            }

                            @Override
                            public void onFailure(Call<List<public_board_socket>> call3, Throwable t) {
                                Log.i(" Socket Public board: ", String.valueOf(t));

                            }
                        });

                        ArrayAdapter<String> adapter75 = new ArrayAdapter<>(edit_public_listing.this, R.layout.sample_text, socket_board_array);
                        spinner_75.setAdapter(adapter75);
                        spinner_75.setVisibility(View.VISIBLE);



                    }
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                br_t=spinner_12.getSelectedItem().toString() ;
                String type_brand_str=spinner_12.getSelectedItem().toString();
                if(br_t.equals("Charger brand"))
                {
                    spinner_38.setVisibility(View.GONE);

                }
                else {
                    spinner_charger_model.clear();
                    spinner_charger_model.add("Charger Model");

                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<public_charger_model>> call = service.getChargerModelPublic(type_brand_str);
                    Log.i("Model Pblic***********************************************************************************************  : ", String.valueOf(call.request().url()));
                    call.enqueue(new Callback<List<public_charger_model>>() {
                        @Override
                        public void onResponse(Call<List<public_charger_model>> call, Response<List<public_charger_model>> response) {
                            if (response.body() != null) {
                                charger_model = response.body();

                                ArrayList make_array = (ArrayList) response.body();


                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    charger_m = charger_model.get(i);
                                    demo_str = charger_m.getChargerModelPublic();
                                    spinner_charger_model.add(demo_str);
                                    Log.i("Model Public Array: ", spinner_charger_model.toString());
                                }

                            } else {
                                String new_str = "Model Public is empty";
                                Log.i("v Array: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<public_charger_model>> call, Throwable t) {
                            Log.i("Model Public error: ", String.valueOf(t));

                        }
                    });
                    ArrayAdapter<String> adapter38 = new ArrayAdapter<>(edit_public_listing.this, R.layout.sample_text, spinner_charger_model);
                    spinner_38.setAdapter(adapter38);
                    spinner_38.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_38.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                model_t=spinner_38.getSelectedItem().toString();
                if (model_t.equals("Charger Model"))
                {
                    spinner_70.setVisibility(View.GONE);
                    charger_A.setVisibility(View.GONE);
                    spinner_72.setVisibility(View.GONE);
                    spinner_71.setVisibility(View.GONE);
                }
                else {
                    String state_str = state_text.getText().toString();
                    String model_str = spinner_38.getSelectedItem().toString();
                    String brand_str = spinner_12.getSelectedItem().toString();


                    spinner_connector.clear();
                    spinner_connector.add("Charger Connector");

                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<public_connector>> call = service.getConnectorTypePublic();
                    Log.i("Connector Public***********************************************************************************************  : ", String.valueOf(call.request().url()));
                    call.enqueue(new Callback<List<public_connector>>() {
                        @Override
                        public void onResponse(Call<List<public_connector>> call, Response<List<public_connector>> response) {
                            if (response.body() != null) {
                                connector_type = response.body();

                                ArrayList make_array = (ArrayList) response.body();


                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    connector_c = connector_type.get(i);
                                    demo_str = connector_c.getConnectorTypePublic();
                                    spinner_connector.add(demo_str);
                                    Log.i("Connector Public Array: ", spinner_connector.toString());
                                }

                            } else {
                                String new_str = "Connector Public is empty";
                                Log.i("Connector Public: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<public_connector>> call, Throwable t) {
                            Log.i("Public error: ", String.valueOf(t));

                        }
                    });

                    ArrayAdapter<String> adapter70 = new ArrayAdapter<>(edit_public_listing.this, R.layout.sample_text, spinner_connector);
                    spinner_70.setAdapter(adapter70);
                    spinner_70.setVisibility(View.VISIBLE);

                    spinner_power_output.clear();
                    spinner_power_output.add("Power output");
                    Call<List<public_power_output>> call2 = service.getPowerOutputPublic(model_str,brand_str );
                    Log.i("Connector Public***********************************************************************************************  : ", String.valueOf(call2.request().url()));
                    call2.enqueue(new Callback<List<public_power_output>>() {
                        @Override
                        public void onResponse(Call<List<public_power_output>> call2, Response<List<public_power_output>> response) {
                            if (response.body() != null) {
                                power_com = response.body();

                                ArrayList make_array = (ArrayList) response.body();


                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    power_c = power_com.get(i);
                                    demo_str = power_c.getPowerOutputPublic();
                                    spinner_power_output.add(demo_str);
                                    Log.i("power output Array: ", spinner_power_output.toString());
                                }

                            } else {
                                String new_str = "Power Output is empty";
                                Log.i("Power output: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<public_power_output>> call2, Throwable t) {
                            Log.i("Power output error: ", String.valueOf(t));

                        }
                    });

                    spinner_voltage.clear();
                    spinner_voltage.add("Voltage");

                    Call<List<public_voltage>> call6 = service.getVoltagePublic(model_str,brand_str );
                    Log.i("Voltage***********************************************************************************************  : ", String.valueOf(call6.request().url()));
                    call6.enqueue(new Callback<List<public_voltage>>() {
                        @Override
                        public void onResponse(Call<List<public_voltage>> call6, Response<List<public_voltage>> response) {
                            if (response.body() != null) {
                                volt_com = response.body();

                                ArrayList make_array = (ArrayList) response.body();


                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    volt_c = volt_com.get(i);
                                    demo_str = volt_c.getVoltagePublic();
                                    spinner_voltage.add(demo_str);
                                    Log.i("Voltage output Array: ", spinner_voltage.toString());
                                }

                            } else {
                                String new_str = "Voltage Output is empty";
                                Log.i("Voltage output: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<public_voltage>> call6, Throwable t) {
                            Log.i("Voltage output error: ", String.valueOf(t));

                        }
                    });
                    charger_Amphere.clear();
                    charger_Amphere.add("Amphere");

                    Call<List<public_charger_amphere>> call7 = service.getChargerAmpherePublic(model_str,brand_str);
                    Log.i("Amphere***********************************************************************************************  : ", String.valueOf(call7.request().url()));
                    call7.enqueue(new Callback<List<public_charger_amphere>>() {
                        @Override
                        public void onResponse(Call<List<public_charger_amphere>> call7, Response<List<public_charger_amphere>> response) {
                            if (response.body() != null) {
                                charger_amp_array = response.body();

                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    charger_amp = charger_amp_array.get(i);
                                    demo_str = charger_amp.getChargerAmpherePublic();
                                    charger_Amphere.add(demo_str);
                                    Log.i("Amphere output Array: ", charger_Amphere.toString());
                                }

                            } else {
                                String new_str = "Amphere Output is empty";
                                Log.i("Amphere output: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<public_charger_amphere>> call7, Throwable t) {
                            Log.i("Amphere output error: ", String.valueOf(t));

                        }
                    });

                    if (spinner_voltage.size()==1 && charger_Amphere.size()==1) {
                        ArrayAdapter<String> adapter71 = new ArrayAdapter<>(edit_public_listing.this, R.layout.sample_text, spinner_power_output);
                        spinner_71.setAdapter(adapter71);
                        spinner_71.setVisibility(View.VISIBLE);
                        spinner_72.setVisibility(View.GONE);
                        charger_A.setVisibility(View.GONE);


                    }
                    else {
                        ArrayAdapter<String> adapter72 = new ArrayAdapter<>(edit_public_listing.this, R.layout.sample_text, spinner_voltage);
                        spinner_72.setAdapter(adapter72);
                        ArrayAdapter<String> adapter_C_A = new ArrayAdapter<>(edit_public_listing.this, R.layout.sample_text, charger_Amphere);
                        charger_A.setAdapter(adapter_C_A);
                        charger_A.setVisibility(View.VISIBLE);
                        spinner_72.setVisibility(View.VISIBLE);
                        spinner_71.setVisibility(View.GONE);

                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_39.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                board_t=spinner_39.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_40.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                rate_t=spinner_40.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_71.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                power_t=spinner_71.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_72.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                vtg_t=spinner_72.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_76.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                socket_vtg_str=spinner_76.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_74.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                socket_rate_str=spinner_74.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_75.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                socket_board_str=spinner_75.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_73.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                socekt_public_str=spinner_73.getSelectedItem().toString();
                if (socekt_public_str.equals("Socket"))
                {
                    spinner_76.setVisibility(View.GONE);
                    socket_A.setVisibility(View.GONE);

                }
                else {
                    String state_str = state_text.getText().toString();
                    socekt_public_str= spinner_73.getSelectedItem().toString();
                    spinner_voltage.clear();
                    spinner_voltage.add("Voltage");
                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<public_voltage_socket>> call = service.getVoltageSocketPublic(socekt_public_str);
                    Log.i("Socket Public Voltage***********************************************************************************************  : ", String.valueOf(call.request().url()));
                    call.enqueue(new Callback<List<public_voltage_socket>>() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void onResponse(Call<List<public_voltage_socket>> call, Response<List<public_voltage_socket>> response) {
                            if (response.body() != null) {
                                socket_volt = response.body();

                                ArrayList make_array = (ArrayList) response.body();


                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    com_voltage = socket_volt.get(i);
                                    demo_str = com_voltage.getVoltageSocketPublic();
                                    socket_voltage.add(demo_str);
                                    Log.i("Socket Public Voltage: ", socket_voltage.toString());
                                }

                            } else {
                                String new_str = "Socket Public Voltage is empty";
                                Log.i("Socket Public Voltage :", new_str);

                            }
                        }


                        @SuppressLint("LongLogTag")
                        @Override
                        public void onFailure(Call<List<public_voltage_socket>> call, Throwable t) {
                            Log.i(" Socket Public Voltage: ", String.valueOf(t));

                        }
                    });
                    ArrayAdapter<String> adapter76 = new ArrayAdapter<>(edit_public_listing.this, R.layout.sample_text, socket_voltage);
                    spinner_76.setAdapter(adapter76);
                    spinner_76.setVisibility(View.VISIBLE);

                    socket_Amphere.clear();
                    socket_Amphere.add("Amphere");
                    Call<List<public_socket_amphere>> call2 = service.getSocketAmpherePublic(socekt_public_str);
                    Log.i("Socket Public Amphere***********************************************************************************************  : ", String.valueOf(call2.request().url()));
                    call2.enqueue(new Callback<List<public_socket_amphere>>() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void onResponse(Call<List<public_socket_amphere>> call2, Response<List<public_socket_amphere>> response) {
                            if (response.body() != null) {
                                socket_amp_array = response.body();

                                ArrayList make_array = (ArrayList) response.body();


                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    socket_amp = socket_amp_array.get(i);
                                    demo_str = socket_amp.getSocketAmpherePublic();
                                    socket_Amphere.add(demo_str);
                                    Log.i("Socket Public Amphere: ", socket_Amphere.toString());
                                }

                            } else {
                                String new_str = "Socket Public Amphere is empty";
                                Log.i("Socket Public Amphere :", new_str);

                            }
                        }


                        @SuppressLint("LongLogTag")
                        @Override
                        public void onFailure(Call<List<public_socket_amphere>> call2, Throwable t) {
                            Log.i(" Socket Public Amphere: ", String.valueOf(t));

                        }
                    });
                    ArrayAdapter<String> adapter_S_A = new ArrayAdapter<>(edit_public_listing.this, R.layout.sample_text, socket_Amphere);
                    socket_A.setAdapter(adapter_S_A);
                    socket_A.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_70.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                connect_t=spinner_70.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        socket_A.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                socket_amp_str=socket_A.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_76.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                socket_vtg_str =spinner_76.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        charger_A.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                amp_str=charger_A.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

                final TableRow row = (TableRow) LayoutInflater.from(v.getContext()).inflate(R.layout.sample_row, null);
                table = findViewById(R.id.table_public_edit);

                final EditText end_txt= row.findViewById(R.id.editText200);
                delete_row=row.findViewById(R.id.image_b);
                final EditText start_txt= row.findViewById(R.id.editText201);
                end_txt.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        calendar = Calendar.getInstance();
                        CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                        CalendarMinute = calendar.get(Calendar.MINUTE);
                        Log.i("time 24 :", String.valueOf(CalendarHour) + String.valueOf(CalendarMinute));

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
                                                Log.i("New time  24 :", String.valueOf(CalendarHour + CalendarMinute));
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


        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (br_t.equals("Charger Brand") || br_t.isEmpty() ||br_t==null)
                {
                    br_t="";
                }
                if (model_t.equals("Charger Model") || model_t.isEmpty() || model_t==null)
                {
                    model_t="";
                }
                if (socekt_public_str.equals("Select socket") || socekt_public_str.isEmpty() || socekt_public_str==null)
                {
                    socekt_public_str="";
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

                if (charger_t.equals("Charging Station"))
                {
                    final_amphere=amp_str;
                    final_voltage=vtg_t;
                    final_power=power_t;
                    final_rate=rate_t;
                    final_board=board_t;
                }else if (charger_t.equals("Standard Domestic Socket"))
                {
                    final_amphere=socket_amp_str;
                    final_voltage=socket_vtg_str;
                    final_power=null;
                    final_rate=socket_rate_str;
                    final_board=socket_board_str;
                }
                Log.i("Chrger type", charger_t);
                Log.i("Model", model_t);
                Log.i("Chrger connector", connect_t);
                Log.i("Chrger brand", br_t);
                Log.i("rate", socket_rate_str);
                Log.i("board", socket_board_str);
                Log.i("power ", power_t);
                Log.i("Monday", String.valueOf(mon_end));
                Log.i("Monday", String.valueOf(mon_start));

                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(edit_public_listing.this);


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
                String private_info = "null";
                String public_info = "null";


                APIInterface service = SingletonRetrofit.getAPIInterface();
                Log.i("Call base url", String.valueOf(SingletonRetrofit.getRetrofit().baseUrl()));
                Log.i("Call converter", String.valueOf(SingletonRetrofit.getRetrofit().converterFactories()));
                Call<ResponseBody> call = null;//service.edit_public_list(id, user_id,city_str, address_txt, address2_txt, land_str, state_str, country_str,final_voltage,final_amphere,profit_string,final_rate,pin,latitude,longitude,selected,checked,final_board,charger_t,connect_t,br_t,model_t,final_power,custum_brand,custum_model,socekt_public_str,custom_connector_type_public,custom_voltage_public,custom_power_output_public, all_day, single_day, all_day_start, all_day_end, mon_start, mon_end, tue_start, tue_end, wed_start, wed_end, thr_start,thr_end,fri_start,fri_end,sat_start,sat_end,sun_start,sun_end,start_time_public,end_time_public, all_day_str,all_mon, all_tue, all_wed, all_thr, all_fri, all_sat, all_sun,public_info,private_info);
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
                        Toast.makeText(edit_public_listing.this, "Edit listing succesfully", Toast.LENGTH_LONG).show();

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

                        Toast.makeText(edit_public_listing.this, t.getMessage(), Toast.LENGTH_LONG).show();
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
