package com.example.elshare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.elshare.utils.SingletonRetrofit;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import residential_pojo.residential_charger_amphere;
import residential_pojo.residential_socket_amphere;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class residential extends Fragment
{
    private static final int RESULT_OK = -1;
    private static final int RESULT_CANCELED = -1;
    double longitude,latitude;
    EditText edit_address;
    private List<charger_type> charger_t;
    private List<Charegr_brand> charger_brand;
    private List<charger_model> charger_model;
    private List<residential_board> board_m;
    private List<residetial_rate> rate_m;
    private List<Connector_type> connetor_set;
    private List<Socket>  socket_set;
    private List<residential_power_output>  power_com;
    private List<residrntial_voltage>  volt_com;
    private List<residential_rate_socket>  socket_rate_res;
    private List<residential_board_socket>  socket_board_res;
    private  List<residential_voltage_socket> socket_vtg_res;
    private  List<residential_charger_amphere> charger_amphere_array;
    private  List<residential_socket_amphere> socket_amphere_array;


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
    private  residential_charger_amphere charger_amp;
    private residential_socket_amphere socket_amp;

    private CardView crd;
    private EditText edittext_1,edittext_2,state_text,city_text,country_text,landmark_text,zip_txt;
    private String address_str1,add_str2,state_st,city_str,country_str,landmark_str,zip_str;
    private String charger_type_str,brand_str,cs_model_str,cs_connector_str,cs_vtg,cs_amphere,cs_bosrd,cs_rate,cs_power,socket_str,socket_rate,socket_board_str,socket_vtg_str,socket_amphere_str;
    private  String api_key="AIzaSyDNdANqUDsLyz6s3mtEJuEQBNaW4xVpK8k";
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_residential, container, false);
        Button btn = rootView.findViewById(R.id.button86);
        address_str1="";
        add_str2="";
        state_st="";
        city_str="";
        country_str="";
        landmark_str="";
        zip_str="";

//        edit_address=rootView.findViewById(R.id.editText49);
        crd=rootView.findViewById(R.id.mycard);
        edittext_1=rootView.findViewById(R.id.editText49);
        edittext_2=rootView.findViewById(R.id.editText50);
        country_text=rootView.findViewById(R.id.editText46);
        state_text=rootView.findViewById(R.id.editText47);
        city_text=rootView.findViewById(R.id.editText48);
        landmark_text=rootView.findViewById(R.id.editText83);
        zip_txt=rootView.findViewById(R.id.editText86);


        Places.initialize(getContext(),"AIzaSyDNdANqUDsLyz6s3mtEJuEQBNaW4xVpK8k");
        edittext_1.setFocusable(false);
        edittext_1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG,Place.Field.ADDRESS_COMPONENTS);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields).setCountry("IN") //NIGERIA
                        .build(Objects.requireNonNull(getContext()));
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

            }
        });

         String[] array1  = {"Charger Type","Charging Station", "Standard Domestic Socket"};


        final Spinner spinner8= rootView.findViewById(R.id.spinner8);//charger type-array1
        final Spinner spinner9= rootView.findViewById(R.id.spinner9);//charger brand-spinner_brand
        final Spinner spinner10= rootView.findViewById(R.id.spinner10);//charger_model-spinner_model
        final Spinner spinner50= rootView.findViewById(R.id.spinner50);//connector_type-spinner_connector
        final Spinner spinner35= rootView.findViewById(R.id.spinner35);//charger board-spinner_board
        final Spinner spinner36= rootView.findViewById(R.id.spinner36);//charger rate-spinner_rate
        final Spinner spinner51= rootView.findViewById(R.id.spinner51);//socket_list-spinner_socket
        final Spinner spinner52= rootView.findViewById(R.id.spinner52);//chareger power output-spinner_power_output
        final Spinner spinner53= rootView.findViewById(R.id.spinner53);//charger voltage-spinner_voltage
        final Spinner spinner54= rootView.findViewById(R.id.spinner54);//socket-voltage-socket_voltage
        final Spinner spinner55= rootView.findViewById(R.id.spinner55);//socket board -socket_eboard
        final Spinner spinner56= rootView.findViewById(R.id.spinner56);//socket rate=socket_rate_array
        final Spinner charger_amphere= rootView.findViewById(R.id.charger_R_amphere);
        final Spinner socket_amphere= rootView.findViewById(R.id.socket_R_amphere);

        final ArrayList<String> spinner_brand=new ArrayList<String>();
        final ArrayList<String> spinner_model=new ArrayList<String>();
        final ArrayList<String> spinner_board=new ArrayList<String>();
        final ArrayList<String> spinner_rate=new ArrayList<String>();
        final ArrayList<String> spinner_connector=new ArrayList<String>();
        final ArrayList<String> spinner_power_output=new ArrayList<String>();
        final ArrayList<String> spinner_voltage=new ArrayList<String>();
        final ArrayList<String> spinner_socket=new ArrayList<String>();
        final ArrayList<String> socket_voltage=new ArrayList<String>();
        final ArrayList<String> socket_rate_array=new ArrayList<String>();
        final ArrayList<String> socket_eboard=new ArrayList<String>();
        final ArrayList<String> charger_A=new ArrayList<String>();
        final ArrayList<String> socket_A=new ArrayList<String>();

        spinner8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String charger_model_str = spinner8.getSelectedItem().toString();
                String city_str2 = city_text.getText().toString();
                String state_str2 = state_text.getText().toString();
                if (state_str2.isEmpty() && city_str2.isEmpty()) {
                    Toast.makeText(getContext(), "Fill all required field of address", Toast.LENGTH_LONG).show();
                    spinner9.setVisibility(View.GONE);
                    spinner10.setVisibility(View.GONE);
                    spinner35.setVisibility(View.GONE);
                    spinner36.setVisibility(View.GONE);
                    spinner52.setVisibility(View.GONE);
                    spinner53.setVisibility(View.GONE);
                    spinner54.setVisibility(View.GONE);
                    spinner50.setVisibility(View.GONE);
                    spinner51.setVisibility(View.GONE);
                    spinner55.setVisibility(View.GONE);
                    spinner56.setVisibility(View.GONE);
                    charger_amphere.setVisibility(View.GONE);
                    socket_amphere.setVisibility(View.GONE);


                } else {
                    if (charger_model_str.equals("Charger Type")) {
                        spinner9.setVisibility(View.GONE);
                        spinner10.setVisibility(View.GONE);
                        spinner35.setVisibility(View.GONE);
                        spinner36.setVisibility(View.GONE);
                        spinner52.setVisibility(View.GONE);
                        spinner53.setVisibility(View.GONE);
                        spinner54.setVisibility(View.GONE);
                        spinner50.setVisibility(View.GONE);
                        spinner51.setVisibility(View.GONE);
                        spinner55.setVisibility(View.GONE);
                        spinner56.setVisibility(View.GONE);
                        charger_amphere.setVisibility(View.GONE);
                        socket_amphere.setVisibility(View.GONE);


                    } else if (charger_model_str.equals("Charging Station")) {

                        spinner9.setVisibility(View.VISIBLE);
                        spinner10.setVisibility(View.GONE);
                        spinner35.setVisibility(View.GONE);
                        spinner36.setVisibility(View.GONE);
                        spinner52.setVisibility(View.GONE);
                        spinner53.setVisibility(View.GONE);
                        spinner54.setVisibility(View.GONE);
                        spinner50.setVisibility(View.GONE);
                        spinner51.setVisibility(View.GONE);
                        spinner55.setVisibility(View.GONE);
                        spinner56.setVisibility(View.GONE);
                        charger_amphere.setVisibility(View.GONE);
                        socket_amphere.setVisibility(View.GONE);

                        charger_type_str = spinner8.getSelectedItem().toString();
                        spinner_board.clear();
                        spinner_board.add("Electricity board");
                        APIInterface service2 = SingletonRetrofit.getAPIInterface();
                        String state_str = state_text.getText().toString();

                        Call<List<residential_board>> call2 = service2.getResidentiallBoard(state_str);
                        call2.enqueue(new Callback<List<residential_board>>() {
                            @Override
                            public void onResponse(Call<List<residential_board>> call2, Response<List<residential_board>> response) {
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
                        ArrayAdapter<String> adapter35 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_board);
                        spinner35.setAdapter(adapter35);
                        spinner35.setVisibility(View.VISIBLE);

                        //Select rate---------------------------------------------------

                        spinner_rate.clear();
                        spinner_rate.add("Rate structure");
                        Retrofit retrofit3 = new Retrofit.Builder()
                                .baseUrl("http://elshare.in/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        final APIInterface service3 = retrofit3.create(APIInterface.class);
                        Call<List<residetial_rate>> call3 = service3.getResidentialRate(state_str);
                        call3.enqueue(new Callback<List<residetial_rate>>() {


                            @SuppressLint("LongLogTag")
                            @Override
                            public void onResponse(Call<List<residetial_rate>> call3, Response<List<residetial_rate>> response) {

                                if (response.body() != null) {
                                    Log.i("Residential Rate: ", String.valueOf(response.body()));
                                    rate_m = response.body();

                                    ArrayList make_array = (ArrayList) response.body();
                                    String demo_str = null;
                                    int i;
                                    for (i = 0; i < make_array.size(); i++) {
                                        //  Log.i("login response: ", String.valueOf((make_array.get(i))));
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
                            public void onFailure(Call<List<residetial_rate>> call3, Throwable t) {
                                Log.i("Residential rate error: ", String.valueOf(t));

                            }
                        });
                        ArrayAdapter<String> adapter36 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_rate);
                        spinner36.setAdapter(adapter36);
                        spinner36.setVisibility(View.VISIBLE);


                        spinner_brand.clear();
                        spinner_brand.add("Charger Brand");
                        APIInterface service1 = SingletonRetrofit.getAPIInterface();

                        Call<List<Charegr_brand>> call = service1.getBrand();
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
                        ArrayAdapter<String> adapter9 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_brand);
                        spinner9.setAdapter(adapter9);
                        spinner9.setVisibility(View.VISIBLE);


                    } else if (charger_model_str.equals("Standard Domestic Socket")) {

                        spinner9.setVisibility(View.GONE);
                        spinner10.setVisibility(View.GONE);
                        spinner35.setVisibility(View.GONE);
                        spinner36.setVisibility(View.GONE);
                        spinner52.setVisibility(View.GONE);
                        spinner53.setVisibility(View.GONE);
                        spinner50.setVisibility(View.GONE);
                        spinner51.setVisibility(View.VISIBLE);
                        spinner54.setVisibility(View.VISIBLE);
                        spinner55.setVisibility(View.VISIBLE);
                        spinner56.setVisibility(View.VISIBLE);
                        charger_amphere.setVisibility(View.GONE);
                        socket_amphere.setVisibility(View.GONE);


                        String state_str = state_text.getText().toString().trim();
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
                        ArrayAdapter<String> adapter51 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_socket);

                        spinner51.setAdapter(adapter51);
                        spinner51.setVisibility(View.VISIBLE);

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
                                        //  make_array.add(my_make.getMy_make());
                                        socket_rate_array.add(demo_str);
                                        Log.i("Residential socket rate Array: ", socket_rate_array.toString());
                                    }
//                Log.i("final Array: ", make_array.toString());
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
                        ArrayAdapter<String> adapter56 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_rate_array);
                        spinner56.setAdapter(adapter56);
                        spinner56.setVisibility(View.VISIBLE);

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
                                        //  Log.i("login response: ", String.valueOf((make_array.get(i))));
                                        socket_board = socket_board_res.get(i);
                                        demo_str = socket_board.getElectricityBoardSocketRes();
                                        Log.i("socket board response: ", demo_str);
                                        //  make_array.add(my_make.getMy_make());
                                        socket_eboard.add(demo_str);
                                        Log.i("Socket board Array: ", socket_eboard.toString());
                                    }
//                Log.i("final Array: ", make_array.toString());
                                } else {
                                    String new_str = "Socket board is empty";
                                }
                            }

                            @Override
                            public void onFailure(Call<List<residential_board_socket>> call5, Throwable t) {
                                Log.i("Socket board error: ", String.valueOf(t));

                            }
                        });


                        ArrayAdapter<String> adapter55 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_eboard);
                        spinner55.setAdapter(adapter55);
                        spinner55.setVisibility(View.VISIBLE);


                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        spinner9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brand_str=spinner9.getSelectedItem().toString();
                if (brand_str.equals("Charger Brand"))
                {
                    spinner10.setVisibility(View.GONE);
                }
                else {
                    String model_str = spinner9.getSelectedItem().toString();
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
                    ArrayAdapter<String> adapter10 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_model);
                    spinner10.setAdapter(adapter10);
                    spinner10.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String brand_str=spinner9.getSelectedItem().toString();
                String state_str=state_text.getText().toString();
                Log.i("State  : ", state_str);
                cs_model_str=spinner10.getSelectedItem().toString();
                if (cs_model_str.equals("Charger Model"))
                {
                    spinner50.setVisibility(View.GONE);
                    spinner52.setVisibility(View.GONE);
                    spinner53.setVisibility(View.GONE);
                    charger_amphere.setVisibility(View.GONE);

                }
                else if (state_str.isEmpty())
                {
                    Toast.makeText(getContext(), "State is empty", Toast.LENGTH_LONG).show();

                }
                else {

                    String model_str = spinner10.getSelectedItem().toString();
                    spinner_connector.clear();
                    spinner_connector.add("Connector Type");
                    APIInterface service = SingletonRetrofit.getAPIInterface();

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
                    ArrayAdapter<String> adapter50 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_connector);
                    spinner50.setAdapter(adapter50);
                    spinner50.setVisibility(View.VISIBLE);

                    spinner_power_output.clear();
                    spinner_voltage.clear();
                    charger_A.clear();

                    spinner_voltage.add("Voltage");
                    spinner_power_output.add("Power output");
                    charger_A.add("Amphere");


                    APIInterface service2 = SingletonRetrofit.getAPIInterface();

                    Call<List<residential_power_output>> call2 = service2.getPowerOutputRes(brand_str,model_str);
                    Log.i("power Res***********************************************************************************************  : ", String.valueOf(call2.request().url()));
                    call2.enqueue(new Callback<List<residential_power_output>>() {
                        @Override
                        public void onResponse(Call<List<residential_power_output>> call2, Response<List<residential_power_output>> response) {
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



                    APIInterface service5 = SingletonRetrofit.getAPIInterface();

                    Call<List<residrntial_voltage>> call6 = service5.getVoltageRes(brand_str,model_str);
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

                    Retrofit retrofit7 = new Retrofit.Builder()
                            .baseUrl("http://elshare.in/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    APIInterface service7 = retrofit7.create(APIInterface.class);

                    Call<List<residential_charger_amphere>> call7 = service7.getChargerAmphereRes(brand_str,model_str);
                    Log.i("Amphere***********************************************************************************************  : ", String.valueOf(call7.request().url()));
                    call7.enqueue(new Callback<List<residential_charger_amphere>>() {
                        @Override
                        public void onResponse(Call<List<residential_charger_amphere>> call7, Response<List<residential_charger_amphere>> response) {
                            if (response.body() != null) {
                                charger_amphere_array = response.body();

                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    charger_amp = charger_amphere_array.get(i);
                                    demo_str = charger_amp.getChargerAmphereRes();
                                    charger_A.add(demo_str);
                                    Log.i("Amphere output Array: ", charger_A.toString());
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

                    if (spinner_voltage.size()==1 && charger_A.size()==1) {
                        ArrayAdapter<String> adapter52 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_power_output);
                        spinner52.setAdapter(adapter52);
                        spinner52.setVisibility(View.VISIBLE);
                        spinner53.setVisibility(View.GONE);
                        charger_amphere.setVisibility(View.GONE);

                    }
                    else {
                        ArrayAdapter<String> adapter53 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_voltage);
                        spinner53.setAdapter(adapter53);
                        spinner53.setVisibility(View.VISIBLE);
                        spinner52.setVisibility(View.GONE);
                        ArrayAdapter<String> adapter_C_A = new ArrayAdapter<>(getActivity(), R.layout.sample_text, charger_A);
                        charger_amphere.setAdapter(adapter_C_A);
                        charger_amphere.setVisibility(View.VISIBLE);

                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner50.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                cs_connector_str=spinner50.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner51.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_str= spinner51.getSelectedItem().toString();

                if (socket_str.equals("Select socket"))
                {
                    spinner54.setVisibility(View.GONE);
                    socket_amphere.setVisibility(View.GONE);
                }
                else {
                    String state_str = state_text.getText().toString();
                    socket_voltage.clear();
                    socket_voltage.add("socket voltage");
                    socket_A.clear();
                    socket_A.add("socket amphere");

//        Select voltage of socket------------------------------------------
                    String socket_str = spinner51.getSelectedItem().toString();
                    APIInterface service = SingletonRetrofit.getAPIInterface();
                    Call<List<residential_voltage_socket>> call4 = service.getVoltageSocketRes(socket_str);
                    call4.enqueue(new Callback<List<residential_voltage_socket>>() {


                        @SuppressLint("LongLogTag")
                        @Override
                        public void onResponse(Call<List<residential_voltage_socket>> call, Response<List<residential_voltage_socket>> response) {

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
                        public void onFailure(Call<List<residential_voltage_socket>> call, Throwable t) {
                            Log.i("Residential socket vtg error: ", String.valueOf(t));

                        }
                    });

                    APIInterface service1 = SingletonRetrofit.getAPIInterface();
                    Call<List<residential_socket_amphere>> call2 = service1.getSocketAmphereRes(socket_str);
                    call2.enqueue(new Callback<List<residential_socket_amphere>>() {


                        @SuppressLint("LongLogTag")
                        @Override
                        public void onResponse(Call<List<residential_socket_amphere>> call2, Response<List<residential_socket_amphere>> response) {

                            if (response.body() != null) {
                                Log.i("Residential socket Amphere: ", String.valueOf(response.body()));
                                socket_amphere_array = response.body();
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    //  Log.i("login response: ", String.valueOf((make_array.get(i))));
                                    socket_amp = socket_amphere_array.get(i);
                                    demo_str = socket_amp.getSocketAmphereRes();
                                    Log.i("Residential socket Amphere R: ", String.valueOf(demo_str));
                                    //  make_array.add(my_make.getMy_make());
                                    socket_A.add(demo_str);
                                    Log.i("Residential socket Amphere Array: ", socket_A.toString());
                                }
                            } else {
                                String new_str = "Residential socket Amphere is empty";
                            }
                        }


                        @SuppressLint("LongLogTag")
                        @Override
                        public void onFailure(Call<List<residential_socket_amphere>> call2, Throwable t) {
                            Log.i("Residential socket Amphere error: ", String.valueOf(t));

                        }
                    });


                    ArrayAdapter<String> adapter54 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_voltage);
                    spinner54.setAdapter(adapter54);
                    spinner54.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter_S_A = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_A);
                    socket_amphere.setAdapter(adapter_S_A);
                    socket_amphere.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner35.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_bosrd=spinner35.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner36.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_rate=spinner36.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner52.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                cs_power=spinner52.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner53.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                cs_vtg=spinner53.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner54.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_vtg_str=spinner54.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner55.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                socket_board_str=spinner55.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner56.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                socket_rate=spinner56.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        charger_amphere.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_amphere=charger_amphere.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        socket_amphere.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_amphere_str=socket_amphere.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter8 = new ArrayAdapter<>(getActivity(),R.layout.sample_text, array1);
        spinner8.setAdapter(adapter8);

        btn.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                Boolean address1_valid,state_valid;
                if (edittext_1.getText().toString().isEmpty()) {
                    edittext_1.setError(getResources().getString(R.string.address_error));
                    address1_valid = false;
                }
                else
                {
                    address1_valid = true;
                }
                if (state_text.getText().toString().isEmpty())
                {
                    state_text.setError(getResources().getString(R.string.state_error));
                    state_valid = false;
                }
                else
                {
                    state_valid = true;

                }
                if (address1_valid && state_valid) {
                    address_str1 = edittext_1.getText().toString();
                    add_str2 = edittext_2.getText().toString();
                    city_str = city_text.getText().toString();
                    state_st = state_text.getText().toString();
                    country_str = country_text.getText().toString();
                    zip_str = zip_txt.getText().toString();
                    landmark_str = landmark_text.getText().toString();


                    if (brand_str.equals("Charger Brand") && state_st.isEmpty()) {
                        Toast.makeText(getContext(), "All field are mandetory", Toast.LENGTH_LONG).show();
//                    setSpinnerError(spinner9,getResources().getString(R.string.password_error));
                    } else {
                        Log.i("Residential field:", charger_type_str + brand_str + cs_model_str + cs_connector_str + cs_power + cs_rate + cs_vtg + cs_bosrd);
                        Log.i("Address field:", edittext_1.toString() + edittext_2.toString() + state_text.toString() + country_text.toString() + landmark_text.toString() + zip_txt.toString() + city_text.toString());
                        Log.i("Address field 2:", address_str1 + add_str2 + state_st + country_str + zip_str + landmark_str + city_str);

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        String host_str2 = "RESIDENTIAL";
                        editor.putString("HOST", host_str2);

//                        String host_str = preferences.getString("HOST", null);
//                        if (host_str!=null) {
//                            editor.putString("HOST", host_str2);
//                        }
//                        else
//                        {
//                            editor.putString("HOST", host_str2);
//
//                        }

                        editor.putString("Charger_type_resi", charger_type_str);
                        editor.putString("Charger_brand_resi", brand_str);
                        editor.putString("Charger_model_resi", cs_model_str);
                        editor.putString("board_resi", cs_bosrd);
                        editor.putString("Connector_resi", cs_connector_str);
                        editor.putString("power_resi", cs_power);
                        editor.putString("Voltage_resi", cs_vtg);
                        editor.putString("State_r", state_text.getText().toString());
                        editor.putString("Add_1_r", edittext_1.getText().toString());
                        editor.putString("Add_2_r", edittext_2.getText().toString());
                        editor.putString("City_r", city_text.getText().toString());
                        editor.putString("Country_r", country_text.getText().toString());
                        editor.putString("Land_r", landmark_text.getText().toString());
                        editor.putString("Zip_r", zip_txt.getText().toString());
                        editor.putString("Latitude_r", String.valueOf(latitude));
                        editor.putString("Longitude_r", String.valueOf(longitude));

                        editor.putString("Socket_residential", socket_str);
                        editor.putString("Socket_vtg_R", socket_vtg_str);
                        if(charger_type_str.equals("Charging Station"))
                        {
//                            editor.putString("Rate_resi", cs_rate);
                            editor.putString("Rate", cs_rate);
                            editor.putString("Board",cs_bosrd);
                            editor.putString("Power",cs_power);
                            editor.putString("Voltage",cs_vtg);
                            editor.putString("Amphere",cs_amphere);
                            editor.putString("Socket_residential",null);



                        }
                        else if (charger_type_str.equals("Standard Domestic Socket"))
                        {
                            editor.putString("Rate", socket_rate);
                            editor.putString("Board", socket_board_str);
                            editor.putString("Power",null);
                            editor.putString("Voltage",socket_vtg_str);
                            editor.putString("Amphere",socket_amphere_str);
                            editor.putString("Socket_residential",socket_str);

                        }


                        editor.commit();
                        editor.apply();

                TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.simpleTabLayout2);
                tabhost.getTabAt(1).select();

                    }

                }

            }
        });

        return rootView;

    }


    private void setSpinnerError(Spinner spinner, String error) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId() + "," + place.getLatLng() + "," + place.getAddressComponents());
                edittext_2.setText(place.getAddress());
                edittext_1.setText(place.getName());
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
                            city_text.setText(sCity);

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
                            country_text.setText(sCountry);
                        }
                        else if (type.equals("sublocality_level_1") |(type.equals("sublocality_level_2") | (type.equals("route"))))
                        {
                            String sLM = ac.getName();
                            Log.i("TAG", "Landmark: " + sLM);
                            landmark_text.setText(sLM);
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
