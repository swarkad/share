package com.example.elshare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class host_frag extends Fragment
{
    TextView general,availability,pricing;
    ViewPager viewPager;
    PageViewAdapter pageViewAdapter;
    Button next_btn;
    MotionEvent ev;
    FrameLayout simpleFrameLayout2;
    TabLayout tabLayout2;
    private int buttonState = 1;
    Button title_button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.host_frag, container, false);
        simpleFrameLayout2 = rootView.findViewById(R.id.simpleFrameLayout2);
        tabLayout2 = rootView.findViewById(R.id.simpleTabLayout2);
        title_button=rootView.findViewById(R.id.button35);


        TabLayout.Tab firstTab = tabLayout2.newTab();
        firstTab.setText("General");
        tabLayout2.addTab(firstTab);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        general_frag av=new general_frag<>();
        ft.replace(R.id.simpleFrameLayout2, av);
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
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout2, fragment);
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

    return rootView;

    }

}
