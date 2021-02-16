package com.example.elshare.adapter;

import android.annotation.SuppressLint;
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

import androidx.annotation.ColorInt;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elshare.BookingHistoryDriver;
import com.example.elshare.EditEvDriverInformation;
import com.example.elshare.HostBookingHistory;
import com.example.elshare.MyAllVehicle;
import com.example.elshare.R;
import com.example.elshare.bookingHistoryHostDetail;
import com.example.elshare.login_home;
import com.example.elshare.utils.SingletonRetrofit;

import java.util.ArrayList;
import java.util.List;

import Booking_history_pojo.HostBookingHistoryPojo;
import Booking_history_pojo.hostParticularBookingDetail;
import Booking_history_pojo.hostParticularBookingDetailBooking;
import datamodel.APIInterface;
import datamodel.ShowVehicleDetail;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class hostBookingHistoryAdapter extends RecyclerView.Adapter <hostBookingHistoryAdapter.ViewHolder> {
    private ArrayList<String> orderNumber;
    private ArrayList<String> totalAmount;
    private ArrayList<String> bookingDate;
    private ArrayList<String> bookingId;
    private ArrayList<String> bookingCount;
    private List<hostParticularBookingDetail> booking_set  ;
    private  hostParticularBookingDetail booking_array ;
    private String roleString;
    private Context context;
    SharedPreferences mPreferences;

    public hostBookingHistoryAdapter(Context context, ArrayList<String> orderNumber, ArrayList<String> totalAmount, ArrayList<String> bookingDate,ArrayList<String> bookingId,ArrayList<String> bookingCount,String roleString) {
        super();
        this.context = context;
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.bookingDate = bookingDate;
        this.bookingId=bookingId;
        this.bookingCount=bookingCount;
        this.roleString=roleString;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.host_booking_history_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        viewHolder.orderNumberHostText.setText(orderNumber.get(i));
        viewHolder.totalAmountHostText.setText(totalAmount.get(i));
        viewHolder.bookingDateText.setText(bookingDate.get(i));
        final String booning_id=bookingId.get(i);
        final APIInterface service = SingletonRetrofit.getAPIInterface();
        Call<hostParticularBookingDetail> call = service.getBookingHistoryHostParticularBooking(booning_id);
        Log.i("Book url:", String.valueOf(call.request().url()));
        call.enqueue(new Callback<hostParticularBookingDetail>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<hostParticularBookingDetail> call, Response<hostParticularBookingDetail> response) {
                if (response.body() != null) {
                    booking_array=response.body();
                    if (booking_array!=null)
                    {
                        String isApprove=booking_array.getBooking().getIsApproved();
                        String isCancle=booking_array.getBooking().getIsCancelled();
                        String isReject=booking_array.getBooking().getIsRejected();
                        Log.i("Boolean:",isApprove+isCancle+isReject);

                        if (isApprove.equals("0") && isCancle.equals("0") && isReject.equals("0"))
                        {
                            viewHolder.bookingStatus.setText("Pending");
                            viewHolder.bookingStatus.setTextColor(Color.parseColor("#FF6A6E"));
                            viewHolder.cancleByUserText.setVisibility(View.GONE);
                        }
                        else if (isApprove.equals("1") && isCancle.equals("0") && isReject.equals("0"))
                        {
                            viewHolder.bookingStatus.setText("Approve");
                            viewHolder.bookingStatus.setTextColor(Color.parseColor("#010483"));
                            viewHolder.cancleByUserText.setVisibility(View.GONE);

                        }
                        else if (isApprove.equals("1") && isCancle.equals("1") && isReject.equals("0") )
                        {
                            viewHolder.bookingStatus.setText("Approve");
                            viewHolder.bookingStatus.setTextColor(Color.parseColor("#010483"));
                            viewHolder.cancleByUserText.setVisibility(View.VISIBLE);

                        }
                        else if (isReject.equals("1"))
                        {
                            viewHolder.bookingStatus.setText("Rejected");
                            viewHolder.bookingStatus.setTextColor(Color.parseColor("#FF6A6E"));
                            viewHolder.cancleByUserText.setVisibility(View.GONE);
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<hostParticularBookingDetail> call, Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
            }
        });
        viewHolder.indexCount.setText(String.valueOf(bookingCount.get(i)));

        viewHolder.viewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(v.getContext(), bookingHistoryHostDetail.class);
                mPreferences = PreferenceManager.getDefaultSharedPreferences((v.getContext()));
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString("BH_BOOKING_ID", bookingId.get(i));
                editor.putString("BOOKED_ON",bookingDate.get(i));
                editor.apply();
                v.getContext().startActivity(intent);
            }

        });
        viewHolder.deleteBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(v.getContext());
                deleteDialog.setTitle("Do you want to delete booking?");
                deleteDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                APIInterface service = SingletonRetrofit.getAPIInterface();
                                Call<ResponseBody> call = service.deleteBookingHost( bookingId.get(i));
                                Log.i("Url=", String.valueOf(call.request().url()));
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.body() != null) {
                                            Toast.makeText(v.getContext(), "Booking delete successfully!!!", Toast.LENGTH_LONG).show();
                                            if (roleString.equals("Host")) {
                                                Log.i("Role Host:",roleString);
                                                Intent intent = new Intent(v.getContext(), HostBookingHistory.class);
                                                v.getContext().startActivity(intent);
                                            }
                                            else if(roleString.equals("Driver")) {
                                                Log.i("Role Driver:",roleString);

                                                Intent intent = new Intent(v.getContext(), BookingHistoryDriver.class);
                                                v.getContext().startActivity(intent);
                                            }
                                            else {

                                            }

                                        } else {
                                            Toast.makeText(v.getContext(), "Booking not delete!!!", Toast.LENGTH_LONG).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Log.i("Error", String.valueOf(t.getMessage()));
                                        Toast.makeText(v.getContext(),String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        });
                deleteDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return orderNumber.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderNumberHostText;
        TextView totalAmountHostText;
        TextView bookingDateText;
        TextView bookingStatus;
        Button viewDetail;
        TextView cancleByUserText;
        Button deleteBooking;
        TextView indexCount;

        ViewHolder(View itemView) {
            super(itemView);
            totalAmountHostText = itemView.findViewById(R.id.totalAmountHost);
            indexCount=itemView.findViewById(R.id.bookingHistoryHostNumber);
            orderNumberHostText = itemView.findViewById(R.id.orderNumberHost);
            bookingDateText = itemView.findViewById(R.id.bookingOnHost);
            viewDetail = itemView.findViewById(R.id.showDetailHostBooking);
            cancleByUserText=itemView.findViewById(R.id.cancleByUser);
            bookingStatus=itemView.findViewById(R.id.statusHostPending);
            deleteBooking=itemView.findViewById(R.id.deleteDetailHostBooking);
        }
    }
}
