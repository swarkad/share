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

public class AvailabilityTimeSlotAdapter extends RecyclerView.Adapter <AvailabilityTimeSlotAdapter.ViewHolder> {
    private final ArrayList<String> dayArray;
    private final ArrayList<String> timeArray;

    private Context context;


    public AvailabilityTimeSlotAdapter(Context context, ArrayList<String> dayArray, ArrayList<String> timeArray) {
        super();
        this.context = context;
        this.dayArray = dayArray;
        this.timeArray=timeArray;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.time_slot_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Log.i("Day recycle", String.valueOf(dayArray.get(i)));
        viewHolder.dayText.setText(String.valueOf(dayArray.get(i)));
        viewHolder.timeText.setText(String.valueOf(timeArray.get(i)));

    }

    @Override
    public int getItemCount() {
        return dayArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayText;
        TextView timeText;

        ViewHolder(View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.daySlot);
            timeText = itemView.findViewById(R.id.timeSlot);

        }
    }
}
