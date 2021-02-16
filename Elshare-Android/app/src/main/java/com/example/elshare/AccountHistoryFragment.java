package com.example.elshare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elshare.adapter.ChargerTypeAdapter;


public class AccountHistoryFragment extends Fragment {
    RecyclerView mPaymentRecycleView;
    RecyclerView.LayoutManager mPaymentLayoutMAnager;
    ChargerTypeAdapter myVehicleAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.account_history_frag, container, false);

        return rootView;
    }

    public void onResume(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.account_history_frag, container, false);

    }
}
