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
import android.widget.ListView;
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

import commertial_pojo.commertial_socket_amphere;
import datamodel.APIInterface;
import datamodel.Socket_public;
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
import public_pojo.public_charger_amphere;
import public_pojo.public_socket_amphere;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.elshare.utils.ElshareConstants.GOOGLE_MAP_API_KEY;

public class add_another_public_g extends Fragment implements AdapterView.OnItemClickListener {
    private GooglePlacesAutocompleteAdapter mGooglePlacesAutocompleteAdapter;
    private AutoCompleteTextView mAddressAutoCompleteTextView;

    private ViewPager viewPager;
    ListView lv;
    private EditText edittext_1,edittext_2,state_text,city_text,country_text,landmark_text,zip_txt,addr_1,addr_2;
     private String charger_t,br_t,model_t,connect_t,power_t,rate_t,vtg_t,board_t,socekt_public_str,socket_vtg_str,socket_board_str,socket_rate_str;
    private static final int RESULT_OK = -1;
    private static final int RESULT_CANCELED = -1;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private double longitude,latitude;

    private List<public_charegr_brand> charger_brand;
    private List<public_charger_model> charger_model;
    private List<public_connector> connector_type;
    private List<public_rate> rate_type;
    private List<Socket_public> socket_type;
    private List<public_voltage_socket> socket_volt;
    private List<public_socket_amphere> socket_amphere;
    private List<public_rate_socket> socket_rate;
    private List<public_board_socket> socket_board;
    private List<public_board> board_com;
    private List<public_power_output> power_com;
    private List<public_voltage> volt_com;
    private List<public_charger_amphere> charger_amphereList;


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

    private public_charegr_brand charger_b;

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


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_another_activity_public_g, container, false);
        String[] array1  = {"Charger Type","Charging Station", "Standard Domestic Socket"};
        String[] array2  = {"Charger Brand","Delta Electronics" ,"Mass-Tech", "Sema Connect","Magenta Power","Amplify Mobility","Energy Efficiency Services Limited (EESL)","EV Motors India (P) Ltd – PlugNgo"," TecSo ChargeZone (P) Ltd."," Volttic","BrightBlu","EVTeQ","EVI Technologies ","Ather Energy","eChargeBays","Kirana Charzer","EO Charging","ZEVPoint","ABB India","Fortum ","Evolt","Okaya Power Group","Ensto","Powerlogixtech","Exicom","Others( if any) please specify"};
        String[] array38 = {"Charger Model","110", "220", "420"};
        String[] array39= {"Rate","110", "220", "420"};
        String[] array40 = {"Power Output ","XYZ", "ABC"};

        edittext_1=rootView.findViewById(R.id.editText60);
        edittext_2=rootView.findViewById(R.id.editText62);
        country_text=rootView.findViewById(R.id.editText57);
        state_text=rootView.findViewById(R.id.editText58);
        city_text=rootView.findViewById(R.id.editText59);
        landmark_text=rootView.findViewById(R.id.editText63);
        zip_txt=rootView.findViewById(R.id.editText85);
        addr_1=rootView.findViewById(R.id.editText60);
        addr_2=rootView.findViewById(R.id.editText62);

        Places.initialize(getContext(),"AIzaSyDNdANqUDsLyz6s3mtEJuEQBNaW4xVpK8k");
        //edittext_1.setFocusable(false);
        edittext_1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG,Place.Field.ADDRESS_COMPONENTS);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields).setCountry("IN") //NIGERIA
                        .build(Objects.requireNonNull(getContext()));
               // startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

            }
        });

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

        final ArrayList<String> spinner_charger_brand=new ArrayList<String>();
        final ArrayList<String> spinner_charger_model=new ArrayList<String>();
        final ArrayList<String> spinner_connector=new ArrayList<String>();
        final ArrayList<String> spinner_electricity_board=new ArrayList<String>();
        final ArrayList<String> spinner_power_output=new ArrayList<String>();
        final ArrayList<String> spinner_rate_structure=new ArrayList<String>();
        final ArrayList<String> spinner_voltage=new ArrayList<String>();
        final ArrayList<String> spinner_amphere = new ArrayList<>();

        final ArrayList<String> socket_board_array=new ArrayList<String>();
        final ArrayList<String> socket_rate_array=new ArrayList<String>();
        final ArrayList<String> socket_power=new ArrayList<String>();
        final ArrayList<String> socket_voltage=new ArrayList<String>();
        final ArrayList<String> socket_amphereList = new ArrayList<>();

        final ArrayList<String> socket_list_array=new ArrayList<String>();
        chargerBrandSpinner.setVisibility(View.GONE);
        chargerModelSpinner.setVisibility(View.GONE);
        electricityBoardSpinner.setVisibility(View.GONE);
        rateStructureSpinner.setVisibility(View.GONE);
        chargerConnectorSpinner.setVisibility(View.GONE);
        powerOutputSpinner.setVisibility(View.GONE);
        voltageSpinner.setVisibility(View.GONE);
        amphereSpinner.setVisibility(View.GONE);

        socketSpinner.setVisibility(View.GONE);
        socketRateSpinner.setVisibility(View.GONE);
        socketElectricityBoardSpinner.setVisibility(View.GONE);
        socketVoltageSpinner.setVisibility(View.GONE);
        socketAmphereSpinner.setVisibility(View.GONE);
        socketPowerOutputEditText.setVisibility(View.GONE);

        mAddressAutoCompleteTextView = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
        mGooglePlacesAutocompleteAdapter = new GooglePlacesAutocompleteAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item);
        mAddressAutoCompleteTextView.setAdapter(mGooglePlacesAutocompleteAdapter);
        mAddressAutoCompleteTextView.setOnItemClickListener(this);

        chargerTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedValue = chargerTypeSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Charger Type")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                String type_str=chargerTypeSpinner.getSelectedItem().toString();

                if (type_str.equalsIgnoreCase("Charger Type")) {
                    socketSpinner.setVisibility(View.GONE);
                    socketRateSpinner.setVisibility(View.GONE);
                    socketElectricityBoardSpinner.setVisibility(View.GONE);
                    socketVoltageSpinner.setVisibility(View.GONE);
                    socketAmphereSpinner.setVisibility(View.GONE);
                    socketPowerOutputEditText.setVisibility(View.GONE);
                    chargerBrandSpinner.setVisibility(View.GONE);
                    chargerModelSpinner.setVisibility(View.GONE);
                    electricityBoardSpinner.setVisibility(View.GONE);
                    rateStructureSpinner.setVisibility(View.GONE);
                    chargerConnectorSpinner.setVisibility(View.GONE);
                    powerOutputSpinner.setVisibility(View.GONE);
                    voltageSpinner.setVisibility(View.GONE);
                    amphereSpinner.setVisibility(View.GONE);
                    charger_t = chargerTypeSpinner.getSelectedItem().toString();
                    mFinalPowerOutputTextView.setVisibility(View.GONE);
                }


                String state_str2=state_text.getText().toString();
                if (state_str2.isEmpty())
                {
                    Toast.makeText(getContext(), "Select state field", Toast.LENGTH_SHORT).show();
                    socketSpinner.setVisibility(View.GONE);
                    socketRateSpinner.setVisibility(View.GONE);
                    socketElectricityBoardSpinner.setVisibility(View.GONE);
                    socketVoltageSpinner.setVisibility(View.GONE);
                    socketAmphereSpinner.setVisibility(View.GONE);
                    socketPowerOutputEditText.setVisibility(View.GONE);
                    chargerBrandSpinner.setVisibility(View.GONE);
                    chargerModelSpinner.setVisibility(View.GONE);
                    electricityBoardSpinner.setVisibility(View.GONE);
                    rateStructureSpinner.setVisibility(View.GONE);
                    chargerConnectorSpinner.setVisibility(View.GONE);
                    powerOutputSpinner.setVisibility(View.GONE);
                    voltageSpinner.setVisibility(View.GONE);
                    amphereSpinner.setVisibility(View.GONE);
                    mFinalPowerOutputTextView.setVisibility(View.GONE);
                }
                else {
                    if (type_str.equals("Charging Station")) {
                        mFinalPowerOutputTextView.setVisibility(View.GONE);
                        socketSpinner.setVisibility(View.GONE);
                        socketRateSpinner.setVisibility(View.GONE);
                        socketElectricityBoardSpinner.setVisibility(View.GONE);
                        socketVoltageSpinner.setVisibility(View.GONE);
                        socketAmphereSpinner.setVisibility(View.GONE);
                        socketPowerOutputEditText.setVisibility(View.GONE);
                        chargerBrandSpinner.setVisibility(View.VISIBLE);
                        chargerModelSpinner.setVisibility(View.GONE);
                        electricityBoardSpinner.setVisibility(View.GONE);
                        rateStructureSpinner.setVisibility(View.GONE);
                        chargerConnectorSpinner.setVisibility(View.GONE);
                        powerOutputSpinner.setVisibility(View.GONE);
                        voltageSpinner.setVisibility(View.GONE);
                        amphereSpinner.setVisibility(View.GONE);
                        charger_t=chargerTypeSpinner.getSelectedItem().toString();


                        spinner_charger_brand.clear();
                        spinner_charger_brand.add("Charger Brand");
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

                        ArrayAdapter<String> adapter_12 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_charger_brand);
                        chargerBrandSpinner.setAdapter(adapter_12);
                        chargerBrandSpinner.setVisibility(View.VISIBLE);

                        spinner_rate_structure.clear();
                        spinner_electricity_board.clear();
                        spinner_rate_structure.add("select socket rate");
                        spinner_electricity_board.add("Select board");
                        APIInterface service2 = SingletonRetrofit.getAPIInterface();

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
                        ArrayAdapter<String> adapter40 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_rate_structure);
                        rateStructureSpinner.setAdapter(adapter40);
                        rateStructureSpinner.setVisibility(View.VISIBLE);

                        APIInterface service3 = SingletonRetrofit.getAPIInterface();
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
                        ArrayAdapter<String> adapter39= new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_electricity_board);
                        electricityBoardSpinner.setAdapter(adapter39);
                        electricityBoardSpinner.setVisibility(View.VISIBLE);




                    }  else if (type_str.equals("Standard Domestic Socket")) {
                        mFinalPowerOutputTextView.setVisibility(View.GONE);
                        socketSpinner.setVisibility(View.VISIBLE);
                        socketRateSpinner.setVisibility(View.VISIBLE);
                        socketElectricityBoardSpinner.setVisibility(View.VISIBLE);
                        socketVoltageSpinner.setVisibility(View.VISIBLE);
                        socketAmphereSpinner.setVisibility(View.VISIBLE);
                        socketPowerOutputEditText.setVisibility(View.VISIBLE);
                        chargerBrandSpinner.setVisibility(View.GONE);
                        chargerModelSpinner.setVisibility(View.GONE);
                        electricityBoardSpinner.setVisibility(View.GONE);
                        rateStructureSpinner.setVisibility(View.GONE);
                        chargerConnectorSpinner.setVisibility(View.GONE);
                        powerOutputSpinner.setVisibility(View.GONE);
                        voltageSpinner.setVisibility(View.GONE);
                        amphereSpinner.setVisibility(View.GONE);

                        charger_t=chargerTypeSpinner.getSelectedItem().toString();

                        socket_list_array.clear();
                        socket_rate_array.clear();
                        socket_board_array.clear();
                        socket_list_array.add("Select socket");
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
                        ArrayAdapter<String> adapter_73 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_list_array);
                        socketSpinner.setAdapter(adapter_73);
                        socketSpinner.setVisibility(View.VISIBLE);


                        socket_rate_array.clear();
                        socket_rate_array.add("select socket rate");
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

                        ArrayAdapter<String> adapter74 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_rate_array);
                        socketRateSpinner.setAdapter(adapter74);
                        socketRateSpinner.setVisibility(View.VISIBLE);


                        socket_board_array.clear();
                        socket_board_array.add("Select board");
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

                        ArrayAdapter<String> adapter75 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_board_array);
                        socketElectricityBoardSpinner.setAdapter(adapter75);
                        socketElectricityBoardSpinner.setVisibility(View.VISIBLE);



                    }
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter_11 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, array1);
        chargerTypeSpinner.setAdapter(adapter_11);


        chargerBrandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedValue = chargerBrandSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Charger Brand")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                br_t=chargerBrandSpinner.getSelectedItem().toString() ;
                String type_brand_str=chargerBrandSpinner.getSelectedItem().toString();
                if(br_t.equals("Charger Brand"))
                {
                    chargerModelSpinner.setVisibility(View.GONE);

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
                    ArrayAdapter<String> adapter38 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_charger_model);
                    chargerModelSpinner.setAdapter(adapter38);
                    chargerModelSpinner.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        chargerModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedValue = chargerModelSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Charger Model")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                model_t=chargerModelSpinner.getSelectedItem().toString();
                if (model_t.equals("Charger Model"))
                {
                    chargerConnectorSpinner.setVisibility(View.GONE);
                }
                else
                {
                String state_str = state_text.getText().toString();
                String model_str = chargerModelSpinner.getSelectedItem().toString();
                String brand_str = chargerBrandSpinner.getSelectedItem().toString();
                if (state_text==null&& model_str==null&&brand_str==null&&model_str.equals("Charger Model")&&brand_str.equals("Charger Brand"))
                {
                    String not_str="Enter all required field";
                    Log.i("Connector Public*****",not_str);
                }
                else {
//                    String state_str = state_text.getText().toString();
//                    String model_str = chargerModelSpinner.getSelectedItem().toString();
//                    String brand_str = chargerBrandSpinner.getSelectedItem().toString();

                    spinner_connector.clear();
                    spinner_connector.add("Connector Type");

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

                    ArrayAdapter<String> adapter70 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_connector);
                    chargerConnectorSpinner.setAdapter(adapter70);
                    chargerConnectorSpinner.setVisibility(View.VISIBLE);
                }

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        electricityBoardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedValue = electricityBoardSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Select board")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                board_t=electricityBoardSpinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rateStructureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedValue = rateStructureSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("select socket rate")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                rate_t=rateStructureSpinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        powerOutputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                power_t = powerOutputSpinner.getSelectedItem().toString();
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

            }
        });

        voltageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                vtg_t = voltageSpinner.getSelectedItem().toString();
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
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                socket_vtg_str = socketVoltageSpinner.getSelectedItem().toString();
                String socketAmphereValue = socketAmphereSpinner.getSelectedItem().toString();
                String socketVoltageValue = socketVoltageSpinner.getSelectedItem().toString();
                if(socketVoltageValue.equalsIgnoreCase("socket voltage")){
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
            public void onNothingSelected(AdapterView<?> parent) {

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


        socketRateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedValue = socketRateSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("select socket rate")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                socket_rate_str=socketRateSpinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        socketElectricityBoardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedValue = socketElectricityBoardSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Select board")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                socket_board_str=socketElectricityBoardSpinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        socketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedValue = socketSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Select socket")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                String socket_str=socketSpinner.getSelectedItem().toString();
                if (socket_str.equals("Socket")) {
                    socketVoltageSpinner.setVisibility(View.GONE);
                    socketAmphereSpinner.setVisibility(View.GONE);
                    socketPowerOutputEditText.setVisibility(View.GONE);
                }
                else {
                    String state_str = state_text.getText().toString();
                    socket_str = socketSpinner.getSelectedItem().toString();
                    socket_voltage.clear();
                    socket_voltage.add("socket voltage");
                    socket_amphereList.clear();
                    socket_amphereList.add("socket Amphere");

                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<public_voltage_socket>> call = service.getVoltageSocketPublic(socket_str);
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


                    Call<List<public_socket_amphere>> call1 = service.getSocketAmpherePublic(socket_str);
                    Log.i("Socket Commertial Voltage***********************************************************************************************  : ", String.valueOf(call.request().url()));
                    call1.enqueue(new Callback<List<public_socket_amphere>>() {
                        @Override
                        public void onResponse(Call<List<public_socket_amphere>> call, Response<List<public_socket_amphere>> response) {
                            if (response.body() != null) {
                                socket_amphere = response.body();
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    public_socket_amphere amphereItem = socket_amphere.get(i);
                                    demo_str = amphereItem.getSocketAmpherePublic();
                                    socket_amphereList.add(demo_str);
                                    Log.i("Socket Commertial Voltage: ", socket_amphereList.toString());
                                }
                            } else {
                                String new_str = "Socket Commertial Voltage is empty";
                                Log.i("Socket Commertial Voltage :", new_str);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<public_socket_amphere>> call, Throwable t) {
                            Log.i(" Socket Commertial Voltage: ", String.valueOf(t));
                        }
                    });


                    ArrayAdapter<String> adapter76 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_voltage);
                    socketVoltageSpinner.setAdapter(adapter76);
                    socketVoltageSpinner.setVisibility(View.VISIBLE);

                    ArrayAdapter<String> adapter77 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_amphereList);
                    socketAmphereSpinner.setAdapter(adapter77);
                    socketAmphereSpinner.setVisibility(View.VISIBLE);
                    socketPowerOutputEditText.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chargerConnectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedValue = chargerConnectorSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Connector Type")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                connect_t=chargerConnectorSpinner.getSelectedItem().toString();
                if (connect_t.equals("Connector Type"))
                {
                    powerOutputSpinner.setVisibility(View.GONE);
                    voltageSpinner.setVisibility(View.GONE);
                    amphereSpinner.setVisibility(View.GONE);
                }
                else
                {
                String state_str = state_text.getText().toString();
                String model_str = chargerModelSpinner.getSelectedItem().toString();
                String brand_str = chargerBrandSpinner.getSelectedItem().toString();
                String connector_str=chargerConnectorSpinner.getSelectedItem().toString();

                    spinner_power_output.clear();
                    spinner_power_output.add("Power output");
                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<public_power_output>> call = service.getPowerOutputPublic(model_str, brand_str);
                    Log.i("Connector Public***********************************************************************************************  : ", String.valueOf(call.request().url()));
                    call.enqueue(new Callback<List<public_power_output>>() {
                        @Override
                        public void onResponse(Call<List<public_power_output>> call, Response<List<public_power_output>> response) {
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
                        public void onFailure(Call<List<public_power_output>> call, Throwable t) {
                            Log.i("Power output error: ", String.valueOf(t));

                        }
                    });


                    spinner_voltage.clear();
                    spinner_voltage.add("Voltage");
                    APIInterface service5 = SingletonRetrofit.getAPIInterface();

                    Call<List<public_voltage>> call6 = service5.getVoltagePublic(model_str, brand_str);
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




                    spinner_amphere.clear();
                    spinner_amphere.add("Amphere");
                    Call<List<public_charger_amphere>> call7 = service.getChargerAmpherePublic(brand_str ,model_str);
                    Log.i("Amphere***********************************************************************************************  : ", String.valueOf(call6.request().url()));
                    call7.enqueue(new Callback<List<public_charger_amphere>>() {
                        @Override
                        public void onResponse(Call<List<public_charger_amphere>> call7, Response<List<public_charger_amphere>> response) {
                            if (response.body() != null) {
                                charger_amphereList = response.body();

                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    public_charger_amphere amphereItem = charger_amphereList.get(i);
                                    demo_str = amphereItem.getChargerAmpherePublic();
                                    spinner_amphere.add(demo_str);
                                    Log.i("Amphere output Array: ", spinner_amphere.toString());
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

            }
        });


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
        spinnerArrayList.add(electricityBoardSpinner); //"Select board"
        spinnerArrayList.add(rateStructureSpinner); //"select socket rate"
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
        spinnerArrayDefaultValueList.add("Select board");
        spinnerArrayDefaultValueList.add("select socket rate");
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

                Log.i("TAG", "Place: " + charger_t + "," + br_t + "," + board_t + "," + model_t + "," + rate_t + vtg_t + power_t + connect_t + state_text.toString() + city_text.toString() + country_text.toString() + addr_1.toString() + addr_2.toString() + zip_txt.toString() + landmark_text.toString() + latitude
                        + longitude);


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = preferences.edit();
                    String host_str = "PUBLIC";
                    editor.putString("HOST", host_str);

//                Bundle bundle = new Bundle();
//                String resi_str_pass = getArguments().getString("Public");
//                bundle.putString("PUBLIC", resi_str_pass);
//                availability_frag av_f = new availability_frag();
//                av_f.setArguments(bundle);

                editor.putString("Charger_type_c", charger_t);
                editor.putString("Charger_brand", br_t);
                editor.putString("Charger_model", model_t);
                editor.putString("Connector", connect_t);
                editor.putString("power", power_t);
                editor.putString("Voltage", vtg_t);
                editor.putString("State_p", state_text.getText().toString());
                editor.putString("Add_1_p", addr_1.getText().toString());
                editor.putString("Add_2_p", addr_2.getText().toString());
                editor.putString("City_p", city_text.getText().toString());
                editor.putString("Country_p", country_text.getText().toString());
                editor.putString("Land_p", landmark_text.getText().toString());
                editor.putString("Zip_p", zip_txt.getText().toString());
                editor.putString("Latitude", String.valueOf(latitude));
                editor.putString("Longitude", String.valueOf(longitude));
                editor.putString("Socket_public", socekt_public_str);
                editor.putString("Socket_vtg_P", socket_vtg_str);
                editor.putString("Socket_P_rate", socket_rate_str);

                    if (charger_t.equals("Charging Station")) {
                        editor.putString("Rate", rate_t);
                        editor.putString("electricity_board", board_t);
                        editor.putString("voltage", voltageSpinner.getSelectedItem().toString());
                        editor.putString("amphere", amphereSpinner.getSelectedItem().toString());
                    } else if (charger_t.equals("Standard Domestic Socket")) {
                        editor.putString("Rate", socket_rate_str);
                        editor.putString("electricity_board", socket_board_str);
                        editor.putString("voltage", socketVoltageSpinner.getSelectedItem().toString());
                        editor.putString("amphere", socketAmphereSpinner.getSelectedItem().toString());
                    }
                    editor.putString("final_poweroutput", mFinalPowerOutputTextView.getText().toString());

                editor.apply();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.simpleFrameLayout2, av_f);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
                TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.simpleTabLayout10);
                tabhost.getTabAt(1).select();

            }
            }
        });
        return rootView;
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
