package com.example.elshare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import com.example.elshare.adapter.GooglePlacesAutocompleteAdapter;
import com.example.elshare.utils.RequiredFieldUtil;
import com.example.elshare.utils.SingletonRetrofit;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

import static com.example.elshare.utils.ElshareConstants.GOOGLE_MAP_API_KEY;

public class AddAnotherResidentialFragment extends Fragment implements AdapterView.OnItemClickListener {
    private GooglePlacesAutocompleteAdapter mGooglePlacesAutocompleteAdapter;
    private AutoCompleteTextView mAddressAutoCompleteTextView;
    private static final int RESULT_OK = -1;
    private static final int RESULT_CANCELED = -1;
    double longitude, latitude;
    EditText edit_address;
    private List<charger_type> charger_t;
    private List<Charegr_brand> charger_brand;
    private List<charger_model> charger_model;
    private List<residential_board> board_m;
    private List<residetial_rate> rate_m;
    private List<Connector_type> connetor_set;
    private List<Socket> socket_set;
    private List<residential_power_output> power_com;
    private List<residrntial_voltage> volt_com;
    private List<residential_charger_amphere> charger_amphereList;
    private List<residential_rate_socket> socket_rate_res;
    private List<residential_board_socket> socket_board_res;
    private List<residential_voltage_socket> socket_vtg_res;
    private List<residential_socket_amphere> socket_amphere_res_list;


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


    private CardView crd;
    private EditText edittext_1, edittext_2, state_text, city_text, country_text, landmark_text, zip_txt;
    private String address_str1, add_str2, state_st, city_str, country_str, landmark_str, zip_str;
    private String charger_type_str, brand_str, cs_model_str, cs_connector_str, cs_vtg, cs_amphere, cs_bosrd, cs_rate, cs_power, socket_str, socket_rate, socket_board_str, socket_vtg_str, socket_amphere_str;
    private String api_key = "AIzaSyDNdANqUDsLyz6s3mtEJuEQBNaW4xVpK8k";
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private TextView mFinalPowerOutputTextView;
     Spinner chargerTypeSpinner;
     Spinner electricityBoardSpinner;
     Spinner rateStructureSpinner;
     Spinner chargerBrandSpinner;
     Spinner chargerModelSpinner;
     Spinner connectorTypeSpinner;
     Spinner socketSpinner;
     Spinner powerOutputSpinner;
     Spinner voltageSpinner;
     Spinner amphereSpinner;
     EditText socketPowerOutputEditText;
     Spinner socketVoltageSpinner;
     Spinner socketAmphereSpinner;
     Spinner socketElectricityBoardSpinner;
     Spinner socketRateSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.add_another_activity_residential, container, false);
        Button nextButton = rootView.findViewById(R.id.nextButton);
        address_str1 = "";
        add_str2 = "";
        state_st = "";
        city_str = "";
        country_str = "";
        landmark_str = "";
        zip_str = "";
        
        crd = rootView.findViewById(R.id.mycard);
        edittext_1 = rootView.findViewById(R.id.editText49);
        edittext_2 = rootView.findViewById(R.id.editText50);
        country_text = rootView.findViewById(R.id.editText46);
        state_text = rootView.findViewById(R.id.editText47);
        city_text = rootView.findViewById(R.id.editText48);
        landmark_text = rootView.findViewById(R.id.editText83);
        zip_txt = rootView.findViewById(R.id.editText86);


        Places.initialize(getContext(), "AIzaSyDNdANqUDsLyz6s3mtEJuEQBNaW4xVpK8k");
        //edittext_1.setFocusable(false);
        edittext_1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.ADDRESS_COMPONENTS);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields).setCountry("IN") //NIGERIA
                        .build(Objects.requireNonNull(getContext()));
                //startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

        String[] array1 = {"Charger Type", "Charging Station", "Standard Domestic Socket"};

        chargerTypeSpinner = rootView.findViewById(R.id.charger_type_spinner);
        electricityBoardSpinner = rootView.findViewById(R.id.electricity_board_spinner);
        rateStructureSpinner = rootView.findViewById(R.id.rate_structure_spinner);
        chargerBrandSpinner = rootView.findViewById(R.id.charger_brand_spinner);
        chargerModelSpinner = rootView.findViewById(R.id.charger_model_spinner);
        connectorTypeSpinner = rootView.findViewById(R.id.connector_type_spinner);
        socketSpinner = rootView.findViewById(R.id.socket_spinner);
        powerOutputSpinner = rootView.findViewById(R.id.power_output_spinner);
        voltageSpinner = rootView.findViewById(R.id.voltage_spinner);
        amphereSpinner = rootView.findViewById(R.id.amphere_spinner);
        socketPowerOutputEditText = rootView.findViewById(R.id.socket_power_output_edit_text);
        socketVoltageSpinner = rootView.findViewById(R.id.socket_voltage_spinner);
        socketAmphereSpinner = rootView.findViewById(R.id.socket_amphere_spinner);
        socketElectricityBoardSpinner = rootView.findViewById(R.id.socket_electricity_board_spinner);
        socketRateSpinner = rootView.findViewById(R.id.socket_rate_spinner);
        mFinalPowerOutputTextView = rootView.findViewById(R.id.final_power_output_text_view);

        final ArrayList<String> spinner_charger_type = new ArrayList<>();
        final ArrayList<String> spinner_brand = new ArrayList<>();
        final ArrayList<String> spinner_model = new ArrayList<>();
        final ArrayList<String> spinner_board = new ArrayList<>();
        final ArrayList<String> spinner_rate = new ArrayList<>();
        final ArrayList<String> spinner_connector = new ArrayList<>();
        final ArrayList<String> spinner_power_output = new ArrayList<>();
        final ArrayList<String> spinner_voltage = new ArrayList<>();
        final ArrayList<String> spinner_amphere = new ArrayList<>();
        final ArrayList<String> spinner_socket = new ArrayList<>();
        final ArrayList<String> socket_voltage = new ArrayList<>();
        final ArrayList<String> socket_amphere = new ArrayList<>();
        final ArrayList<String> socket_rate_array = new ArrayList<>();
        final ArrayList<String> socket_eboard = new ArrayList<>();

        mAddressAutoCompleteTextView = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
        mGooglePlacesAutocompleteAdapter = new GooglePlacesAutocompleteAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item);
        mAddressAutoCompleteTextView.setAdapter(mGooglePlacesAutocompleteAdapter);
        mAddressAutoCompleteTextView.setOnItemClickListener(this);

        //Select board---------------------------------------------
        chargerTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                charger_type_str = chargerTypeSpinner.getSelectedItem().toString();
                if(charger_type_str.equalsIgnoreCase("Charger Type")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                String state_str2 = state_text.getText().toString();
                if (state_str2.isEmpty()) {
                    Toast.makeText(getContext(), "Enter state field", Toast.LENGTH_LONG).show();
                    chargerBrandSpinner.setVisibility(View.GONE);
                    chargerModelSpinner.setVisibility(View.GONE);
                    electricityBoardSpinner.setVisibility(View.GONE);
                    rateStructureSpinner.setVisibility(View.GONE);
                    powerOutputSpinner.setVisibility(View.GONE);
                    voltageSpinner.setVisibility(View.GONE);
                    amphereSpinner.setVisibility(View.GONE);
                    socketVoltageSpinner.setVisibility(View.GONE);
                    socketAmphereSpinner.setVisibility(View.GONE);
                    socketPowerOutputEditText.setVisibility(View.GONE);
                    connectorTypeSpinner.setVisibility(View.GONE);
                    socketSpinner.setVisibility(View.GONE);
                    socketElectricityBoardSpinner.setVisibility(View.GONE);
                    socketRateSpinner.setVisibility(View.GONE);
                    mFinalPowerOutputTextView.setVisibility(View.GONE);
                }

                if (charger_type_str == "Charger Type") {
                    chargerBrandSpinner.setVisibility(View.GONE);
                    chargerModelSpinner.setVisibility(View.GONE);
                    electricityBoardSpinner.setVisibility(View.GONE);
                    rateStructureSpinner.setVisibility(View.GONE);
                    powerOutputSpinner.setVisibility(View.GONE);
                    voltageSpinner.setVisibility(View.GONE);
                    amphereSpinner.setVisibility(View.GONE);
                    socketVoltageSpinner.setVisibility(View.GONE);
                    socketAmphereSpinner.setVisibility(View.GONE);
                    socketPowerOutputEditText.setVisibility(View.GONE);
                    connectorTypeSpinner.setVisibility(View.GONE);
                    socketSpinner.setVisibility(View.GONE);
                    socketElectricityBoardSpinner.setVisibility(View.GONE);
                    socketRateSpinner.setVisibility(View.GONE);
                    mFinalPowerOutputTextView.setVisibility(View.GONE);
                } else if (charger_type_str.equals("Charging Station")) {
                    chargerBrandSpinner.setVisibility(View.VISIBLE);
                    mFinalPowerOutputTextView.setVisibility(View.VISIBLE);
                    chargerModelSpinner.setVisibility(View.GONE);
                    electricityBoardSpinner.setVisibility(View.GONE);
                    rateStructureSpinner.setVisibility(View.GONE);
                    powerOutputSpinner.setVisibility(View.GONE);
                    voltageSpinner.setVisibility(View.GONE);
                    amphereSpinner.setVisibility(View.GONE);
                    socketVoltageSpinner.setVisibility(View.GONE);
                    socketAmphereSpinner.setVisibility(View.GONE);
                    socketPowerOutputEditText.setVisibility(View.GONE);
                    connectorTypeSpinner.setVisibility(View.GONE);
                    socketSpinner.setVisibility(View.GONE);
                    socketElectricityBoardSpinner.setVisibility(View.GONE);
                    socketRateSpinner.setVisibility(View.GONE);

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
                    electricityBoardSpinner.setAdapter(adapter35);
                    electricityBoardSpinner.setVisibility(View.VISIBLE);

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
                    rateStructureSpinner.setAdapter(adapter36);
                    rateStructureSpinner.setVisibility(View.VISIBLE);
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
                    
                    ArrayAdapter<String> adapter9 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_brand);
                    chargerBrandSpinner.setAdapter(adapter9);
                    chargerBrandSpinner.setVisibility(View.VISIBLE);
                    
                } else if (charger_type_str.equals("Standard Domestic Socket")) {
                    mFinalPowerOutputTextView.setVisibility(View.VISIBLE);
                    chargerBrandSpinner.setVisibility(View.GONE);
                    chargerModelSpinner.setVisibility(View.GONE);
                    electricityBoardSpinner.setVisibility(View.GONE);
                    rateStructureSpinner.setVisibility(View.GONE);
                    powerOutputSpinner.setVisibility(View.GONE);
                    voltageSpinner.setVisibility(View.GONE);
                    amphereSpinner.setVisibility(View.GONE);
                    connectorTypeSpinner.setVisibility(View.GONE);
                    socketSpinner.setVisibility(View.VISIBLE);
                    socketVoltageSpinner.setVisibility(View.VISIBLE);
                    socketAmphereSpinner.setVisibility(View.VISIBLE);
                    socketPowerOutputEditText.setVisibility(View.VISIBLE);
                    socketElectricityBoardSpinner.setVisibility(View.VISIBLE);
                    socketRateSpinner.setVisibility(View.VISIBLE);
                    String state_str = state_text.getText().toString();
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
                    socketSpinner.setAdapter(adapter51);
                    socketSpinner.setVisibility(View.VISIBLE);
                    
                    //Select rate of socket------------------------------------------
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
                    
                    ArrayAdapter<String> adapter56 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_rate_array);
                    socketRateSpinner.setAdapter(adapter56);
                    socketRateSpinner.setVisibility(View.VISIBLE);

                    //Select socket board---------------------------------------------
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

                    ArrayAdapter<String> adapter55 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_eboard);
                    socketElectricityBoardSpinner.setAdapter(adapter55);
                    socketElectricityBoardSpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });
        
        chargerBrandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brand_str = chargerBrandSpinner.getSelectedItem().toString();
                if(brand_str.equalsIgnoreCase("Charger Brand")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                if (brand_str.equals("Charger Brand")) {
                    chargerModelSpinner.setVisibility(View.GONE);
                } else {
                    String model_str = chargerBrandSpinner.getSelectedItem().toString();
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
                    chargerModelSpinner.setAdapter(adapter10);
                    chargerModelSpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
        chargerModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String state_str = state_text.getText().toString();
                Log.i("State  : ", state_str);
                cs_model_str = chargerModelSpinner.getSelectedItem().toString();
                if(cs_model_str.equalsIgnoreCase("Charger Model")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                if (cs_model_str.equals("Charger Model")) {
                    connectorTypeSpinner.setVisibility(View.GONE);
                } else if (state_str.isEmpty()) {
                    Toast.makeText(getContext(), "State is empty", Toast.LENGTH_LONG).show();
                } else {
                    String model_str = chargerModelSpinner.getSelectedItem().toString();
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
                    connectorTypeSpinner.setAdapter(adapter50);
                    connectorTypeSpinner.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });
        
        connectorTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String state_str = state_text.getText().toString();
                String model_str = chargerModelSpinner.getSelectedItem().toString();
                String brand_str = chargerBrandSpinner.getSelectedItem().toString();
                String connector_str = connectorTypeSpinner.getSelectedItem().toString();
                cs_connector_str = connectorTypeSpinner.getSelectedItem().toString();
                if(cs_connector_str.equalsIgnoreCase("Connector Type")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                if (cs_connector_str.equals("Connector Type")) {
                    voltageSpinner.setVisibility(View.GONE);
                    powerOutputSpinner.setVisibility(View.GONE);
                    amphereSpinner.setVisibility(View.GONE);
                } else {
                    spinner_power_output.clear();
                    spinner_voltage.clear();
                    spinner_amphere.clear();
                    spinner_voltage.add("Voltage");
                    spinner_power_output.add("Power output");
                    spinner_amphere.add("Amphere");

                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<residential_power_output>> call = service.getPowerOutputRes(brand_str, model_str);
                    Log.i("power Res***********************************************************************************************  : ", String.valueOf(call.request().url()));
                    call.enqueue(new Callback<List<residential_power_output>>() {
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
                        public void onFailure(Call<List<residential_power_output>> call, Throwable t) {
                            Log.i("Pewer output Res error: ", String.valueOf(t));
                        }
                    });
                    
                    APIInterface service5 = SingletonRetrofit.getAPIInterface();

                    Call<List<residrntial_voltage>> call6 = service5.getVoltageRes(brand_str, model_str);
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
                                charger_amphereList = response.body();

                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    residential_charger_amphere amphereItem = charger_amphereList.get(i);
                                    demo_str = amphereItem.getChargerAmphereRes();
                                    spinner_amphere.add(demo_str);
                                    Log.i("Amphere output Array: ", spinner_amphere.toString());
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


                    ArrayAdapter<String> adapter52 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_power_output);
                    ArrayAdapter<String> adapter53 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_voltage);
                    ArrayAdapter<String> adapter54 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_amphere);

                    powerOutputSpinner.setAdapter(adapter52);
                    voltageSpinner.setAdapter(adapter53);
                    amphereSpinner.setAdapter(adapter54);

                    powerOutputSpinner.setVisibility(View.VISIBLE);
                    voltageSpinner.setVisibility(View.VISIBLE);
                    amphereSpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });

        socketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String socket_voltage_str = socketSpinner.getSelectedItem().toString();
                if(socket_voltage_str.equalsIgnoreCase("Select socket")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                if(TextUtils.isEmpty(socket_str)) {
                    socket_str = "";
                }
                if(socketSpinner.getSelectedItem() != null) {
                    socket_str = socketSpinner.getSelectedItem().toString();
                }
                if (socket_str.equals("Select socket")) {
                    socketVoltageSpinner.setVisibility(View.GONE);
                    socketAmphereSpinner.setVisibility(View.GONE);
                    socketPowerOutputEditText.setVisibility(View.GONE);
                } else {
                    String state_str = state_text.getText().toString();
                    socket_voltage.clear();
                    socket_amphere.clear();
                    socket_voltage.add("socket voltage");
                    socket_amphere.add("socket Amphere");

                    //Select voltage of socket------------------------------------------
                    String socket_str = socketSpinner.getSelectedItem().toString();
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
                                    socket_vtg = socket_vtg_res.get(i);
                                    demo_str = socket_vtg.getVoltageSocketRes();
                                    Log.i("Residential socket vtg R: ", String.valueOf(demo_str));
                                    socket_voltage.add(demo_str);
                                    Log.i("Residential socket vtg Array: ", socket_voltage.toString());
                                }
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

                    Call<List<residential_socket_amphere>> call5 = service.getSocketAmphereRes(socket_str);
                    call5.enqueue(new Callback<List<residential_socket_amphere>>() {

                        @SuppressLint("LongLogTag")
                        @Override
                        public void onResponse(Call<List<residential_socket_amphere>> call, Response<List<residential_socket_amphere>> response) {

                            if (response.body() != null) {
                                Log.i("Residential socket vtg: ", String.valueOf(response.body()));
                                socket_amphere_res_list = response.body();
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    residential_socket_amphere amphereItem = socket_amphere_res_list.get(i);
                                    demo_str = amphereItem.getSocketAmphereRes();
                                    Log.i("Residential socket vtg R: ", String.valueOf(demo_str));
                                    socket_amphere.add(demo_str);
                                    Log.i("Residential socket vtg Array: ", socket_amphere.toString());
                                }
                            } else {
                                String new_str = "Residential socket vtg is empty";
                            }
                        }

                        @SuppressLint("LongLogTag")
                        @Override
                        public void onFailure(Call<List<residential_socket_amphere>> call, Throwable t) {
                            Log.i("Residential socket vtg error: ", String.valueOf(t));
                        }
                    });

                    ArrayAdapter<String> adapter54 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_voltage);
                    socketVoltageSpinner.setAdapter(adapter54);
                    socketVoltageSpinner.setVisibility(View.VISIBLE);

                    ArrayAdapter<String> adapter55 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_amphere);
                    socketAmphereSpinner.setAdapter(adapter55);
                    socketAmphereSpinner.setVisibility(View.VISIBLE);

                    socketPowerOutputEditText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });
        
        electricityBoardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_bosrd = electricityBoardSpinner.getSelectedItem().toString();
                if(cs_bosrd.equalsIgnoreCase("Electricity board")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });
        
        rateStructureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_rate = rateStructureSpinner.getSelectedItem().toString();
                if(cs_rate.equalsIgnoreCase("Rate structure")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });
        
        powerOutputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_power = powerOutputSpinner.getSelectedItem().toString();
                String selectedValue = powerOutputSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Power output")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                float finalPowerOutputValue = 0;
                if (!TextUtils.isEmpty(selectedValue) && !selectedValue.equalsIgnoreCase("Power output")) {
                    try {
                        finalPowerOutputValue = Float.parseFloat(selectedValue);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                if(voltageSpinner.getSelectedItemPosition() != 0) {
                    voltageSpinner.setSelection(0);
                }
                if(amphereSpinner.getSelectedItemPosition() != 0) {
                    amphereSpinner.setSelection(0);
                }
                mFinalPowerOutputTextView.setText("" + finalPowerOutputValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });
        
        voltageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_vtg = voltageSpinner.getSelectedItem().toString();
                String selectedValue = voltageSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Voltage")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                float voltageValue = 0;
                float amphereValue = 0;
                if(!TextUtils.isEmpty(cs_vtg) && !cs_vtg.equalsIgnoreCase("Voltage")
                        && !TextUtils.isEmpty(cs_amphere) && !cs_amphere.equalsIgnoreCase("Amphere")){
                    try{
                        voltageValue = Float.parseFloat(cs_vtg);
                        amphereValue = Float.parseFloat(cs_amphere);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                setFinalPowerOutput(voltageValue, amphereValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });

        amphereSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_amphere = amphereSpinner.getSelectedItem().toString();
                String selectedValue = amphereSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Amphere")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                float voltageValue = 0;
                float amphereValue = 0;
                if(!TextUtils.isEmpty(cs_vtg) && !cs_vtg.equalsIgnoreCase("Voltage")
                        && !TextUtils.isEmpty(cs_amphere) && !cs_amphere.equalsIgnoreCase("Amphere")){
                    try{
                        voltageValue = Float.parseFloat(cs_vtg);
                        amphereValue = Float.parseFloat(cs_amphere);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                setFinalPowerOutput(voltageValue, amphereValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });

        socketVoltageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_vtg_str = socketVoltageSpinner.getSelectedItem().toString();
                String selectedValue = socketVoltageSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("socket voltage")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                float voltageValue = 0;
                float amphereValue = 0;
                if(!TextUtils.isEmpty(socket_vtg_str) && !socket_vtg_str.equalsIgnoreCase("socket voltage")
                        && !TextUtils.isEmpty(socket_amphere_str) && !socket_amphere_str.equalsIgnoreCase("socket Amphere")){
                    try{
                        voltageValue = Float.parseFloat(socket_vtg_str);
                        amphereValue = Float.parseFloat(socket_amphere_str);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                setFinalPowerOutput(voltageValue, amphereValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });

        socketPowerOutputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String selectedValue = socketPowerOutputEditText.getText().toString();
                float finalPowerOutputValue = 0;
                if (!TextUtils.isEmpty(selectedValue) && !selectedValue.equalsIgnoreCase("Power output")) {
                    try {
                        finalPowerOutputValue = Float.parseFloat(selectedValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (socketVoltageSpinner.getSelectedItemPosition() != 0){
                    socketVoltageSpinner.setSelection(0);
                }
                if(socketAmphereSpinner.getSelectedItemPosition() != 0) {
                    socketAmphereSpinner.setSelection(0);
                }
                    mFinalPowerOutputTextView.setText(""+finalPowerOutputValue);
            }
        });

        socketAmphereSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_amphere_str = socketAmphereSpinner.getSelectedItem().toString();
                String selectedValue = socketAmphereSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("socket Amphere")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                float voltageValue = 0;
                float amphereValue = 0;
                if(!TextUtils.isEmpty(socket_vtg_str) && !socket_vtg_str.equalsIgnoreCase("socket voltage")
                        && !TextUtils.isEmpty(socket_amphere_str) && !socket_amphere_str.equalsIgnoreCase("socket Amphere")){
                    try{
                        voltageValue = Float.parseFloat(socket_vtg_str);
                        amphereValue = Float.parseFloat(socket_amphere_str);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                setFinalPowerOutput(voltageValue, amphereValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });
        
        socketElectricityBoardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_board_str = socketElectricityBoardSpinner.getSelectedItem().toString();
                String selectedValue = socketElectricityBoardSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Select board")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });
        
        socketRateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_rate = socketRateSpinner.getSelectedItem().toString();
                String selectedValue = socketRateSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("select socket rate")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });
        
        ArrayAdapter<String> adapter8 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, array1);
        chargerTypeSpinner.setAdapter(adapter8);

        final ArrayList<EditText> editTextArrayList = new ArrayList<>();
        editTextArrayList.add(edittext_1);
        editTextArrayList.add(edittext_2);
        editTextArrayList.add(country_text);
        editTextArrayList.add(state_text);
        editTextArrayList.add(city_text);
        editTextArrayList.add(landmark_text);
        editTextArrayList.add(zip_txt);

        //add all spinner
        final ArrayList<Spinner> spinnerArrayList = new ArrayList<>();
        spinnerArrayList.add(chargerTypeSpinner); //"Charger Type"
        spinnerArrayList.add(electricityBoardSpinner); //"Electricity board"
        spinnerArrayList.add(rateStructureSpinner); //"Rate structure"
        spinnerArrayList.add(chargerBrandSpinner); //"Charger Brand"
        spinnerArrayList.add(chargerModelSpinner); //"Charger Model"
        spinnerArrayList.add(connectorTypeSpinner); //"Connector Type"
        spinnerArrayList.add(socketSpinner); //"Select socket"
        //spinnerArrayList.add(powerOutputSpinner); //"Power output"
        spinnerArrayList.add(voltageSpinner); //"Voltage"
        spinnerArrayList.add(socketVoltageSpinner); //"socket voltage"
        spinnerArrayList.add(socketElectricityBoardSpinner); //"Select board"
        spinnerArrayList.add(socketRateSpinner); //"select socket rate"

        //add default value for each spinner in same sequence.
        final ArrayList<String> spinnerArrayDefaultValueList = new ArrayList<>();
        spinnerArrayDefaultValueList.add("Charger Type");
        spinnerArrayDefaultValueList.add("Electricity board");
        spinnerArrayDefaultValueList.add("Rate structure");
        spinnerArrayDefaultValueList.add("Charger Brand");
        spinnerArrayDefaultValueList.add("Charger Model");
        spinnerArrayDefaultValueList.add("Connector Type");
        spinnerArrayDefaultValueList.add("Select socket");
        spinnerArrayDefaultValueList.add("Power output");
        spinnerArrayDefaultValueList.add("Voltage");
        spinnerArrayDefaultValueList.add("socket voltage");
        spinnerArrayDefaultValueList.add("Select board");
        spinnerArrayDefaultValueList.add("select socket rate");

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Boolean address1_valid, state_valid;

                if(RequiredFieldUtil.isRequiredFieldEmpty(editTextArrayList)){
                    Toast.makeText(getContext(), "The required Field can not be blank", Toast.LENGTH_LONG).show();
                    return;
                }

                if(RequiredFieldUtil.isRequiredSpinner(spinnerArrayDefaultValueList, spinnerArrayList)){
                    Toast.makeText(getContext(), "The required drop down field can not be blank", Toast.LENGTH_LONG).show();
                    return;
                }

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
                    address_str1 = edittext_1.getText().toString();
                    add_str2 = edittext_2.getText().toString();
                    city_str = city_text.getText().toString();
                    state_st = state_text.getText().toString();
                    country_str = country_text.getText().toString();
                    zip_str = zip_txt.getText().toString();
                    landmark_str = landmark_text.getText().toString();

                    if (brand_str != null && brand_str.equals("Charger Brand") && state_st.isEmpty()) {
                        Toast.makeText(getContext(), "All field are mandetory", Toast.LENGTH_LONG).show();
                    } else {
                        Log.i("Residential field:", charger_type_str + brand_str + cs_model_str + cs_connector_str + cs_power + cs_rate + cs_vtg + cs_bosrd);
                        Log.i("Address field:", edittext_1.toString() + edittext_2.toString() + state_text.toString() + country_text.toString() + landmark_text.toString() + zip_txt.toString() + city_text.toString());
                        Log.i("Address field 2:", address_str1 + add_str2 + state_st + country_str + zip_str + landmark_str + city_str);

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        String host_str2 = "RESIDENTIAL";
                        editor.putString("HOST", host_str2);
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
                        editor.putString("Charger_type_resi", charger_type_str);
                        editor.putString("Charger_brand_resi", brand_str);
                        editor.putString("Charger_model_resi", cs_model_str);
                        editor.putString("Connector_resi", cs_connector_str);
                        editor.putString("power_resi", cs_power);
                        editor.putString("Voltage_resi", cs_vtg);

                       if (!TextUtils.isEmpty(charger_type_str) && charger_type_str.equals("Charging Station")) {
                            editor.putString("Rate", cs_rate);
                            editor.putString("electricity_board", cs_bosrd);
                            editor.putString("voltage", voltageSpinner.getSelectedItem().toString());
                            editor.putString("amphere", amphereSpinner.getSelectedItem().toString());
                        } else if (!TextUtils.isEmpty(charger_type_str) && charger_type_str.equals("Standard Domestic Socket")) {
                            editor.putString("Rate", socket_rate);
                            editor.putString("electricity_board", socket_board_str);
                            editor.putString("voltage", socketVoltageSpinner.getSelectedItem().toString());
                            editor.putString("amphere", socketAmphereSpinner.getSelectedItem().toString());
                        }
                        editor.putString("final_poweroutput", mFinalPowerOutputTextView.getText().toString());
                        
                        editor.commit();
                        editor.apply();
                        TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.simpleTabLayout10);
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


    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        mAddressAutoCompleteTextView.setText("");
        String place_id = mGooglePlacesAutocompleteAdapter.getPlaceID(position);
        String url = "https://maps.googleapis.com/maps/api/place/details/";
        final APIInterface apiInterface = SingletonRetrofit.getGoogleMapRetrofit(url).create(APIInterface.class);
        Call<JsonObject> call = apiInterface.getWholeAddress(GOOGLE_MAP_API_KEY, place_id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JsonArray addressComponents = (JsonArray) ((JsonObject)response.body().get("result")).get("address_components");
                    JsonObject geometry = (JsonObject) ((JsonObject)response.body().get("result")).get("geometry");

                    edittext_1.setText("");
                    edittext_2.setText("");
                    latitude = 0;
                    longitude = 0;
                    zip_txt.setText("");
                    city_text.setText("");
                    state_text.setText("");
                    country_text.setText("");
                    landmark_text.setText("");

                    String formatted_address = ((JsonObject)response.body().get("result")).get("formatted_address").toString();
                    formatted_address = trimDoubbleQuote(formatted_address);
                    String lat = ((JsonObject)geometry.get("location")).get("lat").toString();
                    String lng = ((JsonObject)geometry.get("location")).get("lng").toString();

                    try {
                        latitude = Double.parseDouble(lat);
                        longitude = Double.parseDouble(lng);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    edittext_1.setText(formatted_address);
                    for(int i = 0; i < addressComponents.size(); i++) {
                        JsonObject object =  ((JsonObject)(addressComponents).get(i));
                        for(int j = 0; j < object.size(); j++) {
                            JsonArray types = (JsonArray) (object).get("types");
                            for (int k = 0 ; k < types.size(); k++){
                                if(types.get(k).toString().equalsIgnoreCase("\"neighborhood\"")){
                                    String value = object.get("long_name").toString();
                                    value = trimDoubbleQuote(value);
                                    Log.i("TAG", "neighborhood: " + value);
                                    edittext_2.setText(value);
                                }else if(types.get(k).toString().equalsIgnoreCase("\"administrative_area_level_1\"")){
                                    String value = object.get("long_name").toString();
                                    value = trimDoubbleQuote(value);
                                    Log.i("TAG", "administrative_area_level_1: " + value);
                                    state_text.setText(value);
                                }else if(types.get(k).toString().equalsIgnoreCase("\"administrative_area_level_2\"")){
                                    String value = object.get("long_name").toString();
                                    value = trimDoubbleQuote(value);
                                    Log.i("TAG", "administrative_area_level_2: " + value);
                                    city_text.setText(value);
                                }else if(types.get(k).toString().equalsIgnoreCase("\"country\"")){
                                    String value = object.get("long_name").toString();
                                    value = trimDoubbleQuote(value);
                                    Log.i("TAG", "country: " + value);
                                    country_text.setText(value);
                                }else if(types.get(k).toString().equalsIgnoreCase("\"sublocality_level_1\"")){
                                    String value = object.get("long_name").toString();
                                    value = trimDoubbleQuote(value);
                                    Log.i("TAG", "sublocality_level_1: " + value);
                                    landmark_text.setText(value);
                                }else if(types.get(k).toString().equalsIgnoreCase("\"postal_code\"")){
                                    String value = object.get("long_name").toString();
                                    value = trimDoubbleQuote(value);
                                    Log.i("TAG", "pincode: " + value);
                                    zip_txt.setText(value);
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static String trimDoubbleQuote(String value){
        return value.replaceAll("^\"|\"$", "");
    }

    public void setFinalPowerOutput(float voltageValue, float amphereValue){
        //(voltage * amp)/1000 = mFinalPowerOutputTextView (final power output)
        mFinalPowerOutputTextView.setVisibility(View.VISIBLE);
        mFinalPowerOutputTextView.setTextColor(Color.parseColor("#62ba46"));
        float finalValue = (float) ((voltageValue * amphereValue) / 1000.0);
        if(powerOutputSpinner.getSelectedItemPosition() != 0){
            powerOutputSpinner.setSelection(0);
        }
        if(!"".equalsIgnoreCase(socketPowerOutputEditText.getText().toString())){
            socketPowerOutputEditText.setText("");
        }
        mFinalPowerOutputTextView.setText(""+ finalValue);
    }

    public boolean isMFinalPowerOutputTextViewChanged(String oldValue, String newValue){
        return !oldValue.equals(newValue);
    }

}
