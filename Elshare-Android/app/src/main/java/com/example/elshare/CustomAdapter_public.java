package com.example.elshare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.elshare.utils.SingletonRetrofit;

import java.util.ArrayList;
import java.util.List;

import datamodel.APIInterface;
import datamodel.PublicListing;
import datamodel.show_public;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class CustomAdapter_public extends RecyclerView.Adapter<CustomAdapter_public.MyViewHolder> {
private ArrayList<DataModel> dataSet;
private show_public show_public_array;
Dialog pw;
    private CustomAdapter_public.ItemOnClickListner mListner;
    public interface ItemOnClickListner {
        void OnItemClickListner(int position);
    }
    public  void setItemOnClickListner(CustomAdapter_public.ItemOnClickListner listner){
        mListner=listner;
    }

public static class MyViewHolder extends RecyclerView.ViewHolder {

    EditText address_pub;
   TextView textViewVersion;
   ImageButton delete_p,edit_p,view_p;
    TextView index_txt;

   public MyViewHolder(View itemView) {
       super(itemView);
       this.address_pub = (EditText) itemView.findViewById(R.id.address_public);
       this.delete_p=(ImageButton) itemView.findViewById(R.id.buttonDelete_p);
       this.view_p=(ImageButton) itemView.findViewById(R.id.public_view);
       this.edit_p=itemView.findViewById(R.id.edit_public_listing);
       this.index_txt= itemView.findViewById(R.id.index_pub);


   }
}

   public CustomAdapter_public(ArrayList<DataModel> data) {
       this.dataSet = data;
   }

   @Override
   public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                          int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.custom_list_public, parent, false);

       view.setOnClickListener(list_public.myOnClickListener);

       MyViewHolder myViewHolder = new MyViewHolder(view);
       pw= new Dialog(parent.getContext());
       return myViewHolder;
   }

   @Override
   public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

       EditText address_pub = holder.address_pub;
       ImageButton delete_b=holder.delete_p;
       ImageButton view_p=holder.view_p;
       address_pub.setText(dataSet.get(listPosition).getName());
       TextView index_view=holder.index_txt;
       ImageButton edit_p=holder.edit_p;

       delete_b.setOnClickListener(new View.OnClickListener() {
           @SuppressLint("LongLogTag")
           @Override
           public void onClick(View view) {
               // Get the clicked item label
               final DataModel itemLabel = dataSet.get(listPosition);
               // Remove the item on remove/button click
//               dataSet.remove(listPosition);
//               notifyItemRemoved(listPosition);
//               notifyItemRangeChanged(listPosition,dataSet.size());

               // Show the removed item label
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

                               Call<List<PublicListing>> call = service.deletePublicListing(id_delete);
                               Log.i("Call", String.valueOf(call.request().url()));
                               dataSet.remove(listPosition);
                               notifyItemRemoved(listPosition);
                               notifyItemRangeChanged(listPosition,dataSet.size());


                               call.enqueue(new Callback<List<PublicListing>>() {

                                   @Override
                                   public void onResponse(Call<List<PublicListing>> call, Response<List<PublicListing>> response) {

                                   }

                                   @Override
                                   public void onFailure(Call<List<PublicListing>> call, Throwable t) {
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

       view_p.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(final View v) {
               final DataModel itemLabel = dataSet.get(listPosition);
//               Intent intent=new Intent(v.getRootView().getContext(), residential_dialog.class);
//               Integer id_list = itemLabel.getId();
//               intent.putExtra("LISTING_ID", id_list);
//               v.getRootView().getContext().startActivity(intent);

               pw = new Dialog(v.getRootView().getContext());
               pw.setContentView(R.layout.view_public);
               pw.setTitle("Public Listing");
               pw.show();

               int id_view=itemLabel.getId();
               Log.i("User_id",String.valueOf(id_view));
               final TextView price_tex,socket_txt,avg_rate_txt,vtg_txt,booking_txt,day_txt,address_str,mon_d,tue_d,wed_d,thu_d,fri_d,sat_d,sun_d;
               price_tex= pw.findViewById(R.id.view_rate_public);
               socket_txt=pw.findViewById(R.id.socket_public_v);
               address_str=pw.findViewById(R.id.address_view_pub);
               avg_rate_txt=pw.findViewById(R.id.avg_rate_public_v);
               vtg_txt=pw.findViewById(R.id.voltage_Pub);
               booking_txt=pw.findViewById(R.id.booking_pub);
               day_txt=pw.findViewById(R.id.days_pub);
               mon_d=pw.findViewById(R.id.monday_resi_p);
               tue_d=pw.findViewById(R.id.tue_resi_p);
               wed_d=pw.findViewById(R.id.wed_resi_p);
               thu_d=pw.findViewById(R.id.thus_resi_p);
               fri_d=pw.findViewById(R.id.fri_resi_p);
               sat_d=pw.findViewById(R.id.sat_resi_p);
               sun_d=pw.findViewById(R.id.sun_resi_p);
               APIInterface service = SingletonRetrofit.getAPIInterface();

               Call<show_public> call = service.getListPublic(id_view);
               Log.i("Call public", String.valueOf(call.request().url()));

               call.enqueue(new Callback<show_public>() {
                   @Override
                   public void onResponse(Call<show_public> call, Response<show_public> response) {

                       Log.e("Residential response1:", String.valueOf(response.isSuccessful()));
                       Log.e("Residential response2:", String.valueOf(response.code()));
                       Log.e("Residential response3:", String.valueOf(response.errorBody()));
                       if (response.body() != null) {
                           Log.i("Make  response: ", String.valueOf(response.body()));
                           show_public_array =  response.body();
                           ArrayList demo_str;
                           int id_res,id_listing,address_id;
                           int i;
//                           for (i = 0; i < make_array.size(); i++) {
                           Log.i("Day array: ", String.valueOf(show_public_array.getAddress()));
                           Log.i("Day array: ", String.valueOf(show_public_array.getAddress().getCountry()));
                           Log.i("Day array: ", String.valueOf(show_public_array.getDaysArray()));
//                           TextView price_tex,socket_txt,avg_rate_txt,vtg_txt,booking_txt,day_txt,mon_d,tue_d,wed_d,thu_d,fri_d,sat_d,sun_d;
//                           GoogleMapOptions options = new GoogleMapOptions();
//                           options.liteMode(true);
//                           SupportMapFragment mapFrag = SupportMapFragment.newInstance(options);
//                           assert mapFrag.getFragmentManager() != null;
//                           mapFrag.getFragmentManager().findFragmentById(R.id.map_pub);
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
//                                                                   .icon(bitmapDescriptorFromVector(v.getRootView().getContext(), R.drawable.arrow_d)));
//                                                       }
//                                                   });

                           socket_txt.setText(String.valueOf(show_public_array.getProvider().getSocket()));
                           avg_rate_txt.setText(String.valueOf(show_public_array.getProvider().getRateStructure()));
                           vtg_txt.setText(String.valueOf(show_public_array.getProvider().getVoltage()));
                           day_txt.setText(String.valueOf(show_public_array.getProvider().getDays()));
                           address_str.setText(show_public_array.getAddress().getAddress1() +"," +show_public_array.getAddress().getAddress2() +"," +show_public_array.getAddress().getCountry()+","+show_public_array.getAddress().getState());
                           List<String> days_array=show_public_array.getDaysArray();
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
                           Long booking;
                           socket_empty=String.valueOf(show_public_array.getProvider().getSocket());
                           if (socket_empty==null || socket_empty.isEmpty() || socket_empty.equals("null"))
                           {
                               //Display chrager details

                           }
                           else {
                               //socket detail


                           }
                           //booking show
                           booking=(show_public_array.getProvider().getInstantBooking());
                           if (booking==0)
                           {
                               booking_txt.setText("Not Available");
                           }
                           else
                           {
                               booking_txt.setText("Available");
                           }

                       } else {
                           String new_str = "Make is empty";
                           Log.i("Make: ", new_str);
                           //  Toast.makeText(getContext(), "Please Select make", Toast.LENGTH_LONG).show();
                       }
                   }

                   @Override
                   public void onFailure(Call<show_public> call, Throwable t) {
                       Log.i("Make error: ", String.valueOf(t));

                   }
               });

               Button ok_btn;
               ok_btn=pw.findViewById(R.id.button148);
               ok_btn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       pw.dismiss();
                   }
               });
               pw.show();


           }

       });
       edit_p.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //Intent intent = new Intent(v.getContext(), edit_public_listing.class);
               Intent intent = new Intent(v.getContext(), EditPublicListingActivity.class);
               final DataModel itemLabel = dataSet.get(listPosition);
               int list_id=itemLabel.getId();
               intent.putExtra("List_V_ID",String.valueOf(list_id));
               v.getContext().startActivity(intent);

           }
       });
       address_pub.setText(dataSet.get(listPosition).getName());
       int num=listPosition + 1 ;
       index_view.setText(String.valueOf(num));

   }

   @Override
   public int getItemCount() {
       return dataSet.size();
   }
}

