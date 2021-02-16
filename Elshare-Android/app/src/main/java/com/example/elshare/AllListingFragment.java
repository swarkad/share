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

public class AllListingFragment extends Fragment {
    FrameLayout simpleFrameLayout6;
    TabLayout tabLayout6;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_listing, container, false);
        simpleFrameLayout6 = rootView.findViewById(R.id.simpleFrameLayout6);
        tabLayout6 = rootView.findViewById(R.id.simpleTabLayout6);
        final TabLayout.Tab firstTab = tabLayout6.newTab();
        firstTab.setText(ElshareConstants.RESIDENTIAL);

        tabLayout6.addTab(firstTab);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        list_residential av = new list_residential();
        ft.replace(R.id.simpleFrameLayout6, av);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        TabLayout.Tab secondTab = tabLayout6.newTab();
        secondTab.setText(ElshareConstants.COMMERCIAL);
        tabLayout6.addTab(secondTab);
        TabLayout.Tab thirdTab = tabLayout6.newTab();
        thirdTab.setText(ElshareConstants.PUBLIC);
        tabLayout6.addTab(thirdTab);

        for (int i = 0; i < tabLayout6.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout6.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 20, 0);
            tab.requestLayout();
        }

        tabLayout6.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new list_residential();

                        break;
                    case 1:
                        fragment = new list_commertial();
                        break;
                    case 2:
                        fragment = new list_public();
                        break;
                    default:
                        break;
                }

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout6, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Override method
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Override Method
            }
        });

        return rootView;
    }
}
