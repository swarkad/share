package com.example.elshare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elshare.utils.SingletonRetrofit;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import datamodel.APIInterface;
import datamodel.ResidentialListing;
import datamodel.residential_show_list.show_residential;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class CustomAdapter_resi extends RecyclerView.Adapter<CustomAdapter_resi.MyViewHolder> {
private ArrayList<DataModel> dataSet;
private ItemOnClickListner mListner;
private show_residential show_resi_arry;
    SupportMapFragment mapFragment;
    Dialog myDialog,pw;



    public interface ItemOnClickListner {
        void OnItemClickListner(int position);
    }
    public  void setItemOnClickListner(ItemOnClickListner listner){
        mListner=listner;
    }
public static class MyViewHolder extends RecyclerView.ViewHolder {


    EditText addres_field;
   TextView textViewVersion,index_txt;
   ImageButton edit_r,delete_r,view_r;
    Context context;


    @SuppressLint("WrongViewCast")
   public MyViewHolder(View itemView, final ItemOnClickListner listner) {
       super(itemView);
       this.addres_field = (EditText) itemView.findViewById(R.id.address_resi);
       this.delete_r=(ImageButton) itemView.findViewById(R.id.delete_resi);
       this.view_r=(ImageButton) itemView.findViewById(R.id.viewAsResi);
       this.index_txt=(TextView) itemView.findViewById(R.id.index_resi);
       this.edit_r=(ImageButton)itemView.findViewById(R.id.edit_resi);
       context=itemView.getContext();

   }
}

   public CustomAdapter_resi(ArrayList<DataModel> data) {
       this.dataSet = data;
   }

   @Override
   public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                          int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.custom_list_resi, parent, false);

       view.setOnClickListener(list_residential.myOnClickListener);
      pw = new Dialog(parent.getContext());

       MyViewHolder myViewHolder = new MyViewHolder(view,mListner);
       return myViewHolder;
   }

   @Override
   public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

       final EditText addres_field = holder.addres_field;
      final ImageButton delete_b=holder.delete_r;
      final ImageButton view_R=holder.view_r;
      ImageButton edit_r=holder.edit_r;
      TextView index_view=holder.index_txt;
//       myDialog = new Dialog(this);
//
       delete_b.setOnClickListener(new View.OnClickListener() {
           @SuppressLint("LongLogTag")
           @Override
           public void onClick(View view) {
               // Get the clicked item label
               final DataModel itemLabel = dataSet.get(listPosition);
               Log.i("Delete ----------------------------------", String.valueOf(itemLabel.getId()));
               Log.i("Delete user ----------------------------------", String.valueOf(itemLabel.getUser_id()));

               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getRootView().getContext());
               alertDialogBuilder.setMessage("Are you sure you want to delete?");
               alertDialogBuilder.setPositiveButton("Ok",
                       new DialogInterface.OnClickListener() {

                           @Override
                           public void onClick(DialogInterface arg0, int arg1)
                           {
                               int id_delete=itemLabel.getId();
                               APIInterface service = SingletonRetrofit.getAPIInterface();

                               Call<List<ResidentialListing>> call = service.deleteResidentialListing(id_delete);
                               Log.i("Call", String.valueOf(call.request().url()));
                                              dataSet.remove(listPosition);
                                             notifyItemRemoved(listPosition);
                                             notifyItemRangeChanged(listPosition,dataSet.size());


                               call.enqueue(new Callback<List<ResidentialListing>>() {

                                   @Override
                                   public void onResponse(Call<List<ResidentialListing>> call, Response<List<ResidentialListing>> response) {

                                   }

                                   @Override
                                   public void onFailure(Call<List<ResidentialListing>> call, Throwable t) {
                                       Log.i("Make error: ", String.valueOf(t));

                                   }
                               });

                           }
                       });

               alertDialogBuilder.setNegativeButton("cancel",
                       new DialogInterface.OnClickListener() {

                           @Override
                           public void onClick(DialogInterface arg0, int arg1) {

                           }
                       });

               AlertDialog alertDialog = alertDialogBuilder.create();
               alertDialog.show();

           }
       });
       view_R.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(final View v) {
               final DataModel itemLabel = dataSet.get(listPosition);
               SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
               SharedPreferences.Editor editor = preferences.edit();
               editor.putString("EDIT_LIST_ID",String.valueOf(itemLabel.getId()));
               editor.apply();
//               AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

//               ViewGroup viewGroup = (ViewGroup) v.getParent();
//                   View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.account_evdriver_frag, viewGroup, false);
//                  
//                   builder.setView(dialogView);
//               AlertDialog alertDialog = builder.create();
//               alertDialog.show();


//               Intent intent=new Intent(v.getRootView().getContext(), residential_dialog.class);
//               Integer id_list = itemLabel.getId();
//               intent.putExtra("LISTING_ID", id_list);
//               v.getRootView().getContext().startActivity(intent);

//               pw = new Dialog(v.getRootView().getContext());

               pw.setContentView(R.layout.view_residential);
               pw.setTitle("Residential Listing");
               pw.show();

               int id_view=itemLabel.getId();
               final TextView price_tex,socket_txt,avg_rate_txt,vtg_txt,booking_txt,day_txt,mon_d,tue_d,wed_d,thu_d,fri_d,sat_d,sun_d,address_str;
               price_tex= pw.findViewById(R.id.view_rate_resi);
               socket_txt=pw.findViewById(R.id.socket_resi);
               address_str=pw.findViewById(R.id.address_view_resi);
               avg_rate_txt=pw.findViewById(R.id.avg_rate_resi);
               vtg_txt=pw.findViewById(R.id.voltage_resi);
               booking_txt=pw.findViewById(R.id.booking_resi);
               day_txt=pw.findViewById(R.id.days_resi);
               mon_d=pw.findViewById(R.id.monday_resi_v);
               tue_d=pw.findViewById(R.id.tue_resi_v);
               wed_d=pw.findViewById(R.id.wed_resi_v);
               thu_d=pw.findViewById(R.id.thus_resi_v);
               fri_d=pw.findViewById(R.id.fri_resi_v);
               sat_d=pw.findViewById(R.id.sat_resi_v);
               sun_d=pw.findViewById(R.id.sun_resi_v);
               APIInterface service = SingletonRetrofit.getAPIInterface();

               Call<show_residential> call = service.getListResidential(id_view);
               Log.i("Call", String.valueOf(call.request().url()));

               call.enqueue(new Callback<show_residential>() {
                   @SuppressLint("SetTextI18n")
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
//                           for (i = 0; i < make_array.size(); i++) {
                           Log.i("Day array: ", String.valueOf(show_resi_arry.getAddress()));
                           Log.i("Day array: ", String.valueOf(show_resi_arry.getAddress().getCountry()));
                           Log.i("Day array: ", String.valueOf(show_resi_arry.getDaysArray()));

                           if (mapFragment == null) {
                               mapFragment = SupportMapFragment.newInstance();
                               mapFragment.getMapAsync(new OnMapReadyCallback() {
                                   @Override
                                   public void onMapReady(GoogleMap googleMap) {
                                       googleMap.addMarker(new MarkerOptions().position(new LatLng(37.4219999, -122.0862462)));
                                       googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(37.4219999, -122.0862462)));
                                   }
                               });
                           }

//                           View view_new=pw.findViewById(R.id.map_sample);
                           AppCompatActivity activity = (AppCompatActivity) v.getRootView().getContext();
                           activity.getSupportFragmentManager().beginTransaction().add(pw.findViewById(R.id.map_sample).getId(), mapFragment).addToBackStack(null).commit();

//                           ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction()
//                                   .replace(Integer.parseInt(String.valueOf(pw.findViewById(R.id.map_sample))), mapFragment)
//                                   .commit();

                           // for fragment
//                            FragmentManager fragmentManager =
                           // for activity
//                           FragmentManager fragmentManager = getSupportFragmentManager();
                           // R.id.map is a FrameLayout, not a Fragment
                           // ERROR: R.id.map might not be attached to RecyclerView/Activity yet
//                           fragmentManager.beginTransaction().replace(R.id.map, mapFragment).commit();

//                           GoogleMapOptions options = new GoogleMapOptions();
//                           options.liteMode(true);
//                           SupportMapFragment mapFrag = SupportMapFragment.newInstance(options);
//                           assert mapFrag.getFragmentManager() != null;
//                           mapFrag.getChildFragmentManager().findFragmentById(pw.findViewById(R.id.map_resi_R).getId());
////                           mapFrag.getFragmentManager().findFragmentByTag(String.valueOf(pw.findViewById(R.id.map_resi_R).getId()));
//                           mapFrag.getMapAsync(new OnMapReadyCallback() {
//                                                       @Override
//                                                       public void onMapReady(GoogleMap mMap) {
//                                                           mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                                                           mMap.clear(); //clear old markers
//                                                           mMap.addMarker(new MarkerOptions()
//                                                                   .position(new LatLng(37.4219999, -122.0862462))
//                                                                   .title("Spider Man")
//                                                                   .icon(bitmapDescriptorFromVector(v.getRootView().getContext(), R.drawable.arrow_d)));
//                                                       }
//                                                   });

//                           final TextView price_tex,socket_txt,avg_rate_txt,vtg_txt,booking_txt,day_txt,mon_d,tue_d,wed_d,thu_d,fri_d,sat_d,sun_d;

                           socket_txt.setText(String.valueOf(show_resi_arry.getProvider().getSocket()));
                           avg_rate_txt.setText(String.valueOf(show_resi_arry.getProvider().getRateStructure()));
                           vtg_txt.setText(String.valueOf(show_resi_arry.getProvider().getVoltage()));
                           day_txt.setText(String.valueOf(show_resi_arry.getDaysArray()));
                           address_str.setText(show_resi_arry.getAddress().getAddress1() +"," +show_resi_arry.getAddress().getAddress2() +"," +show_resi_arry.getAddress().getCountry()+","+show_resi_arry.getAddress().getState());

                           List<String> days_array=show_resi_arry.getDaysArray();
                           int j;
                           for (j = 0; j < days_array.size(); j++) {

                               String day_str=days_array.get(j);
                               Log.i("day is:",day_str);

                               if (day_str.equals("monday"))
                               {
                                   mon_d.setText("Available");
                               }
                               else if (day_str.equals("tuesday"))
                               {
                                   tue_d.setText("Available");
                               }
                               else if (day_str.equals("wednesday"))
                               {
                                   wed_d.setText("Available");
                               }
                               else if (day_str.equals("thursday"))
                               {
                                   thu_d.setText("Available");
                               }
                               else if (day_str.equals("friday"))
                               {
                                   fri_d.setText("Available");
                               }
                               else if (day_str.equals("satursday"))
                               {
                                   sat_d.setText("Available");
                               }
                               else if (day_str.equals("sunday"))
                               {
                                   sun_d.setText("Available");
                               }

                           }

                           String chatrger_empty,socket_empty,m_d,t_d,w_d,th_d,f_d,sat,sun;
                           double booking;
                           socket_empty=String.valueOf(show_resi_arry.getProvider().getSocket());
                           if (socket_empty==null || socket_empty.isEmpty() || socket_empty.equals("null"))
                           {
                               //Display chrager details

                           }
                           else {
                               //socket detail


                           }
                           //booking show
                           booking=(show_resi_arry.getProvider().getInstantBooking());
                           if (booking==0)
                           {
                               booking_txt.setText("Not Available");
                           }
                           else
                           {
                               booking_txt.setText("Available");
                           }
                           //



                       }
                       else {
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

               Button ok_btn;
               ok_btn=pw.findViewById(R.id.button142);
               ok_btn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       pw.dismiss();


                   }
               });
               pw.show();

           }

       });

       edit_r.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(v.getContext(), EditResidentialListingActivity.class);
               //  Intent intent = new Intent(v.getContext(), edit_residential_listing.class);
               final DataModel itemLabel = dataSet.get(listPosition);
               int list_id=itemLabel.getId();
               intent.putExtra("List_V_ID",String.valueOf(list_id));
               v.getContext().startActivity(intent);

           }
       });
       addres_field.setText(dataSet.get(listPosition).getName());
       int num=listPosition + 1 ;
       index_view.setText(String.valueOf(num));

   }

   @Override
   public int getItemCount() {
       return dataSet.size();
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


