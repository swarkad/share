package com.example.elshare;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.elshare.utils.SingletonRetrofit;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xw.repo.BubbleSeekBar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import datamodel.APIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAnotherPricingFragment extends Fragment {
    Button smart_p, back_b, cancel_btn, ok_btn, b_back, submit_b;
    Dialog pw;
    ViewPager viewPager;
    SeekBar seekBar;
    String rate;
    TextView profit_value;
    TextView electricityBoardRate;
    float pro_str;
    String host_str;
    private Activity mActivity;
    int pin_int, user_id;
    Double final_profit_double;
    String final_profit;
    String User_str;
    private  String all_mon,all_tue,all_wed,all_thr,all_fri,all_sat,all_sun,all_day_str;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_another_pricing_frag, container, false);
        BubbleSeekBar bubbleSeekBar2 = rootView.findViewById(R.id.demo_3_seek_bar_4);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        profit_value = rootView.findViewById(R.id.profit_text_view);
        submit_b = rootView.findViewById(R.id.submit_button);
        electricityBoardRate = rootView.findViewById(R.id.electricity_board_rate);
        host_str = preferences.getString("HOST", "");
        rate = preferences.getString("Rate", "");
        if (rate == null) {
            Toast.makeText(getContext(), "State rate is empty", Toast.LENGTH_SHORT).show();
        } else {
            electricityBoardRate.setText(rate);
        }

        Log.i("Host of pricing:", host_str);

        bubbleSeekBar2.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                Double rate_integr = new Double(0);
                if(!TextUtils.isEmpty(rate)) {
                    try {
                        rate_integr = Double.valueOf(rate);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                String s = String.format(Locale.CHINA, " %.1f", progressFloat);
                profit_value.setText(s);
                Double bubble_str = Double.valueOf(s);
                //final_profit_double = (double) rate_integr / (double) bubble_str;
                // So 10% of 150 = 10/100 × 150 = 15
                final_profit_double = (bubble_str/100) * rate_integr;
                final_profit = String.format("%.2f", final_profit_double);
                profit_value.setText(final_profit);
                Log.i("Profit is", final_profit);

            }
        });

        smart_p = rootView.findViewById(R.id.smart_pricing_info_button);
        b_back = rootView.findViewById(R.id.back_button);
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.simpleTabLayout10);
                tabhost.getTabAt(1).select();
            }
        });

        back_b = rootView.findViewById(R.id.back_button);
        smart_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw = new Dialog(getActivity());
                pw.setContentView(R.layout.smart_window);
                pw.show();
                cancel_btn = pw.findViewById(R.id.button90);
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                    }
                });
                ok_btn = pw.findViewById(R.id.button91);
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                    }
                });

            }
        });

        submit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                User_str = preferences.getString("Name", "");
                if (User_str.isEmpty())
                {
                    Toast.makeText(getContext(),"Usr id not found .Please log in again",Toast.LENGTH_LONG).show();
                }
                else {
                    Log.i("User ID", User_str);
                    user_id =Integer.parseInt(String.valueOf(User_str));
                    Log.i("User id is:", String.valueOf(user_id));
                }
                Log.i("State Rate is:", rate);

                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                int flag = 0;
                int selected = 0;
                ArrayList<String> selected_day = new ArrayList<String>();
                ArrayList<String> selected_days = new ArrayList<String>();
                ArrayList<String> start_time = new ArrayList<String>();
                ArrayList<String> end_time = new ArrayList<String>();
                ArrayList<String> start_time_mon = new ArrayList<String>();
                ArrayList<String> end_time_mon = new ArrayList<String>();
                ArrayList<String> start_time_tues = new ArrayList<String>();
                ArrayList<String> end_time_tues = new ArrayList<String>();
                ArrayList<String> start_time_wed = new ArrayList<String>();
                ArrayList<String> end_time_wed = new ArrayList<String>();
                ArrayList<String> end_time_thus = new ArrayList<String>();
                ArrayList<String> start_time_thus = new ArrayList<String>();
                ArrayList<String> start_time_fri = new ArrayList<String>();
                ArrayList<String> end_time_fri = new ArrayList<String>();
                ArrayList<String> start_time_sat = new ArrayList<String>();
                ArrayList<String> end_time_sat = new ArrayList<String>();
                ArrayList<String> start_time_sun = new ArrayList<String>();
                ArrayList<String> end_time_sun = new ArrayList<String>();

                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>() {}.getType();
                String json_str = "";

                json_str = preferences.getString("MON_START", null);
                start_time_mon = gson.fromJson(json_str, type);
                json_str = preferences.getString("MON_END", null);
                end_time_mon = gson.fromJson(json_str, type);
                json_str = preferences.getString("TUE_START", null);
                start_time_tues = gson.fromJson(json_str, type);
                json_str = preferences.getString("TUE_END", null);
                end_time_tues = gson.fromJson(json_str, type);
                json_str = preferences.getString("WED_START", null);
                start_time_wed = gson.fromJson(json_str, type);
                json_str = preferences.getString("WED_END", null);
                end_time_wed = gson.fromJson(json_str, type);
                json_str = preferences.getString("THU_START", null);
                start_time_thus = gson.fromJson(json_str, type);
                json_str = preferences.getString("THU_END", null);
                end_time_thus = gson.fromJson(json_str, type);
                json_str = preferences.getString("FRI_START", null);
                start_time_fri = gson.fromJson(json_str, type);
                json_str = preferences.getString("FRI_END", null);
                end_time_fri = gson.fromJson(json_str, type);
                json_str = preferences.getString("SAT_START", null);
                start_time_sat = gson.fromJson(json_str, type);
                json_str = preferences.getString("SAT_END", null);
                end_time_sat = gson.fromJson(json_str, type);
                json_str = preferences.getString("SUN_START", null);
                start_time_sun = gson.fromJson(json_str, type);
                json_str = preferences.getString("SUN_END", null);
                end_time_sun = gson.fromJson(json_str, type);

                json_str = preferences.getString("multiDayStartTimeList", null);
                start_time = gson.fromJson(json_str, type);
                json_str = preferences.getString("multiDayEndTimeList", null);
                end_time = gson.fromJson(json_str, type);


                json_str = preferences.getString("multidaysSelectedList", null);
                selected_day = gson.fromJson(json_str, type);
                json_str = preferences.getString("singleDaySelectedList", null);
                selected_days = gson.fromJson(json_str, type);

                flag=preferences.getInt("SINGLE_MULTI_DAY_SWITCH", 0);
                selected=preferences.getInt("INSTANCE_BOOKING_SWITCH",1);
                
                all_mon=preferences.getString("ALL_MON",null);
                all_tue=preferences.getString("ALL_TUE",null);
                all_wed=preferences.getString("ALL_WED",null);
                all_thr=preferences.getString("ALL_THR",null);
                all_fri=preferences.getString("ALL_FRI",null);
                all_sat=preferences.getString("ALL_SAT",null);
                all_sun=preferences.getString("ALL_SUN",null);
                all_day_str=preferences.getString("ALL_DAY_STR",null);

                

                if (host_str.equals("PUBLIC")) {
                    rate = preferences.getString("Rate", null);
                    String charger_type = preferences.getString("Charger_type_c", null);
                    String charger_brand = preferences.getString("Charger_brand", null);
                    String charger_model = preferences.getString("Charger_model", null);
                    String board = preferences.getString("electricity_board", null);
                    String connector = preferences.getString("Connector", null);
                    String finalPoweroutput = preferences.getString("final_poweroutput", "");
                    String voltage_p = preferences.getString("voltage", "");
                    String amphere_p=preferences.getString("amphere",null);
                    String state = preferences.getString("State_p", "");
                    String add_1 = preferences.getString("Add_1_p", "");
                    String add_2 = preferences.getString("Add_2_p", "");
                    String city = preferences.getString("City_p", "");
                    String country = preferences.getString("Country_p", "");
                    String land = preferences.getString("Land_p", "");
                    String zip = preferences.getString("Zip_p", "");
                    String Lat = preferences.getString("Latitude", "");
                    String Long = preferences.getString("Longitude", "");
                    String socket_public = preferences.getString("Socket_public", "");

                    Log.e("TAG", "Place: " + charger_type + ", " + charger_brand + "," + charger_model + "," + board + connector + finalPoweroutput + voltage_p + state + add_1 + add_2 + city + country + land + zip + Lat + Long);
                    String name = preferences.getString("Name", "");
                    Log.i("Call ID", name);
                    float latitude = Float.parseFloat(Lat);
                    float longitude = Float.parseFloat(Long);

                    String profit_str = profit_value.getText().toString();



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
                    String private_info = "null";
                    String public_info = "null";
                    String[] start_time_all = new String[]{"null"};
                    String[] end_time_all = new String[]{"null"};


                    APIInterface service = SingletonRetrofit.getAPIInterface();
                    Log.i("Call base url", String.valueOf(SingletonRetrofit.getRetrofit().baseUrl()));
                    Log.i("Call converter", String.valueOf(SingletonRetrofit.getRetrofit().converterFactories()));

                    if(!TextUtils.isEmpty(zip)){
                        try {
                            pin_int = Integer.parseInt(zip);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    Call<ResponseBody> call = service.add_public_list2(user_id, city, add_1, add_2, land, state, country, voltage_p,amphere_p, profit_str, rate, pin_int, latitude, longitude, selected, flag, board, charger_type, connector, charger_brand, charger_model, finalPoweroutput, custum_brand, custum_model, socket_public, custom_connector_type_public, custom_voltage_public, custom_power_output_public, selected_day, selected_days,start_time,end_time, start_time_mon, end_time_mon, start_time_tues, end_time_tues, start_time_wed, end_time_wed, start_time_thus, end_time_thus, start_time_fri, end_time_fri, start_time_sat, end_time_sat, start_time_sun, end_time_sun, start_time_public, end_time_public, all_day_str, all_mon, all_tue, all_wed, all_thr, all_fri, all_sat, all_sun,public_info,private_info);
                    Log.i("Call url", String.valueOf(call.request().url()));
                    Log.i("Call", String.valueOf(call.request().body()));

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
                            if (response.isSuccessful()) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getRootView().getContext());
                                alertDialogBuilder.setMessage("Added public listing successful!!");
                                alertDialogBuilder.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {


                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {

                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                ///Future activity Navigate to MY Listing of login home navigation menu not implement at yet.

                            }


                            else
                            {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getRootView().getContext());
                                alertDialogBuilder.setMessage("Sorry failed!!");
                                alertDialogBuilder.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {


                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {

                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.i("Error", String.valueOf(t.getMessage()));
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    });


                }

                if (host_str.equals("COMMERCIAL")) {
                    rate = preferences.getString("Rate", null);
                    String charger_type = preferences.getString("Charger_type_com", null);
                    String charger_brand = preferences.getString("Charger_brand_com", null);
                    String charger_model = preferences.getString("Charger_model_com", null);
                    String board = preferences.getString("electricity_board", null);
                    String connector = preferences.getString("Connector_com", null);
                    String finalPoweroutput = preferences.getString("final_poweroutput", null);
                    String voltage = preferences.getString("voltage", null);
                    String amphere_c = preferences.getString("amphere", null);
                    String state = preferences.getString("State_c", null);
                    String add_1 = preferences.getString("Add_1_c", null);
                    String add_2 = preferences.getString("Add_2_c", null);
                    String city = preferences.getString("City_c", null);
                    String country = preferences.getString("Country_c", null);
                    String land = preferences.getString("Land_c", null);
                    String zip = preferences.getString("Zip_c", null);
                    String Lat = preferences.getString("Latitude_c", null);
                    String Long = preferences.getString("Longitude_c", null);
                    String socket_str=preferences.getString("Socket_commetial",null);
                    Log.e("TAG", "Place: " + charger_type + ", " + charger_brand + "," + charger_model + "," + board + connector + finalPoweroutput + voltage + state + add_1 + add_2 + city + country + land + zip + Lat + Long);
                    String name = preferences.getString("Name", null);
                    Log.i("Call ID", name);
                    float latitude = Float.parseFloat(Lat);
                    float longitude = Float.parseFloat(Long);

                    String profit_str = profit_value.getText().toString();

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

                    Log.i("Host of pricing:", String.valueOf(start_time_mon));


                    String[] start_time_all = new String[]{"null"};
                    String[] end_time_all = new String[]{"null"};



                    APIInterface service = SingletonRetrofit.getAPIInterface();
                    Log.i("Call base url", String.valueOf(SingletonRetrofit.getRetrofit().baseUrl()));
                    Log.i("Call converter", String.valueOf(SingletonRetrofit.getRetrofit().converterFactories()));

                    if(!TextUtils.isEmpty(zip)){
                        try {
                            pin_int = Integer.parseInt(zip);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    Call<ResponseBody> call = service.add_commercial_list2(user_id,add_1,add_2,land,city,state,country,latitude,longitude,pin_int,board,rate,profit_str,selected,charger_type,connector,charger_brand,voltage,amphere_c,flag,charger_model,finalPoweroutput,custum_brand,custum_model,socket_str,custom_connector_type_public,custom_voltage_public,custom_power_output_public,selected_day,selected_days,start_time,end_time,start_time_mon,end_time_mon,start_time_tues,end_time_tues,start_time_wed,end_time_wed,start_time_thus,end_time_thus,start_time_fri,end_time_fri,start_time_sat,end_time_sat,start_time_sun,end_time_sun,start_time_all,end_time_all,all_day_str,all_mon,all_tue,all_wed,all_thr,all_fri,all_sat,all_sun);
                    Log.i("Call url", String.valueOf(call.request().url()));
                    Log.i("Call", String.valueOf(call.request().body()));

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
                            if (response.isSuccessful()) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getRootView().getContext());
                                alertDialogBuilder.setMessage("Added commercial listing successful!!");
                                alertDialogBuilder.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {


                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {

                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                ///Future activity Navigate to MY Listing of login home navigation menu not implement at yet.
                            }
                            else
                            {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getRootView().getContext());
                                alertDialogBuilder.setMessage("Sorry failed!!");
                                alertDialogBuilder.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {


                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {

                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        }


                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.i("Error", String.valueOf(t.getMessage()));
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    });


                }
                if(host_str.equals("RESIDENTIAL"))
                {
                    rate = preferences.getString("Rate", null);
                    String charger_type = preferences.getString("Charger_type_resi", null);
                    String charger_brand = preferences.getString("Charger_brand_resi", null);
                    String charger_model = preferences.getString("Charger_model_resi", null);
                    String board = preferences.getString("electricity_board", null);
                    String connector = preferences.getString("Connector_resi", null);
                    String finalPoweroutput = preferences.getString("final_poweroutput", null);
                    String voltage = preferences.getString("voltage", null);
                    String amphere = preferences.getString("amphere", null);
                    String state = preferences.getString("State_r", null);
                    String add_1 = preferences.getString("Add_1_r", null);
                    String add_2 = preferences.getString("Add_2_r", null);
                    String city = preferences.getString("City_r", null);
                    String country = preferences.getString("Country_r", null);
                    String land = preferences.getString("Land_r", null);
                    String zip = preferences.getString("Zip_r", null);
                    String Lat = preferences.getString("Latitude_r", null);
                    String Long = preferences.getString("Longitude_r", null);
                    String Socket_resi = preferences.getString("Socket_residential", null);


                    Log.e("TAG", "Place: " + charger_type + ", " + charger_brand + "," + charger_model + "," + board + connector + finalPoweroutput + voltage +amphere + state + add_1 + add_2 + city + country + land + zip + Lat + Long);
                    String name = preferences.getString("Name", "");
                    Log.i("Call ID", name);
                    float latitude = Float.parseFloat(Lat);
                    float longitude = Float.parseFloat(Long);


                    String profit_str = profit_value.getText().toString();


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


                    APIInterface service = SingletonRetrofit.getAPIInterface();
                    Log.i("Call base url", String.valueOf(SingletonRetrofit.getRetrofit().baseUrl()));
                    Log.i("Call converter", String.valueOf(SingletonRetrofit.getRetrofit().converterFactories()));

                    if(!TextUtils.isEmpty(zip)){
                        try {
                            pin_int = Integer.parseInt(zip);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    Call<ResponseBody> call = service.add_residential_list2(user_id,add_1,add_2,land,city,state,country,latitude,longitude,pin_int,board,rate,profit_str,selected,charger_type,connector,charger_brand,voltage,amphere,flag,charger_model,finalPoweroutput,custum_brand,custum_model,Socket_resi,custom_connector_type_public,custom_voltage_public,custom_power_output_public,selected_day,selected_days,start_time,end_time,start_time_mon,end_time_mon,start_time_tues,end_time_tues,start_time_wed,end_time_wed,start_time_thus,end_time_thus,start_time_fri,end_time_fri,start_time_sat,end_time_sat,start_time_sun,end_time_sun,start_time_all,end_time_all,all_day_str,all_mon,all_tue,all_wed,all_thr,all_fri,all_sat,all_sun);
                    Log.i("Call url", String.valueOf(call.request().url()));
                    Log.i("Call", String.valueOf(call.request().body()));

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
                            if (response.isSuccessful()) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getRootView().getContext());
                                alertDialogBuilder.setMessage("Added residential listing succesfully!!!");
                                alertDialogBuilder.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {


                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {

                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                ///Future activity Navigate to MY Listing of login home navigation menu not implement at yet.
                            }
                            else
                            {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getRootView().getContext());
                                alertDialogBuilder.setMessage("Sorry failed!!");
                                alertDialogBuilder.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {


                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {

                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        }


                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.i("Error", String.valueOf(t.getMessage()));
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    });


                }
            }
        });

        return rootView;
    }
}
