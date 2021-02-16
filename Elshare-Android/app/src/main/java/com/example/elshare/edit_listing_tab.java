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

import com.google.android.material.tabs.TabLayout;

public class edit_listing_tab extends Fragment
{
    FrameLayout simpleFrameLayout5;
    TabLayout tabLayout5;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_listing_tab, container, false);

        simpleFrameLayout5 = rootView.findViewById(R.id.simpleFrameLayout5);
        tabLayout5 = rootView.findViewById(R.id.simpleTabLayout5);

        TabLayout.Tab firstTab = tabLayout5.newTab();
        firstTab.setText("All Listing");
        tabLayout5.addTab(firstTab);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AllListingFragment av=new AllListingFragment();
        ft.replace(R.id.simpleFrameLayout5, av);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        TabLayout.Tab secondTab = tabLayout5.newTab();
        secondTab.setText("Add another Listing");
        tabLayout5.addTab(secondTab);
//        TabLayout.Tab thirdTab = tabLayout5.newTab();
//        thirdTab.setText("Delete Listing");
//        tabLayout5.addTab(thirdTab);

        tabLayout5.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new AllListingFragment();
                        break;
                    case 1:
                        fragment = new AddAnotherListingFragment();
                        break;
//                    case 2:
//                        fragment=new delete_another_listing();
//                        break;

                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout5, fragment);
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
        return  rootView;
    }
}
