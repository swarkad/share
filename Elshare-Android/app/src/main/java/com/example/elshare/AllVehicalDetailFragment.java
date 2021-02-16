package com.example.elshare;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elshare.adapter.MyVehicleAdapter;
import com.example.elshare.adapter.hostBookingHistoryAdapter;
import com.example.elshare.utils.SingletonRetrofit;

import java.util.ArrayList;
import java.util.List;

import datamodel.APIInterface;
import datamodel.AllVehicle;
import datamodel.DriverDetail;
import datamodel.DriverUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllVehicalDetailFragment extends Fragment {
    View mRootView;
    String name;
    SharedPreferences preferences;
    TextView mNoAnyVehicleText;
    LinearLayout mRecycleViewLayout;
    Integer driverId;
    private List<AllVehicle> allVehicles;
    private AllVehicle allVehicleArray;
    RecyclerView mAllVehicleRecycleView;
    MyVehicleAdapter myVehicleAdapter;
    RecyclerView.LayoutManager mMyVehicleLayoutMAnager;
    Integer userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView= inflater.inflate(R.layout.fragment_all_vehical_detail, container, false);

        mNoAnyVehicleText=mRootView.findViewById(R.id.myVehicleNoAnyVehicle);
        mNoAnyVehicleText.setVisibility(View.GONE);
        mRecycleViewLayout=mRootView.findViewById(R.id.myAllVehicleLayout);
        mRecycleViewLayout.setVisibility(View.GONE);


        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        name = preferences.getString("Name", "null");
        if (name.equals("null")) {
            Toast.makeText(getContext(),"User not login!!", Toast.LENGTH_LONG).show();
            mRecycleViewLayout.setVisibility(View.GONE);
            mNoAnyVehicleText.setVisibility(View.VISIBLE);
        }
        else {
            Log.i("Call ID", name);
            userId = Integer.parseInt(name);

            final ArrayList<String> makeArray = new ArrayList<>();
            final ArrayList<String> modelArray = new ArrayList<>();
            final ArrayList<String> licenseArray = new ArrayList<>();
            final ArrayList<Integer> vehicleIdArray = new ArrayList<>();
            final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();

            final APIInterface service = SingletonRetrofit.getAPIInterface();
            Call<DriverDetail> call = service.getDriverDetail(userId);
            Log.i("Book url:", String.valueOf(call.request().url()));
            call.enqueue(new Callback<DriverDetail>() {
                @Override
                public void onResponse(Call<DriverDetail> call, Response<DriverDetail> response) {
                    if (response.body() != null) {
                        DriverUser driverDetail = response.body().getUser();
                        if (driverDetail != null) {
                            driverId = driverDetail.getUserId();
                            Call<List<AllVehicle>> call2 = service.getAllVehicleOfParticularDriver(driverId);
                            Log.i("Book url:", String.valueOf(call2.request().url()));
                            call2.enqueue(new Callback<List<AllVehicle>>() {
                                @Override
                                public void onResponse(Call<List<AllVehicle>> call2, Response<List<AllVehicle>> response) {
                                    if (response.body() != null) {
                                        allVehicles = response.body();
                                        ArrayList make_array = (ArrayList) response.body();

                                        int i;
                                        for (i = 0; i < make_array.size(); i++) {
                                            allVehicleArray = allVehicles.get(i);
                                            makeArray.add(allVehicleArray.getMake());
                                            modelArray.add(allVehicleArray.getModel());
                                            licenseArray.add(allVehicleArray.getLicence());
                                            vehicleIdArray.add(allVehicleArray.getId());
                                        }

                                        Log.i("ArrayNAMe:", String.valueOf(makeArray));
                                        if (make_array.isEmpty()) {
                                            mRecycleViewLayout.setVisibility(View.GONE);
                                            mNoAnyVehicleText.setVisibility(View.VISIBLE);
                                        } else {
                                            mRecycleViewLayout.setVisibility(View.VISIBLE);
                                            mNoAnyVehicleText.setVisibility(View.GONE);
                                            mAllVehicleRecycleView = mRootView.findViewById(R.id.My_vehicle_recycleview);
                                            mAllVehicleRecycleView.setHasFixedSize(true);
                                            mMyVehicleLayoutMAnager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                            mAllVehicleRecycleView.setLayoutManager(mMyVehicleLayoutMAnager);
                                            myVehicleAdapter = new MyVehicleAdapter(getContext(), makeArray, modelArray, licenseArray, vehicleIdArray);
                                            mAllVehicleRecycleView.setAdapter(myVehicleAdapter);
                                        }
                                        mProgressDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<AllVehicle>> call2, Throwable t) {
                                    Log.i("Error", String.valueOf(t.getMessage()));
                                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            mRecycleViewLayout.setVisibility(View.GONE);
                            mNoAnyVehicleText.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mRecycleViewLayout.setVisibility(View.GONE);
                        mNoAnyVehicleText.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<DriverDetail> call, Throwable t) {
                    Log.i("Error", String.valueOf(t.getMessage()));
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        return  mRootView;
    }
}