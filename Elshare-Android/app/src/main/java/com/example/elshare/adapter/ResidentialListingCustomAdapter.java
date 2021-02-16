package com.example.elshare.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.elshare.DataModel;
import com.example.elshare.EditResidentialListingActivity;
import com.example.elshare.R;
import com.example.elshare.list_residential;
import com.example.elshare.utils.SingletonRetrofit;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import datamodel.APIInterface;
import datamodel.ResidentialListing;
import datamodel.residential_show_list.show_residential;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResidentialListingCustomAdapter extends RecyclerView.Adapter<ResidentialListingCustomAdapter.MyViewHolder> {
    Dialog showListingDialog;
    private ArrayList<DataModel> dataSet;
    private ResidentialListingCustomAdapter.ItemOnClickListner mListner;
    private show_residential showResidentialObject;
    private StringBuilder dayWithTimeStringBuilder = new StringBuilder("");

    public ResidentialListingCustomAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }

    public void setItemOnClickListner(ResidentialListingCustomAdapter.ItemOnClickListner listner) {
        mListner = listner;
    }

    @Override
    public ResidentialListingCustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_resi, parent, false);
        view.setOnClickListener(list_residential.myOnClickListener);
        showListingDialog = new Dialog(parent.getContext());
        ResidentialListingCustomAdapter.MyViewHolder myViewHolder =
                new ResidentialListingCustomAdapter.MyViewHolder(view, mListner);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ResidentialListingCustomAdapter.MyViewHolder holder, final int listPosition) {

        final EditText addres_field = holder.addres_field;
        final ImageButton deleteButton = holder.deleteButton;
        final ImageButton viewButton = holder.viewButton;
        ImageButton editButton = holder.editButton;
        TextView index_view = holder.index_txt;

        deleteButton.setOnClickListener(new View.OnClickListener() {
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
                            public void onClick(DialogInterface arg0, int arg1) {
                                int id_delete = itemLabel.getId();
                                APIInterface service = SingletonRetrofit.getAPIInterface();

                                Call<List<ResidentialListing>> call = service.deleteResidentialListing(id_delete);
                                Log.i("Call", String.valueOf(call.request().url()));
                                dataSet.remove(listPosition);
                                notifyItemRemoved(listPosition);
                                notifyItemRangeChanged(listPosition, dataSet.size());


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

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final DataModel itemLabel = dataSet.get(listPosition);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("EDIT_LIST_ID", String.valueOf(itemLabel.getId()));
                editor.apply();

                showListingDialog.setContentView(R.layout.show_listing_custom_adapter);
                showListingDialog.setTitle("Residential Listing");
                showListingDialog.show();

                int id_view = itemLabel.getId();

                final TextView chargerTypeValueTextView;
                final TextView socketValueTextView;
                final TextView chargerBrandValueTextView;
                final TextView chargerModelValueTextView;
                final TextView connectorTypeValueTextView;
                final TextView electricityBoardValueTextView;
                final TextView powerOutputValueTextView;
                final TextView voltageValueTextView;
                final TextView amphereValueTextView;
                final TextView totalPriceValueTextView;
                final TextView instanceBookingValueTextView;
                final TextView daysValueTextView;
                final TextView mondayValueTextView;
                final TextView tuesdayValueTextView;
                final TextView wednesdayValueTextView;
                final TextView thursdayValueTextView;
                final TextView fridayValueTextView;
                final TextView saturdayValueTextView;
                final TextView sundayValueTextView;
                final ArrayList<TextView> fieldValueTextViewList = new ArrayList<>();

                // final TextView price_tex,socket_txt,avg_rate_txt,vtg_txt,booking_txt,day_txt,mon_d,tue_d,wed_d,thu_d,fri_d,sat_d,sun_d,address_str;
                chargerTypeValueTextView = showListingDialog.findViewById(R.id.charger_type_value);
                socketValueTextView = showListingDialog.findViewById(R.id.socket_value);
                chargerBrandValueTextView = showListingDialog.findViewById(R.id.charger_brand_value);
                chargerModelValueTextView = showListingDialog.findViewById(R.id.charger_model_value);
                connectorTypeValueTextView = showListingDialog.findViewById(R.id.charger_connector_value);
                electricityBoardValueTextView = showListingDialog.findViewById(R.id.electricity_board_value);
                powerOutputValueTextView = showListingDialog.findViewById(R.id.power_output_value);
                voltageValueTextView = showListingDialog.findViewById(R.id.voltage_value);
                amphereValueTextView = showListingDialog.findViewById(R.id.amphere_value);
                totalPriceValueTextView = showListingDialog.findViewById(R.id.total_price_value);
                instanceBookingValueTextView = showListingDialog.findViewById(R.id.instance_booking_value);
                daysValueTextView = showListingDialog.findViewById(R.id.days_value);
                mondayValueTextView = showListingDialog.findViewById(R.id.monday_value);
                tuesdayValueTextView = showListingDialog.findViewById(R.id.tuesday_value);
                wednesdayValueTextView = showListingDialog.findViewById(R.id.wednesday_value);
                thursdayValueTextView = showListingDialog.findViewById(R.id.thursday_value);
                fridayValueTextView = showListingDialog.findViewById(R.id.friday_value);
                saturdayValueTextView = showListingDialog.findViewById(R.id.saturday_value);
                sundayValueTextView = showListingDialog.findViewById(R.id.sunday_value);

                fieldValueTextViewList.add(chargerTypeValueTextView);
                fieldValueTextViewList.add(socketValueTextView);
                fieldValueTextViewList.add(chargerBrandValueTextView);
                fieldValueTextViewList.add(chargerModelValueTextView);
                fieldValueTextViewList.add(connectorTypeValueTextView);
                fieldValueTextViewList.add(electricityBoardValueTextView);
                fieldValueTextViewList.add(powerOutputValueTextView);
                fieldValueTextViewList.add(voltageValueTextView);
                fieldValueTextViewList.add(amphereValueTextView);
                fieldValueTextViewList.add(totalPriceValueTextView);
                fieldValueTextViewList.add(instanceBookingValueTextView);
                fieldValueTextViewList.add(daysValueTextView);
                fieldValueTextViewList.add(mondayValueTextView);
                fieldValueTextViewList.add(tuesdayValueTextView);
                fieldValueTextViewList.add(wednesdayValueTextView);
                fieldValueTextViewList.add(thursdayValueTextView);
                fieldValueTextViewList.add(fridayValueTextView);
                fieldValueTextViewList.add(saturdayValueTextView);
                fieldValueTextViewList.add(sundayValueTextView);


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


                        if (response.body() == null) {
                            Log.i("Make  response: null", null);
                            return;
                        }
                        Log.i("Make  response: ", String.valueOf(response.body()));
                        dayWithTimeStringBuilder = new StringBuilder("");
                        showResidentialObject = response.body();

                        GoogleMap googleMap;
                        MapView mMapView = (MapView) showListingDialog.findViewById(R.id.mapView);
                        MapsInitializer.initialize(showListingDialog.getContext());
                        mMapView.onCreate(showListingDialog.onSaveInstanceState());
                        mMapView.onResume();// needed to get the map to display immediately
                        mMapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(final GoogleMap googleMap) {
                                Double lat = showResidentialObject.getAddress().getLat();
                                Double lng = showResidentialObject.getAddress().getLng();
                                LatLng latLngPosition = new LatLng(lat, lng); ////your lat lng
                                MarkerOptions markerOptions = new MarkerOptions().position(latLngPosition);
                                googleMap.addMarker(markerOptions);

                                final String connectorTypeOrSocket;
                                String chargerType = showResidentialObject.getChargerType();
                                if ("Standard Domestic Socket".equalsIgnoreCase(chargerType)) {
                                    connectorTypeOrSocket = showResidentialObject.getProvider().getSocket();
                                }else {
                                    connectorTypeOrSocket = showResidentialObject.getConnectorType().getConnectorType();
                                }

                                final String powerOutput = showResidentialObject.getProvider().getPowerOutput();
                                final String totalPrice = showResidentialObject.getProvider().getFinalPrice();

                                //googleMap.addMarker(new MarkerOptions().position(latLngPosition).title("See Availability"));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLngPosition));
                                googleMap.getUiSettings().setZoomControlsEnabled(true);
                                googleMap.animateCamera(CameraUpdateFactory.zoomTo(8), 1000, null);

                                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick(Marker marker) {
                                        showAlertDialog();
                                    }
                                });

                                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {
                                        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                                            // Use default InfoWindow frame
                                            @Override
                                            public View getInfoWindow(Marker arg0) {
                                                return null;
                                            }

                                            // Defines the contents of the InfoWindow
                                            @Override
                                            public View getInfoContents(Marker arg0) {
                                                View markerView = null;
                                                try {
                                                    // Getting view from the layout file info_window_layout
                                                    markerView = showListingDialog.getLayoutInflater().inflate(R.layout.marker_view_information_layout,null);

                                                    // Getting reference to the TextView to set latitude
                                                    TextView connectorOrSocketTextView = (TextView) markerView.findViewById(R.id.connector_or_socket_type);
                                                    TextView powerOutputTextView = (TextView) markerView.findViewById(R.id.power_output_text_view);
                                                    TextView rateTextView = (TextView) markerView.findViewById(R.id.rate_text_view);
                                                    Button show_availability_button=(Button) markerView.findViewById(R.id.show_availability);

                                                    connectorOrSocketTextView.setText(connectorTypeOrSocket);
                                                    powerOutputTextView.setText(powerOutput);
                                                    rateTextView.setText("Rate : "+totalPrice);
                                                    show_availability_button.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            showAlertDialog();
                                                        }
                                                    });

                                                } catch (Exception ev) {
                                                    ev.printStackTrace();
                                                }
                                                return markerView;
                                            }
                                        });

                                        return false;
                                    }
                                });

                            }
                        });


                        String chargerType = showResidentialObject.getChargerType();
                        chargerTypeValueTextView.setText(chargerType);
                        if ("Standard Domestic Socket".equalsIgnoreCase(chargerType)) {
                            socketValueTextView.setText(showResidentialObject.getProvider().getSocket());
                        }

                        if ("Charging Station".equalsIgnoreCase(chargerType)) {
                            chargerBrandValueTextView.setText(showResidentialObject.getChargerBrand().getChargerBrand());
                            chargerModelValueTextView.setText(showResidentialObject.getChargerModel().getChargerModel());
                            connectorTypeValueTextView.setText(showResidentialObject.getConnectorType().getConnectorType());
                        }

                        electricityBoardValueTextView.setText(showResidentialObject.getProvider().getElectricity());
                        powerOutputValueTextView.setText(showResidentialObject.getProvider().getPowerOutput());
                        voltageValueTextView.setText(showResidentialObject.getProvider().getVoltage());
                        amphereValueTextView.setText(showResidentialObject.getProvider().getAmphere());
                        totalPriceValueTextView.setText(showResidentialObject.getProvider().getFinalPrice());
                        String selectedDays = showResidentialObject.getProvider().getDays();
                        daysValueTextView.setText(selectedDays);


                        //booking show
                        double booking = (showResidentialObject.getProvider().getInstantBooking());
                        if (booking == 0) {
                            instanceBookingValueTextView.setText("Not Available");
                        } else {
                            instanceBookingValueTextView.setText("Available");
                        }


                        if (!TextUtils.isEmpty(selectedDays)) {
                            if (showResidentialObject.getProvider().getSingleDay() == 0) {
                                StringBuilder stringBuilder = new StringBuilder("");
                                int listSize = showResidentialObject.getStartTime().size();
                                for (int itemIndex = 0; itemIndex < showResidentialObject.getStartTime().size(); itemIndex++) {
                                    String startTime = showResidentialObject.getStartTime().get(itemIndex);
                                    String endTime = showResidentialObject.getEndTime().get(itemIndex);
                                    stringBuilder.append(startTime + "-" + endTime);
                                    if (itemIndex < listSize - 1) {
                                        stringBuilder.append(", ");
                                    }
                                }
                                if (selectedDays.contains("monday")) {
                                    mondayValueTextView.setText(stringBuilder);
                                    dayWithTimeStringBuilder.append("\n Monday : " + stringBuilder);
                                }
                                if (selectedDays.contains("tuesday")) {
                                    tuesdayValueTextView.setText(stringBuilder);
                                    dayWithTimeStringBuilder.append("\n tuesday : " + stringBuilder);
                                }
                                if (selectedDays.contains("wednesday")) {
                                    wednesdayValueTextView.setText(stringBuilder);
                                    dayWithTimeStringBuilder.append("\n wednesday : " + stringBuilder);
                                }
                                if (selectedDays.contains("thursday")) {
                                    thursdayValueTextView.setText(stringBuilder);
                                    dayWithTimeStringBuilder.append("\n thursday : " + stringBuilder);
                                }
                                if (selectedDays.contains("friday")) {
                                    fridayValueTextView.setText(stringBuilder);
                                    dayWithTimeStringBuilder.append("\n friday : " + stringBuilder);
                                }
                                if (selectedDays.contains("saturday")) {
                                    saturdayValueTextView.setText(stringBuilder);
                                    dayWithTimeStringBuilder.append("\n saturday : " + stringBuilder);
                                }
                                if (selectedDays.contains("sunday")) {
                                    sundayValueTextView.setText(stringBuilder);
                                    dayWithTimeStringBuilder.append("\n sunday : " + stringBuilder);
                                }
                            }
                            else {

                                if (selectedDays.contains("monday")) {
                                    String selectedTimeValue = getSelectedTimeForDay(showResidentialObject.getStartTimeMon(), showResidentialObject.getEndTimeMon());
                                    mondayValueTextView.setText(selectedTimeValue);
                                    dayWithTimeStringBuilder.append("\n monday : " + selectedTimeValue);
                                }
                                if (selectedDays.contains("tuesday")) {
                                    String selectedTimeValue = getSelectedTimeForDay(showResidentialObject.getStartTimeTues(), showResidentialObject.getEndTimeTues());
                                    tuesdayValueTextView.setText(selectedTimeValue);
                                    dayWithTimeStringBuilder.append("\n tuesday : " + selectedTimeValue);
                                }
                                if (selectedDays.contains("wednesday")) {
                                    String selectedTimeValue = getSelectedTimeForDay(showResidentialObject.getStartTimeWed(), showResidentialObject.getEndTimeWed());
                                    wednesdayValueTextView.setText(selectedTimeValue);
                                    dayWithTimeStringBuilder.append("\n wednesday : " + selectedTimeValue);
                                }
                                if (selectedDays.contains("thursday")) {
                                    String selectedTimeValue = getSelectedTimeForDay(showResidentialObject.getStartTimeThus(), showResidentialObject.getEndTimeThus());
                                    thursdayValueTextView.setText(selectedTimeValue);
                                    dayWithTimeStringBuilder.append("\n thursday : " + selectedTimeValue);
                                }
                                if (selectedDays.contains("friday")) {
                                    String selectedTimeValue = getSelectedTimeForDay(showResidentialObject.getStartTimeFri(), showResidentialObject.getEndTimeFri());
                                    fridayValueTextView.setText(selectedTimeValue);
                                    dayWithTimeStringBuilder.append("\n friday : " + selectedTimeValue);
                                }
                                if (selectedDays.contains("saturday")) {
                                    String selectedTimeValue = getSelectedTimeForDay(showResidentialObject.getStartTimeSat(), showResidentialObject.getEndTimeSat());
                                    saturdayValueTextView.setText(selectedTimeValue);
                                    dayWithTimeStringBuilder.append("\n saturday : " + selectedTimeValue);
                                }
                                if (selectedDays.contains("sunday")) {
                                    String selectedTimeValue = getSelectedTimeForDay(showResidentialObject.getStartTimeSun(), showResidentialObject.getEndTimeSun());
                                    sundayValueTextView.setText(selectedTimeValue);
                                    dayWithTimeStringBuilder.append("\n sunday : " + selectedTimeValue);
                                }
                            }
                        }

                        for(TextView currentTextView : fieldValueTextViewList) {
                            if (TextUtils.isEmpty(currentTextView.getText())) {
                                TableRow tableRow = (TableRow) currentTextView.getParent();
                                tableRow.setVisibility(View.GONE);
                            }
                        }
                    }


                    @Override
                    public void onFailure(Call<show_residential> call, Throwable t) {
                        Log.i("Make error: ", String.valueOf(t));
                    }
                });


                Button closeButton = showListingDialog.findViewById(R.id.close_button);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showListingDialog.dismiss();
                    }
                });

                showListingDialog.show();

            }

        });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditResidentialListingActivity.class);
                //  Intent intent = new Intent(v.getContext(), edit_residential_listing.class);
                final DataModel itemLabel = dataSet.get(listPosition);
                int list_id = itemLabel.getId();
                intent.putExtra("List_V_ID", String.valueOf(list_id));
                v.getContext().startActivity(intent);

            }
        });

        addres_field.setText(dataSet.get(listPosition).getName());
        int num = listPosition + 1;
        index_view.setText(String.valueOf(num));

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
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

    public void showAlertDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(showListingDialog.getContext());
        builder1.setMessage("Residential Details \n\n Availability : \n" + dayWithTimeStringBuilder);
        builder1.setCancelable(true);

        builder1.setPositiveButton("close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

       /* builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/

        AlertDialog alertDialog = builder1.create();
        alertDialog.show();
    }


    public interface ItemOnClickListner {
        void OnItemClickListner(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        EditText addres_field;
        TextView index_txt;
        ImageButton editButton, deleteButton, viewButton;
        Context context;

        @SuppressLint("WrongViewCast")
        public MyViewHolder(View itemView, final ResidentialListingCustomAdapter.ItemOnClickListner listner) {
            super(itemView);
            this.addres_field = (EditText) itemView.findViewById(R.id.address_resi);
            this.deleteButton = (ImageButton) itemView.findViewById(R.id.delete_resi);
            this.viewButton = (ImageButton) itemView.findViewById(R.id.viewAsResi);
            this.index_txt = (TextView) itemView.findViewById(R.id.index_resi);
            this.editButton = (ImageButton) itemView.findViewById(R.id.edit_resi);
            context = itemView.getContext();
        }
    }
}



