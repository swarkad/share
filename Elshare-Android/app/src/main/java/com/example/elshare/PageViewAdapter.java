package com.example.elshare;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PageViewAdapter extends FragmentPagerAdapter
{

    public PageViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)

    {
        Fragment fragment = null;

        switch (position)
        {
            case 0:
                fragment=new general_frag();
                break;
            case 1:
                fragment =new AvailabilityFragment();
                break;
            case 2:
                fragment=new pricing_frag();


        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
