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

public class AddAnotherGeneralFragment extends Fragment {
    FrameLayout simpleFrameLayout11;
    TabLayout tabLayout11;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_another_general_frag, container, false);
        simpleFrameLayout11 = rootView.findViewById(R.id.simpleFrameLayout11);
        tabLayout11 = rootView.findViewById(R.id.simpleTabLayout11);
        final TabLayout.Tab firstTab = tabLayout11.newTab();
        firstTab.setText(ElshareConstants.RESIDENTIAL);

        tabLayout11.addTab(firstTab);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AddAnotherResidentialFragment av = new AddAnotherResidentialFragment();
        ft.replace(R.id.simpleFrameLayout11, av);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        TabLayout.Tab secondTab = tabLayout11.newTab();
        secondTab.setText(ElshareConstants.COMMERCIAL);
        tabLayout11.addTab(secondTab);
        TabLayout.Tab thirdTab = tabLayout11.newTab();
        thirdTab.setText(ElshareConstants.PUBLIC);
        tabLayout11.addTab(thirdTab);
        for (int i = 0; i < tabLayout11.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout11.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 20, 0);
            tab.requestLayout();
        }

        tabLayout11.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new AddAnotherResidentialFragment();
                        break;
                    case 1:
                        fragment = new AddAnotherCommercialFragment();
                        break;
                    case 2:
                        fragment = new add_another_public_g();
                        break;
                    default:
                        break;
                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout11, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Override method
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Override method
            }
        });

        return rootView;
    }

}