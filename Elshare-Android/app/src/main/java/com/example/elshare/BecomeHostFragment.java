package com.example.elshare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.elshare.utils.ElshareConstants;
import com.google.android.material.tabs.TabLayout;

public class BecomeHostFragment extends Fragment {
    FrameLayout simpleFrameLayout10;
    TabLayout tabLayout10;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_become_host, container, false);
        simpleFrameLayout10 = rootView.findViewById(R.id.simpleFrameLayout10);
        tabLayout10 = rootView.findViewById(R.id.simpleTabLayout10);

        TabLayout.Tab firstTab = tabLayout10.newTab();
        firstTab.setText(ElshareConstants.GENERAL);
        tabLayout10.addTab(firstTab);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AddAnotherGeneralFragment av = new AddAnotherGeneralFragment();
        ft.replace(R.id.simpleFrameLayout10, av);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        TabLayout.Tab secondTab = tabLayout10.newTab();
        secondTab.setText(ElshareConstants.AVAILABILITY);
        tabLayout10.addTab(secondTab);
        TabLayout.Tab thirdTab = tabLayout10.newTab();
        thirdTab.setText(ElshareConstants.PRICING);
        tabLayout10.addTab(thirdTab);
        tabLayout10.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new AddAnotherGeneralFragment();
                        break;
                    case 1:
                        fragment = new AllListingAvailabilityFragment();
                        break;
                    case 2:
                        fragment = new AddAnotherPricingFragment();
                        break;
                    default:
                        break;
                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout10, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Override method
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Override
            }
        });

        return rootView;
    }
}
