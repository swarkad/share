package com.example.elshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elshare.R;
import com.example.elshare.bookingHistoryHostDetail;
import com.example.elshare.utils.SingletonRetrofit;

import java.util.ArrayList;
import java.util.Random;

import datamodel.APIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverPaymentHistoryAdapter extends RecyclerView.Adapter <DriverPaymentHistoryAdapter.ViewHolder> {
    private final ArrayList<String> billingArray;
    private final ArrayList<String> statusArray;
    private final ArrayList<String> amountArray;

    private Context context;
    SharedPreferences mPreferences;


    public DriverPaymentHistoryAdapter(Context context, ArrayList<String> billingArray, ArrayList<String> statusArray, ArrayList<String> amountArray) {
        super();
        this.context = context;
        this.billingArray = billingArray;
        this.amountArray=amountArray;
        this.statusArray=statusArray;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.payment_history_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        viewHolder.billingDate.setText(String.valueOf(billingArray.get(i)));
        viewHolder.amountText.setText(String.valueOf(amountArray.get(i)));
        viewHolder.statusText.setText(String.valueOf(statusArray.get(i)));

        int num = i + 1;
        viewHolder.myBookingIndex.setText(String.valueOf(num));

    }

    @Override
    public int getItemCount() {
        return billingArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView myBookingIndex;
        TextView billingDate;
        TextView amountText;
        TextView statusText;

        ViewHolder(View itemView) {
            super(itemView);
            myBookingIndex = itemView.findViewById(R.id.paymentHistoryDriverIndex);
            billingDate = itemView.findViewById(R.id.driverPHBilingDate);
            amountText = itemView.findViewById(R.id.driverPHAmount);
            statusText = itemView.findViewById(R.id.driverPHStatus);

        }
    }
}
