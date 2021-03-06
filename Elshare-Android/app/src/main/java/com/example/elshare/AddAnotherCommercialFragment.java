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
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.elshare.adapter.GooglePlacesAutocompleteAdapter;
import com.example.elshare.utils.RequiredFieldUtil;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

import static com.example.elshare.utils.ElshareConstants.GOOGLE_MAP_API_KEY;

public class AddAnotherCommercialFragment extends Fragment implements AdapterView.OnItemClickListener {
    private GooglePlacesAutocompleteAdapter mGooglePlacesAutocompleteAdapter;
    private AutoCompleteTextView mAddressAutoCompleteTextView;
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
    private List<commertial_socket_amphere> socket_amphere;
    private List<commertial_rate_socket> socket_rate;
    private List<commertial_board_socket> socket_board;
    private List<commertial_board> board_com;
    private List<commertial_power_output> power_com;
    private List<commertial_voltage> volt_com;
    private List<commertial_charger_amphere> charger_amphereList;
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

    private ArrayList<String> spinner_charger_brand, spinner_charger_model, socket_board_array, socket_voltage, socket_power, spinner_power_output, spinner_electricity_board;
    //private Spinner electricityBoardSpinner, socketRateSpinner, socketElectricityBoardSpinner, rateStructureSpinner;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private ArrayList<Double> spinner_rate_structure, socket_rate_array;

    private TextView mFinalPowerOutputTextView;
    private Spinner chargerTypeSpinner;
    private Spinner electricityBoardSpinner;
    private Spinner rateStructureSpinner;
    private Spinner chargerBrandSpinner;
    private Spinner chargerModelSpinner;
    private Spinner chargerConnectorSpinner;
    private Spinner socketSpinner;
    private Spinner powerOutputSpinner;
    private Spinner voltageSpinner;
    private Spinner amphereSpinner;
    private EditText socketPowerOutputEditText;
    private Spinner socketVoltageSpinner;
    private Spinner socketAmphereSpinner;
    private Spinner socketElectricityBoardSpinner;
    private Spinner socketRateSpinner;

    @SuppressLint("LongLogTag")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_another_activity_commertial, container, false);
        String[] array1 = {"Charger Type", "Charging Station", "Standard Domestic Socket"};

        edittext_1 = rootView.findViewById(R.id.editText54);
        edittext_2 = rootView.findViewById(R.id.editText55);
        country_text = rootView.findViewById(R.id.editText51);
        state_text = rootView.findViewById(R.id.editText52);
        city_text = rootView.findViewById(R.id.editText53);
        landmark_text = rootView.findViewById(R.id.editText56);
        zip_txt = rootView.findViewById(R.id.editText84);
        Places.initialize(getContext(), "AIzaSyDNdANqUDsLyz6s3mtEJuEQBNaW4xVpK8k");
       // edittext_1.setFocusable(false);
        edittext_1.setOnClickListener(new View.OnClickListener() {
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

        final ArrayList<String> spinner_charger_brand = new ArrayList<>();
        final ArrayList<String> spinner_charger_model = new ArrayList<>();
        final ArrayList<String> spinner_connector = new ArrayList<>();
        final ArrayList<String> spinner_electricity_board = new ArrayList<>();
        final ArrayList<String> spinner_power_output = new ArrayList<>();
        final ArrayList<String> spinner_rate_structure = new ArrayList<>();
        final ArrayList<String> spinner_voltage = new ArrayList<>();
        final ArrayList<String> spinner_amphere = new ArrayList<>();

        final ArrayList<String> socket_board_array = new ArrayList<>();
        final ArrayList<String> socket_rate_array = new ArrayList<>();
        final ArrayList<String> socket_power = new ArrayList<>();
        final ArrayList<String> socket_voltage = new ArrayList<>();
        final ArrayList<String> socket_amphereList = new ArrayList<>();
        final ArrayList<String> socket_list_array = new ArrayList<>();

        chargerTypeSpinner = rootView.findViewById(R.id.charger_type_spinner);
        electricityBoardSpinner = rootView.findViewById(R.id.electricity_board_spinner);
        rateStructureSpinner = rootView.findViewById(R.id.rate_structure_spinner);
        chargerBrandSpinner = rootView.findViewById(R.id.charger_brand_spinner);
        chargerModelSpinner = rootView.findViewById(R.id.charger_model_spinner);
        chargerConnectorSpinner = rootView.findViewById(R.id.charger_connector_spinner);
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

        chargerBrandSpinner.setVisibility(View.GONE);
        chargerModelSpinner.setVisibility(View.GONE);
        chargerConnectorSpinner.setVisibility(View.GONE);
        electricityBoardSpinner.setVisibility(View.GONE);
        rateStructureSpinner.setVisibility(View.GONE);
        socketSpinner.setVisibility(View.GONE);
        socketElectricityBoardSpinner.setVisibility(View.GONE);
        socketRateSpinner.setVisibility(View.GONE);
        socketVoltageSpinner.setVisibility(View.GONE);
        socketAmphereSpinner.setVisibility(View.GONE);
        socketPowerOutputEditText.setVisibility(View.GONE);
        powerOutputSpinner.setVisibility(View.GONE);
        voltageSpinner.setVisibility(View.GONE);
        amphereSpinner.setVisibility(View.GONE);

        mAddressAutoCompleteTextView = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
        mGooglePlacesAutocompleteAdapter = new GooglePlacesAutocompleteAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item);
        mAddressAutoCompleteTextView.setAdapter(mGooglePlacesAutocompleteAdapter);
        mAddressAutoCompleteTextView.setOnItemClickListener(this);

        chargerTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = chargerTypeSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Charger Type")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                charger_type_str = chargerTypeSpinner.getSelectedItem().toString();

                if (selectedValue.equalsIgnoreCase("Charger Type")) {
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
                    chargerConnectorSpinner.setVisibility(View.GONE);
                    socketSpinner.setVisibility(View.GONE);
                    socketElectricityBoardSpinner.setVisibility(View.GONE);
                    socketRateSpinner.setVisibility(View.GONE);
                    mFinalPowerOutputTextView.setVisibility(View.GONE);
                }

                String state_str = state_text.getText().toString();
                if (state_str.isEmpty()) {
                    Toast.makeText(getContext(), "Select state ", Toast.LENGTH_SHORT).show();
                    chargerBrandSpinner.setVisibility(View.GONE);
                    chargerModelSpinner.setVisibility(View.GONE);
                    chargerConnectorSpinner.setVisibility(View.GONE);
                    electricityBoardSpinner.setVisibility(View.GONE);
                    rateStructureSpinner.setVisibility(View.GONE);
                    socketSpinner.setVisibility(View.GONE);
                    socketElectricityBoardSpinner.setVisibility(View.GONE);
                    socketRateSpinner.setVisibility(View.GONE);
                    socketVoltageSpinner.setVisibility(View.GONE);
                    socketAmphereSpinner.setVisibility(View.GONE);
                    socketPowerOutputEditText.setVisibility(View.GONE);
                    powerOutputSpinner.setVisibility(View.GONE);
                    voltageSpinner.setVisibility(View.GONE);
                    amphereSpinner.setVisibility(View.GONE);
                    mFinalPowerOutputTextView.setVisibility(View.GONE);

                } else {
                    String type_str = chargerTypeSpinner.getSelectedItem().toString();
                    if (type_str.equals("Charging Station")) {

                        chargerBrandSpinner.setVisibility(View.VISIBLE);
                        chargerModelSpinner.setVisibility(View.GONE);
                        chargerConnectorSpinner.setVisibility(View.GONE);
                        electricityBoardSpinner.setVisibility(View.GONE);
                        rateStructureSpinner.setVisibility(View.GONE);
                        socketSpinner.setVisibility(View.GONE);
                        socketElectricityBoardSpinner.setVisibility(View.GONE);
                        socketRateSpinner.setVisibility(View.GONE);
                        socketVoltageSpinner.setVisibility(View.GONE);
                        socketAmphereSpinner.setVisibility(View.GONE);
                        socketPowerOutputEditText.setVisibility(View.GONE);
                        powerOutputSpinner.setVisibility(View.GONE);
                        voltageSpinner.setVisibility(View.GONE);
                        amphereSpinner.setVisibility(View.GONE);
                        spinner_charger_brand.clear();
                        spinner_rate_structure.clear();
                        spinner_electricity_board.clear();
                        spinner_charger_brand.add("Charger Brand");
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
                        chargerBrandSpinner.setAdapter(adapter30);
                        chargerBrandSpinner.setVisibility(View.VISIBLE);
                        spinner_rate_structure.add("Rate structure");
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
                        rateStructureSpinner.setAdapter(adapter34);
                        rateStructureSpinner.setVisibility(View.VISIBLE);
                        spinner_electricity_board.add("Electricity board");
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
                        electricityBoardSpinner.setAdapter(adapter33);
                        electricityBoardSpinner.setVisibility(View.VISIBLE);
                    } else if (type_str.equals("Standard Domestic Socket")) {
                        chargerBrandSpinner.setVisibility(View.GONE);
                        chargerModelSpinner.setVisibility(View.GONE);
                        chargerConnectorSpinner.setVisibility(View.GONE);
                        electricityBoardSpinner.setVisibility(View.GONE);
                        rateStructureSpinner.setVisibility(View.GONE);
                        powerOutputSpinner.setVisibility(View.GONE);
                        voltageSpinner.setVisibility(View.GONE);
                        amphereSpinner.setVisibility(View.GONE);
                        socketSpinner.setVisibility(View.VISIBLE);
                        socketElectricityBoardSpinner.setVisibility(View.VISIBLE);
                        socketRateSpinner.setVisibility(View.VISIBLE);
                        socketVoltageSpinner.setVisibility(View.VISIBLE);
                        socketAmphereSpinner.setVisibility(View.VISIBLE);
                        socketPowerOutputEditText.setVisibility(View.VISIBLE);
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
                        socketSpinner.setAdapter(adapter60);
                        socketSpinner.setVisibility(View.VISIBLE);
                        socket_rate_array.clear();
                        socket_rate_array.add("select socket rate");
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
                        socketRateSpinner.setAdapter(adapter62);
                        socketRateSpinner.setVisibility(View.VISIBLE);
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
                        socketElectricityBoardSpinner.setAdapter(adapter61);
                        socketElectricityBoardSpinner.setVisibility(View.VISIBLE);

                    }
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
                String selectedValue = chargerBrandSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Charger Brand")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                brand_str = chargerBrandSpinner.getSelectedItem().toString();
                if (brand_str.equals("Charger Brand")) {
                    chargerModelSpinner.setVisibility(View.GONE);
                } else {
                    String type_brand_str = chargerBrandSpinner.getSelectedItem().toString();
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
                    chargerModelSpinner.setAdapter(adapter31);
                    chargerModelSpinner.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });

        chargerModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = chargerModelSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Charger Model")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                CS_model_str = chargerModelSpinner.getSelectedItem().toString();
                if (CS_model_str.equals("Charger Model")) {
                    chargerConnectorSpinner.setVisibility(View.GONE);
                } else {
                    String state_str = state_text.getText().toString();
                    String model_str = chargerModelSpinner.getSelectedItem().toString();
                    String brand_str = chargerBrandSpinner.getSelectedItem().toString();
                    spinner_connector.clear();
                    spinner_connector.add("Connector Type");

                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<commertial_connector>> call = service.getConnectorType();
                    Log.i("Connector***********************************************************************************************  : ", String.valueOf(call.request().url()));
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
                    chargerConnectorSpinner.setAdapter(adapter32);
                    chargerConnectorSpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });

        chargerConnectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = chargerConnectorSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Connector Type")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                cs_connector_str = chargerConnectorSpinner.getSelectedItem().toString();
                if (cs_connector_str.equals("Connector Type")) {
                    powerOutputSpinner.setVisibility(View.GONE);
                    voltageSpinner.setVisibility(View.GONE);
                    amphereSpinner.setVisibility(View.GONE);
                } else {
                    String cont_str = chargerConnectorSpinner.getSelectedItem().toString();
                    String state_str = state_text.getText().toString();
                    String model_str = chargerModelSpinner.getSelectedItem().toString();
                    String brand_str = chargerBrandSpinner.getSelectedItem().toString();
                    spinner_power_output.clear();
                    spinner_power_output.add("Power output");

                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<commertial_power_output>> call = service.getPowerOutputCommertial(model_str, brand_str);
                    Log.i(" Commertial power***********************************************************************************************  : ", String.valueOf(call.request().url()));
                    call.enqueue(new Callback<List<commertial_power_output>>() {
                        @Override
                        public void onResponse(Call<List<commertial_power_output>> call, Response<List<commertial_power_output>> response) {
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
                        public void onFailure(Call<List<commertial_power_output>> call, Throwable t) {
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

                    spinner_amphere.clear();
                    spinner_amphere.add("Amphere");
                    Call<List<commertial_charger_amphere>> call7 = service.getChargerAmphereCom(brand_str ,model_str);
                    Log.i("Amphere***********************************************************************************************  : ", String.valueOf(call6.request().url()));
                    call7.enqueue(new Callback<List<commertial_charger_amphere>>() {
                        @Override
                        public void onResponse(Call<List<commertial_charger_amphere>> call7, Response<List<commertial_charger_amphere>> response) {
                            if (response.body() != null) {
                                charger_amphereList = response.body();

                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    commertial_charger_amphere amphereItem = charger_amphereList.get(i);
                                    demo_str = amphereItem.getChargerAmphereCom();
                                    spinner_amphere.add(demo_str);
                                    Log.i("Amphere output Array: ", spinner_amphere.toString());
                                }

                            } else {
                                String new_str = "Amphere Output is empty";
                                Log.i("Amphere output: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<commertial_charger_amphere>> call7, Throwable t) {
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

        electricityBoardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = electricityBoardSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Electricity board")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                cs_board = electricityBoardSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });

        rateStructureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = rateStructureSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Rate structure")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                cs_rate = rateStructureSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });

        socketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = socketSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Select socket")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                socket_str = socketSpinner.getSelectedItem().toString();
                if (socket_str.equals("Select socket")) {
                    socketVoltageSpinner.setVisibility(View.GONE);
                    socketAmphereSpinner.setVisibility(View.GONE);
                    socketPowerOutputEditText.setVisibility(View.GONE);
                } else {
                    String socket_str = socketSpinner.getSelectedItem().toString();
                    String state_str = state_text.getText().toString();
                    socket_voltage.clear();
                    socket_voltage.add("socket voltage");
                    socket_amphereList.clear();
                    socket_amphereList.add("socket Amphere");
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


                    Call<List<commertial_socket_amphere>> call1 = service.getSocketAmphereCom(socket_str);
                    Log.i("Socket Commertial Voltage***********************************************************************************************  : ", String.valueOf(call.request().url()));
                    call1.enqueue(new Callback<List<commertial_socket_amphere>>() {
                        @Override
                        public void onResponse(Call<List<commertial_socket_amphere>> call, Response<List<commertial_socket_amphere>> response) {
                            if (response.body() != null) {
                                socket_amphere = response.body();
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    commertial_socket_amphere amphereItem = socket_amphere.get(i);
                                    demo_str = amphereItem.getSocketAmphereCom();
                                    socket_amphereList.add(demo_str);
                                    Log.i("Socket Commertial Voltage: ", socket_amphereList.toString());
                                }
                            } else {
                                String new_str = "Socket Commertial Voltage is empty";
                                Log.i("Socket Commertial Voltage :", new_str);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<commertial_socket_amphere>> call, Throwable t) {
                            Log.i(" Socket Commertial Voltage: ", String.valueOf(t));
                        }
                    });



                    ArrayAdapter<String> adapter63 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_voltage);
                    socketVoltageSpinner.setAdapter(adapter63);
                    socketVoltageSpinner.setVisibility(View.VISIBLE);

                    ArrayAdapter<String> adapter64 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_amphereList);
                    socketAmphereSpinner.setAdapter(adapter64);
                    socketAmphereSpinner.setVisibility(View.VISIBLE);
                    socketPowerOutputEditText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });

        socketElectricityBoardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = socketElectricityBoardSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Select board")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                socket_board_str = socketElectricityBoardSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Override method
            }
        });

        socketRateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = socketRateSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("select socket rate")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                socket_rate_str = socketRateSpinner.getSelectedItem().toString();
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

        socketVoltageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String socketAmphereValue = socketAmphereSpinner.getSelectedItem().toString();
                String socketVoltageValue = socketVoltageSpinner.getSelectedItem().toString();
                if(socketVoltageValue.equalsIgnoreCase("socket voltage")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                socket_vtg = socketVoltageSpinner.getSelectedItem().toString();

                float voltageValue = 0;
                float amphereValue = 0;
                if(!TextUtils.isEmpty(socketVoltageValue) && !socketVoltageValue.equalsIgnoreCase("socket voltage")
                        && !TextUtils.isEmpty(socketAmphereValue) && !socketAmphereValue.equalsIgnoreCase("socket Amphere")){
                    try{
                        voltageValue = Float.parseFloat(socketVoltageValue);
                        amphereValue = Float.parseFloat(socketAmphereValue);
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

        socketAmphereSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String socketAmphereValue = socketAmphereSpinner.getSelectedItem().toString();
                String socketVoltageValue = socketVoltageSpinner.getSelectedItem().toString();
                if(socketAmphereValue.equalsIgnoreCase("socket Amphere")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                float voltageValue = 0;
                float amphereValue = 0;
                if(!TextUtils.isEmpty(socketVoltageValue) && !socketVoltageValue.equalsIgnoreCase("socket voltage")
                        && !TextUtils.isEmpty(socketAmphereValue) && !socketAmphereValue.equalsIgnoreCase("socket Amphere")){
                    try{
                        voltageValue = Float.parseFloat(socketVoltageValue);
                        amphereValue = Float.parseFloat(socketAmphereValue);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                setFinalPowerOutput(voltageValue, amphereValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
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
                String amphereSpinnerValue = amphereSpinner.getSelectedItem().toString();
                String voltageSpinnerValue = voltageSpinner.getSelectedItem().toString();
                if(voltageSpinnerValue.equalsIgnoreCase("Voltage")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                float voltageValue = 0;
                float amphereValue = 0;
                if(!TextUtils.isEmpty(voltageSpinnerValue) && !voltageSpinnerValue.equalsIgnoreCase("Voltage")
                        && !TextUtils.isEmpty(amphereSpinnerValue) && !amphereSpinnerValue.equalsIgnoreCase("Amphere")){
                    try{
                        voltageValue = Float.parseFloat(voltageSpinnerValue);
                        amphereValue = Float.parseFloat(amphereSpinnerValue);
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
                String amphereSpinnerValue = amphereSpinner.getSelectedItem().toString();
                String voltageSpinnerValue = voltageSpinner.getSelectedItem().toString();
                if(amphereSpinnerValue.equalsIgnoreCase("Amphere")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                float voltageValue = 0;
                float amphereValue = 0;
                if(!TextUtils.isEmpty(voltageSpinnerValue) && !voltageSpinnerValue.equalsIgnoreCase("Voltage")
                        && !TextUtils.isEmpty(amphereSpinnerValue) && !amphereSpinnerValue.equalsIgnoreCase("Amphere")){
                    try{
                        voltageValue = Float.parseFloat(voltageSpinnerValue);
                        amphereValue = Float.parseFloat(amphereSpinnerValue);
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
        spinnerArrayList.add(chargerConnectorSpinner); //"Connector Type"
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



        Button nextButton = (Button) rootView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RequiredFieldUtil.isRequiredFieldEmpty(editTextArrayList)){
                    Toast.makeText(getContext(), "The required Field can not be blank", Toast.LENGTH_LONG).show();
                    return;
                }

                if(RequiredFieldUtil.isRequiredSpinner(spinnerArrayDefaultValueList, spinnerArrayList)){
                    Toast.makeText(getContext(), "The required drop down field can not be blank", Toast.LENGTH_LONG).show();
                    return;
                }

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
                    editor.putString("power_com", cs_power);
                    editor.putString("Voltage_com", cs_vtg);
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
                    editor.putString("Socket_C_rate", socket_rate_str);

                    if (charger_type_str.equals("Charging Station")) {
                        editor.putString("Rate", cs_rate);
                        editor.putString("electricity_board", cs_board);
                        editor.putString("voltage", voltageSpinner.getSelectedItem().toString());
                        editor.putString("amphere", amphereSpinner.getSelectedItem().toString());
                    } else if (charger_type_str.equals("Standard Domestic Socket")) {
                        editor.putString("Rate", socket_rate_str);
                        editor.putString("electricity_board", socket_board_str);
                        editor.putString("voltage", socketVoltageSpinner.getSelectedItem().toString());
                        editor.putString("amphere", socketAmphereSpinner.getSelectedItem().toString());
                    }
                    editor.putString("final_poweroutput", mFinalPowerOutputTextView.getText().toString());

                    editor.apply();
                    TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.simpleTabLayout10);
                    tabhost.getTabAt(1).select();
                }
            }
        });

        return rootView;
    }

    public static AddAnotherCommercialFragment newInstance() {
        AddAnotherCommercialFragment f = new AddAnotherCommercialFragment();
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
//                SharedPreferences.Editor editor = prefs.edit();
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
