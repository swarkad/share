package com.example.elshare;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.elshare.adapter.HostNotificationsAdapter;
import com.example.elshare.utils.SingletonRetrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import datamodel.APIInterface;
import datamodel.NotificationHost;
import datamodel.NotificationHostPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HostNotification extends Fragment {

    RecyclerView mNotificationRecycleView;
    RecyclerView.LayoutManager mPaymentLayoutMAnager;
    HostNotificationsAdapter mNotificationAdapter;
    String mUserIdString;
    SharedPreferences mPrefrances;
    View mRootView;

    private List<NotificationHost> notification_object;
    private NotificationHost notification_array;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView= inflater.inflate(R.layout.fragment_host_notification, container, false);

        mPrefrances = PreferenceManager.getDefaultSharedPreferences(getContext());
        mUserIdString = mPrefrances.getString("Name", "null");

        final ArrayList<String> mNotificationMessage=new ArrayList<>();
        final ArrayList<String> mUserBookingId=new ArrayList<>();
        final ArrayList<String> mNotificationIsRead=new ArrayList<>();
        final ArrayList<String> mNotificationId=new ArrayList<>();

        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        APIInterface service= SingletonRetrofit.getAPIInterface();
        Call<NotificationHostPOJO> call=service.getDriverNotification(mUserIdString);
        Log.i("Book url:",String.valueOf(call.request().url()));
        call.enqueue(new Callback<NotificationHostPOJO>() {
            @Override
            public void onResponse(Call<NotificationHostPOJO> call, Response<NotificationHostPOJO> response) {

                if (response.body() != null) {
                    notification_object=response.body().getNotification();
                    if (notification_object!=null) {
                        int i;
                        for (i = 0; i < notification_object.size(); i++) {
                            notification_array = notification_object.get(i);
                            if (notification_array != null) {
                                mNotificationMessage.add(notification_array.getData().getConfirmation());
                                mUserBookingId.add(String.valueOf(notification_array.getData().getBookingId()));
                                mNotificationIsRead.add(notification_array.getReadAt());
                                mNotificationId.add(notification_array.getId());
                            }
                        }
                    }
                    Log.i("message:", String.valueOf(mNotificationMessage));
                    mNotificationRecycleView =mRootView.findViewById(R.id.host_notification_recycleview);
                    mNotificationRecycleView.setHasFixedSize(true);
                    mPaymentLayoutMAnager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    mNotificationRecycleView.setLayoutManager(mPaymentLayoutMAnager);
                    mNotificationAdapter = new HostNotificationsAdapter(getContext(),mNotificationIsRead,mUserBookingId,mNotificationMessage,mNotificationId);
                    mNotificationRecycleView.setAdapter(mNotificationAdapter);
                    mProgressDialog.dismiss();
                }
                else
                {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<NotificationHostPOJO> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        return mRootView;
    }
}