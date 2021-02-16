package com.example.elshare;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class contact_us extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_contact_us,null);
    }
    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of contact frag");
        super.onResume();
    }
    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of contact frag");
        super.onPause();
    }
}
