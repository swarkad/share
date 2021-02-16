package com.example.elshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;

public class show_list_activity extends AppCompatActivity {

    FrameLayout simpleFrameLayout2;
    TabLayout tabLayout2;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_activity);

        simpleFrameLayout2 = findViewById(R.id.simpleFrameLayout2_showList);
        tabLayout2 = findViewById(R.id.simpleTabLayout_showList);

        TabLayout.Tab firstTab = tabLayout2.newTab();
        firstTab.setText("General");
        tabLayout2.addTab(firstTab);
        FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        general_frag av=new general_frag<>();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        TabLayout.Tab secondTab = tabLayout2.newTab();
        secondTab.setText("Availability");
        tabLayout2.addTab(secondTab);
        TabLayout.Tab thirdTab = tabLayout2.newTab();
        thirdTab.setText("Pricing");
        tabLayout2.addTab(thirdTab);
        tabLayout2.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new general_frag<>();
                        break;
                    case 1:
                        fragment = new AvailabilityFragment();
                        break;
                    case 2:
                        fragment = new pricing_frag();
                        break;

                }
                FragmentManager fm = getFragmentManager();
                android.app.FragmentTransaction ft = fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });

    }
}
