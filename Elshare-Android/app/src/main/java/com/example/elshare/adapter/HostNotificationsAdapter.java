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

public class HostNotificationsAdapter extends RecyclerView.Adapter <HostNotificationsAdapter.ViewHolder> {
    private final ArrayList<String> listing_message;
    private final ArrayList<String> isRead;
    private final ArrayList<String> booking_id;
    private final ArrayList<String> notificationId;

    private Context context;
    SharedPreferences mPreferences;


    public HostNotificationsAdapter(Context context, ArrayList<String> isRead, ArrayList<String> booking_id, ArrayList<String> listing_message,ArrayList<String> notificationId) {
        super();
        this.context = context;
        this.listing_message = listing_message;
        this.booking_id=booking_id;
        this.notificationId=notificationId;
        this.isRead = isRead;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notification_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        if (isRead.get(i) != null) {
            Random rnd = new Random();
            viewHolder.mNotification.setBackgroundColor(Color.LTGRAY);
        }

        viewHolder.listingMessageText.setText(String.valueOf(listing_message.get(i)));
        int num = i + 1;
        viewHolder.myBookingIndex.setText(String.valueOf(num));
        viewHolder.mNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                APIInterface service = SingletonRetrofit.getAPIInterface();
                Call<ResponseBody> call = service.markHostNotificationRead( notificationId.get(i));
                Log.i("Url=", String.valueOf(call.request().url()));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.body() != null) {
                        } else {}
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("Error", String.valueOf(t.getMessage()));
                        Toast.makeText(v.getContext(),String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
                    }
                });
                Intent intent = new Intent(v.getContext(), bookingHistoryHostDetail.class);
                mPreferences = PreferenceManager.getDefaultSharedPreferences((v.getContext()));
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString("BH_BOOKING_ID", booking_id.get(i));
                editor.apply();
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listing_message.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView myBookingIndex;
        TextView listingMessageText;
        CardView mNotification;

        ViewHolder(View itemView) {
            super(itemView);
            myBookingIndex = itemView.findViewById(R.id.hostNotificationIndex);
            listingMessageText = itemView.findViewById(R.id.hostNotificationMessage);
            mNotification = itemView.findViewById(R.id.cardviewOfNotificationHost);
        }
    }
}
