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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

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
import datamodel.Socket_commertial;
import datamodel.Socket_public;
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

public class public_g extends Fragment
{

    private ViewPager viewPager;
    ListView lv;
    private EditText edittext_1,edittext_2,state_text,city_text,country_text,landmark_text,zip_txt,addr_1,addr_2;
     private String charger_t,br_t,model_t,connect_t,power_t,rate_t,vtg_t,amp_str,board_t,socekt_public_str,socket_vtg_str,socket_amp_str,socket_board_str,socket_rate_str;
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
    private List<public_rate_socket> socket_rate;
    private List<public_board_socket> socket_board;
    private List<public_board> board_com;
    private List<public_power_output> power_com;
    private List<public_voltage> volt_com;
    private List<public_charger_amphere> charger_amp_array;
    private List<public_socket_amphere> socket_amp_array;


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


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_public_g, container, false);
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
        edittext_1.setFocusable(false);
        edittext_1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG,Place.Field.ADDRESS_COMPONENTS);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields).setCountry("IN") //NIGERIA
                        .build(Objects.requireNonNull(getContext()));
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


        final Spinner spinner_11= rootView.findViewById(R.id.spinner11);//charger type
        final Spinner spinner_12= rootView.findViewById(R.id.spinner12);//charger brand
        final Spinner spinner_38= rootView.findViewById(R.id.spinner38);//charger model
        final Spinner spinner_39= rootView.findViewById(R.id.spinner39);//cs_board
        final Spinner spinner_40= rootView.findViewById(R.id.spinner40);//charger rate
        final Spinner spinner_70= rootView.findViewById(R.id.spinner70);//connector
        final Spinner spinner_71= rootView.findViewById(R.id.spinner71);//charger power
        final Spinner spinner_72= rootView.findViewById(R.id.spinner72);//chareger vtg
        final Spinner spinner_73= rootView.findViewById(R.id.spinner73);//socket list
        final Spinner spinner_74= rootView.findViewById(R.id.spinner74);//socket rate
        final Spinner spinner_75= rootView.findViewById(R.id.spinner75);//socket board
        final Spinner spinner_76= rootView.findViewById(R.id.spinner76);//socket vtg
        final Spinner spinner_77= rootView.findViewById(R.id.spinner77);
        final Spinner charger_A= rootView.findViewById(R.id.charger_Public_A);
        final Spinner socket_A= rootView.findViewById(R.id.socket_A_public);

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
        spinner_77.setVisibility(View.GONE);
        charger_A.setVisibility(View.GONE);
        socket_A.setVisibility(View.GONE);
        spinner_11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String type_str=spinner_11.getSelectedItem().toString();
                String state_str2=state_text.getText().toString();
                if (state_str2.isEmpty())
                {
                    Toast.makeText(getContext(), "All field are mandatory", Toast.LENGTH_SHORT).show();
                    spinner_73.setVisibility(View.GONE);
                    spinner_74.setVisibility(View.GONE);
                    spinner_75.setVisibility(View.GONE);
                    spinner_76.setVisibility(View.GONE);
                    spinner_77.setVisibility(View.GONE);
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
                        spinner_77.setVisibility(View.GONE);
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

                        ArrayAdapter<String> adapter_12 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_charger_brand);
                        spinner_12.setAdapter(adapter_12);
                        spinner_12.setVisibility(View.VISIBLE);

                        spinner_rate_structure.clear();
                        spinner_electricity_board.clear();
                        spinner_rate_structure.add("Rate structure");
                        spinner_electricity_board.add("Electricity board");
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
                        spinner_40.setAdapter(adapter40);
                        spinner_40.setVisibility(View.VISIBLE);

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
                        spinner_39.setAdapter(adapter39);
                        spinner_39.setVisibility(View.VISIBLE);




                    } else if (type_str.equals("Charger Type"))
                    {
                        spinner_73.setVisibility(View.GONE);
                        spinner_74.setVisibility(View.GONE);
                        spinner_75.setVisibility(View.GONE);
                        spinner_76.setVisibility(View.GONE);
                        spinner_77.setVisibility(View.GONE);
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


                    } else if (type_str.equals("Standard Domestic Socket")) {
                        spinner_73.setVisibility(View.VISIBLE);
                        spinner_74.setVisibility(View.VISIBLE);
                        spinner_75.setVisibility(View.VISIBLE);
                        spinner_76.setVisibility(View.VISIBLE);

                        spinner_77.setVisibility(View.GONE);
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
                        ArrayAdapter<String> adapter_73 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_list_array);
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

                        ArrayAdapter<String> adapter74 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_rate_array);
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

                        ArrayAdapter<String> adapter75 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_board_array);
                        spinner_75.setAdapter(adapter75);
                        spinner_75.setVisibility(View.VISIBLE);



                    }
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter_11 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, array1);
        spinner_11.setAdapter(adapter_11);
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
                    ArrayAdapter<String> adapter38 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_charger_model);
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

                    ArrayAdapter<String> adapter70 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_connector);
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
                        ArrayAdapter<String> adapter71 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_power_output);
                        spinner_71.setAdapter(adapter71);
                        spinner_71.setVisibility(View.VISIBLE);
                        spinner_72.setVisibility(View.GONE);
                        charger_A.setVisibility(View.GONE);


                    }
                    else {
                        ArrayAdapter<String> adapter72 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_voltage);
                        spinner_72.setAdapter(adapter72);
                        ArrayAdapter<String> adapter_C_A = new ArrayAdapter<>(getActivity(), R.layout.sample_text, charger_Amphere);
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

//                    Call<List<public_voltage_socket>> call = service.getVoltageSocketPublic();
//                    Log.i("Socket Public Voltage***********************************************************************************************  : ", String.valueOf(call.request().url()));
//                    call.enqueue(new Callback<List<public_voltage_socket>>() {
//                        @SuppressLint("LongLogTag")
//                        @Override
//                        public void onResponse(Call<List<public_voltage_socket>> call, Response<List<public_voltage_socket>> response) {
//                            if (response.body() != null) {
//                                socket_volt = response.body();
//
//                                ArrayList make_array = (ArrayList) response.body();
//
//
//                                String demo_str = null;
//                                int i;
//                                for (i = 0; i < make_array.size(); i++) {
//                                    com_voltage = socket_volt.get(i);
//                                    demo_str = com_voltage.getVoltageSocketPublic();
//                                    socket_voltage.add(demo_str);
//                                    Log.i("Socket Public Voltage: ", socket_voltage.toString());
//                                }
//
//                            } else {
//                                String new_str = "Socket Public Voltage is empty";
//                                Log.i("Socket Public Voltage :", new_str);
//
//                            }
//                        }
//
//
//                        @SuppressLint("LongLogTag")
//                        @Override
//                        public void onFailure(Call<List<public_voltage_socket>> call, Throwable t) {
//                            Log.i(" Socket Public Voltage: ", String.valueOf(t));
//
//                        }
//                    });
//                    ArrayAdapter<String> adapter76 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_voltage);
//                    spinner_76.setAdapter(adapter76);
//                    spinner_76.setVisibility(View.VISIBLE);

//                    socket_Amphere.clear();
//                    socket_Amphere.add("Amphere");
//                    Call<List<public_socket_amphere>> call2 = service.getSocketAmpherePublic(socket_str);
//                    Log.i("Socket Public Amphere***********************************************************************************************  : ", String.valueOf(call2.request().url()));
//                    call2.enqueue(new Callback<List<public_socket_amphere>>() {
//                        @SuppressLint("LongLogTag")
//                        @Override
//                        public void onResponse(Call<List<public_socket_amphere>> call2, Response<List<public_socket_amphere>> response) {
//                            if (response.body() != null) {
//                                socket_amp_array = response.body();
//
//                                ArrayList make_array = (ArrayList) response.body();
//
//
//                                String demo_str = null;
//                                int i;
//                                for (i = 0; i < make_array.size(); i++) {
//                                    socket_amp = socket_amp_array.get(i);
//                                    demo_str = socket_amp.getSocketAmpherePublic();
//                                    socket_Amphere.add(demo_str);
//                                    Log.i("Socket Public Amphere: ", socket_Amphere.toString());
//                                }
//
//                            } else {
//                                String new_str = "Socket Public Amphere is empty";
//                                Log.i("Socket Public Amphere :", new_str);
//
//                            }
//                        }
//
//
//                        @SuppressLint("LongLogTag")
//                        @Override
//                        public void onFailure(Call<List<public_socket_amphere>> call2, Throwable t) {
//                            Log.i(" Socket Public Amphere: ", String.valueOf(t));
//
//                        }
//                    });
//                    ArrayAdapter<String> adapter_S_A = new ArrayAdapter<>(getActivity(), R.layout.sample_text, socket_Amphere);
//                    socket_A.setAdapter(adapter_S_A);
//                    socket_A.setVisibility(View.VISIBLE);
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






        Button btn = (Button) rootView.findViewById(R.id.button88);
        btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
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

                editor.putString("Charger_type_c", charger_t);
                editor.putString("Charger_brand", br_t);
                editor.putString("Charger_model", model_t);
                editor.putString("board", board_t);
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
                editor.putString("Socket_P_board", socket_board_str);
                editor.putString("Socket_P_rate", socket_rate_str);
                    if(charger_t.equals("Charging Station"))
                    {
                        editor.putString("Rate", rate_t);
                    }
                    else if (charger_t.equals("Standard Domestic Socket"))
                    {
                        editor.putString("Rate", socket_rate_str);
                    }
                    else
                    {

                    }
                editor.apply();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.simpleFrameLayout2, av_f);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
                TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.simpleTabLayout2);
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



}
