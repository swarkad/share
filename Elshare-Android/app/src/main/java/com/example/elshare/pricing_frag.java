package com.example.elshare;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.provider.ContactsContract.Data;



import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.elshare.utils.SingletonRetrofit;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xw.repo.BubbleSeekBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.xml.namespace.QName;

import datamodel.APIInterface;
import datamodel.driver;
import datamodel.residential_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

public class pricing_frag extends Fragment
{
    Button smart_p,back_b,cancel_btn,ok_btn,b_back,submit_b;
    Dialog pw;
    ViewPager viewPager;
    SeekBar seekBar;
    String rate;
    EditText profit_value,state_edittext;
    float pro_str;
    private  String all_mon,all_tue,all_wed,all_thr,all_fri,all_sat,all_sun,all_day_str;

    String host_str;
    private Activity mActivity;
    int pin_int,user_id;
//    Double rate_double;
    Double final_profit_double;
    String final_profit;
    String User_str;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pricing_frag, container, false);
         BubbleSeekBar bubbleSeekBar2 = rootView.findViewById(R.id.demo_3_seek_bar_4);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        profit_value=rootView.findViewById(R.id.editText24);
        submit_b=rootView.findViewById(R.id.button124);
        state_edittext=rootView.findViewById(R.id.editText87);
        host_str = preferences.getString("HOST", "");
        rate = preferences.getString("Rate", "");
        if (rate==null)
        {
            Toast.makeText(getContext(), "State rate is empty", Toast.LENGTH_SHORT).show();
        }
        else {
         state_edittext.setText(rate);
        }


        final Bundle b=this.getArguments();
//        host_str= b.getString("final");
//        assert host_str != null;
//        if (host_str.isEmpty())
//        {
//            Toast.makeText(getContext(), "Host not selected ", Toast.LENGTH_SHORT).show();
//        }
//        else {
            Log.i("Host of pricing:", host_str);
//
//        }

        bubbleSeekBar2.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                    Double rate_integr = new Double(0) ;
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
                    final_profit_double = (double) rate_integr / (double) bubble_str;
                    final_profit = String.valueOf(final_profit_double);
                    profit_value.setText(final_profit);
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
        smart_p=rootView.findViewById(R.id.button89);

        b_back=rootView.findViewById(R.id.button92);
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.simpleTabLayout2);
                tabhost.getTabAt(1).select();

            }
        });

        back_b=rootView.findViewById(R.id.button92);
       // viewPager = (ViewPager) getActivity(). findViewById(R.id.fragment_container);
        smart_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  pw = new Dialog(getActivity());
                pw.setContentView(R.layout.smart_window);
                pw.show();
                cancel_btn=pw.findViewById(R.id.button90);
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                    }
                });
                ok_btn=pw.findViewById(R.id.button91);
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                    }
                });

            }
        });
//        final String finalHost_str = host_str;
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
                ArrayList<String> json = new ArrayList<String>();  //For all day
                ArrayList<String> json_start = new ArrayList<String>();////For all day start
                ArrayList<String> json_end = new ArrayList<String>();///For all day end
                flag=preferences.getInt("FLAG", 0);
                selected=preferences.getInt("BOOKING",1);
                all_mon=preferences.getString("ALL_MON",null);
                all_tue=preferences.getString("ALL_TUE",null);
                all_wed=preferences.getString("ALL_WED",null);
                all_thr=preferences.getString("ALL_THR",null);
                all_fri=preferences.getString("ALL_FRI",null);
                all_sat=preferences.getString("ALL_SAT",null);
                all_sun=preferences.getString("ALL_SUN",null);
                all_day_str=preferences.getString("ALL_DAY_STR",null);

                Gson gson = new Gson();
                String json_str = preferences.getString("MON_END", null);
                if (json_str.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    end_time_mon = gson.fromJson(json_str, type);
                }
                String json_str2 = preferences.getString("MON_START", null);
                if (json_str2.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    start_time_mon = gson.fromJson(json_str2, type);
                }
                String json_str3 = preferences.getString("TUE_END", null);
                if (json_str3.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    end_time_tues = gson.fromJson(json_str3, type);
                }

                String json_str4 = preferences.getString("TUE_START", null);
                if (json_str4.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    start_time_tues = gson.fromJson(json_str4, type);
                }

                String json_str5 = preferences.getString("WED_END", null);
                if (json_str5.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    end_time_wed = gson.fromJson(json_str5, type);
                }
                String json_str6 = preferences.getString("WED_START", null);
                if (json_str6.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    start_time_wed = gson.fromJson(json_str6, type);
                }
                String json_str7 = preferences.getString("THU_END", null);
                if (json_str7.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    end_time_thus = gson.fromJson(json_str7, type);
                }

                String json_str8 = preferences.getString("THU_START", null);
                if (json_str8.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    start_time_thus = gson.fromJson(json_str8, type);
                }
                String json_str9 = preferences.getString("FRI_END", null);
                if (json_str9.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    end_time_fri = gson.fromJson(json_str9, type);
                }
                String json_str10 = preferences.getString("FRI_START", null);
                if (json_str10.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    start_time_fri = gson.fromJson(json_str10, type);
                }

                String json_str11 = preferences.getString("SAT_END", null);
                if (json_str11.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    end_time_sat = gson.fromJson(json_str11, type);
                }
                String json_str12 = preferences.getString("SAT_START", null);
                if (json_str12.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    start_time_sat = gson.fromJson(json_str12, type);
                }
                String json_str13 = preferences.getString("SUN_END", null);
                if (json_str13.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    end_time_sun = gson.fromJson(json_str13, type);
                }

                String json_str14 = preferences.getString("SUN_START", null);
                if (json_str14.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    start_time_sun = gson.fromJson(json_str14, type);
                }

                String json_str15 = preferences.getString("SINGLE", null);
                if (json_str15.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    selected_day = gson.fromJson(json_str15, type);
                }

                String json_str16 = preferences.getString("ALL_START", null);
                if (json_str16.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    json_start = gson.fromJson(json_str16, type);
                }

                String json_str17 = preferences.getString("ALL_END", null);
                if (json_str17.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    json_end = gson.fromJson(json_str17, type);
                }
                String json_str18 = preferences.getString("ALL_DAY", null);
                if (json_str18.isEmpty()) {

                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    json = gson.fromJson(json_str18, type);
                }



                if (host_str.equals("PUBLIC")) {
                    rate = preferences.getString("Rate", null);
                    String charger_type = preferences.getString("Charger_type_c", null);
                    String charger_brand = preferences.getString("Charger_brand", null);
                    String charger_model = preferences.getString("Charger_model", null);
                    String board = preferences.getString("board_p", null);
                    String connector = preferences.getString("Connector", null);
                    String power_str = preferences.getString("power_p", "");
                    String voltage_p = preferences.getString("Voltage_p", "");
                    String amphere_p=preferences.getString("Amphere_P",null);
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

                    Log.e("TAG", "Place: " + charger_type + ", " + charger_brand + "," + charger_model + "," + board + connector + power_str + voltage_p + state + add_1 + add_2 + city + country + land + zip + Lat + Long);
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

                    Call<ResponseBody> call = service.add_public_list2(user_id, city, add_1, add_2, land, state, country, voltage_p,amphere_p, profit_str, rate, pin_int, latitude, longitude, selected, flag, board, charger_type, connector, charger_brand, charger_model, power_str, custum_brand, custum_model, socket_public, custom_connector_type_public, custom_voltage_public, custom_power_output_public, selected_day, json, json_start, json_end, start_time_mon, end_time_mon, start_time_tues, end_time_tues, start_time_wed, end_time_wed, start_time_thus, end_time_thus, start_time_fri, end_time_fri, start_time_sat, end_time_sat, start_time_sun, end_time_sun, start_time_public, end_time_public, all_day_str, all_mon, all_tue, all_wed, all_thr, all_fri, all_sat, all_sun,public_info,private_info);
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
                    String board = preferences.getString("board_com", null);
                    String connector = preferences.getString("Connector_com", null);
                    String power = preferences.getString("power_com", null);
                    String voltage = preferences.getString("Voltage_com", null);
                    String amphere_c = preferences.getString("Amphere_com", null);
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
                    Log.e("TAG", "Place: " + charger_type + ", " + charger_brand + "," + charger_model + "," + board + connector + power + voltage + state + add_1 + add_2 + city + country + land + zip + Lat + Long);
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

                    Call<ResponseBody> call = service.add_commercial_list2(user_id,add_1,add_2,land,city,state,country,latitude,longitude,pin_int,board,rate,profit_str,selected,charger_type,connector,charger_brand,voltage,amphere_c,flag,charger_model,power,custum_brand,custum_model,socket_str,custom_connector_type_public,custom_voltage_public,custom_power_output_public,selected_day,json,json_start,json_end,start_time_mon,end_time_mon,start_time_tues,end_time_tues,start_time_wed,end_time_wed,start_time_thus,end_time_thus,start_time_fri,end_time_fri,start_time_sat,end_time_sat,start_time_sun,end_time_sun,start_time_all,end_time_all,all_day_str,all_mon,all_tue,all_wed,all_thr,all_fri,all_sat,all_sun);
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
                    String board = preferences.getString("Board", null);
                    String connector = preferences.getString("Connector_resi", null);
                    String power = preferences.getString("power_resi", null);
                    String voltage = preferences.getString("Voltage", null);
                    String amphere = preferences.getString("Amphere", null);
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


                    Log.e("TAG", "Place: " + charger_type + ", " + charger_brand + "," + charger_model + "," + board + connector + power + voltage +amphere + state + add_1 + add_2 + city + country + land + zip + Lat + Long);
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

                    Call<ResponseBody> call = service.add_residential_list2(user_id,add_1,add_2,land,city,state,country,latitude,longitude,pin_int,board,rate,profit_str,selected,charger_type,connector,charger_brand,voltage,amphere,flag,charger_model,power,custum_brand,custum_model,Socket_resi,custom_connector_type_public,custom_voltage_public,custom_power_output_public,selected_day,json,json_start,json_end,start_time_mon,end_time_mon,start_time_tues,end_time_tues,start_time_wed,end_time_wed,start_time_thus,end_time_thus,start_time_fri,end_time_fri,start_time_sat,end_time_sat,start_time_sun,end_time_sun,start_time_all,end_time_all,all_day_str,all_mon,all_tue,all_wed,all_thr,all_fri,all_sat,all_sun);
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
