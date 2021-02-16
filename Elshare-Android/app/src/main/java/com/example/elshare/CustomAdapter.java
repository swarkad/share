package com.example.elshare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elshare.utils.SingletonRetrofit;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
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
import datamodel.CommertialListing;
import datamodel.show_commertial;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
private ArrayList<DataModel> dataSet;
private show_commertial comm_show_array;
    Dialog pw;
     private CustomAdapter.ItemOnClickListner mListner;

     public interface ItemOnClickListner {
         void OnItemClickListner(int position);
     }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

    EditText addres_com;
    ImageButton delete_c,edit_c,view_c;
        TextView index_txt;

    public MyViewHolder(View itemView) {
        super(itemView);
        this.addres_com = (EditText) itemView.findViewById(R.id.address_com);
        this.delete_c=(ImageButton) itemView.findViewById(R.id.buttonDelete);
        this.view_c=(ImageButton) itemView.findViewById(R.id.viewAsPublic_com);
        this.edit_c=(ImageButton) itemView.findViewById(R.id.edit_com);
        this.index_txt= itemView.findViewById(R.id.index_com);

    }
}

    public CustomAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list_item, parent, false);

        view.setOnClickListener(list_commertial.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        pw = new Dialog(parent.getContext());
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        EditText addres_com = holder.addres_com;
        addres_com.setText(dataSet.get(listPosition).getName());
        ImageButton delete_b=holder.delete_c;
        ImageButton view_c=holder.view_c;
        ImageButton edit_c=holder.edit_c;
        TextView index_view=holder.index_txt;
        delete_b.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
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

                                Call<List<CommertialListing>> call = service.deleteCommertialListing(id_delete);
                                Log.i("Call", String.valueOf(call.request().url()));
                                dataSet.remove(listPosition);
                                notifyItemRemoved(listPosition);
                                notifyItemRangeChanged(listPosition,dataSet.size());
                                call.enqueue(new Callback<List<CommertialListing>>() {

                                    @Override
                                    public void onResponse(Call<List<CommertialListing>> call, Response<List<CommertialListing>> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<List<CommertialListing>> call, Throwable t) {
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
        view_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final DataModel itemLabel = dataSet.get(listPosition);
//               Intent intent=new Intent(v.getRootView().getContext(), residential_dialog.class);
//               Integer id_list = itemLabel.getId();
//               intent.putExtra("LISTING_ID", id_list);
//               v.getRootView().getContext().startActivity(intent);

                pw.show();

                int id_view=itemLabel.getId();
                final TextView price_tex,socket_txt,avg_rate_txt,vtg_txt,booking_txt,address_str,day_txt,mon_d,tue_d,wed_d,thu_d,fri_d,sat_d,sun_d;
                price_tex= pw.findViewById(R.id.view_rate_com);
                socket_txt=pw.findViewById(R.id.socket_com);
                address_str=pw.findViewById(R.id.address_view_com);
                avg_rate_txt=pw.findViewById(R.id.avg_rate_com);
                vtg_txt=pw.findViewById(R.id.voltage_com);
                booking_txt=pw.findViewById(R.id.booking_com);
                day_txt=pw.findViewById(R.id.days_com);
                mon_d=pw.findViewById(R.id.monday_resi_c);
                tue_d=pw.findViewById(R.id.tue_resi_c);
                wed_d=pw.findViewById(R.id.wed_resi_c);
                thu_d=pw.findViewById(R.id.thus_resi_c);
                fri_d=pw.findViewById(R.id.fri_resi_c);
                sat_d=pw.findViewById(R.id.sat_resi_c);
                sun_d=pw.findViewById(R.id.sun_resi_c);
                APIInterface service = SingletonRetrofit.getAPIInterface();

                Call<show_commertial> call = service.getListCommertial(id_view);
                Log.i("Call", String.valueOf(call.request().url()));

                call.enqueue(new Callback<show_commertial>() {
                    @Override
                    public void onResponse(Call<show_commertial> call, Response<show_commertial> response) {

                        Log.e("Residential response1:", String.valueOf(response.isSuccessful()));
                        Log.e("Residential response2:", String.valueOf(response.code()));
                        Log.e("Residential response3:", String.valueOf(response.errorBody()));
                        if (response.body() != null) {
                            Log.i("Make  response: ", String.valueOf(response.body()));
                            comm_show_array =  response.body();
                            ArrayList demo_str;
                            int id_res,id_listing,address_id;
                            int i;
//                           for (i = 0; i < make_array.size(); i++) {
                            Log.i("Day array: ", String.valueOf(comm_show_array.getAddress()));
                            Log.i("Day array: ", String.valueOf(comm_show_array.getAddress().getCountry()));
                            Log.i("Day array: ", String.valueOf(comm_show_array.getDaysArray()));
//                           TextView price_tex,socket_txt,avg_rate_txt,vtg_txt,booking_txt,day_txt,mon_d,tue_d,wed_d,thu_d,fri_d,sat_d,sun_d;
                            GoogleMapOptions options = new GoogleMapOptions();
                            options.liteMode(true);
                            SupportMapFragment mapFrag = SupportMapFragment.newInstance(options);
                            assert mapFrag.getFragmentManager() != null;
                            mapFrag.getFragmentManager().findFragmentById(R.id.map_com);
                            mapFrag.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap mMap) {
                                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                                    mMap.clear(); //clear old markers

                                    CameraPosition googlePlex = CameraPosition.builder()
                                            .target(new LatLng(37.4219999, -122.0862462))
                                            .zoom(10)
                                            .bearing(0)
                                            .tilt(45)
                                            .build();

                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(37.4219999, -122.0862462))
                                            .title("Spider Man")
                                            .icon(bitmapDescriptorFromVector(v.getRootView().getContext(), R.drawable.arrow_d)));
                                }
                            });

                            socket_txt.setText(String.valueOf(comm_show_array.getProvider().getSocket()));
                            avg_rate_txt.setText(String.valueOf(comm_show_array.getProvider().getRateStructure()));
                            vtg_txt.setText(String.valueOf(comm_show_array.getProvider().getVoltage()));
                            day_txt.setText(String.valueOf(comm_show_array.getProvider().getDays()));
                            address_str.setText(comm_show_array.getAddress().getAddress1() +"," +comm_show_array.getAddress().getAddress2() +"," +comm_show_array.getAddress().getCountry()+","+comm_show_array.getAddress().getState());

                            List<String> days_array=comm_show_array.getDaysArray();
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
                            socket_empty=String.valueOf(comm_show_array.getProvider().getSocket());
                            if (socket_empty==null || socket_empty.isEmpty() || socket_empty.equals("null"))
                            {
                                //Display chrager details

                            }
                            else {
                                //socket detail


                            }
                            //booking show
                            booking=(comm_show_array.getProvider().getInstantBooking());
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
                    public void onFailure(Call<show_commertial> call, Throwable t) {
                        Log.i("Make error: ", String.valueOf(t));

                    }
                });

                Button ok_btn;
                ok_btn=pw.findViewById(R.id.button147);
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                    }
                });
                pw.show();


            }

        });
        edit_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditCommercialListingActivity.class);
                //Intent intent = new Intent(v.getContext(), edit_commertial_listing.class);
                final DataModel itemLabel = dataSet.get(listPosition);
                int list_id=itemLabel.getId();
                intent.putExtra("List_V_ID",String.valueOf(list_id));
                v.getContext().startActivity(intent);
            }
        });
        addres_com.setText(dataSet.get(listPosition).getName());
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

