package com.example.elshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.example.elshare.utils.SingletonRetrofit;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import datamodel.APIInterface;
import datamodel.residential_show_list.show_residential;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class residential_dialog extends AppCompatActivity {
    private show_residential show_resi_arry;
    int id_view;
    private static MapView mMapView;
    private static MapFragment mMapFragment;
    private static GoogleMap mGoogleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        final MapView sch_map = findViewById(R.id.map_resi_view);
        sch_map.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_residential_dialog);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        id_view = preferences.getInt("LISTING_ID", Integer.parseInt(""));
        //        id_view = preferences.getInt("EDIT_LIST_ID", Integer.parseInt(""));
        id_view=10;
        Log.i("Listing Id", String.valueOf(id_view));

               final TextView price_tex,socket_txt,avg_rate_txt,vtg_txt,booking_txt,day_txt,mon_d,tue_d,wed_d,thu_d,fri_d,sat_d,sun_d,addreess_txt;
               addreess_txt=findViewById(R.id.address_view_resi);
               price_tex= findViewById(R.id.view_rate_resi);
               socket_txt=findViewById(R.id.socket_resi);
               avg_rate_txt=findViewById(R.id.avg_rate_resi);
               vtg_txt=findViewById(R.id.voltage_resi);
               booking_txt=findViewById(R.id.booking_resi);
               day_txt=findViewById(R.id.days_resi);
               mon_d=findViewById(R.id.monday_resi_v);
               tue_d=findViewById(R.id.tue_resi_v);
               wed_d=findViewById(R.id.wed_resi_v);
               thu_d=findViewById(R.id.thus_resi_v);
               fri_d=findViewById(R.id.fri_resi_v);
               sat_d=findViewById(R.id.sat_resi_v);
               sun_d=findViewById(R.id.sun_resi_v);
               APIInterface service = SingletonRetrofit.getAPIInterface();

               Call<show_residential> call = service.getListResidential(id_view);
               Log.i("Call", String.valueOf(call.request().url()));

               call.enqueue(new Callback<show_residential>() {
                   @SuppressLint("Assert")
                   @Override
                   public void onResponse(Call<show_residential> call, Response<show_residential> response) {

                       Log.e("Residential response1:", String.valueOf(response.isSuccessful()));
                       Log.e("Residential response2:", String.valueOf(response.code()));
                       Log.e("Residential response3:", String.valueOf(response.errorBody()));
                       if (response.body() != null) {
                           Log.i("Make  response: ", String.valueOf(response.body()));
                           show_resi_arry =  response.body();
                           ArrayList demo_str;
                           int id_res,id_listing,address_id;
                           int i;

                           sch_map.getMapAsync(new OnMapReadyCallback() {
                               @Override
                               public void onMapReady(final GoogleMap googleMap) {
                                   sch_map.onResume();
                                   LatLng sydney = new LatLng(-33.867, 151.206);
                                   googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                               }
                           });

//                           for (i = 0; i < make_array.size(); i++) {

//                           SupportMapFragment mapFragment = SupportMapFragment.newInstance();
//                           mapFragment.getFragmentManager().findFragmentById(R.id.map_resi_view);
//                           mapFragment.getMapAsync(new OnMapReadyCallback() {
//                                                       @Override
//                                                       public void onMapReady(GoogleMap mMap) {
//                                                           mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//                                                           mMap.clear(); //clear old markers
//
//                                                           CameraPosition googlePlex = CameraPosition.builder()
//                                                                   .target(new LatLng(37.4219999, -122.0862462))
//                                                                   .zoom(10)
//                                                                   .bearing(0)
//                                                                   .tilt(45)
//                                                                   .build();
//
//                                                           mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);
//
//                                                           mMap.addMarker(new MarkerOptions()
//                                                                   .position(new LatLng(37.4219999, -122.0862462))
//                                                                   .title("Spider Man")
//                                                                   .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.arrow_d)));
//                                                       }
//                                                   });
//                           TextView price_tex,socket_txt,avg_rate_txt,vtg_txt,booking_txt,day_txt,mon_d,tue_d,wed_d,thu_d,fri_d,sat_d,sun_d;
//                           GoogleMapOptions options = new GoogleMapOptions();
//                           options.liteMode(true);
//                           SupportMapFragment mapFrag = SupportMapFragment.newInstance(options);
//                           if (mapFrag == null) {
//                               map = SupportMapFragment.newInstance();
//                               mapFragment.getMapAsync(new OnMapReadyCallback() {
//                                   @Override
//                                   public void onMapReady(GoogleMap googleMap) {
//                                       LatLng latLng = new LatLng(1.289545, 103.849972);
//                                       googleMap.addMarker(new MarkerOptions().position(latLng)
//                                               .title("Singapore"));
//                                       googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//                                   }
//                               });
//                           }

//                           assert mapFrag.getFragmentManager() != null;
//                           mapFrag.getFragmentManager().findFragmentById(R.id.map_resi);
//                           mapFrag.onResume();
//                           mapFrag.getMapAsync(new OnMapReadyCallback() {
//                                                       @Override
//                                                       public void onMapReady(GoogleMap mMap) {
//                                                           mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//                                                           mMap.clear(); //clear old markers
//
//                                                           CameraPosition googlePlex = CameraPosition.builder()
//                                                                   .target(new LatLng(37.4219999, -122.0862462))
//                                                                   .zoom(10)
//                                                                   .bearing(0)
//                                                                   .tilt(45)
//                                                                   .build();
//
//                                                           mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);
//
//                                                           mMap.addMarker(new MarkerOptions()
//                                                                   .position(new LatLng(37.4219999, -122.0862462))
//                                                                   .title("Spider Man")
//                                                                   .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.arrow_d)));
//                                                       }
//                                                   });

                           String address_str = new String();
                           String chatrger_empty,socket_empty,m_d,t_d,w_d,th_d,f_d,sat,sun;
                                   int booking;
//                           chatrger_empty=String.valueOf(show_resi_arry.getCharger());
                           socket_empty=String.valueOf(show_resi_arry.getSocket());
                           address_str=show_resi_arry.getAddress().getAddress1() +"," +show_resi_arry.getAddress().getAddress2() +"," +show_resi_arry.getAddress().getCountry()+","+show_resi_arry.getAddress().getState();
//                          addreess_txt.setText(address_str);
//                           if (chatrger_empty==null)
//                           {
//                               avg_rate_txt.setText(String.valueOf(show_resi_arry.getCharger().getRateStructure()));
//                               vtg_txt.setText(String.valueOf(show_resi_arry.getCharger().getVoltage()));
//
//
//                           }
//                           else {
//                               socket_txt.setText(String.valueOf(show_resi_arry.getSocket().getSocket()));
//                               avg_rate_txt.setText(String.valueOf(show_resi_arry.getSocket().getRateStructure()));
//                               vtg_txt.setText(String.valueOf(show_resi_arry.getSocket().getVoltage()));
//                           }
//
//                           booking=Integer.valueOf(show_resi_arry.getProvider().getInstantBooking());
//                           if (booking==0)
//                           {
//                               booking_txt.setText("Not Available");
//                           }
//                           else
//                           {
//                               booking_txt.setText("Available");
//                           }
                           day_txt.setText(String.valueOf(show_resi_arry.getDaysArray()));





                       } else {
                           String new_str = "Make is empty";
                           Log.i("Make: ", new_str);
                           //  Toast.makeText(getContext(), "Please Select make", Toast.LENGTH_LONG).show();
                       }
                   }

                   @Override
                   public void onFailure(Call<show_residential> call, Throwable t) {
                       Log.i("Make error: ", String.valueOf(t));

                   }
               });
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
