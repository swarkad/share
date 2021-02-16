package com.example.elshare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class
general_frag<override> extends Fragment {
    ViewPager viewPager;
    PageViewAdapter4 pageViewAdapter;

    Button next_btn;
    private int buttonState = 1;
    FrameLayout simpleFrameLayout4;
    TabLayout tabLayout4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.general_frag, container, false);

        simpleFrameLayout4 = rootView.findViewById(R.id.simpleFrameLayout4);
        tabLayout4 = rootView.findViewById(R.id.simpleTabLayout4);

        final TabLayout.Tab firstTab = tabLayout4.newTab();
        firstTab.setText("Residential");

        tabLayout4.addTab(firstTab);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        residential av=new residential();
        ft.replace(R.id.simpleFrameLayout4, av);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        TabLayout.Tab secondTab = tabLayout4.newTab();
        secondTab.setText("Commercial");
        tabLayout4.addTab(secondTab);
        TabLayout.Tab thirdTab = tabLayout4.newTab();
        thirdTab.setText("Public");
        tabLayout4.addTab(thirdTab);
        for(int i=0; i < tabLayout4.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout4.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 20, 0);
            tab.requestLayout();
        }
        tabLayout4.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new residential();

                        break;
                    case 1:
                        fragment = new CommercialFragment();

                        break;
                    case 2:
                        fragment = new public_g();
                        break;

                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout4, fragment);
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