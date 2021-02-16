package com.example.elshare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class listing_frag extends Fragment {
    TextView edit_list, vac_mode;
    ViewPager viewPager;
    PageViewAdapter3 pageViewAdapter;
    FrameLayout simpleFrameLayout3;
    TabLayout tabLayout3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listing_frag, container, false);
        simpleFrameLayout3 = rootView.findViewById(R.id.simpleFrameLayout3);
        tabLayout3 = rootView.findViewById(R.id.simpleTabLayout3);

        TabLayout.Tab firstTab = tabLayout3.newTab();
        firstTab.setText("All Listing");
        tabLayout3.addTab(firstTab);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AllListingFragment av=new AllListingFragment();
        ft.replace(R.id.simpleFrameLayout3, av);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        TabLayout.Tab secondTab = tabLayout3.newTab();
        secondTab.setText("Add another listing");
        tabLayout3.addTab(secondTab);

        tabLayout3.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

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

                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout3, fragment);
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

//        edit_list = rootView.findViewById(R.id.button114);
//        edit_list.setBackgroundResource(R.drawable.green_round_btn);
//        vac_mode = rootView.findViewById(R.id.button115);
//        viewPager = rootView.findViewById(R.id.fragment_container3);
//        pageViewAdapter = new PageViewAdapter3(getFragmentManager());
//        viewPager.setAdapter(pageViewAdapter);
//        edit_list.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(0);
//
//            }
//        });
//        vac_mode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(1);
//
//            }
//        });
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                onChangeTab(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        return rootView;

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //    private void onChangeTab(int position) {
//        if (position == 0) {
//            edit_list.setTextSize(14);
//            vac_mode.setTextSize(14);
//            edit_list.setBackgroundResource(R.drawable.green_round_btn);
//            vac_mode.setBackgroundResource(R.drawable.bg_text);
//
//        }
//        if (position == 1) {
//            edit_list.setTextSize(14);
//            vac_mode.setTextSize(14);
//            edit_list.setBackgroundResource(R.drawable.bg_text);
//            vac_mode.setBackgroundResource(R.drawable.green_round_btn);
//
//        }
//    }
}
