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

import com.example.elshare.adapter.DriverPaymentHistoryAdapter;
import com.example.elshare.adapter.HostNotificationsAdapter;
import com.example.elshare.utils.SingletonRetrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import datamodel.APIInterface;
import datamodel.DriverPaymentHistoryPojo;
import datamodel.NotificationHost;
import datamodel.NotificationHostPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DriverPaymentHistory extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DriverPaymentHistoryAdapter paymentHistoryAdapter;
    String mUserIdString;
    SharedPreferences preferences;
    View mRootView;

    private List<DriverPaymentHistoryPojo> paymentHistory_object;
    private DriverPaymentHistoryPojo paymentHistory_array;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView= inflater.inflate(R.layout.fragment_driver_payment_history, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mUserIdString = preferences.getString("Name", "null");

        final ArrayList<String> billingDatearray=new ArrayList<>();
        final ArrayList<String> statusArray=new ArrayList<>();
        final ArrayList<String> amountArray=new ArrayList<>();

        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        APIInterface service= SingletonRetrofit.getAPIInterface();
        Call<List<DriverPaymentHistoryPojo>> call=service.paymentHistoryDriver(mUserIdString);
        Log.i("Book url:",String.valueOf(call.request().url()));
        call.enqueue(new Callback<List<DriverPaymentHistoryPojo>>() {
            @Override
            public void onResponse(Call<List<DriverPaymentHistoryPojo>> call, Response<List<DriverPaymentHistoryPojo>> response) {

                if (response.body() != null) {
                    paymentHistory_object=response.body();
                    if (paymentHistory_object!=null) {
                        int i;
                        for (i = 0; i < paymentHistory_object.size(); i++) {
                            paymentHistory_array = paymentHistory_object.get(i);
                            if (paymentHistory_array != null) {
                                billingDatearray.add(paymentHistory_array.getBillingDate());
                                statusArray.add(String.valueOf(paymentHistory_array.getStatus()));
                                amountArray.add(String.valueOf(paymentHistory_array.getAmountINR()));
                            }
                        }
                    }
                    recyclerView =mRootView.findViewById(R.id.driver_PH_recycleview);
                    recyclerView.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    paymentHistoryAdapter = new DriverPaymentHistoryAdapter(getContext(),billingDatearray,amountArray,statusArray);
                    recyclerView.setAdapter(paymentHistoryAdapter);
                    mProgressDialog.dismiss();
                }
                else
                {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                    Log.i("Error:",response.message());
                }
            }


            @Override
            public void onFailure(Call<List<DriverPaymentHistoryPojo>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("Error 2:", Objects.requireNonNull(t.getMessage()));

            }
        });




        return mRootView;
    }
}