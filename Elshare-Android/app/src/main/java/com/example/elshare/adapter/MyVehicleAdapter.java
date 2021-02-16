package com.example.elshare.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elshare.EditEvDriverInformation;
import com.example.elshare.MyAllVehicle;
import com.example.elshare.R;
import com.example.elshare.utils.SingletonRetrofit;

import java.util.ArrayList;

import datamodel.APIInterface;
import datamodel.ShowVehicleDetail;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyVehicleAdapter extends RecyclerView.Adapter <MyVehicleAdapter.ViewHolder>{
    private ArrayList<String> makeStr;
    private ArrayList<String> modelStr;
    private ArrayList<String> licenseStr;
    private ArrayList<Integer> vehicleId;
    private Context context;
    Dialog viewDetailDialog;
    SharedPreferences mPreferences;
    SharedPreferences.Editor editor;

    public MyVehicleAdapter(Context context, ArrayList<String> makeStr, ArrayList<String> modelStr, ArrayList<String> licenseStr, ArrayList<Integer> vehicleId) {
        super();
        this.context = context;
        this.makeStr = makeStr;
        this.modelStr = modelStr;
        this.licenseStr =licenseStr ;
        this.vehicleId = vehicleId;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_vehicle_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        viewHolder.allVehicleMake.setText(makeStr.get(i));
        viewHolder.allVehicleModel.setText(modelStr.get(i));
        viewHolder.allVehicleLicence.setText(licenseStr.get(i));
        Log.i("Vehicle Id:",String.valueOf(vehicleId.get(i)));
        int num=i + 1 ;
        viewHolder.allVehicleIndex.setText(String.valueOf(num));
        viewHolder.viewVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                viewDetailDialog=new Dialog(v.getRootView().getContext(), R.style.PauseDialog);
                viewDetailDialog.setTitle("Vehicle Details");
                viewDetailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                viewDetailDialog.setContentView(R.layout.view_vehicle_detail);
                final TextView vehicle_type=viewDetailDialog.findViewById(R.id.viewVehicltype);
                final TextView vehicle_make=viewDetailDialog.findViewById(R.id.viewMake);
                final TextView vehicle_model=viewDetailDialog.findViewById(R.id.viewModel);
                final TextView vehicle_licence=viewDetailDialog.findViewById(R.id.viewLicence);
                final TextView vehicle_year=viewDetailDialog.findViewById(R.id.viewModelYear);
                final TextView vehicle_battery_size=viewDetailDialog.findViewById(R.id.viewBatterySize);
                final TextView vehicle_charger_size=viewDetailDialog.findViewById(R.id.viewChargerSize);
                final APIInterface service = SingletonRetrofit.getAPIInterface();
                Call<ShowVehicleDetail> call=service.getVehicleDetail(vehicleId.get(i));
                Log.i("Book url:",String.valueOf(call.request().url()));
                call.enqueue(new Callback<ShowVehicleDetail>() {
                    @Override
                    public void onResponse(Call<ShowVehicleDetail> call, Response<ShowVehicleDetail> response) {
                        if (response.body() != null) {
                            ShowVehicleDetail showVehicleDetail=response.body();
                            vehicle_type.setText(String.valueOf(showVehicleDetail.getType()));
                            vehicle_make.setText(showVehicleDetail.getMake());
                            vehicle_model.setText(showVehicleDetail.getModel());
                            vehicle_licence.setText(String.valueOf(showVehicleDetail.getLicence()));
                            vehicle_year.setText(String.valueOf(showVehicleDetail.getModelYear()));
                            vehicle_battery_size.setText(String.valueOf(showVehicleDetail.getBatterySize()));
                            vehicle_charger_size.setText(String.valueOf(showVehicleDetail.getChargerSize()));

                        }
                    }
                    @Override
                    public void onFailure(Call<ShowVehicleDetail> call, Throwable t) {
                        Log.i("Error", String.valueOf(t.getMessage()));
                        Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                viewDetailDialog.show();
            }
        });
        viewHolder.editVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditEvDriverInformation.class);
                mPreferences = PreferenceManager.getDefaultSharedPreferences((v.getContext()));
                editor=mPreferences.edit();
                editor.putInt("VEHICLE_ID", vehicleId.get(i));
                editor.commit();
                v.getContext().startActivity(intent);
            }
        });
        viewHolder.deleteVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final APIInterface service = SingletonRetrofit.getAPIInterface();
                Call<ResponseBody> call=service.deleteVehicle(vehicleId.get(i));
                Log.i("Book url:",String.valueOf(call.request().url()));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.body() != null) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                            alertDialogBuilder.setMessage("Vehicle Delete successfully!!!");
                            alertDialogBuilder.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            Intent intent = new Intent(v.getContext(), MyAllVehicle.class);
                                            v.getContext().startActivity(intent);
                                            ((Activity)context).finish();

                                        }
                                    });


                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                        }
                    }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.i("Error", String.valueOf(t.getMessage()));
                            Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });

    }

    @Override
    public int getItemCount() {
        return makeStr.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView allVehicleIndex;
        TextView allVehicleMake;
        TextView allVehicleModel;
        TextView allVehicleLicence;
        Button editVehicle;
        Button viewVehicle;
        Button deleteVehicle;

        CardView mNotification;
        ViewHolder(View itemView) {
            super(itemView);
            allVehicleMake=itemView.findViewById(R.id.allVehicleMake);
            allVehicleModel=itemView.findViewById(R.id.allVehicleModel);
            allVehicleIndex=itemView.findViewById(R.id.allVehicleIndex);
            allVehicleLicence=itemView.findViewById(R.id.allVehicleLicence);
            editVehicle=itemView.findViewById(R.id.editAllVehicle);
            deleteVehicle=itemView.findViewById(R.id.deleteAllVehicle);
            viewVehicle=itemView.findViewById(R.id.viewAllVehicle);
        }
    }
}