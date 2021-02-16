package com.example.elshare;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.elshare.utils.SingletonRetrofit;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommercialFragment extends Fragment {
    private static final int RESULT_OK = -1;
    private static final int RESULT_CANCELED = -1;
    Spinner spinner;
    private ViewPager viewPager;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private EditText edittext_1, edittext_2, state_text, city_text, country_text, landmark_text, zip_txt;
    private String charger_type_str, brand_str, CS_model_str, cs_connector_str, cs_power, cs_vtg, cs_rate, cs_board, socket_str, socket_vtg, str, socket_rate_str, socket_board_str;
    private String api_key = "AIzaSyDNdANqUDsLyz6s3mtEJuEQBNaW4xVpK8k";
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;

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

    private double longitude, latitude;
    private commertial_charger_type charger_b;
    private commertial_charger_model charger_m;
    private commertial_connector connector_c;
    private commertial_rate rate_r;
    private Socket_commertial socket_list;
    private commertial_voltage_socket com_voltage;
    private commertial_rate_socket com_rate;
    private commertial_board_socket com_board;
    private commertial_board board_c;
    private commertial_power_output power_c;
    private commertial_voltage volt_c;
    private commertial_charger_amphere charger_amp;
    private commertial_socket_amphere socket_amp;

    private ArrayList<String> spinner_charger_brand, spinner_charger_model, socket_board_array, socket_voltage, socket_power, spinner_power_output, spinner_electricity_board;
    private Spinner spinner33, spinner62, spinner61, spinner34;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private ArrayList<Double> spinner_rate_structure, socket_rate_array;

    @SuppressLint("LongLogTag")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_commertial, container, false);
        String[] array1 = {"Charger Type", "Charging Station", "Standard Domestic Socket"};

        edittext_1 = rootView.findViewById(R.id.editText54);
        edittext_2 = rootView.findViewById(R.id.editText55);
        country_text = rootView.findViewById(R.id.editText51);
        state_text = rootView.findViewById(R.id.editText52);
        city_text = rootView.findViewById(R.id.editText53);
        landmark_text = rootView.findViewById(R.id.editText56);
        zip_txt = rootView.findViewById(R.id.editText84);

        Places.initialize(getContext(), "AIzaSyDNdANqUDsLyz6s3mtEJuEQBNaW4xVpK8k");
        edittext_1.setFocusable(false);
        edittext_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.ADDRESS_COMPONENTS);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields).setCountry("IN") //NIGERIA
                        .build(Objects.requireNonNull(getContext()));
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

        final ArrayList<String> spinner_charger_brand = new ArrayList<>();
        final ArrayList<String> spinner_charger_model = new ArrayList<>();
        final ArrayList<String> spinner_connector = new ArrayList<>();
        final ArrayList<String> spinner_electricity_board = new ArrayList<>();
        final ArrayList<String> spinner_power_output = new ArrayList<>();
        final ArrayList<String> spinner_rate_structure = new ArrayList<>();
        final ArrayList<String> spinner_voltage = new ArrayList<>();
        final ArrayList<String> socket_board_array = new ArrayList<>();
        final ArrayList<String> socket_rate_array = new ArrayList<>();
        final ArrayList<String> socket_power = new ArrayList<>();
        final ArrayList<String> socket_voltage = new ArrayList<>();
        final ArrayList<String> socket_list_array = new ArrayList<>();
        final ArrayList<String> socket_amphere = new ArrayList<>();
        final ArrayList<String> charger_amphere = new ArrayList<>();


        final Spinner spinner = rootView.findViewById(R.id.spinner);//charger type -array1
        final Spinner spinner30 = rootView.findViewById(R.id.spinner30);//brand
        final Spinner spinner31 = rootView.findViewById(R.id.spinner31);//model
        final Spinner spinner32 = rootView.findViewById(R.id.spinner32);//connector
        final Spinner spinner33 = rootView.findViewById(R.id.spinner33);//cs-board
        final Spinner spinner34 = rootView.findViewById(R.id.spinner34);//cs-rate
        final Spinner spinner60 = rootView.findViewById(R.id.spinner60);//socket list
        final Spinner spinner61 = rootView.findViewById(R.id.spinner61);//socket-board
        final Spinner spinner62 = rootView.findViewById(R.id.spinner62);//socket-rate
        final Spinner spinner63 = rootView.findViewById(R.id.spinner63);//socket-vtg
        final Spinner spinner64 = rootView.findViewById(R.id.spinner64);//cs_power
        final Spinner spinner65 = rootView.findViewById(R.id.spinner65);//cs_vtg
        final Spinner charger_A = rootView.findViewById(R.id.com_amphere_charger);
        final Spinner socket_A = rootView.findViewById(R.id.com_socket_amphere);

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


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String state_str = state_text.getText().toString();
                if (state_str.isEmpty()) {
                    Toast.makeText(getContext(), "Select state ", Toast.LENGTH_SHORT).show();
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
                } else {
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
                        charger_type_str = spinner.getSelectedItem().toString();
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

                        ArrayAdapter<String> adapter30 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_charger_brand);
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
                                        spinner_rate_structure.add(String.valueOf(demo_str));
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

                        ArrayAdapter<String> adapter34 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_rate_structure);
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

                        ArrayAdapter<String> adapter33 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_electricity_board);
                        spinner33.setAdapter(adapter33);
                        spinner33.setVisibility(View.VISIBLE);
                    } else if (type_str.equals("Standard Domestic Socket")) {
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
                        charger_type_str = spinner.getSelectedItem().toString();
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

                        ArrayAdapter<String> adapter60 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_list_array);
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

                        ArrayAdapter<String> adapter62 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_rate_array);
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

                        ArrayAdapter<String> adapter61 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_board_array);
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
              //Override Method
            }
        });

        spinner30.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brand_str = spinner30.getSelectedItem().toString();
                if (brand_str.equals("Charger brand")) {
                    spinner31.setVisibility(View.GONE);
                } else {
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

                    ArrayAdapter<String> adapter31 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_charger_model);
                    spinner31.setAdapter(adapter31);
                    spinner31.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override Method
            }
        });

        spinner31.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CS_model_str = spinner31.getSelectedItem().toString();
                if (CS_model_str.equals("Charger Model")) {
                    spinner32.setVisibility(View.GONE);
                    spinner64.setVisibility(View.GONE);
                    spinner65.setVisibility(View.GONE);
                    charger_A.setVisibility(View.GONE);
                } else {
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

                    ArrayAdapter<String> adapter32 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_connector);
                    spinner32.setAdapter(adapter32);
                    spinner32.setVisibility(View.VISIBLE);
                    spinner_power_output.clear();
                    spinner_power_output.add("Power output");
                    APIInterface service2 = SingletonRetrofit.getAPIInterface();
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

                    if (spinner_voltage.size() == 1 && charger_amphere.size() == 1) {
                        ArrayAdapter<String> adapter64 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_power_output);
                        spinner64.setAdapter(adapter64);
                        spinner64.setVisibility(View.VISIBLE);
                        spinner65.setVisibility(View.GONE);
                        charger_A.setVisibility(View.GONE);
                    } else {
                        ArrayAdapter<String> adapter65 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_voltage);
                        spinner65.setAdapter(adapter65);
                        spinner65.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter_C_A = new ArrayAdapter<>(getActivity(), R.layout.sample_text, charger_amphere);
                        charger_A.setAdapter(adapter_C_A);
                        charger_A.setVisibility(View.VISIBLE);
                        spinner64.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override Method
            }

        });

        spinner32.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_connector_str = spinner32.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override Method
            }
        });

        spinner33.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_board = spinner33.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override Method
            }
        });

        spinner34.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_rate = spinner34.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override Method
            }
        });

        spinner60.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_str = spinner60.getSelectedItem().toString();
                if (socket_str.equals("Select socket")) {
                    spinner63.setVisibility(View.GONE);
                    socket_A.setVisibility(View.GONE);
                } else {
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

                    ArrayAdapter<String> adapter63 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_voltage);
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

                    ArrayAdapter<String> adapter_S_A = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_amphere);
                    socket_A.setAdapter(adapter_S_A);
                    socket_A.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override Method
            }
        });

        spinner61.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_board_str = spinner61.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override Method
            }
        });

        spinner62.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_rate_str = spinner62.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override Method
            }
        });

        spinner63.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_vtg = spinner63.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override Method
            }
        });

        ArrayAdapter<String> adapter8 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, array1);
        spinner.setAdapter(adapter8);

        Button btn = (Button) rootView.findViewById(R.id.button42);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean address1_valid, state_valid;
                if (edittext_1.getText().toString().isEmpty()) {
                    edittext_1.setError(getResources().getString(R.string.address_error));
                    address1_valid = false;
                } else {
                    address1_valid = true;
                }

                if (state_text.getText().toString().isEmpty()) {
                    state_text.setError(getResources().getString(R.string.state_error));
                    state_valid = false;
                } else {
                    state_valid = true;
                }

                if (address1_valid && state_valid) {
                    Log.i("TAG", "Commetial field: " + charger_type_str + brand_str + CS_model_str + cs_connector_str + cs_power + cs_vtg + cs_board + cs_rate);
                    Log.i("TAG", "Address field: " + edittext_1.getText().toString() + edittext_2.getText().toString() + country_text.getText().toString() + state_text.getText().toString() + city_text.getText().toString() + landmark_text.getText().toString() + zip_txt.getText().toString());
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    String host_str2 = "COMMERCIAL";
                    editor.putString("HOST", host_str2);
                    editor.putString("Charger_type_com", charger_type_str);
                    editor.putString("Charger_brand_com", brand_str);
                    editor.putString("Charger_model_com", CS_model_str);
                    editor.putString("Connector_com", cs_connector_str);
                    editor.putString("State_c", state_text.getText().toString());
                    editor.putString("Add_1_c", edittext_1.getText().toString());
                    editor.putString("Add_2_c", edittext_2.getText().toString());
                    editor.putString("City_c", city_text.getText().toString());
                    editor.putString("Country_c", country_text.getText().toString());
                    editor.putString("Land_c", landmark_text.getText().toString());
                    editor.putString("Zip_c", zip_txt.getText().toString());
                    editor.putString("Latitude_c", String.valueOf(latitude));
                    editor.putString("Longitude_c", String.valueOf(longitude));
                    editor.putString("Socket_commetial", socket_str);
                    editor.putString("Socket_vtg_C", socket_vtg);
                    editor.putString("Socket_C_board", socket_board_str);
                    editor.putString("Socket_C_rate", socket_rate_str);

                    if (charger_type_str.equals("Charging Station")) {
                        editor.putString("Rate", cs_rate);
                        editor.putString("board_com", cs_board);
                        editor.putString("power_com", cs_power);
                        editor.putString("Voltage_com", cs_vtg);
                    } else if (charger_type_str.equals("Standard Domestic Socket")) {
                        editor.putString("Rate", socket_rate_str);
                        editor.putString("board_com", socket_board_str);
                        editor.putString("power_com", null);
                        editor.putString("Voltage_com", socket_vtg);
                    }

                    editor.apply();
                    TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.simpleTabLayout2);
                    tabhost.getTabAt(1).select();
                }
            }
        });

        return rootView;
    }

    public static CommercialFragment newInstance() {
        CommercialFragment f = new CommercialFragment();
        return f;
    }


    @SuppressLint("LongLogTag")
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
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                Log.i("Latitude :", String.valueOf(latitude));
                Log.i("Longitude :", String.valueOf(longitude));
                List<AddressComponent> list = place.getAddressComponents().asList();
                for (int x = 0; x < list.size(); x++) {
                    AddressComponent ac = list.get(x);
                    List<String> types = ac.getTypes();
                    for (int y = 0; y < types.size(); y++) {
                        String type = types.get(y);
                        if (type.equals("postal_code")) {
                            String sZIP = ac.getName();
                            Log.i("TAG", "pincode: " + sZIP);
                            zip_txt.setText(sZIP);
                        } else if (type.equals("administrative_area_level_2")) {
                            String sCity = ac.getName();
                            Log.i("TAG", "city: " + sCity);
                            city_text.setText(sCity);
                        } else if (type.equals("administrative_area_level_1")) {
                            String sState = ac.getName();
                            Log.i("TAG", "state: " + sState);
                            state_text.setText(sState);
                        } else if (type.equals("country")) {
                            String sCountry = ac.getName();
                            Log.i("TAG", "Country: " + sCountry);
                            country_text.setText(sCountry);
                        } else if (type.equals("sublocality_level_1") | (type.equals("sublocality_level_2") | (type.equals("route")))) {
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
