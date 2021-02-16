package com.example.elshare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elshare.adapter.AvailabilityTimeSlotAdapter;
import com.example.elshare.adapter.ChargerTypeAdapter;
import com.example.elshare.adapter.GooglePlacesAutocompleteAdapter;
import com.example.elshare.adapter.SocketTypeAdapter;
import com.example.elshare.utils.SingletonRetrofit;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mohammedalaa.seekbar.DoubleValueSeekBarView;
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import datamodel.APIInterface;
import datamodel.Connector_type;
import datamodel.MarkerData;
import datamodel.ShowAvailabilityPojo;
import datamodel.ShowAvailabilityProvider;
import datamodel.Socket;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class map_frag extends Fragment implements OnMapReadyCallback, AdapterView.OnItemClickListener
{
    ImageButton mapFilter;
    private GoogleMap mMap;
    Dialog pw;
    ListView lv_languages;
    BottomSheetDialog bottomSheetDialog;
    ArrayAdapter list_adapter;
    Dialog myDialog;
    DoubleValueSeekBarView  rangeSeekBar;
    GooglePlacesAutocompleteAdapter mGooglePlacesAutocompleteAdapter;
    AutoCompleteTextView mAddressAutoCompleteTextView;
    private Button apply_p,reset_b,price,avail,rate,rate_a,rate_r,avail_a,avail_r,price_a,price_r,show_list;
     Boolean priceVisible;
    Dialog viewDetailDialog;

    RecyclerView recyclerView;
    AvailabilityTimeSlotAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> time_array = new ArrayList<>();
    ArrayList<String> day_array = new ArrayList<>();

    RangeSeekBar seekBar;
    RequestBody priceStartBody;
    RequestBody priceEndBody;
    RequestBody placeNameBody;
    RequestBody socketDataBody;
    RequestBody timeSlotBody;
    RequestBody availableDayBody;
    RequestBody chargerBody;

    TextView chargerTypeText;
    TextView socketTypeText;
    TextView connetorText;
    TextView priceText;
    private List<MarkerData> markerData;
    private MarkerData markerList;
    private List<Connector_type> connetor_set;
    private Connector_type my_connector;
    private List<Socket> socket_set;
    private Socket socketArray;

    public int i;
    SharedPreferences.Editor editor;
    final ArrayList<String> chargerType = new ArrayList<>();
    final ArrayList<String> socketType = new ArrayList<>();
    ArrayList<String> selectedSocket = new ArrayList<>();
    ArrayList<String> selectedCharger = new ArrayList<>();
    ArrayList<String> availabilityDays = new ArrayList<>();
    ArrayList<String> timeSlotArray = new ArrayList<>();
    private ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();

    View mapMarkerView ;


    SocketTypeAdapter socketTypeAdapter;
    ChargerTypeAdapter chargerTypeAdapter;
    SupportMapFragment mapFragment;
    Set set = new HashSet();
    String hostStr;
    CheckBox monday;
    CheckBox tuesday;
    CheckBox wednesday;
    CheckBox thursday;
    CheckBox friday;
    CheckBox satursday;
    CheckBox sunday;
    CheckBox morningTime;
    CheckBox middayTime;
    CheckBox afternoonTime;
    CheckBox eveningTime;
    CheckBox  nightTime;

     String chargerString ;
     String connectorTypeString;
     String priceRateString;
     String list_id;
     SharedPreferences preferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.map_frag, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        mapFilter = rootView.findViewById(R.id.imageButton35);
        myDialog = new Dialog(getContext());
        mAddressAutoCompleteTextView=rootView.findViewById(R.id.autoCompleteTextViewMap);
        mGooglePlacesAutocompleteAdapter = new GooglePlacesAutocompleteAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item);
        mAddressAutoCompleteTextView.setAdapter(mGooglePlacesAutocompleteAdapter);
        mAddressAutoCompleteTextView.setOnItemClickListener(this);
         seekBar = new RangeSeekBar<Integer>(getContext());

        mapFragment= (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        initialMarkers();

        mapFilter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                socketType.clear();
                chargerType.clear();
                final RecyclerView chargerTypeRecycleView;
                final RecyclerView socketTypeRecycleView;
                final TextView minText;
                final TextView maxText;
                Button mapFilterSortByPrice;
                final LinearLayout priceLayout;
                pw=new Dialog(v.getRootView().getContext(), R.style.PauseDialog);
                pw.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                pw.setTitle("Map Filter");
                pw.setContentView(R.layout.filter_window);
                minText=pw.findViewById(R.id.filterMinRange);
                maxText=pw.findViewById(R.id.filterMaxRange);
                priceLayout=pw.findViewById(R.id.pricesortLayout);
                priceLayout.setVisibility(View.GONE);
                priceVisible=true;
                mapFilterSortByPrice=pw.findViewById(R.id.mapFilterSortByPrice);
                mapFilterSortByPrice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (priceVisible)
                        {
                            priceLayout.setVisibility(View.VISIBLE);
                            priceVisible=false;
                        }
                        else
                        {
                            priceLayout.setVisibility(View.GONE);
                            priceVisible=true;

                        }

                    }
                });

                 rangeSeekBar=pw.findViewById(R.id.double_range_seekbar);
                rangeSeekBar.setOnRangeSeekBarViewChangeListener(new OnDoubleValueSeekBarChangeListener() {
                    @Override
                    public void onValueChanged(@Nullable DoubleValueSeekBarView seekBar, int min, int max, boolean fromUser) {

                        Log.i("Min:", String.valueOf(min));
                        Log.i("Max:", String.valueOf(max));
                        minText.setText(String.valueOf(min));
                        maxText.setText(String.valueOf(max));

                    }

                    @Override
                    public void onStartTrackingTouch(@Nullable DoubleValueSeekBarView seekBar, int min, int max) {

                    }

                    @Override
                    public void onStopTrackingTouch(@Nullable DoubleValueSeekBarView seekBar, int min, int max) {

                    }
                });
                if (priceVisible) {
                    priceStartBody = RequestBody.create(MediaType.parse("multipart/form-data"), minText.getText().toString());
                    priceEndBody = RequestBody.create(MediaType.parse("multipart/form-data"), maxText.getText().toString());
                }

                monday=pw.findViewById(R.id.mapFilterMonday);
                tuesday=pw.findViewById(R.id.mapFilterTuesday);
                wednesday=pw.findViewById(R.id.mapFilterWednesday);
                thursday=pw.findViewById(R.id.mapFilterThursday);
                friday=pw.findViewById(R.id.mapFilterFriday);
                satursday=pw.findViewById(R.id.mapFilterSatursday);
                sunday=pw.findViewById(R.id.mapFilterSunday);
                morningTime=pw.findViewById(R.id.mapFilterTimeMorning);
                afternoonTime=pw.findViewById(R.id.mapFilterTimeAfternoon);
                middayTime=pw.findViewById(R.id.mapFilterTimeMidDay);
                eveningTime=pw.findViewById(R.id.mapFilterTimeEvening);
                nightTime=pw.findViewById(R.id.mapFilterTimeOverNight);
                mainatinStateOfCheckboxes(v);
                monday.setChecked(preferences.getBoolean("monday", false));
                tuesday.setChecked(preferences.getBoolean("tuesday", false));
                wednesday.setChecked(preferences.getBoolean("wednesday", false));
                thursday.setChecked(preferences.getBoolean("thursday", false));
                friday.setChecked(preferences.getBoolean("friday", false));
                satursday.setChecked(preferences.getBoolean("satursday", false));
                sunday.setChecked(preferences.getBoolean("sunday", false));

                morningTime.setChecked(preferences.getBoolean("morning", false));
                middayTime.setChecked(preferences.getBoolean("midday", false));
                afternoonTime.setChecked(preferences.getBoolean("afternoon", false));
                eveningTime.setChecked(preferences.getBoolean("evening", false));
                nightTime.setChecked(preferences.getBoolean("overnight", false));

                monday.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
                tuesday.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
                wednesday.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
                thursday.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
                friday.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
                satursday.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
                sunday.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
                morningTime.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
                middayTime.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
                afternoonTime.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
                eveningTime.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
                nightTime.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());

                chargerTypeRecycleView =pw.findViewById(R.id.mapFilterChargerType);
                socketTypeRecycleView =pw.findViewById(R.id.mapFilterSocketType);


                APIInterface service = SingletonRetrofit.getAPIInterface();
                Call<List<Connector_type>> call = service.getConnectorMapFilter();
                Log.i("Connector_api  : ", String.valueOf(call.request().url()));
                call.enqueue(new Callback<List<Connector_type>>() {
                    @Override
                    public void onResponse(Call<List<Connector_type>> call, Response<List<Connector_type>> response) {
                        if (response.body() != null) {
                            Log.i("login response: ", String.valueOf(response.body()));
                            connetor_set = response.body();
                            if (connetor_set != null) {
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    my_connector = connetor_set.get(i);
                                    demo_str = my_connector.getConnectorType();
                                    chargerType.add(demo_str);
                                }
                                chargerTypeRecycleView.setHasFixedSize(true);
                                GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
                                chargerTypeRecycleView.setLayoutManager(manager);
                                chargerTypeAdapter = new ChargerTypeAdapter(getContext(), chargerType);
                                chargerTypeRecycleView.setAdapter(chargerTypeAdapter);
                            }
                        }
                        else {

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Connector_type>> call, Throwable t) {
                        Log.i("login error: ", String.valueOf(t));
                    }
                });

                Call<List<Socket>> call_socket = service.getSocketMapFilter();
                call_socket.enqueue(new Callback<List<Socket>>() {
                    @Override
                    public void onResponse(Call<List<Socket>> call_socket, Response<List<Socket>> response) {
                        if (response.body() != null) {
                            Log.i("login response: ", String.valueOf(response.body()));
                            socket_set = response.body();
                            if (socket_set != null) {
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    socketArray = socket_set.get(i);
                                    demo_str = socketArray.getSocket();
                                    socketType.add(demo_str);
                                }
                                socketTypeRecycleView.setHasFixedSize(true);
                                GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
                                socketTypeRecycleView.setLayoutManager(manager);
                                socketTypeAdapter = new SocketTypeAdapter(getContext(), socketType);
                                socketTypeRecycleView.setAdapter(socketTypeAdapter);
                            }
                        }
                        else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Socket>> call_socket, Throwable t) {
                        Log.i("login error: ", String.valueOf(t));
                    }
                });

                pw.show();
                reset_b=pw.findViewById(R.id.mapFilterReset);
                final LinearLayout timeSlotLayout=pw.findViewById(R.id.timeSlotLayout);
                final LinearLayout availabilityLayout=pw.findViewById(R.id.availabilityLayout);
                reset_b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        availableDayBody = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                        timeSlotBody = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                        chargerBody = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                        socketDataBody = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                        for(int i=0; i<timeSlotLayout.getChildCount();i++){
                            v = timeSlotLayout.getChildAt(i);
                            if(v instanceof CheckBox ){
                                ((CheckBox) v).setChecked(false);
                            }
                        }
                        for (int i=0;i<availabilityLayout.getChildCount();i++)
                        {
                            v=availabilityLayout.getChildAt(i);
                            {
                                if (v instanceof  CheckBox)
                                {
                                    ((CheckBox) v).setChecked(false);

                                }
                            }
                        }
                        editor=preferences.edit();
                        editor.remove("SOCKET_SET");
                        editor.remove("CHARGER_SET");
                        editor.apply();
                        pw.dismiss();
                        defaultMarker();
                    }
                });
                apply_p=pw.findViewById(R.id.mapFilterApply);
                apply_p.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();

                        Gson gson = new Gson();
                        String charger_str = preferences.getString("CHARGER_SET", "null");
                        if (charger_str.isEmpty()) { } else {
                            Type type = new TypeToken<List<String>>() {
                            }.getType();
                            selectedCharger = gson.fromJson(charger_str, type);
                            Log.i("Selected charger:", String.valueOf(selectedCharger));
                        }

                        String socket_str = preferences.getString("SOCKET_SET", "null");
                        if (socket_str.isEmpty()) { } else {
                            Type type = new TypeToken<List<String>>() {
                            }.getType();
                            selectedSocket = gson.fromJson(socket_str, type);
                            Log.i("Selected charger:", String.valueOf(selectedSocket));
                        }
                        availableDayBody = RequestBody.create(MediaType.parse("multipart/form-data"), availabilityDays.toString());
                        timeSlotBody = RequestBody.create(MediaType.parse("multipart/form-data"), timeSlotArray.toString());
                        Log.i("Selected Time:", String.valueOf(timeSlotArray));


                        if (selectedSocket!=null && selectedCharger!=null) {
                            chargerBody = RequestBody.create(MediaType.parse("multipart/form-data"), charger_str);
                            socketDataBody = RequestBody.create(MediaType.parse("multipart/form-data"), socket_str);
                            Log.i("Available Day:", String.valueOf(availabilityDays));
                            defaultMarker();
                        }

                        else if (selectedSocket!=null && selectedCharger==null && selectedCharger.isEmpty())
                        {
                            socketDataBody = RequestBody.create(MediaType.parse("multipart/form-data"), socket_str);
                            defaultMarker();
                        }

                    }
                });

            }
        });

        return rootView;
    }

    private void mainatinStateOfCheckboxes(View v) {
    }

    private void defaultMarker()
    {
        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear();

                final ArrayList<Double> latArray=new ArrayList<>();
                final ArrayList<Double> longArray=new ArrayList<>();

                final ArrayList<String> chrgerTypeArray=new ArrayList<>();
                final ArrayList<String> connectorTypeArray=new ArrayList<>();
                final ArrayList<String> rateFinalPriceArray=new ArrayList<>();
                final ArrayList<String> listingId=new ArrayList<>();
                final ArrayList<String> hostArray=new ArrayList<>();


                placeNameBody = RequestBody.create(MediaType.parse("multipart/form-data"), mAddressAutoCompleteTextView.getText().toString());

                APIInterface service = SingletonRetrofit.getAPIInterface();
                Call<List<MarkerData>> call = service.getMarkerData(priceStartBody,priceEndBody,placeNameBody,socketDataBody,timeSlotBody,availableDayBody,chargerBody);
                call.enqueue(new Callback<List<MarkerData>>() {
                    @Override
                    public void onResponse(Call<List<MarkerData>> call, Response<List<MarkerData>> response) {
                        if (response.body() != null) {
                            markerData=response.body();
                            if (markerData!=null) {
                                ArrayList markerArray = (ArrayList) response.body();

                                for (i = 0; i < markerArray.size(); i++) {
                                    markerList = markerData.get(i);

                                    Double lat = markerList.getLatitude();
                                    Double lng = markerList.getLongitude();
                                    latArray.add(lat);
                                    longArray.add(lng);
                                    chrgerTypeArray.add(markerList.getBrand());
                                    connectorTypeArray.add(markerList.getConnectorType());
                                    rateFinalPriceArray.add(markerList.getFinalPrice());
                                    listingId.add(String.valueOf(markerList.getId()));
                                    list_id=String.valueOf(markerList.getId());
                                    hostArray.add(markerList.getTable());
                                }
                                for (i = 0; i < latArray.size(); i++) {
                                    if (latArray.get(i)!=null) {
                                        if (chrgerTypeArray.get(i) != null) {
                                            chargerString = chrgerTypeArray.get(i);
                                            connectorTypeString = connectorTypeArray.get(i);
                                            priceRateString = rateFinalPriceArray.get(i);
                                            list_id = listingId.get(i);
                                            Log.i("Brand:", chargerString);
                                            Log.i("Listing Id:", listingId.get(i));

                                            Log.i("Lattitude:", String.valueOf(latArray.get(i)));
                                            Log.i("Longitude:", String.valueOf(longArray.get(i)));

                                            LatLng latLngPosition = new LatLng(latArray.get(i), longArray.get(i)); ///
                                            Log.i("Lat Lng:", String.valueOf(latLngPosition));
                                            mMap.addMarker(new MarkerOptions().position(latLngPosition).title(list_id).snippet(hostArray.get(i)).icon(bitmapDescriptorFromVector(getContext(),R.drawable.pin_marker)));
                                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngPosition));
                                            mMap.getUiSettings().setZoomControlsEnabled(true);
                                            mMap.animateCamera(CameraUpdateFactory.zoomOut(), 10, null);

                                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                                @Override
                                                public void onInfoWindowClick(Marker marker) {
                                                    showAlertDialog(marker.getTitle(),marker.getSnippet());
                                                }
                                            });

                                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                                @Override
                                                public boolean onMarkerClick(Marker marker) {

                                                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                                                        @Override
                                                        public View getInfoWindow(Marker arg0) {
                                                            return null;
                                                        }

                                                        @Override
                                                        public View getInfoContents(Marker arg0) {
                                                            Log.i("ID:", String.valueOf(arg0.getTitle()));
                                                            Log.i("HOST:",arg0.getSnippet());
                                                            String hostStr=arg0.getSnippet();
                                                            hostStr=trimSingleQuote(hostStr);

                                                            try {
                                                                APIInterface service = SingletonRetrofit.getAPIInterface();
                                                                Call<ShowAvailabilityPojo> call = service.getAvailability(arg0.getTitle(),hostStr);
                                                                Log.i("Connector_api  : ", String.valueOf(call.request().url()));
                                                                call.enqueue(new Callback<ShowAvailabilityPojo>() {
                                                                    @Override
                                                                    public void onResponse(Call<ShowAvailabilityPojo> call, Response<ShowAvailabilityPojo> response) {
                                                                        if (response.body() != null) {
                                                                            ShowAvailabilityPojo avaiPojo=response.body();
                                                                            if (avaiPojo!=null)
                                                                            {
                                                                                ShowAvailabilityProvider providerObject=avaiPojo.getProvider();
                                                                                if (providerObject!=null)
                                                                                {
                                                                                    mapMarkerView = getLayoutInflater().inflate(R.layout.text_auto, null);

                                                                                    connetorText =(TextView) mapMarkerView.findViewById(R.id.type_type);
                                                                                    priceText = mapMarkerView.findViewById(R.id.rate_type);
                                                                                    chargerTypeText = mapMarkerView.findViewById(R.id.charger_type);
                                                                                    chargerTypeText.bringToFront();

                                                                                    if (providerObject.getPowerOutput()!=null) {
                                                                                        connetorText.setText(providerObject.getPowerOutput());
                                                                                    }
                                                                                    else {
                                                                                        connetorText.setText("null");
                                                                                    }
                                                                                    priceText.setText(providerObject.getFinalPrice());
                                                                                    if (providerObject.getChargerBrand()!=null)
                                                                                    {
                                                                                        StringWriter sw = new StringWriter();
                                                                                        sw.append("Charger Brand:");
                                                                                        sw.append(providerObject.getChargerBrand());
                                                                                        chargerTypeText.setText(sw.toString());
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        StringWriter sw = new StringWriter();
                                                                                        sw.append("Socket:");
                                                                                        sw.append(providerObject.getChargerBrand());
                                                                                        chargerTypeText.setText(sw.toString());
                                                                                    }
                                                                                    Log.i("Data:",(connetorText.getText().toString()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<ShowAvailabilityPojo> call, Throwable t) {

                                                                    }
                                                                });


                                                            } catch (Exception ev) {
                                                                System.out.print(ev.getMessage());
                                                            }
                                                            return mapMarkerView;
                                                        }
                                                    });
                                                    return false;
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                            else {
                                Toast.makeText(getContext(), "No Listing available.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<List<MarkerData>> call, Throwable t) {
                        Log.i("Map error: ", String.valueOf(t));
                        Toast.makeText(getContext(), "Please check network.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }


    public View getInfoContents(Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.text_auto, null);

        TextView titleTextView = (TextView) view.findViewById(R.id.type_type);
        TextView snippetTextView = (TextView) view.findViewById(R.id.rate_type);

        titleTextView.setText(marker.getTitle());
        snippetTextView.setText(marker.getSnippet());

        return view;
    }

    private void initialMarkers()
    {
        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear();

                final ArrayList<Double> latArray=new ArrayList<>();
                final ArrayList<Double> longArray=new ArrayList<>();

                final ArrayList<String> chrgerTypeArray=new ArrayList<>();
                final ArrayList<String> connectorTypeArray=new ArrayList<>();
                final ArrayList<String> rateFinalPriceArray=new ArrayList<>();
                final ArrayList<String> listingId=new ArrayList<>();
                final ArrayList<String> hostArray=new ArrayList<>();

                placeNameBody = RequestBody.create(MediaType.parse("multipart/form-data"), mAddressAutoCompleteTextView.getText().toString());

                APIInterface service = SingletonRetrofit.getAPIInterface();
                Call<List<MarkerData>> call = service.getMarkerData(priceStartBody,priceEndBody,placeNameBody,socketDataBody,timeSlotBody,availableDayBody,chargerBody);
                call.enqueue(new Callback<List<MarkerData>>() {
                    @Override
                    public void onResponse(Call<List<MarkerData>> call, Response<List<MarkerData>> response) {
                        if (response.body() != null) {
                            markerData=response.body();
                            if (markerData!=null) {
                                ArrayList markerArray = (ArrayList) response.body();

                                for (i = 0; i < markerArray.size(); i++) {
                                    markerList = markerData.get(i);
                                    Double lat = markerList.getLatitude();
                                    Double lng = markerList.getLongitude();
                                    latArray.add(lat);
                                    longArray.add(lng);
                                    chrgerTypeArray.add(markerList.getBrand());
                                    connectorTypeArray.add(markerList.getConnectorType());
                                    rateFinalPriceArray.add(markerList.getFinalPrice());
                                    listingId.add(String.valueOf(markerList.getId()));
                                    hostArray.add(markerList.getTable());
                                }
                                for (i = 0; i < latArray.size(); i++) {
                                    chargerString = chrgerTypeArray.get(i);
                                    connectorTypeString=connectorTypeArray.get(i);
                                    priceRateString=rateFinalPriceArray.get(i);
                                    list_id=listingId.get(i);
                                    Log.i("Brand:",chargerString);
                                    Log.i("Listing Id:",listingId.get(i));
                                        Log.i("Lattitude:", String.valueOf(latArray.get(i)));
                                        Log.i("Longitude:", String.valueOf(longArray.get(i)));


                                        LatLng latLngPosition = new LatLng(latArray.get(i), longArray.get(i)); ///
                                        Log.i("Lat Lng:", String.valueOf(latLngPosition));
//                                    mMap.addMarker(new MarkerOptions().position(latLngPosition));
                                        mMap.addMarker(new MarkerOptions().position(latLngPosition).title(list_id).snippet(hostArray.get(i)).icon(bitmapDescriptorFromVector(getContext(),R.drawable.pin_marker)));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngPosition));
                                        mMap.getUiSettings().setZoomControlsEnabled(true);
                                        mMap.animateCamera(CameraUpdateFactory.zoomOut(), 10, null);

                                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                            @Override
                                            public void onInfoWindowClick(Marker marker) {
                                                showAlertDialog(marker.getTitle(),marker.getSnippet());
                                            }
                                        });

                                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                            @Override
                                            public boolean onMarkerClick(final Marker marker) {

                                                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                                                    @Override
                                                    public View getInfoWindow(Marker arg0) {
                                                        return null;
                                                    }

                                                    @Override
                                                    public View getInfoContents(Marker arg0) {
                                                        Log.i("ID:", String.valueOf(arg0.getTitle()));
                                                        Log.i("HOST:",arg0.getSnippet());
                                                        String hostStr=arg0.getSnippet();
                                                        hostStr=trimSingleQuote(hostStr);
                                                        try {
                                                            APIInterface service = SingletonRetrofit.getAPIInterface();
                                                            Call<ShowAvailabilityPojo> call = service.getAvailability(arg0.getTitle(),hostStr);
                                                            Log.i("Connector_api  : ", String.valueOf(call.request().url()));
                                                            call.enqueue(new Callback<ShowAvailabilityPojo>() {
                                                                @Override
                                                                public void onResponse(Call<ShowAvailabilityPojo> call, Response<ShowAvailabilityPojo> response) {
                                                                    if (response.body() != null) {
                                                                        ShowAvailabilityPojo avaiPojo=response.body();
                                                                        if (avaiPojo!=null)
                                                                        {
                                                                            ShowAvailabilityProvider providerObject=avaiPojo.getProvider();
                                                                            if (providerObject!=null)
                                                                            {
                                                                                mapMarkerView = getLayoutInflater().inflate(R.layout.text_auto, null);

                                                                                connetorText =(TextView) mapMarkerView.findViewById(R.id.type_type);
                                                                                priceText = mapMarkerView.findViewById(R.id.rate_type);
                                                                                chargerTypeText = mapMarkerView.findViewById(R.id.charger_type);
                                                                                chargerTypeText.bringToFront();
                                                                                connetorText.setText(marker.getTitle());

                                                                                if (providerObject.getConnectorType()!=null) {
                                                                                    connetorText.setText(providerObject.getConnectorType());
                                                                                }
                                                                                else {
                                                                                    connetorText.setText("null");
                                                                                }
                                                                                priceText.setText(providerObject.getFinalPrice());
                                                                                if (providerObject.getChargerBrand()!=null)
                                                                                {

                                                                                    StringWriter sw = new StringWriter();
                                                                                    sw.append("Charger Brand:");
                                                                                    sw.append(providerObject.getChargerBrand());
                                                                                    chargerTypeText.setText(sw.toString());
                                                                                }
                                                                                else
                                                                                {
                                                                                    StringWriter sw = new StringWriter();
                                                                                    sw.append("Socket:");
                                                                                    sw.append(providerObject.getChargerBrand());
                                                                                    chargerTypeText.setText(sw.toString());
                                                                                }
                                                                                Log.i("Data:",(connetorText.getText().toString()));
                                                                            }
                                                                        }
                                                                    }
                                                                }

                                                                @Override
                                                                public void onFailure(Call<ShowAvailabilityPojo> call, Throwable t) {

                                                                }
                                                            });


                                                        } catch (Exception ev) {
                                                            System.out.print(ev.getMessage());
                                                        }
                                                        return mapMarkerView;
                                                    }
                                                });
                                                return false;
                                            }
                                        });

                                }
                            }
                            else {
                                Toast.makeText(getContext(), "No Listing available.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<List<MarkerData>> call, Throwable t) {
                        Log.i("Map error: ", String.valueOf(t));
                        Toast.makeText(getContext(), "Please check network.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    private void showAlertDialog(String hostId,String  hostTable)
    {

        hostStr=hostTable;
        hostStr=trimSingleQuote(hostStr);

        String title_str=hostStr +" "+"charger";
        Log.i("Table name:",title_str);
        viewDetailDialog=new Dialog(getContext(),R.style.DialogAlignment);
        viewDetailDialog.setContentView(R.layout.pricing_reservation_pop_up);
        recyclerView=viewDetailDialog.findViewById(R.id.availabilityTimeSlotRecycleView);

        TextView pop_up_title=viewDetailDialog.findViewById(R.id.mapMarkerTitle);
        if (hostStr.equals("publics"))
        {
            pop_up_title.setText("Public Charger");
        }
        else   if (hostStr.equals("residentials"))
        {
            pop_up_title.setText("Residential Charger");
        }
        else   if (hostStr.equals("commercials"))
        {
            pop_up_title.setText("Commercial Charger");
        }

        final TextView socket_type=viewDetailDialog.findViewById(R.id.mapMarkerPopUpSocket);
        final TextView charger_type=viewDetailDialog.findViewById(R.id.mapMarkerPopUpCharger);
        final TextView power_output=viewDetailDialog.findViewById(R.id.mapMarkerPopUpPower);
        final TextView model_type=viewDetailDialog.findViewById(R.id.mapMarkerPopUpModel);
        final TextView connector_type=viewDetailDialog.findViewById(R.id.mapMarkerPopUpConnctor);
        final TextView rate_text=viewDetailDialog.findViewById(R.id.mapMarkerPopUpRate);
        final TextView availableDay=viewDetailDialog.findViewById(R.id.mapMarkerPopUpAvailableDay);
        final LinearLayout socketLayout=viewDetailDialog.findViewById(R.id.socketTypeLayout);
        final LinearLayout chargerLayout=viewDetailDialog.findViewById(R.id.chargerTypeLayout);
        Button pricingAndReservationButton=viewDetailDialog.findViewById(R.id.mapMarkerPopUpPricingButton);
        pricingAndReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),AddBookingActivity.class);
                startActivity(intent);
            }
        });

        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        day_array.clear();
        time_array.clear();

        APIInterface service = SingletonRetrofit.getAPIInterface();
        Call<ShowAvailabilityPojo> call = service.getAvailability(hostId,hostStr);
        Log.i("Connector_api  : ", String.valueOf(call.request().url()));
        call.enqueue(new Callback<ShowAvailabilityPojo>() {
                         @Override
                         public void onResponse(Call<ShowAvailabilityPojo> call, Response<ShowAvailabilityPojo> response) {
                             if (response.body() != null) {
                                 ShowAvailabilityPojo availabilityPojo = response.body();
                                 if (availabilityPojo!=null) {
                                     Log.i("days:",availabilityPojo.getProvider().getDays());
                                     availableDay.setText(availabilityPojo.getProvider().getDays());
                                     power_output.setText(availabilityPojo.getProvider().getPowerOutput());
                                     rate_text.setText(availabilityPojo.getProvider().getFinalPrice());
                                     editor=preferences.edit();
                                     editor.putString("HOST_ID",String.valueOf(availabilityPojo.getProvider().getId()));
                                     editor.putString("USER_ID",String.valueOf(availabilityPojo.getData().getUserId()));
                                     editor.putString("HOST_BOOKING_PRICE",availabilityPojo.getProvider().getFinalPrice());
                                     editor.putString("HOST_BOOKING_POWER_OUTPUT",availabilityPojo.getProvider().getPowerOutput());
                                     editor.apply();

                                     if (availabilityPojo.getProvider().getChargerBrand()!=null) {
                                         if (availabilityPojo.getProvider().getSocket()!=null) {
                                             socket_type.setText(availabilityPojo.getProvider().getSocket());
                                             power_output.setText(availabilityPojo.getProvider().getPowerOutput());
                                             chargerLayout.setVisibility(View.GONE);
                                             socketLayout.setVisibility(View.VISIBLE);
                                             mProgressDialog.dismiss();
                                         }
                                         else
                                         {
                                             socket_type.setText("null");
                                         }
                                     }
                                     else
                                     {
                                      socketLayout.setVisibility(View.GONE);
                                      chargerLayout.setVisibility(View.VISIBLE);
                                      if (availabilityPojo.getProvider().getChargerBrand()!=null) {
                                          charger_type.setText(availabilityPojo.getProvider().getChargerBrand());
                                          model_type.setText(availabilityPojo.getProvider().getChargerModel());
                                          connector_type.setText(availabilityPojo.getProvider().getConnectorType());
                                          mProgressDialog.dismiss();
                                      }
                                      else
                                      {
                                          charger_type.setText("null");
                                      }
                                     }

                                     String selected_days=availabilityPojo.getProvider().getDays();
                                     String single_day_string=availabilityPojo.getSingleDay();
                                     Log.i("Connector_api",single_day_string);

                                     if (single_day_string.equals("0"))
                                     {
                                         StringBuilder stringBuilder = new StringBuilder("");
                                         int listSize = availabilityPojo.getStartTime().size();
                                         for (int itemIndex = 0; itemIndex < availabilityPojo.getStartTime().size(); itemIndex++) {
                                             String startTime = availabilityPojo.getStartTime().get(itemIndex);
                                             String endTime = availabilityPojo.getEndTime().get(itemIndex);
                                             stringBuilder.append(startTime + "-" + endTime);
                                             if (itemIndex < listSize - 1) {
                                                 stringBuilder.append(", ");
                                             }
                                         }
                                         if (selected_days.contains("monday")) {
                                             time_array.add(stringBuilder.toString());
                                             day_array.add("Monday");
                                         }
                                         if (selected_days.contains("tuesday")) {
                                             time_array.add(stringBuilder.toString());
                                             day_array.add("Tuesday");
                                         }
                                         if (selected_days.contains("wednesday")) {
                                             time_array.add(stringBuilder.toString());
                                             day_array.add("Wednesday");
                                         }
                                         if (selected_days.contains("thursday")) {
                                             time_array.add(stringBuilder.toString());
                                             day_array.add("Thursday");
                                         }
                                         if (selected_days.contains("friday")) {
                                             time_array.add(stringBuilder.toString());
                                             day_array.add("Friday");
                                         }
                                         if (selected_days.contains("saturday")) {
                                             time_array.add(stringBuilder.toString());
                                             day_array.add("Satursday");
                                         }
                                         if (selected_days.contains("sunday")) {
                                             time_array.add(stringBuilder.toString());
                                             day_array.add("Sunday");
                                         }
                                     }
                                     else
                                     {
                                         if (selected_days.contains("monday")) {
                                             String selectedTimeValue = getSelectedTimeForDay(availabilityPojo.getStartTimeMon(), availabilityPojo.getEndTimeMon());
                                             time_array.add(selectedTimeValue);
                                             day_array.add("Monday");
                                         }
                                         if (selected_days.contains("tuesday")) {
                                             String selectedTimeValue = getSelectedTimeForDay(availabilityPojo.getStartTimeTues(), availabilityPojo.getEndTimeTues());
                                             time_array.add(selectedTimeValue);
                                             day_array.add("Tuesday");
                                         }
                                         if (selected_days.contains("wednesday")) {
                                             String selectedTimeValue = getSelectedTimeForDay(availabilityPojo.getStartTimeWed(), availabilityPojo.getEndTimeWed());
                                             time_array.add(selectedTimeValue);
                                             day_array.add("Wednesday");
                                         }
                                         if (selected_days.contains("thursday")) {
                                             String selectedTimeValue = getSelectedTimeForDay(availabilityPojo.getStartTimeThus(), availabilityPojo.getEndTimeThus());
                                             time_array.add(selectedTimeValue);
                                             day_array.add("Thursday");
                                         }
                                         if (selected_days.contains("friday")) {
                                             String selectedTimeValue = getSelectedTimeForDay(availabilityPojo.getStartTimeFri(), availabilityPojo.getEndTimeFri());
                                             time_array.add(selectedTimeValue);
                                             day_array.add("friday");

                                         }
                                         if (selected_days.contains("saturday")) {
                                             String selectedTimeValue = getSelectedTimeForDay(availabilityPojo.getStartTimeSat(), availabilityPojo.getEndTimeSat());
                                             time_array.add(selectedTimeValue);
                                             day_array.add("saturday");

                                         }
                                         if (selected_days.contains("sunday")) {
                                             String selectedTimeValue = getSelectedTimeForDay(availabilityPojo.getStartTimeSun(), availabilityPojo.getEndTimeSun());
                                             time_array.add(selectedTimeValue);
                                             day_array.add("Sunday");

                                         }
                                     }
                                     Log.i("Provider", availabilityPojo.getProvider().getDays());
                                     Gson gson = new Gson();
                                     String mDayArrayString=gson.toJson(day_array);
                                     editor.putString("DAY_ARRAY", mDayArrayString);
                                     String mTimeArrayString=gson.toJson(time_array);
                                     editor.putString("TIME_ARRAY",mTimeArrayString);
                                     editor.putString("TABLE_NAME",hostStr);
                                     editor.apply();
                                     Log.i("Day:",String.valueOf(day_array));
                                 }

                                 try {

                                 }catch (Exception e)
                                 {
                                     e.printStackTrace();
                                 }

                                 if (day_array.size()==0) {
                                     Toast.makeText(getContext(), "No Availability of host.", Toast.LENGTH_SHORT).show();
                                 }
                                 else
                                 {
                                     recyclerView.setHasFixedSize(true);
                                     layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                     recyclerView.setLayoutManager(layoutManager);
                                     adapter = new AvailabilityTimeSlotAdapter(getContext(), day_array, time_array);
                                     recyclerView.setAdapter(adapter);
                                 }

                             }
                             else {

                             }
                         }
            @Override
            public void onFailure(Call<ShowAvailabilityPojo> call, Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
                Toast.makeText(getContext(), "Internal server error.", Toast.LENGTH_SHORT).show();
            }
        });

        viewDetailDialog.show();
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    private void showAvailbilityPopUp() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Commercial Details \n\n Availability");
        builder1.setCancelable(true);
        builder1.setPositiveButton("close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder1.create();
        alertDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String place_name = mGooglePlacesAutocompleteAdapter.getItem(position);
        place_name=trimDoubbleQuote(place_name);
        Log.i("Place is:",place_name);
        mAddressAutoCompleteTextView.setText(place_name);
        placeNameBody = RequestBody.create(MediaType.parse("multipart/form-data"), mAddressAutoCompleteTextView.getText().toString());
        defaultMarker();

    }
    public static String trimDoubbleQuote(String value){
        return value.replaceAll("^\"|\"$", "");
    }
    public static String trimSingleQuote(String value){
        return value.replaceAll("^\'|\'$", "");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public class mSingleCheckBoxChangeListener implements CheckBox.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int backgroundColorGrey = ContextCompat.getColor(getContext(), R.color.quantum_grey);
            int backgroundColorGreen = ContextCompat.getColor(getContext(), R.color.green);
            boolean checkBoxValue;
            editor=preferences.edit();
            if (buttonView == monday) {
                if (isChecked) {
                    availabilityDays.add("monday");
                    checkBoxValue=true;
                    editor.putBoolean("monday", checkBoxValue);
                    editor.apply();
                } else {
                    availabilityDays.remove("monday");
                     checkBoxValue =false;
                    editor.putBoolean("monday", checkBoxValue);
                    editor.commit();

                }
            }

            if (buttonView == tuesday) {
                if (isChecked) {
                    availabilityDays.add("tuesday");
                    checkBoxValue=true;
                    editor.putBoolean("tuesday", checkBoxValue);
                    editor.apply();
                } else {
                    availabilityDays.remove("tuesday");
                    checkBoxValue=false;
                    editor.putBoolean("tuesday", checkBoxValue);
                    editor.apply();
                }
            }

            if (buttonView == wednesday) {
                if (isChecked) {
                    availabilityDays.add("wednesday");
                    checkBoxValue=true;
                    editor.putBoolean("wednesday", checkBoxValue);
                    editor.apply();
                } else {
                    availabilityDays.remove("wednesday");
                    checkBoxValue=false;
                    editor.putBoolean("wednesday", checkBoxValue);
                    editor.apply();
                }
            }

            if (buttonView == thursday) {
                if (isChecked) {
                    availabilityDays.add("thursday");
                    checkBoxValue=true;
                    editor.putBoolean("thursday", checkBoxValue);
                    editor.apply();
                } else {
                    availabilityDays.remove("thursday");
                    checkBoxValue=false;
                    editor.putBoolean("thursday", checkBoxValue);
                    editor.apply();
                }
            }

            if (buttonView == friday) {
                if (isChecked) {
                    availabilityDays.add("friday");
                    checkBoxValue=true;
                    editor.putBoolean("friday", checkBoxValue);
                    editor.apply();
                } else {
                    availabilityDays.remove("friday");
                    checkBoxValue=false;
                    editor.putBoolean("friday", checkBoxValue);
                    editor.apply();
                }
            }

            if (buttonView == satursday) {
                if (isChecked) {
                    availabilityDays.add("saturday");
                    checkBoxValue=true;
                    editor.putBoolean("satursday", checkBoxValue);
                    editor.apply();
                } else {
                    availabilityDays.remove("saturday");
                    checkBoxValue=false;
                    editor.putBoolean("saturday", checkBoxValue);
                    editor.apply();
                }
            }

            if (buttonView == sunday) {
                if (isChecked) {
                    availabilityDays.add("sunday");
                    checkBoxValue=true;
                    editor.putBoolean("sunday", checkBoxValue);
                    editor.apply();
                } else {
                    availabilityDays.remove("sunday");
                    checkBoxValue=false;
                    editor.putBoolean("sunday", checkBoxValue);
                    editor.apply();
                }
            }
            if (buttonView==morningTime)
            {
                if (isChecked){
                    timeSlotArray.add("8:00-11:00");
                    checkBoxValue=true;
                    editor.putBoolean("morning", checkBoxValue);
                    editor.apply();
                }
                else {
                    timeSlotArray.remove("8:00-11:00");
                    checkBoxValue=false;
                    editor.putBoolean("morning", checkBoxValue);
                    editor.apply();
                }
            }
            if (buttonView==middayTime)
            {
                if (isChecked){
                    timeSlotArray.add("11:00-15:00");
                    checkBoxValue=true;
                    editor.putBoolean("midday", checkBoxValue);
                    editor.apply();
                }
                else {
                    timeSlotArray.remove("11:00-15:00");
                    checkBoxValue=false;
                    editor.putBoolean("midday", checkBoxValue);
                    editor.apply();
                }
            }
            if (buttonView==afternoonTime)
            {
                if (isChecked){
                    timeSlotArray.add("15:00-18:00");
                    checkBoxValue=true;
                    editor.putBoolean("afternoon", checkBoxValue);
                    editor.apply();
                }
                else {
                    timeSlotArray.remove("15:00-18:00");
                    checkBoxValue=false;
                    editor.putBoolean("afternoon", checkBoxValue);
                    editor.apply();
                }
            }
            if (buttonView==eveningTime)
            {
                if (isChecked){
                    timeSlotArray.add("18:00-21:00");
                    checkBoxValue=true;
                    editor.putBoolean("evening", checkBoxValue);
                    editor.apply();
                }
                else {
                    timeSlotArray.remove("18:00-21:00");
                    checkBoxValue=false;
                    editor.putBoolean("evening", checkBoxValue);
                    editor.apply();
                }
            }
            if (buttonView==nightTime)
            {
                if (isChecked){
                    timeSlotArray.add("21:00-8:00");
                    checkBoxValue=true;
                    editor.putBoolean("overnight", checkBoxValue);
                    editor.apply();
                }
                else {
                    timeSlotArray.remove("21:00=8:00");
                    checkBoxValue=false;
                    editor.putBoolean("overnight", checkBoxValue);
                    editor.apply();
                }
            }
        }
    }
    public String getSelectedTimeForDay(List<String> startTimeList, List<String> endTimeList) {
        StringBuilder stringBuilder = new StringBuilder("");
        int listSize = startTimeList.size();
        for (int itemIndex = 0; itemIndex < listSize; itemIndex++) {
            String startTime = startTimeList.get(itemIndex);
            String endTime = endTimeList.get(itemIndex);
            stringBuilder.append(startTime + "-" + endTime);
            if (itemIndex < listSize - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }


}
